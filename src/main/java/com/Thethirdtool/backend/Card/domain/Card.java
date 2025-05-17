package com.Thethirdtool.backend.Card.domain;


import com.Thethirdtool.backend.Deck.domain.Deck;
import com.Thethirdtool.backend.Note.domain.Note;
import com.Thethirdtool.backend.common.jpa.AuditingField;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
@Builder
@Entity
public class Card extends AuditingField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_id")
    private Note note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deck_id")
    private Deck deck;

    @Column(name = "due_date")
    private Instant dueDate;

    private int intervalDays; //다음 복습까지 간격을 알려주는데 사용
    private boolean isArchived;
    private int successCount;
    //복습을 성공하든 실패하든 복습한 횟수
    private int reps;
    private int easeFactor;
    //📌 "카드를 외우는 데 실패한 횟수"
    private int lapses;

    @Enumerated(EnumType.STRING)
    private DuePeriod duePeriod;

    //카드 썸네일 관련 태그
    @ElementCollection
    @CollectionTable(name = "card_tags", joinColumns = @JoinColumn(name = "card_id"))
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();


    // ====== 학습 로직 ======

    public void archive() {
        this.isArchived = true;
        this.dueDate = null;
        this.intervalDays = 0;
    }


    //3day에서 제공하는 4개의 블록 중 하나-> 3day로 복귀하자 permanent용
    public void restoreToGeneralStudy() {
        this.isArchived = false;
        this.intervalDays = 1;
        this.dueDate = Instant.now().plusSeconds(86400L);
    }
    //3day에서 제공하는 4개의 블록 중 하나
    public void successReview() {
        this.reps += 1;
        this.successCount += 1;

        if (successCount == 1) intervalDays += 1;
        else if (successCount == 2) intervalDays += 2;
        else if (successCount == 3) intervalDays += 3;
        else intervalDays *= 2;

        this.dueDate = Instant.now().plusSeconds(intervalDays * 86400L);

        if (intervalDays > 30) {
            archive(); // 영구 저장소로 이동
        }
    }
    // success 수준 slow 버전
    public void successReviewSlow() {
        this.reps += 1;
        this.successCount += 1;

        if (intervalDays <= 3) {
            intervalDays += 1; // 처음엔 조금만 증가
        } else if (intervalDays <= 10) {
            intervalDays += 2; // 약간씩 증가
        } else if (intervalDays <= 20) {
            intervalDays += 3; // 여전히 점진적으로
        } else {
            intervalDays = (int) (intervalDays * 1.2); // 완만한 곱셈 증가
        }

        this.dueDate = Instant.now().plusSeconds(intervalDays * 86400L);

        if (intervalDays > 30) {
            archive(); // 영구 덱으로 전환
        }
    }
    // 3day에서 제공하는 4개의 블록 중 하나 -> 우선 3개라고 하자구 ㅎ
    public void failReview(float lapseMultiplier) {
        this.successCount = 0;
        this.lapses += 1;
        this.easeFactor = (int) (this.easeFactor * lapseMultiplier);
        this.dueDate = Instant.now().plusSeconds(10 * 60); // 10분 후 복습
    }
    //interval days 유지하고 계속 dueDate만 미루기
    public void refreshDueDate() {
        this.dueDate = Instant.now().plusSeconds(intervalDays * 86400L);
    }


    // permanent 프로젝트에서 선택된 due 그룹에 따라 초기화 permanent 버튼 중 하나
    public void resetDueGroup(String selectedGroup) {
        if (selectedGroup.equals("stay")) {
            keepCurrentDue(); // 아무 것도 하지 않음
            return;
        }

        int newInterval = switch (selectedGroup) {
            case "3day" -> 3;
            case "1week" -> 7;
            case "2week" -> 14;
            default -> throw new IllegalArgumentException("잘못된 그룹 선택");
        };

        this.isArchived = false;
        this.intervalDays = newInterval;
        this.dueDate = Instant.now().plusSeconds(newInterval * 86400L);
    }

    // 유지하기 (dueDate를 그대로 유지)- permanent에 그대로 잇기
    public void keepCurrentDue() {
        // 아무 작업도 하지 않음
    }

    // 범위 안에 있는 카드냐 ->> 로직 선택
    public boolean isInDueRangeByCreatedAt(int min, int max) {
        if (this.getCreatedAt() == null || this.dueDate == null) return false;

        long diffDays = ChronoUnit.DAYS.between(this.getCreatedAt(), this.dueDate);
        return !isArchived && diffDays > min && diffDays <= max;
    }





}

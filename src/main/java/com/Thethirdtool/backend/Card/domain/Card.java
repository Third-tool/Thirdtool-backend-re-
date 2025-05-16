package com.Thethirdtool.backend.Card.domain;


import com.Thethirdtool.backend.Deck.domain.Deck;
import com.Thethirdtool.backend.Note.domain.Note;
import com.Thethirdtool.backend.common.jpa.AuditingField;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

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





}

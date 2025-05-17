package com.Thethirdtool.backend.Card.domain.StudyPolicy;

import com.Thethirdtool.backend.Card.domain.Card;
import com.Thethirdtool.backend.Card.domain.StudyResult;

import java.util.LinkedHashMap;
import java.util.Map;

public class ThreeDayCardBehavior implements CardBehavior {

    @Override
    public void processStudyResult(Card card, StudyResult result) {
        switch (result) {
            case AGAIN -> card.failReview(0.8f); // 실패 시 간격 축소
            case NORMAL -> card.successReviewSlow(); // 보통: 일반 복습으로 리셋 ->
            case GOOD -> card.successReview(); // 성공 시 간격 증가
            case STAY -> card.refreshDueDate();
        }
    }

    @Override
    public void resetDueGroup(Card card, String groupName) {
        // 3day에선 resetGroup은 쓰지 않을 수 있음 → No-op 또는 예외
    }
    @Override
    public Map<String, String> previewIntervals(Card card) {
        Map<String, String> preview = new LinkedHashMap<>();

        int interval = card.getIntervalDays();
        int successCount = card.getSuccessCount();

        // GOOD
        int nextGood;
        if (successCount == 0) nextGood = interval + 1;
        else if (successCount == 1) nextGood = interval + 2;
        else if (successCount == 2) nextGood = interval + 3;
        else nextGood = interval * 2;
        preview.put("GOOD", nextGood + "일 후");

        // NORMAL
        int nextNormal;
        if (interval <= 3) nextNormal = interval + 1;
        else if (interval <= 10) nextNormal = interval + 2;
        else if (interval <= 20) nextNormal = interval + 3;
        else nextNormal = (int)(interval * 1.2);
        preview.put("NORMAL", nextNormal + "일 후");

        // AGAIN
        preview.put("AGAIN", "10분 후");

        // STAY
        preview.put("STAY", interval + "일 유지");

        return preview;
    }
}

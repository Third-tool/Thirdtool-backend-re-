package com.Thethirdtool.backend.Card.domain.StudyPolicy;

import com.Thethirdtool.backend.Card.domain.Card;
import com.Thethirdtool.backend.Card.domain.StudyResult;

import java.util.LinkedHashMap;
import java.util.Map;

public class PermanentCardBehavior implements CardBehavior {

    @Override
    public void processStudyResult(Card card, StudyResult result) {
        // Permanent에서는 study 결과 반영 안함 → No-op
    }

    @Override
    public void resetDueGroup(Card card, String groupName) {
        if (groupName == null || groupName.isBlank()) {
            card.keepCurrentDue(); // 유지
            return;
        }

        switch (groupName) {
            case "3day", "1week", "2week", "stay" -> card.resetDueGroup(groupName);
            default -> throw new IllegalArgumentException("잘못된 그룹 선택: " + groupName);
        }
    }

    @Override
    public Map<String, String> previewIntervals(Card card) {
        Map<String, String> preview = new LinkedHashMap<>();

        // STAY: 현재 interval 유지
        preview.put("STAY", card.getIntervalDays() + "일 유지");

        // 3DAY
        preview.put("3day", "3day로 복귀");

        // 1WEEK
        preview.put("1week", "1week로 복귀");

        // 2WEEK
        preview.put("2week", "2week로 복귀");

        return preview;
    }
}

package com.Thethirdtool.backend.Card.domain.StudyPolicy;

import com.Thethirdtool.backend.Card.domain.Card;
import com.Thethirdtool.backend.Card.domain.StudyResult;

import java.util.Map;

public interface CardBehavior {
    void processStudyResult(Card card, StudyResult result);
    void resetDueGroup(Card card, String groupName);

    /**
     * 학습 결과에 따른 간격 예측
     */
    Map<String, String> previewIntervals(Card card);
}
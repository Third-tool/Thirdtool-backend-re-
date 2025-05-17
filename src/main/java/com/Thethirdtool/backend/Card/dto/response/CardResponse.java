package com.Thethirdtool.backend.Card.dto.response;

import com.Thethirdtool.backend.Card.domain.Card;

public record CardResponse(
        Long cardId,
        String question,
        String answer,
        int intervalDays,
        boolean isArchived,
        int successCount,
        int reps,
        int easeFactor,
        int lapses,
        String imageUrl // ✅ 추가된 필드
) {
    public static CardResponse from(Card card) {
        return new CardResponse(
                card.getId(),
                card.getNote().getQuestion(),
                card.getNote().getAnswer(),
                card.getIntervalDays(),
                card.isArchived(),
                card.getSuccessCount(),
                card.getReps(),
                card.getEaseFactor(),
                card.getLapses(),
                card.getImageUrl() // ✅ 추가된 값
        );
    }
}
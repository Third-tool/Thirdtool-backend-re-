package com.Thethirdtool.backend.Card.domain.StudyPolicy;

import com.Thethirdtool.backend.Card.domain.Card;

public class CardBehaviorFactory {

    private static final CardBehavior THREE_DAY = new ThreeDayCardBehavior();
    private static final CardBehavior PERMANENT = new PermanentCardBehavior();

    public static CardBehavior from(Card card) {
        return card.isArchived() ? PERMANENT : THREE_DAY;
    }
}

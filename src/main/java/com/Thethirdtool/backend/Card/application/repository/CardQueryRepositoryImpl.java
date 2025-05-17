package com.Thethirdtool.backend.Card.application.repository;

import com.Thethirdtool.backend.Card.domain.Card;
import com.Thethirdtool.backend.Card.domain.QCard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CardQueryRepositoryImpl implements CardQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Card> findNextCards(Long deckId, Long lastId, int size) {
        QCard card = QCard.card;

        return queryFactory
                .selectFrom(card)
                .where(
                        card.deck.id.eq(deckId),
                        lastId != null ? card.id.gt(lastId) : null
                      )
                .orderBy(card.id.asc()) // 최신순으로 하고 싶다면 desc
                .limit(size)
                .fetch();
    }
}

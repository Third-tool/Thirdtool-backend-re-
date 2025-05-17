package com.Thethirdtool.backend.Card.application.repository;

import com.Thethirdtool.backend.Card.domain.Card;
import com.Thethirdtool.backend.Deck.domain.Deck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByDeckAndIsArchivedFalse(Deck deck);
    List<Card> findByDeckAndIsArchivedTrue(Deck deck);
    List<Card> findNextCards(Long deckId, Long lastId, int size);

    // 3day 프로젝트용 카드
    List<Card> findByDeckIdAndIsArchivedFalseAndIntervalDaysLessThanEqual(Long deckId, int intervalLimit);

    // 특정 덱에 속한 아카이브 카드들
    List<Card> findByDeckIdAndIsArchivedTrue(Long deckId);
}
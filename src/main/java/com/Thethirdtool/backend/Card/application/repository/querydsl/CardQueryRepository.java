package com.Thethirdtool.backend.Card.application.repository.querydsl;

import com.Thethirdtool.backend.Card.domain.Card;

import java.util.List;

public interface CardQueryRepository {
    List<Card> findNextCards(Long deckId, Long lastId, int size);
}

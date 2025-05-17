package com.Thethirdtool.backend.Deck.application.repository;

import com.Thethirdtool.backend.Deck.domain.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeckRepository extends JpaRepository<Deck,Long> {
    List<Deck> findByParentIsNull();
}

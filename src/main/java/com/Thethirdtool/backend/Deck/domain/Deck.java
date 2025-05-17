package com.Thethirdtool.backend.Deck.domain;

import com.Thethirdtool.backend.Card.domain.Card;
import com.Thethirdtool.backend.Card.domain.DuePeriod;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "deck", fetch = FetchType.LAZY)
    private List<Card> cards = new ArrayList<>();

    @Column(nullable = false)
    private boolean isFrozen = false;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Deck parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Deck> children = new ArrayList<>();

    public List<Card> getCardsByDuePeriod(DuePeriod period) {
        List<Card> result = new ArrayList<>();
        for (Card card : cards) {
            if (card.getDuePeriod() == period) {
                result.add(card);
            }
        }
        return result;
    }


    public boolean isRoot() {
        return this.parent == null;
    }

    public void freeze() {
        this.isFrozen = true;
    }

    public void unfreeze() {
        this.isFrozen = false;
    }


}
package com.Thethirdtool.backend.Deck.domain;

import com.Thethirdtool.backend.Card.domain.Card;
import com.Thethirdtool.backend.Card.domain.DuePeriod;
import com.Thethirdtool.backend.Member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member owner;

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

    // ====== 빌더 패턴======
    @Builder
    public Deck(String name, Deck parent, boolean isFrozen) {
        this.name = name;
        this.parent = parent;
        this.isFrozen = isFrozen;
        this.cards = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    public static Deck of(String name, Deck parent) {
        return Deck.builder()
                   .name(name)
                   .parent(parent)
                   .isFrozen(false)
                   .build();
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

    public List<Card> getCardsByDuePeriod(DuePeriod period) {
        List<Card> result = new ArrayList<>();
        for (Card card : cards) {
            if (card.getDuePeriod() == period) {
                result.add(card);
            }
        }
        return result;
    }


}
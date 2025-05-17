package com.Thethirdtool.backend.Deck.dto.response;

import com.Thethirdtool.backend.Deck.domain.Deck;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeckResponse {

    private Long id;
    private String name;
    private boolean isFrozen;
    private boolean isRoot;
    private boolean hasChildren;

    public static DeckResponse from(Deck deck) {
        return DeckResponse.builder()
                           .id(deck.getId())
                           .name(deck.getName())
                           .isFrozen(deck.isFrozen())
                           .isRoot(deck.isRoot())
                           .hasChildren(deck.getChildren() != null && !deck.getChildren().isEmpty())
                           .build();
    }
}
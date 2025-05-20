package com.Thethirdtool.backend.Deck.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDeck is a Querydsl query type for Deck
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDeck extends EntityPathBase<Deck> {

    private static final long serialVersionUID = -158807269L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDeck deck = new QDeck("deck");

    public final ListPath<com.Thethirdtool.backend.Card.domain.Card, com.Thethirdtool.backend.Card.domain.QCard> cards = this.<com.Thethirdtool.backend.Card.domain.Card, com.Thethirdtool.backend.Card.domain.QCard>createList("cards", com.Thethirdtool.backend.Card.domain.Card.class, com.Thethirdtool.backend.Card.domain.QCard.class, PathInits.DIRECT2);

    public final ListPath<Deck, QDeck> children = this.<Deck, QDeck>createList("children", Deck.class, QDeck.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isFrozen = createBoolean("isFrozen");

    public final StringPath name = createString("name");

    public final com.Thethirdtool.backend.Member.domain.QMember owner;

    public final QDeck parent;

    public QDeck(String variable) {
        this(Deck.class, forVariable(variable), INITS);
    }

    public QDeck(Path<? extends Deck> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDeck(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDeck(PathMetadata metadata, PathInits inits) {
        this(Deck.class, metadata, inits);
    }

    public QDeck(Class<? extends Deck> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.owner = inits.isInitialized("owner") ? new com.Thethirdtool.backend.Member.domain.QMember(forProperty("owner")) : null;
        this.parent = inits.isInitialized("parent") ? new QDeck(forProperty("parent"), inits.get("parent")) : null;
    }

}


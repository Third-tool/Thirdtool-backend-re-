package com.Thethirdtool.backend.Member.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 1285591421L;

    public static final QMember member = new QMember("member1");

    public final ListPath<com.Thethirdtool.backend.Deck.domain.Deck, com.Thethirdtool.backend.Deck.domain.QDeck> decks = this.<com.Thethirdtool.backend.Deck.domain.Deck, com.Thethirdtool.backend.Deck.domain.QDeck>createList("decks", com.Thethirdtool.backend.Deck.domain.Deck.class, com.Thethirdtool.backend.Deck.domain.QDeck.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath kakaoId = createString("kakaoId");

    public final StringPath nickname = createString("nickname");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}


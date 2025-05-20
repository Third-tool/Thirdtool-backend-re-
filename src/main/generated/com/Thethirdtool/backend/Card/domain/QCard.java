package com.Thethirdtool.backend.Card.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCard is a Querydsl query type for Card
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCard extends EntityPathBase<Card> {

    private static final long serialVersionUID = 1676830057L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCard card = new QCard("card");

    public final com.Thethirdtool.backend.common.jpa.QAuditingField _super = new com.Thethirdtool.backend.common.jpa.QAuditingField(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.Instant> createdAt = _super.createdAt;

    public final com.Thethirdtool.backend.Deck.domain.QDeck deck;

    public final DateTimePath<java.time.Instant> dueDate = createDateTime("dueDate", java.time.Instant.class);

    public final EnumPath<DuePeriod> duePeriod = createEnum("duePeriod", DuePeriod.class);

    public final NumberPath<Integer> easeFactor = createNumber("easeFactor", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<String, StringPath> imageUrls = this.<String, StringPath>createList("imageUrls", String.class, StringPath.class, PathInits.DIRECT2);

    public final NumberPath<Integer> intervalDays = createNumber("intervalDays", Integer.class);

    public final BooleanPath isArchived = createBoolean("isArchived");

    public final NumberPath<Integer> lapses = createNumber("lapses", Integer.class);

    //inherited
    public final DateTimePath<java.time.Instant> modifiedAt = _super.modifiedAt;

    public final com.Thethirdtool.backend.Note.domain.QNote note;

    public final NumberPath<Integer> reps = createNumber("reps", Integer.class);

    public final NumberPath<Integer> successCount = createNumber("successCount", Integer.class);

    public final ListPath<String, StringPath> tags = this.<String, StringPath>createList("tags", String.class, StringPath.class, PathInits.DIRECT2);

    public QCard(String variable) {
        this(Card.class, forVariable(variable), INITS);
    }

    public QCard(Path<? extends Card> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCard(PathMetadata metadata, PathInits inits) {
        this(Card.class, metadata, inits);
    }

    public QCard(Class<? extends Card> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.deck = inits.isInitialized("deck") ? new com.Thethirdtool.backend.Deck.domain.QDeck(forProperty("deck"), inits.get("deck")) : null;
        this.note = inits.isInitialized("note") ? new com.Thethirdtool.backend.Note.domain.QNote(forProperty("note")) : null;
    }

}


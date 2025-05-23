package com.Thethirdtool.backend.common.jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuditingField is a Querydsl query type for AuditingField
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QAuditingField extends EntityPathBase<AuditingField> {

    private static final long serialVersionUID = 458503402L;

    public static final QAuditingField auditingField = new QAuditingField("auditingField");

    public final DateTimePath<java.time.Instant> createdAt = createDateTime("createdAt", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> modifiedAt = createDateTime("modifiedAt", java.time.Instant.class);

    public QAuditingField(String variable) {
        super(AuditingField.class, forVariable(variable));
    }

    public QAuditingField(Path<? extends AuditingField> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuditingField(PathMetadata metadata) {
        super(AuditingField.class, metadata);
    }

}


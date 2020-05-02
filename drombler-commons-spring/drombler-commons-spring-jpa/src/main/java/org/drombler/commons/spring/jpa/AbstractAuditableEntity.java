/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (GitHub user: puce77).
 * Copyright 2019 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.spring.jpa;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import org.softsmithy.lib.persistence.AbstractVersionedEntity;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * A base class for auditable entities.
 *
 * @see AuditingEntityListener
 */
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class AbstractAuditableEntity extends AbstractVersionedEntity {

    @CreatedDate
    @Column(name = "CREATED_AT", updatable = false)
    private Instant createdAt;

    @CreatedBy
    @Column(name = "CREATED_BY", updatable = false)
    private String createdBy;

    @CreatedDate
    @Column(name = "LAST_MODIFIED_AT")
    private Instant lastModifiedAt;

    @CreatedBy
    @Column(name = "LAST_MODIFIED_BY")
    private String lastModifiedBy;

    /**
     * Gets the timestamp, when this entity was created.
     *
     * @return the timestamp, when this entity was created
     */
    public Instant getCreatedAt() {
        return createdAt;
    }

    /**
     * Gets the user id, who created this entity.
     *
     * @return the user id, who created this entity
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Gets the timestamp, when this entity was last modified.
     *
     * @return the timestamp, when this entity was last modified
     */
    public Instant getLastModifiedAt() {
        return lastModifiedAt;
    }

    /**
     * Gets the user id, who last modified this entity.
     *
     * @return the user id, who last modified this entity
     */
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }
}

/*
 * Copyright (C) 2017 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.frontend.impl.acp.model;

import org.hibernate.envers.Audited;
import org.jbb.frontend.api.acp.AcpElement;
import org.jbb.lib.db.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

@Getter
@Setter
@Entity
@Audited
@Table(name = "JBB_FRONTEND_ACP_ELEMENTS")
@Builder
@EqualsAndHashCode(callSuper = true)
public class AcpElementEntity extends BaseEntity implements AcpElement {

    private String name;

    private Integer ordering;

    @Column(name = "view_name")
    private String viewName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subcategory_id")
    private AcpSubcategoryEntity subcategory;

    @Tolerate
    public AcpElementEntity() {
        // for JPA
    }
}

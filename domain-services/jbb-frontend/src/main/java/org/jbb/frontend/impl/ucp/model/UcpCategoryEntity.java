/*
 * Copyright (C) 2017 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.frontend.impl.ucp.model;

import com.google.common.collect.Lists;

import org.hibernate.envers.Audited;
import org.jbb.frontend.api.ucp.UcpCategory;
import org.jbb.lib.db.domain.BaseEntity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
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
@Table(name = "JBB_FRONTEND_UCP_CATEGORIES")
@Builder
@EqualsAndHashCode(callSuper = true)
public class UcpCategoryEntity extends BaseEntity implements UcpCategory {

    private String name;

    private Integer ordering;

    @Column(name = "view_name")
    private String viewName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.ALL)
    private List<UcpElementEntity> elements = Lists.newArrayList();

    @Tolerate
    UcpCategoryEntity() {
        // for JPA
    }
}

/*
 * Copyright (C) 2017 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.permissions.impl.acl.model;

import com.google.common.collect.Lists;

import org.hibernate.envers.Audited;
import org.jbb.lib.db.domain.BaseEntity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

@Getter
@Setter
@Entity
@Audited
@Table(name = "JBB_ACL_PERMISSION_CATEGORIES")
@Builder
@EqualsAndHashCode(callSuper = true)
public class AclPermissionCategoryEntity extends BaseEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private AclPermissionTypeEntity type;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.ALL)
    private List<AclPermissionEntity> permissions = Lists.newArrayList();

    @NotBlank
    private String name;

    @NotNull
    @Min(0)
    private Integer position;

    @Tolerate
    AclPermissionCategoryEntity() {
        // for JPA
    }

}

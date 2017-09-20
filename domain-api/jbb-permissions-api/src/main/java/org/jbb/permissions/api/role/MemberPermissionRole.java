/*
 * Copyright (C) 2017 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.permissions.api.role;

import org.jbb.permissions.api.permission.PermissionType;

public enum MemberPermissionRole implements PermissionRole {
    STANDARD_MEMBER("Standard member"),
    LIMITED_MEMBER("Limited member"),;

    private String name;

    MemberPermissionRole(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PermissionType getPermissionType() {
        return PermissionType.MEMBER_PERMISSIONS;
    }

    @Override
    public Integer getPosition() {
        return ordinal();
    }
}

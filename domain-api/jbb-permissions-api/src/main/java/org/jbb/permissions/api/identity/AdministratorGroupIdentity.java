/*
 * Copyright (C) 2017 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.permissions.api.identity;

public class AdministratorGroupIdentity implements SecurityIdentity {

    public static AdministratorGroupIdentity getInstance() {
        return new AdministratorGroupIdentity();
    }

    @Override
    public Long getId() {
        return 0L;
    }

    @Override
    public Type getType() {
        return Type.ADMIN_GROUP;
    }
}

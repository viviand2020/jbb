/*
 * Copyright (C) 2018 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.permissions.web.role.form;

import org.jbb.permissions.api.permission.PermissionValue;
import org.jbb.permissions.web.role.model.RoleDefinition;

import java.util.Map;

import javax.validation.Valid;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDetailsForm {

    @Valid
    private RoleDefinition definition;

    private Map<String, PermissionValue> valueMap;

}

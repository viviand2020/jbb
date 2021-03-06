/*
 * Copyright (C) 2018 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.permissions.web.effective.controller;

import org.jbb.permissions.api.PermissionService;
import org.jbb.permissions.api.permission.PermissionType;
import org.jbb.permissions.web.base.logic.SecurityIdentityMapper;
import org.jbb.permissions.web.effective.logic.EffectivePermissionTableMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/acp/permissions/effective-administrators")
public class AcpEffectiveAdministratorPermissionsController extends AbstractAcpEffectivePermissionsController {

    private static final String VIEW_NAME = "acp/permissions/effective-administrators";

    public AcpEffectiveAdministratorPermissionsController(PermissionService permissionService,
                                                          SecurityIdentityMapper securityIdentityMapper,
                                                          EffectivePermissionTableMapper tableMapper) {
        super(permissionService, securityIdentityMapper, tableMapper);
    }

    @Override
    public String getViewName() {
        return VIEW_NAME;
    }

    @Override
    public String getPermissionTypeUrlSuffix() {
        return "effective-administrators";
    }

    @Override
    public String getViewDescription() {
        return "Effective administrator permissions";
    }

    @Override
    public PermissionType getPermissionType() {
        return PermissionType.ADMINISTRATOR_PERMISSIONS;
    }

}

/*
 * Copyright (C) 2018 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.permissions.web.effective;

import org.jbb.permissions.api.PermissionService;
import org.jbb.permissions.api.effective.EffectivePermissionTable;
import org.jbb.permissions.api.identity.SecurityIdentity;
import org.jbb.permissions.api.permission.PermissionType;
import org.jbb.permissions.web.base.EffectivePermissionTableMapper;
import org.jbb.permissions.web.base.SecurityIdentityMapper;
import org.jbb.permissions.web.base.controller.AbstractAcpSecurityIdentityChooseController;
import org.jbb.permissions.web.base.form.SecurityIdentityChooseForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/acp/permissions/effective-administrators")
public class AcpEffectiveAdministratorPermissionsController extends AbstractAcpSecurityIdentityChooseController {

    private static final String VIEW_NAME = "acp/permissions/effective-administrators";

    private final PermissionService permissionService;
    private final SecurityIdentityMapper securityIdentityMapper;
    private final EffectivePermissionTableMapper tableMapper;

    @Override
    public String getPermissionTypeUrlSuffix() {
        return "effective-administrators";
    }

    @Override
    public String getViewName() {
        return "Effective administrator permissions";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String showAdministratorPermissions(Model model,
                                               @ModelAttribute(SECURITY_IDENTITY_FORM) SecurityIdentityChooseForm form,
                                               BindingResult bindingResult,
                                               RedirectAttributes redirectAttributes) {
        Optional<SecurityIdentity> securityIdentity = securityIdentityMapper.toModel(form);
        // todo: not found member case
        EffectivePermissionTable effectivePermissionTable = permissionService.getEffectivePermissionTable(
                PermissionType.ADMINISTRATOR_PERMISSIONS, securityIdentity.get());
        model.addAttribute("effectivePermissions", tableMapper.toDto(effectivePermissionTable));
        return VIEW_NAME;
    }

}

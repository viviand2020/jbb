/*
 * Copyright (C) 2017 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.security.impl.lockout;

import org.jbb.security.api.lockout.MemberLockoutException;
import org.jbb.security.api.lockout.MemberLockoutSettings;
import org.springframework.stereotype.Component;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberLockoutSettingsValidator {
    private final Validator validator;

    public void validate(MemberLockoutSettings settings) {
        Set<ConstraintViolation<MemberLockoutSettings>> validationResult = validator
                .validate(settings);

        if (!validationResult.isEmpty()) {
            throw new MemberLockoutException(validationResult);
        }
    }

}

/*
 * Copyright (C) 2017 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.members.impl.registration;

import org.jbb.members.api.registration.RegistrationRequest;
import org.jbb.members.impl.registration.data.PasswordPair;
import org.jbb.security.api.password.PasswordException;
import org.jbb.security.api.password.PasswordService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PasswordSaver {
    private final PasswordService passwordService;
    private final Validator validator;

    @Transactional
    public void save(RegistrationRequest regRequest, Long memberId) {
        Set<ConstraintViolation<PasswordPair>> validationResult = validator.validate(
                new PasswordPair(regRequest.getPassword(), regRequest.getPasswordAgain()));
        if (!validationResult.isEmpty()) {
            throw new PasswordException(validationResult);
        }

        passwordService.changeFor(memberId, regRequest.getPassword());
    }

}

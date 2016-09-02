/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.security.entities.validation;

import org.jbb.security.services.PasswordRequirementsPolicy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.validation.ConstraintValidatorContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.eq;

@RunWith(MockitoJUnitRunner.class)
public class PasswordRequirementsSatisfiedValidatorTest {
    private static final ConstraintValidatorContext ANY_CONTEXT = null;

    @Mock
    private PasswordRequirementsPolicy requirementsPolicyMock;

    @InjectMocks
    private PasswordRequirementsSatisfiedValidator validator;

    @Test
    public void shouldValid_whenPasswordMeetsCriteria() throws Exception {
        // given
        String pass = "AiSD";
        given(requirementsPolicyMock.assertMeetCriteria(eq(pass))).willReturn(true);

        // when
        boolean passwordValid = validator.isValid(pass, ANY_CONTEXT);

        // then
        assertThat(passwordValid).isTrue();
    }

    @Test
    public void shouldNotValid_whenPasswordDoNotMeetCriteria() throws Exception {
        // given
        String pass = "AiSD";
        given(requirementsPolicyMock.assertMeetCriteria(eq(pass))).willReturn(false);

        // when
        boolean passwordValid = validator.isValid(pass, ANY_CONTEXT);

        // then
        assertThat(passwordValid).isFalse();
    }
}
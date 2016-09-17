/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.members.impl.base.model.validation;

import org.jbb.lib.core.security.UserDetailsSource;
import org.jbb.lib.core.vo.Username;
import org.jbb.members.api.data.DisplayedName;
import org.jbb.members.api.data.Member;
import org.jbb.members.api.service.MemberService;
import org.jbb.members.impl.base.dao.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import javax.validation.ConstraintValidatorContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DisplayedNameNotBusyValidatorTest {
    private static final ConstraintValidatorContext ANY_CONTEXT = null;

    @Mock
    private MemberRepository memberRepositoryMock;

    @Mock
    private UserDetailsSource userDetailsSourceMock;

    @Mock
    private MemberService memberServiceMock;

    @Mock
    private UserDetails userDetailsMock;

    @Mock
    private DisplayedName displayedName;

    @InjectMocks
    private DisplayedNameNotBusyValidator validator;

    @Test
    public void shouldPass_whenNoGivenDisplayedName() throws Exception {
        // given
        when(memberRepositoryMock.countByDisplayedName(any(DisplayedName.class))).thenReturn(0L);

        // when
        boolean validationResult = validator.isValid(displayedName, ANY_CONTEXT);

        // then
        assertThat(validationResult).isTrue();
    }

    @Test
    public void shouldPass_whenSingleDisplayedNameExists_butItIsDisplayedNameOfCurrentUser() throws Exception {
        // given
        when(memberRepositoryMock.countByDisplayedName(any(DisplayedName.class))).thenReturn(1L);
        when(userDetailsSourceMock.getFromApplicationContext()).thenReturn(userDetailsMock);
        when(userDetailsMock.getUsername()).thenReturn("foo");

        Member memberMock = mock(Member.class);
        when(memberServiceMock.getMemberWithUsername(any(Username.class))).thenReturn(Optional.of(memberMock));
        when(memberMock.getDisplayedName()).thenReturn(displayedName);

        // when
        boolean validationResult = validator.isValid(displayedName, ANY_CONTEXT);

        // then
        assertThat(validationResult).isTrue();
    }

    @Test
    public void shouldFail_whenSingleDisplayedNameExists_butItIsNotDisplayedNameOfCurrentUser() throws Exception {
        // given
        when(memberRepositoryMock.countByDisplayedName(any(DisplayedName.class))).thenReturn(1L);
        when(userDetailsSourceMock.getFromApplicationContext()).thenReturn(userDetailsMock);
        when(userDetailsMock.getUsername()).thenReturn("bar");

        Member memberMock = mock(Member.class);
        when(memberServiceMock.getMemberWithUsername(any(Username.class))).thenReturn(Optional.of(memberMock));
        when(memberMock.getDisplayedName()).thenReturn(DisplayedName.builder().value("bar").build());

        // when
        boolean validationResult = validator.isValid(displayedName, ANY_CONTEXT);

        // then
        assertThat(validationResult).isFalse();
    }

    @Test
    public void shouldFail_whenSingleDisplayedNameExists_butUserIsNotAuthenticated() throws Exception {
        // given
        when(memberRepositoryMock.countByDisplayedName(any(DisplayedName.class))).thenReturn(1L);
        when(userDetailsSourceMock.getFromApplicationContext()).thenReturn(null);

        // when
        boolean validationResult = validator.isValid(displayedName, ANY_CONTEXT);

        // then
        assertThat(validationResult).isFalse();
    }

    @Test
    public void shouldFail_whenDisplayedNameExists() throws Exception {
        // given
        when(memberRepositoryMock.countByDisplayedName(any(DisplayedName.class))).thenReturn(1L);

        // when
        boolean validationResult = validator.isValid(displayedName, ANY_CONTEXT);

        // then
        assertThat(validationResult).isFalse();
    }

}
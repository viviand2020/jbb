/*
 * Copyright (C) 2017 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.system.impl.stacktrace.format;

import com.google.common.collect.Lists;

import org.jbb.system.api.stacktrace.StackTraceVisibilityLevel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@RunWith(MockitoJUnitRunner.class)
public class OnlyAdministratorsCanSeeStackTraceStrategyTest {
    private static final String ADMINISTRATOR_ROLE_NAME = "ROLE_ADMINISTRATOR";

    @Mock
    private UserDetails userDetailsMock;

    @InjectMocks
    private OnlyAdministratorsCanSeeStackTraceStrategy strategy;

    @Test
    public void shouldHandle_whenVisibilityValueIsEqualToAdministrators_andUserDetailsHasAdministratorAuthority() throws Exception {
        // given
        StackTraceVisibilityLevel visibility = StackTraceVisibilityLevel.ADMINISTRATORS;

        Collection<GrantedAuthority> authorities = Lists.newArrayList(new SimpleGrantedAuthority(ADMINISTRATOR_ROLE_NAME));
        BDDMockito.given(userDetailsMock.getAuthorities()).willAnswer(invocationOnMock -> authorities);

        // when
        boolean canHandle = strategy.canHandle(visibility, userDetailsMock);

        // then
        assertThat(canHandle).isTrue();
    }

    @Test
    public void shouldNotHandle_whenVisibilityValueIsEqualToAdministrators_andUserDetailsAreNotPresent() throws Exception {
        // given
        StackTraceVisibilityLevel visibility = StackTraceVisibilityLevel.ADMINISTRATORS;

        // when
        boolean canHandle = strategy.canHandle(visibility, null);

        // then
        assertThat(canHandle).isFalse();
    }

    @Test
    public void shouldNotHandle_whenVisibilityValueIsEqualToAdministrators_andUserDetailsHasNotAdministratorAuthority() throws Exception {
        // given
        StackTraceVisibilityLevel visibility = StackTraceVisibilityLevel.ADMINISTRATORS;

        Collection<GrantedAuthority> authorities = Lists.newArrayList(new SimpleGrantedAuthority("ANOTHER ROLE"));
        BDDMockito.given(userDetailsMock.getAuthorities()).willAnswer(invocationOnMock -> authorities);

        // when
        boolean canHandle = strategy.canHandle(visibility, userDetailsMock);

        // then
        assertThat(canHandle).isFalse();
    }

    @Test
    public void shouldNotHandle_whenVisibilityValueIsNotEqualToAdministrators() throws Exception {
        // given
        List<StackTraceVisibilityLevel> visibilities = Lists.newArrayList(Arrays.asList(StackTraceVisibilityLevel.values()));
        visibilities.remove(StackTraceVisibilityLevel.ADMINISTRATORS);

        Collection<GrantedAuthority> authorities = Lists.newArrayList(new SimpleGrantedAuthority(ADMINISTRATOR_ROLE_NAME));

        // when
        for (StackTraceVisibilityLevel visibility : visibilities) {
            if (strategy.canHandle(visibility, userDetailsMock)) {
                // then
                fail("Should not be handled with visibility: " + visibility);
            }
        }
    }

    @Test
    public void shouldReturnStackTrace() throws Exception {
        // given
        Exception ex = new Exception("Example");

        // when
        Optional<String> stackTraceString = strategy.getStackTraceAsString(ex);

        // then
        assertThat(stackTraceString).isPresent();
    }
}

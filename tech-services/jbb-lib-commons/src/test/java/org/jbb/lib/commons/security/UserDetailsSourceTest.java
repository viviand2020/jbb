/*
 * Copyright (C) 2017 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.lib.commons.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityContextHolder.class)
@PowerMockIgnore("javax.security.*")
public class UserDetailsSourceTest {

    @Mock
    private SecurityContext securityContextMock;

    @InjectMocks
    private UserDetailsSource userDetailsSource;

    @Test
    public void shouldReturnNullUserDetails_whenNoAuthentication() throws Exception {
        // given
        mockStatic(SecurityContextHolder.class);
        given(SecurityContextHolder.getContext()).willReturn(securityContextMock);

        given(securityContextMock.getAuthentication()).willReturn(null);

        // when
        SecurityContentUser userDetails = userDetailsSource.getFromApplicationContext();

        // then
        assertThat(userDetails).isNull();
    }

    @Test
    public void shouldReturnNotNullUserDetails_whenAuthenticationPresent() throws Exception {
        // given
        mockStatic(SecurityContextHolder.class);
        given(SecurityContextHolder.getContext()).willReturn(securityContextMock);

        Authentication authMock = mock(Authentication.class);
        given(authMock.getPrincipal()).willReturn(mock(SecurityContentUser.class));

        given(securityContextMock.getAuthentication()).willReturn(authMock);

        // when
        SecurityContentUser userDetails = userDetailsSource.getFromApplicationContext();

        // then
        assertThat(userDetails).isNotNull();
    }
}
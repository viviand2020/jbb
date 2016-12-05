/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.security.web.controller;

import org.jbb.lib.eventbus.JbbEventBus;
import org.jbb.lib.mvc.properties.MvcProperties;
import org.jbb.security.api.service.UserLockService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.text.SimpleDateFormat;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Configuration
public class SecurityConfigMock {
    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        UserDetailsService userDetailsServiceMock = Mockito.mock(UserDetailsService.class);
        given(userDetailsServiceMock.loadUserByUsername(any())).willReturn(mock(UserDetails.class));
        return userDetailsServiceMock;
    }

    @Bean
    @Primary
    public UserLockService userLockService() {
        return Mockito.mock(UserLockService.class);
    }

    @Bean
    @Primary
    public AuthenticationProvider authenticationProvider() {
        return Mockito.mock(AuthenticationProvider.class);
    }

    @Bean
    @Primary
    public JbbEventBus jbbEventBus() {
        return Mockito.mock(JbbEventBus.class);
    }

    @Bean
    @Primary
    public MvcProperties mvcProperties() {
        MvcProperties prop = Mockito.mock(MvcProperties.class);
        when(prop.localDateTimeFormatPattern()).thenReturn(new SimpleDateFormat().toLocalizedPattern());
        return prop;
    }
}

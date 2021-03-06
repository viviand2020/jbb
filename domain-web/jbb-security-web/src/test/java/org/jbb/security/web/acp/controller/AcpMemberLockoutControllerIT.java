/*
 * Copyright (C) 2017 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.security.web.acp.controller;

import com.google.common.collect.Lists;

import org.jbb.security.api.lockout.MemberLockoutService;
import org.jbb.security.api.lockout.MemberLockoutSettings;
import org.jbb.security.web.BaseIT;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collection;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class AcpMemberLockoutControllerIT extends BaseIT {
    @Autowired
    WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private MemberLockoutService memberLockoutServiceMock;

    @Autowired
    private UserDetailsService userDetailsServiceMock;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMINISTRATOR"})
    public void shouldUseSigninView_whenSigninUrlInvoked() throws Exception {
        // given
        given(memberLockoutServiceMock.getLockoutSettings()).willReturn(mock(MemberLockoutSettings.class));
        UserDetails userDetails = mock(UserDetails.class);
        Collection<? extends GrantedAuthority> administrator = Lists.newArrayList(new SimpleGrantedAuthority("ROLE_ADMINISTRATOR"));

        Mockito.doReturn(administrator).when(userDetails).getAuthorities();
        Mockito.doReturn(userDetails).when(userDetailsServiceMock).loadUserByUsername(any());

        // when
        ResultActions result = mockMvc.perform(get("/acp/general/lockout"));

        // then
        result.andExpect(status().isOk())
                .andExpect(view().name("acp/general/lockout"));
    }

}
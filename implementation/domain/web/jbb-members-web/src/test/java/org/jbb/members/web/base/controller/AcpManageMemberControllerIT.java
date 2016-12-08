/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.members.web.base.controller;

import com.google.common.collect.Lists;

import org.jbb.lib.core.vo.Email;
import org.jbb.lib.core.vo.Username;
import org.jbb.lib.mvc.MvcConfig;
import org.jbb.lib.properties.PropertiesConfig;
import org.jbb.lib.test.CoreConfigMocks;
import org.jbb.lib.test.SpringSecurityConfigMocks;
import org.jbb.members.api.data.DisplayedName;
import org.jbb.members.api.data.MemberRegistrationAware;
import org.jbb.members.api.data.MemberSearchCriteria;
import org.jbb.members.api.data.RegistrationMetaData;
import org.jbb.members.api.exception.MemberSearchJoinDateFormatException;
import org.jbb.members.api.service.MemberService;
import org.jbb.members.web.MembersConfigMock;
import org.jbb.members.web.MembersWebConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {MvcConfig.class, MembersWebConfig.class, PropertiesConfig.class,
        MembersConfigMock.class, CoreConfigMocks.class, SpringSecurityConfigMocks.class})
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class,
        WithSecurityContextTestExecutionListener.class})
public class AcpManageMemberControllerIT {
    @Autowired
    WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private MemberService memberServiceMock;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }

    @Test
    public void shouldSetView_whenGET() throws Exception {
        // when
        ResultActions result = mockMvc.perform(get("/acp/members/manage"));

        // then
        result.andExpect(status().isOk())
                .andExpect(view().name("acp/members/manage"));
    }

    @Test
    public void shouldSetFlag_whenCorrectForm_whenPOST() throws Exception {
        // given
        memberMockPrepare();

        // when
        ResultActions result = mockMvc.perform(post("/acp/members/manage"));

        // then
        result.andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/acp/members/manage"))
                .andExpect(flash().attribute("memberSearchFormSent", true));
    }

    @Test
    public void shouldNotSetFlag_whenMemberSearchJoinDateFormatException_whenPOST() throws Exception {
        // given
        given(memberServiceMock.getAllMembersWithCriteria(any())).willThrow(MemberSearchJoinDateFormatException.class);

        // when
        ResultActions result = mockMvc.perform(post("/acp/members/manage"));

        // then
        result.andExpect(status().isOk())
                .andExpect(view().name("acp/members/manage"))
                .andExpect(model().attribute("memberSearchFormSent", false));
    }

    private void memberMockPrepare() {
        MemberRegistrationAware memberMock = mock(MemberRegistrationAware.class);
        given(memberServiceMock.getAllMembersWithCriteria(any(MemberSearchCriteria.class)))
                .willReturn(Lists.newArrayList(memberMock));

        Username username = Username.builder().value("john").build();
        DisplayedName displayedName = DisplayedName.builder().value("John").build();
        Email email = Email.builder().value("john@john.com").build();
        Long memberId = 100L;

        given(memberMock.getUsername()).willReturn(username);
        given(memberMock.getDisplayedName()).willReturn(displayedName);
        given(memberMock.getEmail()).willReturn(email);
        given(memberMock.getId()).willReturn(memberId);

        given(memberMock.getRegistrationMetaData()).willReturn(mock(RegistrationMetaData.class));

    }

}
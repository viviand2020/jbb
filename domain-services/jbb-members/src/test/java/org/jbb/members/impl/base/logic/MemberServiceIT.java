/*
 * Copyright (C) 2017 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.members.impl.base.logic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.jbb.lib.commons.CommonsConfig;
import org.jbb.lib.commons.vo.Email;
import org.jbb.lib.commons.vo.IPAddress;
import org.jbb.lib.commons.vo.Password;
import org.jbb.lib.commons.vo.Username;
import org.jbb.lib.db.DbConfig;
import org.jbb.lib.eventbus.EventBusConfig;
import org.jbb.lib.properties.PropertiesConfig;
import org.jbb.lib.test.MockCommonsConfig;
import org.jbb.lib.test.MockSpringSecurityConfig;
import org.jbb.members.api.base.AccountDataToChange;
import org.jbb.members.api.base.AccountException;
import org.jbb.members.api.base.DisplayedName;
import org.jbb.members.api.base.MemberService;
import org.jbb.members.api.base.ProfileDataToChange;
import org.jbb.members.api.base.ProfileException;
import org.jbb.members.api.registration.MemberRegistrationAware;
import org.jbb.members.impl.MembersConfig;
import org.jbb.members.impl.SecurityConfigMocks;
import org.jbb.members.impl.base.dao.MemberRepository;
import org.jbb.members.impl.base.model.MemberEntity;
import org.jbb.members.impl.registration.model.RegistrationMetaDataEntity;
import org.jbb.security.api.password.PasswordService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {CommonsConfig.class, MockCommonsConfig.class,
    SecurityConfigMocks.class,
        MembersConfig.class, PropertiesConfig.class,
        EventBusConfig.class, DbConfig.class, MockSpringSecurityConfig.class})
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class,
        WithSecurityContextTestExecutionListener.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MemberServiceIT {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository repository;

    @Autowired
    private PasswordService passwordServiceMock;

    @Test
    public void shouldReturnMembersSortedByRegistrationDate_whenMembersHadSavedNotAscendingByRegistrationDate() throws Exception {
        // given
        // note that we're saving members not ascending by join date
        repository.save(memberJoinedOneMinuteAgo());
        repository.save(memberJoinedForTwoWeeks());
        repository.save(memberJoinedFiveMonthsAgo());

        // when
        List<MemberRegistrationAware> members = memberService.getAllMembersSortedByRegistrationDate();
        Iterator<MemberRegistrationAware> membersIterator = members.iterator();

        // then
        assertThat(membersIterator.next().getDisplayedName().toString()).isEqualTo("John");
        assertThat(membersIterator.next().getDisplayedName().toString()).isEqualTo("Mark");
        assertThat(membersIterator.next().getDisplayedName().toString()).isEqualTo("Tom");
        assertThat(membersIterator.hasNext()).isFalse();
    }

    @Test
    public void shouldRemoveMember_afterSavingAndRemoveMethodInvoking() throws Exception {
        // given
        MemberEntity memberEntity = repository.save(memberJoinedForTwoWeeks());

        // when
        memberService.removeMember(memberEntity.getId());

        // then
        assertThat(repository.count()).isZero();
    }

    @Test
    public void shouldReturnEmptyList_whenThereIsNoMemberRegistered() throws Exception {
        // when
        List<MemberRegistrationAware> members = memberService.getAllMembersSortedByRegistrationDate();

        // then
        assertThat(members).isEmpty();
    }

    @Test
    @WithMockUser(username = "jack", roles = {})
    public void shouldUpdateDisplayedName_whenUpdateProfileInvoked() throws Exception {
        // given
        MemberEntity memberEntity = repository.save(exampleMember());
        assertThat(memberEntity.getDisplayedName()).isEqualTo(DisplayedName.builder().value("Jack").build());

        Username jackUsername = Username.builder().value("jack").build();

        ProfileDataToChange profileDataToChange = mock(ProfileDataToChange.class);
        DisplayedName newDisplayedName = DisplayedName.builder().value("Jack2000").build();
        given(profileDataToChange.getDisplayedName()).willReturn(Optional.of(newDisplayedName));

        // when
        memberService.updateProfile(memberEntity.getId(), profileDataToChange);

        // then
        MemberEntity jackMember = repository.findOne(memberEntity.getId());
        assertThat(jackMember.getDisplayedName()).isEqualTo(newDisplayedName);
    }

    @Test(expected = ProfileException.class)
    @WithMockUser(username = "jack", roles = {})
    public void shouldThrowProfileException_whenSomethingIsWrongWithDisplayedName() throws Exception {
        // given
        MemberEntity memberEntity = repository.save(exampleMember());
        assertThat(memberEntity.getDisplayedName()).isEqualTo(DisplayedName.builder().value("Jack").build());

        ProfileDataToChange profileDataToChange = mock(ProfileDataToChange.class);
        DisplayedName newDisplayedName = DisplayedName.builder().value("J").build();
        given(profileDataToChange.getDisplayedName()).willReturn(Optional.of(newDisplayedName));

        // when
        memberService.updateProfile(memberEntity.getId(), profileDataToChange);

        // then
        // throw ProfileException
    }

    @Test
    @WithMockUser(username = "jack", roles = {})
    public void shouldUpdateEmail_whenUpdateAccountInvoked() throws Exception {
        // given
        MemberEntity memberEntity = repository.save(exampleMember());
        assertThat(memberEntity.getDisplayedName()).isEqualTo(DisplayedName.builder().value("Jack").build());

        AccountDataToChange accountDataToChange = mock(AccountDataToChange.class);
        Email newEmail = Email.builder().value("new@email.com").build();
        given(accountDataToChange.getEmail()).willReturn(Optional.of(newEmail));
        given(accountDataToChange.getNewPassword()).willReturn(Optional.empty());

        // when
        memberService.updateAccount(memberEntity.getId(), accountDataToChange);

        // then
        MemberEntity jackMember = repository.findOne(memberEntity.getId());
        assertThat(jackMember.getEmail()).isEqualTo(newEmail);
    }

    @Test(expected = AccountException.class)
    @WithMockUser(username = "jack", roles = {})
    public void shouldThrowAccountException_whenSomethingIsWrongWithEmail() throws Exception {
        // given
        MemberEntity memberEntity = repository.save(exampleMember());
        assertThat(memberEntity.getDisplayedName()).isEqualTo(DisplayedName.builder().value("Jack").build());

        AccountDataToChange accountDataToChange = mock(AccountDataToChange.class);
        Email newEmail = Email.builder().value("new(AT)email.com").build();
        given(accountDataToChange.getEmail()).willReturn(Optional.of(newEmail));
        given(accountDataToChange.getNewPassword()).willReturn(Optional.empty());

        // when
        memberService.updateAccount(memberEntity.getId(), accountDataToChange);

        // then
        // throw AccountException
    }

    @Test
    @WithMockUser(username = "jack", roles = {})
    public void shouldInvokePasswordService_whenUpdateAccountInvoked() throws Exception {
        // given
        MemberEntity memberEntity = repository.save(exampleMember());
        assertThat(memberEntity.getDisplayedName()).isEqualTo(DisplayedName.builder().value("Jack").build());

        AccountDataToChange accountDataToChange = mock(AccountDataToChange.class);
        Password newPassword = Password.builder().value("newPass".toCharArray()).build();
        given(accountDataToChange.getEmail()).willReturn(Optional.empty());
        given(accountDataToChange.getNewPassword()).willReturn(Optional.of(newPassword));

        // when
        memberService.updateAccount(memberEntity.getId(), accountDataToChange);

        // then
        verify(passwordServiceMock, times(1)).changeFor(eq(memberEntity.getId()), eq(newPassword));
    }

    private MemberEntity memberJoinedFiveMonthsAgo() {
        return MemberEntity.builder()
                .username(Username.builder().value("john").build())
                .displayedName(DisplayedName.builder().value("John").build())
                .email(Email.builder().value("john@john.com").build())
                .registrationMetaData(RegistrationMetaDataEntity.builder()
                        .joinDateTime(LocalDateTime.now().minusMonths(5))
                        .ipAddress(IPAddress.builder().value("127.0.0.1").build()
                        ).build())
                .build();
    }

    private MemberEntity memberJoinedOneMinuteAgo() {
        return MemberEntity.builder()
                .username(Username.builder().value("mark").build())
                .displayedName(DisplayedName.builder().value("Mark").build())
                .email(Email.builder().value("mark@mark.com").build())
                .registrationMetaData(RegistrationMetaDataEntity.builder()
                        .joinDateTime(LocalDateTime.now().minusMinutes(1))
                        .ipAddress(IPAddress.builder().value("127.0.0.1").build()
                        ).build())
                .build();
    }

    private MemberEntity memberJoinedForTwoWeeks() {
        return MemberEntity.builder()
                .username(Username.builder().value("tom").build())
                .displayedName(DisplayedName.builder().value("Tom").build())
                .email(Email.builder().value("tom@tom.com").build())
                .registrationMetaData(RegistrationMetaDataEntity.builder()
                        .joinDateTime(LocalDateTime.now().plusWeeks(2))
                        .ipAddress(IPAddress.builder().value("127.0.0.1").build()
                        ).build())
                .build();
    }

    private MemberEntity exampleMember() {
        return MemberEntity.builder()
                .username(Username.builder().value("jack").build())
                .displayedName(DisplayedName.builder().value("Jack").build())
                .email(Email.builder().value("jack@jack.com").build())
                .registrationMetaData(RegistrationMetaDataEntity.builder()
                        .joinDateTime(LocalDateTime.now())
                        .ipAddress(IPAddress.builder().value("127.0.0.1").build()
                        ).build())
                .build();
    }


    @After
    public void tearDown() throws Exception {
        repository.deleteAll();
    }
}
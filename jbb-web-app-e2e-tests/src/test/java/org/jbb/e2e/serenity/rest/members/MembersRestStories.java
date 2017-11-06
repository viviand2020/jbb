/*
 * Copyright (C) 2017 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.e2e.serenity.rest.members;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jbb.e2e.serenity.Tags.Feature;
import static org.jbb.e2e.serenity.Tags.Release;
import static org.jbb.e2e.serenity.Tags.Type;

import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.WithTagValuesOf;
import org.jbb.e2e.serenity.Tags.Interface;
import org.jbb.e2e.serenity.rest.EndToEndRestStories;
import org.jbb.e2e.serenity.rest.commons.AuthRestSteps;
import org.jbb.e2e.serenity.rest.commons.PageDto;
import org.junit.Test;
import org.springframework.http.HttpStatus;

public class MembersRestStories extends EndToEndRestStories {

    @Steps
    AuthRestSteps authRestSteps;

    @Steps
    MemberResourceSteps memberResourceSteps;

    @Test
    @WithTagValuesOf({Interface.REST, Type.SMOKE, Feature.REGISTRATION, Release.VER_0_10_0})
    public void should_administrator_account_be_visible_in_api_after_installation()
        throws Exception {
        PageDto<MemberPublicDto> pageResult = memberResourceSteps
            .getWithDisplayedName("Administrator");
        assertThat(pageResult.getContent()).isNotEmpty();
    }

    @Test
    @WithTagValuesOf({Interface.REST, Type.SMOKE, Feature.REGISTRATION, Release.VER_0_10_0})
    public void invalid_page() throws Exception {
        Response response = memberResourceSteps.getMemberPage("aaa");
        memberResourceSteps.assertBadRequestError(response);
        memberResourceSteps.assertErrorDto(response);
    }

    @Test
    @WithTagValuesOf({Interface.REST, Type.SMOKE, Feature.REGISTRATION, Release.VER_0_10_0})
    public void create_member() throws Exception {
        RegistrationRequestDto registrationRequest = RegistrationRequestDto.builder()
            .username("testrest2")
            .displayedName("Test Rest2")
            .password("testrest")
            .email("test2@rest.com")
            .build();
        Response registrationResponse = memberResourceSteps.postMember(registrationRequest);
        MemberPublicDto memberPublicDto = registrationResponse.as(MemberPublicDto.class);
        authRestSteps.includeBasicAuthHeaderToEveryRequest("administrator", "administrator");
        Response deleteResponse = memberResourceSteps
            .deleteMember(memberPublicDto.getId().toString());
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }


}

/*
 * Copyright (C) 2017 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.e2e.serenity.rest.account;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.steps.ScenarioSteps;

import org.jbb.e2e.serenity.rest.RestUtils;
import org.jbb.e2e.serenity.rest.commons.AssertRestSteps;
import org.jbb.e2e.serenity.rest.commons.ErrorDetailDto;

import io.restassured.response.Response;

import static net.serenitybdd.rest.SerenityRest.then;
import static org.assertj.core.api.Assertions.assertThat;

public class MemberAccountResourceSteps extends ScenarioSteps {

    public static final String V1_MEMBERS_ACCOUNT = "api/v1/members/{memberId}/account";
    public static final String MEMBER_ID = "memberId";

    @Steps
    AssertRestSteps assertRestSteps;

    @Step
    public Response get_member_account(String memberId) {
        return RestUtils.prepareApiRequest()
                .basePath(V1_MEMBERS_ACCOUNT)
                .pathParam(MEMBER_ID, memberId)
                .when()
                .get()
                .andReturn();
    }

    @Step
    public Response put_member_account(String memberId, UpdateAccountDto updateAccountDto) {
        return RestUtils.prepareApiRequest()
                .basePath(V1_MEMBERS_ACCOUNT)
                .pathParam(MEMBER_ID, memberId)
                .when()
                .body(updateAccountDto)
                .put()
                .andReturn();
    }

    @Step
    public void account_should_contains_email(String email) {
        AccountDto accountDto = then().extract().response().as(AccountDto.class);
        assertThat(accountDto.getEmail()).isEqualTo(email);
    }

    @Step
    public void should_contain_error_detail_about_empty_email() {
        assertRestSteps.assert_response_error_detail_exists(
                ErrorDetailDto.builder()
                        .name("email")
                        .message("must not be empty").build()
        );
    }

    @Step
    public void should_contain_error_detail_about_invalid_email() {
        assertRestSteps.assert_response_error_detail_exists(
                ErrorDetailDto.builder()
                        .name("email")
                        .message("must be a well-formed email address").build()
        );
    }

    @Step
    public void should_contain_error_detail_about_busy_email() {
        assertRestSteps.assert_response_error_detail_exists(
                ErrorDetailDto.builder()
                        .name("email")
                        .message("This e-mail is already used by another member").build()
        );
    }

    @Step
    public void should_contain_error_detail_about_invalid_password_length() {
        assertRestSteps.assert_response_error_detail_exists(
                ErrorDetailDto.builder()
                        .name("password")
                        .message("Password has incorrect length (min: 4, max: 16)").build()
        );
    }
}

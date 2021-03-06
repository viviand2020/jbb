/*
 * Copyright (C) 2017 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.e2e.serenity.web.membermanagement;

import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.WithTagValuesOf;

import org.jbb.e2e.serenity.web.EndToEndWebStories;
import org.jbb.e2e.serenity.web.memberbrowser.MemberBrowserSteps;
import org.jbb.e2e.serenity.web.registration.RegistrationSteps;
import org.jbb.e2e.serenity.web.signin.SignInSteps;
import org.junit.Test;

import static org.jbb.e2e.serenity.Tags.Feature;
import static org.jbb.e2e.serenity.Tags.Interface;
import static org.jbb.e2e.serenity.Tags.Release;
import static org.jbb.e2e.serenity.Tags.Type;

public class MemberDeletingStories extends EndToEndWebStories {

    @Steps
    RegistrationSteps registrationSteps;
    @Steps
    MemberBrowserSteps memberBrowserSteps;
    @Steps
    SignInSteps signInSteps;
    @Steps
    AcpMemberBrowserSteps acpMemberBrowserSteps;

    @Test
    @WithTagValuesOf({Interface.WEB, Type.SMOKE, Feature.REGISTRATION, Release.VER_0_7_0})
    public void remove_member_by_administrator() throws Exception {
        registrationSteps.register_new_member("toDelete", "ToDelete",
                "to@delete.com", "toDelete", "toDelete");
        memberBrowserSteps.open_members_browser_page();
        memberBrowserSteps.should_see_member_name("ToDelete");
        signInSteps.sign_in_as_administrator_with_success();
        acpMemberBrowserSteps.open_acp_member_browser_page();
        acpMemberBrowserSteps.type_username_to_search("toDelete");
        acpMemberBrowserSteps.send_member_search_form();
        acpMemberBrowserSteps.select_first_result();
        acpMemberBrowserSteps.click_delete_member_button();
        acpMemberBrowserSteps.open_acp_member_browser_page();
        acpMemberBrowserSteps.type_username_to_search("toDelete");
        acpMemberBrowserSteps.send_member_search_form();
        acpMemberBrowserSteps.should_not_found_any_results();
        memberBrowserSteps.open_members_browser_page();
        memberBrowserSteps.should_not_see_member_name("ToDelete");

    }
}

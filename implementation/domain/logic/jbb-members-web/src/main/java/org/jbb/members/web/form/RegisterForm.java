/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.members.web.form;


import org.jbb.lib.core.vo.Email;
import org.jbb.lib.core.vo.IPAddress;
import org.jbb.members.api.model.DisplayedName;
import org.jbb.members.api.model.Login;
import org.jbb.members.api.model.RegistrationDetails;

public class RegisterForm {
    private String login;
    private String displayedName;
    private String email;

    public RegisterForm() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getDisplayedName() {
        return displayedName;
    }

    public void setDisplayedName(String displayedName) {
        this.displayedName = displayedName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RegistrationDetails registrationDetails() {
        return new RegisterDet();
    }

    private class RegisterDet implements RegistrationDetails {

        @Override
        public Login getLogin() {
            return Login.builder().value(login).build();
        }

        @Override
        public DisplayedName getDisplayedName() {
            return DisplayedName.builder().value(displayedName).build();
        }

        @Override
        public Email getEmail() {
            return Email.builder().value(email).build();
        }

        @Override
        public IPAddress getIPAddress() {
            return null;
        }
    }
}

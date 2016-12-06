/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.qa.pages;

import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.DefaultUrl;

@DefaultUrl(AcpPage.URL)

public class AcpPage extends PageObject {
    public static final String URL = "/acp";

    public void should_contain_info_about_403_forbidden_error() {
        shouldContainText("Access is denied");
    }
}

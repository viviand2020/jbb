/*
 * Copyright (C) 2017 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.members.api.base;

import org.jbb.lib.commons.vo.Email;
import org.jbb.lib.commons.vo.Username;

public interface Member {
    Long getId();

    Username getUsername();

    DisplayedName getDisplayedName();

    Email getEmail();
}

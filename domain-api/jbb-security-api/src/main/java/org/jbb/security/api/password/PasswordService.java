/*
 * Copyright (C) 2017 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.security.api.password;

import org.jbb.lib.commons.vo.Password;

import java.util.Optional;

public interface PasswordService {
    void changeFor(Long memberId, Password newPassword);

    boolean verifyFor(Long memberId, Password currentPassword);

    Optional<String> getPasswordHash(Long memberId);

    PasswordRequirements currentRequirements();

    void updateRequirements(PasswordRequirements requirements);
}

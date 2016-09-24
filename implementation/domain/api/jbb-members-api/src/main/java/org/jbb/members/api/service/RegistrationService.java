/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.members.api.service;

import org.jbb.lib.core.vo.Username;
import org.jbb.members.api.data.RegistrationMetaData;
import org.jbb.members.api.data.RegistrationRequest;
import org.jbb.members.api.exception.RegistrationException;

public interface RegistrationService {

    /**
     *
     * @param request
     * @throws RegistrationException
     */
    void register(RegistrationRequest request);

    void allowEmailDuplication(boolean allow);

    RegistrationMetaData getRegistrationMetaData(Username username);
}

/*
 * Copyright (C) 2017 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.security.api.service;


import org.jbb.security.api.model.UserLockSettings;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserLockService {

    void lockUserIfQualify(Long memberID);

    boolean isUserHasAccountLock(Long memberID);

    void setProperties(UserLockSettings settings);

    UserLockSettings getUserLockServiceSettings();

    Optional<LocalDateTime> getUserLockExpireTime(Long memberID);

    void releaseUserAccountLockOnDemand(Long memberID);

    void cleanInvalidAttemptsForSpecifyUser(Long memberID);

}

/*
 * Copyright (C) 2017 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.system.api.cache;


import org.jbb.system.api.cache.validation.PositiveDuration;

import java.time.Duration;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HazelcastClientSettings extends HazelcastSettings {

    @NotNull
    @PositiveDuration
    private Duration connectionTimeout;

    @NotNull
    @PositiveDuration
    private Duration connectionAttemptPeriod;

    @Min(1)
    private int connectionAttemptLimit;

    @Override
    public CacheProvider getCacheProvider() {
        return CacheProvider.HAZELCAST_CLIENT;
    }
}

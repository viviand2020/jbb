/*
 * Copyright (C) 2017 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.system.web;

import org.jbb.system.api.cache.CacheSettingsService;
import org.jbb.system.api.database.DatabaseSettingsService;
import org.jbb.system.api.install.InstallationService;
import org.jbb.system.api.logging.LoggingSettingsService;
import org.jbb.system.api.session.SessionService;
import org.jbb.system.api.stacktrace.StackTraceService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class SystemConfigMock {

    @Bean
    @Primary
    public StackTraceService stackTraceService() {
        return Mockito.mock(StackTraceService.class);
    }

    @Bean
    @Primary
    public LoggingSettingsService loggingSettingsService() {
        return Mockito.mock(LoggingSettingsService.class);
    }

    @Bean
    @Primary
    public DatabaseSettingsService databaseSettingsService() {
        return Mockito.mock(DatabaseSettingsService.class);
    }

    @Bean
    @Primary
    public SessionService sessionService() {
        return Mockito.mock(SessionService.class);
    }

    @Bean
    @Primary
    public CacheSettingsService cacheSettingsService() {
        return Mockito.mock(CacheSettingsService.class);
    }

    @Bean
    @Primary
    public InstallationService installationService() {
        return Mockito.mock(InstallationService.class);
    }

}

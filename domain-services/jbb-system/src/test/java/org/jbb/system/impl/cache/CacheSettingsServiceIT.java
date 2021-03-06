/*
 * Copyright (C) 2018 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.system.impl.cache;

import org.jbb.system.api.cache.CacheConfigException;
import org.jbb.system.api.cache.CacheProvider;
import org.jbb.system.api.cache.CacheSettings;
import org.jbb.system.api.cache.CacheSettingsService;
import org.jbb.system.impl.BaseIT;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class CacheSettingsServiceIT extends BaseIT {

    @Autowired
    private CacheSettingsService cacheSettingsService;

    @Test(expected = NullPointerException.class)
    public void shouldThrowNPE_whenNullNewCacheSettingsPassed() {
        // when
        cacheSettingsService.setCacheSettings(null);
    }

    @Test
    public void defaultCacheSettings_shouldEnableApplicationAndSecondLevelCaching() {
        // when
        CacheSettings cacheSettings = cacheSettingsService.getCacheSettings();

        // then
        assertThat(cacheSettings.isApplicationCacheEnabled()).isTrue();
        assertThat(cacheSettings.isSecondLevelCacheEnabled()).isTrue();
        assertThat(cacheSettings.isQueryCacheEnabled()).isFalse();
    }

    @Test
    public void shouldSetNewCacheSettings_whenProvided() {
        // given
        CacheSettings cacheSettings = cacheSettingsService.getCacheSettings();
        cacheSettings.setApplicationCacheEnabled(false);
        cacheSettings.setSecondLevelCacheEnabled(true);
        cacheSettings.setQueryCacheEnabled(true);
        cacheSettings.setCurrentCacheProvider(CacheProvider.HAZELCAST_SERVER);

        // when
        cacheSettingsService.setCacheSettings(cacheSettings);
        CacheSettings result = cacheSettingsService.getCacheSettings();

        // then
        assertThat(result.isApplicationCacheEnabled()).isFalse();
        assertThat(result.isSecondLevelCacheEnabled()).isTrue();
        assertThat(result.isQueryCacheEnabled()).isTrue();
        assertThat(result.getCurrentCacheProvider()).isEqualTo(CacheProvider.HAZELCAST_SERVER);
    }

    @Test(expected = CacheConfigException.class)
    public void shouldThrowCacheConfigException_whenCurrentCacheProviderNotSet() {
        // given
        CacheSettings cacheSettings = cacheSettingsService.getCacheSettings();
        cacheSettings.setCurrentCacheProvider(null);

        // when
        cacheSettingsService.setCacheSettings(cacheSettings);

        // then
        // throw CacheConfigException
    }
}
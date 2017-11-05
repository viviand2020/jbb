/*
 * Copyright (C) 2017 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.system.impl.cache.logic.install;

import com.github.zafarkhaja.semver.Version;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.jbb.install.InstallUpdateAction;
import org.jbb.install.InstallationData;
import org.jbb.install.JbbVersions;
import org.jbb.install.cache.CacheInstallationData;
import org.jbb.install.cache.HazelcastClientInstallationData;
import org.jbb.install.cache.HazelcastServerInstallationData;
import org.jbb.system.api.cache.CacheProvider;
import org.jbb.system.api.cache.CacheSettings;
import org.jbb.system.api.cache.CacheSettingsService;
import org.jbb.system.api.cache.HazelcastClientSettings;
import org.jbb.system.api.cache.HazelcastServerSettings;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@DependsOn("cachePropertiesPropertyListener")
public class CacheInstallAction implements InstallUpdateAction {

    private final CacheSettingsService cacheSettingsService;

    @Override
    public Version fromVersion() {
        return JbbVersions.VERSION_0_9_0;
    }

    @Override
    public void install(InstallationData installationData) {
        Optional<CacheInstallationData> cacheDataOptional = installationData
            .getCacheInstallationData();
        if (!cacheDataOptional.isPresent()) {
            return;
        }
        CacheInstallationData cacheData = cacheDataOptional.get();
        CacheSettings cacheSettings = cacheSettingsService.getCacheSettings();
        CacheProvider cacheProvider = EnumUtils
            .getEnum(CacheProvider.class, cacheData.getCacheType().toString());
        cacheSettings.setCurrentCacheProvider(cacheProvider);

        if (cacheProvider == CacheProvider.HAZELCAST_SERVER) {
            HazelcastServerSettings hazelcastServerSettings = cacheSettings
                .getHazelcastServerSettings();
            HazelcastServerInstallationData serverInstallationData = cacheData
                .getHazelcastServerInstallationData();
            hazelcastServerSettings.setMembers(serverInstallationData.getMembers());
            hazelcastServerSettings.setGroupName(serverInstallationData.getGroupName());
            hazelcastServerSettings.setGroupPassword(serverInstallationData.getGroupPassword());
            hazelcastServerSettings.setServerPort(serverInstallationData.getServerPort());
            hazelcastServerSettings
                .setManagementCenterEnabled(serverInstallationData.getManagementCenterEnabled());
            hazelcastServerSettings
                .setManagementCenterUrl(serverInstallationData.getManagementCenterUrl());
        } else if (cacheProvider == CacheProvider.HAZELCAST_CLIENT) {
            HazelcastClientSettings hazelcastClientSettings = cacheSettings
                .getHazelcastClientSettings();
            HazelcastClientInstallationData clientInstallationData = cacheData
                .getHazelcastClientInstallationData();
            hazelcastClientSettings.setMembers(clientInstallationData.getMembers());
            hazelcastClientSettings.setGroupName(clientInstallationData.getGroupName());
            hazelcastClientSettings.setGroupPassword(clientInstallationData.getGroupPassword());
        }

        cacheSettingsService.setCacheSettings(cacheSettings);
    }
}

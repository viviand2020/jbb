/*
 * Copyright (C) 2017 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.lib.db.provider;

import com.google.common.collect.ImmutableMap;

import org.jbb.lib.db.DbProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DatabaseProviderService {

    private static final Map<String, Class<? extends DatabaseProvider>> PROVIDERS =
            ImmutableMap.<String, Class<? extends DatabaseProvider>>builder()
                    .put(H2InMemoryProvider.PROVIDER_VALUE, H2InMemoryProvider.class)
                    .put(H2EmbeddedProvider.PROVIDER_VALUE, H2EmbeddedProvider.class)
                    .put(H2ManagedServerProvider.PROVIDER_VALUE, H2ManagedServerProvider.class)
                    .put(H2RemoteServerProvider.PROVIDER_VALUE, H2RemoteServerProvider.class)
                    .put(PostgresqlServerProvider.PROVIDER_VALUE, PostgresqlServerProvider.class)
                    .build();

    private final DbProperties dbProperties;
    private final ApplicationContext appContext;

    public DatabaseProvider getCurrentProvider() {
        String providerName = dbProperties.currentProvider();
        return getProviderForName(providerName);
    }

    private DatabaseProvider getProviderForName(String providerName) {
        Class managerClass = PROVIDERS.get(providerName.trim().toLowerCase());

        if (managerClass != null) {
            return (DatabaseProvider) appContext.getBean(managerClass);
        }

        throw new IllegalStateException(
                String.format("No database provider with name: %s", providerName));

    }

}

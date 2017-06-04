/*
 * Copyright (C) 2017 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.system.impl.database.logic;

import org.apache.commons.lang3.Validate;
import org.jbb.lib.db.DbProperties;
import org.jbb.system.api.database.DatabaseConfigException;
import org.jbb.system.api.database.DatabaseSettings;
import org.jbb.system.api.database.DatabaseSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

@Service
public class DatabaseSettingsServiceImpl implements DatabaseSettingsService {
    private final DbProperties dbProperties;
    private final DatabaseSettingsImplFactory databaseSettingsFactory;
    private final Validator validator;
    private final ReconnectionToDbPropertyListener reconnectionPropertyListener;
    private final ConnectionToDatabaseEventSender eventSender;

    @Autowired
    public DatabaseSettingsServiceImpl(DbProperties dbProperties,
                                       DatabaseSettingsImplFactory databaseSettingsFactory,
                                       Validator validator,
                                       ReconnectionToDbPropertyListener reconnectionPropertyListener,
                                       ConnectionToDatabaseEventSender eventSender) {
        this.dbProperties = dbProperties;
        this.databaseSettingsFactory = databaseSettingsFactory;
        this.validator = validator;
        this.reconnectionPropertyListener = reconnectionPropertyListener;
        this.eventSender = eventSender;
    }

    @PostConstruct
    public void addReconnectionPropertyListenerToDbProperties() {
        dbProperties.addPropertyChangeListener(reconnectionPropertyListener);
    }

    @Override
    public DatabaseSettings getDatabaseSettings() {
        return databaseSettingsFactory.currentDatabaseSettings();
    }

    @Override
    public void setDatabaseSettings(DatabaseSettings databaseSettings) {
        Validate.notNull(databaseSettings);

        DatabaseSettings newDatabaseSettings = databaseSettingsFactory.create(databaseSettings);
        Set<ConstraintViolation<DatabaseSettings>> validationResult = validator.validate(newDatabaseSettings);
        if (!validationResult.isEmpty()) {
            throw new DatabaseConfigException(validationResult);
        }

        dbProperties.removePropertyChangeListener(reconnectionPropertyListener);

        dbProperties.setProperty(DbProperties.DB_FILENAME_KEY, newDatabaseSettings.databaseFileName());
        dbProperties.setProperty(DbProperties.DB_MIN_IDLE_KEY, Integer.toString(newDatabaseSettings.minimumIdleConnections()));
        dbProperties.setProperty(DbProperties.DB_MAX_POOL_KEY, Integer.toString(newDatabaseSettings.maximumPoolSize()));
        dbProperties.setProperty(DbProperties.DB_CONN_TIMEOUT_MS_KEY, Integer.toString(newDatabaseSettings.connectionTimeoutMilliseconds()));
        dbProperties.setProperty(DbProperties.DB_INIT_FAIL_FAST_KEY, Boolean.toString(newDatabaseSettings.failAtStartingImmediately()));
        dbProperties.setProperty(DbProperties.DB_DROP_DURING_START_KEY, Boolean.toString(newDatabaseSettings.dropDatabaseAtStart()));
        dbProperties.setProperty(DbProperties.DB_AUDIT_ENABLED_KEY, Boolean.toString(newDatabaseSettings.auditEnabled()));

        dbProperties.addPropertyChangeListener(reconnectionPropertyListener);

        eventSender.emitEvent();
    }
}

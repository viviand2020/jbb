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

import com.google.common.collect.Sets;

import org.jbb.lib.db.DbProperties;
import org.jbb.system.api.database.DatabaseConfigException;
import org.jbb.system.api.database.DatabaseSettings;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseSettingsServiceImplTest {
    @Mock
    private ConnectionToDatabaseEventSender eventSenderMock;
    @Mock
    private DbProperties dbPropertiesMock;
    @Mock
    private DatabaseSettingsImplFactory databaseSettingsFactoryMock;
    @Mock
    private Validator validatorMock;

    @InjectMocks
    private DatabaseSettingsServiceImpl databaseSettingsService;

    @Test
    public void shouldReturnSettings_fromFactory() throws Exception {
        // given
        DatabaseSettings databaseSettingsMock = mock(DatabaseSettings.class);
        given(databaseSettingsFactoryMock.currentDatabaseSettings()).willReturn(databaseSettingsMock);

        // when
        DatabaseSettings result = databaseSettingsService.getDatabaseSettings();

        // then
        assertThat(result).isEqualTo(databaseSettingsMock);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNPE_whenNullSettingsPassed() throws Exception {
        // when
        databaseSettingsService.setDatabaseSettings(null);

        // then
        // throw NullPointerException
    }

    @Test(expected = DatabaseConfigException.class)
    public void shouldThrowDatabaseSettingsException_whenValidationOfSettingsFailed() throws Exception {
        // given
        DatabaseSettings databaseSettingsMock = mock(DatabaseSettings.class);
        given(databaseSettingsFactoryMock.create(any())).willReturn(databaseSettingsMock);
        given(validatorMock.validate(any())).willReturn(Sets.newHashSet(mock(ConstraintViolation.class)));

        // when
        databaseSettingsService.setDatabaseSettings(mock(DatabaseSettings.class));

        // then
        // throw DatabaseSettingsException
    }

    @Test
    public void shouldSetProperties_whenValidationOfSettingsPassed() throws Exception {
        // given
        DatabaseSettings databaseSettingsMock = mock(DatabaseSettings.class);
        given(databaseSettingsFactoryMock.create(any())).willReturn(databaseSettingsMock);
        given(validatorMock.validate(any())).willReturn(Sets.newHashSet());

        // when
        databaseSettingsService.setDatabaseSettings(mock(DatabaseSettings.class));

        // then
        verify(dbPropertiesMock, times(7)).setProperty(any(String.class), nullable(String.class));
    }

}
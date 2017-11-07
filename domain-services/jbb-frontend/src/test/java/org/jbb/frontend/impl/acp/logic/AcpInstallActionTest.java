/*
 * Copyright (C) 2017 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.frontend.impl.acp.logic;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.jbb.frontend.impl.acp.dao.AcpCategoryRepository;
import org.jbb.frontend.impl.acp.dao.AcpElementRepository;
import org.jbb.frontend.impl.acp.dao.AcpSubcategoryRepository;
import org.jbb.frontend.impl.acp.model.AcpCategoryEntity;
import org.jbb.install.InstallationData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AcpInstallActionTest {

    @Mock
    private AcpCategoryFactory acpCategoryFactoryMock;

    @Mock
    private AcpSubcategoryFactory acpSubcategoryFactoryMock;

    @Mock
    private AcpCategoryRepository acpCategoryRepositoryMock;

    @Mock
    private AcpSubcategoryRepository acpSubcategoryRepositoryMock;

    @Mock
    private AcpElementRepository acpElementRepositoryMock;

    @InjectMocks
    private AcpInstallAction acpInstallAction;

    @Test
    public void shouldBuild_whenAcpTablesAreEmpty() throws Exception {
        // given
        given(acpCategoryRepositoryMock.count()).willReturn(0L);
        given(acpSubcategoryRepositoryMock.count()).willReturn(0L);
        given(acpElementRepositoryMock.count()).willReturn(0L);

        // when
        acpInstallAction.install(mock(InstallationData.class));

        // then
        verify(acpCategoryRepositoryMock, atLeastOnce()).save(nullable(AcpCategoryEntity.class));
    }

    @Test
    public void shouldNotBuild_whenAcpTablesAreNotEmpty() throws Exception {
        // given
        given(acpCategoryRepositoryMock.count()).willReturn(1L);

        // when
        acpInstallAction.install(mock(InstallationData.class));

        // then
        verify(acpCategoryRepositoryMock, times(0)).save(any(AcpCategoryEntity.class));
    }

}
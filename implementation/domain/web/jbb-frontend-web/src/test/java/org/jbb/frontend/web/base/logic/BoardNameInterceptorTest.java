/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.frontend.web.base.logic;

import org.jbb.board.api.service.BoardSettingsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BoardNameInterceptorTest {
    private static final HttpServletResponse ANY_HTTP_RESPONSE = null;
    private static final Object ANY_HANDLER = null;

    @Mock
    private BoardSettingsService boardSettingsServiceMock;

    @InjectMocks
    private BoardNameInterceptor boardNameInterceptor;

    @Test
    public void shouldSetBoardNameFromServiceToRequestAttribute() throws Exception {
        // given
        String boardName = "jBB Board";
        given(boardSettingsServiceMock.getBoardName()).willReturn(boardName);

        HttpServletRequest requestMock = Mockito.mock(HttpServletRequest.class);

        // when
        boardNameInterceptor.preHandle(requestMock, ANY_HTTP_RESPONSE, ANY_HANDLER);

        // then
        verify(requestMock, times(1)).setAttribute(eq("boardName"), eq(boardName));
    }

    @Test
    public void shouldProcessingMoveOn() throws Exception {
        // given
        given(boardSettingsServiceMock.getBoardName()).willReturn("anyName");
        HttpServletRequest requestMock = Mockito.mock(HttpServletRequest.class);

        // when
        boolean process = boardNameInterceptor.preHandle(requestMock, ANY_HTTP_RESPONSE, ANY_HANDLER);

        // then
        assertThat(process).isTrue();
    }
}
/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.frontend.web.stacktrace.logic;

import org.jbb.frontend.web.base.logic.BoardNameInterceptor;
import org.jbb.frontend.web.base.logic.JbbVersionInterceptor;
import org.jbb.frontend.web.base.logic.ReplacingViewInterceptor;
import org.jbb.system.api.stacktrace.StackTraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class DefaultRequestExceptionHandler {

    private static final String DEFAULT_EXCEPTION_VIEW_NAME = "defaultException";
    private static final String NOT_FOUND_EXCEPTION_VIEW_NAME = "notFoundException";

    private final StackTraceService stackTraceService;
    private final BoardNameInterceptor boardNameInterceptor;
    private final JbbVersionInterceptor jbbVersionInterceptor;
    private final ReplacingViewInterceptor replacingViewInterceptor;

    @Autowired
    public DefaultRequestExceptionHandler(StackTraceService stackTraceService,
                                          BoardNameInterceptor boardNameInterceptor,
                                          JbbVersionInterceptor jbbVersionInterceptor,
                                          ReplacingViewInterceptor replacingViewInterceptor) {
        this.stackTraceService = stackTraceService;
        this.boardNameInterceptor = boardNameInterceptor;
        this.jbbVersionInterceptor = jbbVersionInterceptor;
        this.replacingViewInterceptor = replacingViewInterceptor;
    }

    @ExceptionHandler(value = {NoHandlerFoundException.class})
    public ModelAndView notFoundExceptionHandler() {
        return new ModelAndView(NOT_FOUND_EXCEPTION_VIEW_NAME);
    }

    @ExceptionHandler(value = {RuntimeException.class, Exception.class})
    public ModelAndView defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        ModelAndView modelAndView = new ModelAndView(DEFAULT_EXCEPTION_VIEW_NAME);

        modelAndView.addObject("requestURL", request.getRequestURL());
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("status", response.getStatus());
        modelAndView.addObject("stacktrace", getStackTraceAsString(e));

        boardNameInterceptor.preHandle(request, response, this);
        jbbVersionInterceptor.preHandle(request, response, this);
        replacingViewInterceptor.postHandle(request, response, this, modelAndView);

        return modelAndView;
    }

    private String getStackTraceAsString(Exception e) {
        return stackTraceService.getStackTraceAsString(e).orElse(null);
    }
}
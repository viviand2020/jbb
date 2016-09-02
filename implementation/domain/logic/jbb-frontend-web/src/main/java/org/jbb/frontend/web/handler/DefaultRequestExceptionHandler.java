/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.frontend.web.handler;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class DefaultRequestExceptionHandler {

    private static final String DEFAULT_EXCEPTION_VIEW_NAME = "/exception/default";
    private static final String NOT_FOUND_EXCEPTION_VIEW_NAME = "/exception/notfound";

    @ExceptionHandler(value = {RuntimeException.class, Exception.class})
    public ModelAndView defaultErrorHandler(HttpServletResponse response, HttpServletRequest request, Exception e) throws Exception {
        ModelAndView modelAndView = new ModelAndView(DEFAULT_EXCEPTION_VIEW_NAME);

        modelAndView.addObject("requestURL", request.getRequestURL());
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("status", response.getStatus());

        if (isUserShouldSeeStrackTrace()) {
            modelAndView.addObject("stacktrace", getStackTraceAsString(e));
        }
        return modelAndView;
    }

    private String getStackTraceAsString(Exception e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        return stringWriter.toString();
    }

    @ExceptionHandler(value = {NoHandlerFoundException.class})
    public ModelAndView notFoundExceptionHandler(HttpServletResponse response, HttpServletRequest request, Exception e) {
        ModelAndView modelAndView = new ModelAndView(NOT_FOUND_EXCEPTION_VIEW_NAME);
        return modelAndView;
    }

    private boolean isUserShouldSeeStrackTrace() {
        /** TODO
         * Properties ktory bedzie czytany, http://www.hostedredmine.com/issues/574022
         */
        return true;
    }
}

/*
 * Copyright (C) 2017 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.system.api.logging.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoggingConfiguration {
    private List<LogConsoleAppender> consoleAppenders = new ArrayList<>();
    private List<LogFileAppender> fileAppenders = new ArrayList<>();
    private List<AppLogger> loggers = new ArrayList<>();
    private boolean debugLoggingFrameworkMode;
    private boolean showPackagingData;
}

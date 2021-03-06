/*
 * Copyright (C) 2017 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.system.web.logging.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileAppenderSettingsForm {
    private String name;
    private String currentLogFileName;
    private String rotationFileNamePattern;
    private String maxFileSize;
    private int maxHistory = 0;
    private String filter = "None";
    private String pattern;

    private boolean addingMode;
}

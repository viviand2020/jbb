/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.system.api.model.logging;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogConsoleAppender implements LogAppender {
    private String name;
    private Target target;
    private LogFilter filter;
    private String pattern;
    private boolean useColor;

    public enum Target {
        SYSTEM_OUT("System.out"), SYSTEM_ERR("System.err");

        private String value;

        Target(String value) {
            this.value = value;
        }

        public static Target getFromStreamName(String streamName) {
            if (SYSTEM_OUT.getValue().equalsIgnoreCase(streamName)) {
                return SYSTEM_OUT;
            } else if (SYSTEM_ERR.getValue().equalsIgnoreCase(streamName)) {
                return SYSTEM_ERR;
            } else {
                return null;
            }
        }

        public String getValue() {
            return value;
        }
    }
}

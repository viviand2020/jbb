/*
 * Copyright (C) 2018 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.lib.metrics;

public enum MetricType {

    JVM("jvm"),
    JDBC("jdbc"),
    LOGGING("logging"),
    CACHE("cache");

    private final String code;

    MetricType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

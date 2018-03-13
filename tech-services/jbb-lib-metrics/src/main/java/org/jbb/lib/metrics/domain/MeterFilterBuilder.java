/*
 * Copyright (C) 2018 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.lib.metrics.domain;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import javax.annotation.PostConstruct;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.config.MeterFilterReply;

@Component
public class MeterFilterBuilder {

    private Map<MetricType, Predicate<Meter.Id>> predicateMap = new HashMap<>();

    @PostConstruct
    public void fillMap() {
        predicateMap.put(MetricType.JVM, id -> id.getName().startsWith("jvm"));
        predicateMap.put(MetricType.OS, id -> id.getName().startsWith("process") ||
                id.getName().startsWith("system"));
        predicateMap.put(MetricType.JDBC, id -> id.getName().startsWith("hibernate") ||
                id.getName().startsWith("jdbc"));
        predicateMap.put(MetricType.LOG, id -> id.getName().startsWith("logback"));
        predicateMap.put(MetricType.CACHE, id -> id.getName().startsWith("cache"));
        predicateMap.put(MetricType.REQUEST, id -> id.getName().startsWith("request"));
    }

    public MeterFilter build(Set<MetricType> types) {
        return new MeterFilter() {
            @Override
            public MeterFilterReply accept(Meter.Id id) {
                boolean deny = types.stream()
                        .map(type -> predicateMap.get(type))
                        .noneMatch(p -> p.test(id));
                if (deny) {
                    return MeterFilterReply.DENY;
                } else {
                    return MeterFilterReply.NEUTRAL;
                }
            }
        };
    }

}

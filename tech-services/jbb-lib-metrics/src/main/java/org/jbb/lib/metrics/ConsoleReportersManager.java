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

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;

import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.core.instrument.dropwizard.DropwizardConfig;
import io.micrometer.core.instrument.dropwizard.DropwizardMeterRegistry;
import io.micrometer.core.instrument.util.HierarchicalNameMapper;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ConsoleReportersManager extends ReportersManager {

    private final CompositeMeterRegistry compositeMeterRegistry;

    @Override
    void init(MetricProperties properties, MetricType type) {
        MetricRegistry dropwizardRegistry = new MetricRegistry();

        ConsoleReporter reporter = ConsoleReporter.forRegistry(dropwizardRegistry)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(10, TimeUnit.SECONDS);

        DropwizardConfig consoleConfig = new DropwizardConfig() {
            @Override
            public String prefix() {
                return "console";
            }

            @Override
            public String get(String key) {
                return null;
            }
        };

        DropwizardMeterRegistry dropwizardMeterRegistry = new DropwizardMeterRegistry(consoleConfig, dropwizardRegistry, HierarchicalNameMapper.DEFAULT, Clock.SYSTEM) {

            @Override
            protected Double nullGaugeValue() {
                return Double.NaN;
            }
        };

        compositeMeterRegistry.add(dropwizardMeterRegistry);
    }

    @Override
    void configure(MetricProperties properties, MetricType type) {
        init(properties, type);
    }

}

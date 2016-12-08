/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.system.impl.logging.logic;

import org.jbb.lib.logging.ConfigurationRepository;
import org.jbb.lib.logging.jaxb.Configuration;
import org.jbb.lib.logging.jaxb.Logger;
import org.jbb.system.api.exception.LoggingConfigException;
import org.jbb.system.api.model.logging.AppLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Component
public class LoggerEditor {
    private final ConfigurationRepository configRepository;
    private final LoggingConfigMapper configMapper;

    @Autowired
    public LoggerEditor(ConfigurationRepository configRepository,
                        LoggingConfigMapper configMapper) {
        this.configRepository = configRepository;
        this.configMapper = configMapper;
    }

    public void add(AppLogger logger) {
        Configuration configuration = configRepository.getConfiguration();
        List<Object> confElements = configuration.getShutdownHookOrStatusListenerOrContextListener();
        boolean alreadyExists = confElements.stream().anyMatch(loggerWithName(logger.getName()));
        if (alreadyExists) {
            throw new LoggingConfigException(String.format("Logger with name '%d' exists yet", logger.getName()));
        }
        Logger jaxbLogger = configMapper.buildJaxb(logger);
        confElements.add(jaxbLogger);
        configRepository.persistNewConfiguration(configuration);
    }

    public void update(AppLogger logger) {
        Configuration configuration = configRepository.getConfiguration();
        List<Object> confElements = configuration.getShutdownHookOrStatusListenerOrContextListener();
        Optional<Object> jaxbLogger = confElements.stream()
                .filter(loggerWithName(logger.getName()))
                .findFirst();
        if (jaxbLogger.isPresent()) {
            confElements.set(confElements.indexOf(jaxbLogger.get()), configMapper.buildJaxb(logger));
            configRepository.persistNewConfiguration(configuration);
        } else {
            throw new LoggingConfigException(String.format("Logger with name '%d' doesn't exist", logger.getName()));
        }
    }

    public void delete(AppLogger logger) {
        assertNotRoot(logger);
        Configuration configuration = configRepository.getConfiguration();
        List<Object> confElements = configuration.getShutdownHookOrStatusListenerOrContextListener();
        Optional<Object> jaxbLogger = confElements.stream()
                .filter(loggerWithName(logger.getName()))
                .findFirst();
        if (jaxbLogger.isPresent()) {
            confElements.remove(jaxbLogger.get());
            configRepository.persistNewConfiguration(configuration);
        } else {
            throw new LoggingConfigException(String.format("Logger with name '%d' doesn't exist", logger.getName()));
        }
    }

    private void assertNotRoot(AppLogger logger) {
        if (logger.isRootLogger()) {
            throw new LoggingConfigException("You can't remove root logger");
        }
    }

    private Predicate<? super Object> loggerWithName(String name) {
        return o -> o instanceof Logger && ((Logger) o).getName().equals(name);
    }
}

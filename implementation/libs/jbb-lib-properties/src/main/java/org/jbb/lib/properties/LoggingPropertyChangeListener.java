/*
 * Copyright (C) 2017 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.lib.properties;

import org.jbb.lib.properties.encrypt.PropertiesEncryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import lombok.extern.slf4j.Slf4j;

import static org.jbb.lib.properties.encrypt.EncryptionPlaceholderUtils.isInEncPlaceholder;

@Slf4j
@Component
class LoggingPropertyChangeListener implements PropertyChangeListener {
    private final PropertiesEncryption propertiesEncryption;

    @Autowired
    LoggingPropertyChangeListener(PropertiesEncryption propertiesEncryption) {
        this.propertiesEncryption = propertiesEncryption;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if (isInEncPlaceholder(evt.getNewValue().toString())) {
            ModuleProperties properties = (ModuleProperties) evt.getSource();
            properties.setProperty(evt.getPropertyName(), propertiesEncryption.encryptIfNeeded(evt.getNewValue().toString()));
        }
        log.info("Property '{}' in {} changed from '{}' to '{}'",
                evt.getPropertyName(), evt.getPropagationId(), evt.getOldValue(), evt.getNewValue());
    }
}

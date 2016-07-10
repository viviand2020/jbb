/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.lib.properties;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class LoggingPropertyChangeListener implements PropertyChangeListener {
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        log.info("Property '{}' in {} changed from '{}' to '{}'",
                evt.getPropertyName(), evt.getPropagationId(), evt.getOldValue(), evt.getNewValue());
    }
}

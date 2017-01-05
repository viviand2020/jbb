/*
 * Copyright (C) 2017 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.system.impl.database.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

@Component
public class ReconnectionToDbPropertyListener implements PropertyChangeListener {
    private final ConnectionToDatabaseEventSender eventSender;

    @Autowired
    public ReconnectionToDbPropertyListener(ConnectionToDatabaseEventSender eventSender) {
        this.eventSender = eventSender;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        eventSender.emitEvent();
    }
}

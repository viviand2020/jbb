/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.lib.eventbus;

import com.google.common.eventbus.EventBus;

import org.springframework.stereotype.Component;

@Component
public class JbbEventBus extends EventBus {
    public JbbEventBus(EventExceptionHandler exceptionHandler, EventBusAuditRecorder auditRecorder) {
        super(exceptionHandler);
        register(auditRecorder);
    }
}

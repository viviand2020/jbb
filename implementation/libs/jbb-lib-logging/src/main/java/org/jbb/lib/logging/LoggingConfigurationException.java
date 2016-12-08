/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.lib.logging;

import javax.xml.bind.JAXBException;

public class LoggingConfigurationException extends RuntimeException {

    public LoggingConfigurationException(String message, JAXBException e) {
        super(message, e);
    }
}

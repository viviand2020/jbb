/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.frontend;

import org.jbb.frontend.api.services.FormatterService;
import org.jbb.frontend.properties.FrontendProperties;
import org.jbb.frontend.services.LocalDateTimeFormatter;
import org.jbb.lib.properties.ModulePropertiesFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.jbb.frontend")
public class FrontendConfig {

    @Bean
    public FrontendProperties frontendProperties(ModulePropertiesFactory propertiesFactory) {
        return propertiesFactory.create(FrontendProperties.class);
    }

    @Bean
    public LocalDateTimeFormatter localDateTimeFormatter(FormatterService formatterService) {
        return new LocalDateTimeFormatter(formatterService);
    }
}

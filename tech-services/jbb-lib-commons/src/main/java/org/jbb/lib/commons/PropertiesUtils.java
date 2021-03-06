/*
 * Copyright (C) 2017 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.lib.commons;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.net.URL;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PropertiesUtils {

    public static Configuration buildPropertiesConfiguration(URL url) {
        FileBasedConfigurationBuilder<PropertiesConfiguration> builder =
                new FileBasedConfigurationBuilder<>(PropertiesConfiguration.class)
                        .configure(new Parameters().properties()
                                .setURL(url)
                                .setThrowExceptionOnMissing(true)
                                .setIncludesAllowed(false));
        try {
            return builder.getConfiguration();
        } catch (ConfigurationException e) {
            throw new IllegalStateException(e);
        }
    }

}

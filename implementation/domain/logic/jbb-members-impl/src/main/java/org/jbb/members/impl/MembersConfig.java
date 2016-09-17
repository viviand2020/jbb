/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.members.impl;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;
import org.jbb.lib.db.DbConfig;
import org.jbb.lib.properties.ModulePropertiesFactory;
import org.jbb.members.impl.base.data.MembersProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@EnableJpaRepositories(
        basePackages = {"org.jbb.members.impl.base.dao"},
        entityManagerFactoryRef = DbConfig.EM_FACTORY_BEAN_NAME,
        transactionManagerRef = DbConfig.JTA_MANAGER_BEAN_NAME)
@EnableTransactionManagement
@ComponentScan("org.jbb.members.impl")
public class MembersConfig {

    @Bean
    public MembersProperties membersProperties(ModulePropertiesFactory propertiesFactory) {
        return propertiesFactory.create(MembersProperties.class);
    }

    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean() {
        PlatformResourceBundleLocator rbLocator =
                new PlatformResourceBundleLocator(ResourceBundleMessageInterpolator.USER_VALIDATION_MESSAGES, null, true);
        LocalValidatorFactoryBean validFactory = new LocalValidatorFactoryBean();
        validFactory.setMessageInterpolator(new ResourceBundleMessageInterpolator(rbLocator));
        return validFactory;
    }
}
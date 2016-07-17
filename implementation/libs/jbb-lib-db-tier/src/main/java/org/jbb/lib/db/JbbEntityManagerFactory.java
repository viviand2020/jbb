/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.lib.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.Set;

import javax.sql.DataSource;
import javax.validation.ValidatorFactory;


@Component
public class JbbEntityManagerFactory {
    private DataSource dataSource;
    private ValidatorFactory factory;
    private DbStaticProperties dbProperties;

    @Autowired
    public JbbEntityManagerFactory(DataSource dataSource, ValidatorFactory validatorFactory,
                                   DbStaticProperties dbProperties) {
        this.dataSource = dataSource;
        this.factory = validatorFactory;
        this.dbProperties = dbProperties;
    }

    public LocalContainerEntityManagerFactoryBean getNewInstance(Set<String> packagesToScan) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan(packagesToScan.toArray(new String[]{}));

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
        jpaProperties.put("hibernate.hbm2ddl.auto", schemaDdlBehave());
        jpaProperties.put("hibernate.show_sql", true);
        jpaProperties.put("hibernate.format_sql", true);
        jpaProperties.put("org.hibernate.flushMode", "COMMIT");
        jpaProperties.put("hibernate.enable_lazy_load_no_trans", true);
        jpaProperties.put("javax.persistence.validation.factory", factory);
        entityManagerFactoryBean.setJpaProperties(jpaProperties);

        return entityManagerFactoryBean;
    }

    private String schemaDdlBehave() {
        return dbProperties.dropDbDuringStart() ? "create-drop" : "update";
    }
}
/*
 * Copyright (C) 2017 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.qa.steps;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

import org.jbb.qa.pages.AcpDatabaseSettingsPage;

public class DatabaseSettingsSteps extends ScenarioSteps {
    private AcpDatabaseSettingsPage databaseSettingsPage;

    @Step
    public void open_database_settings_page() {
        databaseSettingsPage.open();
    }

    @Step
    public void type_database_filename(String databaseFilename) {
        databaseSettingsPage.typeDatabaseFilename(databaseFilename);
    }

    @Step
    public void send_database_settings_form() {
        databaseSettingsPage.clickSaveButton();
    }

    @Step
    public void should_be_informed_about_empty_database_filename() {
        databaseSettingsPage.shouldContainInfoAboutEmptyDatabaseFilename();
    }

    @Step
    public void type_minimum_amount_idle_db_connections(String minimumIdleDbConnections) {
        databaseSettingsPage.typeMinimumIdleDbConnections(minimumIdleDbConnections);
    }

    @Step
    public void should_be_informed_about_invalid_minimum_idle_db_connections_value() {
        databaseSettingsPage.shouldContainInfoAboutInvalidMinimumIdleDbConnections();
    }

    @Step
    public void should_be_informed_about_not_positive_minimum_idle_db_connections_value() {
        databaseSettingsPage.shouldContainInfoAboutNotPositiveMinimumIdleDbConnections();
    }

    @Step
    public void type_maximum_size_connection_pool(String maximumSizeConnectionPool) {
        databaseSettingsPage.typeMaximumSizeConnectionPool(maximumSizeConnectionPool);
    }

    @Step
    public void should_be_informed_about_invalid_maximum_size_connection_pool_value() {
        databaseSettingsPage.shouldContainInfoAboutInvalidMaximumSizeConnectionPool();
    }

    @Step
    public void should_be_informed_about_not_positive_maximum_size_connection_pool_value() {
        databaseSettingsPage.shouldContainInfoAboutNotPositiveMaximumSizeConnectionPool();
    }

    @Step
    public void type_connection_timeout_miliseconds(String connectionTimeoutMiliseconds) {
        databaseSettingsPage.typeConnectionTimeoutMiliseconds(connectionTimeoutMiliseconds);
    }

    @Step
    public void should_be_informed_about_invalid_connection_timeout_miliseconds_value() {
        databaseSettingsPage.shouldContainInfoAboutInvalidConnectionTimeoutMiliseconds();
    }

    @Step
    public void should_be_informed_about_negative_connection_timeout_miliseconds_value() {
        databaseSettingsPage.shouldContainInfoAboutNegativeConnectionTimeoutMilisecondsValue();
    }

    @Step
    public void should_be_informed_about_saving_settings() {
        databaseSettingsPage.containsInfoAboutSavingSettingsCorrectly();
    }
}

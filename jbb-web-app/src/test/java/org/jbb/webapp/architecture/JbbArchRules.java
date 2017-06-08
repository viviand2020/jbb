/*
 * Copyright (C) 2017 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.webapp.architecture;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.Priority;

import org.aeonbits.owner.Config;
import org.hibernate.envers.Audited;
import org.jbb.lib.db.domain.BaseEntity;
import org.jbb.lib.db.revision.RevisionInfo;
import org.jbb.lib.eventbus.JbbEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.Table;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.priority;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

public class JbbArchRules {
    public static final String TECH_LIBS_LAYER = "Tech libs Layer";
    public static final String API_LAYER = "API Layer";
    public static final String EVENT_API_LAYER = "Event API Layer";
    public static final String SERVICES_LAYER = "Services Layer";
    public static final String WEB_LAYER = "Web Layer";

    public static final String TECH_LIBS_PACKAGES = "org.jbb.lib..";
    public static final String API_PACKAGES = "org.jbb.(*).api..";
    public static final String EVENT_API_PACKAGES = "org.jbb.(*).event..";
    public static final String SERVICES_PACKAGES = "org.jbb.(*).impl..";
    public static final String WEB_PACKAGES = "org.jbb.(*).web..";

    @ArchTest
    public static void testLayeredArchitecture(JavaClasses classes) {
        layeredArchitecture()
                .layer(TECH_LIBS_LAYER).definedBy(TECH_LIBS_PACKAGES)
                .layer(API_LAYER).definedBy(API_PACKAGES)
                .layer(EVENT_API_LAYER).definedBy(EVENT_API_PACKAGES)
                .layer(SERVICES_LAYER).definedBy(SERVICES_PACKAGES)
                .layer(WEB_LAYER).definedBy(WEB_PACKAGES)

                .whereLayer(TECH_LIBS_LAYER).mayOnlyBeAccessedByLayers(SERVICES_LAYER, WEB_LAYER, EVENT_API_LAYER)
                .whereLayer(API_LAYER).mayOnlyBeAccessedByLayers(SERVICES_LAYER, WEB_LAYER)
                .whereLayer(EVENT_API_LAYER).mayOnlyBeAccessedByLayers(SERVICES_LAYER, WEB_LAYER)
                .whereLayer(SERVICES_LAYER).mayNotBeAccessedByAnyLayer()
                .whereLayer(WEB_LAYER).mayNotBeAccessedByAnyLayer()

                .check(classes);
    }

    @ArchTest
    public static void serviceLayerShouldNotUseWebLayer(JavaClasses classes) {
        priority(Priority.HIGH).noClasses()
                .that().resideInAPackage(SERVICES_PACKAGES)
                .should().accessClassesThat().resideInAPackage(WEB_PACKAGES)
                .check(classes);
    }

    @ArchTest
    public static void webLayerShouldNotUseServiceLayer(JavaClasses classes) {
        priority(Priority.HIGH).noClasses()
                .that().resideInAPackage(WEB_PACKAGES)
                .should().accessClassesThat().resideInAPackage(SERVICES_PACKAGES)
                .check(classes);
    }

    @ArchTest
    public static void controllersShouldNotUseRepositoriesDirectly(JavaClasses classes) {
        priority(Priority.HIGH).noClasses().that().areAnnotatedWith(Controller.class)
                .should().accessClassesThat().areAnnotatedWith(Repository.class)
                .check(classes);
    }

    @ArchTest
    public static void controllerNameShouldEndsWithController(JavaClasses classes) {
        priority(Priority.LOW).classes().that().areAnnotatedWith(Controller.class)
                .should().haveNameMatching(".*Controller")
                .check(classes);
    }

    @ArchTest
    public static void entitiesShouldExtendBaseEntity(JavaClasses classes) {
        priority(Priority.MEDIUM).classes().that(areEntity()).and(notBe(RevisionInfo.class))
                .should().implement(BaseEntity.class)
                .check(classes);
    }

    @ArchTest
    public static void entitiesShouldBeAudited(JavaClasses classes) {
        priority(Priority.HIGH).classes().that(areEntity()).and(notBe(RevisionInfo.class))
                .should().beAnnotatedWith(Audited.class)
                .check(classes);
    }

    @ArchTest
    public static void entityNameShouldEndsWithEntity(JavaClasses classes) {
        priority(Priority.LOW).classes().that(areEntity()).and(notBe(RevisionInfo.class))
                .should().haveNameMatching(".*Entity")
                .check(classes);
    }

    @ArchTest
    public static void allClassesMustBeInOrgJbbPackage(JavaClasses classes) {
        priority(Priority.MEDIUM).classes()
                .should().resideInAPackage("org.jbb..").check(classes);
    }

    @ArchTest
    public static void springConfigurationClassNameShouldEndsWithConfig(JavaClasses classes) {
        priority(Priority.LOW).classes().that().areAnnotatedWith(Configuration.class)
                .should().haveNameMatching(".*Config")
                .check(classes);
    }

    @ArchTest
    public static void ownerConfigurationClassNameShouldEndsWithProperties(JavaClasses classes) {
        priority(Priority.LOW).classes().that().areAssignableTo(Config.class)
                .should().haveNameMatching(".*Properties")
                .check(classes);
    }

    @ArchTest
    public static void jbbDomainEventClassNameShouldEndsWithEvent(JavaClasses classes) {
        priority(Priority.LOW).classes().that().areAssignableTo(JbbEvent.class)
                .should().haveNameMatching(".*Event")
                .check(classes);
    }

    @ArchTest
    public static void libModulesCannotHaveCycle(JavaClasses classes) {
        slices().matching(TECH_LIBS_PACKAGES).namingSlices("$1 lib")
                .as(TECH_LIBS_LAYER).should().beFreeOfCycles().check(classes);
    }

    @ArchTest
    public static void apiModuleCannotUseAnotherApiModule(JavaClasses classes) {
        slices().matching(API_PACKAGES).namingSlices("$1 api")
                .as(API_LAYER).should().notDependOnEachOther().check(classes);
    }

    @ArchTest
    public static void eventApiModuleCannotUseAnotherEventApiModule(JavaClasses classes) {
        slices().matching(EVENT_API_PACKAGES).namingSlices("$1 event api")
                .as(EVENT_API_LAYER).should().notDependOnEachOther().check(classes);
    }

    @ArchTest
    public static void serviceModuleCannotUseAnotherServiceModule(JavaClasses classes) {
        slices().matching(SERVICES_PACKAGES).namingSlices("$1 service")
                .as(SERVICES_LAYER).should().notDependOnEachOther().check(classes);
    }

    @ArchTest
    public static void webModuleCannotUseAnotherWebModule(JavaClasses classes) {
        slices().matching(WEB_PACKAGES).namingSlices("$1 web")
                .as(WEB_LAYER).should().notDependOnEachOther().check(classes);
    }

    private static DescribedPredicate<JavaClass> areEntity() {
        return new DescribedPredicate<JavaClass>("Entity class") {
            @Override
            public boolean apply(JavaClass javaClass) {
                return javaClass.isAnnotatedWith(Entity.class) ||
                        javaClass.isAnnotatedWith(Table.class);
            }
        };
    }

    private static DescribedPredicate<JavaClass> notBe(Class clazz) {
        return new DescribedPredicate<JavaClass>("Class " + clazz.getCanonicalName()) {
            @Override
            public boolean apply(JavaClass javaClass) {
                return !javaClass.getName().equals(clazz.getName());
            }
        };
    }
}
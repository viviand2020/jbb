/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.frontend.impl.acp.logic;

import org.jbb.frontend.impl.acp.dao.AcpCategoryRepository;
import org.jbb.frontend.impl.acp.dao.AcpElementRepository;
import org.jbb.frontend.impl.acp.dao.AcpSubcategoryRepository;
import org.jbb.frontend.impl.acp.logic.AcpCategoryFactory.AcpCategoryTuple;
import org.jbb.frontend.impl.acp.logic.AcpSubcategoryFactory.AcpElementTuple;
import org.jbb.frontend.impl.acp.model.AcpCategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
public class AcpComponentsAutoCreator {
    private final AcpCategoryFactory acpCategoryFactory;
    private final AcpSubcategoryFactory acpSubcategoryFactory;
    private final AcpCategoryRepository acpCategoryRepository;
    private final AcpSubcategoryRepository acpSubcategoryRepository;
    private final AcpElementRepository acpElementRepository;

    @Autowired
    public AcpComponentsAutoCreator(AcpCategoryFactory acpCategoryFactory,
                                    AcpSubcategoryFactory acpSubcategoryFactory,
                                    AcpCategoryRepository acpCategoryRepository,
                                    AcpSubcategoryRepository acpSubcategoryRepository,
                                    AcpElementRepository acpElementRepository) {
        this.acpCategoryFactory = acpCategoryFactory;
        this.acpSubcategoryFactory = acpSubcategoryFactory;
        this.acpCategoryRepository = acpCategoryRepository;
        this.acpSubcategoryRepository = acpSubcategoryRepository;
        this.acpElementRepository = acpElementRepository;
    }

    @PostConstruct
    @Transactional
    public void buildAcp() {
        if (acpIsEmpty()) {
            AcpCategoryEntity generalCategory = acpCategoryFactory.createWithSubcategories(
                    new AcpCategoryTuple("General", "general"),
                    acpSubcategoryFactory.createWithElements(
                            "Board configuration",
                            new AcpElementTuple("Board settings", "board"),
                            new AcpElementTuple("Member registration settings", "registration")
                    ),
                    acpSubcategoryFactory.createWithElements(
                            "Server configuration",
                            new AcpElementTuple("Logging & debugging settings", "logging")
                    )
            );

            AcpCategoryEntity membersCategory = acpCategoryFactory.createWithSubcategories(
                    new AcpCategoryTuple("Members and groups", "members"),
                    acpSubcategoryFactory.createWithElements(
                            "Members",
                            new AcpElementTuple("Manage members", "manage"),
                            new AcpElementTuple("Create new member", "create")
                    )
            );

            AcpCategoryEntity systemCategory = acpCategoryFactory.createWithSubcategories(
                    new AcpCategoryTuple("System", "system"),
                    acpSubcategoryFactory.createWithElements(
                            "Database",
                            new AcpElementTuple("Database settings", "database")
                    )
            );

            acpCategoryRepository.save(generalCategory);
            acpCategoryRepository.save(membersCategory);
            acpCategoryRepository.save(systemCategory);
        }
    }

    private boolean acpIsEmpty() {
        return acpCategoryRepository.count() == 0 &&
                acpSubcategoryRepository.count() == 0 &&
                acpElementRepository.count() == 0;
    }
}
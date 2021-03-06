/*
 * Copyright (C) 2017 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.frontend.impl.faq;

import com.google.common.collect.Lists;

import com.github.zafarkhaja.semver.Version;

import org.jbb.frontend.impl.faq.dao.FaqCategoryRepository;
import org.jbb.frontend.impl.faq.model.FaqCategoryEntity;
import org.jbb.frontend.impl.faq.model.FaqEntryEntity;
import org.jbb.install.InstallUpdateAction;
import org.jbb.install.InstallationData;
import org.jbb.install.JbbVersions;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FaqInstallAction implements InstallUpdateAction {

    private final FaqCategoryRepository faqCategoryRepository;

    @Override
    public Version fromVersion() {
        return JbbVersions.VERSION_0_9_0;
    }

    @Override
    public void install(InstallationData installationData) {

        FaqEntryEntity firstFaqEntry = FaqEntryEntity.builder()
                .question("What is jBB?")
                .answer("jBB is a bulletin board software")
                .position(1)
                .build();

        FaqEntryEntity secondFaqEntry = FaqEntryEntity.builder()
                .question("How can I get support?")
                .answer("Visit https://github.com/jbb-project/jbb")
                .position(2)
                .build();

        FaqCategoryEntity firstCategory = FaqCategoryEntity.builder()
                .name("General")
                .entries(Lists.newArrayList(firstFaqEntry, secondFaqEntry))
                .position(1)
                .build();

        firstFaqEntry.setCategory(firstCategory);
        secondFaqEntry.setCategory(firstCategory);

        faqCategoryRepository.save(firstCategory);
    }

}

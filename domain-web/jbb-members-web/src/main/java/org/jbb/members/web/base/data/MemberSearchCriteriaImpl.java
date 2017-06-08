/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.members.web.base.data;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.jbb.lib.commons.vo.Email;
import org.jbb.lib.commons.vo.Username;
import org.jbb.members.api.base.DisplayedName;
import org.jbb.members.api.base.MemberSearchCriteria;
import org.jbb.members.api.base.MemberSearchJoinDateFormatException;
import org.jbb.members.web.base.form.SearchMemberForm;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemberSearchCriteriaImpl implements MemberSearchCriteria {
    private Optional<Username> username;
    private Optional<DisplayedName> displayedName;
    private Optional<Email> email;
    private Optional<JoinCriteria> joinCriteria;
    private SortBy sortBy;

    public MemberSearchCriteriaImpl(SearchMemberForm form) {
        username = Optional.ofNullable(Username.builder().value(form.getUsername()).build());
        displayedName = Optional.ofNullable(DisplayedName.builder().value(form.getDisplayedName()).build());
        email = Optional.ofNullable(Email.builder().value(form.getEmail()).build());

        JoinCriteria criteria = new JoinCriteria() {
            @Override
            public LocalDate getJoinDate() {
                try {
                    return StringUtils.isNotBlank(form.getJoinedDate()) ?
                            LocalDate.parse(form.getJoinedDate(),
                                    DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
                } catch (DateTimeParseException e) {
                    log.trace("Date time parsing error", e);
                    throw new MemberSearchJoinDateFormatException();
                }
            }

            @Override
            public JoinMoment getJoinMoment() {
                return EnumUtils.getEnum(JoinMoment.class, form.getJoinedMoment());
            }
        };

        joinCriteria = Optional.ofNullable(criteria);

        sortBy = new SortBy() {
            @Override
            public SortColumn sortColumn() {
                return EnumUtils.getEnum(SortColumn.class, form.getSortByField());
            }

            @Override
            public SortingOrder sortingOrder() {
                return EnumUtils.getEnum(SortingOrder.class, form.getSortDirection());
            }
        };
    }

    @Override
    public Optional<Username> withUsername() {
        return username;
    }

    @Override
    public Optional<DisplayedName> withDisplayedName() {
        return displayedName;
    }

    @Override
    public Optional<Email> withEmail() {
        return email;
    }

    @Override
    public Optional<JoinCriteria> withJoinCriteria() {
        return joinCriteria;
    }

    @Override
    public SortBy sortBy() {
        return sortBy;
    }
}
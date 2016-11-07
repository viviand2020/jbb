/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.members.impl.base.logic.search;

import org.jbb.members.api.data.MemberSearchCriteria;
import org.jbb.members.api.data.MemberSearchCriteria.SortBy;
import org.jbb.members.api.data.MemberSearchCriteria.SortColumn;
import org.jbb.members.api.data.MemberSearchCriteria.SortingOrder;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortCreator { //TODO - move to jbb-lib-db-tier?
    public Sort create(MemberSearchCriteria criteria) {
        SortBy sortBy = criteria.sortBy();

        Sort.Direction direction = null;
        if (sortBy.sortingOrder().equals(SortingOrder.ASC)) {
            direction = Sort.Direction.ASC;
        } else {
            direction = Sort.Direction.DESC;
        }

        return new Sort(new Sort.Order(direction, resolveColumnName(sortBy.sortColumn())));
    }

    private String resolveColumnName(SortColumn sortColumn) {
        switch (sortColumn) {
            case USERNAME:
                return "username";
            case DISPLAYED_NAME:
                return "displayedName";
            case EMAIL:
                return "email";
            case JOIN_DATE:
                return "registrationMetaData.joinDateTime";
        }
        return null;
    }
}

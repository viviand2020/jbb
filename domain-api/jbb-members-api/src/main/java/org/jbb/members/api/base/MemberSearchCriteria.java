/*
 * Copyright (C) 2017 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.members.api.base;

import java.time.LocalDate;
import java.util.Optional;
import org.jbb.lib.commons.vo.Email;
import org.jbb.lib.commons.vo.Username;

@Deprecated
public interface MemberSearchCriteria {

    Optional<Username> withUsername();

    Optional<DisplayedName> withDisplayedName();

    Optional<Email> withEmail();

    Optional<JoinCriteria> withJoinCriteria();

    SortBy sortBy();


    enum JoinMoment {
        BEFORE, THAT_DAY, AFTER
    }

    enum SortColumn {
        USERNAME, DISPLAYED_NAME, EMAIL, JOIN_DATE
    }

    enum SortingOrder {
        ASC, DESC
    }

    interface JoinCriteria {

        LocalDate getJoinDate();

        JoinMoment getJoinMoment();
    }

    interface SortBy {

        SortColumn sortColumn();

        SortingOrder sortingOrder();
    }
}

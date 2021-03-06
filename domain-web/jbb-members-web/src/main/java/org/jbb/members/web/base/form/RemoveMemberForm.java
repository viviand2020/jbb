/*
 * Copyright (C) 2017 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.members.web.base.form;

import org.apache.commons.lang3.Validate;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoveMemberForm {
    @NotNull
    private Long id;

    public RemoveMemberForm() {
        id = 0L;
    }

    public RemoveMemberForm(Long id) { //NOSONAR
        Validate.notNull(id);
        this.id = id;
    }


}

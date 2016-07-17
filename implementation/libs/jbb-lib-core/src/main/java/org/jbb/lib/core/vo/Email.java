/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.lib.core.vo;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Tolerate;

@Getter
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "value")
public class Email implements Serializable {
    @org.hibernate.validator.constraints.Email
    @NotEmpty
    @Length(min = 3, max = 254)
    String value;

    @Tolerate
    Email() {
        // for JPA
    }

    @Override
    public String toString() {
        return value;
    }
}

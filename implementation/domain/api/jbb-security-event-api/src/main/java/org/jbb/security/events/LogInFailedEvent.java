/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.security.events;

import org.jbb.lib.eventbus.JbbEvent;

public class LogInFailedEvent extends JbbEvent {
    private final Long memberId;
    private final Integer attempt;

    public LogInFailedEvent(Long memberId, Integer attempt) {
        this.memberId = memberId;
        this.attempt = attempt;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Integer getAttempt() {
        return attempt;
    }
}

/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.system.impl;

import org.aeonbits.owner.Config;
import org.jbb.lib.properties.ModuleProperties;

@Config.HotReload(type = Config.HotReloadType.ASYNC)
@Config.Sources({"file:${jbb.home}/config/system.properties"})
public interface SystemProperties extends ModuleProperties { // NOSONAR (key names should stay)
    String STACK_TRACE_VISIBILITY_LEVEL_KEY = "stacktrace.visibility.level";
    String SESSION_INACTIVE_INTERVAL_TIME_SECONDS_KEY = "session.inactiveIntervalSeconds";

    @Key(STACK_TRACE_VISIBILITY_LEVEL_KEY)
    String stackTraceVisibilityLevel();

    @Key(SESSION_INACTIVE_INTERVAL_TIME_SECONDS_KEY)
    Integer sessionMaxInActiveTimeAsSeconds();
}
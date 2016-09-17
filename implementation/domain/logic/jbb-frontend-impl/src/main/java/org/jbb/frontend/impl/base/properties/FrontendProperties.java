/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.frontend.impl.base.properties;

import org.aeonbits.owner.Config;
import org.jbb.lib.properties.ModuleProperties;

@Config.HotReload(type = Config.HotReloadType.ASYNC)
@Config.Sources({"file:${jbb.home}/jbb-frontend.properties"})
public interface FrontendProperties extends ModuleProperties { // NOSONAR (key names should stay)
    String BOARD_NAME_KEY = "board.name";
    String STACK_TRACE_VISIBILITY_LEVEL_KEY = "stacktrace.visibility.level";

    @Key(BOARD_NAME_KEY)
    String boardName();

    @Key(STACK_TRACE_VISIBILITY_LEVEL_KEY)
    String stackTraceVisibilityLevel();
}
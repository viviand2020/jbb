/*
 * Copyright (C) 2017 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.frontend.api.faq;

import org.jbb.frontend.api.faq.model.FaqTuple;

import java.util.List;
import java.util.Map;

public interface FaqService {

    void save(List<FaqTuple> faqTupleList);

    Map<String,List<FaqTuple>> getFaqDataMap();
}

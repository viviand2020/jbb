/*
 * Copyright (C) 2017 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.security.impl.lock.dao;


import org.jbb.security.impl.lock.model.InvalidSignInAttemptEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvalidSignInAttemptRepository extends CrudRepository<InvalidSignInAttemptEntity, Long> {

    Optional<List<InvalidSignInAttemptEntity>> findByMemberID(Long memberID);

    @Query("delete from InvalidSignInAttemptEntity p WHERE p.memberID = :memberID")
    void clearInvalidSigInAttemptForSpecifyMember(@Param("memberID") Long memberID);

}

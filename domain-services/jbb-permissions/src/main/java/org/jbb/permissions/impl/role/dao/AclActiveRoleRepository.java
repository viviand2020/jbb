/*
 * Copyright (C) 2017 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.permissions.impl.role.dao;

import java.util.Optional;
import org.jbb.permissions.impl.acl.model.AclSecurityIdentityEntity;
import org.jbb.permissions.impl.role.model.AclActiveRoleEntity;
import org.jbb.permissions.impl.role.model.AclRoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AclActiveRoleRepository extends CrudRepository<AclActiveRoleEntity, Long> {

    Optional<AclActiveRoleEntity> findBySecurityIdentityAndRole(
        AclSecurityIdentityEntity securityIdentity, AclRoleEntity role);

}

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

import org.jbb.lib.commons.vo.Username;
import org.jbb.members.api.registration.MemberRegistrationAware;

import java.util.List;
import java.util.Optional;

public interface MemberService {

    List<MemberRegistrationAware> getAllMembersSortedByRegistrationDate();

    Optional<Member> getMemberWithId(Long id);

    Optional<Member> getMemberWithUsername(Username username);

    void updateProfile(Long memberId, ProfileDataToChange profileDataToChange);

    void updateAccount(Long memberId, AccountDataToChange accountDataToChange);

    List<MemberRegistrationAware> getAllMembersWithCriteria(MemberSearchCriteria criteria);

    void removeMember(Long memberId);
}
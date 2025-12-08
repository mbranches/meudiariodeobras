package com.branches.usertenant.repository;

import com.branches.usertenant.domain.UserObraPermitidaEntity;
import com.branches.usertenant.domain.UserObraPermitidaKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserObraPermitidaRepository extends JpaRepository<UserObraPermitidaEntity, UserObraPermitidaKey> {
}
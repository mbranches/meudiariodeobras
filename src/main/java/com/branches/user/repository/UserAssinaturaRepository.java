package com.branches.user.repository;

import com.branches.user.domain.UserAssinaturaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAssinaturaRepository extends JpaRepository<UserAssinaturaEntity, Long> {
    Optional<UserAssinaturaEntity> findByUserId(Long userId);
}
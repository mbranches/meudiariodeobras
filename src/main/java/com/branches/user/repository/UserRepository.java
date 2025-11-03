package com.branches.user.repository;

import com.branches.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByIdExterno(String idExterno);

    Optional<UserEntity> findByEmail(String email);
}

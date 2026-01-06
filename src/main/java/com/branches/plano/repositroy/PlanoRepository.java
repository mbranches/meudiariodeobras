package com.branches.plano.repositroy;

import com.branches.plano.domain.PlanoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlanoRepository extends JpaRepository<PlanoEntity, Long> {
    Optional<PlanoEntity> findByNome(String nome);
}

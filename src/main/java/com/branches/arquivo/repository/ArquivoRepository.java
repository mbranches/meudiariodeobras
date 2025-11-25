package com.branches.arquivo.repository;

import com.branches.arquivo.domain.ArquivoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArquivoRepository extends JpaRepository<ArquivoEntity, Long> {
}
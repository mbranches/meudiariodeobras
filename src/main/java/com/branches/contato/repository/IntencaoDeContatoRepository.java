package com.branches.contato.repository;

import com.branches.contato.entity.IntencaoDeContatoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IntencaoDeContatoRepository extends JpaRepository<IntencaoDeContatoEntity, Long> {
}
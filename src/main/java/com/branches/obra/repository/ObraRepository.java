package com.branches.obra.repository;

import com.branches.obra.domain.ObraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObraRepository extends JpaRepository<ObraEntity, Long> {
}

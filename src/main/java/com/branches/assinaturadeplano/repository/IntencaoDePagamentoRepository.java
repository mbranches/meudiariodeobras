package com.branches.assinaturadeplano.repository;

import com.branches.assinaturadeplano.domain.IntencaoDePagamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IntencaoDePagamentoRepository extends JpaRepository<IntencaoDePagamentoEntity, Long> {
    Optional<IntencaoDePagamentoEntity> findByStripeSessionId(String stripeSessionId);
}

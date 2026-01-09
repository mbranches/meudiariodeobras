package com.branches.assinaturadeplano.domain;

import com.branches.assinaturadeplano.domain.enums.AssinaturaStatus;
import com.branches.config.envers.AuditableTenantOwned;
import com.branches.plano.domain.IntencaoDePagamentoEntity;
import com.branches.plano.domain.PlanoEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AssinaturaDePlanoEntity extends AuditableTenantOwned {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plano_id")
    private PlanoEntity plano;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssinaturaStatus status;

    private LocalDate dataInicio;
    private LocalDate dataFim;

    private LocalDateTime canceladoEm;

    private String stripeSubscriptionId;

    @OneToOne
    @JoinColumn(name = "intencao_de_pagamento_id")
    private IntencaoDePagamentoEntity intencaoDePagamento;

    public void cancelar() {
        this.status = AssinaturaStatus.CANCELADO;
        this.canceladoEm = LocalDateTime.now();
    }

    public void ativar() {
        this.status = AssinaturaStatus.ATIVO;
    }
}

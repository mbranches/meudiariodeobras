package com.branches.assinatura.domain;

import com.branches.assinatura.domain.enums.AssinaturaStatus;
import com.branches.config.envers.Auditable;
import com.branches.plano.domain.PlanoEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AssinaturaEntity extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long tenantId;

    @ManyToOne
    @JoinColumn(name = "plano_id")
    private PlanoEntity plano;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssinaturaStatus status;

    private LocalDate dataInicio;
    private LocalDate dataFim;
}

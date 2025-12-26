package com.branches.atividade.domain;

import com.branches.config.envers.AuditableTenantOwned;
import com.branches.relatorio.domain.CampoPersonalizadoEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity(name = "atividade_relatorio_campo_personalizado")
public class AtividadeDeRelatorioCampoPersonalizadoEntity extends AuditableTenantOwned {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "atividade_de_relatorio_id", nullable = false)
    private AtividadeDeRelatorioEntity atividadeDeRelatorio;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "campo_personalizado_id", nullable = false)
    private CampoPersonalizadoEntity campoPersonalizado;

    public static AtividadeDeRelatorioCampoPersonalizadoEntity from(AtividadeDeRelatorioEntity atividade, CampoPersonalizadoEntity campoPersonalizadoEntity, Long tenantId) {
        return AtividadeDeRelatorioCampoPersonalizadoEntity.builder()
                .atividadeDeRelatorio(atividade)
                .campoPersonalizado(campoPersonalizadoEntity)
                .tenantId(tenantId)
                .build();
    }
}

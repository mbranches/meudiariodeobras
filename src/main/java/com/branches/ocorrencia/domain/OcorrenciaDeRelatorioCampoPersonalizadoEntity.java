package com.branches.ocorrencia.domain;

import com.branches.config.envers.AuditableTenantOwned;
import com.branches.relatorio.domain.CampoPersonalizadoEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity(name = "ocorrencia_relatorio_campo_personalizado")
public class OcorrenciaDeRelatorioCampoPersonalizadoEntity extends AuditableTenantOwned {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ocorrencia_de_relatorio_id", nullable = false)
    private OcorrenciaDeRelatorioEntity ocorrenciaDeRelatorio;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "campo_personalizado_id", nullable = false)
    private CampoPersonalizadoEntity campoPersonalizado;

    public static OcorrenciaDeRelatorioCampoPersonalizadoEntity from(OcorrenciaDeRelatorioEntity ocorrencia, CampoPersonalizadoEntity campoPersonalizadoEntity, Long tenantId) {
        return OcorrenciaDeRelatorioCampoPersonalizadoEntity.builder()
                .ocorrenciaDeRelatorio(ocorrencia)
                .campoPersonalizado(campoPersonalizadoEntity)
                .tenantId(tenantId)
                .build();
    }
}

package com.branches.ocorrencia.domain;

import com.branches.atividade.domain.AtividadeDeRelatorioEntity;
import com.branches.config.envers.AuditableTenantOwned;
import com.branches.relatorio.domain.RelatorioEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;
import java.util.List;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OcorrenciaDeRelatorioEntity extends AuditableTenantOwned {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "relatorio_id", nullable = false)
    private RelatorioEntity relatorio;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "ocorrencia_relatorio_tipos_de_ocorrencia",
            joinColumns = @JoinColumn(name = "ocorrencia_relatorio_id"),
            inverseJoinColumns = @JoinColumn(name = "tipo_de_ocorrencia_id")
    )
    private List<TipoDeOcorrenciaEntity> tiposDeOcorrencia;

    @Column(nullable = false)
    private String descricao;

    private LocalTime horaInicio;
    private LocalTime horaFim;
    private LocalTime totalHoras;

    @ManyToOne
    @JoinColumn(name = "atividade_vinculada_id")
    private AtividadeDeRelatorioEntity atividadeVinculada;

    @OneToMany(mappedBy = "ocorrenciaDeRelatorio", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<OcorrenciaDeRelatorioCampoPersonalizadoEntity> camposPersonalizados;
}

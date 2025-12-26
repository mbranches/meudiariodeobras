package com.branches.maodeobra.domain;

import com.branches.atividade.domain.AtividadeDeRelatorioEntity;
import com.branches.config.envers.AuditableTenantOwned;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MaoDeObraDeAtividadeDeRelatorioEntity extends AuditableTenantOwned {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mao_de_obra_id", nullable = false)
    private MaoDeObraEntity maoDeObra;

    @Column(length = 100, nullable = false)
    private String funcao;

    @ManyToOne
    @JoinColumn(name = "atividade_de_relatorio_id", nullable = false)
    private AtividadeDeRelatorioEntity atividadeDeRelatorio;
}

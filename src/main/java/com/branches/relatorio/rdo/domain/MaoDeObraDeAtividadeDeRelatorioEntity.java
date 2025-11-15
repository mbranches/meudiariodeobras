package com.branches.relatorio.rdo.domain;

import com.branches.relatorio.maodeobra.domain.MaoDeObraEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MaoDeObraDeAtividadeDeRelatorioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mao_de_obra_id", nullable = false)
    private MaoDeObraEntity maoDeObra;

    @Column(length = 100, nullable = false)
    private String funcao;

    private LocalTime horaInicio;
    private LocalTime horaFim;
    private LocalTime horasIntervalo;
    private LocalTime horasTrabalhadas;

    @ManyToOne
    @JoinColumn(name = "atividade_de_relatorio_id", nullable = false)
    private AtividadeDeRelatorioEntity atividadeDeRelatorio;
}

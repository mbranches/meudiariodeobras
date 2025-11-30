package com.branches.obra.domain;

import com.branches.relatorio.domain.AssinaturaDeRelatorioEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ConfiguracaoDeAssinaturaDeRelatorioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeAssinante;

    @OneToMany(mappedBy = "configuracao", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<AssinaturaDeRelatorioEntity> assinaturasDeRelatorio;

    @ManyToOne
    @JoinColumn(name = "configuracao_relatorios_id", nullable = false)
    private ConfiguracaoRelatoriosEntity configuracaoRelatorios;
}

package com.branches.relatorio.domain;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ArquivoDeRelatorioDeUsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "relatorio_id")
    private Long relatorioId;

    @Column(nullable = false, name = "user_id")
    private Long userId;

    @Column(nullable = false)
    private String arquivoUrl;
}

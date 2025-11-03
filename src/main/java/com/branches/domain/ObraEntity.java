package com.branches.domain;

import com.branches.domain.enums.StatusObra;
import com.branches.domain.enums.TipoContratoDeObra;
import com.branches.domain.enums.TipoMaoDeObra;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ObraEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String nome;
    @Column(length = 100)
    private String responsavel;

    @Column(length = 100)
    private String contratante;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoContratoDeObra tipoContrato;

    @Column(nullable = false)
    private LocalDate dataInicio;
    @Column(nullable = false)
    private LocalDate dataPrevistaFim;

    @Column(length = 100)
    private String numeroContrato;

    @Column(length = 200)
    private String endereco;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @Column(columnDefinition = "TEXT")
    private String capaUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusObra status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoMaoDeObra tipoMaoDeObra;
    @ManyToOne
    @JoinColumn(name = "grupo_de_obra_id")
    private GrupoDeObraEntity grupoDeObra;

    private Boolean ativo;
}

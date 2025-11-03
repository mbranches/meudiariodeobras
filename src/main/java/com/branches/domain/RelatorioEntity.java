package com.branches.domain;

import com.branches.domain.enums.StatusRelatorio;
import com.branches.domain.enums.TipoMaoDeObra;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RelatorioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDate data;
    @Column(nullable = false)
    private Integer numero;
    @Column(nullable = false)
    private Integer prazoContratualObra;
    @Column(nullable = false)
    private Integer prazoDecorridoObra;
    @Column(nullable = false)
    private Integer prazoPraVencerObra;
    private String pdfUrl;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusRelatorio status;
    @ManyToOne
    @JoinColumn(name = "obra_id", nullable = false)
    private ObraEntity obra;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMaoDeObra tipoMaoDeObra;
    @Column(length = 10)
    private String indiciePluviometrico;
    //todo: adicionar videos e anexos
}

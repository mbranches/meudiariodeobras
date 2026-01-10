package com.branches.arquivo.domain;

import com.branches.arquivo.domain.enums.TipoArquivo;
import com.branches.config.envers.AuditableTenantOwned;
import com.branches.relatorio.domain.RelatorioEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ArquivoEntity extends AuditableTenantOwned {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeArquivo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String url;

    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoArquivo tipoArquivo;

    @ManyToOne
    @JoinColumn(name = "relatorio_id")
    private RelatorioEntity relatorio;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal tamanhoEmMb;

    @Column(precision = 10, scale = 2)
    private BigDecimal segundosDeDuracao;

    public boolean getIsFoto() {
        return this.tipoArquivo == TipoArquivo.FOTO;
    }

    public boolean getIsVideo() {
        return this.tipoArquivo == TipoArquivo.VIDEO;
    }

    public String getFormattedSegundosDeDuracao() {
        if (segundosDeDuracao == null) {
            return "00:00";
        }

        int totalSeconds = segundosDeDuracao.setScale(0, RoundingMode.HALF_UP).intValue();
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }
}

package com.branches.relatorio.maodeobra.domain;

import com.branches.config.envers.AuditableTenantOwned;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class GrupoMaoDeObraEntity extends AuditableTenantOwned {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false)
    private String descricao;
    @Column(nullable = false)
    private Boolean ativo;

    public boolean isAtivo() {
        return Boolean.TRUE.equals(ativo);
    }
}

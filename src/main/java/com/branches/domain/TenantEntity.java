package com.branches.domain;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TenantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100)
    private String nomeFantasia;
    @Column(length = 100)
    private String razaoSocial;
    @Column(length = 14)
    private String cnpj;
    @Column(columnDefinition = "TEXT")
    private String logoUrl;
    @Column(length = 13)
    private String telefone;
    private Boolean ativo;
}

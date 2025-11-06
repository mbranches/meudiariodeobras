package com.branches.user.domain;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserTenantAuthoritiesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @Embedded
    @Column(columnDefinition = "TEXT", nullable = false)
    private PermissionsRelatorio relatorios;


    @Embedded
    @Column(columnDefinition = "TEXT", nullable = false)
    private PermissionsDefault obras;

    @Embedded
    @Column(columnDefinition = "TEXT", nullable = false)
    private PermissionsCadastro cadastros;

    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            @JoinColumn(name = "tenant_id", referencedColumnName = "tenant_id")
    })
    private UserTenantEntity userTenant;
}

package com.branches.user.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class PermissionsCadastro {
    private Boolean grupoDeObras;
    private Boolean equipamentos;
    private Boolean maoDeObra;
    private Boolean tiposDeOcorrencia;
}

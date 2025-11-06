package com.branches.user.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class PermissionsCadastro {
    private Boolean canCreateGrupoDeObras;
    private Boolean canCreateEquipamentos;
    private Boolean canCreateMaoDeObra;
    private Boolean canCreateTiposDeOcorrencia;
}

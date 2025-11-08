package com.branches.user.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class PermissionsCadastro {
    private Boolean canCreateAndEditGrupoDeObras;
    private Boolean canCreateAndEditEquipamentos;
    private Boolean canCreateAndEditMaoDeObra;
    private Boolean canCreateAndEditTiposDeOcorrencia;
}

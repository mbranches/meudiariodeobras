package com.branches.user.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PermissionsCadastro {
    private Boolean canCreateGrupoDeObras;
    private Boolean canCreateEquipamentos;
    private Boolean canCreateMaoDeObra;
    private Boolean canCreateTiposDeOcorrencia;
}

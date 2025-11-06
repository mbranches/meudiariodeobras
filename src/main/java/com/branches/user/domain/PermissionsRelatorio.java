package com.branches.user.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionsRelatorio extends PermissionsDefault {
    private Boolean canAprovar;
}

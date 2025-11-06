package com.branches.user.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class PermissionsRelatorio extends PermissionsDefault {
    private Boolean canAprovar;
}

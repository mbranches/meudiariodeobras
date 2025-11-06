package com.branches.user.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Setter
@Getter
public class PermissionsDefault {
    private Boolean canCreateAndEdit;
    private Boolean canDelete;
}

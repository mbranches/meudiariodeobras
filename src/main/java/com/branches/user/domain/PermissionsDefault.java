package com.branches.user.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PermissionsDefault {
    private Boolean canCreateAndEdit;
    private Boolean canDelete;
}

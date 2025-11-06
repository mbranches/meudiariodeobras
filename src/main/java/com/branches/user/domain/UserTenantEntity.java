package com.branches.user.domain;

import com.branches.user.domain.enums.PerfilUserTenant;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserTenantEntity {
    @EmbeddedId
    private UserTenantKey id;

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "tenant_id", insertable = false, updatable = false)
    private Long tenantId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PerfilUserTenant perfil;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "userTenant")
    private Set<UserObraPermitidaEntity> userObraPermitidaEntities;

    @Convert(converter = UserTenantAuthoritiesConverter.class)
    @Column(nullable = false, columnDefinition = "TEXT")
    private UserTenantAuthorities authorities;

    public List<Long> getObrasPermitidasIds() {
        return this.userObraPermitidaEntities.stream()
                .map(UserObraPermitidaEntity::getObraId)
                .toList();
    }

    public void setarId() {
        id = UserTenantKey.from(this.user.getId(), this.tenantId);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UserTenantEntity that)) return false;
        return Objects.equals(user, that.user) && Objects.equals(tenantId, that.tenantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, tenantId);
    }
}

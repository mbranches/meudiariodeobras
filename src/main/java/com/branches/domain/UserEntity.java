package com.branches.domain;

import com.branches.domain.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100)
    private String nome;
    @Column(length = 100)
    private String email;
    @Column(length = 100)
    private String password;
    @Column(length = 100)
    private String cargo;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(columnDefinition = "TEXT")
    private String fotoUrl;
    private Boolean ativo;

}

package com.theh.moduleuser.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.Collection;

@Table(name = "role")
@Entity @Data
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
//    @JsonIgnore
//    @ManyToMany(mappedBy = "roles")
//    private Collection<Utilisateur> users;

    @EqualsAndHashCode.Include
    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name = "roles_privileges", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "privilege_id"))
    private Collection<Privilege> privileges;

    public Role(final String name) {
        super();
        this.name = name;
    }

}

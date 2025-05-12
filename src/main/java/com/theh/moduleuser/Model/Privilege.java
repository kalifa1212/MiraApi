package com.theh.moduleuser.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Table(name = "privilege")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @EqualsAndHashCode.Include
    @Column(unique = true, nullable = false)
    private String name;

//    @ManyToMany(mappedBy = "privileges")
//    private Collection<Role> roles;

    public Privilege(final String name) {
        super();
        this.name = name;
    }
}

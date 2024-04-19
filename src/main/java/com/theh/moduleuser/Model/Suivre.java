package com.theh.moduleuser.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@Table(name="Suivre_utilisateur_mosque")
@EqualsAndHashCode(callSuper=true)
@Data @NoArgsConstructor @AllArgsConstructor
public class Suivre extends AbstractEntity{

    private static final long serialVersionUID = 1L;

    @Column(name="idimamidimam_suivie")
    private Integer idimamsuivie;

    @Column(name="utilisateur")
    private Integer utilisateur;

    @Column(name="mosque")
    private Integer mosque;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur user;

    @ManyToOne
    @JoinColumn(name = "mosque_id")
    private Mosque mosq;
}

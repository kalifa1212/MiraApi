package com.theh.moduleuser.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Table(name = "utilisateur")
@Entity @EqualsAndHashCode(callSuper=true)
@Data @NoArgsConstructor @AllArgsConstructor
public class Utilisateur extends AbstractEntity{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(length = 50,name = "email")
    private String email;

    @Column(length = 60,name="motdepasse")
    private String motDePasse;

    @Column(name="datedenaissance")
    private Date dateDeNaissance;

    @Column(name="photo")
    private String photo;

    @Lob
    private byte[] imagedata;

    @Column(name="typecompte")
    private String typecompte;

    @Column(length = 15)
    private String phone;

    @Column(name="imageurl")
    private String imageUrl;

    @Column(name="secret")
    private String secret;

    private boolean enabled;
    private boolean isLocked;
    private boolean isUsingMfa;
    private boolean isUsing2FA;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    @ManyToOne
    private Localisation localisation;

    // TODO Impl suivre
//    @ManyToMany
//    @JoinTable(name = "utilisateur_mosquee_suivie",
//            joinColumns = @JoinColumn(name = "utilisateur_id"),
//            inverseJoinColumns = @JoinColumn(name = "mosquée_id"))
//    private Set<Mosque> followedMosques = new HashSet<>();

    // test
    @OneToMany(mappedBy = "user")
    private Set<Suivre> followedMosques;
    // ...

//    public void addMosqueSuivie(Mosque mosque) {
//
//        followedMosques.add(mosque);
//        //mosque.addFollower(utilisateur);
//    }
//
//    public void removeMosqueSuivie(Mosque mosque) {
//        followedMosques.remove(mosque);
//        mosque.removeFollower(this);
//    }
}

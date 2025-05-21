package com.theh.moduleuser.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Table(name = "utilisateur")
@Entity @EqualsAndHashCode(callSuper=true)
@NoArgsConstructor @AllArgsConstructor
public class Utilisateur extends AbstractEntity{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Column(name = "nom")
    private String nom;
    
    private String refreshToken;

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
    private TypeCompte typecompte;

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

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    @JsonIgnore
    @ManyToOne
    private Localisation localisation;

    // TODO follow implementation
    @ManyToMany
    @JoinTable(
            name = "user_mosque_follow",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "mosque_id")
    )
    private Set<Mosque> followedMosques= new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_user_follow",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    private Set<Utilisateur> followingUsers= new HashSet<>();
    // TODO new
    // 🔁 LIKE de mosquées
    @ManyToMany
    @JoinTable(
            name = "user_mosque_like",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "mosque_id")
    )
    private Set<Mosque> likedMosques = new HashSet<>();
    // 🔁 LIKE de predication
    @ManyToMany
    @JoinTable(
            name = "user_predication_like",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "predication_id")
    )
    private Set<Predication> likedPredications = new HashSet<>();

    // 🔁 FAVORIS de mosquées
    @ManyToMany
    @JoinTable(
            name = "user_mosque_favorite",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "mosque_id")
    )
    private Set<Mosque> favoriteMosques = new HashSet<>();

}

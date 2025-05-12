package com.theh.moduleuser.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.theh.moduleuser.Model.*;
import com.theh.moduleuser.Repository.MosqueRepository;
import com.theh.moduleuser.Repository.SuivreRepository;
import com.theh.moduleuser.Repository.UtilisateurRepository;
import com.theh.moduleuser.Services.MosqueService;
import com.theh.moduleuser.Services.UtilisateurService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.*;
import java.util.stream.Collectors;

@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class UtilisateurDto {
    private Integer id;
    private String refreshToken;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateDeNaissance;
    private String photo;
    private TypeCompte typecompte;
    private String phone;
    private String imageUrl;
    private String secret;
    private byte[] imagedata;
    private LocalisationDto localisation;

    private boolean enabled;
    private boolean isLocked;
    private boolean isUsingMfa;
    private boolean isUsing2FA;
    //@JsonIgnore
   // @JsonManagedReference
    private Collection<RoleDto> roles;

    // TODO follow implementation

    private Set<Integer> followedMosquesId= new HashSet<>();
    private Set<Integer> followingUsersId= new HashSet<>();

    public static UtilisateurDto fromEntity(Utilisateur utilisateur) {
        if(utilisateur==null) {
            return null;
        }
        return UtilisateurDto.builder()
                .id(utilisateur.getId())
                .followedMosquesId(utilisateur.getFollowedMosques().stream()
                        .map(mosque -> mosque.getId()).collect(Collectors.toSet()))
                .followingUsersId(utilisateur.getFollowingUsers().stream()
                        .map(util->util.getId() ).collect(Collectors.toSet()))
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .email(utilisateur.getEmail())
                .motDePasse(utilisateur.getMotDePasse())
                .dateDeNaissance(utilisateur.getDateDeNaissance())
                .localisation(LocalisationDto.fromEntity(utilisateur.getLocalisation()))
                .imageUrl(utilisateur.getImageUrl())
                .typecompte(utilisateur.getTypecompte())
                .imagedata(utilisateur.getImagedata())
                .roles(

                        utilisateur.getRoles() != null ?
                                utilisateur.getRoles().stream()
                                        .map(RoleDto::fromEntity).collect(Collectors.toList()): null
                )
                .build();
    }
    public static Utilisateur toEntity(UtilisateurDto utilisateurDto) {
        if(utilisateurDto==null) {
            return null;
        }
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(utilisateurDto.getId());
        utilisateur.setNom(utilisateurDto.getNom());
        utilisateur.setPrenom(utilisateurDto.getPrenom());
        utilisateur.setEmail(utilisateurDto.getEmail());
        utilisateur.setImageUrl(utilisateurDto.getImageUrl());
        utilisateur.setMotDePasse(utilisateurDto.getMotDePasse());
        utilisateur.setDateDeNaissance(utilisateurDto.getDateDeNaissance());
        utilisateur.setTypecompte(utilisateurDto.getTypecompte());
        utilisateur.setImagedata(utilisateurDto.getImagedata());
       // utilisateur.setLocalisation(LocalisationDto.toEntity(utilisateurDto.getLocalisation()));
        // Relations sécurisées (éviter NPE)
        if (utilisateurDto.getLocalisation() != null) {
            utilisateur.setLocalisation(LocalisationDto.toEntity(utilisateurDto.getLocalisation()));
        }

        if (utilisateurDto.getRoles() != null) {
            utilisateur.setRoles(utilisateurDto.getRoles().stream()
                    .map(RoleDto::toEntity)
                    .collect(Collectors.toList()));
        }
        //utilisateur.setRoles(utilisateurDto.getRoles().stream().map(RoleDto::toEntity).collect(Collectors.toList()));
        utilisateur.setRefreshToken(utilisateurDto.getRefreshToken());
        return utilisateur;
    }
}
// TODO solution de serialisation  indique a jpa de ne listé que l'id de l'element
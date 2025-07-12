package com.theh.moduleuser.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.theh.moduleuser.Model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private Set<MosqueDto> favoriteMosques = new HashSet<>();
    private Set<MosqueDto> likedMosques = new HashSet<>();

    private Set<PredicationDto> likedPredications = new HashSet<>();

    public static UtilisateurDto fromEntity(Utilisateur utilisateur) {
        if(utilisateur==null) {
            return null;
        }
        return UtilisateurDto.builder()
                .id(utilisateur.getId())
                .likedPredications(utilisateur.getLikedPredications().stream()
                        .map(PredicationDto::fromEntity)
                        .collect(Collectors.toSet()))
                .favoriteMosques(utilisateur.getFavoriteMosques()
                        .stream()
                        .map(MosqueDto::fromEntity)
                        .collect(Collectors.toSet()))
                .likedMosques(utilisateur.getLikedMosques()
                        .stream()
                        .map(MosqueDto::fromEntity)
                        .collect(Collectors.toSet()))
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
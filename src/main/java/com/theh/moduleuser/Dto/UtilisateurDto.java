package com.theh.moduleuser.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.theh.moduleuser.Model.Mosque;
import com.theh.moduleuser.Model.Role;
import com.theh.moduleuser.Model.Utilisateur;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class UtilisateurDto {
    private Integer id;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateDeNaissance;
    private String photo;
    private String typecompte;
    private String phone;
    private String imageUrl;
    private String secret;
    private byte[] imagedata;
    private LocalisationDto localisation;

    private boolean enabled;
    private boolean isLocked;
    private boolean isUsingMfa;
    private boolean isUsing2FA;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<RoleDto> roles;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<Mosque> followedMosques;

    public static UtilisateurDto fromEntity(Utilisateur utilisateur) {
        if(utilisateur==null) {
            return null;
        }
        return UtilisateurDto.builder()
                .id(utilisateur.getId())
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .email(utilisateur.getEmail())
                .motDePasse(utilisateur.getMotDePasse())
                .dateDeNaissance(utilisateur.getDateDeNaissance())
                .localisation(LocalisationDto.fromEntity(utilisateur.getLocalisation()))
                .imageUrl(utilisateur.getImageUrl())
                .typecompte(utilisateur.getTypecompte())
                .imagedata(utilisateur.getImagedata())
                .followedMosques(utilisateur.getFollowedMosques())
                .roles(
                                //utilisateur.getRoles().stream().toList()
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
        utilisateur.setLocalisation(LocalisationDto.toEntity(utilisateurDto.getLocalisation()));
        utilisateur.setRoles(utilisateurDto.getRoles().stream().map(RoleDto::toEntity).collect(Collectors.toList()));
        utilisateur.setFollowedMosques(utilisateurDto.getFollowedMosques());
        return utilisateur;
    }

//    public String toString() {
//        final StringBuilder builder = new StringBuilder();
//        builder.append("UtilisateurDto [firstName=")
//                .append(nom)
//                .append(", lastName=")
//                .append(prenom)
//                .append(", email=")
//                .append(email)
//                .append(", isUsing2FA=")
//                .append(isUsing2FA)
//                .append(", role=")
//                .append(roles).append("]");
//        return builder.toString();
//    }
}

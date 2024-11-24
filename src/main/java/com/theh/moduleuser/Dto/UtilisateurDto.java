package com.theh.moduleuser.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.theh.moduleuser.Model.Mosque;
import com.theh.moduleuser.Model.Role;
import com.theh.moduleuser.Model.Suivre;
import com.theh.moduleuser.Model.Utilisateur;
import com.theh.moduleuser.Repository.MosqueRepository;
import com.theh.moduleuser.Repository.SuivreRepository;
import com.theh.moduleuser.Repository.UtilisateurRepository;
import com.theh.moduleuser.Services.MosqueService;
import com.theh.moduleuser.Services.UtilisateurService;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
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

    private List<SuivreDto> followedMosques;
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    private Set<Mosque> followedMosques  = new HashSet<>();

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
                .followedMosques(utilisateur.getFollowedMosques().stream().map(SuivreDto::fromEntity).collect(Collectors.toList()))
                //.followedMosques(utilisateur.getFollowedMosques().stream().map(Mosque::getId).collect(Collectors.toSet()))
                //.followedMosques(utilisateur.getFollowedMosques().stream().map(SuivreDto::fromEntity).collect(Collectors.toSet()))
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
        //retourneMosque(utilisateurDto.getFollowedMosques());
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
        //utilisateur.setFollowedMosques(retourneMosque(utilisateurDto.getFollowedMosques()));
        utilisateur.setFollowedMosques( utilisateurDto.getFollowedMosques().stream().map(SuivreDto::toEntity).collect(Collectors.toSet()));
        return utilisateur;
    }
}

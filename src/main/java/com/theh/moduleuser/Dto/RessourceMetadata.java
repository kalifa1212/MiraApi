package com.theh.moduleuser.Dto;

import lombok.Data;

@Data
public class RessourceMetadata {
    private String mimeType; // pour stocker le type de fichier (video/mp4, etc.)
    private Long size;       // taille en octets
    private String resolution; // ex: "1920x1080"
    private Long duration;  // Durée de la vidéo en secondes
    private String thumbnailUrl; // lien de l'image si video
}

package com.theh.moduleuser.Dto;

import com.theh.moduleuser.Model.Suivre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class SuivreDto {

    private Integer id;

    private Integer idimamsuivie;

    private Integer utilisateur;

    private Integer mosque;

    public static SuivreDto fromEntity(Suivre suivre) {
        if(suivre==null) {
            return null;
        }
        return SuivreDto.builder()
                .id(suivre.getId())
                .idimamsuivie(suivre.getIdimamsuivie())
                .utilisateur(suivre.getUtilisateur())
                .mosque(suivre.getMosque())
                .build();
    }

    public static Suivre toEntity(SuivreDto suivreDto) {
        if(suivreDto==null) {
            return null;
        }
        Suivre suivre = new Suivre();
        suivre.setId(suivreDto.getId());
        suivre.setUtilisateur(suivreDto.getUtilisateur());
        suivre.setMosque(suivreDto.getMosque());
        suivre.setIdimamsuivie(suivreDto.getIdimamsuivie());
        return suivre;
    }
}

package com.theh.moduleuser.Dto;

import com.theh.moduleuser.Model.Ville;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VilleDto {
    private Integer id;
    private String name;
    private PaysDto country;

    public static VilleDto fromEntity(Ville ville) {
        if (ville == null) {
            return null;
        }

        return VilleDto.builder()
                .id(ville.getId())
                .name(ville.getName())
                .country(PaysDto.fromEntity(ville.getPays()))
                .build();
    }

    public static Ville toEntity(VilleDto villeDto) {
        if (villeDto == null) {
            return null;
        }

        Ville ville = new Ville();
        ville.setId(villeDto.getId());
        ville.setName(villeDto.getName());
        ville.setPays(PaysDto.toEntity(villeDto.getCountry()));

        return ville;
    }

}

package com.theh.moduleuser.Dto;

import com.theh.moduleuser.Model.Pays;
import com.theh.moduleuser.Model.Ville;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaysDto {

    private Integer id;
    private String name;
    private String code;

    public static PaysDto fromEntity(Pays pays) {
        if (pays == null) {
            return null;
        }

        return PaysDto.builder()
                .id(pays.getId())
                .name(pays.getName())
                .code(pays.getCode())
                .build();
    }

    public static Pays toEntity(PaysDto paysDto) {
        if (paysDto == null) {
            return null;
        }

        Pays pays = new Pays();
        pays.setId(paysDto.getId());
        pays.setName(paysDto.getName());
        pays.setCode(paysDto.getCode());

        return pays;
    }
}

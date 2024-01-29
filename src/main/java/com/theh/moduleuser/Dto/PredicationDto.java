package com.theh.moduleuser.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.theh.moduleuser.Model.Predication;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class PredicationDto {

    private Integer id;

	@JsonFormat(pattern="yyyy-MM-dd")
	private Date date;

	private String theme;

	private String fichier;

	// type (sermon ou predication)
	private String type;

	private Integer idImam;

	private Integer idMosque;

	private String  info;

	private String type_doc;

    public static PredicationDto fromEntity(Predication predication) {
		if(predication==null) {
			return null;
		}
		return PredicationDto.builder()
				.id(predication.getId())
				.date(predication.getDate())
				.theme(predication.getTheme())
				//.duree(predication.getDuree())
				.fichier(predication.getFichier())
                .type(predication.getType())
                .idImam(predication.getIdImam())
                .idMosque(predication.getIdMosque())
				.info(predication.getInfo())
				.type_doc(predication.getType_doc())
				.build();
	}
	
	public static Predication toEntity(PredicationDto predicationDto) {
		if(predicationDto==null) {
			return null;
		}
		Predication predication = new Predication();
		predication.setId(predicationDto.getId());
		predication.setDate(predicationDto.getDate());
		//predication.setDuree(predicationDto.getDuree());
		predication.setFichier(predicationDto.getFichier());
		predication.setTheme(predicationDto.getTheme());
		predication.setType(predicationDto.getType());
		predication.setType_doc(predicationDto.getType_doc());
		predication.setIdImam(predicationDto.getIdImam());
		predication.setIdMosque(predicationDto.getIdMosque());
		predication.setInfo(predicationDto.getInfo());
        return predication;
	}
}

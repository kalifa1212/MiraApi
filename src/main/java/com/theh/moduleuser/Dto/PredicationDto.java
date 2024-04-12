package com.theh.moduleuser.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.theh.moduleuser.Model.Predication;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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

	@JsonFormat(pattern="HH:mm:ss")
	private Date heure;

	@JsonFormat(pattern="HH:mm:ss")
	private Date duree;

	private String type;

	private String  nomImam;

	private String  nomMosque;

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
				.duree(predication.getDuree())
				.fichier(predication.getFichier())
                .type(predication.getType())
                .idImam(predication.getIdImam())
                .idMosque(predication.getIdMosque())
				.info(predication.getInfo())
				.type_doc(predication.getType_doc())
				.nomImam(predication.getNomImam())
				.nomMosque(predication.getNomMosque())
				.heure(predication.getHeure())
				.build();
	}
	
	public static Predication toEntity(PredicationDto predicationDto) {
		if(predicationDto==null) {
			return null;
		}
		Predication predication = new Predication();
		predication.setId(predicationDto.getId());
		predication.setDate(predicationDto.getDate());
		predication.setDuree(predicationDto.getDuree());
		predication.setFichier(predicationDto.getFichier());
		predication.setTheme(predicationDto.getTheme());
		predication.setType(predicationDto.getType());
		predication.setType_doc(predicationDto.getType_doc());
		predication.setIdImam(predicationDto.getIdImam());
		predication.setIdMosque(predicationDto.getIdMosque());
		predication.setInfo(predicationDto.getInfo());
		predication.setNomImam(predicationDto.getNomImam());
		predication.setNomMosque(predicationDto.getNomMosque());
		predication.setHeure(predicationDto.getHeure());
        return predication;
	}
}

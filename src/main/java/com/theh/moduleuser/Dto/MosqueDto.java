package com.theh.moduleuser.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.theh.moduleuser.Model.Mosque;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data @Builder  @AllArgsConstructor @NoArgsConstructor
public class MosqueDto {
	
	private Integer id;

	private String nom;

	private String code;

	private int superficie;

	private String photo;

	@JsonFormat(pattern="HH:mm:ss")
	private Date balte;

	@JsonFormat(pattern="HH:mm:ss")
	private Date asr;

	@JsonFormat(pattern="HH:mm:ss")
	private Date magrib;

	@JsonFormat(pattern="HH:mm:ss")
	private Date icha;

	@JsonFormat(pattern="HH:mm:ss")
	private Date soub;

	@JsonFormat(pattern="HH:mm:ss")
	private Date zour;

	@JsonFormat(pattern="HH:mm:ss")
	private Date djouma;

	private Boolean isVendredi;

	private String quartier;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private byte[] imagedata;

	private double longitude;

	private double latitude;

	private LocalisationDto localisation;
	
	public static MosqueDto fromEntity(Mosque mosque) {
		if(mosque==null) {
			return null;
		}
		return MosqueDto.builder()
				.id(mosque.getId())
				.nom(mosque.getNom())
				.superficie(mosque.getSuperficie())
				.isVendredi(mosque.getIsVendredi())
				.photo(mosque.getPhoto())
				.code(mosque.getCode())
				.localisation(LocalisationDto.fromEntity(mosque.getLocalisation()))
				.balte(mosque.getBalte())
				.asr(mosque.getAsr())
				.magrib(mosque.getMagrib())
				.icha(mosque.getIcha())
				.soub(mosque.getSoub())
				.zour(mosque.getZour())
				.djouma(mosque.getDjouma())
				.quartier(mosque.getQuartier())
				.imagedata(mosque.getImagedata())
				.build();
	}
	
	public static Mosque toEntity(MosqueDto mosqueDto) {
		if(mosqueDto==null) {
			return null;
		}
		Mosque mosque = new Mosque();
		mosque.setId(mosqueDto.getId());
		mosque.setNom(mosqueDto.getNom());
		mosque.setIsVendredi(mosqueDto.getIsVendredi());
		mosque.setPhoto(mosqueDto.getPhoto());
		mosque.setSuperficie(mosqueDto.getSuperficie());
		mosque.setCode(mosqueDto.getCode());
		mosque.setLocalisation(LocalisationDto.toEntity(mosqueDto.getLocalisation()));
		mosque.setBalte(mosqueDto.getBalte());
		mosque.setAsr(mosqueDto.getAsr());
		mosque.setMagrib(mosqueDto.getMagrib());
		mosque.setIcha(mosqueDto.getIcha());
		mosque.setSoub(mosqueDto.getSoub());
		mosque.setZour(mosqueDto.getZour());
		mosque.setDjouma(mosqueDto.getDjouma());
		mosque.setQuartier(mosqueDto.getQuartier());
		mosque.setImagedata(mosqueDto.getImagedata());
		return mosque;
	}
}

package com.theh.moduleuser.Dto;

import com.theh.moduleuser.Model.Localisation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class LocalisationDto {

	private Integer id;

	private String ville;

	private String pays;

	private String description;
	
	public static LocalisationDto fromEntity(Localisation localisation) {
		if (localisation==null) {
			return null;
			//  throw an exception
		}
		
		return LocalisationDto.builder()
				.id(localisation.getId())
				.ville(localisation.getVille())
				.pays(localisation.getPays())
				.description(localisation.getDescription())
//				.longitude(localisation.getLongitude())
//				.latitude(localisation.getLatitude())
				.build();
	}
	
	public static Localisation toEntity(LocalisationDto localisationDto) {
		if (localisationDto==null) {
			return null;
			//  throw an exception
		}
		
		Localisation localisation = new Localisation();
		localisation.setId(localisationDto.getId());
		localisation.setVille(localisationDto.getVille());
		localisation.setPays(localisationDto.getPays());
		localisation.setDescription(localisationDto.getDescription());
//		localisation.setLongitude(localisationDto.getLongitude());
//		localisation.setLatitude(localisationDto.getLatitude());
		return localisation;
	}
}

package com.theh.moduleuser.Dto;

import com.theh.moduleuser.Model.Localisation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class LocalisationDto {
	private Integer id;
	private String rue;
	private String latitude;
	private String longitude;
	private VilleDto villeDto;

	public static LocalisationDto fromEntity(Localisation localisation) {
		if (localisation == null) {
			return null;
		}

		return LocalisationDto.builder()
				.id(localisation.getId())
				.rue(localisation.getRue())
				.latitude(localisation.getLatitude())
				.longitude(localisation.getLongitude())
				.villeDto(VilleDto.fromEntity(localisation.getVille()))
				.build();
	}

	public static Localisation toEntity(LocalisationDto localisationDto) {
		if (localisationDto == null) {
			return null;
		}

		Localisation localisation = new Localisation();
		localisation.setId(localisationDto.getId());
		localisation.setRue(localisationDto.getRue());
		localisation.setLatitude(localisationDto.getLatitude());
		localisation.setLongitude(localisationDto.getLongitude());
		localisation.setVille(VilleDto.toEntity(localisationDto.getVilleDto()));

		return localisation;
	}

}

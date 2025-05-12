package com.theh.moduleuser.Validation;

import com.theh.moduleuser.Dto.LocalisationDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class LocalisationValidator {
	
	public static List<String> validate(LocalisationDto localisationDto){
		List<String> errors = new ArrayList<>();
		if(localisationDto==null) {
			errors.add("Veuillez renseigner les Information de la localisation");
		}
		if(!StringUtils.hasLength(localisationDto.getVilleDto().getName())) {
			errors.add("Veuillez renseigner la ville de la mosque");
		}
		else
			if(!StringUtils.hasLength(localisationDto.getVilleDto().getCountry().getName())) {
				errors.add("Veuillez renseigner le quartier ou se situe la mosque");
			}
		
		return errors;
	}
}

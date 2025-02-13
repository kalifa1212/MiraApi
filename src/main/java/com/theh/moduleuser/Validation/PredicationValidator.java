package com.theh.moduleuser.Validation;

import com.theh.moduleuser.Dto.PredicationDto;
import com.theh.moduleuser.Dto.TypePredication;

import java.util.ArrayList;
import java.util.List;

public class PredicationValidator {

	public static List<String> validate(PredicationDto predicationDto){
		List<String> errors = new ArrayList<>();
		if(predicationDto==null) {
			errors.add("Veuillez renseigner ....");
			errors.add("Veuillez renseigner ....");
			errors.add("Veuillez renseigner ....");
			errors.add("Veuillez renseigner ....");
		}
		if (predicationDto.getType()!= TypePredication.PRECHE &&predicationDto.getType()!= TypePredication.SERMON
				&& predicationDto.getType()!= TypePredication.CONFERENCE) {
			errors.add("Veuillez renseigner le type PRECHE,SERMON,CONFERENCE ....");
		}
//		if(StringUtils.hasLength(predicationDto.getVille())) {
//			errors.add("Veuillez renseigner la ville de la mosque");
//		}
//		else
//			if(predicationDto==null || StringUtils.hasLength(predicationDto.getQuartier())) {
//				errors.add("Veuillez renseigner le quartier ou se situe la mosque");
//			}
		
		return errors;
	}
}

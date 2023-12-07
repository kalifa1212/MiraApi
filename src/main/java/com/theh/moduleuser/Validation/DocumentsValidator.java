package com.theh.moduleuser.Validation;

import com.theh.moduleuser.Dto.DocumentsDto;

import java.util.ArrayList;
import java.util.List;

//@Slf4j
public class DocumentsValidator {
	
	public static List<String> validate(DocumentsDto documentsDto){
		List<String> errors = new ArrayList<>();
		if(documentsDto==null) {
			errors.add("Veuillez renseigner ....");
			errors.add("Veuillez renseigner ....");
			errors.add("Veuillez renseigner ....");
			errors.add("Veuillez renseigner ....");
		}
		if(documentsDto.getPredication()==null && documentsDto.getPredication()==null ) {
		errors.add("Veuillez renseigner l'id de d'une entité Sermont ou Predication ");
		}
//		else
//			if(documentsDto==null || StringUtils.hasLength(documentsDto.getQuartier())) {
//				errors.add("Veuillez renseigner le quartier ou se situe la mosque");
//			}
		
		return errors;
	}
}

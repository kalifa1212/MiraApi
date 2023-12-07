package com.theh.moduleuser.Validation;

import com.theh.moduleuser.Dto.RoleDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class RoleValidator {

	public static List<String> validate(RoleDto roleDto){
		List<String> errors = new ArrayList<>();
		if(roleDto==null) {
			errors.add("Veuillez renseigner le role ....");
		}
		if(!StringUtils.hasLength(roleDto.getName())) {
			errors.add("Veuillez renseigner le Role");
		}
		
		return errors;
	}
}

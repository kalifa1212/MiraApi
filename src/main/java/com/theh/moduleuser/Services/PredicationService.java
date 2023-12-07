package com.theh.moduleuser.Services;

import com.theh.moduleuser.Dto.PredicationDto;
import com.theh.moduleuser.Model.Predication;

import java.util.List;

public interface PredicationService {

	PredicationDto save(PredicationDto dto);
	PredicationDto findById(Integer id);
	List<Predication> findByImam(String nom);
	List<PredicationDto> findByThemeImamNom(String str);
	List<PredicationDto> findByTheme(String str);
	List<PredicationDto> findByType(String str);
	List<PredicationDto> findAll();
	void delete(Integer id);
}

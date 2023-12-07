package com.theh.moduleuser.Services;

import com.theh.moduleuser.Dto.LocalisationDto;

import java.util.List;


public interface LocalisationService {

	LocalisationDto save(LocalisationDto  dto);
	LocalisationDto findById(Integer id);
	List<LocalisationDto> findLocalisationByVille(String str);
	List<LocalisationDto> findLocalisationByQuartier(String str);
	
	List<LocalisationDto> findAll();
	void delete(Integer id);
}

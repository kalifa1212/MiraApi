package com.theh.moduleuser.Services;

import com.theh.moduleuser.Dto.LocalisationDto;
import com.theh.moduleuser.Model.Localisation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface LocalisationService {

	LocalisationDto save(LocalisationDto  dto);
	LocalisationDto findById(Integer id);
	Page<LocalisationDto> findLocalisationByVille(String str, Pageable page);
	List<LocalisationDto> findLocalisationByQuartier(String str);
	
	Page<LocalisationDto> findAllByPages( Pageable page);
	List<LocalisationDto> findAll();
	void delete(Integer id);
}

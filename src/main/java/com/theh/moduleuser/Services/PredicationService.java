package com.theh.moduleuser.Services;

import com.theh.moduleuser.Dto.PredicationDto;
import com.theh.moduleuser.Dto.TypePredication;
import com.theh.moduleuser.Model.Predication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PredicationService {

	PredicationDto save(PredicationDto dto);
	PredicationDto findById(Integer id);
	Page<Predication> findByImam(String nom,Pageable page);
	Page<PredicationDto> findByThemeImamNom(String str,Pageable page);
	Page<PredicationDto> findByTheme(String str,Pageable page);
	Page<PredicationDto> findByType(TypePredication type, Pageable page);
	Page<PredicationDto> findAll(Pageable page);
	void delete(Integer id);
}

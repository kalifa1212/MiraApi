package com.theh.moduleuser.Controller;
import com.theh.moduleuser.Controller.Api.PredicationApi;
import com.theh.moduleuser.Dto.PredicationDto;
import com.theh.moduleuser.Services.PredicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PredicationController implements PredicationApi {

	private PredicationService predicationService;
	
	@Autowired
	public PredicationController(
			PredicationService predicationService
	) {
		this.predicationService = predicationService;
	}
	
	@Override
	public ResponseEntity<PredicationDto> savePredication(PredicationDto dto) {
		// TODO enregistrer une predication
		return ResponseEntity.ok(predicationService.save(dto));
	}

	@Override
	public PredicationDto findByIdPredication(Integer id) {
		// TODO rechercher par id
		return predicationService.findById(id);
	}

	@Override
	public List<PredicationDto> findAllPredication() {
		// TODO find all predication
		return predicationService.findAll();

	}

	@Override
	public void deletePredication(Integer id) {
		// TODO supprimer
		
		predicationService.delete(id);
	}

//	@Override
//	public List<Predication> findByNomImam(String nom) {
//		// TODO Auto-generated method stub
//		return predicationService.findByImam(nom);
//	}

	@Override
	public List<PredicationDto> findByThemImamNomPredication(String nom) {
		// TODO recherche
		return predicationService.findByThemeImamNom(nom);
	}

	@Override
	public List<PredicationDto> findByType(String type) {
		// TODO find par type
		return predicationService.findByType(type);
	}

	@Override
	public List<PredicationDto> findByTheme(String type) {
		// TODO find par theme
		return predicationService.findByTheme(type);
	}

}

package com.theh.moduleuser.Controller;
import com.theh.moduleuser.Controller.Api.LocalisationApi;
import com.theh.moduleuser.Dto.LocalisationDto;
import com.theh.moduleuser.Services.EmailService;
import com.theh.moduleuser.Services.LocalisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class LocalisationController implements LocalisationApi {

	private LocalisationService localisationService;


	@Autowired
	public LocalisationController(
			LocalisationService localisationService
	) {
		this.localisationService = localisationService;
	}
	
	@Override
	public ResponseEntity<LocalisationDto> saveLocalisation(LocalisationDto dto) {
		// TODO enregistrer localisation
		return ResponseEntity.ok(localisationService.save(dto));
	}

	@Override
	public LocalisationDto findByIdLocalisation(Integer id) {
		// TODO rechercher par id
		return localisationService.findById(id);
	}

	@Override
	public List<LocalisationDto> findByVilleLocalisation(String str) {
		// TODO rechercher par ville
		return localisationService.findLocalisationByVille(str);
	}
	
//	@Override
//	public List<LocalisationDto> findByQuartierLocalisation(String str) {
//		// TODO rechercher par quartier
//		return  localisationService.findLocalisationByQuartier(str);
//	}

	@Override
	public List<LocalisationDto> findAllLocalisation() {
		// TODO rechercher tout les localisations
		return localisationService.findAll();
	}

	@Override
	public void deleteLocalisation(Integer id) {
		// TODO supprimer
		localisationService.delete(id);
	}

}

package com.theh.moduleuser.Controller;
import com.theh.moduleuser.Controller.Api.LocalisationApi;
import com.theh.moduleuser.Dto.LocalisationDto;
import com.theh.moduleuser.Services.EmailService;
import com.theh.moduleuser.Services.LocalisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
	public Page<LocalisationDto> findByVilleLocalisation(String str, String sortColumn, int page, int taille, String sortDirection) {
		// TODO rechercher par ville
		Pageable paging = PageRequest.of(page, taille, Sort.by(sortColumn).ascending());
		return localisationService.findLocalisationByVille(str,paging);
	}

	@Override
	public Page<LocalisationDto> findAllLocalisation(String sortColumn, int page, int taille, String sortDirection) {
		// TODO rechercher tout les localisations
		Pageable paging = PageRequest.of(page, taille,Sort.by(sortColumn).ascending());

		return localisationService.findAll(paging);
	}

	@Override
	public void deleteLocalisation(Integer id) {
		// TODO supprimer
		localisationService.delete(id);
	}

}

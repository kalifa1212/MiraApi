package com.theh.moduleuser.Controller.Api;
import com.theh.moduleuser.Dto.LocalisationDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.theh.moduleuser.Constant.Constants.APP_ROOT;


@SecurityRequirement(name = "Bearer Authentication")
public interface LocalisationApi {

	@Operation(summary = "Enregistrer ",description = "Enregistrer une localisation")
	@PostMapping(value=APP_ROOT+"localisation/nouveau/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<LocalisationDto> saveLocalisation(@RequestBody LocalisationDto  dto);

	@Operation(summary = "Recherche ",description = "Recherche par Id")
	@GetMapping(value=APP_ROOT+"localisation/{idmosque}",produces=MediaType.APPLICATION_JSON_VALUE)
	LocalisationDto findByIdLocalisation(@PathVariable("idmosque") Integer id);

	@Operation(summary = "Recherche ",description = "Recherche par ville")
	@GetMapping(value=APP_ROOT+"localisation/ville/{ville}",produces=MediaType.APPLICATION_JSON_VALUE)
	Page<LocalisationDto> findByVilleLocalisation(@PathVariable("ville")String str,
												  @RequestParam(required = false,defaultValue = "pays") String sortColumn,
												  @RequestParam(defaultValue = "0") int page,
												  @RequestParam(defaultValue = "2") int taille,
												  @RequestParam(defaultValue = "ascending") String sortDirection);

	@Operation(summary = "Tout afficher Pages",description = "tout afficher pages ")
	@GetMapping(value=APP_ROOT+"localisation/all/pages/")
	Page<LocalisationDto> findAllLocalisationByPages(@RequestParam(required = false,defaultValue = "pays") String sortColumn,
											  @RequestParam(defaultValue = "0") int page,
											  @RequestParam(defaultValue = "2") int taille,
											  @RequestParam(defaultValue = "ascending") String sortDirection);

	@Operation(summary = "Tout afficher ",description = "tout afficher ")
	@GetMapping(value=APP_ROOT+"localisation/all")
	List<LocalisationDto> findAllLocalisation();

	@DeleteMapping(value=APP_ROOT+"localisation/supprimer/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	void deleteLocalisation(@PathVariable("id")Integer id);
}

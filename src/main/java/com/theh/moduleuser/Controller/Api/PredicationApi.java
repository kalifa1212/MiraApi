package com.theh.moduleuser.Controller.Api;

import com.theh.moduleuser.Dto.PredicationDto;
import com.theh.moduleuser.Dto.TypePredication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.theh.moduleuser.Constant.Constants.APP_ROOT;

@SecurityRequirement(name = "Bearer Authentication")
@CrossOrigin(origins = "*")
public interface PredicationApi {


	@Operation(summary = "Enregistrer ",description = "Enregistrer une predication")
	@PostMapping(value=APP_ROOT+"predication/nouveau/",produces=MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<PredicationDto> savePredication(@RequestBody PredicationDto  dto);
	// find by id

	@Operation(summary = "Recherche ",description = "Recherche par ID")
	@GetMapping(value=APP_ROOT+"predication/{idpredication}")
	PredicationDto findByIdPredication(@PathVariable("idpredication") Integer id);

	@Operation(summary = "Export Data ",description = "export  Predication Data ")
	@GetMapping(value = APP_ROOT+"export/")
	ResponseEntity<byte[]> exportTableToCsv() throws IOException;

	@Operation(summary = "Import Data ",description = "Import  predication Data ")
	@PostMapping(value = APP_ROOT+"import/",consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	ResponseEntity<String> importTablefromCsv(@RequestPart("file") MultipartFile file) throws IOException;

	//Find by Nom Imam

	@Operation(summary = "Recherche ",description = "Recherche Predication par Nom predicateur ")
	@GetMapping(value=APP_ROOT+"predication/find/nom/{nom}",produces=MediaType.APPLICATION_JSON_VALUE)
	Page<PredicationDto> findByThemImamNomPredication(@PathVariable("nom") String nom,
													  @RequestParam(required = false,defaultValue = "creationDate") String sortColumn,
													  @RequestParam(defaultValue = "0") int page,
													  @RequestParam(defaultValue = "5") int taille,
													  @RequestParam(defaultValue = "desc") String sortDirection);

	@Operation(summary = "Recherche ",description = "Recherche par type (SERMON, CONFERENCE,PRECHE ou COURS)")
	@GetMapping(value=APP_ROOT+"predication/find/type/{type}",produces=MediaType.APPLICATION_JSON_VALUE)
	Page<PredicationDto> findByType(@PathVariable("type") TypePredication type,
									@RequestParam(required = false,defaultValue = "creationDate") String sortColumn,
									@RequestParam(defaultValue = "0") int page,
									@RequestParam(defaultValue = "2") int taille,
									@RequestParam(defaultValue = "desc") String sortDirection);

	@Operation(summary = "Recherche ",description = "Recherche par theme")
	@GetMapping(value=APP_ROOT+"predication/find/theme/{theme}",produces=MediaType.APPLICATION_JSON_VALUE)
	Page<PredicationDto> findByTheme(@PathVariable("theme") String theme,
									 @RequestParam(required = false,defaultValue = "creationDate") String sortColumn,
									 @RequestParam(defaultValue = "0") int page,
									 @RequestParam(defaultValue = "5") int taille,
									 @RequestParam(defaultValue = "desc") String sortDirection);

	//find all

	@Operation(summary = "Recherche ",description = "tout afficher")
	@GetMapping(value=APP_ROOT+"predication/all",produces=MediaType.APPLICATION_JSON_VALUE)
	Page<PredicationDto> findAllPredication(@RequestParam(required = false,defaultValue = "creationDate") String sortColumn,
											@RequestParam(defaultValue = "0") int page,
											@RequestParam(defaultValue = "5") int taille,
											@RequestParam(defaultValue = "desc") String sortDirection);
	// delete

	@DeleteMapping(value=APP_ROOT+"predication/supprimer/{id}")
	void deletePredication(@PathVariable("id")Integer id);
}

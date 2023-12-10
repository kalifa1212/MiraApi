package com.theh.moduleuser.Controller.Api;

import com.theh.moduleuser.Dto.PredicationDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.theh.moduleuser.Constant.Constants.APP_ROOT;

@SecurityRequirement(name = "Bearer Authentication")
@CrossOrigin(origins = "*")
public interface PredicationApi {


	@PostMapping(value=APP_ROOT+"predication/nouveau/",produces=MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<PredicationDto> savePredication(@RequestBody PredicationDto  dto);
	// find by id

	@GetMapping(value=APP_ROOT+"predication/{idpredication}")
	PredicationDto findByIdPredication(@PathVariable("idpredication") Integer id);
	//Find by Nom Imam

	@GetMapping(value=APP_ROOT+"predication/find/{nom}",produces=MediaType.APPLICATION_JSON_VALUE)
	List<PredicationDto> findByThemImamNomPredication(@PathVariable("nom") String nom);

	@GetMapping(value=APP_ROOT+"predication/find/type/{type}",produces=MediaType.APPLICATION_JSON_VALUE)
	List<PredicationDto> findByType(@PathVariable("type") String type);

	@GetMapping(value=APP_ROOT+"predication/find/theme/{theme}",produces=MediaType.APPLICATION_JSON_VALUE)
	List<PredicationDto> findByTheme(@PathVariable("theme") String type);

	//find all

	@GetMapping(value=APP_ROOT+"predication/all",produces=MediaType.APPLICATION_JSON_VALUE)
	List<PredicationDto> findAllPredication();
	// delete

	@DeleteMapping(value=APP_ROOT+"predication/supprimer/{id}")
	void deletePredication(@PathVariable("id")Integer id);
}

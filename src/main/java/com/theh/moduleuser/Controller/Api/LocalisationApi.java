package com.theh.moduleuser.Controller.Api;
import com.theh.moduleuser.Dto.LocalisationDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.theh.moduleuser.Constant.Constants.APP_ROOT;


//@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = {"Requestor-Type", "Authorization"}, exposedHeaders = "X-Get-Header",allowCredentials = "true") // juste le cors ici suffi de resoudre le pb
@SecurityRequirement(name = "Bearer Authentication")
public interface LocalisationApi {
	
	@PostMapping(value=APP_ROOT+"localisation/nouveau/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<LocalisationDto> saveLocalisation(@RequestBody LocalisationDto  dto);

	@GetMapping(value=APP_ROOT+"localisation/{idmosque}",produces=MediaType.APPLICATION_JSON_VALUE)
	LocalisationDto findByIdLocalisation(@PathVariable("idmosque") Integer id);

	@GetMapping(value=APP_ROOT+"localisation/ville/{ville}",produces=MediaType.APPLICATION_JSON_VALUE)
	List<LocalisationDto> findByVilleLocalisation(@PathVariable("ville")String str);

	@GetMapping(value=APP_ROOT+"localisation/all")
	List<LocalisationDto> findAllLocalisation();

	@DeleteMapping(value=APP_ROOT+"localisation/supprimer/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	void deleteLocalisation(@PathVariable("id")Integer id);
}

package com.theh.moduleuser.Controller.Api;
import com.theh.moduleuser.Dto.MosqueDto;
import com.theh.moduleuser.Dto.MosqueInfoDto;
import com.theh.moduleuser.Dto.UtilisateurDto;
import com.theh.moduleuser.Model.Mosque;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.theh.moduleuser.Constant.Constants.MOSQUE_ENDPOINT;

@CrossOrigin(origins = "*")
@RequestMapping(MOSQUE_ENDPOINT)
@SecurityRequirement(name = "Bearer Authentication")
public interface MosqueApi {//kjlkjskfsfskdfksjdfkl

	@Operation(summary = "Enregistrer une mosqué ",description = "Permet d'enregistrer une mosqué ")
	@ApiResponses(value={
			@ApiResponse(responseCode = "200",description = "Enregistrer"),
			@ApiResponse(responseCode = "401",description = "Utilisateur non Autoriser",content = @Content),
			@ApiResponse(responseCode = "400",description = "Utilisateur Invalide",content = @Content),
			@ApiResponse(responseCode = "403",description = "Connection requise",content = @Content)

	})
	@PostMapping(value = "nouveau/{update}",consumes=MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<MosqueDto> save(@RequestBody MosqueDto  dto, @PathVariable("update") Boolean update);
	

	@GetMapping(value="find/{idmosque}")
	MosqueDto findById(@PathVariable("idmosque") Integer id);

	@GetMapping(value="find/localisation/{villeOrPays}",produces=MediaType.APPLICATION_JSON_VALUE)
	List<MosqueDto> findMosqueByVilleOrPays(@PathVariable("villeOrPays")String str);

	@GetMapping(value="find/vendredi/{vendredi}",produces=MediaType.APPLICATION_JSON_VALUE)
	List<MosqueDto> findByVendredis(@PathVariable("vendredi") Boolean a);

	@GetMapping(value="find/all/{type}",produces=MediaType.APPLICATION_JSON_VALUE)
	List<Mosque> findAll(@PathVariable("type") String type);

	@GetMapping(value="find/",produces=MediaType.APPLICATION_JSON_VALUE)
	Page<Mosque> findAllPagingAndSorting(@RequestParam(required = false) String sortColumn,
											@RequestParam(defaultValue = "0") int page,
											@RequestParam(defaultValue = "2") int taille,
											@RequestParam(defaultValue = "ascending") String sortDirection);
	@GetMapping(value="count",produces=MediaType.APPLICATION_JSON_VALUE)
	int countAll();

	@GetMapping(value="count/vendredi/{bool}",produces=MediaType.APPLICATION_JSON_VALUE)
	int countAllMosqueVendredi(@PathVariable("bool") Boolean bool);

	@GetMapping(value="count/vendredi/{bool}/{ville}",produces=MediaType.APPLICATION_JSON_VALUE)
	int countAllMosqueVendrediByLocation(@PathVariable("bool") Boolean bool,@PathVariable("ville") String ville);


	@GetMapping(value="count/localisation/{string}",produces=MediaType.APPLICATION_JSON_VALUE)
	int countAllMosqueByLocalisation(@PathVariable("string") String bool);


	@GetMapping(value="find/nom/{nom}",produces=MediaType.APPLICATION_JSON_VALUE)
	List<MosqueDto> find(@PathVariable("nom") String str);

	@GetMapping(value="find/date/{date}/{type}",produces=MediaType.APPLICATION_JSON_VALUE)
	MosqueInfoDto findByDate(@PathVariable("date") String date, @PathVariable("type") String type);

	@DeleteMapping(value="supprimer/{id}")
	void delete(@PathVariable("id")Integer id);
}

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

import static com.theh.moduleuser.Constant.Constants.IMAGE_ENDPOINT;
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


    @Operation(summary = "Rechercher par id",description = "Permet de rechercher un element ")
	@GetMapping(value="find/{idmosque}")
	MosqueDto findById(@PathVariable("idmosque") Integer id);

    @Operation(summary = "Recherche Par localisation ",description = " Entrer la ville ou le pays ")
	@GetMapping(value="find/localisation/{villeOrPays}",produces=MediaType.APPLICATION_JSON_VALUE)
	Page<MosqueDto> findMosqueByVilleOrPays(@PathVariable("villeOrPays")String str,
											@RequestParam(required = false,defaultValue = "nom") String sortColumn,
											@RequestParam(defaultValue = "0") int page,
											@RequestParam(defaultValue = "2") int taille,
											@RequestParam(defaultValue = "ascending") String sortDirection);

	@Operation(summary = "Display ",description = "Display Mosque Image ")
	@GetMapping(value = "display/{id}",produces= MediaType.IMAGE_JPEG_VALUE)
	ResponseEntity getFile(@PathVariable("id") Integer id);

    @Operation(summary = "Recherche Mosque du vendredi ",description = "True pour les mosque du Vendredi et false pour les autres ")
	@GetMapping(value="find/vendredi/{vendredi}",produces=MediaType.APPLICATION_JSON_VALUE)
	Page<MosqueDto> findByVendredis(@PathVariable("vendredi") Boolean a,
									@RequestParam(required = false,defaultValue = "nom") String sortColumn,
									@RequestParam(defaultValue = "0") int page,
									@RequestParam(defaultValue = "2") int taille,
									@RequestParam(defaultValue = "ascending") String sortDirection);

    @Operation(summary = "Tout afficher ",description = "Paramettre old/new  pour l'ordre d'affichage ")
	@GetMapping(value="find/all/",produces=MediaType.APPLICATION_JSON_VALUE)
	Page<MosqueDto> findAll(@RequestParam(defaultValue = "new") String type,
							@RequestParam(required = false,defaultValue = "nom") String sortColumn,
							@RequestParam(defaultValue = "0") int page,
							@RequestParam(defaultValue = "2") int taille,
							@RequestParam(defaultValue = "ascending") String sortDirection);

    @Operation(summary = "Test",description = "Test")
	@GetMapping(value="find/",produces=MediaType.APPLICATION_JSON_VALUE)
	Page<MosqueDto> findAllPagingAndSorting(@RequestParam(required = false,defaultValue = "nom") String sortColumn,
											@RequestParam(defaultValue = "0") int page,
											@RequestParam(defaultValue = "2") int taille,
											@RequestParam(defaultValue = "ascending") String sortDirection);

    @Operation(summary = "Count ",description = "le nombre total des mosques enregistrer")
	@GetMapping(value="count",produces=MediaType.APPLICATION_JSON_VALUE)
	int countAll();

    @Operation(summary = "count",description = "nombre des mosquée du vendredi ")
	@GetMapping(value="count/vendredi/{bool}",produces=MediaType.APPLICATION_JSON_VALUE)
	int countAllMosqueVendredi(@PathVariable("bool") Boolean bool);

    @Operation(summary = "Count",description = "le nombre des mosque du vendredi par localisation ")
	@GetMapping(value="count/vendredi/{bool}/{ville}",produces=MediaType.APPLICATION_JSON_VALUE)
	int countAllMosqueVendrediByLocation(@PathVariable("bool") Boolean bool,@PathVariable("ville") String ville);


    @Operation(summary = "count ",description = "nombre des mosque par localisation")
	@GetMapping(value="count/localisation/{string}",produces=MediaType.APPLICATION_JSON_VALUE)
	int countAllMosqueByLocalisation(@PathVariable("string") String bool);

    @Operation(summary = "Rechercher par nom ",description = "Recherchepar nom")
	@GetMapping(value="find/nom/{nom}",produces=MediaType.APPLICATION_JSON_VALUE)
	Page<MosqueDto> find(@PathVariable("nom") String str,
						 @RequestParam(required = false,defaultValue = "nom") String sortColumn,
						 @RequestParam(defaultValue = "0") int page,
						 @RequestParam(defaultValue = "2") int taille,
						 @RequestParam(defaultValue = "ascending") String sortDirection);

    @Operation(summary = "Recherche par date",description = "Recherche apres la date, yyyy-MM-ddThh:mm:ss.sssZ type=(create ou modified)")
	@GetMapping(value="find/date/{date}/{type}",produces=MediaType.APPLICATION_JSON_VALUE)
	MosqueInfoDto findByDate(@PathVariable("date") String date, @PathVariable("type") String type);

	@DeleteMapping(value="supprimer/{id}")
	void delete(@PathVariable("id")Integer id);
}

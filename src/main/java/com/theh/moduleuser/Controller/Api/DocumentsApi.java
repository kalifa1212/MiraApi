package com.theh.moduleuser.Controller.Api;
import com.theh.moduleuser.Dto.DocumentsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import static com.theh.moduleuser.Constant.Constants.APP_ROOT;


@SecurityRequirement(name = "Bearer Authentication")
@CrossOrigin(origins = "*")
public interface DocumentsApi {

	// save Documents
	@Operation(summary = "Enregistrer ",description = "Enregistrer un document ")
	@PostMapping(value=APP_ROOT+"documents/nouveau/{idPredication}", consumes=MediaType.MULTIPART_FORM_DATA_VALUE,produces= MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<DocumentsDto> save(@RequestPart("file") MultipartFile multipartFile ,@PathVariable (value = "idPredication") Integer  idPredication)throws IOException ;
	
	//Download Documents
	@Operation(summary = "Download ",description = "Download Document")
	@GetMapping(value=APP_ROOT+"documents/downloads/{idDocument}/",produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)

	ResponseEntity<?> downloadFileFromLocal(@PathVariable (value = "idDocument") Integer id) throws FileNotFoundException;

	@Operation(summary = "Download ",description = "Download Document by id predication")
	@GetMapping(value=APP_ROOT+"documents/downloads/by/{idPredicationt}/",produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)

	ResponseEntity<?> downloadFileFromLocalBypredication(@PathVariable (value = "idPredicationt") Integer id) throws FileNotFoundException;

	// find by id

	@Operation(summary = "Recherche ",description = "id du document")
	@GetMapping(value=APP_ROOT+"documents/{iddocuments}")
	DocumentsDto findById(@PathVariable("iddocuments") Integer id);
	
	//find all

	@Operation(summary = "Recherche ",description = "tout afficher")
	@GetMapping(value=APP_ROOT+"documents/all",produces=MediaType.APPLICATION_JSON_VALUE)
	Page<DocumentsDto> findAll(@RequestParam(required = false,defaultValue = "nom") String sortColumn,
							   @RequestParam(defaultValue = "0") int page,
							   @RequestParam(defaultValue = "2") int taille,
							   @RequestParam(defaultValue = "ascending") String sortDirection);

	@Operation(summary = "Display File ",description = "Display predication Document and the id of element OK")
	@GetMapping(value = APP_ROOT+"documents/displayFile/{id}", produces= MediaType.IMAGE_JPEG_VALUE)
	void HandlerFile(@PathVariable( "id") Integer id, HttpServletResponse response) throws IOException;
	// delete

	@DeleteMapping(value=APP_ROOT+"documents/supprimer/{id}")
	void delete(@PathVariable("id")Integer id);
}

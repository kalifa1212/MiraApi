package com.theh.moduleuser.Controller.Api;
import com.theh.moduleuser.Dto.DocumentsDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import static com.theh.moduleuser.Constant.Constants.APP_ROOT;


@SecurityRequirement(name = "Bearer Authentication")
@CrossOrigin(origins = "*")
public interface DocumentsApi {

	// save Documents
	@PostMapping(value=APP_ROOT+"documents/nouveau/{idPredication}", consumes=MediaType.MULTIPART_FORM_DATA_VALUE,produces= MediaType.APPLICATION_JSON_VALUE)

	ResponseEntity<DocumentsDto> save(@RequestPart("file") MultipartFile multipartFile ,@PathVariable (value = "idPredication") Integer  idPredication)throws IOException ;
	
	//Download Documents
	
	@GetMapping(value=APP_ROOT+"documents/downloads/{id}/",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)

	ResponseEntity<?> downloadFileFromLocal(@PathVariable (value = "id") Integer id);
	
	// find by id

	@GetMapping(value=APP_ROOT+"documents/{iddocuments}")
	DocumentsDto findById(@PathVariable("iddocuments") Integer id);
	
	//find all

	@GetMapping(value=APP_ROOT+"documents/all",produces=MediaType.APPLICATION_JSON_VALUE)
	List<DocumentsDto> findAll();
	
	// delete

	@DeleteMapping(value=APP_ROOT+"documents/supprimer/{id}")
	void delete(@PathVariable("id")Integer id);
}

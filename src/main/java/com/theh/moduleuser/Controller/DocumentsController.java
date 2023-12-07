package com.theh.moduleuser.Controller;
import com.theh.moduleuser.Controller.Api.DocumentsApi;
import com.theh.moduleuser.Dto.DocumentsDto;
import com.theh.moduleuser.Exceptions.ErrorCodes;
import com.theh.moduleuser.Exceptions.InvalidEntityException;
import com.theh.moduleuser.File.FileUploader;
import com.theh.moduleuser.Services.DocumentsService;
import com.theh.moduleuser.Validation.DocumentsValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
public class DocumentsController implements DocumentsApi {

	private DocumentsService documentsService;
	// G:\HAMIDOU\Projets\Projet Personnel\muslim\Documents
	@Autowired
	public DocumentsController(
			DocumentsService documentsService
	) {
		this.documentsService = documentsService;
	}

	@Override
	public DocumentsDto findById(Integer id) {
		// TODO Auto-generated method stub
		return documentsService.findById(id);
	}

	@Override
	public List<DocumentsDto> findAll() {
		// TODO Auto-generated method stub
		return documentsService.findAll();
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
		documentsService.delete(id);
	}

	@Override
	public ResponseEntity<DocumentsDto> save(MultipartFile multipartFile, DocumentsDto dto) throws IOException {
		// TODO Auto-generated method stub
		//Nommage du fichier 
		String FileName=StringUtils.cleanPath(multipartFile.getOriginalFilename());
		String extension=FileName.substring(FileName.lastIndexOf(".")+1);
		String uploadDir="";
		DateFormat format= new SimpleDateFormat("yyyy-MM-dd HH-mm-SS");
		Date date = new Date();
		
		//verification de la conformité du document
		
		List<String> errors = DocumentsValidator.validate(dto);
		if(!errors.isEmpty()) {
			log.error("La documents est non valide veuiller entrer l'id d'un element");
			throw new InvalidEntityException("La documents n'est pas valide ", ErrorCodes.DOCUMENTS_NOT_VALID,errors);
		}

		
		//Verification du type de fichier
		
		if(extension.equals("jpg")|| extension.equals("png")||extension.equals("jpeg")) {
			dto.setType_doc("Image");
			log.error("c'est une image {}",extension);
		}
		else if(extension.equals("mp3")){
			dto.setType_doc("Audio");
			log.error("un audio {}",extension);
			}
		else if(extension.equals("pdf")){
			dto.setType_doc("Pdf");
			log.error("documents pdf {}",extension);
			}
		else if(extension.equals("mp4")||extension.equals("avi")){
			dto.setType_doc("Video");
			log.error("video {}",extension);
			}
		
		FileUploader.saveFile(uploadDir,FileName,multipartFile);
		
		 return ResponseEntity.ok(documentsService.save(dto));
	}

	@Override
	public ResponseEntity<Resource> downloadFileFromLocal(@PathVariable (value = "filename") String filename,@PathVariable (value = "nom") char  nom) {
		
		String fileBasePath="";
		
		if(nom=='S') {
			fileBasePath ="Documents/Sermont/";
		}else
			if(nom=='P') {
			 fileBasePath ="Documents/Predication/";
			}
			else {
				throw new InvalidEntityException("La documents n'est pas valide ", ErrorCodes.DOCUMENTS_NOT_EXIST);
			}
		
    	Path path = Paths.get(fileBasePath + filename);
    	Resource resource = null;
    	try {
    		resource = (Resource) new UrlResource(path.toUri());
    	} catch (MalformedURLException e) {
    		e.printStackTrace();
    	}
    	return ResponseEntity.ok()
    			.contentType(MediaType.parseMediaType("image/jpg")) //attachment
    			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
    			.body(resource);
    }

}

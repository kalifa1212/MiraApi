package com.theh.moduleuser.Controller;
import com.theh.moduleuser.Controller.Api.DocumentsApi;
import com.theh.moduleuser.Dto.DocumentsDto;
import com.theh.moduleuser.Exceptions.ErrorCodes;
import com.theh.moduleuser.Exceptions.InvalidEntityException;
import com.theh.moduleuser.Services.DocumentsService;
import com.theh.moduleuser.Services.File.FileUpload;
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
	public ResponseEntity<DocumentsDto> save(MultipartFile multipartFile, Integer idPredication) throws IOException {
		// TODO Auto-generated method stub
		DocumentsDto documentsDto=documentsService.save(idPredication,multipartFile);
		 return ResponseEntity.ok(documentsDto);
	}

	@Override
	public ResponseEntity<Resource> downloadFileFromLocal(Integer id) {
		
		//String fileBasePath ="Documents/Predication/";
		DocumentsDto documentsDto =documentsService.findById(id);
    	Path path = Paths.get(documentsDto.getFichier());
    	Resource resource = null;
    	try {
    		resource = (Resource) new UrlResource(path.toUri());
    	} catch (MalformedURLException e) {
    		e.printStackTrace();
    	}
    	return ResponseEntity.ok()
    			.contentType(MediaType.parseMediaType("")) //attachment
    			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
    			.body(resource);
    }

}

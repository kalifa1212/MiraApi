package com.theh.moduleuser.Controller;
import com.theh.moduleuser.Controller.Api.DocumentsApi;
import com.theh.moduleuser.Dto.DocumentsDto;
import com.theh.moduleuser.Dto.PredicationDto;
import com.theh.moduleuser.Exceptions.ErrorCodes;
import com.theh.moduleuser.Exceptions.InvalidEntityException;
import com.theh.moduleuser.Services.DocumentsService;
import com.theh.moduleuser.Services.File.FileUpload;
import com.theh.moduleuser.Services.PredicationService;
import com.theh.moduleuser.Validation.DocumentsValidator;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@RestController
public class DocumentsController implements DocumentsApi {

	private DocumentsService documentsService;
	private PredicationService predicationService;
	@Value("${project.poster}")
	String path;
	@Autowired
	public DocumentsController(
			DocumentsService documentsService,
			PredicationService predicationService
	) {
		this.documentsService = documentsService;
		this.predicationService = predicationService;
	}

	@Override
	public DocumentsDto findById(Integer id) {
		// TODO Auto-generated method stub
		return documentsService.findById(id);
	}

	@Override
	public Page<DocumentsDto> findAll(String sortColumn, int page, int taille, String sortDirection) {
		// TODO Auto-generated method stub
		Pageable paging = PageRequest.of(page, taille, Sort.by(sortColumn).ascending());
		return documentsService.findAll(paging);
	}

	@Override
	public void HandlerFile(Integer id, HttpServletResponse response) throws IOException {
		PredicationDto predication = predicationService.findById(id);
		InputStream ressourceFile=FileUpload.getRessourceAsStream(path,predication);
		response.setContentType(MediaType.IMAGE_PNG_VALUE);
		StreamUtils.copy(ressourceFile,response.getOutputStream());

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

	//ResponseEntity<InputStreamResource>
	@Override
	public ResponseEntity<Resource> downloadFileFromLocal(Integer id) throws FileNotFoundException {
		
		//String fileBasePath ="Documents/Predication/";
		DocumentsDto documentsDto =documentsService.findById(id);
		//log.error("doc log {}",documentsDto);
    	Path path = Paths.get(documentsDto.getFichier());
    	Resource resource = null;
    	try {
    		resource = (Resource) new UrlResource(path.toUri());
    	} catch (MalformedURLException e) {
    		e.printStackTrace();
    	}

    	return ResponseEntity.ok()
    			.contentType(MediaType.APPLICATION_OCTET_STREAM) //attachment
    			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				//.headers(headers)
    			.body(resource);
    }

	@Override
	public ResponseEntity<Resource> downloadFileFromLocalBypredication(Integer id) throws FileNotFoundException {

			DocumentsDto documentsDto =documentsService.findByPredication(id);
			Path path = Paths.get(documentsDto.getFichier());
			Resource resource = null;
			try {
				resource = (Resource) new UrlResource(path.toUri());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			return ResponseEntity.ok()
					.contentType(MediaType.APPLICATION_OCTET_STREAM) //attachment
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
					.body(resource);
	}

}

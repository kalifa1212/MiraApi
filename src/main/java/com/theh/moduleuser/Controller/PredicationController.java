package com.theh.moduleuser.Controller;
import com.theh.moduleuser.Controller.Api.PredicationApi;
import com.theh.moduleuser.Dto.PredicationDto;
import com.theh.moduleuser.Dto.TypePredication;
import com.theh.moduleuser.Services.PredicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class PredicationController implements PredicationApi {

	private PredicationService predicationService;
	
	@Autowired
	public PredicationController(
			PredicationService predicationService
	) {
		this.predicationService = predicationService;
	}
	
	@Override
	public ResponseEntity<PredicationDto> savePredication(PredicationDto dto) {
		// TODO enregistrer une predication
		return ResponseEntity.ok(predicationService.save(dto));
	}

	@Override
	public PredicationDto findByIdPredication(Integer id) {
		// TODO rechercher par id
		return predicationService.findById(id);
	}

	@Override
	public ResponseEntity<byte[]> exportTableToCsv() throws IOException {
		StringWriter stringWriter = new StringWriter();
		predicationService.exportData(new PrintWriter(stringWriter));

		byte[] csvBytes = stringWriter.toString().getBytes(StandardCharsets.UTF_8);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=predication.csv")
				.contentType(MediaType.parseMediaType("text/csv"))
				.body(csvBytes);
	}

	@Override
	public ResponseEntity<String> importTablefromCsv(MultipartFile file) throws IOException {

		if (file.isEmpty()) {
			return ResponseEntity.badRequest().body("Fichier CSV manquant !");
		}

		try {
			predicationService.importDataToDB(file);
			return ResponseEntity.ok("Importation des Predication réussie !");
		} catch (IOException e) {
			return ResponseEntity.status(500).body("Erreur d'importation : " + e.getMessage());
		}
	}

	@Override
	public Page<PredicationDto> findAllPredication(String sortColumn, int page, int taille, String sortDirection) {
		// TODO find all predication
		Sort.Direction direction = sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable paging = PageRequest.of(page, taille,Sort.by(direction,sortColumn));
		return predicationService.findAll(paging);

	}

	@Override
	public void deletePredication(Integer id) {
		// TODO supprimer
		
		predicationService.delete(id);
	}

	@Override
	public Page<PredicationDto> findByThemImamNomPredication(String nom,String sortColumn, int page, int taille, String sortDirection) {
		// TODO recherche
		Sort.Direction direction = sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable paging = PageRequest.of(page, taille,Sort.by(direction,sortColumn));
		return predicationService.findByThemeImamNom(nom,paging);
	}

	@Override
	public Page<PredicationDto> findByType(TypePredication type, String sortColumn, int page, int taille, String sortDirection) {
		// TODO find par type
		Sort.Direction direction = sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable paging = PageRequest.of(page, taille,Sort.by(direction,sortColumn));
		return predicationService.findByType(type, paging);
	}

	@Override
	public Page<PredicationDto> findByTheme(String theme,String sortColumn, int page, int taille, String sortDirection) {
		// TODO find par theme
		Sort.Direction direction = sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable paging = PageRequest.of(page, taille,Sort.by(direction,sortColumn));
		return predicationService.findByTheme(theme,paging);
	}

}

package com.theh.moduleuser.Controller;
import com.opencsv.exceptions.CsvValidationException;
import com.theh.moduleuser.Controller.Api.MosqueApi;
import com.theh.moduleuser.Dto.MosqueDto;
import com.theh.moduleuser.Dto.MosqueInfoDto;
import com.theh.moduleuser.Exceptions.ErrorCodes;
import com.theh.moduleuser.Exceptions.InvalidEntityException;
import com.theh.moduleuser.Model.Mosque;
import com.theh.moduleuser.Repository.MosqueRepository;
import com.theh.moduleuser.Services.MosqueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
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
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@Slf4j
public class MosqueController implements MosqueApi {
	private MosqueService mosqueService;
	private MosqueRepository mosqueRepository;

	@Autowired
	public MosqueController(
			MosqueService mosqueService,
			MosqueRepository mosqueRepository
	) {
		this.mosqueService = mosqueService;
		this.mosqueRepository=mosqueRepository;
	}
	
	@Override
	public ResponseEntity<MosqueDto> save(MosqueDto dto, Boolean update) {
		// TODO  Enregistrement d'une mosque
		return ResponseEntity.ok(mosqueService.save(dto,update));
	}

	@Override
	public MosqueDto findById(Integer id) {
		// TODO find mosque by id
		return mosqueService.findById(id);
	}


	@Override
	public Page<MosqueDto> findMosqueByVilleOrPays(String str,String sortColumn, int page, int taille, String sortDirection) {
		// TODO Rechercher des mosque par ville ou pays
		Sort.Direction direction = sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable paging = PageRequest.of(page, taille,Sort.by(direction,sortColumn));
		String pays=str;
		//Page<MosqueDto> mosqueList=mosqueRepository.findMosqueByLocalisationVilleContainingOrLocalisationPays(str,pays,paging).map(MosqueDto::fromEntity);
		Page<MosqueDto> mosqueList=mosqueRepository.findByLocalisation_Ville_NameContainingOrLocalisation_Ville_Pays_Name(str,pays,paging).map(MosqueDto::fromEntity);
//		mosqueList.addAll(mosqueRepository.findMosqueByLocalisationPaysContaining(str,paging).stream()
//				.map(MosqueDto::fromEntity)
//				.collect(Collectors.toList()));
		//return mosqueService.findMosqueByVilleOrQuartier(str);

		return mosqueList;
	}


	@Override
	public ResponseEntity getFile(Integer id) {
		MosqueDto mosque= mosqueService.findById(id);
		//log.error("image data {} ",mosque.getImagedata());
		if(mosque==null){
			throw new InvalidEntityException("Image non disponible", ErrorCodes.FILE_NOT_FOUND);
		}
		Resource resourceM=uploadingFile(mosque);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\" profile \"")
				.body(resourceM);
	}

	@Override
	public ResponseEntity<byte[]> exportTableToCsv() throws IOException {
		StringWriter stringWriter = new StringWriter();
		mosqueService.exportData(new PrintWriter(stringWriter));

		byte[] csvBytes = stringWriter.toString().getBytes(StandardCharsets.UTF_8);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=mosque.csv")
				.contentType(MediaType.parseMediaType("text/csv"))
				.body(csvBytes);

	}

	@Override
	public ResponseEntity<String> importTablefromCsv(MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			return ResponseEntity.badRequest().body("Fichier CSV manquant !");
		}

		try {
			mosqueService.importDataToDB(file);
			return ResponseEntity.ok("Importation des mosquées réussie !");
		} catch (IOException e) {
			return ResponseEntity.status(500).body("Erreur d'importation : " + e.getMessage());
		}

	}

	public Resource uploadingFile(MosqueDto mosqueDto){
		Path path = Paths.get(mosqueDto.getPhoto());
		Resource resource = null;
		try {
			resource = (Resource) new UrlResource(path.toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return  resource;
	}

	@Override
	public Page<MosqueDto> findByVendredis(Boolean a,String sortColumn, int page, int taille, String sortDirection) {

		// TODO Rechercher des mosqué par leur type : mosque du vendredi
		Sort.Direction direction = sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable paging = PageRequest.of(page, taille,Sort.by(direction,sortColumn));

		return  mosqueService.findByVendredis(a,paging);
	}
	
	@Override
	public Page<MosqueDto> findAll(String sortColumn, int page, int taille, String sortDirection) {
		// TODO afficher toutes les mosque par nouveauté et ancienneté
		Sort.Direction direction = sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable paging = PageRequest.of(page, taille,Sort.by(direction,sortColumn));

		return mosqueService.findAll(paging);

	}

	@Override
	public int countAll() {
		// TODO nombre de mosque
		return (int) mosqueRepository.count();
	}

	@Override
	public int countAllMosqueVendredi(Boolean bool) {
		// TODO nombre des mosques du vendredi
		return mosqueRepository.countByIsVendredi(bool);

	}

	@Override
	public int countAllMosqueVendrediByLocation(Boolean bool, String ville) {
		// TODO nombre de mosque du vendredi par localisation
		return mosqueRepository.countMosqueByIsVendrediAndLocalisation_Ville_NameContaining(bool,ville);
		//return 0;
	}

	@Override
	public int countAllMosqueByLocalisation(String bool) {
		// TODO  nombre de mosque par localisation
		return mosqueRepository.countMosqueByLocalisation_Ville_NameContaining(bool);
		//return 0;
	}

	@Override
	public Page<MosqueDto> find(String str,String sortColumn, int page, int taille, String sortDirection) {
		// TODO rechercher les mosque par nom et localisation
		Sort.Direction direction = sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable paging = PageRequest.of(page, taille,Sort.by(sortColumn).ascending());
		return mosqueService.findAllByName(str,paging);
	}

	@Override
	public MosqueInfoDto findByDate(String dateSearch, String type){
		// TODO rechercher par date et le type ( date de modification ou de creation)
		log.info("voici le string {}",dateSearch);
		Instant date= Instant.parse(dateSearch);
		if(type.contentEquals("create")){
			//List<Mosque> mosqueDto=mosqueRepository.findByCreationDateAfter(date);
			List<Mosque> mosque=mosqueRepository.findByCreationDateGreaterThanEqual(date);
			MosqueInfoDto mosqueInfoDto=new MosqueInfoDto();
			mosqueInfoDto.setMosqueAfterCreationDate(mosque);
			return mosqueInfoDto;
		}
		else if(type.contentEquals("modified")){
			List<Mosque> mosque=mosqueRepository.findByLastModifiedDateGreaterThanEqual(date);
			MosqueInfoDto mosqueInfoDto=new MosqueInfoDto();
			mosqueInfoDto.setMosqueAfterLastModifiedDate(mosque);
			return mosqueInfoDto;
		}
		else {
			throw new InvalidEntityException("Le type entrée n'est pas pris en compte (new or old)");
		}

	}

	@Override
	public void delete(Integer id) {
		// TODO supprimer mosqué
		mosqueService.delete(id);
	}
}

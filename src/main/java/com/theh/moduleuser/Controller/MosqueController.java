package com.theh.moduleuser.Controller;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

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
		Pageable paging = PageRequest.of(page, taille,Sort.by(sortColumn).ascending());
		String pays=str;
		Page<MosqueDto> mosqueList=mosqueRepository.findMosqueByLocalisationVilleContainingOrLocalisationPays(str,pays,paging).map(MosqueDto::fromEntity);
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
		if(mosque.getImagedata()==null){
			throw new InvalidEntityException("Image non disponible", ErrorCodes.FILE_NOT_FOUND);
		}
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + mosque.getPhoto() + "\"")
				.body(mosque.getImagedata());
	}

	@Override
	public Page<MosqueDto> findByVendredis(Boolean a,String sortColumn, int page, int taille, String sortDirection) {

		// TODO Rechercher des mosqué par leur type : mosque du vendredi
		Pageable paging = PageRequest.of(page, taille,Sort.by(sortColumn).ascending());

		return  mosqueService.findByVendredis(a,paging);
	}
	
	@Override
	public Page<MosqueDto> findAll(String type,String sortColumn, int page, int taille, String sortDirection) {
		// TODO afficher toutes les mosque par nouveauté et ancienneté
		Pageable paging = PageRequest.of(page, taille,Sort.by(sortColumn).ascending());
		if(type.contentEquals("old")){
			//return mosqueRepository.findByOrderByCreationDateAsc(paging).map(MosqueDto::fromEntity); // TODO a implementé dans service Mosque
			return mosqueService.findAll(paging);
		}
			else if(type.contentEquals("new")){
			//return mosqueRepository.findByOrderByCreationDateDesc(paging).map(MosqueDto::fromEntity);// TODO idem
			return mosqueService.findAll(paging);
			}
				else {
					throw new InvalidEntityException("Le type entrée n'est pas en compte (new or old)");}
		//  Auto-generated method stub
		//return mosqueService.findAll();

	}

	@Override
	public Page<MosqueDto> findAllPagingAndSorting(String sortColumn, int page, int taille, String sortDirection) {
		String ascending="ascending";String descending="descending";

		if(sortDirection.equals("ascending")){
			Pageable paging = PageRequest.of(page, taille,Sort.by(sortColumn).ascending());
			return mosqueRepository.findAll(paging).map(MosqueDto::fromEntity);
		} else if (sortDirection.equals("descending")) {
			Pageable paging = PageRequest.of(page, taille,Sort.by(sortColumn).descending());
			return mosqueRepository.findAll(paging).map(MosqueDto::fromEntity);
		}else {
			Pageable paging = PageRequest.of(page, taille,Sort.by(sortColumn).ascending());
			return mosqueRepository.findAll(paging).map(MosqueDto::fromEntity);
		}
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
		return mosqueRepository.countMosqueByIsVendrediAndLocalisation_VilleContaining(bool,ville);
	}

	@Override
	public int countAllMosqueByLocalisation(String bool) {
		// TODO  nombre de mosque par localisation
		return mosqueRepository.countMosqueByLocalisation_VilleContaining(bool);
	}

	@Override
	public Page<MosqueDto> find(String str,String sortColumn, int page, int taille, String sortDirection) {
		// TODO rechercher les mosque par nom et localisation
		Pageable paging = PageRequest.of(page, taille,Sort.by(sortColumn).ascending());
		//Page<MosqueDto> mosqueList=mosqueRepository.findMosqueByNomContaining(str,paging).map(MosqueDto::fromEntity);
		//log.info("test lob strem {}",mosqueList);
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
			throw new InvalidEntityException("Le type entrée n'est pas en compte (new or old)");
		}

		//List<MosqueDto> mosqueDtoLastModified=mosqueRepository.findByLastModifiedDateAfter(date);

		//mosqueInfoDto.setMosqueAfterLastModifiedDate(mosqueDtoLastModified);
	}

	@Override
	public void delete(Integer id) {
		// TODO supprimer mosqué
		mosqueService.delete(id);
	}
}

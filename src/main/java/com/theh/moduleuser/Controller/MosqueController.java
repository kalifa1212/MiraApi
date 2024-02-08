package com.theh.moduleuser.Controller;
import com.theh.moduleuser.Controller.Api.MosqueApi;
import com.theh.moduleuser.Dto.MosqueDto;
import com.theh.moduleuser.Dto.MosqueInfoDto;
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
	public List<MosqueDto> findMosqueByVilleOrPays(String str) {
		// TODO Rechercher des mosque par ville ou pays
		List<MosqueDto> mosqueList=mosqueRepository.findMosqueByLocalisationVilleContaining(str).stream()
				.map(MosqueDto::fromEntity)
				.collect(Collectors.toList());
		mosqueList.addAll(mosqueRepository.findMosqueByLocalisationPaysContaining(str).stream()
				.map(MosqueDto::fromEntity)
				.collect(Collectors.toList()));
		//return mosqueService.findMosqueByVilleOrQuartier(str);
		return mosqueList;
	}

	@Override
	public List<MosqueDto> findByVendredis(Boolean a) {
		// TODO Rechercher des mosqué par leur type : mosque du vendredi
		return  mosqueService.findByVendredis(a);
	}
	
	@Override
	public List<Mosque> findAll(String type) {
		// TODO afficher toutes les mosque par nouveauté et ancienneté
		if(type.contentEquals("old")){
			return mosqueRepository.findByOrderByCreationDateAsc(); // TODO a implementé dans service Mosque
		}
			else if(type.contentEquals("new")){
			return mosqueRepository.findByOrderByCreationDateDesc();// TODO idem
		}
				else {throw new InvalidEntityException("Le type entrée n'est pas en compte (new or old)");}
		//  Auto-generated method stub
		//return mosqueService.findAll();

	}

	@Override
	public Page<Mosque> findAllPagingAndSorting(String sortColumn, int page, int taille, String sortDirection) {
		String ascending="ascending";String descending="descending";

		if(sortDirection.equals("ascending")){
			Pageable paging = PageRequest.of(page, taille,Sort.by(sortColumn).ascending());
			Page<Mosque> test =mosqueRepository.findAll(paging);
			return test;
		} else if (sortDirection.equals("descending")) {
			Pageable paging = PageRequest.of(page, taille,Sort.by(sortColumn).descending());
			Page<Mosque> test =mosqueRepository.findAll(paging);
			return test;
		}else {
			Pageable paging = PageRequest.of(page, taille,Sort.by(sortColumn).ascending());
			Page<Mosque> test =mosqueRepository.findAll(paging);
			return test;
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
	public List<MosqueDto> find(String str) {
		// TODO rechercher les mosque par nom et localisation
		List<MosqueDto> mosqueList=mosqueRepository.findMosqueByNomContaining(str).stream()
				.map(MosqueDto::fromEntity)
				.collect(Collectors.toList());
		mosqueList.addAll(mosqueRepository.findMosqueByLocalisationVilleContaining(str).stream()
				.map(MosqueDto::fromEntity)
				.collect(Collectors.toList()));
		mosqueList.addAll(mosqueRepository.findMosqueByLocalisationPaysContaining(str).stream()
				.map(MosqueDto::fromEntity)
				.collect(Collectors.toList()));
		return mosqueList;
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

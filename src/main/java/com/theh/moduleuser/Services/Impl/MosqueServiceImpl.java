package com.theh.moduleuser.Services.Impl;

import com.theh.moduleuser.Dto.MosqueDto;
import com.theh.moduleuser.Exceptions.EntityNotFoundException;
import com.theh.moduleuser.Exceptions.ErrorCodes;
import com.theh.moduleuser.Exceptions.InvalidEntityException;
import com.theh.moduleuser.Model.Localisation;
import com.theh.moduleuser.Model.Mosque;
import com.theh.moduleuser.Model.Notification;
import com.theh.moduleuser.Model.Utilisateur;
import com.theh.moduleuser.Repository.*;
import com.theh.moduleuser.Services.MosqueService;
import com.theh.moduleuser.Validation.MosqueValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service @Slf4j
public class MosqueServiceImpl implements MosqueService {
	
	private MosqueRepository mosqueRepository;
	private UtilisateurRepository utilisateurRepository;
	private LocalisationRepository localisationRepository;
	private NotificationRepository notificationRepository;
	private SuivreRepository suivreRepository;
	
	@Autowired
	public MosqueServiceImpl(
			MosqueRepository mosqueRepository,
			LocalisationRepository localisationRepository,
			UtilisateurRepository utilisateurRepository,
			NotificationRepository notificationRepository,
			SuivreRepository suivreRepository
	) {
		this.mosqueRepository=mosqueRepository;
		this.localisationRepository=localisationRepository;
		this.utilisateurRepository=utilisateurRepository;
		this.notificationRepository=notificationRepository;
		this.suivreRepository=suivreRepository;
	}
	
	@Override
	public MosqueDto save(MosqueDto dto, Boolean update) {
		//  Auto-generated method stub
		if(!update) {
			List<String> errors = MosqueValidator.validate(dto);
			if(!errors.isEmpty()) {
				throw new InvalidEntityException("Les information de la mosque ne sont pas valide ", ErrorCodes.MOSQUE_NOT_VALID,errors);
			}
			Optional<Localisation> localisation = localisationRepository.findById(dto.getLocalisation().getId());
			if(localisation.isEmpty()){
				throw new InvalidEntityException("Localisation non trouver", ErrorCodes.LOCALISATION_NOT_FOUND,errors);
			}
		}

		//TODO Notification pour la creation d'une mosque
		Notification(dto,update);
		return MosqueDto.fromEntity(mosqueRepository.save(MosqueDto.toEntity(dto)));
	}

	@Override
	public MosqueDto findById(Integer id) {
		//  Auto-generated method stub
		if(id==null ) {
			throw new InvalidEntityException("L'id entre est NULL");
		}
		Optional<Mosque> mosque= mosqueRepository.findById(id);
		//log.error("test {}",mosque.get().getNom());
//		if(!mosque.isPresent()) {
//			log.error("inside test {}",mosque);
//			throw new InvalidEntityException("Aucune mosque avec l'id "+id+" n'a ete trouver dans la BD",
//					ErrorCodes.MOSQUE_NOT_FOUND);
//		}
		
		return Optional.of(MosqueDto.fromEntity(mosque.get())).orElseThrow(() ->
				new EntityNotFoundException(
						"Aucune mosque avec l'id "+id+"n'a ete trouver dans la BD",
						ErrorCodes.MOSQUE_NOT_FOUND)
		);
	}

	@Override
	public List<MosqueDto> findByNom(String nom) {
		//  Auto-generated method stub
		if(!StringUtils.hasLength(nom)) {
			throw new InvalidEntityException("Le nom entrée est NULL");
		}

		return this.mosqueRepository.findMosqueByNomContaining(nom).stream()
				.map(MosqueDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public List<MosqueDto> findMosqueByVilleOrQuartier(String str) {
		if(!StringUtils.hasLength(str)) {
			throw new InvalidEntityException("Le nom entrée est NULL");
		}

		return null;
	}

	@Override
	public Page<MosqueDto> findByVendredis(Boolean vendredi, Pageable page) {
		
		return this.mosqueRepository.findMosqueByIsVendredi(vendredi,page)
				.map(MosqueDto::fromEntity);
	}

	@Override
	public Page<MosqueDto> findAll(Pageable page) {
		//  Auto-generated method stub
		return mosqueRepository.findAll(page)
				.map(MosqueDto::fromEntity);
	}

	@Override
	public Page<MosqueDto> findAllByName(String str, Pageable page) {
		Page<MosqueDto> mosqueList=mosqueRepository.findMosqueByNomContaining(page,str).map(MosqueDto::fromEntity);
//		List<MosqueDto> mosqueList= mosqueRepository.findMosqueByNomContaining(str)
//				.stream()
//				.map(MosqueDto::fromEntity)
//				.collect(Collectors.toList());
		//Page <MosqueDto> mosquePage= convertListToPage(mosqueList,page);
		//return mosqueRepository.findMosqueByNomContaining(page,str).map(MosqueDto::fromEntity);
		return mosqueList;
	}
	// TODO Conversion Function
	public  static <T> Page<T> convertListToPage(List<T> list,Pageable page) {
		PagedListHolder pageC= new PagedListHolder(list);
		pageC.setPage(page.getPageNumber());
		pageC.setPageSize(page.getPageSize());
		return (Page<T>) pageC;
	}

	@Override
	public void delete(Integer id) {
		//  Auto-generated method stub
		if(id==null) {
			throw new InvalidEntityException("L'Id entrée est NULL");
		}
		
		mosqueRepository.deleteById(id);

	}
	void Notification(MosqueDto dto,Boolean update){
		dto.getLocalisation().getVille();
		String type="Nouvelle Mosque";
		List<Utilisateur> utilisateur= utilisateurRepository.findUtilisateurByLocalisationId(dto.getLocalisation().getId());
		//log.warn("la taille {} utilisateurs : {}",utilisateur.size(),utilisateur);
		Boolean isvendredi=dto.getIsVendredi();
		if(update){
			type="Mise a jour d'une mosque";
		}
		for (int i=0; i<utilisateur.size();i++){
			Notification notification=new Notification();
			if(isvendredi && update){
				notification.setMessage("La mosquée "+dto.getNom()+" du vendredi a modifier certains information.");
			}else {
				notification.setMessage("La Mosque "+dto.getNom()+ " de votre localitée a du nouveau. ");
			}
			notification.setType(type);
			// log.warn("tour {} ,notification : ",i,utilisateur.get(i));
			notification.setUtilisateur(utilisateur.get(i));
			notificationRepository.save(notification);
		}
	}
}

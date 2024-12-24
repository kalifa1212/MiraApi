package com.theh.moduleuser.Services.Impl;

import com.theh.moduleuser.Dto.NotificationDto;
import com.theh.moduleuser.Dto.PredicationDto;
import com.theh.moduleuser.Dto.UtilisateurDto;
import com.theh.moduleuser.Exceptions.EntityNotFoundException;
import com.theh.moduleuser.Exceptions.ErrorCodes;
import com.theh.moduleuser.Exceptions.InvalidEntityException;
import com.theh.moduleuser.Model.Mosque;
import com.theh.moduleuser.Model.Predication;
import com.theh.moduleuser.Model.Suivre;
import com.theh.moduleuser.Model.Utilisateur;
import com.theh.moduleuser.Repository.*;
import com.theh.moduleuser.Services.PredicationService;
import com.theh.moduleuser.Validation.PredicationValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@Slf4j
public class PredicationServiceImpl implements PredicationService {
	
	private PredicationRepository predicationRepository;
	private UtilisateurRepository utilisateurRepository;
	private MosqueRepository mosqueRepository;
	private NotificationRepository notificationRepository;
	private SuivreRepository suivreRepository;
	String NotificationMessage;
	
	@Autowired
	public PredicationServiceImpl(
			PredicationRepository predicationRepository,
			MosqueRepository mosqueRepository,
			UtilisateurRepository utilisateurRepository,
			SuivreRepository suivreRepository,
			NotificationRepository notificationRepository
	) {
		this.predicationRepository=predicationRepository;
		this.mosqueRepository=mosqueRepository;
		this.utilisateurRepository=utilisateurRepository;
		this.suivreRepository=suivreRepository;
		this.notificationRepository=notificationRepository;
	}
	
	@Override
	public PredicationDto save(PredicationDto dto) {
		// TODO Implementation de l'enregistrement d'une predication
		List<String> errors = PredicationValidator.validate(dto);
		if(!errors.isEmpty()) {
			throw new InvalidEntityException("Predication non valide ", ErrorCodes.PREDICATION_NOT_VALID,errors);
		}
//		dto.setType(dto.getType().toUpperCase());
//		if(!dto.getType().equals("SERMON")){
//			if(!dto.getType().equals("PRECHE")){
//				if(!dto.getType().equals("CONFERENCE")){
//			throw new EntityNotFoundException("champ Type non valide SERMON/CONFERENCE.",ErrorCodes.PREDICATION_NOT_VALID);
//				}
//			}
//		}
		//TODO Condition de verification de l'existance de l'imam
		Utilisateur util=new Utilisateur();
		Optional<Utilisateur> imam= Optional.of(new Utilisateur());
		Optional<Mosque> mosque= Optional.of(new Mosque());
		if(dto.getIdImam()!=0){
			 imam=utilisateurRepository.findById(dto.getIdImam());
			if(imam.isEmpty()) {
				throw new EntityNotFoundException("Aucun Imam avec l'id "+dto.getIdImam()+" n'a ete trouver",ErrorCodes.IMAM_NOT_FOUND);
			}
			//TODO implementation a verifier
			util=imam.get();
		}
		else if(dto.getIdMosque()!=0){ //TODO Condition de verification de l'existance de la mosque
			mosque=mosqueRepository.findById(dto.getIdMosque());
			if(mosque.isEmpty()) {
				throw new EntityNotFoundException("Aucune mosque avec l'id "+dto.getIdMosque()+" n'a ete trouver",ErrorCodes.MOSQUE_NOT_EXIST);
			}
		}
		//TODO Notification
		Notification(util,dto,mosque);

		return PredicationDto.fromEntity(predicationRepository.save(PredicationDto.toEntity(dto)));
	}

	@Override
	public PredicationDto findById(Integer id) {
		// TODO implementation recherche par id
		if(id==null) {
			throw new EntityNotFoundException("l'Id est NULL");
		}
		Optional<Predication> predication= predicationRepository.findById(id);
		
		return Optional.of(PredicationDto.fromEntity(predication.get())).orElseThrow(() ->
				new EntityNotFoundException(
						"Aucune predication avec l'id ="+id+"n'a ete trouver dans la BD",
						ErrorCodes.PREDICATION_NOT_FOUND)
		);
	}

	@Override
	public Page<PredicationDto> findAll(Pageable pageable) {
		// TODO implementation afficher tout
		return predicationRepository.findAll(pageable)
				.map(PredicationDto::fromEntity);
	}

	@Override
	public void delete(Integer id) {
		// TODO suppression
		if(id==null) {
			throw new EntityNotFoundException("l'Id est NULL");
		}
		
		predicationRepository.deleteById(id);
	}

	//fonction de remplissement de la list des prediication
	List<Predication> addToList(List<Predication> listPredication,Predication predication,int test) {
		if(test==0) {
			listPredication.remove(0);
		}
		listPredication.add(predication);
		return listPredication;
	}
	@Override
	public Page<Predication> findByImam(String nom,Pageable pageable) {
		// TODO find by imam
		// TODO list des imam avec le nom fourni
		return null;
	}

	@Override
	public Page<PredicationDto> findByThemeImamNom(String str,Pageable pageable) {
		// TODO recherche des predication nom imam (nom predicateur)
		List<Utilisateur> listParImam=utilisateurRepository.findUtilisateurByNomAndTypecompte(str,"IMAM");

		if (listParImam.size()!=0){
			return predicationRepository.findPredicationByIdImam(listParImam.get(0).getId(),pageable)
					.map(PredicationDto::fromEntity);
		}
		throw new EntityNotFoundException("Aucune predication avec pour predicateur<<"+str+">> avec role admin  n'a ete trouver");

	}

	@Override
	public Page<PredicationDto> findByTheme(String str,Pageable pageable) {
		return predicationRepository.findPredicationByThemeContaining(str,pageable)
				.map(PredicationDto::fromEntity);
	}

	@Override
	public Page<PredicationDto> findByType(String type, Pageable page) {
		type =type.toUpperCase();
		return predicationRepository.findPredicationByTypeContaining(type,page)
				.map(PredicationDto::fromEntity);
	}
	void Notification(Utilisateur imam,PredicationDto dto,Optional<Mosque> mosque){
		if(imam==null){
			//TODO configuration de la notification pour ceux qui suive les info d'une mosque
			log.info("Envois des notifications");
			List<Suivre> suivreMosque=suivreRepository.findSuivreByMosque(dto.getIdMosque());
			Suivre test;
			Optional<Utilisateur> u;
			for(int i=0; i<suivreMosque.size(); i++){
				test=suivreMosque.get(i);
				NotificationMessage="Une nouvelle predication Dans votre mosque "+mosque.get().getNom()+" .";
				u=utilisateurRepository.findById(test.getUtilisateur());
				NotificationDto notificationDto=new NotificationDto();
				notificationDto.setUtilisateurDto(UtilisateurDto.fromEntity(u.get()));
				notificationDto.setMessage(NotificationMessage);
				notificationRepository.save(NotificationDto.toEntity(notificationDto));
			}
		} else if (mosque.isEmpty()) {
			//TODO configuration de la notification pour nouveau sermont/preche des imam qu'ils suivent
			log.info("Envois des notifications");
			List<Suivre> suivreDto=suivreRepository.findByIdimamsuivie(imam.getId());

			Suivre tst=new Suivre();
			Optional<Utilisateur> ut= Optional.of(new Utilisateur());
			for(int i=0; i<suivreDto.size(); i++){
				tst=suivreDto.get(i);
				NotificationMessage="Une nouvelle predication de votre imam que vous suivé Imam "+imam.getNom();
				//log.info("message {}",NotificationMessage);
				ut=utilisateurRepository.findById(tst.getUtilisateur());
				NotificationDto notificationDto=new NotificationDto();
				notificationDto.setUtilisateurDto(UtilisateurDto.fromEntity(ut.get()));
				notificationDto.setMessage(NotificationMessage);
				notificationRepository.save(NotificationDto.toEntity(notificationDto));
			}
		}
}
}

package com.theh.moduleuser.Services.Impl;

import com.theh.moduleuser.Dto.LocalisationDto;
import com.theh.moduleuser.Exceptions.EntityNotFoundException;
import com.theh.moduleuser.Exceptions.ErrorCodes;
import com.theh.moduleuser.Exceptions.InvalidEntityException;
import com.theh.moduleuser.Model.Localisation;
import com.theh.moduleuser.Repository.LocalisationRepository;
import com.theh.moduleuser.Services.EmailService;
import com.theh.moduleuser.Services.LocalisationService;
import com.theh.moduleuser.Validation.LocalisationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocalisationServiceImpl implements LocalisationService {

	private LocalisationRepository localisationRepository;
	private EmailService emailService;
	
	@Autowired
	public LocalisationServiceImpl(
			LocalisationRepository localisationRepository,
			EmailService emailService
	) {
		this.localisationRepository=localisationRepository;
		this.emailService=emailService;
	}
	
	@Override
	public LocalisationDto save(LocalisationDto dto) {
		//  Auto-generated method stub 
		List<String> errors = LocalisationValidator.validate(dto);
		if(!errors.isEmpty()) {
			//log.error("La localisation est non valid {}",dto);
			throw new InvalidEntityException("Les information de la  localisation ne sont pas valide ", ErrorCodes.LOCALISATION_NOT_VALID,errors);
		}
		//gestion email operationnel
		//emailService.sendEmail("kalifakalifh12@gmail.com","test","test");

		return LocalisationDto.fromEntity(localisationRepository.save(LocalisationDto.toEntity(dto)));
	}

	@Override
	public LocalisationDto findById(Integer id) {
		//  Auto-generated method stub
		if(id==null) {
			throw new InvalidEntityException("L'id entre est NULL");
		}
		Optional<Localisation> localisation= localisationRepository.findById(id);
		
		return Optional.of(LocalisationDto.fromEntity(localisation.get())).orElseThrow(() ->
				new EntityNotFoundException(
						"Aucune mosque avec l'id ="+id+"n'a ete trouver dans la BD",
						ErrorCodes.LOCALISATION_NOT_FOUND)
		);
	}

	@Override
	public  List<LocalisationDto> findLocalisationByVille(String ville) {
		//  Auto-generated method stub
		if(!StringUtils.hasLength(ville)) {
			throw new InvalidEntityException("La ville entrée est NULL");
		}
		//Optional<Localisation> localisation= localisationRepository.findLocalisationByVille(ville);
		
		return this.localisationRepository.findLocalisationByVilleLike(ville).stream()
				.map(LocalisationDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public List<LocalisationDto> findLocalisationByQuartier(String quartier) {
		//  Auto-generated method stub
		if(!StringUtils.hasLength(quartier)) {
			throw new InvalidEntityException("Le quartier entrée est NULL");
		}
		return null;
	}

	@Override
	public List<LocalisationDto> findAll() {
		//  Auto-generated method stub
		return localisationRepository.findAll().stream()
				.map(LocalisationDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public void delete(Integer id) {
		//  Auto-generated method stub
		if(id==null) {
			throw new InvalidEntityException("L'id entre est NULL");
		}
		
		localisationRepository.deleteById(id);
	}
	

}

package com.theh.moduleuser.Services.Impl;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import com.theh.moduleuser.Dto.*;
import com.theh.moduleuser.Exceptions.EntityNotFoundException;
import com.theh.moduleuser.Exceptions.ErrorCodes;
import com.theh.moduleuser.Exceptions.InvalidEntityException;
import com.theh.moduleuser.Model.*;
import com.theh.moduleuser.Repository.*;
import com.theh.moduleuser.Services.PredicationService;
import com.theh.moduleuser.Validation.PredicationValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
		Notification(dto);

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

	@Override
	public void exportData(PrintWriter writer) throws IOException {
		List<PredicationDto> listall =predicationRepository.findAll().stream()
				.map(PredicationDto::fromEntity)
				.collect(Collectors.toList());
		CSVWriter csvWriter = new CSVWriter(writer);
		log.info("Exporting Predication Data");
		// Écrire l'en-tête du CSV
		String[] header = {
				"theme","type","date","heure","duree","nom Imam",
				"Nom Mosque"
		};
		csvWriter.writeNext(header);

		for (PredicationDto predicationDto : listall) {
			String[] data = {
					predicationDto.getTheme(),
					String.valueOf(predicationDto.getType()),
					String.valueOf(predicationDto.getDate()),
					String.valueOf(predicationDto.getDuree()),
					String.valueOf(predicationDto.getHeure()),
					predicationDto.getNomImam(),
					predicationDto.getNomMosque()
			};
			csvWriter.writeNext(data);
		}

		csvWriter.close();

	}

	@Override
	public void importDataToDB(MultipartFile file) throws IOException {
		log.info("Importing data process");
		try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
			 CSVReader csvReader = new CSVReader(reader))
		{
			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
			SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.s");
			String[] nextRecord;
			boolean isHeader = true;
			int i=1;
			while ((nextRecord = csvReader.readNext()) != null) {
				if (isHeader) {  // Ignorer l'en-tête
					isHeader = false;
					continue;
				}
				String[] header = {
						"theme","type","date","heure","duree","nom Imam",
						"Nom Mosque"
				};
				Predication predication= new Predication();
				predication.setTheme(nextRecord[0]);
				predication.setType(TypePredication.valueOf(nextRecord[1]));
				predication.setDate(dateFormat.parse(nextRecord[2]));
				predication.setHeure(format.parse(nextRecord[3]));
				predication.setDuree(format.parse(nextRecord[4]));
				predication.setNomImam(nextRecord[5]);
				predication.setNomMosque(nextRecord[6]);

				predicationRepository.save(predication);
				i++;

			}
		} catch (CsvValidationException e) {
			throw new RuntimeException(e);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
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
	public Page<PredicationDto> findByThemeImamNom(String nomImam,Pageable pageable) {
		// TODO recherche des predication nom imam (nom predicateur)
		//List<Utilisateur> listParImam=utilisateurRepository.findUtilisateurByNomAndTypecompte(str,"IMAM");

			return predicationRepository.findPredicationByNomImam(nomImam,pageable)
					.map(PredicationDto::fromEntity);

	}

	@Override
	public Page<PredicationDto> findByTheme(String str,Pageable pageable) {
		return predicationRepository.findPredicationByThemeContaining(str,pageable)
				.map(PredicationDto::fromEntity);
	}

	@Override
	public Page<PredicationDto> findByType(TypePredication type, Pageable page) {

		return predicationRepository.findPredicationByType(type,page)
				.map(PredicationDto::fromEntity);
	}
	void Notification(PredicationDto dto){
		NotificationDto notificationDto= new NotificationDto();
		notificationDto.setType(TypeNotification.PREDICATION);
		notificationDto.setRead(false);
		notificationDto.setDateTime(LocalDateTime.now());
		notificationDto.setMessage("Une nouvelle predication de Type:"+dto.getType()+" Date: "+
				dto.getDate()+" theme: "+dto.getTheme()+" Par l'imam: "+dto.getNomImam()+" Lieux: "+dto.getNomMosque());
	}
}
package com.theh.moduleuser.Repository;

import com.theh.moduleuser.Dto.MosqueDto;
import com.theh.moduleuser.Model.Mosque;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Date;
import java.util.List;


public interface MosqueRepository extends JpaRepository<Mosque,Integer>{

		// comptage des entité
	int countAllByCreationDateAfter(Date date);
	int countByIsVendredi(boolean isVendredi);
	int countMosqueByLocalisation_VilleContaining(String ville);
	int countMosqueByIsVendrediAndLocalisation_VilleContaining(boolean isvendredi,String ville);

		// tri
	List<Mosque> findByOrderByCreationDateDesc(); //plus recent au plus ancien
	List<Mosque> findByOrderByCreationDateAsc(); // plus ancien au plus recent
	// Recherche par type
	MosqueDto findMosqueByNom(String nom);
	List<Mosque> findMosqueByNomContaining(String nom);
	List<Mosque> findMosqueByIsVendredi(Boolean Vendredi);// a supprimer

		// rechercher par localisastion
	Page<Mosque> findMosqueByLocalisationVilleContainingOrLocalisationPays(String ville,String pays,Pageable pageable);
	//List<MosqueDto> findMosqueByLocalisationContaining(String pays);
	Page<Mosque> findMosqueByLocalisationPaysContaining(String pays,Pageable pageable);

		// Operation sur les dates de creation
	List<Mosque> findByCreationDateAfter(Instant date);
//	List<MosqueDto> findByCreationDateBetween(Date start, Date end);
	List<Mosque> findByCreationDateGreaterThanEqual(Instant date);
//		// Operation sur les dates de modification
	List<MosqueDto> findByLastModifiedDateAfter(Instant date);
//	List<MosqueDto> findByLastModifiedDateBetween(Instant start, Instant end);
	List<Mosque> findByLastModifiedDateGreaterThanEqual(Instant date);

	//TODO test de pagination
	Pageable firstPageWithTwoElements = PageRequest.of(0, 10);
	List<Mosque> findAll(Sort sort);
	Page<Mosque> findAll(Pageable page);
	Page<Mosque> findByOrderByCreationDateAsc(Pageable page);
	Page<Mosque> findByOrderByCreationDateDesc(Pageable page);
	Page<Mosque> findMosqueByNomContaining(String nom,Pageable page);
	Page<Mosque> findMosqueByIsVendredi(Boolean Vendredi,Pageable page);
}

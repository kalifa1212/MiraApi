package com.theh.moduleuser.Repository;

import com.theh.moduleuser.Model.Localisation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LocalisationRepository extends JpaRepository<Localisation,Integer> {
	
	//Page<Localisation> findLocalisationByVille(String ville,Pageable page);
	//Localisation findLocalisationByVilleEquals(String quartier);
	Localisation findByVille_NameIgnoreCase(String quartier);
	//Page<Localisation> findLocalisationByPaysLike(String pays, Pageable page);
	//Page<Localisation> findLocalisationByVilleLike(String str,Pageable page);
	Page<Localisation> findByVille_NameIgnoreCase(String str,Pageable page);

	Optional<Localisation> findByVille_NameAndVille_Pays_Name(String villeName, String paysName);
}

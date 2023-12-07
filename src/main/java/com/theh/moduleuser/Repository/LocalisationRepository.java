package com.theh.moduleuser.Repository;

import com.theh.moduleuser.Model.Localisation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocalisationRepository extends JpaRepository<Localisation,Integer> {
	
	List<Localisation> findLocalisationByVille(String ville);
	//List<Localisation> findLocalisationByQuartier(String quartier);
	List<Localisation> findLocalisationByPaysLike(String pays);
	List<Localisation> findLocalisationByVilleLike(String str);
}

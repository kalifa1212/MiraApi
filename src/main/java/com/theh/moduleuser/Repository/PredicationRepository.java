package com.theh.moduleuser.Repository;


import com.theh.moduleuser.Model.Predication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PredicationRepository extends JpaRepository<Predication, Integer> {

	List<Predication> findById(int a);
	//Predication findById(int a);
	List<Predication> findPredicationByIdMosque(int a);
	List<Predication> findPredicationByThemeContaining(String str);
	List<Predication> findPredicationByTypeContaining(String str);
	List<Predication> findPredicationByIdImam(int a);

}

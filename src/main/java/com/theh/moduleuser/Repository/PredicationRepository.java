package com.theh.moduleuser.Repository;


import com.theh.moduleuser.Dto.TypePredication;
import com.theh.moduleuser.Model.Predication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PredicationRepository extends JpaRepository<Predication, Integer> {

	List<Predication> findById(int a);
	//Predication findById(int a);
	Page<Predication> findPredicationByNomImam(String nomImam,Pageable pageable);
	Page<Predication> findPredicationByThemeContaining(String str,Pageable pageable);
	Page<Predication> findPredicationByType(TypePredication type, Pageable pageable);
	Page<Predication> findPredicationByIdImam(int a,Pageable pageable);

}

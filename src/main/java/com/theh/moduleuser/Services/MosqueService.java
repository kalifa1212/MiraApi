package com.theh.moduleuser.Services;

import com.theh.moduleuser.Dto.MosqueDto;

import java.util.List;

public interface MosqueService {

	MosqueDto save(MosqueDto dto, Boolean update);
	MosqueDto findById(Integer id);
	List<MosqueDto> findByNom(String str);
	List<MosqueDto> findMosqueByVilleOrQuartier(String str);
	List<MosqueDto> findByVendredis(Boolean a);
	List<MosqueDto> findAll();
	void delete(Integer id);
}

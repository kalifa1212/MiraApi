package com.theh.moduleuser.Services;

import com.theh.moduleuser.Dto.MosqueDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public interface MosqueService {

	MosqueDto save(MosqueDto dto, Boolean update);
	void exportData(PrintWriter writer) throws IOException;
	void importDataToDB(MultipartFile file)throws IOException;
	MosqueDto findById(Integer id);
	List<MosqueDto> findByNom(String str);
	List<MosqueDto> findMosqueByVilleOrQuartier(String str);
	Page<MosqueDto> findByVendredis(Boolean a, Pageable page);
	Page<MosqueDto> findAll(Pageable page);
	Page<MosqueDto> findAllByName(String str,Pageable page);
	void delete(Integer id);
}

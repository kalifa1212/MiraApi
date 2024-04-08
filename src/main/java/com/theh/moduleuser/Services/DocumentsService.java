package com.theh.moduleuser.Services;


import com.theh.moduleuser.Dto.DocumentsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DocumentsService {

	DocumentsDto save(Integer idPredication, MultipartFile multipartFile)throws IOException;

//	int countAll();
//	int countAllByTypeDoc(String typedoc);
	DocumentsDto findById(Integer id);
	DocumentsDto findByPredication(Integer id);
	Page<DocumentsDto> findAll(Pageable pageable);
	void delete(Integer id);
}

package com.theh.moduleuser.Services;


import com.theh.moduleuser.Dto.DocumentsDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DocumentsService {

	DocumentsDto save(Integer idPredication, MultipartFile multipartFile)throws IOException;

//	int countAll();
//	int countAllByTypeDoc(String typedoc);
	DocumentsDto findById(Integer id);
	List<DocumentsDto> findAll();
	void delete(Integer id);
}

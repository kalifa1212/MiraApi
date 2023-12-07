package com.theh.moduleuser.Services;


import com.theh.moduleuser.Dto.DocumentsDto;

import java.util.List;

public interface DocumentsService {

	DocumentsDto save(DocumentsDto  dto);

//	int countAll();
//	int countAllByTypeDoc(String typedoc);
	DocumentsDto findById(Integer id);
	List<DocumentsDto> findAll();
	void delete(Integer id);
}

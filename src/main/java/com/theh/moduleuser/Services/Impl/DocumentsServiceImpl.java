package com.theh.moduleuser.Services.Impl;

import com.theh.moduleuser.Dto.DocumentsDto;
import com.theh.moduleuser.Exceptions.EntityNotFoundException;
import com.theh.moduleuser.Exceptions.ErrorCodes;
import com.theh.moduleuser.Exceptions.InvalidEntityException;
import com.theh.moduleuser.Model.Documents;
import com.theh.moduleuser.Repository.DocumentsRepository;
import com.theh.moduleuser.Services.DocumentsService;
import com.theh.moduleuser.Validation.DocumentsValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DocumentsServiceImpl implements DocumentsService {
	
	private DocumentsRepository documentsRepository;
	
	@Autowired
	public DocumentsServiceImpl(
			DocumentsRepository documentsRepository
	) {
		this.documentsRepository=documentsRepository;
	}
	
	@Override
	public DocumentsDto save(DocumentsDto dto) {
		// TODO Auto-generated method stub
		List<String> errors = DocumentsValidator.validate(dto);
		if(!errors.isEmpty()) {
			log.error("La documents est non valide {}",dto);
			throw new InvalidEntityException("La documents n'est pas valide ", ErrorCodes.DOCUMENTS_NOT_VALID,errors);
		}
		return DocumentsDto.fromEntity(documentsRepository.save(DocumentsDto.toEntity(dto)));
	}

	@Override
	public DocumentsDto findById(Integer id) {
		// TODO Auto-generated method stub
		if(id==null) {
			log.error("l'id de la documents est null");
			return null;
		}
		Optional<Documents> documents= documentsRepository.findById(id);
		
		return Optional.of(DocumentsDto.fromEntity(documents.get())).orElseThrow(() ->
				new EntityNotFoundException(
						"Aucune documents avec l'id ="+id+"n'a ete trouver dans la BD",
						ErrorCodes.MOSQUE_NOT_FOUND)
		);
	}

	@Override
	public List<DocumentsDto> findAll() {
		// TODO Auto-generated method stub
		return documentsRepository.findAll().stream()
				.map(DocumentsDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		if(id==null) {
			log.error("l'id de la documents est null");
			return ;
		}
		
		documentsRepository.deleteById(id);
	}

}

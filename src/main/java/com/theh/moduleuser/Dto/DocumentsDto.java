package com.theh.moduleuser.Dto;

import com.theh.moduleuser.Model.Documents;
import lombok.Builder;
import lombok.Data;

@Data @Builder 
public class DocumentsDto {

	private Integer id;

	private String type_doc;

	private String nom;
	
	private String fichier;

	private PredicationDto predication;

	public boolean isEmpty() {
		if(id==null) {
		return true;}
		else {
			return false;
		}
	}

	public static DocumentsDto fromEntity(Documents documents) {
		if(documents==null) {
			return null;
		}
		return DocumentsDto.builder()
				.id(documents.getId())
				.nom(documents.getNom())
				.type_doc(documents.getType_doc())
				.fichier(documents.getFichier())
			//	.FileName(documents.getFileName())
				.predication(PredicationDto.fromEntity(documents.getPredication()))
				.build();
	}

	public static Documents toEntity(DocumentsDto documentsDto) {
		if(documentsDto==null) {
			return null;
		}
		Documents documents = new Documents();
		documents.setId(documentsDto.getId());
		documents.setNom(documentsDto.getNom());
		documents.setFichier(documentsDto.getFichier());
	//	documents.setFileName(documentsDto.getFileName());
		documents.setType_doc(documentsDto.getType_doc());
		documents.setPredication(PredicationDto.toEntity(documentsDto.getPredication()));
		return documents;
	}
}

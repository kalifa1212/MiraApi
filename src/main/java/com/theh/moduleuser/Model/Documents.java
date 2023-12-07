package com.theh.moduleuser.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name="documents")
@EqualsAndHashCode(callSuper=true)
@Data @NoArgsConstructor @AllArgsConstructor 
public class Documents extends AbstractEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="type_doc")
	private String type_doc;
	
	@Column(name="nom")
	private String nom;
	
	@Column(name="fichier")
	private String fichier;

	@OneToOne
	private Predication predication;

}

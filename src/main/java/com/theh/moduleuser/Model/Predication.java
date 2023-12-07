package com.theh.moduleuser.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Table(name="predication")
@EqualsAndHashCode(callSuper=true)
@Data @NoArgsConstructor @AllArgsConstructor
public class Predication extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="date")
	private Date date;
	
	@Column(name="theme")
	private String theme;
	
	@Column(name="fichier")
	private String fichier;
	
	@Column(name="type")// sermon, preche ou conference.
	private String type;
	
	@Column(name="idimam")
	private Integer idImam;
	
	@Column(name="idmosque")
	private Integer idMosque;

	@Column(name="info")
	private String  Info;

	@Column(name="type_doc")
	private String type_doc;

	@OneToOne(mappedBy = "predication")
	private Documents documents ;
	
}

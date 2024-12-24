package com.theh.moduleuser.Model;

import com.theh.moduleuser.Dto.TypePredication;
import jakarta.persistence.*;
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

	@Column(name="heure")
	@Temporal(TemporalType.TIME)
	private Date heure;

	@Column(name="duree")
	@Temporal(TemporalType.TIME)
	private Date duree;
	
	@Column(name="theme")
	private String theme;
	
	@Column(name="fichier")
	private String fichier;
	
	@Column(name="type")// sermon, preche ou conference.
	private TypePredication type;
	
	@Column(name="idimam")
	private Integer idImam;
	
	@Column(name="idmosque")
	private Integer idMosque;

	@Column(name="info")
	private String  Info;

	@Column(name="nomimam")
	private String  nomImam;

	@Column(name="nommosque")
	private String  nomMosque;

	@Column(name="type_doc")
	private String type_doc;

	@OneToOne(mappedBy = "predication")
	private Documents documents ;
	
}

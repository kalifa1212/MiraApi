package com.theh.moduleuser.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

@Entity
@Table(name="localisation")
@EqualsAndHashCode(callSuper=true)
@Data @NoArgsConstructor @AllArgsConstructor 
public class Localisation extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String rue;    // optionnel : nom de rue, quartier

	private String latitude;
	private String longitude;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ville_id")
	private Ville ville;

}

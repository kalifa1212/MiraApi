package com.theh.moduleuser.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="mosque")
@EqualsAndHashCode(callSuper=true)
@Data @NoArgsConstructor @AllArgsConstructor
public class Mosque extends AbstractEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="nom",length = 50)
	private String nom;

	@Column(name="code",length = 20)
	private String code;
	
	@Column(name="superficie")
	private int superficie;
	
	@Column(name="photo")
	private String photo;

	@Column(name="description")
	private String description;

	@Column(name="imam")
	private String imam;

	@Lob
	//@Column(name = "imagedata", columnDefinition="BLOB")
	private byte[] imagedata;
	
	@Column(name="balte")
	@Temporal(TemporalType.TIME)
	private Date balte;
	
	@Column(name="asr")
	@Temporal(TemporalType.TIME)
	private Date asr;
	
	@Column(name="magrib")
	@Temporal(TemporalType.TIME)
	private Date magrib;
	
	@Column(name="icha")
	@Temporal(TemporalType.TIME)
	private Date icha;
	
	@Column(name="soub")
	@Temporal(TemporalType.TIME)
	private Date soub;
	
	@Column(name="zour")
	@Temporal(TemporalType.TIME)
	private Date zour;
	
	@Column(name="djouma")
	@Temporal(TemporalType.TIME)
	private Date djouma;
	
	@Column(name="vendredi")
	private Boolean isVendredi;

	@Column(name="longitude")
	private double longitude;

	@Column(name="latitude")
	private double latitude;

	@Column(name="quartier")
	private String quartier;

	@ManyToOne
	private Localisation localisation;

//	@ManyToMany(mappedBy = "followedMosques")
//	private Set<Utilisateur> followers = new HashSet<>();
	// test
	@OneToMany(mappedBy = "mosq")
	private Set<Suivre> followers;

//	public void addFollower(Utilisateur utilisateur) {
//		this.followers.add(utilisateur);
//		//utilisateur.getFollowedMosques().add(this);
//	}

//	public void removeFollower(Utilisateur utilisateur) {
//		followers.remove(utilisateur);
//		//utilisateur.getFollowedMosques().remove(this);
//	}

}
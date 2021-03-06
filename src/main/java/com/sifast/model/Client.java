package com.sifast.model;

// Generated 16 mars 2015 12:37:03 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Client generated by hbm2java
 */
@Entity
@Table(name = "Client", schema = "dbo", catalog = "reclamation")
public class Client implements java.io.Serializable {

	/**
	 * G�n�rer serial version ID
	 */
	private static final long serialVersionUID = 6186906556941475230L;
	private int idClient;
	private String nom;
	private String prenom;
	private String email;
	private Integer numTel;
	private Set<Reclamation> reclamations = new HashSet<Reclamation>(0);

	public Client() {
	}

	public Client(int idClient, String nom, String prenom, String email) {
		this.idClient = idClient;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
	}

	public Client(int idClient, String nom, String prenom, String email, Integer numTel, Set<Reclamation> reclamations) {
		this.idClient = idClient;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.numTel = numTel;
		this.reclamations = reclamations;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idClient", unique = true, nullable = false)
	public int getIdClient() {
		return this.idClient;
	}

	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}

	@Column(name = "nom", nullable = false, length = 15)
	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@Column(name = "prenom", nullable = false, length = 15)
	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	@Column(name = "email", nullable = false, length = 30)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "numTel")
	public Integer getNumTel() {
		return this.numTel;
	}

	public void setNumTel(Integer numTel) {
		this.numTel = numTel;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
	public Set<Reclamation> getReclamations() {
		return this.reclamations;
	}

	public void setReclamations(Set<Reclamation> reclamations) {
		this.reclamations = reclamations;
	}

}

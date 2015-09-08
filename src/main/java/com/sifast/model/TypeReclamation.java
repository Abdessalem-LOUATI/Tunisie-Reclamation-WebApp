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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * TypeReclamation generated by hbm2java
 */
@Entity
@Table(name = "TypeReclamation", schema = "dbo", catalog = "reclamation")
public class TypeReclamation implements java.io.Serializable {

	/**
	 * G�n�rer serial version ID
	 */
	private static final long serialVersionUID = 1345180420929421491L;
	private int idTypeRec;
	private Institution institution;
	private String type;
	private Set<Reclamation> reclamations = new HashSet<Reclamation>(0);

	public TypeReclamation() {
	}

	public TypeReclamation(int idTypeRec, Institution institution, String type) {
		this.idTypeRec = idTypeRec;
		this.institution = institution;
		this.type = type;
	}

	public TypeReclamation(int idTypeRec, Institution institution, String type, Set<Reclamation> reclamations) {
		this.idTypeRec = idTypeRec;
		this.institution = institution;
		this.type = type;
		this.reclamations = reclamations;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idTypeRec", unique = true, nullable = false)
	public int getIdTypeRec() {
		return this.idTypeRec;
	}

	public void setIdTypeRec(int idTypeRec) {
		this.idTypeRec = idTypeRec;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "nomInstitFK", nullable = false)
	public Institution getInstitution() {
		return this.institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	@Column(name = "type", nullable = false, length = 50)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "typeReclamation")
	public Set<Reclamation> getReclamations() {
		return this.reclamations;
	}

	public void setReclamations(Set<Reclamation> reclamations) {
		this.reclamations = reclamations;
	}
}
package main.java.fr.batis.entites;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="CPT_RENDU")
public class CompteRenduJournalier implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4506798469660506576L;
	private Long id;
	private String date;
	private String titre;
	private String description;
	private String typeSuivi;
	private PhaseConstruction phaseConstruction;
	
	
	
	public CompteRenduJournalier() {
		super();
	}

	


	/**
	 * @param date
	 * @param titre
	 * @param description
	 * @param typeSuivi
	 * @param phaseConstruction
	 */
	public CompteRenduJournalier(String date, String titre, String description, String typeSuivi, PhaseConstruction phaseConstruction) {
		super();
		this.date = date;
		this.titre = titre;
		this.description = description;
		this.typeSuivi = typeSuivi;
		this.phaseConstruction = phaseConstruction;
	}




	/**
	 * @return the id
	 */
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	public Long getId() {
		return id;
	}



	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}


	@Column(name="DATE")
	public String getDate() {
		return date;
	}
	
	
	public void setDate(String date) {
		this.date = date;
	}
	

	/**
	 * @return the titre
	 */
	

	@Column(name="TITRE" )
	public String getTitre() {
		return titre;
	}




	/**
	 * @param titre the titre to set
	 */
	public void setTitre(String titre) {
		this.titre = titre;
	}



	/**
	 * @return the description
	 */
	@Type(type="text")
	@Column(name="DESCRIPTION" )
	public String getDescription() {
		return description;
	}



	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}




	@Column(name="TYPE_SUIVI")
	public String getTypeSuivi() {
		return typeSuivi;
	}

	
	public void setTypeSuivi(String typeSuivi) {
		this.typeSuivi = typeSuivi;
	}




	/**
	 * @return the phaseConstruction
	 */
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PHASE")
	public PhaseConstruction getPhaseConstruction() {
		return phaseConstruction;
	}




	/**
	 * @param phaseConstruction the phaseConstruction to set
	 */
	public void setPhaseConstruction(PhaseConstruction phaseConstruction) {
		this.phaseConstruction = phaseConstruction;
	}

	
}

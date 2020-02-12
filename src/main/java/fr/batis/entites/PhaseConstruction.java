package main.java.fr.batis.entites;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import main.java.fr.batis.enumeration.StatutPhase;

//@Embeddable
@Entity
@Table(name = "PHASE_CONSTRUCT")
public class PhaseConstruction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -664813463632352689L;
	private Long id;
	private String nom;
	private String description;
	private List<Devis> listDevis;
	private String dateDebutPrevu;
	private String dateFinPrevu;
	private String dateDebutReel;
	private String dateFinReel;
	private StatutPhase codeStatut;
	private Integer numero;
	private List<CompteRenduJournalier> compteRenduJournalier;
	private Chantier chantier;

	/**
	 * 
	 * @param nom
	 * @param description
	 * @param devis
	 * @param dateDebutPrevu
	 * @param dateFinPrevu
	 * @param dateDebutReel
	 * @param dateFinReel
	 * @param codeStatut
	 * @param numero
	 * @param compteRenduJournalier
	 * @param chantier
	 */
	public PhaseConstruction(String nom, String description, String dateDebutPrevu, String dateFinPrevu,
			String dateDebutReel, String dateFinReel, StatutPhase codeStatut, Integer numero,
			List<CompteRenduJournalier> compteRenduJournalier, Chantier chantier) {
		super();
		this.nom = nom;
		this.description = description;
		this.dateDebutPrevu = dateDebutPrevu;
		this.dateFinPrevu = dateFinPrevu;
		this.dateDebutReel = dateDebutReel;
		this.dateFinReel = dateFinReel;
		this.codeStatut = codeStatut;
		this.numero = numero;
		this.compteRenduJournalier = compteRenduJournalier;
		this.chantier = chantier;
	}

	public PhaseConstruction() {
		listDevis = new ArrayList<Devis>();
		compteRenduJournalier = new ArrayList<CompteRenduJournalier>();
	}

	/**
	 * @return the id
	 */
	@Id
	@Column(name = "ID_PHASE")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NOM_PHASE")
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the dateDebutPrevu
	 */
	@Column(name = "DATE_DEBUT_PREVU")
	public String getDateDebutPrevu() {
		return dateDebutPrevu;
	}

	/**
	 * @param dateDebutPrevu the dateDebutPrevu to set
	 */
	public void setDateDebutPrevu(String dateDebutPrevu) {
		this.dateDebutPrevu = dateDebutPrevu;
	}

	/**
	 * @return the dateFinPrevu
	 */
	@Column(name = "DATE_FIN_PREVU")
	public String getDateFinPrevu() {
		return dateFinPrevu;
	}

	/**
	 * @param dateFinPrevu the dateFinPrevu to set
	 */
	public void setDateFinPrevu(String dateFinPrevu) {
		this.dateFinPrevu = dateFinPrevu;
	}

	/**
	 * @return the dateDebutReel
	 */
	@Column(name = "DATE_DEBUT_REEL")
	public String getDateDebutReel() {
		return dateDebutReel;
	}

	/**
	 * @param dateDebutReel the dateDebutReel to set
	 */
	public void setDateDebutReel(String dateDebutReel) {
		this.dateDebutReel = dateDebutReel;
	}

	/**
	 * @return the dateFinReel
	 */
	@Column(name = "DATE_FIN_REEL")
	public String getDateFinReel() {
		return dateFinReel;
	}

	/**
	 * @param dateFinReel the dateFinReel to set
	 */
	public void setDateFinReel(String dateFinReel) {
		this.dateFinReel = dateFinReel;
	}

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "phaseConstruction", orphanRemoval = true)
	// @JoinColumn(name="ID_SUIVI")
	@Fetch(FetchMode.SUBSELECT)
	public List<CompteRenduJournalier> getCompteRenduJournalier() {
		return compteRenduJournalier;
	}

	/**
	 * @return the codeStatut
	 */
	@Column(name = "CODE_STATUT")
	public StatutPhase getCodeStatut() {
		return codeStatut;
	}

	/**
	 * @param codeStatut the codeStatut to set
	 */
	public void setCodeStatut(StatutPhase codeStatut) {
		this.codeStatut = codeStatut;
	}

	public void setCompteRenduJournalier(List<CompteRenduJournalier> compteRenduJournalier) {
		this.compteRenduJournalier = compteRenduJournalier;
	}

	@Column(name = "NUMERO")
	public Integer getNumero() {
		return numero;
	}

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "phase", orphanRemoval = true)
	// @JoinColumn(name="ID_SUIVI")
	@Fetch(FetchMode.SUBSELECT)
	public List<Devis> getListDevis() {
		return listDevis;
	}

	public void setListDevis(List<Devis> listDevis) {
		this.listDevis = listDevis;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	/**
	 * @return the chantier
	 */

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_CHANTIER")
	public Chantier getChantier() {
		return chantier;
	}

	/**
	 * @param chantier the chantier to set
	 */
	public void setChantier(Chantier chantier) {
		this.chantier = chantier;
	}

}

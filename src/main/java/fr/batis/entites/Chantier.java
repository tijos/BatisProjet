package main.java.fr.batis.entites;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.Hibernate;

@Entity
@Table(name = "CHANTIER")

public class Chantier implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5605954863845422951L;

	private Long id;
	private String nomProjet;
	private String typeTravaux;
	private String typeChantier;
	private boolean maisonSimple;
	private boolean maisonEnEtage;
	private int niveau;
	private String nbreEtape;
	private String siteProjet;
	private String statut;
	private VoletAdmnistratif voletAdministratif;
	private List<PhaseConstruction> phasesConstruction;
	private String dateDebutChantier;
	private String dateFinChantier;
	private String description;
	private String dateLivraison;
	private String execution;
	private String financement;
	private Set<Employe> listeEmployes;
	private boolean actif;
	private boolean closed;

	public Chantier(String nomProjet, String typeProjet, String typeChantier, boolean maisonSimple,
			boolean maisonEnEtage, int niveau, String nbreEtape, String siteProjet, String statut,
			String dateDebutChantier, String dateFinChantier) {
		super();
		this.nomProjet = nomProjet;
		this.typeTravaux = typeProjet;
		this.typeChantier = typeChantier;
		this.maisonSimple = maisonSimple;
		this.maisonEnEtage = maisonEnEtage;
		this.niveau = niveau;
		this.nbreEtape = nbreEtape;
		this.siteProjet = siteProjet;
		this.statut = statut;
		this.dateDebutChantier = dateDebutChantier;
		this.dateFinChantier = dateFinChantier;
		this.actif = true;
	}

	/**
	 * 
	 */
	public Chantier() {
		super();
		listeEmployes = new HashSet<Employe>();
		phasesConstruction = new ArrayList<PhaseConstruction>();
		Hibernate.initialize(phasesConstruction);
		this.actif = true;
	}

	public Chantier(String string, String string2) {
		this.nomProjet = string;
		this.siteProjet = string2;
		listeEmployes = new HashSet<Employe>();
		phasesConstruction = new ArrayList<PhaseConstruction>();
		this.actif = true;
	}

	/**
	 * @return the id
	 */
	@Id
	@Column(name = "ID_CHANTIER")
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

	/**
	 * @return the nomProjet
	 */

	@Column(name = "NOM_CHANTIER")
	public String getNomProjet() {
		return nomProjet;
	}

	/**
	 * @param nomProjet the nomProjet to set
	 */
	public void setNomProjet(String nomProjet) {
		this.nomProjet = nomProjet;
	}

	/**
	 * @return the TypeTravaux
	 */
	@Column(name = "TYPE_PROJET")
	public String getTypeTravaux() {
		return typeTravaux;
	}

	/**
	 * @param TypeTravaux the TypeTravaux to set
	 */
	public void setTypeTravaux(String typeTravaux) {
		this.typeTravaux = typeTravaux;
	}

	/**
	 * @return the TypeChantier
	 */

	@Column(name = "TYPE_CHANTIER")
	public String getTypeChantier() {
		return typeChantier;
	}

	/**
	 * @param TypeChantier the TypeChantier to set
	 */
	public void setTypeChantier(String typeChantier) {
		this.typeChantier = typeChantier;
	}

	/**
	 * @return the maisonSimple
	 */
	public boolean isMaisonSimple() {
		return maisonSimple;
	}

	/**
	 * @param maisonSimple the maisonSimple to set
	 */
	public void setMaisonSimple(boolean maisonSimple) {
		this.maisonSimple = maisonSimple;
	}

	/**
	 * @return the maisonEnEtage
	 */
	public boolean isMaisonEnEtage() {
		return maisonEnEtage;
	}

	/**
	 * @param maisonEnEtage the maisonEnEtage to set
	 */
	public void setMaisonEnEtage(boolean maisonEnEtage) {
		this.maisonEnEtage = maisonEnEtage;
	}

	/**
	 * @return the niveau
	 */

	@Column(name = "NIVEAU")
	public Integer getNiveau() {
		return niveau;
	}

	/**
	 * @param niveau the niveau to set
	 */
	public void setNiveau(int niveau) {
		this.niveau = niveau;
	}

	/**
	 * @return the nbreEtape
	 */

	@Column(name = "NBRE_ETAPES")
	public String getNbreEtape() {
		return nbreEtape;
	}

	/**
	 * @param nbreEtape the nbreEtape to set
	 */
	public void setNbreEtape(String nbreEtape) {
		this.nbreEtape = nbreEtape;
	}

	/**
	 * @return the siteProjet
	 */
	@Column(name = "SITE_CHANTIER")
	public String getSiteProjet() {
		return siteProjet;
	}

	/**
	 * @param siteProjet the siteProjet to set
	 */
	public void setSiteProjet(String siteProjet) {
		this.siteProjet = siteProjet;
	}

	/**
	 * @return the statut
	 */
	public String getStatut() {
		return statut;
	}

	/**
	 * @param statut the statut to set
	 */
	public void setStatut(String statut) {
		this.statut = statut;
	}

	/**
	 * @return the dateDebutChantier
	 */
	@Column(name = "DATE_DEBUT")
	public String getDateDebutChantier() {
		return dateDebutChantier;
	}

	/**
	 * @param dateDebut the dateDebutChantier to set
	 */
	public void setDateDebutChantier(String dateDebut) {
		this.dateDebutChantier = dateDebut;
	}

	/**
	 * @return the dateFinChantier
	 */
	public String getDateFinChantier() {
		return dateFinChantier;
	}

	/**
	 * @param dateFinChantier the dateFinChantier to set
	 */
	public void setDateFinChantier(String dateFinChantier) {
		this.dateFinChantier = dateFinChantier;
	}

	/**
	 * @return the phasesConstruction
	 */

	@OneToMany(mappedBy = "chantier", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	public List<PhaseConstruction> getPhasesConstruction() {
		return phasesConstruction;
	}

	/**
	 * @param phasesConstruction the phasesConstruction to set
	 */
	public void setPhasesConstruction(List<PhaseConstruction> phasesConstruction) {
		this.phasesConstruction = phasesConstruction;
	}

	/**
	 * @return the listeEmployes
	 */
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "listChantier")
//	@Fetch(value = FetchMode.SUBSELECT)
	public Set<Employe> getListeEmployes() {
		return listeEmployes;
	}

	/**
	 * @param listeEmployes the listeEmployes to set
	 */
	public void setListeEmployes(Set<Employe> listeEmployes) {
		this.listeEmployes = listeEmployes;
	}

	/**
	 * @return the description
	 */
	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the voletAdministratif
	 */
	// TODO BASE
	@OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
	public VoletAdmnistratif getVoletAdministratif() {
		return voletAdministratif;
	}

	/**
	 * @param voletAdministratif the voletAdministratif to set
	 */
	public void setVoletAdministratif(VoletAdmnistratif voletAdministratif) {
		this.voletAdministratif = voletAdministratif;
	}

	/**
	 * @return the dateLivraison
	 */
	@Column(name = "LIVRAISON")
	public String getDateLivraison() {
		return dateLivraison;
	}

	/**
	 * @param dateLivraison the dateLivraison to set
	 */
	public void setDateLivraison(String dateLivraison) {
		this.dateLivraison = dateLivraison;
	}

	/**
	 * @return the execution
	 */
	@Column(name = "EXECUTION")
	public String getExecution() {
		return execution;
	}

	/**
	 * @param execution the execution to set
	 */
	public void setExecution(String execution) {
		this.execution = execution;
	}

	/**
	 * @return the financement
	 */
	@Column(name = "FINANCEMENT")
	public String getFinancement() {
		return financement;
	}

	/**
	 * @param financement the financement to set
	 */
	public void setFinancement(String financement) {
		this.financement = financement;
	}

	/**
	 * @return the isActif
	 */
	@Column(name = "IS_ACTIF")
	public boolean isActif() {
		return actif;
	}

	/**
	 * @param isActif the isActif to set
	 */
	public void setActif(boolean actif) {
		this.actif = actif;
	}

	/**
	 * @return the closed
	 */
	@Column(name = "IS_CLOSED")
	public boolean isClosed() {
		return closed;
	}

	/**
	 * @param closed the closed to set
	 */
	public void setClosed(boolean isClosed) {
		this.closed = isClosed;
	}

}

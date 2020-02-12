package main.java.fr.batis.entites;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EMPLOYE")

public class Employe implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2859822808853985604L;
	private Long id;
	private String nom;
	private String prenom;
	private String dateNaissance;

	private String salaire;
	private String devise;
	private String frequenceReunumeration;
	private String dateEmbauche;
	private String autresInfos;
	private String telephone;
	private String email;
	private List<Chantier> listChantier;
	private Adresse adresse;
	private Set<Role> listRole;

	public Employe() {
		super();
	}

	public Employe(String nom, String prenom, String dateNaissance, String descriptionQualification,
			Integer codeQualification, String salaire, String devise, String frequenceReunumeration, Adresse adresse,
			String dateEmbauche, String autresInfos, String telephone, String email, List<Chantier> listChantier,
			Set<Role> roles) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaissance = dateNaissance;

		this.salaire = salaire;
		this.devise = devise;
		this.frequenceReunumeration = frequenceReunumeration;
		this.adresse = adresse;
		this.dateEmbauche = dateEmbauche;
		this.autresInfos = autresInfos;
		this.telephone = telephone;
		this.email = email;
		this.listChantier = listChantier;
		this.listRole = roles;
	}

	public Employe(String string, String string2, String string3) {
		this.nom = string;
		this.prenom = string2;
		this.email = string3;
	}

	@Id
	@Column(name = "ID_EMPLOYE")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NOM")
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@Column(name = "PRENOM")
	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	// @Temporal(TemporalType.DATE)
	@Column(name = "DATE_NAISSANCE")
	public String getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(String age) {
		this.dateNaissance = age;
	}

	/*
	 * @Column(name = "CODE_QUALIF") public Integer getCodeQualification() { return
	 * codeQualification; }
	 * 
	 * 
	 * public void setCodeQualification(Integer codeQualification) {
	 * this.codeQualification = codeQualification; }
	 * 
	 * @Column(name = "ROLE") public String getDescriptionQualification() { return
	 * descriptionQualification; }
	 * 
	 * 
	 * public void setDescriptionQualification(String descriptionQualification) {
	 * this.descriptionQualification = descriptionQualification; }
	 */

	@Column(name = "SALAIRE")
	public String getSalaire() {
		return salaire;
	}

	public void setSalaire(String salaire) {
		this.salaire = salaire;
	}

	/**
	 * @return the adresse
	 */
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_ADRESSE")
	public Adresse getAdresse() {
		return adresse;
	}

	/**
	 * @param adresse the adresse to set
	 */

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	@Column(name = "UNITE_PAIE")
	public String getFrequenceReunumeration() {
		return frequenceReunumeration;
	}

	public void setFrequenceReunumeration(String frequenceReunumeration) {
		this.frequenceReunumeration = frequenceReunumeration;
	}

	@Column(name = "DATE_EMBAUCHE")
	public String getDateEmbauche() {
		return dateEmbauche;
	}

	public void setDateEmbauche(String dateEmbauche) {
		this.dateEmbauche = dateEmbauche;
	}

	@Column(name = "DEVISE")
	public String getDevise() {
		return devise;
	}

	public void setDevise(String devise) {
		this.devise = devise;
	}

	@Column(name = "AUTRES_INFOS")
	public String getAutresInfos() {
		return autresInfos;
	}

	public void setAutresInfos(String autresInfos) {
		this.autresInfos = autresInfos;
	}

	@Column(name = "TELEPHONE")
	public String getTelephone() {
		return telephone;
	}

	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * @return the chantier
	 */
	// @ManyToMany (fetch = FetchType.EAGER,cascade= CascadeType.ALL,mappedBy
	// ="listeEmployes")
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "EmployeRole", joinColumns = { @JoinColumn(name = "ID_EMPLOYE") }, inverseJoinColumns = {
			@JoinColumn(name = "ID_CHANTIER") })
	public List<Chantier> getListChantier() {
		return listChantier;
	}

	/**
	 * @param chantier the chantier to set
	 */
	public void setListChantier(List<Chantier> listChantier) {
		this.listChantier = listChantier;
	}

	/**
	 * @return the listRole
	 */
	public Set<Role> getListRole() {
		return listRole;
	}

	/**
	 * @param listRole the listRole to set
	 */
	public void setListRole(Set<Role> listRole) {
		this.listRole = listRole;
	}

}

package main.java.fr.batis.entites;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="SIGNATURE")
public class Signature implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6848664189915835615L;
	private Long id;
	private String nom;
	private String prenom;
	private String fonction;
	private boolean luEtAccepte;
    private Devis devis;
    private Date dateSignature;
    private String email;
    private String telephone;

	public Signature() {
		super();
	}
	
	
	
	/**
	 * @param nom
	 * @param prenom
	 * @param fonction
	 * @param luEtAccepte
	 * @param devis
	 * @param dateSignature
	 * @param email
	 * @param telephone
	 */
	public Signature(String nom, String prenom, String fonction, boolean luEtAccepte, Devis devis,String email, String telephone) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.fonction = fonction;
		this.luEtAccepte = luEtAccepte;
		this.devis = devis;
		this.email = email;
		this.telephone = telephone;
	}



	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID_SIGNATURE")
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="NOM_SIGNATAIRE")
	public String getNom() {
		return nom;
	}
	
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	@Column(name="PRENOM_SIGNATAIRE")
	public String getPrenom() {
		return prenom;
	}
	
	
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	@Column(name="FCT_SIGNATAIRE")
	public String getFonction() {
		return fonction;
	}
	
	
	public void setFonction(String fonction) {
		this.fonction = fonction;
	}
	
	@Column(name="LU_ACCEPTE")
	public boolean isLuEtAccepte() {
		return luEtAccepte;
	}
	
	
	public void setLuEtAccepte(boolean luEtAccepte) {
		this.luEtAccepte = luEtAccepte;
	}


	/**
	 * @return the devis
	 */
	//@XmlTransient
	@OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL ,orphanRemoval = true)
	@JoinColumn(name="ID_DEVIS")
	public Devis getDevis() {
		return devis;
	}


	/**
	 * @param devis the devis to set
	 */
	
	public void setDevis(Devis devis) {
		this.devis = devis;
	}


	/**
	 * @return the dateSignature
	 */
	@Column(name="DATE_SIGNATURE")
	public Date getDateSignature() {
		return dateSignature;
	}


	/**
	 * @param dateSignature the dateSignature to set
	 */
	
	public void setDateSignature(Date dateSignature) {
		this.dateSignature = dateSignature;
	}


	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}


	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}


	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
 
	
}

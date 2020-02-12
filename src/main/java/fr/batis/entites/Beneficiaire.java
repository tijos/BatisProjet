package main.java.fr.batis.entites;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;

@Entity
@Table (name="BENEFICIAIRE")
public class Beneficiaire  extends Personne implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2854903203122057214L;

	private Long id;
	private RetraitDepotFond retraitDepot;
	private String type;
	
	
	public Beneficiaire() {
		super();
	}
	public Beneficiaire(String nom, String prenom, String telephone,String type) {
		super(nom,prenom,telephone,null);
		this.type  = type;
	}
	
	
	
	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue (strategy =GenerationType.IDENTITY)
	@Column(name="ID_BENEFICIAIRE")
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
	 * @return the nom
	 */
	
	@Column(name="NOM")
	public String getNom() {
		return nom;
	}
	/**
	 * @param nom the nom to set
	 */
	@XmlElement
	public void setNom(String nom) {
		this.nom = nom;
	}
	/**
	 * @return the prenom
	 */
	@Column(name="PRENOM")
	public String getPrenom() {
		return prenom;
	}
	/**
	 * @param prenom the prenom to set
	 */
	@XmlElement
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	/**
	 * @return the telephone
	 */
	@Column(name="TELPHONE")
	public String getTelephone() {
		return telephone;
	}
	/**
	 * @param telephone the telephone to set
	 */
	@XmlElement
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	/**
	 * @return the retrait
	 */
	
	@OneToOne(mappedBy = "beneficiaire",fetch =FetchType.LAZY,orphanRemoval=true,cascade =CascadeType.ALL )
	//@Fetch(FetchMode.SUBSELECT)
	public RetraitDepotFond getRetrait() {
		return retraitDepot;
	}
	/**
	 * @param retrait the retrait to set
	 */
	public void setRetrait(RetraitDepotFond retrait) {
		this.retraitDepot = retrait;
	}
	/**
	 * @return the type
	 */
	@Column(name="TYPE")
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	
}

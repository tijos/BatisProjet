package main.java.fr.batis.entites;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table (name="Deposant")
public class Deposant  extends Personne implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2854903203122057214L;

	private Long id;
	private List<RetraitDepotFond> retraitDepot;
	
	
	public Deposant() {
		super();
	}
	public Deposant(String nom, String prenom, String telephone,String adresse) {
		super(nom,prenom,telephone,adresse);
	}
	
	
	
	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue (strategy =GenerationType.IDENTITY)
	@Column(name="ID_DEPOSANT")
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
	 * @return the retraitDepot
	 */
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "deposant",orphanRemoval=true,cascade = CascadeType.ALL )
	@Fetch(FetchMode.SUBSELECT)
	public List<RetraitDepotFond> getRetraitDepot() {
		return retraitDepot;
	}
	/**
	 * @param retraitDepot the retraitDepot to set
	 */
	public void setRetraitDepot(List<RetraitDepotFond> retraitDepot) {
		this.retraitDepot = retraitDepot;
	}
	
	
	
}

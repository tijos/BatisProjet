package main.java.fr.batis.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="AUTORISATION")
public class Autorisation extends Document {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1568506246936251728L;

	private Long id;
	public Autorisation() {
		super();
	}


	public Autorisation(String nom, String statut, String numero) {
		super(nom, statut, numero);
	}


	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID_AUTORISATION")
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
	public void setNom(String nom) {
		this.nom = nom;
	}
	/**
	 * @return the statut
	 */
	@Column(name="STATUT")
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
	 * @return the numero
	 */
	@Column(name="REFERENCE")
	public String getReference() {
		return reference;
	}
	/**
	 * @param Reference the Reference to set
	 */
	public void setReference(String numero) {
		this.reference = numero;
	}
	
}

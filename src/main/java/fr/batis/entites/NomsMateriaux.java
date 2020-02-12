package main.java.fr.batis.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="LISTE_MATERIEL")
public class NomsMateriaux {

	private Long id;
	private String nom;
	
	
	private  Long code;
	/**
	 * @param id
	 * @param nom
	 * @param code
	 */
	public NomsMateriaux(Long id, String nom, Long code) {
		super();
		this.id = id;
		this.nom = nom;
		this.code = code;
	}
	
	/**
	 * 
	 */
	public NomsMateriaux() {
		super();
	
	}

	/**
	 * @return the id
	 */
	
	@Id
	@Column(name="ID")
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
	 * @return the code
	 */

	//@Generated(value = GenerationTime.INSERT)
	@Column(name="CODE")	
	public Long getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(Long code) {
		this.code = code;
	}
	
	
}

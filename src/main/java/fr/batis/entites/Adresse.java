package main.java.fr.batis.entites;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="ADRESSE")
public class Adresse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8411139256289721253L;
	
	private String numeroRue;
	private String nomRue;
	private String codePostale;
	private String ville;
	private String pays;
	private Long id;
	
		
	public Adresse() {
		super();
	}


	public Adresse(String numeroRue, String nomRue, String codePostale, String ville, String pays) {
		super();
		this.numeroRue = numeroRue;
		this.nomRue = nomRue;
		this.codePostale = codePostale;
		this.ville = ville;
		this.pays = pays;
	}


	
	/**
	 * @return the id
	 */
	@Id
	@Column(name="ID_ADRESSE")
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
	 * @return the numeroRue
	 */
	@Column(name="NUMERO")
	public String getNumeroRue() {
		return numeroRue;
	}


	/**
	 * @param numeroRue the numeroRue to set
	 */
	public void setNumeroRue(String numeroRue) {
		this.numeroRue = numeroRue;
	}


	/**
	 * @return the nomRue
	 */
	@Column(name="NOM")
	public String getNomRue() {
		return nomRue;
	}


	/**
	 * @param nomRue the nomRue to set
	 */
	public void setNomRue(String nomRue) {
		this.nomRue = nomRue;
	}


	/**
	 * @return the codePostale
	 */
	@Column(name="CODE_POSTAL")
	public String getCodePostale() {
		return codePostale;
	}


	/**
	 * @param codePostale the codePostale to set
	 */
	public void setCodePostale(String codePostale) {
		this.codePostale = codePostale;
	}


	/**
	 * @return the ville
	 */
	@Column(name="VILLE")
	public String getVille() {
		return ville;
	}


	/**
	 * @param ville the ville to set
	 */
	
	public void setVille(String ville) {
		this.ville = ville;
	}


	/**
	 * @return the pays
	 */
	@Column(name="PAYS")
	public String getPays() {
		return pays;
	}


	/**
	 * @param pays the pays to set
	 */
	public void setPays(String pays) {
		this.pays = pays;
	}
	
	

}

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

@Entity
@Table(name="FRAIS")
public class Frais implements Serializable{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = -2046606355634435781L;
	private Long id;
	private String type;
	private Double montant;
	private String devise;
	private String statut;
	private Integer codeStatut;
    private VoletAdmnistratif voletAdministratif;
	private String date;
	
	public Frais() {
		super();
	}


	public Frais(String type, Double montant, String devise, String statut, Integer codeStatut,
			VoletAdmnistratif voletAdmin, String date) {
		super();
		this.type = type;
		this.montant = montant;
		this.devise = devise;
		this.statut = statut;
		this.codeStatut = codeStatut;
		this.voletAdministratif = voletAdmin;
		this.date = date;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="ID_FRAIS")
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	@Column(name ="ID_TYPE")
	public String getType() {
		return type;
	}

	
	public void setType(String type) {
		this.type = type;
	}


	@Column(name ="MONTANT")
	public Double getMontant() {
		return montant;
	}

	
	public void setMontant(Double montant) {
		this.montant = montant;
	}

	@Column(name ="DEVISE")
	public String getDevise() {
		return devise;
	}

	
	public void setDevise(String devise) {
		this.devise = devise;
	}

	@Column(name ="STATUT")
	public String getStatut() {
		return statut;
	}

	
	public void setStatut(String statut) {
		this.statut = statut;
	}


	/**
	 * @return the voletAdmin
	 */
	
/*	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_VOLET" , nullable = false)*/
	
	//@XmlTransient
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_VOLET" , nullable = false)
	public VoletAdmnistratif getVoletAdministratif() {
		return voletAdministratif;
	}


	/**
	 * @param voletAdmin the voletAdmin to set
	 */
	public void setVoletAdministratif(VoletAdmnistratif voletAdmin) {
		this.voletAdministratif = voletAdmin;
	}




	/**
	 * @return the date
	 */
	@Column(name="DATE")
	public String getDate() {
		return date;
	}




	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}





	/**
	 * @return the codeStatut
	 */
	@Column(name="CODE_STATUT")
	public Integer getCodeStatut() {
		return codeStatut;
	}



	/**
	 * @param codeStatut the codeStatut to set
	 */
	public void setCodeStatut(Integer codeStatut) {
		this.codeStatut = codeStatut;
	}
	

}

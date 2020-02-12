package main.java.fr.batis.entites;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="PARAMETRES")
public class Parametres implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7576372957762063059L;
	
	private Double pourcentageMo;
	private Long id;

	
	/**
	 * 
	 */
	public Parametres() {
		super();
	
	}

	
	
	/**
	 * @param pourcentageMo
	 * @param paramatreName
	 */
	public Parametres(Double pourcentageMo) {
		super();
		this.pourcentageMo = pourcentageMo;
	}



	/**
	 * @return the id
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID_PARAMETRE")
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
	 * @return the pourcentageMo
	 */
	
	@Column(name="POURCENTAGE_MO")
	public Double getPourcentageMo() {
		return pourcentageMo;
	}

	/**
	 * @param pourcentageMo the pourcentageMo to set
	 */
	public void setPourcentageMo(Double pourcentageMo) {
		this.pourcentageMo = pourcentageMo;
	}


}

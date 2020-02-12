package main.java.fr.batis.entites;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;


public class Responsable extends Employe {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4077875105873158898L;
	private Long id;
	private Devis devis;
	public Responsable() {
		super();
	}
	
	public Responsable(Devis devis) {
		super();
		this.devis = devis;
	}
	
	
	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID_RESPONSABLE")
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
	 * @return the devis
	 */
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
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
	
	
}

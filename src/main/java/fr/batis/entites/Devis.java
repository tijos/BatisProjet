package main.java.fr.batis.entites;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@XmlRootElement
@Entity
@Table(name="DEVIS")

public class Devis implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2479996167149289140L;
	private Long id;
	private String nom;
	private String description;
	private List<Materiel> listMateriaux;
	private String commentaires;
	private List<Contrainte> listeContraintes;
	private Signature signature;
	private PhaseConstruction phase;
	private Double mainOeuvrePaye;
	private Double pourcentageMO;
	private Employe reponsable;

	

	/**
	 * @param nom
	 * @param description
	 * @param listMateriaux
	 * @param commentaires
	 * @param listeContraintes
	 * @param signature
	 * @param phase
	 * @param mainOeuvreApayer
	 * @param mainOeuvrePaye
	 */
	public Devis(String nom, String description, List<Materiel> listMateriaux, String commentaires,
			List<Contrainte> listeContraintes, Signature signature, PhaseConstruction phase,Double mainOeuvrePaye) {
		super();
		this.nom = nom;
		this.description = description;
		this.listMateriaux = listMateriaux;
		this.commentaires = commentaires;
		this.listeContraintes = listeContraintes;
		this.signature = signature;
		this.phase = phase;
		this.mainOeuvrePaye = mainOeuvrePaye;
	}


	public Devis() {
		super();
		listMateriaux = new ArrayList<Materiel>();
		listeContraintes = new ArrayList<Contrainte>();
	}

	
	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID_DEVIS")
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="NOM")
	public String getNom() {
		return nom;
	}

	
	public void setNom(String nom) {
		this.nom = nom;
	}

	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

//	@OneToMany(fetch= FetchType.EAGER, cascade = CascadeType.ALL,orphanRemoval =true)
//	@JoinColumn(name="ID_MATERIEL")
//	@Fetch(FetchMode.SUBSELECT)
	
	@OneToMany(fetch = FetchType.EAGER,cascade= CascadeType.ALL, mappedBy = "devis",orphanRemoval =true)
	@Fetch(FetchMode.SUBSELECT)	
	public List<Materiel> getListMateriaux() {
		return listMateriaux;
	}


	public void setListMateriaux(List<Materiel> listMateriaux) {
		this.listMateriaux = listMateriaux;
	}

	@Column(name="COMMENTAIRE")
	public String getCommentaires() {
		return commentaires;
	}

	public void setCommentaires(String commenatires) {
		this.commentaires = commenatires;
	}


	@OneToMany(fetch =FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval= true)
	@JoinColumn(name="ID_DEVIS")
	@Fetch(FetchMode.SUBSELECT)
	public List<Contrainte> getListeContraintes() {
		return listeContraintes;
	}

	
	public void setListeContraintes(List<Contrainte> listeContraintes) {
		this.listeContraintes = listeContraintes;
	}

	@OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL ,orphanRemoval = true)
	@JoinColumn(name="ID_SIGNATURE")
	public Signature getSignature() {
		return signature;
	}

	
	public void setSignature(Signature signature) {
		this.signature = signature;
	}

	/**
	 * @return the phase
	 */
	
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	public PhaseConstruction getPhase() {
		return phase;
	}

	/**
	 * @param phase the phase to set
	 */
	public void setPhase(PhaseConstruction phase) {
		this.phase = phase;
	}


	/**
	 * @return the mainOeuvrePaye
	 */
	public Double getMainOeuvrePaye() {
		return mainOeuvrePaye;
	}


	/**
	 * @param mainOeuvrePaye the mainOeuvrePaye to set
	 */
	public void setMainOeuvrePaye(Double mainOeuvrePaye) {
		this.mainOeuvrePaye = mainOeuvrePaye;
	}


	/**
	 * @return the pourcentageMO
	 */
	@Column(name="POURCENTAGE_MO")
	public Double getPourcentageMO() {
		return pourcentageMO;
	}


	/**
	 * @param pourcentageMO the pourcentageMO to set
	 */
	public void setPourcentageMO(Double pourcentageMO) {
		this.pourcentageMO = pourcentageMO;
	}


	/**
	 * @return the reponsable
	 */
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name="ID_EMPLOYE")
	public Employe getReponsable() {
		return reponsable;
	}


	/**
	 * @param reponsable the reponsable to set
	 */
	public void setReponsable(Employe reponsable) {
		this.reponsable = reponsable;
	}
	
	
}

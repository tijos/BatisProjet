package main.java.fr.batis.entites;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement
@Entity
@Table(name="STOCK")
public class StockageDestockage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -777109179789731793L;
	private String nomMateriel;
	private Long codeMateriel;
	private String qualite;
	private Double quantite;
	private String uniteMesure;
	private String motif;
	private Date  date;
	private String statut;
	private String typeOperation;
	private Long id;
	private Long idChantier;
//	private Chantier chantier;
	
	public StockageDestockage() {
		super();
	}



	public StockageDestockage(String nomMateriel, Long codeMateriel, String qualite, Double quantite, String uniteMesure,
			String motif, Date date, String statut, String typeOperation,Long idChantier) {
		super();
		this.nomMateriel = nomMateriel;
		this.codeMateriel = codeMateriel;
		this.qualite = qualite;
		this.quantite = quantite;
		this.uniteMesure = uniteMesure;
		this.motif = motif;
		this.date = date;
		this.statut = statut;
		this.idChantier = idChantier;
		this.typeOperation = typeOperation;
	}



	/**
	 * @return the nomMateriel
	 */
	
	@Column(name="NOM_TYPE")
	public String getNomMateriel() {
		return nomMateriel;
	}



	/**
	 * @param nomMateriel the nomMateriel to set
	 */
	@XmlElement
	public void setNomMateriel(String nomMateriel) {
		this.nomMateriel = nomMateriel;
	}



	/**
	 * @return the qualite
	 */
	@Column(name="QUALITE")
	public String getQualite() {
		return qualite;
	}



	/**
	 * @param qualite the qualite to set
	 */
	@XmlElement
	public void setQualite(String qualite) {
		this.qualite = qualite;
	}



	/**
	 * @return the quantite
	 */
	@Column(name="QUANTITE")
	public Double getQuantite() {
		return quantite;
	}



	/**
	 * @param quantite the quantite to set
	 */
	@XmlElement
	public void setQuantite(Double quantite) {
		this.quantite = quantite;
	}



	/**
	 * @return the dateInventaire
	 */
	@Column(name="DATE")
	public Date getDate() {
		return date ; //BatisUtils.getFormatedDate(date);
	}



	/**
	 * @param dateInventaire the dateInventaire to set
	 */
	@XmlElement
	public void setDate(Date dateInventaire) {
		this.date = dateInventaire;
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
	@XmlElement
	public void setStatut(String statut) {
		this.statut = statut;
	}



	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID_STOCK")
	public Long getId() {
		return id;
	}



	/**
	 * @param id the id to set
	 */
	@XmlElement
	public void setId(Long id) {
		this.id = id;
	}



	/**
	 * @return the unite
	 */
	@Column(name ="UNITE_MESURE")
	public String getUniteMesure() {
		return uniteMesure;
	}



	/**
	 * @param unite the unite to set
	 */
	@XmlElement
	public void setUniteMesure(String unite) {
		this.uniteMesure = unite;
	}

	/**
	 * @return the motif
	 */
	@Column(name="MOTIF_DESTOCKAGE")
	public String getMotif() {
		return motif;
	}



	/**
	 * @param motif the motif to set
	 */
	public void setMotif(String motif) {
		this.motif = motif;
	}

	/**
	 * @return the codeMateriel
	 */
	@Column(name="CODE_MATERIEL")
	public Long getCodeMateriel() {
		return codeMateriel;
	}



	/**
	 * @param codeMateriel the codeMateriel to set
	 */
	@XmlElement
	public void setCodeMateriel(Long codeMateriel) {
		this.codeMateriel = codeMateriel;
	}



	/**
	 * @return the typeOperation
	 */
	@Column(name="TYPE_OPERATION")
	public String getTypeOperation() {
		return typeOperation;
	}



	/**
	 * @param typeOperation the typeOperation to set
	 */
	@XmlElement
	public void setTypeOperation(String typeOperation) {
		this.typeOperation = typeOperation;
	}



	/**
	 * @return the chantier
	 */
	//@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Column(name="ID_CHANTIER")
	public Long getIdChantier() {
		return idChantier;
	}



	/**
	 * @param chantier the chantier to set
	 */
	public void setIdChantier(Long idChantier) {
		this.idChantier = idChantier;
	}

/*

	*//**
	 * @return the chantier
	 *//*
	@Column(name="ID_CHANTIER")
	@ManyT(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	public Chantier getChantier() {
		return chantier;
	}



	*//**
	 * @param chantier the chantier to set
	 *//*
	public void setChantier(Chantier chantier) {
		this.chantier = chantier;
	}
	*/
	
	
}

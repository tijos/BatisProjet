package main.java.fr.batis.entites;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="INVENTAIRE")
public class Inventaire implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -777109179789731793L;
	private String nomMateriel;
	private String codeMateriel;
	private String qualite;
	private Double quantite;
	private String uniteMesure;
	private String motif;
	private Date  dateInventaire;
	private String statut;
	private Long id;
	private Long idChantier;
	
	
	public Inventaire() {
		super();
	}



	public Inventaire(Long idChantier,String nomMateriel, String qualite, Double quantite, Date dateInventaire, String statut) {
		super();
		this.nomMateriel = nomMateriel;
		this.qualite = qualite;
		this.quantite = quantite;
		this.dateInventaire = dateInventaire;
		this.statut = statut;
		this.idChantier = idChantier;
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
	
	public void setQuantite(Double quantite) {
		this.quantite = quantite;
	}



	/**
	 * @return the dateInventaire
	 */
	@Column(name="DATE_INVENTAIRE")
	public Date getDateInventaire() {
		return dateInventaire ;//BatisUtils.getFormatedDate(dateInventaire);
	}



	/**
	 * @param dateOp the dateInventaire to set
	 */
	
	public void setDateInventaire(Date dateOp) {
		this.dateInventaire = dateOp;
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
	
	public void setUniteMesure(String unite) {
		this.uniteMesure = unite;
	}



	/**
	 * @return the motifDestockage
	 */
	@Column(name="MOTIF_DESTOCKAGE")
	public String getMotif() {
		return motif;
	}



	/**
	 * @param motifDestockage the motifDestockage to set
	 */
	
	public void setMotif(String motif) {
		this.motif = motif;
	}



	/**
	 * @return the codeMateriel
	 */
	@Column(name="CODE_MATERIEL")
	public String getCodeMateriel() {
		return codeMateriel;
	}



	/**
	 * @param codeMateriel the codeMateriel to set
	 */
	
	public void setCodeMateriel(String codeMateriel) {
		this.codeMateriel = codeMateriel;
	}



	/**
	 * @return the idChantier
	 */
	@Column(name="ID_CHANTIER")
	public Long getIdChantier() {
		return idChantier;
	}



	/**
	 * @param idChantier the idChantier to set
	 */
	public void setIdChantier(Long idChantier) {
		this.idChantier = idChantier;
	}

	
}

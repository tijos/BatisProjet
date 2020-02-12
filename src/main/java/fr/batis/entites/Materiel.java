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
/**
 * Classe qui represente un materiel
 * @author Thierry KUBWIMANA
 * @since 0.0
 *
 */

@Entity
@Table(name="MATERIEL")
//@Cache(region = "MaterielCache", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Materiel implements Serializable , Cloneable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3724874662720558908L;
	private Long id;
	private String nom;
	private String qualitePrevu;
	private String qualiteReel;
	private String type;
	private String quantitePrevu;
	private String quantiteReel;
	private String  prixUnitairePrevu;
	private String  prixUnitaireReel;
	private String  prixTotPrevu;
	private String  prixTotReel;
	private Integer codeTraitement;
	private Integer codeDevisInitial;
	private String  statutAchat;
	private Integer codeStatutAchat;

	private String devise;
	private String uniteDemesure;
	private Devis devis;
	private Long idChantier;
	private String dateAchat;
	private String  prixTotPrevuInit;
	private String quantitePrevuInit;
	private String diffPrix;

	



	public Materiel() {
		super();
	}
	
	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	@Column(name="ID_MATERIEL")
	public Long getId() {
		return id;
	}



	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}


	@Column(name="NOM_MATERIEL")
	public String getNom() {
		return nom;
	}

	
	/**
	 * @param nom
	 * @param qualitePrevu
	 * @param qualiteReel
	 * @param type
	 * @param quantitePrevu
	 * @param quantiteReel
	 * @param prixUnitairePrevu
	 * @param prixUnitaireReel
	 * @param prixTotPrevu
	 * @param prixTotReel
	 * @param codeTraitement
	 * @param codeDevisInitial
	 * @param statutAchat
	 * @param codeStatutAchat
	 * @param devise
	 * @param uniteDemesure
	 * @param devis
	 * @param idChantier
	 * @param dateAchat
	 * @param prixTotPrevuInit
	 * @param quantitePrevuInit
	 * @param diffPrix
	 */
	public Materiel(String nom, String qualitePrevu, String qualiteReel, String type, String quantitePrevu,
			String quantiteReel, String prixUnitairePrevu, String prixUnitaireReel, String prixTotPrevu,
			String prixTotReel, Integer codeTraitement, Integer codeDevisInitial, String statutAchat,
			Integer codeStatutAchat, String devise, String uniteDemesure, Devis devis, Long idChantier,
			String dateAchat, String prixTotPrevuInit, String quantitePrevuInit, String diffPrix) {
		super();
		this.nom = nom;
		this.qualitePrevu = qualitePrevu;
		this.qualiteReel = qualiteReel;
		this.type = type;
		this.quantitePrevu = quantitePrevu;
		this.quantiteReel = quantiteReel;
		this.prixUnitairePrevu = prixUnitairePrevu;
		this.prixUnitaireReel = prixUnitaireReel;
		this.prixTotPrevu = prixTotPrevu;
		this.prixTotReel = prixTotReel;
		this.codeTraitement = codeTraitement;
		this.codeDevisInitial = codeDevisInitial;
		this.statutAchat = statutAchat;
		this.codeStatutAchat = codeStatutAchat;
		this.devise = devise;
		this.uniteDemesure = uniteDemesure;
		this.devis = devis;
		this.idChantier = idChantier;
		this.dateAchat = dateAchat;
		this.prixTotPrevuInit = prixTotPrevuInit;
		this.quantitePrevuInit = quantitePrevuInit;
		this.diffPrix = diffPrix;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@Column(name="TYPE_MATERIEL")
	public String getType() {
		return type;
	}

	
	public void setType(String type) {
		this.type = type;
	}

	
	@Column(name="PRIX_UNITAIRE_PREVU")
	public String getPrixUnitairePrevu() {
		return prixUnitairePrevu;
	}

	
	public void setPrixUnitairePrevu(String prixUnitaire) {
		this.prixUnitairePrevu = prixUnitaire;
	}
	

	/**
	 * @return the codeTraitement
	 */
	@Column(name="CODE_TRAITEMENT")
	public Integer getCodeTraitement() {
		return codeTraitement;
	}



	/**
	 * @param codeTraitement the codeTraitement to set
	 */
	public void setCodeTraitement(Integer codeTraitement) {
		this.codeTraitement = codeTraitement;
	}

	/**
	 * @return the codeDevisInitial
	 */
	@Column(name="CODE_DEVIS")
	public Integer getCodeDevisInitial() {
		return codeDevisInitial;
	}



	/**
	 * @param codeDevisInitial the codeDevisInitial to set
	 */
	public void setCodeDevisInitial(Integer codeDevisInitial) {
		this.codeDevisInitial = codeDevisInitial;
	}

	/**
	 * @return the statutAchat
	 */

	public String getStatutAchat() {
		return statutAchat;
	}



	/**
	 * @param statutAchat the statutAchat to set
	 */
	public void setStatutAchat(String statutAchat) {
		this.statutAchat = statutAchat;
	}



	/**
	 * @return the statutAchatCode
	 */
	@Column(name="STATUT_ACHAT")
	public Integer getCodeStatutAchat() {
		return codeStatutAchat;
	}



	/**
	 * @param statutAchatCode the statutAchatCode to set
	 */
	public void setCodeStatutAchat(Integer statutAchatCode) {
		this.codeStatutAchat = statutAchatCode;
	}



	@Column(name="DEVISE")
	public String getDevise() {
		return devise;
	}

	
	public void setDevise(String devise) {
		this.devise = devise;
	}

	
	@Column(name="QUALITE_PREVU")
	public String getQualitePrevu() {
		return qualitePrevu;
	}

	
	public void setQualitePrevu(String qualitePrevu) {
		this.qualitePrevu = qualitePrevu;
	}

	@Column(name="QUALITE_REEL")
	public String getQualiteReel() {
		return qualiteReel;
	}

	
	public void setQualiteReel(String qualiteReel) {
		this.qualiteReel = qualiteReel;
	}

	@Column(name="QUANTITE_PREVU")
	public String getQuantitePrevu() {
		return quantitePrevu;
	}

	
	public void setQuantitePrevu(String quantitePrevu) {
		this.quantitePrevu = quantitePrevu;
	}

	@Column(name="QUANTITE_REEL")
	public String getQuantiteReel() {
		return quantiteReel;
	}

	
	public void setQuantiteReel(String quantiteReel) {
		this.quantiteReel = quantiteReel;
	}

	@Column(name="PRIX_U_REEL")
	public String getPrixUnitaireReel() {
		return prixUnitaireReel;
	}

	
	public void setPrixUnitaireReel(String prixUnitaireReel) {
		this.prixUnitaireReel = prixUnitaireReel;
	}

	@Column(name="UNITE_MESURE")
	public String getUniteDemesure() {
		return uniteDemesure;
	}

	
	public void setUniteDemesure(String uniteDemesure) {
		this.uniteDemesure = uniteDemesure;
	}



	/**
	 * @return the devis
	 */
	

	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DEVIS" , nullable = true)
	public Devis getDevis() {
		return devis;
	}



	/**
	 * @param devis the devis to set
	 */
	public void setDevis(Devis devis) {
		this.devis = devis;
	}



	/**
	 * @return the prixTotPrevu
	 */
	@Column(name="PRIX_TOTAL_PREVU")
	public String getPrixTotPrevu() {
		return prixTotPrevu;
	}



	/**
	 * @param string the prixTotPrevu to set
	 */
	public void setPrixTotPrevu(String string) {
		this.prixTotPrevu = string;
	}



	/**
	 * @return the prixTotReel
	 */
	@Column(name="PRIX_TOTAL_REEL")
	public String getPrixTotReel() {
		return prixTotReel;
	}



	/**
	 * @param prixTotReel the prixTotReel to set
	 */
	public void setPrixTotReel(String prixTotReel) {
		this.prixTotReel = prixTotReel;
	}


/*
	*//**
	 * @return the idChantier
	 *//*
	@Column(name="ID_CHANTIER")
	public Long getIdChantier() {
		return idChantier;
	}



	*//**
	 * @param idChantier the idChantier to set
	 *//*
	public void setIdChantier(Long idChantier) {
		this.idChantier = idChantier;
	}*/

	public Object clone() {
		Materiel materiel = null;
	    try {
	    	materiel = (Materiel) super.clone();
	    } catch(CloneNotSupportedException cnse) {
	     
	      	cnse.printStackTrace(System.err);
	    }
	    
	
	    return materiel;
	}



	/**
	 * @return the prixTotPrevuInit
	 */
	
	@Column(name="PRIX_TOT_PRV_INIT")
	public String getPrixTotPrevuInit() {
		return prixTotPrevuInit;
	}



	/**
	 * @param string the prixTotPrevuInit to set
	 */
	public void setPrixTotPrevuInit(String string) {
		this.prixTotPrevuInit = string;
	}



	/**
	 * @return the quantitePrevuInit
	 */
	@Column(name="QUANTITE_PRV_INIT")
	public String getQuantitePrevuInit() {
		return quantitePrevuInit;
	}



	/**
	 * @param quantitePrevuInit the quantitePrevuInit to set
	 */
	public void setQuantitePrevuInit(String quantitePrevuInit) {
		this.quantitePrevuInit = quantitePrevuInit;
	}



	/**
	 * @return the dateAchat
	 */
	@Column(name="DATE_ACHAT")
	public String getDateAchat() {
		return dateAchat;
	}



	/**
	 * @param dateAchat the dateAchat to set
	 */
	public void setDateAchat(String dateAchat) {
		this.dateAchat = dateAchat;
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

	@Column(name="DIFF_PRIX")
	public String getDiffPrix() {
		return diffPrix;
	}


	public void setDiffPrix(String diffPrix) {
		this.diffPrix = diffPrix;
	}



	/**
	 * @return the codeSuiviAchat
	 */
/*	
	@Column(name="STATUT_ACHAT")
	public Integer getCodeSuiviAchat() {
		return codeSuiviAchat;
	}



	*//**
	 * @param codeSuiviAchat the codeSuiviAchat to set
	 *//*
	public void setCodeSuiviAchat(Integer codeSuiviAchat) {
		this.codeSuiviAchat = codeSuiviAchat;
	}*/

	
}

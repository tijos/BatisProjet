package main.java.fr.batis.entites;

import java.beans.Transient;
import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "RETRAIT_DEPOT")
public class RetraitDepotFond implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8340565676754209657L;
	private Beneficiaire beneficiaire;
	private String motif;
	private String dateOperation;
	private Double montant;
	private String devise;
	private Employe comptable;
	private String type;
	private Long id;
	@SuppressWarnings("unused")
	private String nomPrenomComptable;
	@SuppressWarnings("unused")
	private String nomPrenomBeneficaire;
	private String typeBeneficiaire;
	private Chantier chantier;

	public RetraitDepotFond() {
		super();
	}

	/**
	 * @return the id
	 */

	@Id
	@Column(name = "ID_RETRAIT")
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
	 * @return the beneficiaire
	 */
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_BENEFICIARE", nullable = true)
	public Beneficiaire getBeneficiaire() {
		return beneficiaire;
	}

	/**
	 * @param beneficiaire the beneficiaire to set
	 */

	public void setBeneficiaire(Beneficiaire beneficiaire) {
		this.beneficiaire = beneficiaire;
	}

	/**
	 * @return the motif
	 */
	@Column(name = "MOTIF")
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
	 * @return the date
	 */
	@Column(name = "DATE_OP")
	public String getDateOperation() {
		return dateOperation;
	}

	/**
	 * @param date the date to set
	 */

	public void setDateOperation(String date) {
		this.dateOperation = date;
	}

	/**
	 * @return the montant
	 */
	@Column(name = "MONTANT")
	public Double getMontant() {
		return montant; // BatisUtils.getNumberFormatter(montant);
	}

	/**
	 * @param montant the montant to set
	 */

	public void setMontant(final Double montant) {
		this.montant = montant;
	}

	/**
	 * @return the devise
	 */

	@Column(name = "DEVISE")
	public String getDevise() {
		return devise;
	}

	/**
	 * @param devise the devise to set
	 */

	public void setDevise(String devise) {
		this.devise = devise;
	}

	/**
	 * @return the comptable
	 */
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "ID_EMPLOYE", nullable = true)
	public Employe getComptable() {
		return comptable;
	}

	/**
	 * @param comptable the comptable to set
	 */

	public void setComptable(Employe comptable) {
		this.comptable = comptable;
	}

	/**
	 * @return the type
	 */
	@Column(name = "TYPE")
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the typeBeneficiaire
	 */

	@Column(name = "TYPE_BENEFICIAIRE")
	public String getTypeBeneficiaire() {
		return typeBeneficiaire;
	}

	/**
	 * @param typeBeneficiaire the typeBeneficiaire to set
	 */

	public void setTypeBeneficiaire(String typeBeneficiaire) {
		this.typeBeneficiaire = typeBeneficiaire;
	}

	/**
	 * @return the nomPrenomComptable
	 */
	@Transient
	public String getNomPrenomComptable() {
		return comptable != null ? comptable.getNom() + " " + comptable.getPrenom() : "";
	}

	/**
	 * @param nomPrenomComptable the nomPrenomComptable to set
	 */
	public void setNomPrenomComptable(String nomPrenomComptable) {
		this.nomPrenomComptable = nomPrenomComptable;
	}

	/**
	 * @return the nomPrenomBeneficaire
	 */
	@Transient
	public String getNomPrenomBeneficaire() {
		return beneficiaire != null ? beneficiaire.nom + " " + beneficiaire.prenom : "";
	}

	/**
	 * @param nomPrenomBeneficaire the nomPrenomBeneficaire to set
	 */
	public void setNomPrenomBeneficaire(String nomPrenomBeneficaire) {
		this.nomPrenomBeneficaire = nomPrenomBeneficaire;
	}

	/**
	 * @return the chantier
	 */
	public Chantier getChantier() {
		return chantier;
	}

	/**
	 * @param chantier the chantier to set
	 */

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "ID_CHANTIER")
	public void setChantier(Chantier chantier) {
		this.chantier = chantier;
	}

}

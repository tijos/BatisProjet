package main.java.fr.batis.entites;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="VOLET_ADMIN")
public class VoletAdmnistratif implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7188300171647074247L;
	private Long id;
	private EtudeSol etudeSol;
	private Autorisation autorisationBatir;
	private List<Frais> listeFrais;
	private Chantier chantier;
   
	public VoletAdmnistratif() {

	}

	


	public VoletAdmnistratif(EtudeSol etudeSol, Autorisation autorisationBatir, List<Frais> listeFrais,
			Chantier chantier) {
		super();
		this.etudeSol = etudeSol;
		this.autorisationBatir = autorisationBatir;
		this.listeFrais = listeFrais;
		this.chantier = chantier;
	}




	@Id
	@Column(name="ID_VOLET")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

//	@Column(name="STATUT_ETUDE_SOL")
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
	@JoinColumn(name="ID_ETUDE")
	public EtudeSol getEtudeSol() {
		return etudeSol;
	}

	
	
	@XmlElement
	public void setEtudeSol(EtudeSol etudeSol) {
		this.etudeSol = etudeSol;
	}

	//@Column(name="SATATUT_AUTORISATION")
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
	@JoinColumn(name="ID_AUTORISATION")
	public Autorisation getAutorisationBatir() {
		return autorisationBatir;
	}

	@XmlElement
	public void setAutorisationBatir(Autorisation autorisationBatir) {
		this.autorisationBatir = autorisationBatir;
	}

	@OneToMany(fetch = FetchType.EAGER,cascade= CascadeType.ALL, mappedBy = "voletAdministratif",orphanRemoval =true)
	@Fetch(FetchMode.SUBSELECT)
	public List<Frais> getListeFrais() {
		return listeFrais;
	}

	@XmlElement
	public void setListeFrais(List<Frais> listeFrais) {
		this.listeFrais = listeFrais;
	}




	/**
	 * @return the chantier
	 */
	@OneToOne (fetch =FetchType.LAZY,cascade= CascadeType.ALL,orphanRemoval = true,mappedBy ="voletAdministratif")
	public Chantier getChantier() {
		return chantier;
	}




	/**
	 * @param chantier the chantier to set
	 */
	
	public void setChantier(Chantier chantier) {
		this.chantier = chantier;
	}

    
}

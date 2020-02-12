package main.java.fr.batis.enumeration;

/**
 * 
 * @author carlos
 *
 */
public enum TypeBeneficiaire {

	EMPLOYE(0, "Employé"), FOURNISSEUR(1, "Fournisseur"), AUTRE(2, "Autre");

	String description;
	Integer code;

	TypeBeneficiaire(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

}

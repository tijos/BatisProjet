package main.java.fr.batis.enumeration;

public enum TypeSuivi {

	PROBLEME(0, "Problème"), CONSTAT(1, "Constat"), A_FAIRE(2, "A faire"), COMPTE_RENDU(3, "Compte rendu"),
	AUTRES(4, "Autres");

	String description;
	Integer code;

	TypeSuivi(int code, String description) {
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

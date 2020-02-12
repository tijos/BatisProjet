package main.java.fr.batis.enumeration;

public enum TypeChantier {

	MAISON_HABITATION(0, "Maison d'habitation"), ECOLE(1, "Ecole"), BUREAUX(2, "Bureaux"),
	COMMERCE(3, "Bâtiment pour Commerce"), AUTRES(4, "Autres");

	String description;
	Integer code;

	TypeChantier(int code, String description) {
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

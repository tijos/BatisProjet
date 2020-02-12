package main.java.fr.batis.enumeration;

public enum RoleCode {

	CHEF_CHATIER(0, "Chef de chantier"), MACON(1, "Maçon"), AIDE_MACON(2, "Aide-Maçon"), VEILLEUR(3, "Veilleur"),
	CHARPENTIER(4, "Charpentier"), ELECTRICIEN(5, "Electricien"), PLOMBIER(6, "Plombier"), COMPTABLE(7, "Comptable"),
	ADMIN(8, "Admin"), GESTIONNAIRE(9, "Gestionnaire");

	String description;
	Integer code;

	RoleCode(int code, String description) {
		this.code = code;
		this.description = description;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the code
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(Integer code) {
		this.code = code;
	}

}

package main.java.fr.batis.enumeration;

public enum StatutPhase {

	ENPREPARATION(0, "En préparation"), ENCOURS(1, "En cours"), TERMINE(2, "Terminé");

	String description;
	Integer code;

	StatutPhase(int code, String description) {
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

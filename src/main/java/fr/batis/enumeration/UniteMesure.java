package main.java.fr.batis.enumeration;

/**
 * 
 * @author tijos
 *
 */
public enum UniteMesure {

	KG(0, "Kg"), METRES(1, "Mètres"), SACS(2, "Sacs"), PIECES(3, "Pièces"), BENNES(4, "Bennes");

	String description;
	Integer code;

	UniteMesure(int code, String description) {
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

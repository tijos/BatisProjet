package main.java.fr.batis.enumeration;

/**
 * 
 * @author tijos
 *
 */
public enum TypeRetraitDepot {

	RETRAIT(0, "Retrait"), DEPOT(1, "Depot");

	String description;
	Integer code;

	TypeRetraitDepot(final int code, final String description) {
		this.code = code;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(final Integer code) {
		this.code = code;
	}

}

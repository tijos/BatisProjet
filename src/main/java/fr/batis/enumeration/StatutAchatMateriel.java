package main.java.fr.batis.enumeration;

public enum StatutAchatMateriel {

	DEVIS_INIT(-1, "Devis initial"), A_ACHETER(0, "A acheter"), EN_COMMANDE(1, "En commande"),
	ACHETE_P(2, "Achat Partiel"), // Achat partiel
	ACHETE_T(3, "Achaté(s)"), // achat terminé, fini
	PUPTURE_STOCK(4, "En Rupture de Stock"), INTROUVABLE_SUR_MARCHE(5, "Introuvable sur le marché"),
	A_PAYER(6, "A payer"), PAYE(7, "Payé");

	String description;
	Integer code;

	StatutAchatMateriel(int code, String description) {
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

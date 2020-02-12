package main.java.fr.batis.enumeration;

/**
 * 
 * @author tijos
 *
 */
public enum MotifRetrait {

		SALAIRE(0,"Salaire"),
		ACHATMAT(1,"Achat du matériel"),
		MAINOEUVRE(2,"Main oeuvre"),
		AUTRE(3,"Autre");
		

		String description;
		Integer code;

		MotifRetrait(final int code,final String description) {
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


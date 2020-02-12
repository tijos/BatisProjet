package main.java.fr.batis.enumeration;

public enum CodeMateriaux{
	
		   CIMENTS(1,"CIMENTS"),
		   BRIQUES(2,"BRIQUES"),
		   GRAVIERS(3,"GRAVIER"),
		   SABLE_F(4,"SABLE FIN"),
		   SABLE_O(5,"SABLE ORDINAIRE"),
		   PIERRES(6,"PIERRES"),
		   MADRIER(7,"MADRIER"),
		   PLANCHES(8,"PLANCHES"),
		   ETRIERS(9,"ETRIERS"),
		   BROUETTES(10,"BROUETTES"),
		   MOELLON_C(11,"MOELLON DE CARRIERE"),
		   MOELLON_R(12,"MOELLON DE RIVIERE"),
		   PERCHES(13,"PERCHES"),
		   CLOUS(14,"CLOUS"),
		   FER_A_BETON8(15,"FER_A_BETON8"),
		   FER_A_BETON10(16,"FER_A_BETON10"),
		   FIL_A_LIGATURER(17,"FIL_A_LIGATURER"),
		   PORTAIL(18,"PORTAIL"),
		   VITRES(19,"VITRES"),
		   GAINES(20,"GAINES"),
		   FICELLE(21,"FICELLE"),
		   TUBE(22,"TUBE"),
		  
		   CARREAUX(23,"CARREAUX"),
		   TERRE(24,"TERRE"),
		   CHEVRONS(25,"CHEVRONS"),
		   FIL_A_LIGATURE(26,"FIL A LIGATURE"),
		   ECHAFFAUDAGES(27,"ECHAFFAUDAGES"),
		   BOUILLON(28,"BOUILLON"),
		   TOLES(29,"TOLES"),
		   TUILES(30,"TUILES"),
		   CLAUSTRAS(31,"CLAUSTRAS"),
		   MAINS_D_OEUVRE(32,"MAIN D'OEUVRE");
		   
		   String description;
		   Long code;
		   
		   CodeMateriaux(long code,String description) {
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
			public Long getCode() {
				return code;
			}
	
			/**
			 * @param code the code to set
			 */
			public void setCode(Long code) {
				this.code = code;
			}
	   
		}



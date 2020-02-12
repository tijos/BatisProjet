package main.java.fr.batis.entites;

public class CurrentChantier {

	private Long id;
	private Chantier chantier;
	
	
	public CurrentChantier() {
		
	}
	
	/**
	 * 
	 * @param id
	 * @param chantier
	 */
	
	
	public CurrentChantier(Long id, Chantier chantier) {
		super();
		this.id = id;
		this.chantier = chantier;
	}




	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the chantier
	 */
	public Chantier getChantier() {
		return chantier;
	}
	/**
	 * @param chantier the chantier to set
	 */
	public void setChantier(Chantier chantier) {
		this.chantier = chantier;
	}

	
}

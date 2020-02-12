package main.java.fr.batis.entites;

import java.io.Serializable;

public abstract class Document implements Serializable {

	private static final long serialVersionUID = 6774292564698093677L;
	protected String nom;
	protected String statut;
	protected String reference;

	public Document() {
		super();
	}

	public Document(String nom, String statut, String numero) {
		super();
		this.nom = nom;
		this.statut = statut;
		this.reference = numero;
	}

}

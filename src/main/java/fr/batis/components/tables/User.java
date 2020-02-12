package main.java.fr.batis.components.tables;

public class User {
private  String nom;
private String prenom;

/**
 * @param nom
 * @param prenom
 */
public User(String nom, String prenom) {
	super();
	this.nom = nom;
	this.prenom = prenom;
}

/**
 * @return the nom
 */
public String getNom() {
	return nom;
}

/**
 * @param nom the nom to set
 */
public void setNom(String nom) {
	this.nom = nom;
}

/**
 * @return the prenom
 */
public String getPrenom() {
	return prenom;
}

/**
 * @param prenom the prenom to set
 */
public void setPrenom(String prenom) {
	this.prenom = prenom;
}



}

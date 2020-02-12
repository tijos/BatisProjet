package main.java.fr.batis.entites;

public class User extends Personne {
  private Role role;
  private String login;
  private String passWord;
/**
 * 
 */
public User() {
	super();
	// TODO Auto-generated constructor stub
}
/**
 * @param nom
 * @param prenom
 * @param telephone
 * @param adresse
 */
public User(String nom, String prenom, String telephone, String adresse) {
	super(nom, prenom, telephone, adresse);
	// TODO Auto-generated constructor stub
}
/**
 * @return the role
 */
public Role getRole() {
	return role;
}
/**
 * @param role the role to set
 */
public void setRole(Role role) {
	this.role = role;
}
/**
 * @return the login
 */
public String getLogin() {
	return login;
}
/**
 * @param login the login to set
 */
public void setLogin(String login) {
	this.login = login;
}
/**
 * @return the passWord
 */
public String getPassWord() {
	return passWord;
}
/**
 * @param passWord the passWord to set
 */
public void setPassWord(String passWord) {
	this.passWord = passWord;
}
  
  
}

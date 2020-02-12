package main.java.fr.batis.components.common;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import main.java.fr.batis.entites.Adresse;

public class AdressComponentUtils {
	
	private CommonUtils commonUtils;
	private TextField numeroRue, nomRue, codePostal, ville, pays;
	private Adresse adresse;
	private BorderPane adressPanel;

	public AdressComponentUtils(int width, int height) {
		super();
		commonUtils = new CommonUtils();
		this.adressPanel = initAdressPanel(width, height);		
	}

	public BorderPane initAdressPanel(int width, int height) {
		
		BorderPane panAdress = new BorderPane();
		GridPane panAdressTmp = getCommonUtils().getGridPanel();
		
		panAdressTmp.setPrefSize(width, height);
		numeroRue = new TextField();
		Label numLabel = new Label("Numéro :");
		nomRue = new TextField();
		Label nomLabel = new Label("Rue :");
		ville = new TextField();
		Label bpLabel = new Label("Code Postal :");
		codePostal = new TextField();
		Label villeLabel = new Label("Ville/Etat :");
		pays = new TextField();
		Label paysLabel = new Label("Pays :");

		/*panNumNom.setPrefSize(width - 20, 28);
		panVillePays.setPrefSize(width - 20, 28);
		panVillePays.setPrefSize(width - 20, 28);*/
		numeroRue.setPrefSize(70, 25);

		panAdressTmp.add(numLabel,0,0);
		panAdressTmp.add(numeroRue,1,0);
		
		panAdressTmp.add(nomLabel,2,0);
		panAdressTmp.add(nomRue,3,0);

		panAdressTmp.add(bpLabel,4,0);
		panAdressTmp.add(codePostal,5,0);

		panAdressTmp.add(villeLabel,0,1);
		panAdressTmp.add(ville,1,1);
		panAdressTmp.add(paysLabel,2,1);
		panAdressTmp.add(pays,3,1);
		

	//	panAdress.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Adresse"));
		
		panAdress.setCenter(panAdressTmp);
		
		Adresse adress = initAdresse();
		setAdresse(adress);
		return panAdress;

	}

	private Adresse initAdresse() {
		adresse = new Adresse();
		adresse.setNomRue(nomRue.getText());
		adresse.setNumeroRue(numeroRue.getText());
		adresse.setVille(ville.getText());
		adresse.setPays(pays.getText());
		return adresse;
	}

	/**
	 * @return the numeroRue
	 */
	public TextField getNumeroRue() {
		return numeroRue;
	}

	/**
	 * @param numeroRue
	 *            the numeroRue to set
	 */
	public void setNumeroRue(TextField numeroRue) {
		this.numeroRue = numeroRue;
	}

	/**
	 * @return the nomRue
	 */
	public TextField getNomRue() {
		return nomRue;
	}

	/**
	 * @param nomRue
	 *            the nomRue to set
	 */
	public void setNomRue(TextField nomRue) {
		this.nomRue = nomRue;
	}

	/**
	 * @return the ville
	 */
	public TextField getVille() {
		return ville;
	}

	/**
	 * @param ville
	 *            the ville to set
	 */
	public void setVille(TextField ville) {
		this.ville = ville;
	}

	/**
	 * @return the pays
	 */
	public TextField getPays() {
		return pays;
	}

	/**
	 * @param pays
	 *            the pays to set
	 */
	public void setPays(TextField pays) {
		this.pays = pays;
	}

	/**
	 * @return the adresse
	 */
	public Adresse getAdresse() {
		return adresse;
	}

	/**
	 * @param adresse
	 *            the adresse to set
	 */
	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	/**
	 * @return the adressPanel
	 */
	public BorderPane getAdressPanel() {
		return adressPanel;
	}

	/**
	 * @param adressPanel
	 *            the adressPanel to set
	 */
	public void setAdressPanel(BorderPane adressPanel) {
		this.adressPanel = adressPanel;
	}

	/**
	 * @return the codePostal
	 */
	public TextField getCodePostal() {
		return codePostal;
	}

	/**
	 * @param codePostal
	 *            the codePostal to set
	 */
	public void setCodePostal(TextField codePostal) {
		this.codePostal = codePostal;
	}

	/**
	 * @return the commonUtils
	 */
	public CommonUtils getCommonUtils() {
		return commonUtils;
	}

	/**
	 * @param commonUtils the commonUtils to set
	 */
	public void setCommonUtils(CommonUtils commonUtils) {
		this.commonUtils = commonUtils;
	}
	
	

}

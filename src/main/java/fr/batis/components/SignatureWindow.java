package main.java.fr.batis.components;

import java.util.Date;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.java.fr.batis.components.common.BatisUtils;
import main.java.fr.batis.components.common.CommonUtils;
import main.java.fr.batis.entites.Chantier;
import main.java.fr.batis.entites.Devis;
import main.java.fr.batis.entites.Employe;
import main.java.fr.batis.entites.Signature;
import main.java.fr.batis.service.ChantierService;

public class SignatureWindow {

	private Employe chefChantier;
	private Chantier chantier;
	private Devis devis;
	private ChantierService chantierService;
	private boolean signed;
	private CommonUtils commonUtils;
	private TextField emailField, numeroTelField;

	public SignatureWindow() {
		super();
		commonUtils = new CommonUtils();
		chantierService = new ChantierService();
	}

	public SignatureWindow(Devis dvis) {
		super();
		// this.chefChantier = chefChantier;
		// this.chantier = chantier;
		this.devis = dvis;
		chantierService = new ChantierService();

		// Image icon =
		// Toolkit.getDefaultToolkit().getImage("images/signatureIcon.png");
		// this.setIconImage(icon);
		this.commonUtils = new CommonUtils();
		this.signed = false;
		initSignatureWindow(new Stage(), dvis);
	}

	@SuppressWarnings("unused")
	private void initSignatureWindow(Stage primaryStage, Devis devis) {

		Image signatureImg = new Image(getClass().getResourceAsStream("/images/signature.png"));
		Image unCheckedBoxImg = new Image(getClass().getResourceAsStream("/images/unCheckedBox.png"));
		chantierService = new ChantierService();
		Node panIcon = commonUtils.getHeader("Signature du devis");

		BorderPane panSign = new BorderPane();
		GridPane panSignTmp = commonUtils.getGridPanel();
		TextField nom = new TextField(devis.getReponsable() != null ? devis.getReponsable().getNom() : "");
		nom.setPrefSize(150, 25);
		Label labelNom = new Label("Nom :");

		TextField prenom = new TextField(devis.getReponsable() != null ? devis.getReponsable().getPrenom() : "");
		prenom.setPrefSize(150, 25);
		Label labelPrenNom = new Label("Prenom :");
		// TODO
		TextField fonction = new TextField(devis.getReponsable() != null ? "TODO GET DESCRIPTION" : "");
		fonction.setPrefSize(150, 25);
		Label labelFonction = new Label("Fonction :");

		Label labelEmail = new Label("Email :");
		emailField = new TextField(devis.getReponsable() != null ? devis.getReponsable().getEmail() : "");

		// tel

		Label labelTel = new Label("Téléphone :");
		numeroTelField = new TextField(devis.getReponsable() != null ? devis.getReponsable().getTelephone() : "");

		TextField comment = new TextField();
		comment.setPrefSize(200, 25);
		Label labelcomment = new Label("Commentaire :");

		CheckBox luApprouve = new CheckBox("Lu et Approuvé");// , new ImageIcon("images/unCheckedBox.png"));

		ButtonBar control = new ButtonBar();
		Button signer = new Button("Signer", new ImageView(signatureImg));
		control.getButtons().add(signer);
		// control.getButtons().add(button);
		control.setPadding(new Insets(10));

		signer.setDisable(true);
		panSignTmp.add(labelNom, 0, 0);
		panSignTmp.add(nom, 1, 0);
		panSignTmp.add(labelPrenNom, 0, 1);
		panSignTmp.add(prenom, 1, 1);
		panSignTmp.add(labelFonction, 0, 2);
		panSignTmp.add(fonction, 1, 2);

		panSignTmp.add(labelEmail, 0, 3);
		panSignTmp.add(emailField, 1, 3);
		panSignTmp.add(labelTel, 0, 4);
		panSignTmp.add(numeroTelField, 1, 4);

		panSignTmp.add(labelcomment, 0, 5);
		panSignTmp.add(comment, 1, 5);

		panSignTmp.add(luApprouve, 0, 6);

		luApprouve.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				signer.setDisable(false);
			}
		});

		signer.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				Devis dvi = getDevis();
				String commentValue = comment.getText();
				dvi.setCommentaires(commentValue);

				String nom = devis.getReponsable().getNom();
				String prenom = devis.getReponsable().getPrenom();
				// TODO
				String fonction = "	//TODO get description";// devis.getReponsable().getRole().getDescription();
				String tel = devis.getReponsable().getTelephone();
				String email = devis.getReponsable().getEmail();
				Signature sign = new Signature(nom, prenom, fonction, true, dvi, email, tel);
				sign.setDateSignature(new Date());
				dvi.setSignature(sign);
				setSigned(true);
				chantierService.saveOrUpdate(chantier);
				primaryStage.close();
			}
		});

		panSign.setTop(panIcon);
		panSign.setCenter(panSignTmp);
		panSign.setBottom(control);

		BatisUtils.setIconImage(primaryStage);
		Scene scene = new Scene(panSign, 400, 400);
		scene.getStylesheets().add(getClass().getResource("/css/batis.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	/**
	 * @return the chefChantier
	 */
	public Employe getChefChantier() {
		return chefChantier;
	}

	/**
	 * @param chefChantier the chefChantier to set
	 */
	public void setChefChantier(Employe chefChantier) {
		this.chefChantier = chefChantier;
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

	/**
	 * @return the devis
	 */
	public Devis getDevis() {
		return devis;
	}

	/**
	 * @param devis the devis to set
	 */
	public void setDevis(Devis devis) {
		this.devis = devis;
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

	/**
	 * @return the chantierService
	 */
	public ChantierService getChantierService() {
		return chantierService;
	}

	/**
	 * @param chantierService the chantierService to set
	 */
	public void setChantierService(ChantierService chantierService) {
		this.chantierService = chantierService;
	}

	/**
	 * @return the signed
	 */
	public boolean isSigned() {
		return signed;
	}

	/**
	 * @param signed the signed to set
	 */
	public void setSigned(boolean isSigned) {
		this.signed = isSigned;
	}

}

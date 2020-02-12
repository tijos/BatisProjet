/**
 * 
 */
package main.java.fr.batis.components;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import org.apache.commons.validator.routines.EmailValidator;
import org.controlsfx.tools.Borders;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import main.java.fr.batis.components.common.AdressComponentUtils;
import main.java.fr.batis.components.common.BatisUtils;
import main.java.fr.batis.components.common.CommonUtils;
import main.java.fr.batis.components.common.DateConverterHelper;
import main.java.fr.batis.components.custom.DoubleTextField;
import main.java.fr.batis.entites.Adresse;
import main.java.fr.batis.entites.Chantier;
import main.java.fr.batis.entites.Employe;
import main.java.fr.batis.entites.Role;
import main.java.fr.batis.enumeration.RoleCode;

/**
 * @author dlj7961
 *
 */
public class AjoutEmployeWindow {

	private CommonUtils commonUtils;
	private TabPane tabPanePrincipale;
	private Chantier chantier;
	private TextField nomField;
	private TextField prenomField;
	private TextField emailField;
	private TextField numeroTelField;
	private ComboBox<String> qualificationField;
	private DatePicker dateEmbaucheField;
	private DatePicker dateNaissanceField;
	private DoubleTextField salaireField;
	private ComboBox<String> deviseField;
	private ComboBox<String> uniteField;
	private TextField autresInfosField;
	private AdressComponentUtils adressComp;
	private Employe employe;
	private ObservableList<Employe> employeData;

	public AjoutEmployeWindow(Chantier ch, ObservableList<Employe> data) {
		super();
		this.chantier = ch;
		this.employeData = data;
		this.commonUtils = new CommonUtils();
	}

	/**
	 * 
	 * @param primaryStage
	 */
	public void initComponent(Stage primaryStage) {

		Image okImg = new Image(getClass().getResourceAsStream("/images/ok.png"));
		Image cancelImg = new Image(getClass().getResourceAsStream("/images/undo.png"));
		Image closeImg = new Image(getClass().getResourceAsStream("/images/exit.png"));

		BorderPane contentEmployeTmp = new BorderPane();
		BorderPane contentTmp = new BorderPane();

		Node panIcon = commonUtils.getHeader("Ajout d'un employé");

		GridPane contentEmploye = commonUtils.getGridPanel();
		nomField = new TextField();
		Label labelNom = new Label("Nom :");

		prenomField = new TextField();
		Label labelPrenom = new Label("Prénom :");

		// Role
		Label labelQualif = new Label("Role :");
		qualificationField = new ComboBox<String>();
		for (RoleCode quali : RoleCode.values()) {
			qualificationField.getItems().add(quali.getDescription());
		}

		// dateNaissance = BatisUtils.addDatePanel(panAge);

		Label uniteLabel = new Label("Fréquence rémuneration :");
		uniteField = new ComboBox<String>();
		uniteField.setPrefSize(120, 25);
		uniteField.getItems().add("Par jour");
		uniteField.getItems().add("Par semaine");
		uniteField.getItems().add("Par mois");

		deviseField = new ComboBox<String>();
		deviseField.setPrefSize(80, 25);
		deviseField.getItems().add("Fbu");
		deviseField.getItems().add("€");
		deviseField.getItems().add("$");

		Label salaireLabel = new Label("Salaire :");
		salaireField = new DoubleTextField();

		adressComp = new AdressComponentUtils(700, 50);
		/*
		 * BorderPane panAdress = adressComp.getAdressPanel(); BorderPane panAdresseTmp
		 * = new BorderPane(); panAdresseTmp.setPrefSize(700, 120);
		 * panAdresseTmp.setCenter(panAdress);
		 */

		// email

		Label labelEmail = new Label("Email :");
		emailField = new TextField();

		// tel

		Label labelTel = new Label("Téléphone :");
		numeroTelField = new TextField();

		Label dateNaisLabel = new Label("Date de naissance :");
		// autresInfosField = new TextField();

		dateNaissanceField = DateConverterHelper.getConvetedDate();

		//
		Callback<DatePicker, DateCell> dayCellFactory = getCommonUtils().getDayCellFactory();
		dateNaissanceField.setDayCellFactory(dayCellFactory);

		Label dateEmbaucheLabel = new Label("Date d'embauche :");
		// autresInfosField = new TextField();

		dateEmbaucheField = DateConverterHelper.getConvetedDate();

		//
		dateEmbaucheField.setDayCellFactory(dayCellFactory);

		Label autresInfosLabel = new Label("Autres informations :");
		autresInfosField = new TextField();

		// LOGNE 0

		contentEmploye.add(labelNom, 0, 0);
		contentEmploye.add(nomField, 1, 0);

		contentEmploye.add(labelPrenom, 5, 0);
		contentEmploye.add(prenomField, 6, 0);

		// ligne 1
		contentEmploye.add(labelTel, 0, 1);
		contentEmploye.add(numeroTelField, 1, 1);

		contentEmploye.add(labelEmail, 5, 1);
		contentEmploye.add(emailField, 6, 1);

		// ligne 3
		contentEmploye.add(salaireLabel, 0, 2);
		contentEmploye.add(salaireField, 1, 2);

		// contentEmploye.add(deviseLabel, 2, 2);
		contentEmploye.add(deviseField, 2, 2);

		contentEmploye.add(uniteLabel, 5, 2);
		contentEmploye.add(uniteField, 6, 2);

		contentEmploye.add(labelQualif, 0, 3);
		contentEmploye.add(qualificationField, 1, 3);

		contentEmploye.add(dateNaisLabel, 5, 3);
		contentEmploye.add(dateNaissanceField, 6, 3);

		contentEmploye.add(dateEmbaucheLabel, 0, 4);
		contentEmploye.add(dateEmbaucheField, 1, 4);

		contentEmploye.add(autresInfosLabel, 5, 4);
		contentEmploye.add(autresInfosField, 6, 4);

		// contentEmploye.add(panAdresseTmp, 0, 5,10,10);

		/*
		 * email.focusedProperty().addListener(new ChangeListener<Boolean>() {
		 * 
		 * @Override public void changed(ObservableValue<? extends Boolean> arg0,
		 * Boolean oldPropertyValue, Boolean newPropertyValue) { EmailValidator
		 * emailValidator = EmailValidator.getInstance(); StringBinding validEmailExpr =
		 * Bindings.createStringBinding( () ->
		 * emailValidator.isValid(email.getText())?email.getText():"",
		 * email.textProperty() );
		 * 
		 * System.out.println("is valid 1 " + validEmailExpr);
		 * System.out.println("Changed from " + oldPropertyValue + " to " +
		 * newPropertyValue); } });
		 */

		emailField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				EmailValidator emailValidator = EmailValidator.getInstance();
				StringBinding validEmailExpr = Bindings.createStringBinding(
						() -> emailValidator.isValid(emailField.getText()) ? emailField.getText() : "",
						emailField.textProperty());

				if (!validEmailExpr.isValid()) {
					emailField.setStyle("-fx-border-color: red ;");
					return;
				}
			}
		});

		ButtonBar control = new ButtonBar();
		control.setPrefSize(800, 30);
		Button ajouter = new Button("Ajouter", new ImageView(okImg));
		Button cancel = new Button("Annuler", new ImageView(cancelImg));
		Button fermer = new Button("Fermer", new ImageView(closeImg));

		// bottom
		// control.setBorder(BorderFactory.createRaisedBevelBorder());
		control.getButtons().add(ajouter);
		control.getButtons().add(cancel);
		control.getButtons().add(fermer);

		control.setPadding(new Insets(5));
		Node wraperBarButton = Borders.wrap(control).etchedBorder().innerPadding(0).buildAll();
		Node wraperAdresse = Borders.wrap(adressComp.getAdressPanel()).lineBorder().color(Color.rgb(168, 211, 255))
				.outerPadding(20, 10, 10, 10).title("Adresse ").buildAll();

		Node wrapercontentEmploye = Borders.wrap(contentEmploye).lineBorder().color(Color.rgb(168, 211, 255))
				.outerPadding(20, 10, 10, 10).title("Ajout d'un nouveau employé ").buildAll();

		contentTmp.setCenter(wrapercontentEmploye);
		contentTmp.setBottom(wraperAdresse);

		contentEmployeTmp.setTop(panIcon);
		contentEmployeTmp.setCenter(contentTmp);
		contentEmployeTmp.setBottom(wraperBarButton);
		BatisUtils.setIconImage(primaryStage);
		Scene scene = new Scene(contentEmployeTmp, 800, 550);
		scene.getStylesheets().add(getClass().getResource("/css/batis.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();

		/**
		 * 
		 */
		fermer.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				primaryStage.close();
			}
		});

		/*
		 * ajouter.setOnAction(new EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent e) {
		 * employeData.add(populateTable(chantier));
		 * 
		 * } });
		 */

		ajouter.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				if (nomField.getText().isEmpty()) {
					// nomField.setStyle("-fx-border-color: red ;");
					nomField.getStyleClass().add("error");
					/*
					 * showAlert(Alert.AlertType.ERROR, contentEmploye.getScene().getWindow(),
					 * "Form Error!", "Please enter your name");
					 */
					return;
				}

				if (prenomField.getText().isEmpty()) {
					prenomField.getStyleClass().add("error");
					return;
				}

				EmailValidator emailValidator = EmailValidator.getInstance();

				if (!emailValidator.isValid(emailField.getText())) {
					emailField.setStyle("-fx-border-color: red ;");
					return;
				}

				if (dateEmbaucheField.getValue() == null) {
					dateEmbaucheField.getStyleClass().add("error");
					return;
				}

				if (qualificationField.getValue() == null) {
					qualificationField.getStyleClass().add("error");
					return;
				}

				if (uniteField.getValue() == null) {
					uniteField.getStyleClass().add("error");
					return;
				}

				if (deviseField.getValue() == null) {
					deviseField.getStyleClass().add("error");
					return;
				}

				if (salaireField.getText().isEmpty()) {
					salaireField.getStyleClass().add("error");
					return;
				}
				if (dateNaissanceField.getValue() == null) {
					dateNaissanceField.getStyleClass().add("error");
					return;
				}

				if (emailField.getText().isEmpty()) {
					emailField.getStyleClass().add("error");
					return;
				}

				if (numeroTelField.getText().isEmpty()) {
					numeroTelField.getStyleClass().add("error");
					return;
				}

				if (employeData != null) {
					employeData.add(populateTable(chantier));
				}

				primaryStage.close();
			}
		});
	}

	public void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initOwner(owner);
		alert.show();
	}

	/**
	 * 
	 * @param chantiera
	 * @return
	 */
	public Employe populateTable(Chantier chantier) {

		NumberFormat doubleFormat = NumberFormat.getNumberInstance();
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.FRANCE);
		currencyFormat.setGroupingUsed(true);

		doubleFormat.setMaximumFractionDigits(2);
		String nomValue = getNomField().getText();
		String prenomValue = getPrenomField().getText();
		String qualificationValue = getQualificationField() != null
				? getQualificationField().getSelectionModel().getSelectedItem().toString()
				: "";
		String dateNaissanceValue = getDateNaissanceField().getValue()
				.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String salaireValue = getSalaireField().getText();

		salaireValue = doubleFormat.format(Double.valueOf(BatisUtils.deleteWhiteSpace(salaireValue)));
		String devise = getDeviseField().getSelectionModel().getSelectedItem().toString();
		String unite = getUniteField().getSelectionModel().getSelectedItem().toString();
		String autresInfos = getAutresInfosField().getText();
		// TODO _salaire = BatisUtils.deleteWhiteSpace(_salaire);
		String email = getEmailField().getText();
		String tel = getNumeroTelField().getText();
		String dateEmbauche = getDateEmbaucheField() != null
				? getDateEmbaucheField().getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
				: "";

		// ADRESS

		String numero = getAdressComp().getNumeroRue().getText();
		String nomRue = getAdressComp().getNomRue().getText();
		String bp = getAdressComp().getCodePostal().getText();
		String city = getAdressComp().getVille().getText();
		String pays = getAdressComp().getPays().getText();
		Adresse adresseEmpl = null;
		// adresseEmpl = adresseService.isAdresseExists(numero, nom, bp, city);

		if (adresseEmpl == null) {
			adresseEmpl = new Adresse();
			adresseEmpl.setNomRue(nomRue);
			adresseEmpl.setNumeroRue(numero);
			adresseEmpl.setVille(city);
			adresseEmpl.setCodePostale(bp);
			adresseEmpl.setPays(pays);
		}

		List<Chantier> listChantier = new ArrayList<Chantier>();
		Role role = null;
		Employe employe = null;
		if (adresseEmpl.getId() != null && !dateNaissanceValue.isEmpty()) {

			// employe = employeService.isEmployeExists(_nom, _prenom, _email,
			// _dateNaissance, adresseEmpl.getId());
		}
		List<Role> listRoles = new ArrayList<Role>();
		List<Employe> listEmployes = new ArrayList<Employe>();

		// role = roleService.isRoleExists(getCodeQualification(_qualification));
		if (role == null) {
			role = new Role();
			role.setDescription(qualificationValue);
			role.setCode(BatisUtils.getCodeQualification(qualificationValue));
		}

		// roleService.saveOrUpdate(role);

		if (employe == null) {
			listChantier.add(chantier);
			employe = new Employe();
			employe.setAdresse(adresseEmpl);
			employe.setNom(nomValue);
			employe.setPrenom(prenomValue);

			employe.setDateNaissance(dateNaissanceValue);
			employe.setFrequenceReunumeration(unite);
			employe.setDevise(devise);
			employe.setTelephone(tel);
			employe.setEmail(email);
			employe.setAutresInfos(autresInfos);
			employe.setListChantier(listChantier);
			employe.setSalaire(salaireValue);
			employe.setDateEmbauche(dateEmbauche);
			listEmployes.add(employe);
			listRoles.add(role);
			employe.getListRole().add(role);

			if (chantier.getListeEmployes() != null) {
				chantier.getListeEmployes().add(employe);
			}
			// TODO ENREGISTRE LE ROELE DANS LE PROJET CAR DANS CHAQUE PROJET ROLE DIFFERENT
			// employe.setListRoles(listRoles);

		}

		if (chantier.getListeEmployes() != null) {
			chantier.getListeEmployes().add(employe);
		} else {
			HashSet<Employe> listEmpl = new HashSet<Employe>();
			listEmpl.add(employe);
			chantier.setListeEmployes(listEmpl);

		}

		// employeService.saveOrUpdate(employe);
		// roleService.saveOrUpdate(role);

		// TODO
		// BatisUtils.setRole(chantier, employe, role);

		return employe;
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
	 * @return the tabPanePrincipale
	 */
	public TabPane getTabPanePrincipale() {
		return tabPanePrincipale;
	}

	/**
	 * @param tabPanePrincipale the tabPanePrincipale to set
	 */
	public void setTabPanePrincipale(TabPane tabPanePrincipale) {
		this.tabPanePrincipale = tabPanePrincipale;
	}

	/**
	 * @return the nomField
	 */
	public TextField getNomField() {
		return nomField;
	}

	/**
	 * @param nomField the nomField to set
	 */
	public void setNomField(TextField nomField) {
		this.nomField = nomField;
	}

	/**
	 * @return the prenomField
	 */
	public TextField getPrenomField() {
		return prenomField;
	}

	/**
	 * @param prenomField the prenomField to set
	 */
	public void setPrenomField(TextField prenomField) {
		this.prenomField = prenomField;
	}

	/**
	 * @return the emailField
	 */
	public TextField getEmailField() {
		return emailField;
	}

	/**
	 * @param emailField the emailField to set
	 */
	public void setEmailField(TextField emailField) {
		this.emailField = emailField;
	}

	/**
	 * @return the numeroTelField
	 */
	public TextField getNumeroTelField() {
		return numeroTelField;
	}

	/**
	 * @param numeroTelField the numeroTelField to set
	 */
	public void setNumeroTelField(TextField numeroTelField) {
		this.numeroTelField = numeroTelField;
	}

	/**
	 * @return the qualificationField
	 */
	public ComboBox<String> getQualificationField() {
		return qualificationField;
	}

	/**
	 * @param qualificationField the qualificationField to set
	 */
	public void setQualificationField(ComboBox<String> qualificationField) {
		this.qualificationField = qualificationField;
	}

	/**
	 * @return the dateNaissanceField
	 */
	public DatePicker getDateNaissanceField() {
		return dateNaissanceField;
	}

	/**
	 * @param dateNaissanceField the dateNaissanceField to set
	 */
	public void setDateNaissanceField(DatePicker dateNaissanceField) {
		this.dateNaissanceField = dateNaissanceField;
	}

	/**
	 * @return the salaireField
	 */
	public DoubleTextField getSalaireField() {
		return salaireField;
	}

	/**
	 * @param salaireField the salaireField to set
	 */
	public void setSalaireField(DoubleTextField salaireField) {
		this.salaireField = salaireField;
	}

	/**
	 * @return the deviseField
	 */
	public ComboBox<String> getDeviseField() {
		return deviseField;
	}

	/**
	 * @param deviseField the deviseField to set
	 */
	public void setDeviseField(ComboBox<String> deviseField) {
		this.deviseField = deviseField;
	}

	/**
	 * @return the uniteField
	 */
	public ComboBox<String> getUniteField() {
		return uniteField;
	}

	/**
	 * @param uniteField the uniteField to set
	 */
	public void setUniteField(ComboBox<String> uniteField) {
		this.uniteField = uniteField;
	}

	/**
	 * @return the autresInfosField
	 */
	public TextField getAutresInfosField() {
		return autresInfosField;
	}

	/**
	 * @param autresInfosField the autresInfosField to set
	 */
	public void setAutresInfosField(TextField autresInfosField) {
		this.autresInfosField = autresInfosField;
	}

	/**
	 * @return the adressComp
	 */
	public AdressComponentUtils getAdressComp() {
		return adressComp;
	}

	/**
	 * @param adressComp the adressComp to set
	 */
	public void setAdressComp(AdressComponentUtils adressComp) {
		this.adressComp = adressComp;
	}

	/**
	 * @return the employe
	 */
	public Employe getEmploye() {
		return employe;
	}

	/**
	 * @param employe the employe to set
	 */
	public void setEmploye(Employe employe) {
		this.employe = employe;
	}

	/**
	 * @return the employeData
	 */
	public ObservableList<Employe> getEmployeData() {
		return employeData;
	}

	/**
	 * @param employeData the employeData to set
	 */
	public void setEmployeData(ObservableList<Employe> employeData) {
		this.employeData = employeData;
	}

	/**
	 * @return the dateEmbaucheField
	 */
	public DatePicker getDateEmbaucheField() {
		return dateEmbaucheField;
	}

	/**
	 * @param dateEmbaucheField the dateEmbaucheField to set
	 */
	public void setDateEmbaucheField(DatePicker dateEmbaucheField) {
		this.dateEmbaucheField = dateEmbaucheField;
	}

}

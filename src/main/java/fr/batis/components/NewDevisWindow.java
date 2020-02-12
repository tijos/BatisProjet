package main.java.fr.batis.components;

import java.util.ArrayList;
import java.util.List;

import org.controlsfx.tools.Borders;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.java.fr.batis.components.common.BatisUtils;
import main.java.fr.batis.components.common.CommonUtils;
import main.java.fr.batis.components.common.ImageUtils;
import main.java.fr.batis.entites.Chantier;
import main.java.fr.batis.entites.Devis;
import main.java.fr.batis.entites.Employe;
import main.java.fr.batis.entites.PhaseConstruction;
import main.java.fr.batis.entites.Role;
import main.java.fr.batis.enumeration.RoleCode;
import main.java.fr.batis.service.EmployeService;
import main.java.fr.batis.service.PhaseService;

public class NewDevisWindow {

	private PhaseConstruction phase;
	private CommonUtils commonUtils;
	private ImageUtils imageUtils;
	private ComboBox<String> listPhases;
	private Chantier chantier;
	private PhaseService phaseService;
	private EmployeService employeService;
	private Employe selectedEmploye;

	public NewDevisWindow(Chantier chantier) {
		super();
		this.commonUtils = new CommonUtils();
		this.imageUtils = new ImageUtils();
		this.chantier = chantier;
	}

	public NewDevisWindow(PhaseConstruction phase, Chantier chantier) {
		super();
		this.phase = phase;
		this.commonUtils = new CommonUtils();
		this.imageUtils = new ImageUtils();
		this.chantier = chantier;
		this.phase = phase;
	}

	/**
	 * 
	 * @param primaryStage
	 * @param chtier
	 */
	public void initSignatureWindow(Stage primaryStage, Chantier chtier) {

		employeService = new EmployeService();
		setEmployeService(employeService);
		phaseService = new PhaseService();
		setPhaseService(phaseService);
		// Image signatureImg = new
		// Image(getClass().getResourceAsStream("/images/signature.png"));
		// Image unCheckedBoxImg = new
		// Image(getClass().getResourceAsStream("/images/unCheckedBox.png"));

		Node panIcon = commonUtils.getHeader("Création du nouveau devis");

		BorderPane panNewDevis = new BorderPane();
		GridPane panNewDevisTmp = commonUtils.getGridPanel();
		BorderPane panNewDevisTmp2 = new BorderPane();

		TextField titre = new TextField();
		titre.setPrefSize(150, 25);

		Label labelNom = new Label("Titre :");

		TextArea description = new TextArea();
		description.setPrefSize(150, 25);
		Label labelDescription = new Label("Description :");

		Label labelListPhase = new Label("Selectionner la phase :");
		listPhases = new ComboBox<String>();
		listPhases.getItems().add("");
		for (PhaseConstruction phse : chtier.getPhasesConstruction()) {
			listPhases.getItems().add(phse.getNom());
		}

		if (getPhase() != null) {
			listPhases.getSelectionModel().select(phase.getNom());
			listPhases.setDisable(true);
			listPhases.setStyle("-fx-opacity: 1; -fx-text-fill: black;");
		}

		/// responsable
		BorderPane borderRespDevis = new BorderPane();
		GridPane gridRespoDevis = getCommonUtils().getGridPanel();
		GridPane gridRespoDevisTmp = getCommonUtils().getGridPanel();

		Label employeComboxLabel = new Label("Séléctionnez un employé ");
		// Label responsableLabel = new Label("Nom : ");

		List<Employe> listeEmploye = employeService.findAll();

		ArrayList<String> employes = new ArrayList<String>();
		for (Employe employe : listeEmploye) {
			employes.add(employe.getId() + ". " + employe.getNom() + " " + employe.getPrenom() + " "
					+ "TODO GET DESCRIPTION");
		}

		ComboBox<String> employeCombox = new ComboBox<String>(FXCollections.observableArrayList(employes));

		Label nomLabel = new Label("Nom : ");
		TextField nom = new TextField();
		Label prenomLabel = new Label("Prnom : ");
		TextField prenom = new TextField();
		Label roleLabel = new Label("Fonction : ");
		ComboBox<String> qualificationField = new ComboBox<String>();
		for (RoleCode quali : RoleCode.values()) {
			qualificationField.getItems().add(quali.getDescription());
		}

		Label telphoneLabel = new Label("Téléphone : ");
		TextField telephone = new TextField();
		Label emailLabel = new Label("Email : ");
		TextField email = new TextField();

		gridRespoDevis.add(nomLabel, 0, 0);
		gridRespoDevis.add(nom, 1, 0);
		gridRespoDevis.add(prenomLabel, 2, 0);
		gridRespoDevis.add(prenom, 3, 0);

		gridRespoDevis.add(roleLabel, 0, 2);
		gridRespoDevis.add(qualificationField, 1, 2);

		gridRespoDevis.add(telphoneLabel, 0, 3);
		gridRespoDevis.add(telephone, 1, 3);

		gridRespoDevis.add(emailLabel, 2, 3);
		gridRespoDevis.add(email, 3, 3);

		gridRespoDevisTmp.add(employeComboxLabel, 0, 0);
		gridRespoDevisTmp.add(employeCombox, 1, 0);

		borderRespDevis.setTop(gridRespoDevisTmp);
		borderRespDevis.setCenter(gridRespoDevis);

		Node wraperBorderRespDevis = Borders.wrap(borderRespDevis).lineBorder().color(Color.rgb(168, 211, 255))
				.outerPadding(10, 5, 5, 5).innerPadding(5).title("Responsable ").buildAll();

		ButtonBar control = new ButtonBar();
		Button create = new Button("Créer", new ImageView(getImageUtils().getAddImg()));
		Button fermer = new Button("Fermer", new ImageView(getImageUtils().getCloseImg()));
		control.getButtons().add(create);
		control.getButtons().add(fermer);
		control.setPadding(new Insets(10));

		panNewDevisTmp.add(labelNom, 0, 0);
		panNewDevisTmp.add(titre, 1, 0);
		panNewDevisTmp.add(labelDescription, 0, 1);
		panNewDevisTmp.add(description, 1, 1);

		panNewDevisTmp.add(labelListPhase, 0, 2);
		panNewDevisTmp.add(listPhases, 1, 2);

		panNewDevisTmp2.setTop(panNewDevisTmp);
		panNewDevisTmp2.setCenter(wraperBorderRespDevis);

		panNewDevis.setTop(panIcon);
		panNewDevis.setCenter(panNewDevisTmp2);
		panNewDevis.setBottom(control);

		BatisUtils.setIconImage(primaryStage);
		Scene scene = new Scene(panNewDevis, 500, 480);
		scene.getStylesheets().add(getClass().getResource("/css/batis.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();

		employeCombox.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (employeCombox.getSelectionModel() != null) {
					String selectedEmployeValue = employeCombox.getSelectionModel().getSelectedItem();
					String id = selectedEmployeValue.split("[.]")[0];

					if (!id.isBlank()) {
						Employe selectedEmploye = getEmployeService().findById(Long.valueOf(id));
						setSelectedEmploye(selectedEmploye);
						nom.setText(getSelectedEmploye() != null ? getSelectedEmploye().getNom() : " ");
						nom.setEditable(false);
						prenom.setText(getSelectedEmploye() != null ? getSelectedEmploye().getPrenom() : " ");
						prenom.setEditable(false);
						telephone.setText(getSelectedEmploye() != null ? getSelectedEmploye().getTelephone() : " ");
						telephone.setEditable(false);
						email.setText(getSelectedEmploye() != null ? getSelectedEmploye().getEmail() : " ");
						email.setEditable(false);
						// TODO
						// qualificationField.getSelectionModel().select(getSelectedEmploye().getRole().getDescription());
						qualificationField.setDisable(true);
					}
				}
			}
		});

		listPhases.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				PhaseConstruction phse = BatisUtils.getPhaseByName(chtier,
						listPhases.getSelectionModel().getSelectedItem());
				setPhase(phse);
			}
		});

		create.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Devis devis = new Devis();
				devis.setNom(titre.getText());
				devis.setDescription(description.getText());
				devis.setPhase(getPhase());

				String qualificationValue = qualificationField.getSelectionModel() != null
						? qualificationField.getSelectionModel().getSelectedItem().toString()
						: "";

				Role role = new Role();

				role.setDescription(qualificationValue);
				role.setCode(BatisUtils.getCodeQualification(qualificationValue));

				Employe responsable = getSelectedEmploye();

				responsable.getListRole().add(role);
				devis.setReponsable(responsable);
				getPhase().getListDevis().add(devis);
				getPhaseService().saveOrUpdate(getPhase());
				// BatisUtils.saveChantier(getPhase().getChantier());
				primaryStage.close();
			}
		});
		/**
		 * 
		 */
		fermer.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				primaryStage.close();
			}
		});

	}

	/**
	 * @return the phase
	 */
	public PhaseConstruction getPhase() {
		return phase;
	}

	/**
	 * @param phase the phase to set
	 */
	public void setPhase(PhaseConstruction phase) {
		this.phase = phase;
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
	 * @return the imageUtils
	 */
	public ImageUtils getImageUtils() {
		return imageUtils;
	}

	/**
	 * @param imageUtils the imageUtils to set
	 */
	public void setImageUtils(ImageUtils imageUtils) {
		this.imageUtils = imageUtils;
	}

	/**
	 * @return the listPhases
	 */
	public ComboBox<String> getListPhases() {
		return listPhases;
	}

	/**
	 * @param listPhases the listPhases to set
	 */
	public void setListPhases(ComboBox<String> listPhases) {
		this.listPhases = listPhases;
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
	 * @return the phaseService
	 */
	public PhaseService getPhaseService() {
		return phaseService;
	}

	/**
	 * @param phaseService the phaseService to set
	 */
	public void setPhaseService(PhaseService phaseService) {
		this.phaseService = phaseService;
	}

	/**
	 * @return the employeService
	 */
	public EmployeService getEmployeService() {
		return employeService;
	}

	/**
	 * @param employeService the employeService to set
	 */
	public void setEmployeService(EmployeService employeService) {
		this.employeService = employeService;
	}

	/**
	 * @return the selectedEmploye
	 */
	public Employe getSelectedEmploye() {
		return selectedEmploye;
	}

	/**
	 * @param selectedEmploye the selectedEmploye to set
	 */
	public void setSelectedEmploye(Employe selectedEmploye) {
		this.selectedEmploye = selectedEmploye;
	}

}

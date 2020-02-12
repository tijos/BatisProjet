package main.java.fr.batis.components;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import org.controlsfx.tools.Borders;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.java.fr.batis.components.common.BatisUtils;
import main.java.fr.batis.components.common.CommonUtils;
import main.java.fr.batis.components.common.DateConverterHelper;
import main.java.fr.batis.components.custom.DoubleTextField;
import main.java.fr.batis.entites.Devis;
import main.java.fr.batis.entites.Materiel;
import main.java.fr.batis.enumeration.StatutAchatMateriel;
import main.java.fr.batis.enumeration.StatutTraitementMateriel;
import main.java.fr.batis.enumeration.UniteMesure;
import main.java.fr.batis.service.DevisService;

public class SuiviAchatWindow {
	private ComboBox<String> typeMateriel;
	private DoubleTextField prixUnitairePrevu;
	private DoubleTextField prixUnitaireReel;
	private TextArea observation;
	private ComboBox<String> devise;
	private TableView<Materiel> dataTable;
	private TableView<Materiel> table;
	private TextField qualitePrevu;
	private TextField qualiteAchete;
	private DoubleTextField quantitePrevu;
	private DoubleTextField quantiteAchete;
	private ComboBox<String> unite;
	private Materiel currentMateriel;
	private SuiviAchatWindow suiviAchatWindow;
	private Font font;

	private DatePicker dateAchat;
	private ObservableList<Materiel> materielDataTable;
	private CommonUtils commonUtils;
	private DevisService devisService;
	private Devis selectedDevis;

	public static final String ERROR = "error";

	public SuiviAchatWindow() {
		super();
		this.devisService = new DevisService();
	}

	public SuiviAchatWindow(ObservableList<Materiel> materielTableData2, Devis slectedDevis) {
		this.materielDataTable = materielTableData2;
		this.commonUtils = new CommonUtils();
		this.selectedDevis = slectedDevis;
		this.suiviAchatWindow = this;

		this.devisService = new DevisService();

	}

	public void initComponent(Stage primaryStage, ObservableList<Materiel> materielTableData2) {

		Node panIcon = commonUtils.getHeader("Suivi dachats des matériaux");
		NumberFormat doubleFormat = NumberFormat.getNumberInstance(Locale.FRANCE);
		doubleFormat.setMaximumFractionDigits(2);

		BorderPane contentSuiviAchat = new BorderPane();

		GridPane contentMterielTmp = commonUtils.getGridPanel();
		GridPane contentSelectMat = commonUtils.getGridPanel();

		Label nomTypenLabel = new Label("Materiel:");
		typeMateriel = new ComboBox<String>();
		typeMateriel.getItems().add("");
		List<Materiel> listMat = getSelectedDevis().getListMateriaux();

		for (Materiel materiel : listMat) {
			if (StatutAchatMateriel.A_ACHETER.getCode().equals(materiel.getCodeStatutAchat())
					&& StatutTraitementMateriel.NOT_TREATED.getCode().equals(materiel.getCodeTraitement())
			// &&
			// StatutTraitementMat.NOT_TREATED.getCode().equals(materiel.getCodeDevisInitial())
			) {
				typeMateriel.getItems().add(materiel.getNom());
			}

		}

		//
		Label qualitePrevLabel = new Label("Qualité / Cathégorie prevue:");
		qualitePrevu = new TextField();
		qualitePrevu.setEditable(false);
		Label qualiteReelLabel = new Label("Qualité / Cathégorie achetée:");
		qualiteAchete = new TextField();

		Label quantitePrevuLabel = new Label("Quantité prevue:");
		quantitePrevu = new DoubleTextField();
		quantitePrevu.setEditable(false);

		Label quantiteAcheteLabel = new Label("Quantité achetée:");
		quantiteAchete = new DoubleTextField();

		Label uniteLabel = new Label("Unité :");
		unite = new ComboBox<String>();

		for (UniteMesure uniteMes : UniteMesure.values()) {
			unite.getItems().add(uniteMes.getDescription());
		}
		unite.setEditable(false);

		Label prixUPrevuLabel = new Label("Prix unitaire prevu :");
		prixUnitairePrevu = new DoubleTextField();
		prixUnitairePrevu.setEditable(false);

		Label prixUAcheteLabel = new Label("Prix unitaire achat :");
		prixUnitaireReel = new DoubleTextField();

		Label deviseLabel = new Label("Devise :");
		devise = new ComboBox<String>();
		devise.getItems().add("Fbu");
		devise.getItems().add("€");
		devise.getItems().add("$");
		devise.setEditable(false);

		Label dateAchatLabel = new Label("Date d'achat :");
		dateAchat = DateConverterHelper.getConvetedDate();
		//
		Callback<DatePicker, DateCell> dayCellFactory = commonUtils.getDayCellFactory();
		dateAchat.setDayCellFactory(dayCellFactory);

		Image okImg = new Image(getClass().getResourceAsStream("/images/ok.png"));
		Image closeImg = new Image(getClass().getResourceAsStream("/images/exit.png"));
		Image cancelImg = new Image(getClass().getResourceAsStream("/images/undo.png"));

		ButtonBar control = new ButtonBar();
		Button ajouter = new Button("Ajouter");
		ajouter.setGraphic(new ImageView(okImg));
		Button cancel = new Button("Annuler");
		cancel.setGraphic(new ImageView(cancelImg));
		Button fermer = new Button("Fermer");
		fermer.setGraphic(new ImageView(closeImg));

		control.getButtons().add(ajouter);
		control.getButtons().add(cancel);
		control.getButtons().add(fermer);

		Node wraperBarButton = Borders.wrap(control).etchedBorder().highlight(Color.rgb(168, 211, 255))
				// .outerPadding(10, 10,10,10)
				.innerPadding(5)
				// .title("Ajout du matérielpour la phase "+slsctedPhase.getNom())
				.buildAll();

		fermer.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				primaryStage.close();
			}

		});

		cancel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				typeMateriel.getSelectionModel().clearSelection();
				devise.getSelectionModel().clearSelection();
				unite.getSelectionModel().clearSelection();
				qualiteAchete.clear();
				prixUnitaireReel.clear();
				quantiteAchete.clear();
				dateAchat.setValue(null);
			}
		});

		ajouter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (prixUnitaireReel.getText().isEmpty()) {
					prixUnitaireReel.getStyleClass().add(ERROR);

					return;
				}

				if (qualiteAchete.getText().isEmpty()) {
					qualiteAchete.getStyleClass().add(ERROR);
					return;
				}

				if (quantiteAchete.getText().isEmpty()) {
					quantiteAchete.getStyleClass().add(ERROR);
					return;
				}

				if (unite.getSelectionModel().getSelectedItem() == null) {
					unite.getStyleClass().add(ERROR);
					BatisUtils.showAlert(Alert.AlertType.ERROR, null, "Erreur!", "Le champ Unité est obligatoire !");
					return;
				}

				if (devise.getSelectionModel().getSelectedItem() == null) {
					devise.getStyleClass().add(ERROR);

					return;
				}

				if (getDateAchat().getValue() == null) {
					dateAchat.getStyleClass().add(ERROR);

					return;
				}
				materielTableData2.add(populateTable(getCurrentMateriel()));
				primaryStage.close();
			}
		});

		contentSelectMat.add(nomTypenLabel, 0, 0);
		contentSelectMat.add(typeMateriel, 1, 0);

		contentMterielTmp.add(qualitePrevLabel, 0, 0);
		contentMterielTmp.add(qualitePrevu, 1, 0);

		contentMterielTmp.add(qualiteReelLabel, 5, 0);
		contentMterielTmp.add(qualiteAchete, 6, 0);

		contentMterielTmp.add(quantitePrevuLabel, 0, 1);
		contentMterielTmp.add(quantitePrevu, 1, 1);

		contentMterielTmp.add(quantiteAcheteLabel, 5, 1);
		contentMterielTmp.add(quantiteAchete, 6, 1);

		contentMterielTmp.add(uniteLabel, 0, 2);
		contentMterielTmp.add(unite, 1, 2);

		contentMterielTmp.add(prixUPrevuLabel, 5, 2);
		contentMterielTmp.add(prixUnitairePrevu, 6, 2);

		contentMterielTmp.add(prixUAcheteLabel, 0, 3);
		contentMterielTmp.add(prixUnitaireReel, 1, 3);

		contentMterielTmp.add(deviseLabel, 5, 3);
		contentMterielTmp.add(devise, 6, 3);

		contentMterielTmp.add(dateAchatLabel, 0, 4);
		contentMterielTmp.add(dateAchat, 1, 4);

		BorderPane contentSelectedMat = new BorderPane();
		contentSelectedMat.setTop(contentSelectMat);
		contentSelectedMat.setCenter(contentMterielTmp);

		Node wraperContentSelectedMat = Borders.wrap(contentSelectedMat).lineBorder().color(Color.rgb(168, 211, 255))
				.outerPadding(20, 10, 10, 10).innerPadding(10).title("Suivi achat materiel ").buildAll();

		contentSuiviAchat.setTop(panIcon);
		contentSuiviAchat.setCenter(wraperContentSelectedMat);
		contentSuiviAchat.setBottom(wraperBarButton);

		// contentSelectedMat.setCenter(wraperContentSelectedMat);

		BatisUtils.setIconImage(primaryStage);
		Scene scene = new Scene(contentSuiviAchat, 780, 450);
		scene.getStylesheets().add(getClass().getResource("/css/batis.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();

		typeMateriel.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				Materiel mat = BatisUtils.getMaterielByNameAndDevis(newValue, getSelectedDevis());
				// TODO NETOYER LA SCENE
				setCurrentMateriel(mat);
				populateFromDevis(mat);
			}
		});
	}

	/**
	 * 
	 * @param materiel
	 */
	public void populateFromDevis(Materiel materiel) {
		NumberFormat doubleFormat = NumberFormat.getNumberInstance();
		doubleFormat.setMaximumFractionDigits(2);

		getPrixUnitairePrevu().setText(BatisUtils.deleteWhiteSpace(materiel.getPrixUnitairePrevu()));
		getQualitePrevu().setText(materiel.getQualitePrevu());
		getQuantitePrevu().setText(BatisUtils.deleteWhiteSpace(materiel.getQuantitePrevu()));
		getUnite().getSelectionModel().select(materiel.getUniteDemesure());
		getDevise().getSelectionModel().select(materiel.getDevise());

	}

	/**
	 * 
	 * @param table
	 * @param frais
	 * @return
	 */
	public Materiel populateTable(Materiel materiel) {

		Materiel materielAchete = (Materiel) materiel.clone();
		Materiel materielClone = (Materiel) materiel.clone();
		materiel.setCodeTraitement(StatutTraitementMateriel.TREATED.getCode());
		materielAchete.setId(null);
		NumberFormat doubleFormat = NumberFormat.getNumberInstance();

		String prixUPrevu = getPrixUnitairePrevu().getText();
		String prixUReel = getPrixUnitaireReel().getText();
		String qualiteReel = getQualiteAchete().getText();
		String qtitePrevu = getQuantitePrevu().getText();
		String quantiteReel = getQuantiteAchete().getText();
		Double prixUPrevuDoubleValue = Double.valueOf(BatisUtils.deleteWhiteSpace(prixUPrevu));

		Double quantitePrevu = 1.0;
		if (qtitePrevu != null && !qtitePrevu.isEmpty()) {
			quantitePrevu = Double.valueOf(BatisUtils.deleteWhiteSpace(qtitePrevu));
		}

		Double prixUnitReel = 0.0;
		Double prixTotPrevuReel = 0.0;

		if (prixUReel != null && !prixUReel.isEmpty()) {
			prixUReel = BatisUtils.deleteWhiteSpace(prixUReel);
			prixUnitReel = Double.valueOf(prixUReel);
		}
		Integer quantReel = 1;
		if (quantiteReel != null && !quantiteReel.isEmpty()) {
			quantiteReel = BatisUtils.deleteWhiteSpace(quantiteReel);
			quantReel = Integer.valueOf(quantiteReel);
		}

		Double prixTotReel = prixUnitReel * quantReel;
		if (quantitePrevu < quantReel) {
			prixTotPrevuReel = prixUPrevuDoubleValue * quantitePrevu;
		} else {
			prixTotPrevuReel = prixUPrevuDoubleValue * quantReel;
		}

		Double prixUP = Double.valueOf(BatisUtils.deleteWhiteSpace(prixUPrevu));
		Double prixUR = Double.valueOf(BatisUtils.deleteWhiteSpace(prixUPrevu));
		prixUPrevu = doubleFormat.format(prixUP);
		prixUReel = doubleFormat.format(prixUR);

		String dateAchat = getDateAchat().getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		materielAchete.setDateAchat(dateAchat);

		Double diffPrix = prixTotPrevuReel - prixTotReel;

		materielAchete.setQualiteReel(qualiteReel);
		materielAchete.setQuantiteReel(doubleFormat.format(quantReel));
		materielAchete.setPrixUnitaireReel(doubleFormat.format(prixUnitReel));
		materielAchete.setPrixTotPrevu(doubleFormat.format(prixTotPrevuReel));
		materielAchete.setPrixTotReel(doubleFormat.format(prixTotReel));
		materielAchete.setDateAchat(dateAchat);
		materielAchete.setDiffPrix(String.valueOf(diffPrix));

		// SI N OUBLIE DE SELECTIONNER LE NOUVEAU STATUT ON LE FAIT PAR DEFAUT

		if (materielAchete.getQuantitePrevu().equals(materielAchete.getQuantiteReel())
				|| Double.valueOf(BatisUtils.deleteWhiteSpace(materielAchete.getQuantitePrevu())) < Double
						.valueOf(BatisUtils.deleteWhiteSpace(materielAchete.getQuantiteReel()))) {
			materielAchete.setCodeStatutAchat(StatutAchatMateriel.ACHETE_T.getCode());
			materielAchete.setStatutAchat(StatutAchatMateriel.ACHETE_T.getDescription());

			materielAchete.setCodeDevisInitial(0);
		} else {
			// if (!materielAchete.getQuantitePrevu().equals(materiel.getQuantiteReel())) {
			materielAchete.setCodeStatutAchat(StatutAchatMateriel.ACHETE_P.getCode());
			materielAchete.setStatutAchat(StatutAchatMateriel.ACHETE_P.getDescription());
			// }

			materielAchete.setCodeTraitement(StatutTraitementMateriel.TREATED.getCode());
			// SI achete en pariellement il creer le reste
			// if(_StatutAchatMateriel.ACHETE_P.getCode().equals(materielAchete.getCodeStatutAchat())
			// ){
			// materielAchete.setStatutAchat(_StatutAchatMateriel.ACHETE_T.getDescription());
			// materielAchete.setCodeStatutAchat(_StatutAchatMateriel.ACHETE_T.getCode());
			Integer quantiteRestant = (int) (quantitePrevu - quantReel);
			Double prixTotPrevu = prixUPrevuDoubleValue * quantiteRestant;

			materielClone.setCodeStatutAchat(StatutAchatMateriel.A_ACHETER.getCode());
			materielClone.setCodeTraitement(StatutTraitementMateriel.NOT_TREATED.getCode());
			materielClone.setQualiteReel(null);
			materielClone.setQuantiteReel(null);
			materielClone.setStatutAchat(StatutAchatMateriel.A_ACHETER.getDescription());
			materielClone.setCodeDevisInitial(0);
			materielClone.setPrixUnitaireReel(null);
			materielClone.setPrixTotPrevu(doubleFormat.format(prixTotPrevu));
			materielClone.setQuantitePrevu(doubleFormat.format(quantiteRestant));
			materielClone.setPrixTotReel(null);
			materielClone.setId(null);
			materielClone.getDevis().getListMateriaux().add(materielClone);

		}
		materielAchete.getDevis().getListMateriaux().add(materielAchete);
		return materielAchete;
	}

	public ComboBox<String> getTypeMateriel() {
		return typeMateriel;
	}

	public void setTypeMateriel(ComboBox<String> typeMateriel) {
		this.typeMateriel = typeMateriel;
	}

	public DoubleTextField getPrixUnitairePrevu() {
		return prixUnitairePrevu;
	}

	public void setPrixUnitairePrevu(DoubleTextField prixUnitairePrevu) {
		this.prixUnitairePrevu = prixUnitairePrevu;
	}

	public DoubleTextField getPrixUnitaireReel() {

		return prixUnitaireReel;
	}

	public void setPrixUnitaireReel(DoubleTextField prixUnitaireReel) {
		this.prixUnitaireReel = prixUnitaireReel;
	}

	public ComboBox<String> getDevise() {
		return devise;
	}

	public void setDevise(ComboBox<String> devise) {
		this.devise = devise;
	}

	public TableView<Materiel> getDataTable() {
		return dataTable;
	}

	public void setDataTable(TableView<Materiel> dataTable) {
		this.dataTable = dataTable;
	}

	public TableView<Materiel> getTable() {
		return table;
	}

	public void setTable(TableView<Materiel> table) {
		this.table = table;
	}

	public TextField getQualitePrevu() {
		return qualitePrevu;
	}

	public void setQualitePrevu(TextField qualitePrevu) {
		this.qualitePrevu = qualitePrevu;
	}

	public TextField getQualiteAchete() {
		return qualiteAchete;
	}

	public void setQualiteAchete(TextField qualiteAchete) {
		this.qualiteAchete = qualiteAchete;
	}

	public DoubleTextField getQuantitePrevu() {
		return quantitePrevu;
	}

	public void setQuantitePrevu(DoubleTextField quantitePrevu) {
		this.quantitePrevu = quantitePrevu;
	}

	public DoubleTextField getQuantiteAchete() {
		return quantiteAchete;
	}

	public void setQuantiteAchete(DoubleTextField quantiteAchete) {
		this.quantiteAchete = quantiteAchete;
	}

	public ComboBox<String> getUnite() {
		return unite;
	}

	public void setUnite(ComboBox<String> unite) {
		this.unite = unite;
	}

	/**
	 * @return the currentMateriel
	 */
	public Materiel getCurrentMateriel() {
		return currentMateriel;
	}

	/**
	 * @param currentMateriel the currentMateriel to set
	 */
	public void setCurrentMateriel(Materiel currentMateriel) {
		this.currentMateriel = currentMateriel;
	}

	/**
	 * @return the suiviAchatWindow
	 */
	public SuiviAchatWindow getSuiviAchatWindow() {
		return suiviAchatWindow;
	}

	/**
	 * @param suiviAchatWindow the suiviAchatWindow to set
	 */
	public void setSuiviAchatWindow(SuiviAchatWindow suiviAchatWindow) {
		this.suiviAchatWindow = suiviAchatWindow;
	}

	/**
	 * @return the observation
	 */
	public TextArea getObservation() {
		return observation;
	}

	/**
	 * @param observation the observation to set
	 */
	public void setObservation(TextArea observation) {
		this.observation = observation;
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
	 * @return the dateAchat
	 */
	public DatePicker getDateAchat() {
		return dateAchat;
	}

	/**
	 * @param dateAchat the dateAchat to set
	 */
	public void setDateAchat(DatePicker dateAchat) {
		this.dateAchat = dateAchat;
	}

	/**
	 * @return the materielDataTable
	 */
	public ObservableList<Materiel> getMaterielDataTable() {
		return materielDataTable;
	}

	/**
	 * @param materielDataTable the materielDataTable to set
	 */
	public void setMaterielDataTable(ObservableList<Materiel> materielDataTable) {
		this.materielDataTable = materielDataTable;
	}

	/**
	 * @return the devisService
	 */
	public DevisService getDevisService() {
		return devisService;
	}

	/**
	 * @param devisService the devisService to set
	 */
	public void setDevisService(DevisService devisService) {
		this.devisService = devisService;
	}

	/**
	 * @return the selectedDevis
	 */
	public Devis getSelectedDevis() {
		return selectedDevis;
	}

	/**
	 * @param selectedDevis the selectedDevis to set
	 */
	public void setSelectedDevis(Devis selectedDevis) {
		this.selectedDevis = selectedDevis;
	}

	/**
	 * @return the font
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * @param font the font to set
	 */
	public void setFont(Font font) {
		this.font = font;
	}

}

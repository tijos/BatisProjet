package main.java.fr.batis.components;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.controlsfx.tools.Borders;

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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.java.fr.batis.components.common.BatisUtils;
import main.java.fr.batis.components.common.CommonUtils;
import main.java.fr.batis.components.common.DateConverterHelper;
import main.java.fr.batis.components.common.ImageUtils;
import main.java.fr.batis.components.custom.DoubleTextField;
import main.java.fr.batis.entites.Devis;
import main.java.fr.batis.entites.Materiel;
import main.java.fr.batis.enumeration.CodeMateriaux;
import main.java.fr.batis.enumeration.StatutAchatMateriel;
import main.java.fr.batis.enumeration.StatutTraitementMateriel;
import main.java.fr.batis.enumeration.UniteMesure;
import main.java.fr.batis.service.MaterielService;

public class AjoutMaterielWindow {
	private ComboBox<String> typeMateriel;
	private DoubleTextField prixUnitairePrevu;
	private ComboBox<String> devise;
	private ObservableList<Materiel> materielDataTable;
	private TextField qualite;
	private DoubleTextField quantite;
	private ComboBox<String> unite;
	private ComboBox<String> statut;
	private Devis selectedDevis;
	private CommonUtils commonUtils;
	private MaterielService materielService;
	private ImageUtils imageUtils;
	private DatePicker dateAchat;

	public final String ERROR = "error";

	public AjoutMaterielWindow() {
		super();
		this.imageUtils = new ImageUtils();
	}

	/**
	 * 
	 * @param materielTableData
	 * @param slsctedDevis
	 * @param isAutomaticSave
	 */
	public AjoutMaterielWindow(ObservableList<Materiel> materielTableData, Devis slsctedDevis,
			Boolean isAutomaticSave) {
		this.materielDataTable = materielTableData;
		this.commonUtils = new CommonUtils();
		this.materielService = new MaterielService();
		this.selectedDevis = slsctedDevis;
		this.imageUtils = new ImageUtils();
	}

	public void initComponent(Stage primaryStage, ObservableList<Materiel> materielTableData, Devis slsctedDevis,
			Boolean isAutomaticSave) throws Exception {

		Image okImg = new Image(getClass().getResourceAsStream("/images/ok.png"));
		Image cancelImg = new Image(getClass().getResourceAsStream("/images/undo.png"));
		Image closeImg = new Image(getClass().getResourceAsStream("/images/exit.png"));

		Node panIcon = commonUtils
				.getHeader("Nouveau matériel pour le devis << " + getSelectedDevis().getNom() + " >>");

		BorderPane contentMterielTmp = new BorderPane();
		GridPane contentMteriel = commonUtils.getGridPanel();
		Label nomTypenLabel = new Label("Nom / Type :");

		typeMateriel = BatisUtils.getListMateriaux();

		Label qualiteLabel = new Label("Qualite / Cathégorie :");
		qualite = new TextField();

		Label quantiteLabel = new Label("Quantité :");
		NumberFormat doubleFormat = NumberFormat.getNumberInstance(Locale.FRANCE);
		doubleFormat.setMaximumFractionDigits(2);

		quantite = new DoubleTextField();
		Label uniteLabel = new Label("Unité :");
		unite = new ComboBox<String>();
		unite.getItems().add("");

		for (UniteMesure uniteItem : UniteMesure.values()) {
			unite.getItems().add(uniteItem.getDescription());
		}

		Label prixUnitaireLabel = new Label("Prix unitaire / Montant :");
		prixUnitairePrevu = new DoubleTextField();
		Label deviseLabel = new Label("Devise :");
		devise = new ComboBox<String>();
		devise.getItems().add("Fbu");
		devise.getItems().add("€");
		devise.getItems().add("$");

		Label statutLabel = new Label("Statut :");
		statut = new ComboBox<String>();
		for (StatutAchatMateriel stat : StatutAchatMateriel.values()) {
			if (stat.getDescription().equals(StatutAchatMateriel.A_ACHETER.getDescription())
					|| stat.getDescription().equals(StatutAchatMateriel.ACHETE_T.getDescription())) {
				statut.getItems().add(stat.getDescription());
			}
		}

		Label dateAchatLabel = new Label("Date d'achat :");
		dateAchat = DateConverterHelper.getConvetedDate();

		Callback<DatePicker, DateCell> dayCellFactory = commonUtils.getDayCellFactory();
		dateAchat.setDayCellFactory(dayCellFactory);
		dateAchat.setVisible(false);
		dateAchatLabel.setVisible(false);
		// save
		ButtonBar control = new ButtonBar();
		Button ajouter = new Button("Ajouter", new ImageView(okImg));
		Button cancel = new Button("Annuler", new ImageView(cancelImg));
		Button fermer = new Button("Fermer", new ImageView(closeImg));
		control.setPadding(new Insets(15));

		control.getButtons().add(ajouter);
		control.getButtons().add(cancel);
		control.getButtons().add(fermer);

		control.setPadding(new Insets(5));
		Node wraperBarButton = Borders.wrap(control).etchedBorder().innerPadding(0).buildAll();

		contentMteriel.add(nomTypenLabel, 0, 0);
		contentMteriel.add(typeMateriel, 1, 0);
		contentMteriel.add(qualiteLabel, 5, 0);
		contentMteriel.add(qualite, 6, 0);

		contentMteriel.add(quantiteLabel, 0, 1);
		contentMteriel.add(quantite, 1, 1);
		contentMteriel.add(uniteLabel, 5, 1);
		contentMteriel.add(unite, 6, 1);

		contentMteriel.add(prixUnitaireLabel, 0, 2);
		contentMteriel.add(prixUnitairePrevu, 1, 2);

		contentMteriel.add(deviseLabel, 5, 2);
		contentMteriel.add(devise, 6, 2);

		contentMteriel.add(statutLabel, 0, 3);
		contentMteriel.add(statut, 1, 3);

		contentMteriel.add(dateAchatLabel, 5, 3);
		contentMteriel.add(dateAchat, 6, 3);

		Node wraperContentMteriel = Borders.wrap(contentMteriel).lineBorder().color(Color.rgb(168, 211, 255))
				.outerPadding(20, 10, 10, 10).innerPadding(10)
				.title("Ajout du matériel pour le devis  " + slsctedDevis.getNom()).buildAll();

		contentMterielTmp.setTop(panIcon);
		contentMterielTmp.setCenter(wraperContentMteriel);
		contentMterielTmp.setBottom(wraperBarButton);

		BatisUtils.setIconImage(primaryStage);
		Scene scene = new Scene(contentMterielTmp, 750, 370);
		scene.getStylesheets().add(getClass().getResource("/css/batis.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();

		statut.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (statut.getSelectionModel().getSelectedItem() != null && StatutAchatMateriel.ACHETE_T
						.getDescription().equals(statut.getSelectionModel().getSelectedItem())) {
					dateAchat.setVisible(true);
					dateAchatLabel.setVisible(true);
				} else {
					dateAchat.setVisible(false);
					dateAchatLabel.setVisible(false);
				}
			}
		});
		ajouter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (prixUnitairePrevu.getText().isEmpty() && !CodeMateriaux.MAINS_D_OEUVRE.getDescription()
						.equals(typeMateriel.getSelectionModel().getSelectedItem())) {
					prixUnitairePrevu.getStyleClass().add(ERROR);

					BatisUtils.showAlert(Alert.AlertType.ERROR, scene.getWindow(), "Erreur!",
							"Le champ prix unitaire est obligatoire !");
					return;
				}

				if (prixUnitairePrevu.getText().isEmpty()) {
					prixUnitairePrevu.getStyleClass().add(ERROR);
					return;
				}

				if (qualite.getText().isEmpty()) {
					qualite.getStyleClass().add(ERROR);

					return;
				}

				if (quantite.getText().isEmpty()) {
					quantite.getStyleClass().add(ERROR);

					return;
				}

				if (unite.getSelectionModel().getSelectedItem() == null) {
					unite.getStyleClass().add(ERROR);
					BatisUtils.showAlert(Alert.AlertType.ERROR, scene.getWindow(), "Erreur!",
							"Le champ Unité est obligatoire !");
					return;
				}

				if (statut.getSelectionModel().getSelectedItem() == null) {
					statut.getStyleClass().add(ERROR);

					BatisUtils.showAlert(Alert.AlertType.ERROR, scene.getWindow(), "Erreur!",
							"Le champ Statut est obligatoire !");
					return;
				}

				if (typeMateriel.getSelectionModel().getSelectedItem() == null) {
					typeMateriel.getStyleClass().add(ERROR);

					BatisUtils.showAlert(Alert.AlertType.ERROR, scene.getWindow(), "Erreur!",
							"Le champ Type matériel est obligatoire !");
					return;
				}

				if (devise.getSelectionModel().getSelectedItem() == null) {
					devise.getStyleClass().add(ERROR);

					BatisUtils.showAlert(Alert.AlertType.ERROR, scene.getWindow(), "Erreur!",
							"Le champ devise est obligatoire !");
					return;
				}

				materielDataTable.add(populateTable(getSelectedDevis(), isAutomaticSave));
				primaryStage.close();
			}
		});

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
				prixUnitairePrevu.clear();
				devise.getSelectionModel().clearSelection();
				qualite.clear();
				quantite.clear();
				unite.getSelectionModel().clearSelection();
				statut.getSelectionModel().clearSelection();

			}
		});
	}

	@SuppressWarnings("unused")
	private void activerDesactiver(boolean b) {

		qualite.setEditable(b);
		quantite.setEditable(b);
		unite.setEditable(b);
	}

	/**
	 * 
	 * @param table
	 * @param frais
	 * @return
	 */
	public Materiel populateTable(Devis devis, Boolean saveAutomatic) {

		Materiel materiel = new Materiel();
		NumberFormat doubleFormat = NumberFormat.getNumberInstance();
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.FRANCE);
		currencyFormat.setGroupingUsed(true);

		doubleFormat.setMaximumFractionDigits(2);

		String nomMateriel = getTypeMateriel().getSelectionModel().getSelectedItem().toString();
		String prixU = getPrixUnitairePrevu().getText();
		String devise = getDevise().getSelectionModel().getSelectedItem().toString();
		String qualite = getQualite().getText();
		String quantite = getQuantite().getText();
		String statut = getStatut().getSelectionModel().getSelectedItem().toString();
		String unite = getUnite().getSelectionModel().getSelectedItem().toString();
		prixU = BatisUtils.deleteWhiteSpace(prixU);
		quantite = BatisUtils.deleteWhiteSpace(quantite);
		Double prixUnit = Double.valueOf(prixU);
		Integer quant = null;
		Double prixTot = null;
		String prixTotStringValue = null;
		if (!quantite.isEmpty()) {
			quant = Integer.valueOf(quantite);
			prixTot = prixUnit * quant;
			prixTotStringValue = doubleFormat.format(prixTot);
		}

		String statutAchat = materiel.getStatutAchat();
		if (StatutAchatMateriel.ACHETE_T.getDescription().equals(statutAchat)) {
			materiel.setQuantiteReel(doubleFormat.format(quant));
			materiel.setPrixTotReel(prixTotStringValue);
			materiel.setDiffPrix(String.valueOf(0));
			materiel.setQualiteReel(quantite);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String dateAchat = simpleDateFormat.format(new Date());
			materiel.setDateAchat(dateAchat);
		} else {
			materiel.setCodeStatutAchat(StatutAchatMateriel.A_ACHETER.getCode());
			materiel.setCodeDevisInitial(StatutAchatMateriel.DEVIS_INIT.getCode());
			materiel.setCodeTraitement(StatutTraitementMateriel.NOT_TREATED.getCode());
		}

		materiel.setUniteDemesure(unite);
		materiel.setNom(nomMateriel);
		materiel.setQualitePrevu(qualite);
		materiel.setStatutAchat(statut);
		materiel.setDevise(devise);
		materiel.setPrixUnitairePrevu(doubleFormat.format(prixUnit));
		materiel.setQuantitePrevu(doubleFormat.format(quant));
		materiel.setPrixTotPrevu(prixTotStringValue);
		materiel.setQuantitePrevuInit(doubleFormat.format(quant));
		materiel.setPrixTotPrevuInit(prixTotStringValue);

		if (devis != null) {
			materiel.setIdChantier(devis.getPhase().getChantier().getId());
			materiel.setDevis(devis);
			devis.getListMateriaux().add(materiel);

		}

		if (saveAutomatic) {
			getMaterielService().saveOrUpdate(materiel);
		}
		return materiel;
	}

	/**
	 * @return the typeMateriel
	 */
	public ComboBox<String> getTypeMateriel() {
		return typeMateriel;
	}

	/**
	 * @param typeMateriel the typeMateriel to set
	 */
	public void setTypeMateriel(ComboBox<String> typeMateriel) {
		this.typeMateriel = typeMateriel;
	}

	/**
	 * @return the prixUnitairePrevu
	 */
	public DoubleTextField getPrixUnitairePrevu() {
		return prixUnitairePrevu;
	}

	/**
	 * @param prixUnitairePrevu the prixUnitairePrevu to set
	 */
	public void setPrixUnitairePrevu(DoubleTextField prixUnitairePrevu) {
		this.prixUnitairePrevu = prixUnitairePrevu;
	}

	/**
	 * @return the devise
	 */
	public ComboBox<String> getDevise() {
		return devise;
	}

	/**
	 * @param devise the devise to set
	 */
	public void setDevise(ComboBox<String> devise) {
		this.devise = devise;
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
	 * @return the qualite
	 */
	public TextField getQualite() {
		return qualite;
	}

	/**
	 * @param qualite the qualite to set
	 */
	public void setQualite(TextField qualite) {
		this.qualite = qualite;
	}

	/**
	 * @return the quantite
	 */
	public DoubleTextField getQuantite() {
		return quantite;
	}

	/**
	 * @param quantite the quantite to set
	 */
	public void setQuantite(DoubleTextField quantite) {
		this.quantite = quantite;
	}

	/**
	 * @return the unite
	 */
	public ComboBox<String> getUnite() {
		return unite;
	}

	/**
	 * @param unite the unite to set
	 */
	public void setUnite(ComboBox<String> unite) {
		this.unite = unite;
	}

	/**
	 * @return the statut
	 */
	public ComboBox<String> getStatut() {
		return statut;
	}

	/**
	 * @param statut the statut to set
	 */
	public void setStatut(ComboBox<String> statut) {
		this.statut = statut;
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
	 * @return the materielService
	 */
	public MaterielService getMaterielService() {
		return materielService;
	}

	/**
	 * @param materielService the materielService to set
	 */
	public void setMaterielService(MaterielService materielService) {
		this.materielService = materielService;
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

}

package main.java.fr.batis.components.tabs;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import main.java.fr.batis.components.SuiviJourWindow;
import main.java.fr.batis.components.common.BatisUtils;
import main.java.fr.batis.components.common.CommonUtils;
import main.java.fr.batis.components.common.ImageUtils;
import main.java.fr.batis.components.tables.SuiviJournalierTable;
import main.java.fr.batis.entites.Chantier;
import main.java.fr.batis.entites.CompteRenduJournalier;
import main.java.fr.batis.entites.PhaseConstruction;

public class PilotageTab {
	private CommonUtils commonUtils;
	private ImageUtils imageUtils;

	private ObservableList<CompteRenduJournalier> suiviJourTableData;
	private TableView<CompteRenduJournalier> suiviJourTable;

	public PilotageTab() {
		this.commonUtils = new CommonUtils();
		this.imageUtils = new ImageUtils();
	}

	/**
	 * 
	 * @param chantier
	 * @return
	 */
	public Tab getPilotageTab(Chantier chantier) {

		Image tabPilotageImg = imageUtils.getTabPilotageImg();
		Tab pilotageTab = new Tab("Pilotage chantier");

		pilotageTab.setGraphic(new ImageView(tabPilotageImg));

		// devis tab
		Tab devisTab = new DevisTab().getDevisTab(chantier);

		// planning tab
		Tab suiviAchatTab = new SuiviAchat().getSuiviAchatTab(chantier);

		// gestion stock tab
		Tab gestionStockTab = new GestionStockTab().getGestionStockTab(chantier);

		// SUIVI tab
		Tab suiviJournalierTab = getSuiviJournalierTab(chantier);
		// planning tab
		Tab planningTab = getPlanningTab(chantier);
		// Plans
		Tab plansTab = getPlansTab(chantier);

		TabPane pilotageTabPane = new TabPane();

		pilotageTabPane.getTabs().add(devisTab);
		pilotageTabPane.getTabs().add(suiviAchatTab);
		pilotageTabPane.getTabs().add(gestionStockTab);
		pilotageTabPane.getTabs().add(suiviJournalierTab);
		pilotageTabPane.getTabs().add(planningTab);
		pilotageTabPane.getTabs().add(plansTab);

		pilotageTab.setContent(pilotageTabPane);
		return pilotageTab;
	}

	/**
	 * Planning
	 * 
	 * @param chantier
	 * @return
	 */
	private Tab getPlanningTab(Chantier chantier) {

		Image tabPlanningImg = imageUtils.getTabPlanningImg();
		Tab planningTab = new Tab("Planning");

		planningTab.setGraphic(new ImageView(tabPlanningImg));
		return planningTab;
	}

	/**
	 * Planning
	 * 
	 * @param chantier
	 * @return
	 */
	private Tab getPlansTab(Chantier chantier) {
		Image plansImg = new Image(getClass().getResourceAsStream("/images/plan.png"));
		// Image tabPlanningImg = imageUtils.getTabPlanningImg();
		Tab plansTab = new Tab("Plans");

		plansTab.setGraphic(new ImageView(plansImg));
		return plansTab;
	}

	/**
	 * 
	 * @param chantier2
	 * @return
	 */
	private Tab getSuiviJournalierTab(Chantier chantier) {

		// LES IMAGES
		Image saveImg = imageUtils.getSaveImg();
		Image editImg = imageUtils.getEditImg();

		Image addImg = imageUtils.getAddImg();
		Image consultImg = imageUtils.getConsultImg();
		Image printImg = imageUtils.getPrintImg();
		Image suiviTabImg = imageUtils.getSuiviTabImg();

		Tab suiviTab = new Tab("Suivi Journalier");

		suiviTab.setGraphic(new ImageView(suiviTabImg));

		BorderPane suiviJourContent = new BorderPane();
		Node panHead = commonUtils.getHeader("Suivi quotidien des travaux");
		BorderPane suiviJourContentTmp = new BorderPane();
		suiviJourContentTmp.setPadding(new Insets(0, 10, 10, 10));

		Label selectedPhaseLabel = new Label("Veuillez séléctionnez la phase :");

		GridPane contentSuiviPane = getCommonUtils().getGridPanel();
		ComboBox<String> listePhasesSuiviJour = new ComboBox<String>();
		listePhasesSuiviJour.getItems().add("");
		for (PhaseConstruction phase : chantier.getPhasesConstruction()) {
			listePhasesSuiviJour.getItems().add(phase.getNom());
		}

		contentSuiviPane.add(selectedPhaseLabel, 0, 0);
		contentSuiviPane.add(listePhasesSuiviJour, 1, 0);

		suiviJourTable = new SuiviJournalierTable().getTableauCompteRenduJournalier();
		suiviJourTable.setEditable(true);

		suiviJourTableData = FXCollections.observableArrayList(new ArrayList<CompteRenduJournalier>());

		suiviJourTable.setItems(suiviJourTableData);

		final GridPane buttonsBar = getCommonUtils().getGridPanel();

		Button modifier = new Button("Modifier");
		modifier.setGraphic(new ImageView(editImg));

		Button enregistrer = new Button("Enregistrer");
		enregistrer.setGraphic(new ImageView(saveImg));

		Button ajouter = new Button("Ajouter pilo");
		ajouter.setGraphic(new ImageView(addImg));

		Button resumeAchat = new Button("Consulter resumé");
		resumeAchat.setGraphic(new ImageView(consultImg));
		Button imprimer = new Button("Imprimer", new ImageView(printImg));

		buttonsBar.add(modifier, 2, 2);
		buttonsBar.add(enregistrer, 4, 2);
		buttonsBar.add(ajouter, 6, 2);
		buttonsBar.add(resumeAchat, 8, 2);
		buttonsBar.add(imprimer, 10, 2);
		buttonsBar.setPadding(new Insets(10));

		final HBox centerPane = new HBox();
		centerPane.setPrefSize(1000, 400);
		/* devisContent.setPrefSize(1000,400); */
		// leftPane.setPrefSize(1200,400);
		centerPane.setPadding(new Insets(10, 0, 0, 10));

		centerPane.getChildren().add(suiviJourTable);
		enregistrer.setOnAction(BatisUtils.saveChantier(chantier));

		suiviJourContent.setTop(contentSuiviPane);
		suiviJourContent.setCenter(centerPane);

		suiviJourContentTmp.setTop(panHead);
		suiviJourContentTmp.setCenter(suiviJourContent);
		suiviJourContentTmp.setBottom(buttonsBar);

		suiviTab.setContent(suiviJourContentTmp);

		ajouter.setOnAction(BatisUtils.validatateIfPhaseIsSelected(listePhasesSuiviJour, " la phase "));
		listePhasesSuiviJour.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				PhaseConstruction selectedPhase = BatisUtils.getPhaseByName(chantier, newValue);
				suiviJourTableData.clear();
				suiviJourTableData.addAll(selectedPhase.getCompteRenduJournalier());
				ajouter.setOnAction(openAjoutSuiviJourWindow(suiviJourTableData, selectedPhase));
			}
		});
		return suiviTab;
	}

	/**
	 * 
	 * @param suiviTableData
	 * @param slsctedPhase
	 * @return
	 */
	public EventHandler<ActionEvent> openAjoutSuiviJourWindow(ObservableList<CompteRenduJournalier> suiviTableData,
			PhaseConstruction slsctedPhase) {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				new SuiviJourWindow(suiviTableData, slsctedPhase);

			}
		};
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
	 * @return the suiviJourTableData
	 */
	public ObservableList<CompteRenduJournalier> getSuiviJourTableData() {
		return suiviJourTableData;
	}

	/**
	 * @param suiviJourTableData the suiviJourTableData to set
	 */
	public void setSuiviJourTableData(ObservableList<CompteRenduJournalier> suiviJourTableData) {
		this.suiviJourTableData = suiviJourTableData;
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

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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.java.fr.batis.components.SuiviAchatWindow;
import main.java.fr.batis.components.common.CommonUtils;
import main.java.fr.batis.components.common.BatisUtils;
import main.java.fr.batis.components.tables.MaterielTable;
import main.java.fr.batis.entites.Chantier;
import main.java.fr.batis.entites.Devis;
import main.java.fr.batis.entites.Materiel;
import main.java.fr.batis.entites.PhaseConstruction;
import main.java.fr.batis.service.DevisService;

public class SuiviAchat {

	private CommonUtils commonUtils;
	private ComboBox<String> listePhasesForSuiviAchat;
	private TableView<Materiel> materielTable;
	private ObservableList<Materiel> materielTableData;
	private ComboBox<String> listeDevisForPhase;
	private DevisService devisService;
	private Devis selectedDevis;
	private PhaseConstruction selectedPhase;

	public SuiviAchat() {
		commonUtils = new CommonUtils();
		devisService = new DevisService();
	}

	/**
	 * 
	 * @param chantier
	 * @return
	 */
	public Tab getSuiviAchatTab(Chantier chantier) {
		Tab suiviAchatTab = new Tab("Suivi des Achats");
		Image tabPlanningImg = new Image(getClass().getResourceAsStream("/images/stock.png"));
		suiviAchatTab.setGraphic(new ImageView(tabPlanningImg));
		BorderPane suiviAchatContent = new BorderPane();
		Node panHead = commonUtils.getHeader("Liste des matériaux achetés");
		BorderPane suiviAchatContentTop = new BorderPane();
		suiviAchatContentTop.setPadding(new Insets(0, 10, 10, 10));

		Label selectedPhaseLabel = new Label("Veuillez séléctionnez la phase :");
		Label selectedDevisLabel = new Label("Veuillez séléctionnez le devis :");
		listeDevisForPhase = new ComboBox<String>();

		GridPane contentMatPane = getCommonUtils().getGridPanel();
		listePhasesForSuiviAchat = new ComboBox<String>();
		listePhasesForSuiviAchat.getItems().add("");

		for (PhaseConstruction phase : chantier.getPhasesConstruction()) {
			listePhasesForSuiviAchat.getItems().add(phase.getNom());
		}

		contentMatPane.add(selectedPhaseLabel, 0, 0);
		contentMatPane.add(listePhasesForSuiviAchat, 1, 0);

		contentMatPane.add(selectedDevisLabel, 4, 0);
		contentMatPane.add(listeDevisForPhase, 5, 0);

		suiviAchatContentTop.setTop(panHead);
		suiviAchatContentTop.setCenter(contentMatPane);
		suiviAchatContent.setTop(suiviAchatContentTop);

		materielTable = new MaterielTable().getTableauMateriel();
		// materielTable.setPrefSize(1200, 400);
		materielTable.setEditable(true);

		materielTableData = FXCollections.observableArrayList(new ArrayList<Materiel>());

		materielTable.setEditable(true);
		materielTable.setItems(materielTableData);
		final HBox leftPane = new HBox();
		leftPane.setPadding(new Insets(10, 0, 0, 10));

		leftPane.getChildren().add(materielTable);
		suiviAchatContent.setCenter(leftPane);

		final GridPane buttonsBar = getCommonUtils().getGridPanel();

		Image editImg = new Image(getClass().getResourceAsStream("/images/edit.png"));
		Image saveImg = new Image(getClass().getResourceAsStream("/images/save.png"));
		Image addImg = new Image(getClass().getResourceAsStream("/images/plus.png"));
		Image consultImg = new Image(getClass().getResourceAsStream("/images/consult.png"));
		Button modifier = new Button("Modifier");
		modifier.setGraphic(new ImageView(editImg));

		Button enregistrer = new Button("Enregistrer");
		enregistrer.setGraphic(new ImageView(saveImg));

		Button ajouter = new Button("Ajouter un matériel");
		ajouter.setGraphic(new ImageView(addImg));

		Button resumeAchat = new Button("Consulter resumé");
		resumeAchat.setGraphic(new ImageView(consultImg));

		buttonsBar.add(modifier, 2, 2);
		buttonsBar.add(enregistrer, 4, 2);
		buttonsBar.add(ajouter, 6, 2);
		buttonsBar.add(resumeAchat, 8, 2);

		suiviAchatContent.setBottom(buttonsBar);
		suiviAchatTab.setContent(suiviAchatContent);

		ajouter.setOnAction(validatateIfPhaseIsSelected());

		listePhasesForSuiviAchat.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				PhaseConstruction selectedPhase = BatisUtils.getPhaseByName(chantier, newValue);
				listeDevisForPhase.getItems().clear();
				listeDevisForPhase.getItems().add("");

				if (selectedPhase != null) {
					setSelectedPhase(selectedPhase);
					for (Devis devis : selectedPhase.getListDevis()) {
						listeDevisForPhase.getItems().add(devis.getNom());
					}
				}
			}
		});

		listeDevisForPhase.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				Devis devis = getDevisService().getDevisByNameAndPhase(
						listeDevisForPhase.getSelectionModel().getSelectedItem(), getSelectedPhase());

				materielTableData.clear();
				if (devis != null) {
					materielTableData.addAll(BatisUtils.getListeMaterielAchete(devis));
					ajouter.setOnAction(openAjoutSuiviAchatWindow(materielTableData, devis));
					setSelectedDevis(devis);
				}

			}
		});

		enregistrer.setOnAction(BatisUtils.saveListMateriel(materielTableData));
		return suiviAchatTab;
	}

	/**
	 * 
	 * @param liste
	 * @param type
	 * @return
	 */
	public EventHandler<ActionEvent> validatateIfPhaseIsSelected() {
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (listePhasesForSuiviAchat.getSelectionModel().getSelectedItem() == null) {
					BatisUtils.showAlert(Alert.AlertType.ERROR, null, "Erreur!", "Veuillez séléctionner la phase !");
					listePhasesForSuiviAchat.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID,
							new CornerRadii(1.0), BorderStroke.THIN)));

					return;

				} else if (listeDevisForPhase.getSelectionModel().getSelectedItem() == null) {
					BatisUtils.showAlert(Alert.AlertType.ERROR, null, "Erreur!", "Veuillez séléctionner le devis !");
					listeDevisForPhase.setBorder(Border.EMPTY);
					listeDevisForPhase.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID,
							new CornerRadii(1.0), BorderStroke.THIN)));
					return;
				} else {
					listePhasesForSuiviAchat.setBorder(Border.EMPTY);
					listeDevisForPhase.setBorder(Border.EMPTY);
				}
			}
		};
	}

	/**
	 * 
	 * @param materielTableData2
	 * @param selectedPhase
	 * @return
	 */
	protected EventHandler<ActionEvent> openAjoutSuiviAchatWindow(ObservableList<Materiel> materielTableData2,
			Devis selectedDevis) {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				SuiviAchatWindow suiviAchat = new SuiviAchatWindow(materielTableData2, selectedDevis);
				try {
					suiviAchat.initComponent(new Stage(), materielTableData2);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
	 * @return the listePhasesForSuiviAchat
	 */
	public ComboBox<String> getListePhasesForSuiviAchat() {
		return listePhasesForSuiviAchat;
	}

	/**
	 * @param listePhasesForSuiviAchat the listePhasesForSuiviAchat to set
	 */
	public void setListePhasesForSuiviAchat(ComboBox<String> listePhasesForSuiviAchat) {
		this.listePhasesForSuiviAchat = listePhasesForSuiviAchat;
	}

	/**
	 * @return the materielTable
	 */
	public TableView<Materiel> getMaterielTable() {
		return materielTable;
	}

	/**
	 * @param materielTable the materielTable to set
	 */
	public void setMaterielTable(TableView<Materiel> materielTable) {
		this.materielTable = materielTable;
	}

	/**
	 * @return the materielTableData
	 */
	public ObservableList<Materiel> getMaterielTableData() {
		return materielTableData;
	}

	/**
	 * @param materielTableData the materielTableData to set
	 */
	public void setMaterielTableData(ObservableList<Materiel> materielTableData) {
		this.materielTableData = materielTableData;
	}

	/**
	 * @return the listeDevisForPhase
	 */
	public ComboBox<String> getListeDevisForPhase() {
		return listeDevisForPhase;
	}

	/**
	 * @param listeDevisForPhase the listeDevisForPhase to set
	 */
	public void setListeDevisForPhase(ComboBox<String> listeDevisForPhase) {
		this.listeDevisForPhase = listeDevisForPhase;
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
	 * @return the selectedPhase
	 */
	public PhaseConstruction getSelectedPhase() {
		return selectedPhase;
	}

	/**
	 * @param selectedPhase the selectedPhase to set
	 */
	public void setSelectedPhase(PhaseConstruction selectedPhase) {
		this.selectedPhase = selectedPhase;
	}

}

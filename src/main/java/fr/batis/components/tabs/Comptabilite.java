package main.java.fr.batis.components.tabs;

import java.util.ArrayList;

import org.controlsfx.tools.Borders;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import main.java.fr.batis.components.EncaissementDecaissementWindow;
import main.java.fr.batis.components.common.CommonUtils;
import main.java.fr.batis.components.common.BatisUtils;
import main.java.fr.batis.components.tables.RetraitDepotFondTable;
import main.java.fr.batis.entites.Chantier;
import main.java.fr.batis.entites.Employe;
import main.java.fr.batis.entites.RetraitDepotFond;
import main.java.fr.batis.service.RetraitDepotService;

public class Comptabilite {
	private CommonUtils commonUtils;
	private TableView<RetraitDepotFond> retraitDepotFondTable;
	private ObservableList<RetraitDepotFond> retraitDepotFondTableData;
	private RetraitDepotService retraitDepotService;

	public Comptabilite() {
		commonUtils = new CommonUtils();
		retraitDepotService = new RetraitDepotService();
	}

	/**
	 * 
	 * @param chantier
	 * @return
	 */
	public Tab getComptabiliteTab(Chantier chantier) {
		Tab comptabiliteTab = new Tab("Comptabilité");
		Image tabComptaImg = new Image(getClass().getResourceAsStream("/images/compta.png"));
		GridPane gridComptabilite = getCommonUtils().getGridPanel();
		comptabiliteTab.setGraphic(new ImageView(tabComptaImg));
		comptabiliteTab.setContent(gridComptabilite);

		BorderPane comptabiliteContent = new BorderPane();
		Node panHead = commonUtils.getHeader("Gestion des fonds");

		retraitDepotFondTable = new RetraitDepotFondTable().getTableauRetrait();
		retraitDepotFondTable.setEditable(true);

		ArrayList<RetraitDepotFond> listeOperation = new ArrayList<RetraitDepotFond>();
		listeOperation.addAll(retraitDepotService.findByIdChantier(chantier));
		retraitDepotFondTableData = FXCollections.observableArrayList(listeOperation);

		retraitDepotFondTable.setEditable(true);

		retraitDepotFondTable.setItems(retraitDepotFondTableData);

		// TODO

		final GridPane buttonsBar = getCommonUtils().getGridPanel();

		Image editImg = new Image(getClass().getResourceAsStream("/images/edit.png"));
		Image saveImg = new Image(getClass().getResourceAsStream("/images/save.png"));
		Image addImg = new Image(getClass().getResourceAsStream("/images/plus.png"));
		Image consultImg = new Image(getClass().getResourceAsStream("/images/consult.png"));
		Button modifier = new Button("Modifier");
		modifier.setGraphic(new ImageView(editImg));

		Button enregistrer = new Button("Enregistrer");
		enregistrer.setGraphic(new ImageView(saveImg));

		// mainu cxt
		ContextMenu contextOperationMenu = new ContextMenu();
		MenuItem retrait = new MenuItem("Retrait d'argent");
		MenuItem depot = new MenuItem("Dépôt d'argent");
		// MenuItem mainOeuvre = new MenuItem("Payer");
		contextOperationMenu.getItems().addAll(retrait, new SeparatorMenuItem(), depot);

		Button ajouter = new Button("Faire une opération");
		ajouter.setGraphic(new ImageView(addImg));

		Button resumeAchat = new Button("Synthèse");
		resumeAchat.setGraphic(new ImageView(consultImg));

		buttonsBar.add(modifier, 2, 2);
		buttonsBar.add(enregistrer, 4, 2);
		buttonsBar.add(ajouter, 6, 2);
		buttonsBar.add(resumeAchat, 8, 2);

		final BorderPane tablePane = new BorderPane();
		tablePane.setPrefWidth(940);

		// leftPane.setPrefSize(1200,400);
		tablePane.setPadding(new Insets(10, 0, 0, 10));
		retraitDepotFondTable.setPrefWidth(940);

		tablePane.setLeft(retraitDepotFondTable);
		VBox resume = new VBox();

		Label sommeTotDeposeLabel = new Label("Somme Totale déposé :");
		Label sommeTotalField = new Label("0");

		Label depenseTolLabel = new Label("Depenses totales :");
		Label depenseTolField = new Label("0");

		Label depenseMatLabel = new Label("Depense en materiaux :");
		Label depenseMatField = new Label("0");

		Label depenseSalLabel = new Label("Depense en salaires :");
		Label depenseSalField = new Label("0");

		Label fondDispoLabel = new Label("Somme disponible :");
		Label fondDispoField = new Label("0");

		GridPane resumeContenTmp = commonUtils.getGridPanel();

		resumeContenTmp.add(sommeTotDeposeLabel, 0, 0);
		resumeContenTmp.add(sommeTotalField, 1, 0);

		resumeContenTmp.add(depenseTolLabel, 0, 1);
		resumeContenTmp.add(depenseTolField, 1, 1);

		resumeContenTmp.add(depenseMatLabel, 0, 2);
		resumeContenTmp.add(depenseMatField, 1, 2);
		resumeContenTmp.add(depenseSalLabel, 0, 3);
		resumeContenTmp.add(depenseSalField, 1, 3);
		resumeContenTmp.add(fondDispoLabel, 0, 4);
		resumeContenTmp.add(fondDispoField, 1, 4);

		resume.getChildren().add(resumeContenTmp);

		resume.setPrefSize(300, 400);

		Node wraperResume = Borders.wrap(resume)

				.lineBorder().color(Color.rgb(168, 211, 255)).outerPadding(0, 10, 10, 10).title("Résume").buildAll();

		tablePane.setRight(wraperResume);
		enregistrer.setOnAction(saveDepotRetrait(retraitDepotFondTable));

		comptabiliteContent.setTop(panHead);
		comptabiliteContent.setCenter(tablePane);
		comptabiliteContent.setBottom(buttonsBar);

		comptabiliteTab.setContent(comptabiliteContent);

		ajouter.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

			@Override
			public void handle(ContextMenuEvent event) {

				contextOperationMenu.show(ajouter, event.getScreenX(), event.getScreenY());
			}
		});
		ajouter.setOnAction(openRetraitDepotFondWindow(chantier));
		return comptabiliteTab;
	}

	public static EventHandler<ActionEvent> saveDepotRetrait(TableView<RetraitDepotFond> retraitDepotFondTable2) {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				RetraitDepotService retraitDepotService = new RetraitDepotService();
				// Chantier chantier =
				// chantierService.findById(ChantierWindow.chantier.getId());
				// chantierService.saveOrUpdate(chantier);
				ObservableList<RetraitDepotFond> liste = retraitDepotFondTable2.getItems();
				for (RetraitDepotFond item : liste) {
					retraitDepotService.saveOrUpdate(item);
				}

			}
		};

	}

	/**
	 * 
	 * @param chantier
	 * @return
	 */
	public EventHandler<ActionEvent> openRetraitDepotFondWindow(Chantier chantier) {
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Employe comptable = BatisUtils.getChefComptable(chantier);
				if (comptable == null) {
					BatisUtils.showAlert(Alert.AlertType.ERROR, null, "Erreur!",
							"Vous n'avez pas le droit de faire cette opération");
				} else {
					new EncaissementDecaissementWindow(chantier, retraitDepotFondTableData);
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
	 * @return the retraitDepotFondTable
	 */
	public TableView<RetraitDepotFond> getRetraitDepotFondTable() {
		return retraitDepotFondTable;
	}

	/**
	 * @param retraitDepotFondTable the retraitDepotFondTable to set
	 */
	public void setRetraitDepotFondTable(TableView<RetraitDepotFond> retraitDepotFondTable) {
		this.retraitDepotFondTable = retraitDepotFondTable;
	}

	/**
	 * @return the retraitDepotFondTableData
	 */
	public ObservableList<RetraitDepotFond> getRetraitDepotFondTableData() {
		return retraitDepotFondTableData;
	}

	/**
	 * @param retraitDepotFondTableData the retraitDepotFondTableData to set
	 */
	public void setRetraitDepotFondTableData(ObservableList<RetraitDepotFond> retraitDepotFondTableData) {
		this.retraitDepotFondTableData = retraitDepotFondTableData;
	}

	/**
	 * @return the retraitDepotService
	 */
	public RetraitDepotService getRetraitDepotService() {
		return retraitDepotService;
	}

	/**
	 * @param retraitDepotService the retraitDepotService to set
	 */
	public void setRetraitDepotService(RetraitDepotService retraitDepotService) {
		this.retraitDepotService = retraitDepotService;
	}

}

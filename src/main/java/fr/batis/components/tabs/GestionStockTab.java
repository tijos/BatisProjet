/**
 * 
 */
package main.java.fr.batis.components.tabs;

import java.util.ArrayList;
import java.util.List;

import org.controlsfx.tools.Borders;

import main.java.fr.batis.components.StockageDestockageWindow;
import main.java.fr.batis.components.common.CommonUtils;
import main.java.fr.batis.components.common.ImageUtils;
import main.java.fr.batis.components.common.BatisUtils;
import main.java.fr.batis.components.tables.InventaireTable;
import main.java.fr.batis.components.tables.StockageDestockageTable;
import main.java.fr.batis.entites.Chantier;
import main.java.fr.batis.entites.Inventaire;
import main.java.fr.batis.entites.StockageDestockage;
import main.java.fr.batis.service.InventaireService;
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
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


/**
 * @author admin
 *
 */
public class GestionStockTab {

	private CommonUtils commonUtils;
    private ImageUtils imageUtils;
	private TableView<StockageDestockage> stockageDestockageTable;
	private ObservableList<StockageDestockage> stockageDestockageData;
	private TableView<Inventaire> inventaireTable;
	private ObservableList<Inventaire> inventaireTableData;
	private InventaireService inventaireService;
	private boolean haveLoaded;
	
	/**
	 * 
	 */
	public GestionStockTab() {
		this.commonUtils = new CommonUtils();
		this.imageUtils = new ImageUtils();
		this.inventaireService = new InventaireService();
		this.haveLoaded = false; 
	}

	/**
	 * 
	 * @param chantier2
	 * @return
	 */
	public Tab getGestionStockTab(Chantier chantier) {
		
		Image saveImg = imageUtils.getSaveImg();
		Image editImg = imageUtils.getEditImg();
		Image addImg = imageUtils.getAddImg();
		Image consultImg = imageUtils.getConsultImg();
		Image gestStockImg = imageUtils.getGestStockImg();
	

		Tab gestionStockTab = new Tab("Gestion du stock");
	
		gestionStockTab.setGraphic(new ImageView(gestStockImg));
		BorderPane gestionStockContent = new BorderPane();
		Node panHead = commonUtils.getHeader("Gestion du stock");
		BorderPane gestionStockContentTop = new BorderPane();
		gestionStockContentTop.setPadding(new Insets(10,10,10,10));

		ComboBox<String> tyeOperationCombox = new ComboBox<String>();
		tyeOperationCombox.getItems().add("");
		tyeOperationCombox.getItems().add("Stockage");
		tyeOperationCombox.getItems().add("Destockage");
		tyeOperationCombox.getItems().add("Inventaire");
		tyeOperationCombox.setPadding(new Insets(0, 0, 0, 10));

		stockageDestockageTable = new StockageDestockageTable().getStockageDestockageTable();
		//devisTable.setPrefSize(1200, 400);
		stockageDestockageTable.setEditable(true);
		
		stockageDestockageData = FXCollections.observableArrayList(
				new ArrayList<StockageDestockage>()
				);

		stockageDestockageTable.setItems(stockageDestockageData);
		stockageDestockageTable.setPrefSize(1000, 400);

		inventaireTable =  new InventaireTable().getInventaireeTable();
		inventaireTable.setEditable(true);
		
		/*Inventaire inventaire = new Inventaire();
		inventaire.setDateInventaire(new Date());
		inventaire.setNomMateriel("nomType");
		inventaire.setQualite("_qualite");
		inventaire.setStatut("_statut");
		inventaire.setUniteMesure("_unite");
		inventaire.setQuantite(new Double(25));
		inventaire.setMotif("_motif");
		
		StockageDestockage stockDestock = new StockageDestockage() ;
		stockDestock.setDate(new Date());
		stockDestock.setTypeOperation("Destockage");
		stockDestock.setNomMateriel("nomType");
		stockDestock.setQualite("_qualite");
		stockDestock.setStatut("_statut");
		stockDestock.setUniteMesure("_unite");
		stockDestock.setQuantite(new Double(45));
		
		stockDestock.setMotif("_motif");
		stockDestock.setIdChantier(chantier.getId());*/
		
		
		inventaireTableData = FXCollections.observableArrayList(
				new ArrayList<Inventaire>()
			);
		
	/*	inventaireTableData.add(inventaire);
		stockageDestockageData.add(stockDestock); */
		inventaireTable.setItems(inventaireTableData);
		inventaireTable.setPrefSize(1000, 400);
		
		
		final HBox centerPane = new HBox();		
		centerPane.setPadding(new Insets(10, 0, 0, 0));
		
		

		//TODO

		final GridPane buttonsBar =  getCommonUtils().getGridPanel();
		
		Button modifier = new Button("Modifier");
		modifier.setGraphic(new ImageView(editImg));

		Button enregistrer = new Button("Enregistrer");
		enregistrer.setGraphic(new ImageView(saveImg));

		Button ajouter = new Button("Ajouter");
		ajouter.setGraphic(new ImageView(addImg));

		Button resumeAchat = new Button("Consulter resumé");
		resumeAchat.setGraphic(new ImageView(consultImg));

		buttonsBar.add(modifier, 0, 2);
		buttonsBar.add(enregistrer, 2, 2);
		buttonsBar.add(ajouter, 4, 2);	
		buttonsBar.add(resumeAchat, 6, 2);

		gestionStockContentTop.setTop(tyeOperationCombox);
		gestionStockContentTop.setCenter(centerPane);

		gestionStockContent.setTop(panHead);
		gestionStockContent.setCenter(gestionStockContentTop);
		gestionStockContent.setBottom(buttonsBar);
		
		VBox resume = new VBox();
		//TODO
	
		//Inventaire hd = service.getinventaireByName(chantier.getId());
		Label sommeTotDeposeLabel = new Label("Somme Totale déposé :");
		Label  sommeTotalField    = new Label("0");
		
		Label depenseTolLabel = new Label("Depenses totales :");
		Label depenseTolField    = new Label("0");
		
		Label depenseMatLabel = new Label("Depense en materiaux :");
		Label depenseMatField = new Label("0");
		
		Label depenseSalLabel = new Label("Depense en salaires :");
		Label depenseSalField = new Label("0");
		
		GridPane resumeContenTmp = commonUtils.getGridPanel();
		
		resumeContenTmp.add(sommeTotDeposeLabel, 0, 0);
		resumeContenTmp.add(sommeTotalField, 1, 0);
		
		resumeContenTmp.add(depenseTolLabel, 0, 1);
		resumeContenTmp.add(depenseTolField, 1, 1);
		
		resumeContenTmp.add(depenseMatLabel, 0, 2);
		resumeContenTmp.add(depenseMatField, 1, 2);
		resumeContenTmp.add(depenseSalLabel, 0, 3);
		resumeContenTmp.add(depenseSalField, 1, 3);
		
		resume.getChildren().add(resumeContenTmp);
		
		resume.setPrefSize(250,400);
	
		Node wraperResume = Borders.wrap(resume)

				.lineBorder()
				.color(Color.rgb(168, 211, 255))
				.outerPadding(10, 10, 10, 10)
				.title("Inventaire")
				.buildAll();

		gestionStockContent.setRight(wraperResume);
		
		gestionStockTab.setContent(gestionStockContent);
		ajouter.setOnAction(BatisUtils.validatateIfPhaseIsSelected(tyeOperationCombox," la phase "));

		tyeOperationCombox.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				
				centerPane.getChildren().clear();
				if("Stockage".equals(newValue)) {
					ajouter.setOnAction(openStockageDestockageWindow(stockageDestockageData,inventaireTableData,chantier,true,false,false));	
					centerPane.getChildren().add(stockageDestockageTable);
				}
				if("Destockage".equals(newValue)) {
					ajouter.setOnAction(openStockageDestockageWindow(stockageDestockageData,inventaireTableData,chantier,false,true,false));	
					centerPane.getChildren().add(stockageDestockageTable);
				}
				
				if("Inventaire".equals(newValue)) {
					ajouter.setOnAction(openStockageDestockageWindow(stockageDestockageData,inventaireTableData,chantier,false,false,true));	
					
					if(!haveLoaded) {
						List<Inventaire> inventaires = getInventaireService().findTenFirst();
						inventaireTableData.addAll(inventaires);
					}
					setHaveLoaded(true);
					centerPane.getChildren().add(inventaireTable);
				}
			}
		}); 
		 
		enregistrer.setOnAction(saveInventaire(inventaireTable));
	
		return gestionStockTab;
	}


	public static EventHandler<ActionEvent> saveInventaire(TableView<Inventaire> inventaire) {
		
		return new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {	
				InventaireService inventaireService = new InventaireService();
				ObservableList<Inventaire> liste = inventaire.getItems();
				for(Inventaire item : liste) {
					inventaireService.saveOrUpdate(item);
				}
				
			}
		};

	}
	
	/**
	 * 
	 * @param stockageDestockageData2
	 * @param inventaireDataTable
	 * @param chantier
	 * @param stackage
	 * @param destockage
	 * @param inventaire
	 * @return
	 */
	public EventHandler<ActionEvent> openStockageDestockageWindow(ObservableList<StockageDestockage> stockageDestockageData2,ObservableList<Inventaire> inventaireDataTable,
			Chantier chantier, boolean stackage, boolean destockage, boolean inventaire) {

		return new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {

				new StockageDestockageWindow(stockageDestockageData2,inventaireDataTable,chantier,stackage,destockage,inventaire);

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
	 * @return the stockageDestockageTable
	 */
	public TableView<StockageDestockage> getStockageDestockageTable() {
		return stockageDestockageTable;
	}

	/**
	 * @param stockageDestockageTable the stockageDestockageTable to set
	 */
	public void setStockageDestockageTable(TableView<StockageDestockage> stockageDestockageTable) {
		this.stockageDestockageTable = stockageDestockageTable;
	}

	/**
	 * @return the stockageDestockageData
	 */
	public ObservableList<StockageDestockage> getStockageDestockageData() {
		return stockageDestockageData;
	}

	/**
	 * @param stockageDestockageData the stockageDestockageData to set
	 */
	public void setStockageDestockageData(ObservableList<StockageDestockage> stockageDestockageData) {
		this.stockageDestockageData = stockageDestockageData;
	}

	/**
	 * @return the inventaireTable
	 */
	public TableView<Inventaire> getInventaireTable() {
		return inventaireTable;
	}

	/**
	 * @param inventaireTable the inventaireTable to set
	 */
	public void setInventaireTable(TableView<Inventaire> inventaireTable) {
		this.inventaireTable = inventaireTable;
	}

	/**
	 * @return the inventaireTableData
	 */
	public ObservableList<Inventaire> getInventaireTableData() {
		return inventaireTableData;
	}

	/**
	 * @param inventaireTableData the inventaireTableData to set
	 */
	public void setInventaireTableData(ObservableList<Inventaire> inventaireTableData) {
		this.inventaireTableData = inventaireTableData;
	}

	/**
	 * @return the inventaireService
	 */
	public InventaireService getInventaireService() {
		return inventaireService;
	}

	/**
	 * @param inventaireService the inventaireService to set
	 */
	public void setInventaireService(InventaireService inventaireService) {
		this.inventaireService = inventaireService;
	}


	/**
	 * @return the haveLoaded
	 */
	public boolean isHaveLoaded() {
		return haveLoaded;
	}

	/**
	 * @param haveLoaded the haveLoaded to set
	 */
	public void setHaveLoaded(boolean haveLoaded) {
		this.haveLoaded = haveLoaded;
	}
			
}

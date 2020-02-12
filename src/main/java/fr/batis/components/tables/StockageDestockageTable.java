package main.java.fr.batis.components.tables;

import main.java.fr.batis.entites.StockageDestockage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;



public class StockageDestockageTable  extends TableView<StockageDestockage>  {

	TableView<StockageDestockage> stockageDestockageTable;
	
	public StockageDestockageTable() {
		super();
		stockageDestockageTable = new TableView<StockageDestockage>();
		stockageDestockageTable.setColumnResizePolicy(UNCONSTRAINED_RESIZE_POLICY);
		stockageDestockageTable.setEditable(true);
	}

	@SuppressWarnings("unchecked")
	public TableView<StockageDestockage> initTable() {
		stockageDestockageTable = new TableView<StockageDestockage>();
		
		TableColumn<StockageDestockage, String> dateCol = new TableColumn<StockageDestockage, String>("Date");
		dateCol.setPrefWidth(120);
		TableColumn<StockageDestockage, String> typeOpCol = new TableColumn<StockageDestockage, String>("Type Opération");
		typeOpCol.setPrefWidth(150);
		TableColumn<StockageDestockage, String> motifOpCol = new TableColumn<StockageDestockage, String>("Motif Opération");
		motifOpCol.setPrefWidth(150);
		TableColumn<StockageDestockage, String> nomCol = new TableColumn<StockageDestockage, String>("Nom");
		nomCol.setPrefWidth(150);
		TableColumn<StockageDestockage, String> cathegorieCol = new TableColumn<StockageDestockage, String>("Cathégorie");
		cathegorieCol.setPrefWidth(120);
		TableColumn<StockageDestockage, String> quantiteCol = new TableColumn<StockageDestockage, String>("Quantité");
		quantiteCol.setPrefWidth(120);
		TableColumn<StockageDestockage, String> uniteCol = new TableColumn<StockageDestockage, String>("Unité");
		uniteCol.setPrefWidth(80);
		TableColumn<StockageDestockage, String> statutCol = new TableColumn<StockageDestockage, String>("Statut");
		statutCol.setPrefWidth(100);
		
		dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
		typeOpCol.setCellValueFactory(new PropertyValueFactory<>("typeOperation"));
		
		motifOpCol.setCellValueFactory(new PropertyValueFactory<>("motif"));
		
		nomCol.setCellValueFactory(new PropertyValueFactory<>("nomMateriel"));	
		cathegorieCol.setCellValueFactory(new PropertyValueFactory<>("qualite"));	
	
		quantiteCol.setCellValueFactory(new PropertyValueFactory<>("quantite"));	
	
		uniteCol.setCellValueFactory(new PropertyValueFactory<>("uniteMesure"));
		statutCol.setCellValueFactory(new PropertyValueFactory<>("statutAchat"));
	
	
		dateCol.setSortType(TableColumn.SortType.DESCENDING);
		nomCol.setSortType(TableColumn.SortType.DESCENDING);
		stockageDestockageTable.getColumns().addAll(dateCol,typeOpCol, motifOpCol,nomCol, cathegorieCol, quantiteCol, uniteCol,statutCol);

		return stockageDestockageTable;
	}

	/**
	 * @return the stockageDestockageTable
	 */
	public TableView<StockageDestockage> getStockageDestockageTable() {
		return  initTable();
	}

	/**
	 * @param stockageDestockageTable the stockageDestockageTable to set
	 */
	public void setStockageDestockageTable(TableView<StockageDestockage> stockageDestockageTable) {
		this.stockageDestockageTable = stockageDestockageTable;
	}

	

}

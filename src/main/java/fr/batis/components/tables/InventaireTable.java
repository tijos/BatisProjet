package main.java.fr.batis.components.tables;

import java.text.SimpleDateFormat;
import java.util.Date;

import main.java.fr.batis.entites.Inventaire;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;



public class InventaireTable  extends TableView<Inventaire>  {

	TableView<Inventaire> inventaireeTable;
	//DateTimeFormatter myDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private SimpleDateFormat myDateFormatter = new SimpleDateFormat("dd/MM/yyyy");
	public InventaireTable() {
		super();
		inventaireeTable = new TableView<Inventaire>();
		inventaireeTable.setColumnResizePolicy(UNCONSTRAINED_RESIZE_POLICY);
		inventaireeTable.setEditable(true);
	}

	@SuppressWarnings("unchecked")
	public TableView<Inventaire> initTable() {
		inventaireeTable = new TableView<Inventaire>();
		
		TableColumn<Inventaire, Date> dateCol = new TableColumn<Inventaire, Date>("Date");
		dateCol.setPrefWidth(120);
		//TableColumn<Inventaire, String> typeOpCol = new TableColumn<Inventaire, String>("Type Opération");
		//typeOpCol.setPrefWidth(150);
		TableColumn<Inventaire, String> motifOpCol = new TableColumn<Inventaire, String>("Motif Opération");
		motifOpCol.setPrefWidth(150);
		TableColumn<Inventaire, String> nomCol = new TableColumn<Inventaire, String>("Nom");
		nomCol.setPrefWidth(150);
		TableColumn<Inventaire, String> cathegorieCol = new TableColumn<Inventaire, String>("Cathégorie");
		cathegorieCol.setPrefWidth(120);
		TableColumn<Inventaire, String> quantiteCol = new TableColumn<Inventaire, String>("Quantité");
		quantiteCol.setPrefWidth(120);
		TableColumn<Inventaire, String> uniteCol = new TableColumn<Inventaire, String>("Unité");
		uniteCol.setPrefWidth(80);
		TableColumn<Inventaire, String> statutCol = new TableColumn<Inventaire, String>("Statut");
		statutCol.setPrefWidth(100);
		
		dateCol.setCellFactory(column -> {
	        TableCell<Inventaire, Date> cell = new TableCell<Inventaire, Date>() {
	         
	            @Override
	            protected void updateItem(Date item, boolean empty) {
	                super.updateItem(item, empty);
	                if(empty || item == null ) {
	                    setText(null);
	                }
	                else {
	                    this.setText(myDateFormatter.format(item));

	                }
	            }
	        };

	        return cell;
	    });
		
		dateCol.setCellValueFactory(new PropertyValueFactory<>("dateInventaire"));
	//	typeOpCol.setCellValueFactory(new PropertyValueFactory<>("typeOperation"));
		
		motifOpCol.setCellValueFactory(new PropertyValueFactory<>("motif"));
		
		nomCol.setCellValueFactory(new PropertyValueFactory<>("nomMateriel"));	
		cathegorieCol.setCellValueFactory(new PropertyValueFactory<>("qualite"));	
	
		quantiteCol.setCellValueFactory(new PropertyValueFactory<>("quantite"));	
	
		uniteCol.setCellValueFactory(new PropertyValueFactory<>("uniteMesure"));
		statutCol.setCellValueFactory(new PropertyValueFactory<>("statut"));
	
	
		dateCol.setSortType(TableColumn.SortType.DESCENDING);
		nomCol.setSortType(TableColumn.SortType.DESCENDING);
		inventaireeTable.getColumns().addAll(dateCol,motifOpCol,nomCol, cathegorieCol, quantiteCol, uniteCol,statutCol);

		return inventaireeTable;
	}

	/**
	 * @return the inventaireeTable
	 */
	public TableView<Inventaire> getInventaireeTable() {
		return  initTable();
	}

	/**
	 * @param stockageDestockageTable the stockageDestockageTable to set
	 */
	public void setInventaireeTable(TableView<Inventaire> inventaireTable) {
		this.inventaireeTable = inventaireTable;
	}

	

}

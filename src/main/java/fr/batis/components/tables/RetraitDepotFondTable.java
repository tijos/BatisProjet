package main.java.fr.batis.components.tables;

import main.java.fr.batis.components.common.BatisUtils;
import main.java.fr.batis.entites.RetraitDepotFond;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class RetraitDepotFondTable extends TableView<RetraitDepotFond> {

	
	private TableView<RetraitDepotFond> tableauRetrait;
	
	public RetraitDepotFondTable() {
		super();
		initTable();
	}

	@SuppressWarnings("unchecked")
	private TableView<RetraitDepotFond> initTable() {


		// model = new DefaultTableModel(donnees, entetes);
		tableauRetrait = new TableView<RetraitDepotFond>();
		tableauRetrait.setColumnResizePolicy(UNCONSTRAINED_RESIZE_POLICY);
		tableauRetrait.setEditable(true);
		
		TableColumn<RetraitDepotFond, String> dateCol = new TableColumn<RetraitDepotFond, String>("Date");
		dateCol.setPrefWidth(100);
		TableColumn<RetraitDepotFond, String> beneficiaireCol = new TableColumn<RetraitDepotFond, String>("Bénéficiaire");
		beneficiaireCol.setPrefWidth(150);
		TableColumn<RetraitDepotFond, String> typeBeneficiqreCol = new TableColumn<RetraitDepotFond, String>("Type béneficiaire");
		typeBeneficiqreCol.setPrefWidth(120);
		TableColumn<RetraitDepotFond, String> deviseCol = new TableColumn<RetraitDepotFond, String>("Devise");
		deviseCol.setPrefWidth(60);
		TableColumn<RetraitDepotFond, Double> montantCol = new TableColumn<RetraitDepotFond, Double>("Montant");
		montantCol.setPrefWidth(120);
		TableColumn<RetraitDepotFond, String> motifCol = new TableColumn<RetraitDepotFond, String>("Motif");
		motifCol.setPrefWidth(120);
		TableColumn<RetraitDepotFond, String> gerantCol = new TableColumn<RetraitDepotFond, String>("Gérant");
		gerantCol.setPrefWidth(150);
		TableColumn<RetraitDepotFond, String> typeOperationCol = new TableColumn<RetraitDepotFond, String>("Type opération");
		typeOperationCol.setPrefWidth(110);
		
		dateCol.setCellValueFactory(new PropertyValueFactory<>("dateOperation"));
		beneficiaireCol.setCellValueFactory(new PropertyValueFactory<>("nomPrenomBeneficaire"));
		typeBeneficiqreCol.setCellValueFactory(new PropertyValueFactory<>("typeBeneficiaire"));
		deviseCol.setCellValueFactory(new PropertyValueFactory<>("devise"));
	
		motifCol.setCellValueFactory(new PropertyValueFactory<>("motif"));
		gerantCol.setCellValueFactory(new PropertyValueFactory<>("nomPrenomComptable"));
		typeOperationCol.setCellValueFactory(new PropertyValueFactory<>("type"));
		
		dateCol.setSortType(TableColumn.SortType.DESCENDING);
		
		montantCol.setCellFactory(column -> {
	        TableCell<RetraitDepotFond, Double> cell = new TableCell<RetraitDepotFond, Double>() {
	         
	            @Override
	            protected void updateItem(Double item, boolean empty) {
	                super.updateItem(item, empty);
	                if(empty || item == null ) {
	                    setText(null);
	                }
	                else {
	                    this.setText(BatisUtils.getNumberFormatter(item));

	                }
	            }
	        };
	        return cell;
	    });
	    montantCol.setCellValueFactory(new PropertyValueFactory<>("montant"));
		tableauRetrait.getColumns().addAll(dateCol,beneficiaireCol, typeBeneficiqreCol,deviseCol, montantCol, motifCol, gerantCol,typeOperationCol);
		return tableauRetrait;
	}

	/**
	 * @return the tableauRetrait
	 */
	public TableView<RetraitDepotFond> getTableauRetrait() {
		return tableauRetrait;
	}

	/**
	 * @param tableauRetrait the tableauRetrait to set
	 */
	public void setTableauRetrait(TableView<RetraitDepotFond> tableauRetrait) {
		this.tableauRetrait = tableauRetrait;
	}

	

}

package main.java.fr.batis.components.tables;

import main.java.fr.batis.entites.CompteRenduJournalier;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;



public class SuiviJournalierTable extends TableView<CompteRenduJournalier> {

	TableView<CompteRenduJournalier> tableauCompteRenduJournalier;


	public SuiviJournalierTable() {
		super();
		tableauCompteRenduJournalier = new TableView<CompteRenduJournalier>();
		tableauCompteRenduJournalier.setColumnResizePolicy(UNCONSTRAINED_RESIZE_POLICY);
		tableauCompteRenduJournalier.setEditable(true);
	}

	
	@SuppressWarnings("unchecked")
	public TableView<CompteRenduJournalier> initTable() {

		tableauCompteRenduJournalier = new TableView<CompteRenduJournalier>();
		tableauCompteRenduJournalier.setColumnResizePolicy(UNCONSTRAINED_RESIZE_POLICY);
		 Callback<TableColumn<CompteRenduJournalier, String>, TableCell<CompteRenduJournalier, String>> cellFactory =
	             new Callback<TableColumn<CompteRenduJournalier, String>, TableCell<CompteRenduJournalier, String>>() {
	                 @SuppressWarnings("rawtypes")
					public TableCell<CompteRenduJournalier, String> call(TableColumn<CompteRenduJournalier, String> p) {
	                    return new EditingCell();
	                 }
	     };

	     
	 	TableColumn<CompteRenduJournalier, String> dateCol = new TableColumn<CompteRenduJournalier, String>("Date");
	 	dateCol.setPrefWidth(150);
	 	
		TableColumn<CompteRenduJournalier, String> titreCol = new TableColumn<CompteRenduJournalier, String>("Titre");
		titreCol.setPrefWidth(150);
		
		titreCol.setCellFactory(cellFactory);
		titreCol.setOnEditCommit(
            new EventHandler<CellEditEvent<CompteRenduJournalier, String>>() {
                @Override
                public void handle(CellEditEvent<CompteRenduJournalier, String> t) {
                    ((CompteRenduJournalier) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setTitre(t.getNewValue());
                }
             }
        );
		
	
		TableColumn<CompteRenduJournalier, String> typeCol = new TableColumn<CompteRenduJournalier, String>("Type");
		typeCol.setPrefWidth(150);
		
		TableColumn<CompteRenduJournalier, String> descriptionCol = new TableColumn<CompteRenduJournalier, String>("Description");
		descriptionCol.setPrefWidth(250);
		
		
		
		dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
		titreCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
		
		typeCol.setCellValueFactory(new PropertyValueFactory<>("typeSuivi"));
		
		descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));	
		
	
		dateCol.setSortType(TableColumn.SortType.DESCENDING);

		tableauCompteRenduJournalier.getColumns().addAll(dateCol,titreCol,typeCol, descriptionCol);

		return tableauCompteRenduJournalier;

	}

	/**
	 * @return the tableauCompteRenduJournalier
	 */
	public TableView<CompteRenduJournalier> getTableauCompteRenduJournalier() {
		return initTable();
	}

	/**
	 * @param tableauCompteRenduJournalier the tableauCompteRenduJournalier to set
	 */
	public void setTableauCompteRenduJournalier(TableView<CompteRenduJournalier> tableauCompteRenduJournalier) {
		this.tableauCompteRenduJournalier = tableauCompteRenduJournalier;
	}

}

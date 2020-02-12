package main.java.fr.batis.components.tables;

import main.java.fr.batis.entites.Materiel;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;



public class MaterielTable extends TableView<Materiel> {

	TableView<Materiel> tableauMateriel;


	public MaterielTable() {
		super();
		tableauMateriel = new TableView<Materiel>();
		tableauMateriel.setColumnResizePolicy(UNCONSTRAINED_RESIZE_POLICY);
		tableauMateriel.setEditable(true);
	}

	
	@SuppressWarnings("unchecked")
	public TableView<Materiel> initTable() {

		tableauMateriel = new TableView<Materiel>();
		tableauMateriel.setColumnResizePolicy(UNCONSTRAINED_RESIZE_POLICY);
		 Callback<TableColumn<Materiel, String>, TableCell<Materiel, String>> cellFactory =
	             new Callback<TableColumn<Materiel, String>, TableCell<Materiel, String>>() {
	                 @SuppressWarnings("rawtypes")
					public TableCell<Materiel, String> call(TableColumn<Materiel, String> p) {
	                    return new EditingCell();
	                 }
	     };

		TableColumn<Materiel, String> nomCol = new TableColumn<Materiel, String>("Nom");
		nomCol.setPrefWidth(120);
		TableColumn<Materiel, String> diffPrixCol = new TableColumn<Materiel, String>("Diff Prix");
		diffPrixCol.setPrefWidth(100);
		
		nomCol.setCellFactory(cellFactory);
		
		Callback<TableColumn<Materiel, String>, TableCell<Materiel, String>> cellColorFactory =
	             new Callback<TableColumn<Materiel, String>, TableCell<Materiel, String>>() {
	                 @SuppressWarnings("rawtypes")
					public TableCell<Materiel, String> call(TableColumn<Materiel, String> p) {
	                    return new EditingColorCell();
	                 }
	     };

	     diffPrixCol.setCellFactory(cellColorFactory);
		
		nomCol.setOnEditCommit(
            new EventHandler<CellEditEvent<Materiel, String>>() {
                @Override
                public void handle(CellEditEvent<Materiel, String> t) {
                    ((Materiel) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setNom(t.getNewValue());
                }
             }
        );
		
		TableColumn<Materiel, String> qlitePrevuCol = new TableColumn<Materiel, String>("Qlité Prevu");
		qlitePrevuCol.setPrefWidth(100);
		TableColumn<Materiel, String> qliteReelCol = new TableColumn<Materiel, String>("Qlité Réel");
		qliteReelCol.setPrefWidth(100);
		TableColumn<Materiel, String> qtePrevuCol = new TableColumn<Materiel, String>("Qté Prevu");
		qtePrevuCol.setPrefWidth(100);
		TableColumn<Materiel, String> qteReelCol = new TableColumn<Materiel, String>("Qté Réel");
		qteReelCol.setPrefWidth(100);
		TableColumn<Materiel, String> uniteMesureCol = new TableColumn<Materiel, String>("Unité");
		uniteMesureCol.setPrefWidth(100);
		TableColumn<Materiel, String> prixUPrevuCol = new TableColumn<Materiel, String>("Prix U Prevu");
		prixUPrevuCol.setPrefWidth(100);
		TableColumn<Materiel, String> prixUReelCol = new TableColumn<Materiel, String>("Prix U Réel");	
		prixUReelCol.setPrefWidth(100);
		TableColumn<Materiel, String> prixTotPrevuCol = new TableColumn<Materiel, String>("Prix Tot Prevu");
		prixTotPrevuCol.setPrefWidth(100);
		TableColumn<Materiel, Double> prixTotReelCol = new TableColumn<Materiel, Double>("Prix Tot Réel");	
		prixTotReelCol.setPrefWidth(100);
		
		TableColumn<Materiel, Double> deviseCol = new TableColumn<Materiel, Double>("Devise");	
		deviseCol.setPrefWidth(80);
		
		TableColumn<Materiel, String> statutCol = new TableColumn<Materiel, String>("Statut achat");
		statutCol.setPrefWidth(120);
		TableColumn<Materiel, String> dateAchatCol = new TableColumn<Materiel, String>("Date d'achat");
		dateAchatCol.setPrefWidth(100);
		
		
		nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
		qlitePrevuCol.setCellValueFactory(new PropertyValueFactory<>("qualitePrevu"));
		qliteReelCol.setCellValueFactory(new PropertyValueFactory<>("qualiteReel"));
		
		qtePrevuCol.setCellValueFactory(new PropertyValueFactory<>("quantitePrevu"));
		qteReelCol.setCellValueFactory(new PropertyValueFactory<>("quantiteReel"));
		uniteMesureCol.setCellValueFactory(new PropertyValueFactory<>("uniteDemesure"));	
		prixUPrevuCol.setCellValueFactory(new PropertyValueFactory<>("prixUnitairePrevu"));	
		prixUReelCol.setCellValueFactory(new PropertyValueFactory<>("prixUnitaireReel"));	
		prixTotPrevuCol.setCellValueFactory(new PropertyValueFactory<>("prixTotPrevu"));	
		prixTotReelCol.setCellValueFactory(new PropertyValueFactory<>("prixTotReel"));
		deviseCol.setCellValueFactory(new PropertyValueFactory<>("devise"));
		statutCol.setCellValueFactory(new PropertyValueFactory<>("statutAchat"));
		dateAchatCol.setCellValueFactory(new PropertyValueFactory<>("dateAchat"));
		diffPrixCol.setCellValueFactory(new PropertyValueFactory<>("diffPrix"));
		
		nomCol.setSortType(TableColumn.SortType.DESCENDING);

		tableauMateriel.getColumns().addAll(nomCol,qtePrevuCol, qteReelCol,uniteMesureCol, prixUPrevuCol, prixUReelCol, prixTotPrevuCol,prixTotReelCol,deviseCol,statutCol,dateAchatCol,diffPrixCol);

		return tableauMateriel;

	}

	/**
	 * @return the tableauMateriel
	 */
	public TableView<Materiel> getTableauMateriel() {
		return initTable();
	}

	/**
	 * @param tableauMateriel the tableauMateriel to set
	 */
	public void setTableauMateriel(TableView<Materiel> tableauMateriel) {
		this.tableauMateriel = tableauMateriel;
	}

}

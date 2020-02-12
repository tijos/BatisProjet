package main.java.fr.batis.components.tables;

import main.java.fr.batis.entites.Employe;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class EmployersTable extends TableView<Employe> {


	TableView<Employe> tableauEmploye;

	public EmployersTable() {
		super();
		tableauEmploye = new TableView<Employe>();
		tableauEmploye.setColumnResizePolicy(UNCONSTRAINED_RESIZE_POLICY);
		tableauEmploye.setPrefWidth(800);
	}

	@SuppressWarnings("unchecked")
	public TableView<Employe> initTable() {

		TableColumn<Employe, String> nomCol = new TableColumn<Employe, String>("Nom");
		nomCol.setPrefWidth(150);
		TableColumn<Employe, String> prenomCol = new TableColumn<Employe, String>("Prénom");
		TableColumn<Employe, String> emailCol = new TableColumn<Employe, String>("Email");
		emailCol.setPrefWidth(150);
		TableColumn<Employe, String> dateNaissCol = new TableColumn<Employe, String>("Date de naissance");
		dateNaissCol.setPrefWidth(120);
		TableColumn<Employe, String> roleCol = new TableColumn<Employe, String>("Role");
		TableColumn<Employe, String> dateEmbCol = new TableColumn<Employe, String>("Date d'embauche");
		dateEmbCol.setPrefWidth(120);
		TableColumn<Employe, Double> salaireCol = new TableColumn<Employe, Double>("Salaire");
		salaireCol.setPrefWidth(150);
		TableColumn<Employe, String> deviseCol = new TableColumn<Employe, String>("Devise");
		TableColumn<Employe, String> autresInfosCol = new TableColumn<Employe, String>("Autres Informations");

		nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
		prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
		dateNaissCol.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
		roleCol.setCellValueFactory(new PropertyValueFactory<>("description"));
		dateEmbCol.setCellValueFactory(new PropertyValueFactory<>("dateEmbauche"));
		salaireCol.setCellValueFactory(new PropertyValueFactory<>("salaire"));
		deviseCol.setCellValueFactory(new PropertyValueFactory<>("devise"));
		autresInfosCol.setCellValueFactory(new PropertyValueFactory<>("autresInfos"));
		
		nomCol.setSortType(TableColumn.SortType.DESCENDING);

		tableauEmploye.getColumns().addAll(nomCol,prenomCol, emailCol,roleCol, dateNaissCol, dateEmbCol, salaireCol,deviseCol);

		return tableauEmploye;
	}

	


	/**
	 * @return the tableauEmploye
	 */
	public TableView<Employe> getTableauEmploye() {
		return initTable();
	}

	/**
	 * @param tableauEmploye
	 *            the tableauEmploye to set
	 */
	public void setTableauEmploye(TableView<Employe> tableauEmploye) {
		this.tableauEmploye = tableauEmploye;
	}

}

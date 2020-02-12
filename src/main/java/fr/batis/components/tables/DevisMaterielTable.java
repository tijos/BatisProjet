package main.java.fr.batis.components.tables;

import java.util.Optional;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import main.java.fr.batis.components.common.ImageUtils;
import main.java.fr.batis.entites.Materiel;

public class DevisMaterielTable extends TableView<Materiel> {

	TableView<Materiel> tableauMateriel;
	private ImageUtils imageUtils;

	public DevisMaterielTable() {
		super();
		tableauMateriel = new TableView<Materiel>();
		tableauMateriel.setColumnResizePolicy(UNCONSTRAINED_RESIZE_POLICY);
		tableauMateriel.setEditable(true);
		imageUtils = new ImageUtils();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TableView<Materiel> initTable() {
		imageUtils = new ImageUtils();
		tableauMateriel = new TableView<Materiel>();
		tableauMateriel.setColumnResizePolicy(UNCONSTRAINED_RESIZE_POLICY);

		Callback<TableColumn<Materiel, String>, TableCell<Materiel, String>> cellFactory = new Callback<TableColumn<Materiel, String>, TableCell<Materiel, String>>() {

			@Override
			public TableCell<Materiel, String> call(TableColumn<Materiel, String> p) {
				return new EditingCell();
			}
		};

		TableColumn<Materiel, String> nomCol = new TableColumn<Materiel, String>("Nom");
		nomCol.setPrefWidth(140);

		nomCol.setCellFactory(cellFactory);
		nomCol.setOnEditCommit(new EventHandler<CellEditEvent<Materiel, String>>() {
			@Override
			public void handle(CellEditEvent<Materiel, String> t) {
				t.getTableView().getItems().get(t.getTablePosition().getRow()).setNom(t.getNewValue());
			}
		});

		TableColumn<Materiel, String> qlitePrevuCol = new TableColumn<Materiel, String>("Qualité Prevue");
		qlitePrevuCol.setPrefWidth(120);
		TableColumn<Materiel, String> qtePrevuCol = new TableColumn<Materiel, String>("Quantité Prevue");
		qtePrevuCol.setPrefWidth(120);

		qtePrevuCol.setCellFactory(cellFactory);
		qtePrevuCol.setOnEditCommit(new EventHandler<CellEditEvent<Materiel, String>>() {
			@Override
			public void handle(CellEditEvent<Materiel, String> t) {
				t.getTableView().getItems().get(t.getTablePosition().getRow()).setNom(t.getNewValue());
			}
		});

		TableColumn<Materiel, String> uniteMesureCol = new TableColumn<Materiel, String>("Unité");
		uniteMesureCol.setPrefWidth(100);
		TableColumn<Materiel, String> prixUPrevuCol = new TableColumn<Materiel, String>("Prix Unitaire Prevu");
		prixUPrevuCol.setPrefWidth(150);

		TableColumn<Materiel, String> prixTotPrevuCol = new TableColumn<Materiel, String>("Prix Tot Prevu");
		prixTotPrevuCol.setPrefWidth(120);

		TableColumn<Materiel, Double> deviseCol = new TableColumn<Materiel, Double>("Devise");
		deviseCol.setPrefWidth(100);

		TableColumn<Materiel, String> statutCol = new TableColumn<Materiel, String>("Statut achat");
		statutCol.setPrefWidth(150);

		nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
		qlitePrevuCol.setCellValueFactory(new PropertyValueFactory<>("qualitePrevu"));

		qtePrevuCol.setCellValueFactory(new PropertyValueFactory<>("quantitePrevu"));

		uniteMesureCol.setCellValueFactory(new PropertyValueFactory<>("uniteDemesure"));
		prixUPrevuCol.setCellValueFactory(new PropertyValueFactory<>("prixUnitairePrevu"));

		prixTotPrevuCol.setCellValueFactory(new PropertyValueFactory<>("prixTotPrevu"));

		deviseCol.setCellValueFactory(new PropertyValueFactory<>("devise"));
		statutCol.setCellValueFactory(new PropertyValueFactory<>("statutAchat"));

		TableColumn colAction = new TableColumn<>("Action");
		colAction.setPrefWidth(50);

		colAction.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Materiel, Boolean>, ObservableValue<Boolean>>() {

					@Override
					public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Materiel, Boolean> p) {
						return new SimpleBooleanProperty(p.getValue() != null);
					}
				});

		// Adding the Button to the cell
		colAction.setCellFactory(new Callback<TableColumn<Materiel, Boolean>, TableCell<Materiel, Boolean>>() {

			@Override
			public TableCell<Materiel, Boolean> call(TableColumn<Materiel, Boolean> p) {
				return new ButtonCell(tableauMateriel);
			}

		});
		nomCol.setSortType(TableColumn.SortType.DESCENDING);

		tableauMateriel.getColumns().addAll(nomCol, qtePrevuCol, uniteMesureCol, prixUPrevuCol, prixTotPrevuCol,
				deviseCol, statutCol);

		return tableauMateriel;

	}

	// Define the button cell
	private class ButtonCell extends TableCell<Materiel, Boolean> {
		Button cellButton = new Button("");

		@SuppressWarnings("unchecked")
		ButtonCell(TableView<Materiel> tableauMateriel) {
			cellButton.setGraphic(new ImageView(imageUtils.getDeleteImage()));
			cellButton.setDisable(true);
			tableauMateriel.getSelectionModel().selectedItemProperty()
					.addListener(new RowSelectChangeListener(cellButton));

			cellButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent t) {

					Materiel currentMat = ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
					if (currentMat != null) {
						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("Suppresion du matériel");
						String s = "Voulez-vous supprimer " + currentMat.getNom() + " de la liste?";
						alert.setContentText(s);
						alert.setResizable(true);
						alert.setWidth(300);
						Optional<ButtonType> result = alert.showAndWait();

						if ((result.isPresent()) && (result.get() == ButtonType.OK)) {

							tableauMateriel.getItems().remove(currentMat);
							cellButton.setDisable(true);
						}
					}
					cellButton.setDisable(true);
				}
			});
		}

		@Override
		protected void updateItem(Boolean t, boolean empty) {
			super.updateItem(t, empty);
			if (!empty) {
				setGraphic(cellButton);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	private class RowSelectChangeListener implements ChangeListener {

		private Button deleteButton;

		public RowSelectChangeListener(Button cellButton) {
			this.deleteButton = cellButton;
		}

		@Override
		public void changed(ObservableValue ov, Object oldVal, Object newVal) {
			getDeleteButton().setDisable(false);
		}

		/**
		 * @return the deleteButton
		 */

		public Button getDeleteButton() {
			return deleteButton;
		}

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

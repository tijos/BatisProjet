package main.java.fr.batis.components;

import org.controlsfx.tools.Borders;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.java.fr.batis.components.common.BatisUtils;
import main.java.fr.batis.components.common.CommonUtils;
import main.java.fr.batis.components.common.ImageUtils;
import main.java.fr.batis.components.custom.CustomLabel;
import main.java.fr.batis.components.tables.DevisMaterielTable;
import main.java.fr.batis.entites.Devis;
import main.java.fr.batis.entites.Employe;
import main.java.fr.batis.entites.Materiel;
import main.java.fr.batis.service.DevisService;

public class DevisWindow {

	private TableView<Materiel> devisTable;
	private ObservableList<Materiel> devisTableData;
	private CommonUtils commonUtils;
	private ImageUtils imageUtils;
	private Devis devis;
	private DevisService devisService;

	public DevisWindow(ObservableList<Materiel> devisTableData2, Devis devis) {
		super();
		this.commonUtils = new CommonUtils();
		this.imageUtils = new ImageUtils();
		this.devis = devis;
		this.devisTableData = devisTableData2;
		this.devisService = new DevisService();
		initListMaterielsWindow(new Stage(), devis);
	}

	@SuppressWarnings("unused")
	private void initListMaterielsWindow(Stage primaryStage, Devis devis) {

		Image signatureImg = new Image(getClass().getResourceAsStream("/images/signature.png"));
		Image unCheckedBoxImg = new Image(getClass().getResourceAsStream("/images/unCheckedBox.png"));

		BorderPane panListMat = new BorderPane();
		GridPane panNewDevisTmp = commonUtils.getGridPanel();
		GridPane panRespo = commonUtils.getGridPanel();
		Node panHead = commonUtils.getHeader("Liste des matériaux pour le devis << " + devis.getNom() + " >>");

		ButtonBar control = new ButtonBar();
		Label responsable = new Label();

		Employe reponsable = devis.getReponsable();

		panRespo.add(new Label(reponsable.getNom()), 0, 0);
		panRespo.add(new Label(reponsable.getPrenom()), 1, 0);
		panRespo.add(new CustomLabel("Fonction : "), 3, 0);
		// TODO
		// panRespo.add(new Label(reponsable.getRole().getDescription()), 4, 0);
		panRespo.add(new CustomLabel("Téléphone : "), 5, 0);
		panRespo.add(new Label(reponsable.getTelephone()), 6, 0);
		panRespo.add(new CustomLabel("Email : "), 8, 0);
		panRespo.add(new Label(reponsable.getEmail()), 9, 0);

		Node wraperResponsable = Borders.wrap(panRespo).lineBorder().color(Color.rgb(168, 211, 255))
				.outerPadding(10, 5, 5, 5).innerPadding(5).title("Responsable ").buildAll();

		Button fermer = new Button("Fermer", new ImageView(getImageUtils().getCloseImg()));
		control.getButtons().add(fermer);
		control.setPadding(new Insets(10));
		devisTable = new DevisMaterielTable().getTableauMateriel();

		devisTable.setEditable(true);
		devisTableData.clear();
		devisTableData.addAll(BatisUtils.getMateriauxDevisInitial(devis));
		devisTable.setEditable(true);
		devisTable.getItems().clear();
		devisTable.setItems(devisTableData);
		devisTable.setPrefWidth(900);
		final HBox centerPane = new HBox();

		centerPane.setPadding(new Insets(10, 0, 0, 10));
		centerPane.getChildren().add(devisTable);
		BorderPane panListMatTmp = new BorderPane();
		panListMatTmp.setTop(wraperResponsable);
		panListMatTmp.setCenter(centerPane);

		panListMat.setTop(panHead);
		panListMat.setCenter(panListMatTmp);
		panListMat.setBottom(control);

		BatisUtils.setIconImage(primaryStage);
		Scene scene = new Scene(panListMat, 950, 400);
		scene.getStylesheets().add(getClass().getResource("/css/batis.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();

		/**
		 * 
		 */
		fermer.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				primaryStage.close();
			}
		});

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
	 * @return the devisTable
	 */
	public TableView<Materiel> getDevisTable() {
		return devisTable;
	}

	/**
	 * @param devisTable the devisTable to set
	 */
	public void setDevisTable(TableView<Materiel> devisTable) {
		this.devisTable = devisTable;
	}

	/**
	 * @return the devisTableData
	 */
	public ObservableList<Materiel> getDevisTableData() {
		return devisTableData;
	}

	/**
	 * @param devisTableData the devisTableData to set
	 */
	public void setDevisTableData(ObservableList<Materiel> devisTableData) {
		this.devisTableData = devisTableData;
	}

	/**
	 * @return the devis
	 */
	public Devis getDevis() {
		return devis;
	}

	/**
	 * @param devis the devis to set
	 */
	public void setDevis(Devis devis) {
		this.devis = devis;
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

}

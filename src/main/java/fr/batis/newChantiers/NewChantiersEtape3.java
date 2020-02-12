package main.java.fr.batis.newChantiers;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.controlsfx.tools.Borders;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.java.fr.batis.BatisUi;
import main.java.fr.batis.components.ChantierWindow;
import main.java.fr.batis.components.common.CommonUtils;
import main.java.fr.batis.components.common.DateConverterHelper;
import main.java.fr.batis.components.common.ImageUtils;
import main.java.fr.batis.entites.Autorisation;
import main.java.fr.batis.entites.Chantier;
import main.java.fr.batis.entites.EtudeSol;
import main.java.fr.batis.entites.Frais;
import main.java.fr.batis.entites.VoletAdmnistratif;
import main.java.fr.batis.enumeration.StatutPhase;
import main.java.fr.batis.service.ChantierService;

public class NewChantiersEtape3 extends Application {

	private Chantier chantier;
	private Stage etapePrecedent;
	private ComboBox<String> statutTravaux;
	private ChantierService chantierService;
	private DatePicker datePicker;
	private TextField livraisonTxtField;
	private TextField executionField;
	private TextField financementField;
	private List<Chantier> listChantier;
	private ImageUtils imageUtils;
	private TabPane tabPanePrincipal;
	private BatisUi batisUi;

	public NewChantiersEtape3(TabPane tabPane, Chantier projet, Stage etape2, BatisUi batisUi) {
		this.chantier = projet;
		imageUtils = new ImageUtils();
		this.batisUi = batisUi;
		this.etapePrecedent = etape2;
		chantierService = new ChantierService();
		listChantier = new ArrayList<Chantier>();
		this.tabPanePrincipal = tabPane;
		try {
			start(new Stage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		BorderPane panePrincipal = new BorderPane();
		CommonUtils common = new CommonUtils();
		Node header = common.getHeader("Création nouveau chantier étape III");
		primaryStage.getIcons().add(imageUtils.getIcon());

		primaryStage.setTitle("Nouveau chantier étape III");
		Label statutLabel = new Label("Statut travaux :");
		statutTravaux = new ComboBox<String>();

		for (StatutPhase statut : StatutPhase.values()) {
			statutTravaux.getItems().add(statut.getDescription());
		}

		// Date
		Label dateDebutLabel = new Label("Date de début des travaux :");
		datePicker = DateConverterHelper.getConvetedDate();

		//
		Callback<DatePicker, DateCell> dayCellFactory = common.getDayCellFactory();
		datePicker.setDayCellFactory(dayCellFactory);

		Label livaisonLabel = new Label("Date de livraison :");
		livraisonTxtField = new TextField();

		Label executionLabel = new Label("Execution :");
		executionField = new TextField();

		Label financementLabel = new Label("Finanacement :");
		financementField = new TextField();
		// add

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_LEFT);
		grid.setVgap(10);
		grid.setHgap(10);
		grid.setPadding(new Insets(10, 10, 10, 10));

		grid.add(statutLabel, 0, 0);
		grid.add(statutTravaux, 1, 0);

		grid.add(dateDebutLabel, 0, 1);
		grid.add(datePicker, 1, 1);

		grid.add(livaisonLabel, 0, 2);
		grid.add(livraisonTxtField, 1, 2);

		grid.add(executionLabel, 0, 3);
		grid.add(executionField, 1, 3);

		grid.add(financementLabel, 0, 4);
		grid.add(financementField, 1, 4);

		panePrincipal.setTop(header);
		panePrincipal.setCenter(grid);
		// BOUTONS

		Button annuler = new Button("Annuler", new ImageView(imageUtils.getCancelImg()));
		Button saveButton = new Button("Enregistrer", new ImageView(imageUtils.getSaveImg()));
		Button prevButton = new Button("Précédent", new ImageView(imageUtils.getPrevImg()));

		ButtonBar footer = new ButtonBar();
		footer.setPadding(new Insets(5));
		footer.getButtons().add(prevButton);
		footer.getButtons().add(saveButton);
		footer.getButtons().add(annuler);
		Node wraperBarButton = Borders.wrap(footer).etchedBorder().innerPadding(0).buildAll();

		panePrincipal.setBottom(wraperBarButton);

		panePrincipal.setStyle(common.getBatisStyle());
		Scene sc = new Scene(panePrincipal, 500, 400);

		// set the scene
		primaryStage.setScene(sc);
		showComponent(primaryStage);
		prevButton.setOnAction(cacherEtape3(primaryStage));

		saveButton.setOnAction(creerChantier(primaryStage));

		annuler.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				statutTravaux.getSelectionModel().clearSelection();
				datePicker.setValue(null);
			}
		});
	}

	/**
	 * 
	 * @param primaryStage
	 * @return
	 */
	public EventHandler<ActionEvent> creerChantier(Stage primaryStage) {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Chantier projet = getProjet();
				getChantierService().saveOrUpdate(projet);
				getListChantier().add(projet);
				primaryStage.close();
				getBatisUi().setCurrentChantier(projet);
				// getBatisUi().getCurrentChantier().setId(projet.getId());
				new ChantierWindow().getChantierWindow(projet, getTabPanePrincipal());
				// BatisUtils.reflesh(projet,new TabPane());
			}

			/**
			 * 
			 * @return
			 */
			public Chantier getProjet() {
				chantier = getChantier();
				chantier.setDateLivraison(getLivraisonTxtField().getText());
				chantier.setActif(true);
				chantier.setClosed(false);
				chantier.setExecution(getExecutionField().getText());
				chantier.setFinancement(getFinancementField().getText());
				if (statutTravaux.getSelectionModel() != null
						&& statutTravaux.getSelectionModel().getSelectedItem() != null) {
					chantier.setStatut(statutTravaux.getSelectionModel().getSelectedItem().toString());
				}

				String dateDebut = "";
				if (datePicker.getValue() != null) {
					dateDebut = datePicker.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				}

				EtudeSol etudeSol = new EtudeSol();
				Autorisation autorisationBatir = new Autorisation();
				List<Frais> listeFrais = new ArrayList<Frais>();
				VoletAdmnistratif voletAdmin = new VoletAdmnistratif();
				voletAdmin.setAutorisationBatir(autorisationBatir);
				voletAdmin.setEtudeSol(etudeSol);
				voletAdmin.setChantier(chantier);
				voletAdmin.setListeFrais(listeFrais);
				chantier.setVoletAdministratif(voletAdmin);
				chantier.setDateDebutChantier(dateDebut);

				return chantier;
			}
		};
	}

	/**
	 * 
	 * @param etape2
	 * @return
	 */
	public EventHandler<ActionEvent> afficherEtape2(Stage etape2) {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showComponent(etape2);
			}
		};
	}

	/**
	 * 
	 * @param etape2
	 * @return
	 */
	public EventHandler<ActionEvent> cacherEtape3(Stage etape3) {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				hideComponent(etape3);
				showComponent(getEtapePrecedent());
			}
		};
	}

	/**
	 * 
	 * @param primaryStage
	 */
	public void hideComponent(Stage primaryStage) {
		primaryStage.hide();
	}

	/**
	 * 
	 * @param primaryStage
	 */
	public void showComponent(Stage primaryStage) {
		primaryStage.show();
	}

	/**
	 * @return the chantier
	 */
	public Chantier getChantier() {
		return chantier;
	}

	/**
	 * @param chantier the chantier to set
	 */
	public void setChantier(Chantier chantier) {
		this.chantier = chantier;
	}

	/**
	 * @return the etapePrecedent
	 */
	public Stage getEtapePrecedent() {
		return etapePrecedent;
	}

	/**
	 * @param etapePrecedent the etapePrecedent to set
	 */
	public void setEtapePrecedent(Stage etapePrecedent) {
		this.etapePrecedent = etapePrecedent;
	}

	/**
	 * @return the statutTravaux
	 */
	public ComboBox<String> getStatutTravaux() {
		return statutTravaux;
	}

	/**
	 * @param statutTravaux the statutTravaux to set
	 */
	public void setStatutTravaux(ComboBox<String> statutTravaux) {
		this.statutTravaux = statutTravaux;
	}

	/**
	 * @return the chantierService
	 */
	public ChantierService getChantierService() {
		return chantierService;
	}

	/**
	 * @param chantierService the chantierService to set
	 */
	public void setChantierService(ChantierService chantierService) {
		this.chantierService = chantierService;
	}

	/**
	 * @return the datePicker
	 */
	public DatePicker getDatePicker() {
		return datePicker;
	}

	/**
	 * @param datePicker the datePicker to set
	 */
	public void setDatePicker(DatePicker datePicker) {
		this.datePicker = datePicker;
	}

	/**
	 * @return the listChantier
	 */
	public List<Chantier> getListChantier() {
		return listChantier;
	}

	/**
	 * @param listChantier the listChantier to set
	 */
	public void setListChantier(List<Chantier> listChantier) {
		this.listChantier = listChantier;
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
	 * @return the livraisonTxtField
	 */
	public TextField getLivraisonTxtField() {
		return livraisonTxtField;
	}

	/**
	 * @param livraisonTxtField the livraisonTxtField to set
	 */
	public void setLivraisonTxtField(TextField livraisonTxtField) {
		this.livraisonTxtField = livraisonTxtField;
	}

	/**
	 * @return the executionField
	 */
	public TextField getExecutionField() {
		return executionField;
	}

	/**
	 * @param executionField the executionField to set
	 */
	public void setExecutionField(TextField executionField) {
		this.executionField = executionField;
	}

	/**
	 * @return the financementField
	 */
	public TextField getFinancementField() {
		return financementField;
	}

	/**
	 * @param financementField the financementField to set
	 */
	public void setFinancementField(TextField financementField) {
		this.financementField = financementField;
	}

	/**
	 * @return the tabPanePrincipal
	 */
	public TabPane getTabPanePrincipal() {
		return tabPanePrincipal;
	}

	/**
	 * @param tabPanePrincipal the tabPanePrincipal to set
	 */
	public void setTabPanePrincipal(TabPane tabPanePrincipal) {
		this.tabPanePrincipal = tabPanePrincipal;
	}

	/**
	 * @return the batisUi
	 */
	public BatisUi getBatisUi() {
		return batisUi;
	}

	/**
	 * @param batisUi the batisUi to set
	 */
	public void setBatisUi(BatisUi batisUi) {
		this.batisUi = batisUi;
	}

}

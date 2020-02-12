package main.java.fr.batis.newChantiers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.tools.Borders;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.java.fr.batis.BatisUi;
import main.java.fr.batis.components.common.CommonUtils;
import main.java.fr.batis.components.common.ImageUtils;
import main.java.fr.batis.components.common.BatisUtils;
import main.java.fr.batis.entites.Chantier;
import main.java.fr.batis.enumeration.TypeChantier;

/**
 * 
 * @author tijos
 *
 */
public class NewChantiersEtape1 extends Application {

	private Chantier chantier;
	private TextField nomTxtField;
	private TextField lieuTxtField;
	private TextArea description;
	private ComboBox<String> typeChantier;
	private ComboBox<String> typeTravaux;
	private Label nBrePhases;
	private Label nbreEtage;
	private ToggleGroup buttonsGroup;
	private RadioButton maisonSimple;
	private RadioButton maisonEnEtage;
	private ImageUtils imageUtils;
	private Scene scenePrincipal;
	private TabPane tabPanePrincipal;
	private BatisUi batisUi;

	private static final Logger LOGGER = LogManager.getLogger(NewChantiersEtape1.class);

	public NewChantiersEtape1(TabPane tabPanePrincipal, BatisUi batisUi) {

		this.imageUtils = new ImageUtils();
		this.batisUi = batisUi;
		this.tabPanePrincipal = tabPanePrincipal;
		try {
			start(new Stage());
		} catch (Exception e) {

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Error : " + e.getCause());
			}

		}
	}

	/**
	 * 
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane panePrincipal = new BorderPane();
		CommonUtils common = new CommonUtils();
		Node header = common.getHeader("Création nouveau chantier étape I");

		primaryStage.setTitle("Nouveau chantier étape I");
		primaryStage.getIcons().add(imageUtils.getIcon());
		// DESCRIPTION
		Label descriptionLabel = new Label("Description :");
		description = new TextArea();
		description.setPrefSize(280, 100);

		// NOM
		nomTxtField = new TextField();
		nomTxtField.setPrefColumnCount(15);
		Label nomLabel = new Label("Nom Chantier :");

		// LIEU
		lieuTxtField = new TextField();
		lieuTxtField.setPrefColumnCount(15);
		Label lieuLabel = new Label("Lieu d'exécution :");

		// type travaux
		Label typeTravauxLabel = new Label("Type Travaux :");
		typeTravaux = new ComboBox<String>();

		for (TypeChantier type : TypeChantier.values()) {

			typeTravaux.getItems().add(type.getDescription());
		}

		// type chantier
		Label typeChantierLabel = new Label("Type Chantier :");
		typeChantier = new ComboBox<String>();

		for (TypeChantier type : TypeChantier.values()) {

			typeChantier.getItems().add(type.getDescription());
		}

		// ETAPES

		Label etapeLabel = new Label("Nombre de phases :");
		nBrePhases = new Label("1");
		Button etapePlus = new Button("", new ImageView(imageUtils.getPlusImg()));
		Button etapeMoins = new Button("", new ImageView(imageUtils.getMoinsImg()));
		nBrePhases.setPadding(new Insets(10));
		etapeMoins.setCursor(Cursor.HAND);
		etapePlus.setCursor(Cursor.HAND);
		// niveau
		Label niveau = new Label("Niveau");
		maisonEnEtage = new RadioButton("En étage");
		// TODO
		// maisonEnEtage.setGraphic(new ImageView(imageUtils.getRadioNotCheckedImg()));
		maisonSimple = new RadioButton("Simple");
		// TODO
		// maisonSimple.setGraphic(new ImageView(radioNotCheckedImg));
		// TODO
		// maisonEnEtage.setSelectedIcon(new ImageIcon("images/radio_Ckecked.png"));
		// maisonSimple.setSelectedIcon(new ImageIcon("images/radio_Ckecked.png"));
		// etage

		Label nbreEtageLabel = new Label("Nombre d'étage :");
		nbreEtage = new Label("1");
		// nbreEtage.setPadding(new Insets(10));
		Button etagePlus = new Button("", new ImageView(imageUtils.getPlusImg()));
		Button etageMoins = new Button("", new ImageView(imageUtils.getMoinsImg()));
		nBrePhases.setPadding(new Insets(10));
		etapeMoins.setCursor(Cursor.HAND);
		etapePlus.setCursor(Cursor.HAND);

		// add

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_LEFT);

		grid.setVgap(10);
		grid.setHgap(10);
		grid.setPadding(new Insets(10, 10, 10, 10));

		grid.add(descriptionLabel, 0, 0);
		grid.add(description, 1, 0);

		grid.add(nomLabel, 0, 1);
		grid.add(nomTxtField, 1, 1);

		grid.add(lieuLabel, 0, 2);
		grid.add(lieuTxtField, 1, 2);

		grid.add(typeTravauxLabel, 0, 3);
		grid.add(typeTravaux, 1, 3);

		grid.add(typeChantierLabel, 0, 4);
		grid.add(typeChantier, 1, 4);
		// col, row, colspan, rowspan
		grid.add(etapeLabel, 0, 5);
		BorderPane etapePane = new BorderPane();
		etapePane.setLeft(etapeMoins);
		etapePane.setCenter(nBrePhases);
		etapePane.setRight(etapePlus);
		grid.add(etapePane, 1, 5);

		// niveau
		BorderPane niveauPane = new BorderPane();
		buttonsGroup = new ToggleGroup();
		maisonSimple.setToggleGroup(buttonsGroup);
		maisonEnEtage.setToggleGroup(buttonsGroup);
		niveauPane.setLeft(maisonSimple);
		niveauPane.setRight(maisonEnEtage);

		grid.add(niveau, 0, 6);
		grid.add(niveauPane, 1, 6);

		// etage
		grid.add(nbreEtageLabel, 0, 7);

		BorderPane etagePane = new BorderPane();
		etagePane.setVisible(false);
		nbreEtageLabel.setVisible(false);
		etagePane.setLeft(etageMoins);
		etagePane.setCenter(nbreEtage);
		etagePane.setRight(etagePlus);
		grid.add(etagePane, 1, 7);

		panePrincipal.setTop(header);
		panePrincipal.setCenter(grid);
		// BOUTON

		Button annuler = new Button("Annuler", new ImageView(imageUtils.getCancelImg()));
		Button suivantButton = new Button("Suivant", new ImageView(imageUtils.getNextImg()));

		ButtonBar footer = new ButtonBar();
		footer.setPadding(new Insets(5));
		footer.getButtons().add(suivantButton);
		footer.getButtons().add(annuler);

		Node wraperBarButton = Borders.wrap(footer).etchedBorder().innerPadding(0).buildAll();

		panePrincipal.setBottom(wraperBarButton);
		panePrincipal.setBottom(wraperBarButton);
		scenePrincipal = new Scene(panePrincipal, 500, 500);

		// set the scene
		primaryStage.setScene(scenePrincipal);
		showComponent(primaryStage);

		maisonSimple.setOnAction(hideEtageComponent(etagePane, nbreEtageLabel));
		maisonEnEtage.setOnAction(showEtageComponent(etagePane, nbreEtageLabel));
		etageMoins.setOnAction(diminuer(nbreEtage));
		etagePlus.setOnAction(augmenter(nbreEtage));

		etapeMoins.setOnAction(diminuer(nBrePhases));
		etapePlus.setOnAction(augmenter(nBrePhases));
		suivantButton.setOnAction(creerChantier(primaryStage));

		annuler.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				maisonEnEtage.setSelected(false);
				typeTravaux.getSelectionModel().clearSelection();
				description.clear();
				maisonSimple.setSelected(false);
				nomTxtField.clear();
				lieuTxtField.clear();
				typeChantier.getSelectionModel().clearSelection();
				buttonsGroup.getToggles().clear();

			}
		});
	}

	/**
	 * 
	 * @param etagePane
	 * @param nbreEtageLabel
	 * @return
	 */

	private EventHandler<ActionEvent> showEtageComponent(BorderPane etagePane, Label nbreEtageLabel) {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				etagePane.setVisible(true);
				nbreEtageLabel.setVisible(true);
			}
		};
	}

	/**
	 * 
	 * @param etagePane
	 * @param nbreEtageLabel
	 * @return
	 */
	private EventHandler<ActionEvent> hideEtageComponent(BorderPane etagePane, Label nbreEtageLabel) {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				etagePane.setVisible(false);
				nbreEtageLabel.setVisible(false);
			}
		};
	}

	/**
	 * 
	 * @param nbreEtage
	 * @return
	 */
	public EventHandler<ActionEvent> diminuer(Label nbreEtage) {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				int nbre = Integer.valueOf(nbreEtage.getText());
				if (nbre > 1) {
					nbre = nbre - 1;
				}

				nbreEtage.setText(String.valueOf(nbre));
				// nbreEtage.updateUI();
			}
		};
	}

	/**
	 * 
	 * @param nbreEtage
	 * @return
	 */

	public EventHandler<ActionEvent> augmenter(Label nbreEtage) {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				int nbre = Integer.valueOf(nbreEtage.getText());
				// if(nbre < 10) {
				nbre = nbre + 1;
				// }

				nbreEtage.setText(String.valueOf(nbre));
				// nbreEtage.updateUI();
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
	 * 
	 * @param primaryStage
	 * @return
	 */
	public EventHandler<ActionEvent> creerChantier(Stage primaryStage) {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (isValid()) {
					Chantier projet = getProjet();
					NewChantiersEtape2 newChantier2 = new NewChantiersEtape2(getTabPanePrincipal(), projet,
							primaryStage, getBatisUi());
					try {
						newChantier2.start(new Stage());
					} catch (Exception e) {
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("Error : " + e.getCause());

						}
					}
					hideComponent(primaryStage);
				} else {
					BatisUtils.showAlert(Alert.AlertType.ERROR, scenePrincipal.getWindow(), "Erreur!",
							"Vous devez completer tous les champs!");
				}

			}

			public boolean isValid() {
				if (nomTxtField.getText() != null && lieuTxtField.getText() != null
						&& typeChantier.getSelectionModel() != null
						&& typeChantier.getSelectionModel().getSelectedItem() != null
						&& typeChantier.getSelectionModel() != null
						&& (maisonEnEtage.isSelected() || maisonSimple.isSelected())
						&& typeTravaux.getSelectionModel() != null
						&& typeTravaux.getSelectionModel().getSelectedItem() != null && nBrePhases.getText() != null
						&& description.getText() != null

				) {
					return true;
				}
				return false;
			}

			public Chantier getProjet() {
				chantier = new Chantier();
				chantier.setNomProjet(nomTxtField.getText());
				chantier.setSiteProjet(lieuTxtField.getText());
				chantier.setTypeChantier(typeChantier.getSelectionModel().getSelectedItem().toString());
				chantier.setMaisonEnEtage(maisonEnEtage.isSelected());
				chantier.setMaisonSimple(maisonSimple.isSelected());
				chantier.setTypeTravaux(typeTravaux.getSelectionModel().getSelectedItem().toString());
				chantier.setNbreEtape(nBrePhases.getText());
				chantier.setDescription(description.getText());

				if (maisonEnEtage.isSelected()) {
					chantier.setNiveau(Integer.valueOf(nbreEtage.getText()) + 1);
				}
				return chantier;
			}
		};
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
	 * @return the nomTxtField
	 */
	public TextField getNomTxtField() {
		return nomTxtField;
	}

	/**
	 * @param nomTxtField the nomTxtField to set
	 */
	public void setNomTxtField(final TextField nomTxtField) {
		this.nomTxtField = nomTxtField;
	}

	/**
	 * @return the lieuTxtField
	 */
	public TextField getLieuTxtField() {
		return lieuTxtField;
	}

	/**
	 * @param lieuTxtField the lieuTxtField to set
	 */
	public void setLieuTxtField(TextField lieuTxtField) {
		this.lieuTxtField = lieuTxtField;
	}

	/**
	 * @return the TypeChantier
	 */
	public ComboBox<String> getTypeChantier() {
		return typeChantier;
	}

	/**
	 * @param TypeChantier the TypeChantier to set
	 */
	public void setTypeChantier(final ComboBox<String> typeChantier) {
		this.typeChantier = typeChantier;
	}

	/**
	 * @return the nBrePhases
	 */
	public Label getnBrePhases() {
		return nBrePhases;
	}

	/**
	 * @param nBrePhases the nBrePhases to set
	 */
	public void setnBrePhases(final Label nBrePhases) {
		this.nBrePhases = nBrePhases;
	}

	/**
	 * @return the nbreEtage
	 */
	public Label getNbreEtage() {
		return nbreEtage;
	}

	/**
	 * @param nbreEtage the nbreEtage to set
	 */
	public void setNbreEtage(final Label nbreEtage) {
		this.nbreEtage = nbreEtage;
	}

	/**
	 * @return the buttonsGroup
	 */
	public ToggleGroup getButtonsGroup() {
		return buttonsGroup;
	}

	/**
	 * @param buttonsGroup the buttonsGroup to set
	 */
	public void setButtonsGroup(ToggleGroup buttonsGroup) {
		this.buttonsGroup = buttonsGroup;
	}

	/**
	 * @return the TypeTravaux
	 */
	public ComboBox<String> getTypeTravaux() {
		return typeTravaux;
	}

	/**
	 * @param TypeTravaux the TypeTravaux to set
	 */
	public void setTypeTravaux(ComboBox<String> typeTravaux) {
		this.typeTravaux = typeTravaux;
	}

	/**
	 * @return the maisonSimple
	 */
	public RadioButton getMaisonSimple() {
		return maisonSimple;
	}

	/**
	 * @param maisonSimple the maisonSimple to set
	 */
	public void setMaisonSimple(RadioButton maisonSimple) {
		this.maisonSimple = maisonSimple;
	}

	/**
	 * @return the maisonEnEtage
	 */
	public RadioButton getMaisonEnEtage() {
		return maisonEnEtage;
	}

	/**
	 * @param maisonEnEtage the maisonEnEtage to set
	 */
	public void setMaisonEnEtage(RadioButton maisonEnEtage) {
		this.maisonEnEtage = maisonEnEtage;
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
	public void setImageUtils(final ImageUtils imageUtils) {
		this.imageUtils = imageUtils;
	}

	/**
	 * @return the scenePrincipal
	 */
	public Scene getScenePrincipal() {
		return scenePrincipal;
	}

	/**
	 * @param scenePrincipal the scenePrincipal to set
	 */
	public void setScenePrincipal(Scene scenePrincipal) {
		this.scenePrincipal = scenePrincipal;
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

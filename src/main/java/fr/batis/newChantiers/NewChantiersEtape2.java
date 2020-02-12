package main.java.fr.batis.newChantiers;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.tools.Borders;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.java.fr.batis.BatisUi;
import main.java.fr.batis.components.common.BatisUtils;
import main.java.fr.batis.components.common.CommonUtils;
import main.java.fr.batis.components.common.ImageUtils;
import main.java.fr.batis.entites.Chantier;
import main.java.fr.batis.entites.PhaseConstruction;
import main.java.fr.batis.enumeration.StatutPhase;

/**
 * Etape 2
 * 
 * @author tijos
 *
 */
public class NewChantiersEtape2 extends Application {

	private Chantier chantier;
	private Stage etapePrecedent;
	private List<PhaseConstruction> listPhasesConstruction;
	private GridPane grid;
	private ImageUtils imageUtils;
	private TabPane tabPanePrincipal;
	private BatisUi batisUi;

	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(NewChantiersEtape2.class);

	/**
	 * 
	 * @param tabPane
	 * @param projet
	 * @param etape1
	 * @param batisUi
	 */
	public NewChantiersEtape2(final TabPane tabPane, Chantier projet, final Stage etape1, final BatisUi batisUi) {
		super();
		this.chantier = projet;
		this.etapePrecedent = etape1;
		this.imageUtils = new ImageUtils();
		this.batisUi = batisUi;
		this.tabPanePrincipal = tabPane;
	}

	/**
	 * 
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		LOGGER.info("Start new chantier étape 2");

		final BorderPane panePrincipal = new BorderPane();
		final CommonUtils common = new CommonUtils();
		final Node header = common.getHeader("Création nouveau chantier étape II");

		primaryStage.setTitle("Nouveau chantier étape II");
		primaryStage.getIcons().add(imageUtils.getIcon());

		final String etapeString = getChantier().getNbreEtape();
		final int etape = Integer.valueOf(etapeString);

		// add

		grid = new GridPane();

		grid.setAlignment(Pos.TOP_LEFT);

		grid.setVgap(10);
		grid.setHgap(10);
		grid.setPadding(new Insets(10, 10, 10, 10));

		listPhasesConstruction = new ArrayList<>();
		for (int i = 0; i <= etape; i++) {

			final Label phaseLabel = new Label("Phase " + i);
			TextArea description = new TextArea();

			description.setMaxSize(250, 200);
			if (i == 0) {
				description.setText("Phase de préparation de début des travaux");
			}

			grid.add(phaseLabel, 0, i);
			grid.add(description, 1, i);
		}

		panePrincipal.setTop(header);
		panePrincipal.setCenter(grid);
		// BOUTONS

		final Button annuler = new Button("Annuler", new ImageView(imageUtils.getCancelImg()));
		final Button suivantButton = new Button("Suivant", new ImageView(imageUtils.getNextImg()));
		final Button prevButton = new Button("Précédent", new ImageView(imageUtils.getPrevImg()));

		ButtonBar footer = new ButtonBar();
		footer.setPadding(new Insets(5));
		footer.getButtons().add(prevButton);
		footer.getButtons().add(suivantButton);
		footer.getButtons().add(annuler);

		Node wraperBarButton = Borders.wrap(footer).etchedBorder().innerPadding(0).buildAll();

		panePrincipal.setBottom(wraperBarButton);
		final Scene scene = new Scene(panePrincipal, 500, 400);

		// set the scene
		primaryStage.setScene(scene);
		showComponent(primaryStage);
		prevButton.setOnAction(cacherEtape2(primaryStage));
		suivantButton.setOnAction(creerChantier(primaryStage));

	}

	/**
	 * 
	 * @param primaryStage
	 * @return
	 */
	public EventHandler<ActionEvent> creerChantier(final Stage primaryStage) {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Chantier projet = getChantier();

				if (projet.getNbreEtape() != null) {
					final ObservableList<Node> comp = grid.getChildren();
					int i = 0;

					for (final Node cpnt : comp) {

						if (cpnt instanceof TextArea || cpnt instanceof DatePicker) {

							final Label label = (Label) comp.get(i - 1);
							PhaseConstruction phase = new PhaseConstruction();
							phase.setNom(label.getText());
							phase.setNumero(BatisUtils.getPhaseNumber(label.getText()));
							phase.setDescription(((TextArea) cpnt).getText());

							phase.setChantier(projet);
							phase.setCodeStatut(StatutPhase.ENPREPARATION);

							listPhasesConstruction.add(phase);

						}

						i++;
					}
					projet.setPhasesConstruction(listPhasesConstruction);
				}

				new NewChantiersEtape3(getTabPanePrincipal(), projet, primaryStage, getBatisUi());
				hideComponent(primaryStage);
			}

		};
	}

	/**
	 * 
	 * @param etape1
	 * @return
	 */

	public EventHandler<ActionEvent> afficherEtape1(final Stage etape1) {

		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent event) {
				showComponent(etape1);
			}
		};
	}

	/**
	 * 
	 * @param etape2
	 * @return
	 */
	public EventHandler<ActionEvent> cacherEtape2(final Stage etape2) {

		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent event) {
				hideComponent(etape2);
				showComponent(getEtapePrecedent());
			}
		};
	}

	/**
	 * 
	 * @param primaryStage
	 */
	public void hideComponent(final Stage primaryStage) {
		primaryStage.hide();
	}

	/**
	 * 
	 * @param primaryStage
	 */
	public void showComponent(final Stage primaryStage) {
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
	public void setChantier(final Chantier chantier) {
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
	public void setEtapePrecedent(final Stage etapePrecedent) {
		this.etapePrecedent = etapePrecedent;
	}

	/**
	 * @return the listPhasesConstruction
	 */
	public List<PhaseConstruction> getListPhasesConstruction() {
		return listPhasesConstruction;
	}

	/**
	 * @param listPhases the listPhasesConstruction to set
	 */
	public void setListPhasesConstruction(final List<PhaseConstruction> listPhases) {
		this.listPhasesConstruction = listPhases;
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

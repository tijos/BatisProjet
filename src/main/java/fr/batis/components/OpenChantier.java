/**
 * 
 */
package main.java.fr.batis.components;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.tools.Borders;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.java.fr.batis.components.common.BatisUtils;
import main.java.fr.batis.components.common.CommonUtils;
import main.java.fr.batis.components.common.ImageUtils;
import main.java.fr.batis.entites.Chantier;
import main.java.fr.batis.entites.LastOpened;
import main.java.fr.batis.service.ChantierService;
import main.java.fr.batis.service.MemoryService;

/**
 * @author dlj7961
 *
 */
public class OpenChantier {

	private List<Chantier> listChantiers;
	private TabPane tabPanePrincipale;
	private ImageUtils imageUtils;
	private Chantier currentChantier;
	private CommonUtils commonUtils;
	private ChantierService chantierService;

	private static final Logger LOGGER = LogManager.getLogger(OpenChantier.class);

	public OpenChantier(TabPane tabPanePrincipal) {
		super();
		this.tabPanePrincipale = tabPanePrincipal;
		this.commonUtils = new CommonUtils();
		this.chantierService = new ChantierService();
		this.imageUtils = new ImageUtils();
		this.listChantiers = new ArrayList<Chantier>();
	}

	public Chantier start(Stage primaryStage) throws Exception {
		primaryStage.getIcons().add(imageUtils.getIcon());
		primaryStage.setTitle("Ouverture de fichier...");
		Image rootImg = new Image(getClass().getResourceAsStream("/images/pRoot.png"));
		try {
			BorderPane rootPane = new BorderPane();
			GridPane centerGrid = commonUtils.getGridPanel();
			GridPane topGrid = commonUtils.getGridPanel();

			CheckBox actif = new CheckBox("Actifs");
			actif.setSelected(true);
			CheckBox closed = new CheckBox("Cloturés");
			CheckBox desactived = new CheckBox("Désactivés");
			CheckBox all = new CheckBox("Tout les chantiers");
			Image searchImg = new Image(getClass().getResourceAsStream("/images/rechercher.png"));
			Button search = new Button("Rechercher", new ImageView(searchImg));

			actif.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					closed.setSelected(false);
					desactived.setSelected(false);
					all.setSelected(false);
				}
			});

			closed.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					actif.setSelected(false);
					// desactived.setSelected(false);
					all.setSelected(false);
				}
			});

			desactived.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					// closed.setSelected(false);
					actif.setSelected(false);
					all.setSelected(false);
				}
			});

			all.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {

					if (all.isSelected()) {
						closed.setSelected(true);
						desactived.setSelected(true);
						actif.setSelected(true);
					} else {
						closed.setSelected(false);
						desactived.setSelected(false);
						actif.setSelected(false);
					}

				}
			});

			topGrid.add(actif, 0, 0);
			topGrid.add(closed, 1, 0);
			topGrid.add(desactived, 2, 0);
			topGrid.add(all, 3, 0);
			topGrid.add(search, 6, 0);

			Node wraperTopGrid = Borders.wrap(topGrid).lineBorder().color(Color.rgb(168, 211, 255))
					.outerPadding(15, 5, 5, 5).innerPadding(2).title("Critères de recherche ").buildAll();

			centerGrid.setPadding(new Insets(10));

			search.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					centerGrid.getChildren().clear();
					listChantiers.clear();
					listChantiers = chantierService.findAll(actif.isSelected(), closed.isSelected(),
							desactived.isSelected(), all.isSelected());

					int lineNumber = 0;

					for (Chantier cht : listChantiers) {

						Label label = new Label(cht.getNomProjet());
						label.setCursor(Cursor.HAND);
						label.setGraphic(new ImageView(rootImg));
						if (lineNumber <= 4) {
							centerGrid.add(label, 0, listChantiers.indexOf(cht));
						}

						if ((lineNumber > 4) && (lineNumber < 10)) {
							centerGrid.add(label, 5, listChantiers.indexOf(cht) - 5);

						}

						if ((lineNumber >= 10)) {
							centerGrid.add(label, 10, listChantiers.indexOf(cht) - 10);

						}

						lineNumber++;
						label.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
							@Override
							public void handle(MouseEvent mouseEvent) {
								currentChantier = BatisUtils.getChantierByName(listChantiers, cht.getNomProjet());

								if (currentChantier != null) {
									MemoryService memoryService = new MemoryService();
									LastOpened lastOpenedChantier = memoryService.getFirst();
									if (lastOpenedChantier == null) {
										lastOpenedChantier = new LastOpened();
									}

									lastOpenedChantier.setProjectId(currentChantier.getId());
									lastOpenedChantier.setProjectName(currentChantier.getNomProjet());
									memoryService.saveOrUpdate(lastOpenedChantier);

									new ChantierWindow().getChantierWindow(currentChantier, tabPanePrincipale);
									primaryStage.close();
								}

							}
						});

					}
				}
			});

			rootPane.setTop(wraperTopGrid);
			rootPane.setCenter(centerGrid);
			Scene scene = new Scene(rootPane, 560, 460);
			ButtonBar bar = new ButtonBar();
			bar.setPadding(new Insets(5));
			Image closeImg = new Image(getClass().getResourceAsStream("/images/exit.png"));
			Button closeButton = new Button("Fermer", new ImageView(closeImg));
			bar.getButtons().add(closeButton);

			Node wraperBarButton = Borders.wrap(bar).etchedBorder().innerPadding(0).buildAll();

			rootPane.setBottom(wraperBarButton);

			primaryStage.setScene(scene);
			primaryStage.show();

			closeButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					primaryStage.close();
				}
			});

		} catch (Exception e) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Error : " + e.getCause());

			}
		}

		return currentChantier;
	}

	/**
	 * @param listChantiers the listChantiers to set
	 */
	public void setListChantiers(List<Chantier> listChantiers) {
		this.listChantiers = listChantiers;
	}

	/**
	 * @return the tabPanePrincipale
	 */
	public TabPane getTabPanePrincipale() {
		return tabPanePrincipale;
	}

	/**
	 * @param tabPanePrincipale the tabPanePrincipale to set
	 */
	public void setTabPanePrincipale(TabPane tabPanePrincipale) {
		this.tabPanePrincipale = tabPanePrincipale;
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
	 * @return the currentChantier
	 */
	public Chantier getCurrentChantier() {
		return currentChantier;
	}

	/**
	 * @param currentChantier the currentChantier to set
	 */
	public void setCurrentChantier(Chantier currentChantier) {
		this.currentChantier = currentChantier;
	}

}

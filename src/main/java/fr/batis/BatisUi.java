package main.java.fr.batis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.tools.Borders;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.java.fr.batis.components.common.CommonUtils;
import main.java.fr.batis.components.common.ImageUtils;
import main.java.fr.batis.entites.Chantier;
import main.java.fr.batis.menu.BatisMenu;
import main.java.fr.batis.menu.BatisToolBar;
//import main.java.fr.batis.service.MemoryService;

public class BatisUi extends Application {

	private Stage stagePrincipal;
	private BorderPane conteneurPrincipal;
	private TabPane tabPanePrincipal;
	private CommonUtils common = new CommonUtils();
	private Chantier currentChantier;
	private ImageUtils imageUtils = new ImageUtils();
	private List<Chantier> listChantiers;
	private BatisUi batisUserInterface;
	private Image icon;
	private Image logo;
	private Image exitImg;
	private Image saveImg;

	private static final Logger LOGGER = LogManager.getLogger(BatisUi.class);

	public BatisUi() {
		this.currentChantier = new Chantier();
		this.imageUtils = new ImageUtils();
		this.listChantiers = new ArrayList<Chantier>();
	}

	@Override
	public void start(Stage primaryStage) {

		LOGGER.info("Demarrage ");

		setBatisUserInterface(new BatisUi());
		try {

			stagePrincipal = primaryStage;
			stagePrincipal.setTitle("Gestion de construction");

			icon = new Image(getClass().getResourceAsStream("/images/icon.png"));
			setIcon(icon);
			ImageView editView = new ImageView(icon);
			editView.setFitWidth(15);
			editView.setFitHeight(15);
			stagePrincipal.getIcons().add(icon);

			initConteneurPrincipal();
		} catch (IOException e) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Error : " + e.getCause());
			}

		}

	}

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * 
	 * @throws IOException
	 */
	private void initConteneurPrincipal() throws IOException {

		conteneurPrincipal = new BorderPane();
		tabPanePrincipal = new TabPane();

		BatisMenu batisMenu = new BatisMenu();
		BorderPane menusBar = new BorderPane();
		// Menu
		MenuBar menuBar = batisMenu.initBatisMenusBar(conteneurPrincipal, tabPanePrincipal, getStagePrincipal(),
				getBatisUserInterface());

		ButtonBar barButtons = new ButtonBar();
		barButtons.setPadding(new Insets(5, 5, 5, 5));

		Button annuler = new Button("Fermer", new ImageView(exitImg));
		Button enregistrer = new Button("Enregistrer", new ImageView(saveImg));

		barButtons.getButtons().add(enregistrer);
		barButtons.getButtons().add(annuler);

		Node wraperBarButton = Borders.wrap(barButtons).etchedBorder().innerPadding(0).buildAll();
		conteneurPrincipal.setBottom(wraperBarButton);

		// toolBar
		BatisToolBar batisToolBar = new BatisToolBar();
		ToolBar toolBar = batisToolBar.getToolBarsMenu(tabPanePrincipal, getBatisUserInterface());
		toolBar.setCursor(Cursor.HAND);

		menusBar.setTop(menuBar);
		menusBar.setBottom(toolBar);
		conteneurPrincipal.setTop(menusBar);

		logo = new Image(getClass().getResourceAsStream("/images/logo.png"));
		setLogo(logo);

		conteneurPrincipal.setCenter(tabPanePrincipal);

		Scene scene = new Scene(conteneurPrincipal, 1300, 650);

		scene.getStylesheets().add(getClass().getResource("/css/batis.css").toExternalForm());
		conteneurPrincipal.setStyle(common.getBatisStyle());
		conteneurPrincipal.prefHeightProperty().bind(scene.heightProperty());
		conteneurPrincipal.prefWidthProperty().bind(scene.widthProperty());

		stagePrincipal.setScene(scene);
		stagePrincipal.show();

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
	 * @return the listChantiers
	 */
	public List<Chantier> getListChantiers() {
		return listChantiers;
	}

	/**
	 * @param listChantiers the listChantiers to set
	 */
	public void setListChantiers(List<Chantier> listChantiers) {
		this.listChantiers = listChantiers;
	}

	/**
	 * @return the batisUserInterface
	 */
	public BatisUi getBatisUserInterface() {
		return batisUserInterface;
	}

	/**
	 * @param batisUserInterface the batisUserInterface to set
	 */
	public void setBatisUserInterface(BatisUi batisUserInterface) {
		this.batisUserInterface = batisUserInterface;
	}

	/**
	 * @return the icon
	 */
	public Image getIcon() {
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(Image icon) {
		this.icon = icon;
	}

	/**
	 * @return the logo
	 */
	public Image getLogo() {
		return logo;
	}

	/**
	 * @param logo the logo to set
	 */
	public void setLogo(Image logo) {
		this.logo = logo;
	}

	/**
	 * @return the stagePrincipal
	 */
	public Stage getStagePrincipal() {
		return stagePrincipal;
	}

	/**
	 * @param stagePrincipal the stagePrincipal to set
	 */
	public void setStagePrincipal(Stage stagePrincipal) {
		this.stagePrincipal = stagePrincipal;
	}

	/**
	 * @return the exitImg
	 */
	public Image getExitImg() {
		return exitImg;
	}

	/**
	 * @param exitImg the exitImg to set
	 */
	public void setExitImg(Image exitImg) {
		this.exitImg = exitImg;
	}

	/**
	 * @return the common
	 */
	public CommonUtils getCommon() {
		return common;
	}

	/**
	 * @param common the common to set
	 */
	public void setCommon(CommonUtils common) {
		this.common = common;
	}

}

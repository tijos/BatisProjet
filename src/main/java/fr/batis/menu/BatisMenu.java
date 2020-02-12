package main.java.fr.batis.menu;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.tools.Borders;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.java.fr.batis.BatisUi;
import main.java.fr.batis.components.AboutBatisWindow;
import main.java.fr.batis.components.NewDevisWindow;
import main.java.fr.batis.components.OpenChantier;
import main.java.fr.batis.components.common.BatisUtils;
import main.java.fr.batis.components.common.CommonUtils;
import main.java.fr.batis.components.common.ImageUtils;
import main.java.fr.batis.entites.Chantier;
import main.java.fr.batis.entites.LastOpened;
import main.java.fr.batis.newChantiers.NewChantiersEtape1;
import main.java.fr.batis.service.ChantierService;
import main.java.fr.batis.service.MemoryService;

public class BatisMenu {

	private static final Logger LOGGER = LogManager.getLogger(BatisMenu.class);
	private Image exitImg;
	private Image saveImg;
	private Image openImg;
	private Image newImg;
	private Image undoImg;
	private Image redoImg;
	private Image refleshImg;
	private Image userImg;
	private ImageUtils imageUtils = new ImageUtils();
	private CommonUtils common = new CommonUtils();
	private BatisUi batisUserInterface;

	private ChantierService chantierService = new ChantierService();

	/**
	 * 
	 * @throws IOException
	 */

	public MenuBar initBatisMenusBar(BorderPane conteneurPrincipal, TabPane tabPanePrincipal, Stage stagePrincipal,
			BatisUi batisUi) throws IOException {

		ButtonBar barButtons = new ButtonBar();
		barButtons.setPadding(new Insets(5, 5, 5, 5));
		setBatisUserInterface(batisUi);
		exitImg = imageUtils.getExitImg();
		setExitImg(exitImg);
		saveImg = imageUtils.getSaveImg();
		setSaveImg(saveImg);
		openImg = imageUtils.getOpenImg();
		setOpenImg(openImg);
		newImg = imageUtils.getNewImg();
		setNewImg(newImg);
		undoImg = imageUtils.getUndoImg();
		setUndoImg(undoImg);
		redoImg = imageUtils.getRedoImg();
		setRedoImg(redoImg);
		refleshImg = imageUtils.getRefleshImg();
		setRefleshImg(refleshImg);
		userImg = imageUtils.getUserImg();
		setUserImg(userImg);

		Node wraperBarButton = Borders.wrap(barButtons).etchedBorder().innerPadding(0).buildAll();
		conteneurPrincipal.setBottom(wraperBarButton);

		MenuBar menuBar = new MenuBar();

		MenuItem quitter = getQuitterMenuItem(exitImg);
		MenuItem ouvrir = getOuvrirMenuItem(openImg);
		MenuItem reflesh = getRefleshMenuItem(refleshImg);
		MenuItem enregistrer = getSaveMenuItem(saveImg);
		MenuItem saveAs = getSaveAsMenuItem(saveImg);

		// Nouveau
		Menu nouveauMenu = getNouveauMenuItem(openImg, newImg, tabPanePrincipal, batisUi);

		// Edit
		Menu edit = new Menu("Edition");

		// affichage
		Menu affichage = new Menu("Affichage");

		// help
		Menu help = getHelpMenu();
		// user

		MenuItem creerCompte = new MenuItem("Créer votre compte...");
		MenuItem connexion = new MenuItem("Connexion...");
		// TODO if connecté
		MenuItem deconnexion = new MenuItem("Déconnexion...");

		Menu utilisateur = getUserMenuItem(creerCompte, connexion, deconnexion);

		// fichier

		Menu menuFichier = getMenuFichier(quitter, ouvrir, reflesh, enregistrer, saveAs, nouveauMenu);

		reflesh.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				MemoryService memoryService = new MemoryService();
				LastOpened lastOpenedChantier = memoryService.getFirst();
				BatisUtils.refleshProject(tabPanePrincipal, lastOpenedChantier.getProjectId());
			}
		});

		menuBar.getMenus().add(menuFichier);
		menuBar.getMenus().add(edit);
		menuBar.getMenus().add(affichage);
		menuBar.getMenus().add(utilisateur);
		menuBar.getMenus().add(help);
		// menuBars.setTop(menuBar);

		ouvrir.setOnAction(openChantier(tabPanePrincipal));

		enregistrer.setOnAction(saveProject(batisUserInterface.getCurrentChantier()));

		return menuBar;

	}

	/************ ACTIONS *******************/

	/**
	 * 
	 * @param tabPanePrincipale
	 * @param currentChantier2
	 * @return
	 */
	public EventHandler<ActionEvent> openChantier(TabPane tabPanePrincipale) {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				try {
					OpenChantier openProjet = new OpenChantier(tabPanePrincipale);
					openProjet.start(new Stage());

				} catch (Exception e) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Error : " + e.getCause());

					}
				}
			}
		};
	}

	/**
	 * @return
	 */
	public EventHandler<ActionEvent> close(Stage stagePrincipal) {
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				stagePrincipal.close();
			}
		};
	}

	/**
	 * @param currentChantier
	 * @return
	 */
	public EventHandler<ActionEvent> saveProject(Chantier currentChantier) {
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				LOGGER.info("save ");
				chantierService.saveOrUpdate(currentChantier);
			}
		};
	}

	/**
	 * 
	 * @return
	 */
	private EventHandler<ActionEvent> creerChantier(TabPane tabPanePrincipal, BatisUi batisUi) {

		return new EventHandler<ActionEvent>() {

			@Override
			@SuppressWarnings("unused")
			public void handle(ActionEvent event) {
				MenuItem mItem = (MenuItem) event.getSource();
				new NewChantiersEtape1(tabPanePrincipal, batisUi);
			}
		};
	}

	private EventHandler<ActionEvent> creerDevis(BatisUi batisUi) {

		return new EventHandler<ActionEvent>() {

			@Override
			@SuppressWarnings("unused")
			public void handle(ActionEvent event) {
				MenuItem mItem = (MenuItem) event.getSource();
				NewDevisWindow newDW = new NewDevisWindow(batisUi.getCurrentChantier());
				newDW.initSignatureWindow(new Stage(), batisUi.getCurrentChantier());
			}
		};
	}

	/********* FIN ACTION ********/

	/**
	 * 
	 * @param quitter
	 * @param ouvrir
	 * @param reflesh
	 * @param save
	 * @param saveAs
	 * @param nouveauMenu
	 * @return
	 */
	private Menu getMenuFichier(MenuItem quitter, MenuItem ouvrir, MenuItem reflesh, MenuItem save, MenuItem saveAs,
			Menu nouveauMenu) {

		Menu menu = new Menu("Fichier");
		menu.getItems().addAll(nouveauMenu, new SeparatorMenuItem(), ouvrir, new SeparatorMenuItem(), reflesh,
				new SeparatorMenuItem(), save, new SeparatorMenuItem(), saveAs, new SeparatorMenuItem(), quitter);
		return menu;
	}

	/**
	 * 
	 * @param creerCompte
	 * @param connexion
	 * @param deconnexion
	 * @return
	 */
	private Menu getUserMenuItem(MenuItem creerCompte, MenuItem connexion, MenuItem deconnexion) {
		Menu utilisateur = new Menu("Utilisateur");
		utilisateur.getItems().add(creerCompte);
		utilisateur.getItems().add(new SeparatorMenuItem());
		utilisateur.getItems().add(connexion);
		utilisateur.getItems().add(new SeparatorMenuItem());
		utilisateur.getItems().add(deconnexion);
		return utilisateur;
	}

	/**
	 * 
	 * @return
	 */
	private Menu getHelpMenu() {
		Menu help = new Menu("Help");
		// about
		MenuItem about = new MenuItem("Apropos de Batis");

		help.getItems().add(about);
		about.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				AboutBatisWindow about = new AboutBatisWindow(common, imageUtils);
				about.initSignatureWindow(new Stage());
			}
		});
		return help;
	}

	/**
	 * 
	 * @param openImg
	 * @param newImg
	 * @return
	 */
	private Menu getNouveauMenuItem(Image openImg, Image newImg, TabPane tabPanePrincipal, BatisUi batisUi) {
		Menu nouveauMenu = new Menu("Nouveau");
		MenuItem nouveauChantier = new MenuItem("Chantier");
		nouveauChantier.setGraphic(new ImageView(newImg));
		nouveauMenu.getItems().add(nouveauChantier);
		nouveauMenu.getItems().add(new SeparatorMenuItem());
		EventHandler<ActionEvent> createChantierAction = creerChantier(tabPanePrincipal, batisUi);
		nouveauChantier.setOnAction(createChantierAction);

		MenuItem nouveauDevis = new MenuItem("Devis");
		nouveauDevis.setGraphic(new ImageView(openImg));
		nouveauMenu.getItems().add(nouveauDevis);
		nouveauMenu.getItems().add(new SeparatorMenuItem());
		EventHandler<ActionEvent> createDevisAction = creerDevis(batisUi);
		nouveauDevis.setOnAction(createDevisAction);
		return nouveauMenu;
	}

	/**
	 * 
	 * @param exitImg
	 * @return
	 */
	private MenuItem getQuitterMenuItem(Image exitImg) {
		MenuItem quitter = new MenuItem("Quitter");
		quitter.setGraphic(new ImageView(exitImg));
		return quitter;
	}

	/**
	 * 
	 * @param openImg
	 * @return
	 */
	private MenuItem getOuvrirMenuItem(Image openImg) {
		MenuItem ouvrir = new MenuItem("Ouvrir");
		ouvrir.setGraphic(new ImageView(openImg));
		return ouvrir;
	}

	/**
	 * 
	 * @param saveImg
	 * @return
	 */
	private MenuItem getSaveAsMenuItem(Image saveImg) {
		MenuItem saveAs = new MenuItem("Enregistrer sous...");
		saveAs.setGraphic(new ImageView(saveImg));
		return saveAs;
	}

	/**
	 * 
	 * @param saveImg
	 * @return
	 */
	private MenuItem getSaveMenuItem(Image saveImg) {
		MenuItem save = new MenuItem("Enregistrer");
		save.setGraphic(new ImageView(saveImg));
		return save;
	}

	/**
	 * 
	 * @param refleshImg
	 * @return
	 */
	private MenuItem getRefleshMenuItem(Image refleshImg) {
		MenuItem reflesh = new MenuItem("Rafraîchir");
		reflesh.setGraphic(new ImageView(refleshImg));
		return reflesh;
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
	 * @return the saveImg
	 */
	public Image getSaveImg() {
		return saveImg;
	}

	/**
	 * @param saveImg the saveImg to set
	 */
	public void setSaveImg(Image saveImg) {
		this.saveImg = saveImg;
	}

	/**
	 * @return the openImg
	 */
	public Image getOpenImg() {
		return openImg;
	}

	/**
	 * @param openImg the openImg to set
	 */
	public void setOpenImg(Image openImg) {
		this.openImg = openImg;
	}

	/**
	 * @return the newImg
	 */
	public Image getNewImg() {
		return newImg;
	}

	/**
	 * @param newImg the newImg to set
	 */
	public void setNewImg(Image newImg) {
		this.newImg = newImg;
	}

	/**
	 * @return the undoImg
	 */
	public Image getUndoImg() {
		return undoImg;
	}

	/**
	 * @param undoImg the undoImg to set
	 */
	public void setUndoImg(Image undoImg) {
		this.undoImg = undoImg;
	}

	/**
	 * @return the redoImg
	 */
	public Image getRedoImg() {
		return redoImg;
	}

	/**
	 * @param redoImg the redoImg to set
	 */
	public void setRedoImg(Image redoImg) {
		this.redoImg = redoImg;
	}

	/**
	 * @return the refleshImg
	 */
	public Image getRefleshImg() {
		return refleshImg;
	}

	/**
	 * @param refleshImg the refleshImg to set
	 */
	public void setRefleshImg(Image refleshImg) {
		this.refleshImg = refleshImg;
	}

	/**
	 * @return the userImg
	 */
	public Image getUserImg() {
		return userImg;
	}

	/**
	 * @param userImg the userImg to set
	 */
	public void setUserImg(Image userImg) {
		this.userImg = userImg;
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

}

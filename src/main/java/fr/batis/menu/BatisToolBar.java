package main.java.fr.batis.menu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.java.fr.batis.BatisUi;
import main.java.fr.batis.components.OpenChantier;
import main.java.fr.batis.components.common.BatisUtils;
import main.java.fr.batis.components.common.ImageUtils;
import main.java.fr.batis.entites.LastOpened;
import main.java.fr.batis.newChantiers.NewChantiersEtape1;
import main.java.fr.batis.service.MemoryService;

public class BatisToolBar {

	private static final Logger LOGGER = LogManager.getLogger(BatisToolBar.class);
	private Image saveImg;
	private Image openImg;
	private Image newImg;
	private Image undoImg;
	private Image redoImg;
	private Image refleshImg;
	private Image userImg;
	private BatisUi batisUserInterface;
	private ImageUtils imageUtils = new ImageUtils();

	/**
	 * 
	 * @return ToolBar
	 */
	public ToolBar getToolBarsMenu(TabPane tabPanePrincipal, BatisUi batisUi) {

		batisUserInterface = batisUi;
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

		Label saveLabel = getSaveLabel(saveImg);
		Label newLabel = getNouveauLabel(newImg);
		Label open = getOpenLabel(openImg);
		Label undoLabel = getUndoLabel(undoImg);
		Label redoLabel = getRedoLabel(redoImg);
		Label refleshLabel = getRefleshLabel(refleshImg);
		Label profilLabel = getProfilLabel(userImg);

		ToolBar toolBar = new ToolBar(saveLabel, new Separator(), newLabel, new Separator(), open, new Separator(),
				undoLabel, new Separator(), redoLabel, new Separator(), refleshLabel, new Separator(), profilLabel

		);

		refleshWindow(refleshLabel, tabPanePrincipal);
		/**
		 * 
		 */
		open.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				try {

					OpenChantier openProjet = new OpenChantier(tabPanePrincipal);
					openProjet.start(new Stage());

				} catch (Exception e) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Error : " + e.getCause());
					}

				}
			}
		});

		newLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				new NewChantiersEtape1(tabPanePrincipal, batisUi);
			}
		});
		return toolBar;
	}

	/*************** ACTIONS *******************/

	/**
	 * 
	 * @param refleshLabel
	 */
	private void refleshWindow(Label refleshLabel, TabPane tabPanePrincipale) {
		refleshLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				MemoryService memoryService = new MemoryService();
				LastOpened lastOpenedChantier = memoryService.getFirst();
				BatisUtils.refleshProject(tabPanePrincipale, lastOpenedChantier.getProjectId());
			}

		});
	}

	/************* FIN ACTIONS ***************/

	/**
	 * 
	 * @param userImg
	 * @return
	 */
	private Label getProfilLabel(Image userImg) {
		Label profilLabel = new Label();
		profilLabel.setGraphic(new ImageView(userImg));
		return profilLabel;
	}

	/**
	 * 
	 * @param refleshImg
	 * @return
	 */
	private Label getRefleshLabel(Image refleshImg) {
		Label refleshLabel = new Label();
		refleshLabel.setPadding(new Insets(0, 3, 0, 3));
		refleshLabel.setGraphic(new ImageView(refleshImg));
		return refleshLabel;
	}

	/**
	 * 
	 * @param redoImg
	 * @return
	 */
	private Label getRedoLabel(Image redoImg) {
		Label redoLabel = new Label();
		redoLabel.setPadding(new Insets(0, 3, 0, 3));
		redoLabel.setGraphic(new ImageView(redoImg));
		return redoLabel;
	}

	/**
	 * 
	 * @param undoImg
	 * @return
	 */

	private Label getUndoLabel(Image undoImg) {
		Label undoLabel = new Label();
		undoLabel.setPadding(new Insets(0, 3, 0, 3));
		undoLabel.setGraphic(new ImageView(undoImg));
		return undoLabel;
	}

	/**
	 * 
	 * @param openImg
	 * @return
	 */
	private Label getOpenLabel(Image openImg) {
		Label open = new Label();
		open.setPadding(new Insets(0, 3, 0, 3));
		open.setGraphic(new ImageView(openImg));
		return open;
	}

	/**
	 * 
	 * @param newImg
	 * @return
	 */
	private Label getNouveauLabel(Image newImg) {
		Label newLabel = new Label();
		newLabel.setPadding(new Insets(0, 3, 0, 3));
		newLabel.setGraphic(new ImageView(newImg));
		return newLabel;
	}

	/**
	 * 
	 * @param saveImg
	 * @return
	 */
	private Label getSaveLabel(Image saveImg) {
		Label saveLabel = new Label();
		saveLabel.setPadding(new Insets(0, 3, 0, 3));
		saveLabel.setGraphic(new ImageView(saveImg));
		return saveLabel;
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

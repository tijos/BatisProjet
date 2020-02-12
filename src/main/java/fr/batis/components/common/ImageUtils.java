package main.java.fr.batis.components.common;

import javafx.scene.image.Image;

public class ImageUtils {
	Image newImg;
	Image nextImg;
	Image deleteImage;
	Image openImg;
	Image icon;
	Image saveImg;
	Image prevImg;
	Image undoImg;
	Image radioCheckedImg;
	Image redoImg;
	Image radioNotCheckedImg;
	Image refleshImg;
	Image cloturerImg;
	Image exitImg;
	Image startImg;
	Image userImg;
	Image closeImg;
	Image editImg;
	Image empouleVert;
	Image addImg;
	Image empouleRouge;
	Image consultImg;
	Image empouleJaune;
	Image printImg;
	Image empouleOrange;
	Image suiviTabImg;
	Image tabPilotageImg;
	Image gestStockImg;
	Image tabPlanningImg;
	Image okImg;
	Image logo;
	Image plusImg;
	Image moinsImg;
	Image cancelImg;

	public ImageUtils() {
		try {
			newImg = new Image(getClass().getResourceAsStream("/images/new.png"));
			openImg = new Image(getClass().getResourceAsStream("/images/open.png"));
			saveImg = new Image(getClass().getResourceAsStream("/images/save.png"));
			undoImg = new Image(getClass().getResourceAsStream("/images/undo.png"));
			redoImg = new Image(getClass().getResourceAsStream("/images/redo.png"));
			refleshImg = new Image(getClass().getResourceAsStream("/images/reflesh.png"));
			exitImg = new Image(getClass().getResourceAsStream("/images/exit.png"));
			userImg = new Image(getClass().getResourceAsStream("/images/user.png"));
			editImg = new Image(getClass().getResourceAsStream("/images/edit.png"));
			addImg = new Image(getClass().getResourceAsStream("/images/plus.png"));
			consultImg = new Image(getClass().getResourceAsStream("/images/consult.png"));
			printImg = new Image(getClass().getResourceAsStream("/images/print.png"));
			suiviTabImg = new Image(getClass().getResourceAsStream("/images/planning.png"));
			tabPilotageImg = new Image(getClass().getResourceAsStream("/images/pilotage.png"));
			gestStockImg = new Image(getClass().getResourceAsStream("/images/gestStock.png"));
			tabPlanningImg = new Image(getClass().getResourceAsStream("/images/planning.png"));
			okImg = new Image(getClass().getResourceAsStream("/images/ok.png"));
			plusImg = new Image(getClass().getResourceAsStream("/images/plus.png"));
			moinsImg = new Image(getClass().getResourceAsStream("/images/moins.png"));
			icon = new Image(getClass().getResourceAsStream("/images/logo/icon.png"));
			logo = new Image(getClass().getResourceAsStream("/images/logo.png"));
			cancelImg = new Image(getClass().getResourceAsStream("/images/undo.png"));
			nextImg = new Image(getClass().getResourceAsStream("/images/next.png"));
			prevImg = new Image(getClass().getResourceAsStream("/images/prev.png"));
			cloturerImg = new Image(getClass().getResourceAsStream("/images/cloturer.png"));
			startImg = new Image(getClass().getResourceAsStream("/images/start.png"));
			radioCheckedImg = new Image(getClass().getResourceAsStream("/images/radio_Ckecked.png"));
			radioNotCheckedImg = new Image(getClass().getResourceAsStream("/images/radioNot_Checked.png"));
			closeImg = new Image(getClass().getResourceAsStream("/images/exit.png"));

			empouleVert = new Image(getClass().getResourceAsStream("/images/empouleVert.png"));
			empouleRouge = new Image(getClass().getResourceAsStream("/images/empouleRouge.png"));
			empouleJaune = new Image(getClass().getResourceAsStream("/images/empouleJaune.png"));
			empouleOrange = new Image(getClass().getResourceAsStream("/images/empouleOrange.png"));
			deleteImage = new Image(getClass().getResourceAsStream("/images/delete.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}

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
	 * @return the editImg
	 */
	public Image getEditImg() {
		return editImg;
	}

	/**
	 * @param editImg the editImg to set
	 */
	public void setEditImg(Image editImg) {
		this.editImg = editImg;
	}

	/**
	 * @return the addImg
	 */
	public Image getAddImg() {
		return addImg;
	}

	/**
	 * @param addImg the addImg to set
	 */
	public void setAddImg(Image addImg) {
		this.addImg = addImg;
	}

	/**
	 * @return the consultImg
	 */
	public Image getConsultImg() {
		return consultImg;
	}

	/**
	 * @param consultImg the consultImg to set
	 */
	public void setConsultImg(Image consultImg) {
		this.consultImg = consultImg;
	}

	/**
	 * @return the printImg
	 */
	public Image getPrintImg() {
		return printImg;
	}

	/**
	 * @param printImg the printImg to set
	 */
	public void setPrintImg(Image printImg) {
		this.printImg = printImg;
	}

	/**
	 * @return the suiviTabImg
	 */
	public Image getSuiviTabImg() {
		return suiviTabImg;
	}

	/**
	 * @param suiviTabImg the suiviTabImg to set
	 */
	public void setSuiviTabImg(Image suiviTabImg) {
		this.suiviTabImg = suiviTabImg;
	}

	/**
	 * @return the tabPilotageImg
	 */
	public Image getTabPilotageImg() {
		return tabPilotageImg;
	}

	/**
	 * @param tabPilotageImg the tabPilotageImg to set
	 */
	public void setTabPilotageImg(Image tabPilotageImg) {
		this.tabPilotageImg = tabPilotageImg;
	}

	/**
	 * @return the gestStockImg
	 */
	public Image getGestStockImg() {
		return gestStockImg;
	}

	/**
	 * @param gestStockImg the gestStockImg to set
	 */
	public void setGestStockImg(Image gestStockImg) {
		this.gestStockImg = gestStockImg;
	}

	/**
	 * @return the tabPlanningImg
	 */
	public Image getTabPlanningImg() {
		return tabPlanningImg;
	}

	/**
	 * @param tabPlanningImg the tabPlanningImg to set
	 */
	public void setTabPlanningImg(Image tabPlanningImg) {
		this.tabPlanningImg = tabPlanningImg;
	}

	/**
	 * @return the okImg
	 */
	public Image getOkImg() {
		return okImg;
	}

	/**
	 * @param okImg the okImg to set
	 */
	public void setOkImg(Image okImg) {
		this.okImg = okImg;
	}

	/**
	 * @return the nextImg
	 */
	public Image getNextImg() {
		return nextImg;
	}

	/**
	 * @param nextImg the nextImg to set
	 */
	public void setNextImg(Image nextImg) {
		this.nextImg = nextImg;
	}

	/**
	 * @return the plusImg
	 */
	public Image getPlusImg() {
		return plusImg;
	}

	/**
	 * @param plusImg the plusImg to set
	 */
	public void setPlusImg(Image plusImg) {
		this.plusImg = plusImg;
	}

	/**
	 * @return the moinsImg
	 */
	public Image getMoinsImg() {
		return moinsImg;
	}

	/**
	 * @param moinsImg the moinsImg to set
	 */
	public void setMoinsImg(Image moinsImg) {
		this.moinsImg = moinsImg;
	}

	/**
	 * @return the cancelImg
	 */
	public Image getCancelImg() {
		return cancelImg;
	}

	/**
	 * @param cancelImg the cancelImg to set
	 */
	public void setCancelImg(Image cancelImg) {
		this.cancelImg = cancelImg;
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
	 * @return the prevImg
	 */
	public Image getPrevImg() {
		return prevImg;
	}

	/**
	 * @param prevImg the prevImg to set
	 */
	public void setPrevImg(Image prevImg) {
		this.prevImg = prevImg;
	}

	/**
	 * @return the radioCheckedImg
	 */
	public Image getRadioCheckedImg() {
		return radioCheckedImg;
	}

	/**
	 * @param radioCheckedImg the radioCheckedImg to set
	 */
	public void setRadioCheckedImg(Image radioCheckedImg) {
		this.radioCheckedImg = radioCheckedImg;
	}

	/**
	 * @return the radioNotCheckedImg
	 */
	public Image getRadioNotCheckedImg() {
		return radioNotCheckedImg;
	}

	/**
	 * @param radioNotCheckedImg the radioNotCheckedImg to set
	 */
	public void setRadioNotCheckedImg(Image radioNotCheckedImg) {
		this.radioNotCheckedImg = radioNotCheckedImg;
	}

	/**
	 * @return the cloturerImg
	 */
	public Image getCloturerImg() {
		return cloturerImg;
	}

	/**
	 * @param cloturerImg the cloturerImg to set
	 */
	public void setCloturerImg(Image cloturerImg) {
		this.cloturerImg = cloturerImg;
	}

	/**
	 * @return the startImg
	 */
	public Image getStartImg() {
		return startImg;
	}

	/**
	 * @param startImg the startImg to set
	 */
	public void setStartImg(Image startImg) {
		this.startImg = startImg;
	}

	/**
	 * @return the closeImg
	 */
	public Image getCloseImg() {
		return closeImg;
	}

	/**
	 * @param closeImg the closeImg to set
	 */
	public void setCloseImg(Image closeImg) {
		this.closeImg = closeImg;
	}

	/**
	 * @return the empouleVert
	 */
	public Image getEmpouleVert() {
		return empouleVert;
	}

	/**
	 * @param empouleVert the empouleVert to set
	 */
	public void setEmpouleVert(Image empouleVert) {
		this.empouleVert = empouleVert;
	}

	/**
	 * @return the empouleRouge
	 */
	public Image getEmpouleRouge() {
		return empouleRouge;
	}

	/**
	 * @param empouleRouge the empouleRouge to set
	 */
	public void setEmpouleRouge(Image empouleRouge) {
		this.empouleRouge = empouleRouge;
	}

	/**
	 * @return the empouleJaune
	 */
	public Image getEmpouleJaune() {
		return empouleJaune;
	}

	/**
	 * @param empouleJaune the empouleJaune to set
	 */
	public void setEmpouleJaune(Image empouleJaune) {
		this.empouleJaune = empouleJaune;
	}

	/**
	 * @return the empouleOrange
	 */
	public Image getEmpouleOrange() {
		return empouleOrange;
	}

	/**
	 * @param empouleOrange the empouleOrange to set
	 */
	public void setEmpouleOrange(Image empouleOrange) {
		this.empouleOrange = empouleOrange;
	}

	/**
	 * @return the deleteImage
	 */
	public Image getDeleteImage() {
		return deleteImage;
	}

	/**
	 * @param deleteImage the deleteImage to set
	 */
	public void setDeleteImage(Image deleteImage) {
		this.deleteImage = deleteImage;
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

}

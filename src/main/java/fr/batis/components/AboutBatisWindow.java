package main.java.fr.batis.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.java.fr.batis.components.common.BatisUtils;
import main.java.fr.batis.components.common.CommonUtils;
import main.java.fr.batis.components.common.ImageUtils;

public class AboutBatisWindow {

	private CommonUtils commonUtils;
	private ImageUtils imageUtils;

	public AboutBatisWindow(CommonUtils common, ImageUtils imgUtls) {
		super();
		this.commonUtils = common;
		this.imageUtils = imgUtls;
	}

	/**
	 * 
	 * @param primaryStage
	 */
	public void initSignatureWindow(Stage primaryStage) {

		Image signatureImg = new Image(getClass().getResourceAsStream("/images/signature.png"));
		Image unCheckedBoxImg = new Image(getClass().getResourceAsStream("/images/unCheckedBox.png"));

		final Node panIcon = commonUtils.getHeader("Apropos de Batis");

		BorderPane about = new BorderPane();
		GridPane aboutTmp = commonUtils.getGridPanel();

		Label aboutText = new Label("Batis est un logiciel de gestion des travaux de construction.");
		Label versionText = new Label("Vesion :  1.0 ");
		Label copyRightText = new Label("(C) Batis.Tout Droit réservé.");

		ButtonBar control = new ButtonBar();
		Button fermer = new Button("Fermer", new ImageView(getImageUtils().getCloseImg()));

		control.getButtons().add(fermer);
		control.setPadding(new Insets(10));

		aboutTmp.add(aboutText, 0, 0);
		aboutTmp.add(versionText, 0, 1);
		aboutTmp.add(copyRightText, 0, 2);

		about.setTop(panIcon);
		about.setCenter(aboutTmp);
		about.setBottom(control);

		BatisUtils.setIconImage(primaryStage);
		Scene scene = new Scene(about, 400, 250);
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

}

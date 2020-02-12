package main.java.fr.batis.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.java.fr.batis.components.common.CommonUtils;
import main.java.fr.batis.components.common.ImageUtils;
import main.java.fr.batis.components.common.BatisUtils;
import main.java.fr.batis.entites.Chantier;
import main.java.fr.batis.entites.PhaseConstruction;
import main.java.fr.batis.enumeration.StatutPhase;
import main.java.fr.batis.service.PhaseService;

public class NewPhaseWindow {

	private CommonUtils commonUtils;
	private ImageUtils imageUtils;
	private Chantier chantier;
	private PhaseService phaseService;

	public NewPhaseWindow(Chantier chantier) {
		super();
		this.commonUtils = new CommonUtils();
		this.imageUtils = new ImageUtils();
		this.chantier = chantier;
		this.phaseService = new PhaseService();

	}

	public void initSignatureWindow(Stage primaryStage, Chantier chtier) {

		// Image signatureImg = new
		// Image(getClass().getResourceAsStream("/images/signature.png"));
		// Image unCheckedBoxImg = new
		// Image(getClass().getResourceAsStream("/images/unCheckedBox.png"));

		Node panIcon = commonUtils.getHeader("Création de la nouvelle phase ");

		BorderPane panNewDevis = new BorderPane();
		GridPane panNewDevisTmp = commonUtils.getGridPanel();
		int nbrePhase = chantier.getPhasesConstruction().size() + 1;
		TextField nom = new TextField("Phase " + nbrePhase);
		nom.setPrefSize(150, 25);
		nom.setEditable(false);
		Label labelNom = new Label("Nom :");

		TextArea description = new TextArea();
		description.setPrefSize(150, 25);
		Label labelDescription = new Label("Description :");

		ButtonBar control = new ButtonBar();
		Button create = new Button("Créer", new ImageView(getImageUtils().getAddImg()));
		Button fermer = new Button("Fermer", new ImageView(getImageUtils().getCloseImg()));
		control.getButtons().add(create);
		control.getButtons().add(fermer);
		control.setPadding(new Insets(10));

		panNewDevisTmp.add(labelNom, 0, 0);
		panNewDevisTmp.add(nom, 1, 0);
		panNewDevisTmp.add(labelDescription, 0, 1);
		panNewDevisTmp.add(description, 1, 1);

		panNewDevis.setTop(panIcon);
		panNewDevis.setCenter(panNewDevisTmp);
		panNewDevis.setBottom(control);

		BatisUtils.setIconImage(primaryStage);
		Scene scene = new Scene(panNewDevis, 450, 240);
		scene.getStylesheets().add(getClass().getResource("/css/batis.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();

		create.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				String nomValue = nom.getText();
				String descriptionValue = description.getText();
				PhaseConstruction phaseCons = new PhaseConstruction();
				phaseCons.setNom(nomValue);
				phaseCons.setDescription(descriptionValue);
				phaseCons.setChantier(chantier);
				phaseCons.setCodeStatut(StatutPhase.ENPREPARATION);
				int nbrePhase = chantier.getPhasesConstruction().size();
				phaseCons.setNumero(nbrePhase + 1);
				chantier.getPhasesConstruction().add(phaseCons);

				getPhaseService().saveOrUpdate(phaseCons);
				primaryStage.close();
			}
		});
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
	 * @return the phaseService
	 */
	public PhaseService getPhaseService() {
		return phaseService;
	}

	/**
	 * @param phaseService the phaseService to set
	 */
	public void setPhaseService(PhaseService phaseService) {
		this.phaseService = phaseService;
	}

}

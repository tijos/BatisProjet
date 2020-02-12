/**
 * 
 */
package main.java.fr.batis.components;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.controlsfx.tools.Borders;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import main.java.fr.batis.components.common.BatisUtils;
import main.java.fr.batis.components.common.CommonUtils;
import main.java.fr.batis.components.common.DateConverterHelper;
import main.java.fr.batis.components.common.ImageUtils;
import main.java.fr.batis.entites.Chantier;
import main.java.fr.batis.entites.PhaseConstruction;
import main.java.fr.batis.enumeration.StatutPhase;
import main.java.fr.batis.service.PhaseService;

/**
 * @author dlj7961
 *
 */
public class PhaseWindow extends Application {

	private PhaseConstruction currentPhase;
	private TextArea descripValue;
	private DatePicker dateFinValue;
	private DatePicker dateDebutValue;
	private CommonUtils commonUtils = new CommonUtils();
	private ImageUtils imageUtils;
	private PhaseService phaseService;
	private Chantier currentChantier;
	private boolean editing;

	public PhaseWindow(PhaseConstruction phase, Chantier chantier, Boolean isEditing) {
		super();
		this.currentPhase = phase;
		this.currentChantier = chantier;
		this.editing = isEditing;
		imageUtils = new ImageUtils();
		phaseService = new PhaseService();
	}

	@Override
	public void start(Stage primaryStage) {

		CommonUtils common = new CommonUtils();
		String title = " ";
		if (getCurrentPhase() != null) {
			title = title + getCurrentPhase().getNom().toUpperCase();
		}
		Node header = common.getHeader(title);
		primaryStage.getIcons().add(imageUtils.getIcon());

		try {
			BorderPane panelPrincipal = new BorderPane();
			panelPrincipal.setTop(header);
			GridPane gridPrincipal = commonUtils.getGridPanel();
			gridPrincipal.setPadding(new Insets(10));

			// STATUT
			Label nomLabel = new Label("Statut : ");
			Label statutValue = new Label(
					getCurrentPhase().getCodeStatut() != null ? getCurrentPhase().getCodeStatut().getDescription()
							: "");

			gridPrincipal.add(nomLabel, 0, 0);
			gridPrincipal.add(statutValue, 1, 0);

			// DATE DEBUT

			Callback<DatePicker, DateCell> dayCellFactory = common.getDayCellFactory();

			// Callback<DatePicker, DateCell> dayCellFactory= common.getDayCellFactory();

			Label dateDebutLabel = new Label("Date de début prévue: ");

			dateDebutValue = DateConverterHelper.getConvetedDate();
			dateDebutValue.setDayCellFactory(dayCellFactory);

			if (currentPhase.getDateDebutPrevu() != null) {
				dateDebutValue.setValue(getLocalDate(currentPhase.getDateDebutPrevu()));
				dateDebutValue.setEditable(false);
			}

			gridPrincipal.add(dateDebutLabel, 0, 1);
			gridPrincipal.add(dateDebutValue, 1, 1);

			Label dateDebutReelLabel = new Label("Date de début éffective : ");
			Label dateDebutReelValue = new Label(getCurrentPhase().getDateDebutReel());
			gridPrincipal.add(dateDebutReelLabel, 2, 1);
			gridPrincipal.add(dateDebutReelValue, 3, 1);

			// DATE fin

			Label dateFinLabel = new Label("Date de fin prévue : ");
			dateFinValue = DateConverterHelper.getConvetedDate();
			dateFinValue.setDayCellFactory(dayCellFactory);
			gridPrincipal.add(dateFinLabel, 0, 2);
			gridPrincipal.add(dateFinValue, 1, 2);

			Label dateFinReelLabel = new Label("Date de fin éffective : ");
			Label dateFinReelValue = new Label(getCurrentPhase().getDateFinReel());

			gridPrincipal.add(dateFinReelLabel, 2, 2);
			gridPrincipal.add(dateFinReelValue, 3, 2);

			// DATE fin
			if (currentPhase.getDateFinPrevu() != null) {
				dateFinValue.setValue(getLocalDate(currentPhase.getDateFinPrevu()));
				dateFinValue.setEditable(false);
			}

			Label descripLabel = new Label("Description : ");
			descripValue = new TextArea(getCurrentPhase().getDescription());
			descripValue.setPrefSize(200, 100);
			descripValue.setEditable(false);
			// descripValue.prefWidthProperty().bind(<gridPrincipal>.prefWidthProperty());
			// descripValue.prefHeightProperty().bind(<gridPrincipal>.prefHeightProperty());
			// descripValue.resize(getWidth(), getHeight());

			gridPrincipal.add(descripLabel, 0, 3);
			gridPrincipal.add(descripValue, 1, 3, 3, 1);

			Label devisLabel = new Label("Devis : ");
			Button consulterDevis = new Button("Consulter", new ImageView(imageUtils.getConsultImg()));

			gridPrincipal.add(devisLabel, 0, 4);
			gridPrincipal.add(consulterDevis, 1, 4);

			Node wraperGridPrinc = Borders.wrap(gridPrincipal).lineBorder().color(Color.rgb(168, 211, 255))
					.outerPadding(20, 10, 10, 10)

					.title("Details " + getCurrentPhase().getNom()).buildAll();

			panelPrincipal.setCenter(wraperGridPrinc);
			Scene scene = new Scene(panelPrincipal, 620, 420);

			Button cloturerPhase = new Button("Cloturer", new ImageView(imageUtils.getCloturerImg()));
			Button demarrerPhase = new Button("Demarrer", new ImageView(imageUtils.getStartImg()));

			Button enregistrer = new Button("Enregistrer", new ImageView(imageUtils.getSaveImg()));

			Button modifierPhase = new Button("Modifier", new ImageView(imageUtils.getEditImg()));
			Button closeButton = new Button("Fermer", new ImageView(imageUtils.getCloseImg()));

			ButtonBar barButtons = new ButtonBar();
			barButtons.setPadding(new Insets(10));
			barButtons.getButtons().add(demarrerPhase);
			barButtons.getButtons().add(cloturerPhase);
			barButtons.getButtons().add(modifierPhase);
			barButtons.getButtons().add(enregistrer);
			barButtons.getButtons().add(closeButton);

			StatutPhase codeStatut = getCurrentPhase().getCodeStatut();

			if (codeStatut != null) {
				if (StatutPhase.TERMINE.getCode().equals(codeStatut.getCode())) {
					cloturerPhase.setDisable(true);
					demarrerPhase.setDisable(true);
				}

				if (StatutPhase.ENCOURS.getCode().equals(codeStatut.getCode())) {
					demarrerPhase.setDisable(true);
				}
			}

			// pas defaut la sauvgarde est desactivé
			enregistrer.setDisable(true);

			if (isEditing()) {
				descripValue.setEditable(true);
				dateDebutValue.setEditable(true);
				dateFinValue.setEditable(true);
				enregistrer.setDisable(false);
			}

//			
//			descripValue.setOnMouseExited(new EventHandler<Event>() {
//
//				@Override
//				public void handle(Event event) {
//					if(!getCurrentPhase().getDescription().equals(descripValue.getText())) {
//						enregistrer.setDisable(false);
//					}				
//				}
//			});
//			
//			dateFinValue.setOnMouseExited(new EventHandler<Event>() {
//
//				@Override
//				public void handle(Event event) {
//					if(!getCurrentPhase().getDateFinPrevu().equals(dateFinValue.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))) {
//						enregistrer.setDisable(false);
//					}				
//				}
//			});
//			
//			dateDebutValue.setOnMouseExited(new EventHandler<Event>() {
//
//				@Override
//				public void handle(Event event) {
//					if(!getCurrentPhase().getDateDebutPrevu().equals(dateDebutValue.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))) {
//						enregistrer.setDisable(false);
//					}				
//				}
//			});
//			

			Node wraperBarButton = Borders.wrap(barButtons).etchedBorder()
					// .color(Color.rgb(168,211,255))
					// .outerPadding(10, 10,10,10)
					.innerPadding(0)

					// .title("Synthèse comptabilité du "+getNomPhase())
					.buildAll();
			panelPrincipal.setBottom(wraperBarButton);

			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent event) {
					BatisUtils.chechModification(getCurrentPhase(), descripValue.getText());
					// TODO ANNULER LA FERMETURE
				}
			});
			closeButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

					if (BatisUtils.chechModification(getCurrentPhase(), descripValue.getText())) {
						primaryStage.close();
					}
				}
			});

			modifierPhase.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					descripValue.setEditable(true);
					dateDebutValue.setEditable(true);
					dateFinValue.setEditable(true);
					enregistrer.setDisable(false);
				}
			});

			demarrerPhase.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					BatisUtils.demmarerSelectedPhase(getCurrentChantier(), demarrerPhase, getCurrentPhase());
				}
			});

			/**
			 * 
			 */
			enregistrer.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					String dateDebut = null;
					String dateFin = null;
					if (dateDebutValue != null && dateDebutValue.getValue() != null) {
						dateDebut = dateDebutValue.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
						dateDebutValue.setEditable(false);
					}
					if (dateFinValue != null && dateFinValue.getValue() != null) {
						dateFin = dateFinValue.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
						dateFinValue.setEditable(false);
					}

					currentPhase.setDateDebutPrevu(dateDebut);
					currentPhase.setDateFinPrevu(dateFin);
					currentPhase.setDescription(descripValue.getText());
					phaseService.saveOrUpdate(currentPhase);

					// TODO FACTORISER
					descripValue.setEditable(false);

					// primaryStage.close();
				}
			});

			cloturerPhase.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					BatisUtils.cloturerPhase(getCurrentPhase());
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final LocalDate getLocalDate(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate localDate = LocalDate.parse(dateString, formatter);
		return localDate;
	}

	/**
	 * @return the currentPhase
	 */
	public PhaseConstruction getCurrentPhase() {
		return currentPhase;
	}

	/**
	 * @param currentPhase the currentPhase to set
	 */
	public void setCurrentPhase(PhaseConstruction currentPhase) {
		this.currentPhase = currentPhase;
	}

	/**
	 * @return the descripValue
	 */
	public TextArea getDescripValue() {
		return descripValue;
	}

	/**
	 * @param descripValue the descripValue to set
	 */
	public void setDescripValue(TextArea descripValue) {
		this.descripValue = descripValue;
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
	 * @return the dateFinValue
	 */
	public DatePicker getDateFinValue() {
		return dateFinValue;
	}

	/**
	 * @param dateFinValue the dateFinValue to set
	 */
	public void setDateFinValue(DatePicker dateFinValue) {
		this.dateFinValue = dateFinValue;
	}

	/**
	 * @return the dateDebutValue
	 */
	public DatePicker getDateDebutValue() {
		return dateDebutValue;
	}

	/**
	 * @param dateDebutValue the dateDebutValue to set
	 */
	public void setDateDebutValue(DatePicker dateDebutValue) {
		this.dateDebutValue = dateDebutValue;
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

	public boolean isEditing() {
		return editing;
	}

	public void setEditing(boolean isEditing) {
		this.editing = isEditing;
	}

}

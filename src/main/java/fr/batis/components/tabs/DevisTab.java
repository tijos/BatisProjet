package main.java.fr.batis.components.tabs;

import java.util.ArrayList;
import java.util.Optional;

import org.controlsfx.tools.Borders;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.java.fr.batis.components.AjoutMaterielWindow;
import main.java.fr.batis.components.SignatureWindow;
import main.java.fr.batis.components.common.BatisUtils;
import main.java.fr.batis.components.common.CommonUtils;
import main.java.fr.batis.components.common.ImageUtils;
import main.java.fr.batis.components.custom.CustomLabel;
import main.java.fr.batis.components.tables.DevisMaterielTable;
import main.java.fr.batis.entites.Chantier;
import main.java.fr.batis.entites.Devis;
import main.java.fr.batis.entites.Employe;
import main.java.fr.batis.entites.Materiel;
import main.java.fr.batis.entites.PhaseConstruction;
import main.java.fr.batis.impression.devis.GenerateDevisDocument;
import main.java.fr.batis.service.DevisService;
import main.java.fr.batis.service.ParametresService;

public class DevisTab {

	private CommonUtils commonUtils;
	private ComboBox<String> listePhases;
	private TableView<Materiel> devisTable;
	private ObservableList<Materiel> devisTableData;
	private ImageUtils imageUtils;
	private ComboBox<String> listeDevisForPhase;
	private DevisService devisService;
	private PhaseConstruction selectedPhase;
	private Devis selectedDevis;
	private ParametresService parametresService;

	public DevisTab() {
		this.commonUtils = new CommonUtils();
		this.imageUtils = new ImageUtils();
		devisService = new DevisService();
		parametresService = new ParametresService();
	}

	/**
	 * 
	 * @param chantier
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Tab getDevisTab(Chantier chantier) {
		Image devisImg = new Image(getClass().getResourceAsStream("/images/devis.png"));
		Image signatureImg = new Image(getClass().getResourceAsStream("/images/signature.png"));
		Tab devisTab = new Tab("Devis");
		devisTab.setGraphic(new ImageView(devisImg));

		BorderPane devisContent = new BorderPane();

		String title = "Liste des materiaux";
		Node panHead = commonUtils.getHeader(title);
		BorderPane devisContentTop = new BorderPane();

		devisContentTop.setPadding(new Insets(0, 10, 10, 10));

		Label selectedPhaseLabel = new Label("Veuillez séléctionnez la phase :");
		Label selectedDevisLabel = new Label("Veuillez séléctionnez le devis :");

		listeDevisForPhase = new ComboBox<String>();
		GridPane contentMatPane = getCommonUtils().getGridPanel();
		listePhases = new ComboBox<String>();
		listePhases.getItems().add("");
		for (PhaseConstruction phase : chantier.getPhasesConstruction()) {
			listePhases.getItems().add(phase.getNom());
		}

		contentMatPane.add(selectedPhaseLabel, 0, 0);
		contentMatPane.add(listePhases, 1, 0);
		contentMatPane.add(selectedDevisLabel, 4, 0);
		contentMatPane.add(listeDevisForPhase, 5, 0);

		devisContentTop.setTop(panHead);
		devisContentTop.setCenter(contentMatPane);
		devisContent.setTop(devisContentTop);

		devisTable = new DevisMaterielTable().getTableauMateriel();
		devisTable.setPrefSize(900, 400);
		devisTable.setEditable(true);

		devisTableData = FXCollections.observableArrayList(new ArrayList<Materiel>());

		devisTable.setEditable(true);

		devisTable.setItems(devisTableData);

		final HBox leftPane = new HBox();

		leftPane.setPadding(new Insets(10, 0, 0, 10));
		leftPane.getChildren().add(devisTable);
		devisContent.setCenter(leftPane);

		BorderPane resume = new BorderPane();
		resume.setPrefSize(350, 200);
		Label total = new Label("Total : ");
		Label mainOeuvre = new Label("Main d'oeuvre : ");

		GridPane contentResumePane = getCommonUtils().getGridPanel();
		contentResumePane.add(total, 0, 0);
		contentResumePane.add(mainOeuvre, 0, 1);
		resume.setCenter(contentResumePane);
		Node wraperResume = Borders.wrap(resume).lineBorder().color(Color.rgb(168, 211, 255)).outerPadding(10, 5, 5, 5)
				.innerPadding(5).title("Résumé").buildAll();
		devisContent.setRight(wraperResume);

		final GridPane buttonsBar = getCommonUtils().getGridPanel();

		Button modifier = new Button("Modifier");
		modifier.setGraphic(new ImageView(imageUtils.getEditImg()));

		Button enregistrer = new Button("Enregistrer");
		enregistrer.setGraphic(new ImageView(imageUtils.getSaveImg()));

		Button ajouter = new Button("Ajouter un matériel");
		ajouter.setGraphic(new ImageView(imageUtils.getAddImg()));

		Button delete = new Button("supprimer");
		delete.setGraphic(new ImageView(imageUtils.getDeleteImage()));
		delete.setDisable(true);
		devisTable.getSelectionModel().selectedItemProperty().addListener(new RowSelectChangeListener(delete));

		Button signer = new Button("Signer le dévis");
		signer.setGraphic(new ImageView(signatureImg));

		Button imprimer = new Button("Imprimer");
		imprimer.setGraphic(new ImageView(imageUtils.getPrintImg()));

		buttonsBar.add(modifier, 2, 2);
		buttonsBar.add(enregistrer, 4, 2);
		buttonsBar.add(ajouter, 6, 2);
		buttonsBar.add(delete, 8, 2);
		buttonsBar.add(signer, 10, 2);
		buttonsBar.add(imprimer, 12, 2);

		devisContent.setBottom(buttonsBar);
		devisTab.setContent(devisContent);
		// TODO VERIFIER SI LES CHAMPS SONT SELECTIONNES

//		ajouter.setOnAction(BatisUtils.validatateIfPhaseIsSelected(listePhases, " la phase "));
//		ajouter.setOnAction(BatisUtils.validatateIfPhaseIsSelected(listeDevisForPhase, " le devis "));
//		signer.setOnAction(BatisUtils.validatateIfPhaseIsSelected(listePhases, " la phase "));

		if (getSelectedDevis() != null && getSelectedDevis().getSignature() != null) {
			signer.setDisable(true);
		}

		delete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				// get Selected Item
				// Integer selectdIndex = getTableRow().getIndex();
				Materiel currentMat = devisTable.getSelectionModel().getSelectedItem();
				if (currentMat != null) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Suppresion du matériel");
					// alert.set setIcons().add(icon);
					String s = "Voulez-vous supprimer " + currentMat.getNom() + " de la liste?";
					alert.setContentText(s);
					alert.setResizable(true);
					alert.setWidth(300);
					Optional<ButtonType> result = alert.showAndWait();

					if ((result.isPresent()) && (result.get() == ButtonType.OK)) {

						devisTable.getItems().remove(currentMat);
						devisTableData.remove(currentMat);
						delete.setDisable(true);
					}
				}
				delete.setDisable(true);
			}
		});
		listePhases.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				PhaseConstruction selectedPhase = BatisUtils.getPhaseByName(chantier, newValue);
				if (selectedPhase != null) {
					setSelectedPhase(selectedPhase);
					devisTableData.clear();
					contentResumePane.getChildren().removeAll();
					// contentResumePane.add(new Label(""),1,1);
					listeDevisForPhase.getItems().clear();
					listeDevisForPhase.getItems().add("");
					for (Devis devis : selectedPhase.getListDevis()) {
						listeDevisForPhase.getItems().add(devis.getNom());
					}

				}
			}

		});

		listeDevisForPhase.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				devisTableData.clear();

				Devis devis = getDevisService().getDevisByNameAndPhase(
						listeDevisForPhase.getSelectionModel().getSelectedItem(), getSelectedPhase());
				contentResumePane.getChildren().clear();
				if (devis != null) {
					setSelectedDevis(devis);
					Double sommeTotAchat = BatisUtils.getSommeTotalPrevuDevis(devis, false);
					String sommeTot = BatisUtils.getNumberFormatter(sommeTotAchat);
					Double mo = BatisUtils.getMainOeuvreDevis(sommeTotAchat, devis);
					String mainOeuvreValue = BatisUtils.getNumberFormatter(mo);
					String sommeTotale = BatisUtils.getNumberFormatter(sommeTotAchat + mo);

					Label totalHMo = new Label("Total Hors Mo: ");
					Label mainOeuvre = new Label("Main d'oeuvre (Mo) : ");
					Label pourcentageMo = new Label("Poucentage du main d'oeuvre : ");
					Label total = new Label("Total : ");

					Double pourcentageValue = devis.getPourcentageMO();
					String pourcentage = "";
					if (pourcentageValue != null) {
						pourcentage = String.valueOf(pourcentageValue);
					}
					Label sommeTotValue = new CustomLabel();
					sommeTotValue.setText(sommeTot);
					Label mainOeuvreLabel = new CustomLabel();
					mainOeuvreLabel.setText(mainOeuvreValue);

					contentResumePane.add(totalHMo, 0, 0);
					contentResumePane.add(sommeTotValue, 1, 0);
					String devise = "";
					if (devis.getListMateriaux() != null && !devis.getListMateriaux().isEmpty()) {
						devise = devis.getListMateriaux().get(0).getDevise().toString();
						contentResumePane.add(new CustomLabel(devise), 2, 0);
					}

					contentResumePane.add(mainOeuvre, 0, 1);
					contentResumePane.add(mainOeuvreLabel, 1, 1);
					contentResumePane.add(new CustomLabel(devise), 2, 1);

					contentResumePane.add(pourcentageMo, 0, 2);
					contentResumePane.add(new CustomLabel(pourcentage + " %"), 1, 2);

					contentResumePane.add(total, 0, 3);
					contentResumePane.add(new CustomLabel(sommeTotale), 1, 3);
					contentResumePane.add(new CustomLabel(devise), 2, 3);

					devisTableData.addAll(BatisUtils.getMateriauxDevisInitial(devis));
					Label signature = new Label("Signé", new ImageView(imageUtils.getOkImg()));

					signature.setVisible(false);
					if (!isSigned(devis)) {

						// ajouter.setOnAction(openAjoutMaterielWindow(devisTableData, devis));
						signer.setDisable(false);
						// signer.setVisible(false);
						// signature.setVisible(true);
						// buttonsBar.add(signature, 8, 2);
					} else {

						signer.setDisable(true);
						// signature.setVisible(false);

					}
					signer.setOnAction(openSignatureWidow(devis));
				}

			}

		});
		// signer.setOnAction(openSignatureWidow(chantier, getSelectedDevis()));
		ajouter.setOnAction(openAjoutMaterielWindow(devisTableData, getSelectedDevis()));

		/**
		 * 
		 */
		imprimer.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				if (getSelectedDevis() != null && getSelectedDevis().getSignature() == null) {
					Image icon = new Image(getClass().getResourceAsStream("/images/logo/icon.png"));
					Image logo = new Image(getClass().getResourceAsStream("/images/logo.png"));
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle(" ");
					alert.setHeaderText(" Impression du devis " + getSelectedDevis().getNom());
					String s = "Voulez-vous imprimer un devis non signé?";
					alert.setGraphic(new ImageView(logo));
					Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
					stage.getIcons().add(icon);
					alert.setContentText(s);
					alert.setResizable(true);
					alert.getDialogPane().setPrefSize(400, 200);

					alert.getDialogPane().getStylesheets()
							.add(getClass().getResource("/css/dialogue.css").toExternalForm());
					alert.getDialogPane().getStyleClass().add("myDialog");

					Optional<ButtonType> result = alert.showAndWait();

					if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
						Devis devis = getSelectedDevis();
						GenerateDevisDocument gdd = new GenerateDevisDocument(devis);
						gdd.generate(devis);
					}
				} else if (getSelectedDevis() == null) {
					BatisUtils.showAlert(Alert.AlertType.ERROR, null, "Erreur!", "Veuillez séléctionner le devis !");
				} else {
					new GenerateDevisDocument(getSelectedDevis());
				}

			}
		});
		enregistrer.setOnAction(BatisUtils.saveListMateriel(devisTableData));

		return devisTab;
	}

	private boolean isSigned(Devis devis) {
		return devis != null && devis.getSignature() != null && devis.getSignature().isLuEtAccepte();
	}

	/**
	 * 
	 * @param materielTableData2
	 * @param slsctedPhase
	 * @return
	 */

	public EventHandler<ActionEvent> openAjoutMaterielWindow(ObservableList<Materiel> materielTableData2, Devis devis) {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (listePhases.getSelectionModel().getSelectedItem() == null) {
					BatisUtils.showAlert(Alert.AlertType.ERROR, null, "Erreur!", "Veuillez séléctionner la phase !");
					listePhases.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID,
							new CornerRadii(1.0), BorderStroke.THIN)));

					return;

				} else if (listeDevisForPhase.getSelectionModel().getSelectedItem() == null) {
					BatisUtils.showAlert(Alert.AlertType.ERROR, null, "Erreur!", "Veuillez séléctionner le devis !");
					listePhases.setBorder(Border.EMPTY);
					listeDevisForPhase.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID,
							new CornerRadii(1.0), BorderStroke.THIN)));
					return;
				} else {
					listePhases.setBorder(Border.EMPTY);
					listeDevisForPhase.setBorder(Border.EMPTY);
					AjoutMaterielWindow ajoutMat = new AjoutMaterielWindow(materielTableData2, getSelectedDevis(),
							false);

					try {
						ajoutMat.initComponent(new Stage(), materielTableData2, getSelectedDevis(), false);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		};
	}

	/**
	 * 
	 * @param chantier
	 * @param phase
	 * @return
	 */
	private EventHandler<ActionEvent> openSignatureWidow(Devis devis) {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				Employe responsable = devis.getReponsable();
				if (responsable == null) {
					BatisUtils.showAlert(Alert.AlertType.ERROR, null, "Erreur!",
							"Veuillez d'abord renseigner le responsable !");
				} else if (devis.getListMateriaux().isEmpty()) {
					BatisUtils.showAlert(Alert.AlertType.ERROR, null, "Erreur!",
							"Vous ne puvez pas signer un devis vide !");
				} else if (isSigned(devis)) {
					BatisUtils.showAlert(Alert.AlertType.ERROR, null, "Erreur!", "Devi déjà signé!");
				}

				else {
					new SignatureWindow(devis);
				}
			}
		};
	}

	@SuppressWarnings("rawtypes")
	private class RowSelectChangeListener implements ChangeListener {

		private Button deleteButton;

		public RowSelectChangeListener(Button cellButton) {
			this.deleteButton = cellButton;
		}

		@Override
		public void changed(ObservableValue ov, Object oldVal, Object newVal) {
			getDeleteButton().setDisable(false);
		}

		/**
		 * @return the deleteButton
		 */

		public Button getDeleteButton() {
			return deleteButton;
		}

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
	 * @return the listePhasesForDevis
	 */
	public ComboBox<String> getListePhasesForDevis() {
		return listePhases;
	}

	/**
	 * @param listePhasesForDevis the listePhasesForDevis to set
	 */
	public void setListePhasesForDevis(ComboBox<String> listePhasesForDevis) {
		this.listePhases = listePhasesForDevis;
	}

	/**
	 * @return the devisTable
	 */
	public TableView<Materiel> getDevisTable() {
		return devisTable;
	}

	/**
	 * @param devisTable the devisTable to set
	 */
	public void setDevisTable(TableView<Materiel> devisTable) {
		this.devisTable = devisTable;
	}

	/**
	 * @return the devisTableData
	 */
	public ObservableList<Materiel> getDevisTableData() {
		return devisTableData;
	}

	/**
	 * @param devisTableData the devisTableData to set
	 */
	public void setDevisTableData(ObservableList<Materiel> devisTableData) {
		this.devisTableData = devisTableData;
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
	 * @return the listePhases
	 */
	public ComboBox<String> getListePhases() {
		return listePhases;
	}

	/**
	 * @param listePhases the listePhases to set
	 */
	public void setListePhases(ComboBox<String> listePhases) {
		this.listePhases = listePhases;
	}

	/**
	 * @return the listeDevisForPhase
	 */
	public ComboBox<String> getListeDevisForPhase() {
		return listeDevisForPhase;
	}

	/**
	 * @param listeDevisForPhase the listeDevisForPhase to set
	 */
	public void setListeDevisForPhase(ComboBox<String> listeDevisForPhase) {
		this.listeDevisForPhase = listeDevisForPhase;
	}

	/**
	 * @return the devisService
	 */
	public DevisService getDevisService() {
		return devisService;
	}

	/**
	 * @param devisService the devisService to set
	 */
	public void setDevisService(DevisService devisService) {
		this.devisService = devisService;
	}

	/**
	 * @return the selectedPhase
	 */
	public PhaseConstruction getSelectedPhase() {
		return selectedPhase;
	}

	/**
	 * @param selectedPhase the selectedPhase to set
	 */
	public void setSelectedPhase(PhaseConstruction selectedPhase) {
		this.selectedPhase = selectedPhase;
	}

	/**
	 * @return the selectedDevis
	 */
	public Devis getSelectedDevis() {
		return selectedDevis;
	}

	/**
	 * @param selectedDevis the selectedDevis to set
	 */
	public void setSelectedDevis(Devis selectedDevis) {
		this.selectedDevis = selectedDevis;
	}

	/**
	 * @return the parametresService
	 */
	public ParametresService getParametresService() {
		return parametresService;
	}

	/**
	 * @param parametresService the parametresService to set
	 */
	public void setParametresService(ParametresService parametresService) {
		this.parametresService = parametresService;
	}

}

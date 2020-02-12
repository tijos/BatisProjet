package main.java.fr.batis.components;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Set;

import org.controlsfx.tools.Borders;
import org.jdatepicker.impl.JDatePickerImpl;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.java.fr.batis.components.common.BatisUtils;
import main.java.fr.batis.components.common.CommonUtils;
import main.java.fr.batis.components.common.DateConverterHelper;
import main.java.fr.batis.components.custom.DoubleTextField;
import main.java.fr.batis.entites.Beneficiaire;
import main.java.fr.batis.entites.Chantier;
import main.java.fr.batis.entites.Devis;
import main.java.fr.batis.entites.Employe;
import main.java.fr.batis.entites.PhaseConstruction;
import main.java.fr.batis.entites.RetraitDepotFond;
import main.java.fr.batis.enumeration.MotifRetrait;
import main.java.fr.batis.enumeration.TypeBeneficiaire;
import main.java.fr.batis.enumeration.TypeRetraitDepot;
import main.java.fr.batis.service.DevisService;

public class EncaissementDecaissementWindow {

	private TextField nom;
	private TextField prenom;
	private ComboBox<String> devise;
	private TextArea adresse;
	private DoubleTextField montant;
	private ComboBox<String> typeOperation;
	private ComboBox<String> motifOperation;
	private TextField telephone;
	private JDatePickerImpl date;
	private ComboBox<String> typeBeneficiaire;
	private Chantier chantier;
	private Employe gerant;
	private Beneficiaire beneficiaire;
	private Label phaseLabel;
	private RetraitDepotFond retraitDepot;
	private Set<RetraitDepotFond> operationsList;
	private ComboBox<String> listePhases;
	private Label devisLabel;
	private CommonUtils commonUtils;
	private DatePicker dateDuJour;
	private ComboBox<String> listeDevisForPhase;
	private TextField nomComptable;
	private TextField prenomComptable;
	private TextField fonction;
	private ObservableList<RetraitDepotFond> retraitDepotFondData;
	private Employe comptable;
	private DevisService devisService;
	private PhaseConstruction selectedPhase;
	private Devis selectedDevis;

	public EncaissementDecaissementWindow() {
		super();
		this.commonUtils = new CommonUtils();
		this.listeDevisForPhase = new ComboBox<String>();
		this.listePhases = new ComboBox<String>();
		this.devisService = new DevisService();
		initComponent(new Stage());

	}

	public EncaissementDecaissementWindow(Chantier chantier, ObservableList<RetraitDepotFond> data) {

		this.commonUtils = new CommonUtils();
		this.listeDevisForPhase = new ComboBox<String>();
		this.listePhases = new ComboBox<String>();
		this.retraitDepotFondData = data;
		this.chantier = chantier;
		this.devisService = new DevisService();
		initComponent(new Stage());
	}

	public void initComponent(Stage primaryStage) {

		Image okImg = new Image(getClass().getResourceAsStream("/images/ok.png"));
		Image cancelImg = new Image(getClass().getResourceAsStream("/images/undo.png"));
		Image closeImg = new Image(getClass().getResourceAsStream("/images/exit.png"));

		NumberFormat doubleFormat = NumberFormat.getNumberInstance(Locale.FRANCE);
		doubleFormat.setMaximumFractionDigits(2);

		BorderPane gnrlPane = new BorderPane();
		BorderPane contentRetraitDepot = new BorderPane();
		GridPane contentRetraitDepotTmp = commonUtils.getGridPanel();

		Employe comptable = BatisUtils.getChefComptable(chantier);
		setComptable(comptable);
		Node panIcon = commonUtils.getHeader("Encaissement / décaissement");

		Label dateLabel = new Label("Date :");
		dateDuJour = DateConverterHelper.getConvetedDate();

		Callback<DatePicker, DateCell> dayCellFactory = commonUtils.getDayCellFactory();
		dateDuJour.setDayCellFactory(dayCellFactory);

		nom = new TextField();
		Label labelNom = new Label("Nom :");

		// PRENOM
		prenom = new TextField();
		Label labelPrenom = new Label("Prenom :");

		// telephone
		telephone = new TextField();
		Label labelTelephone = new Label("Téléphone :");

		// type
		Label typeOpLabel = new Label("Type opération :");
		typeOperation = new ComboBox<String>();
		typeOperation.getItems().add("");
		for (TypeRetraitDepot rt : TypeRetraitDepot.values()) {
			typeOperation.getItems().add(rt.getDescription());
		}

		Label beneficiaireLabel = new Label("Bénéficiaire :");

		typeBeneficiaire = new ComboBox<String>();
		typeBeneficiaire.getItems().add("");
		for (TypeBeneficiaire tb : TypeBeneficiaire.values()) {
			typeBeneficiaire.getItems().add(tb.getDescription());
		}

		Label montantLabel = new Label("Montant :");

		montant = new DoubleTextField();
		Label deviseMotif = new Label("Devise :");
		devise = new ComboBox<String>();
		devise.getItems().add("Fbu");
		devise.getItems().add("€");
		devise.getItems().add("$");

		Label labelMotif = new Label("Motif :");

		motifOperation = new ComboBox<String>();
		motifOperation.getItems().add("");
		for (MotifRetrait motifValue : MotifRetrait.values()) {
			motifOperation.getItems().add(motifValue.getDescription());
		}

		Label nomCptableLabel = new Label("Nom :");
		nomComptable = new TextField(comptable != null ? comptable.getNom() : "");
		// if(comptable != null && comptable.getNom() != null) {
		nomComptable.setEditable(false);
		// }

		Label preNomCptableLabel = new Label("Prenom :");
		prenomComptable = new TextField(comptable != null ? comptable.getPrenom() : "");

		// if(comptable != null && comptable.getPrenom() != null) {
		prenomComptable.setEditable(false);
		// }

		Label fonctionLabel = new Label("Fonction :");
		// TODO
		fonction = new TextField(comptable != null ? "TODO" : "");

		if (comptable != null) {
			fonction.setEditable(false);
		}

		GridPane comptablePaneTmp = commonUtils.getGridPanel();

		comptablePaneTmp.add(nomCptableLabel, 0, 0);
		comptablePaneTmp.add(nomComptable, 1, 0);
		comptablePaneTmp.add(preNomCptableLabel, 5, 0);
		comptablePaneTmp.add(prenomComptable, 6, 0);
		comptablePaneTmp.add(fonctionLabel, 10, 0);
		comptablePaneTmp.add(fonction, 11, 0);

		// save

		Button ajouter = new Button("Ajouter", new ImageView(okImg));
		Button cancel = new Button("Annuler", new ImageView(cancelImg));
		Button fermer = new Button("Fermer", new ImageView(closeImg));
		Button ajouterEmploye = new Button("Ajouter un Comptable", new ImageView(okImg));

		ButtonBar control = new ButtonBar();
		control.getButtons().add(ajouter);
		control.getButtons().add(ajouterEmploye);
		control.getButtons().add(cancel);
		control.getButtons().add(fermer);
		control.setPadding(new Insets(10));

		// DATE
		contentRetraitDepotTmp.add(dateLabel, 0, 0);
		contentRetraitDepotTmp.add(dateDuJour, 1, 0);

		contentRetraitDepotTmp.add(typeOpLabel, 2, 0);
		contentRetraitDepotTmp.add(typeOperation, 3, 0);

		contentRetraitDepotTmp.add(beneficiaireLabel, 4, 0);
		contentRetraitDepotTmp.add(typeBeneficiaire, 5, 0);

		contentRetraitDepotTmp.add(labelMotif, 0, 1);
		contentRetraitDepotTmp.add(motifOperation, 1, 1);

		contentRetraitDepotTmp.add(labelNom, 0, 2);
		contentRetraitDepotTmp.add(nom, 1, 2);

		contentRetraitDepotTmp.add(labelPrenom, 2, 2);
		contentRetraitDepotTmp.add(prenom, 3, 2);

		contentRetraitDepotTmp.add(labelTelephone, 4, 2);
		contentRetraitDepotTmp.add(telephone, 5, 2);

		contentRetraitDepotTmp.add(montantLabel, 0, 3);
		contentRetraitDepotTmp.add(montant, 1, 3);

		contentRetraitDepotTmp.add(deviseMotif, 2, 3);
		contentRetraitDepotTmp.add(devise, 3, 3);

		Node wraperContentRetraitDepotTmp = Borders.wrap(contentRetraitDepotTmp)

				.lineBorder().color(Color.rgb(168, 211, 255)).outerPadding(20, 10, 10, 10)
				.title("Opération de retrait/dépot").buildAll();

		Node wraperComptablePaneTmp = Borders.wrap(comptablePaneTmp)

				.lineBorder().color(Color.rgb(168, 211, 255)).outerPadding(20, 10, 10, 10).title("Comptable")
				.buildAll();

		ajouter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				retraitDepotFondData.add(populateTable());
				primaryStage.close();
			}
		});

		typeOperation.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (getTypeOperation().getSelectionModel() != null) {
					String typeOp = getTypeOperation().getSelectionModel().getSelectedItem().toString();
					if (typeOp.equals(TypeRetraitDepot.DEPOT.getDescription())) {
						motifOperation.setValue("");
						typeBeneficiaire.setDisable(true);
						nom.setDisable(true);
						prenom.setDisable(true);
						telephone.setDisable(true);

						contentRetraitDepotTmp.getChildren().removeAll(phaseLabel, listePhases, devisLabel,
								listeDevisForPhase);
					} else {
						motifOperation.setDisable(false);
						typeBeneficiaire.setDisable(false);
						nom.setDisable(false);
						prenom.setDisable(false);
						telephone.setDisable(false);
					}
				}

			}
		});
		motifOperation.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (getMotifOperation().getSelectionModel() != null) {
					String motif = getMotifOperation().getSelectionModel().getSelectedItem().toString();
					if (motif.equals(MotifRetrait.MAINOEUVRE.getDescription())
							|| motif.equals(MotifRetrait.ACHATMAT.getDescription())) {
						phaseLabel = new Label("Phase :");

						listeDevisForPhase = new ComboBox<String>();

						// listePhases = new ComboBox<String>();
						// listePhases.getItems().add("");
						listePhases.getItems().clear();
						listePhases.getItems().add("");
						for (PhaseConstruction phase : chantier.getPhasesConstruction()) {
							listePhases.getItems().add(phase.getNom());
						}

						devisLabel = new Label("Devis :");
						listePhases.valueProperty().addListener(new ChangeListener<String>() {

							@Override
							public void changed(ObservableValue<? extends String> observable, String oldValue,
									String newValue) {

								selectedPhase = BatisUtils.getPhaseByName(chantier, newValue);
								setSelectedPhase(selectedPhase);
								if (selectedPhase != null) {
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
							public void changed(ObservableValue<? extends String> observable, String oldValue,
									String newValue) {

								Devis devis = getDevisService().getDevisByNameAndPhase(
										listeDevisForPhase.getSelectionModel().getSelectedItem(), getSelectedPhase());
								if (devis != null) {
									nom.setText(devis.getReponsable().getNom());
									prenom.setText(devis.getReponsable().getPrenom());
									telephone.setText(devis.getReponsable().getTelephone());
									setSelectedDevis(devis);
								} else {
									nom.setText("");
									prenom.setText("");
									telephone.setText("");
								}
							}

						});

						contentRetraitDepotTmp.add(phaseLabel, 2, 1);
						contentRetraitDepotTmp.add(listePhases, 3, 1);
						contentRetraitDepotTmp.add(devisLabel, 4, 1);
						contentRetraitDepotTmp.add(listeDevisForPhase, 5, 1);
					} else {
						contentRetraitDepotTmp.getChildren().removeAll(phaseLabel, listePhases, devisLabel,
								listeDevisForPhase);
					}
				}
			}
		});
		ajouterEmploye.setOnAction(BatisUtils.openAjoutEmployeWindow(chantier, null));

		/**
		 * 
		 */
		fermer.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				primaryStage.close();
			}
		});

		gnrlPane.setCenter(wraperContentRetraitDepotTmp);
		gnrlPane.setBottom(wraperComptablePaneTmp);

		contentRetraitDepot.setTop(panIcon);
		contentRetraitDepot.setCenter(gnrlPane);
		contentRetraitDepot.setBottom(control);

		BatisUtils.setIconImage(primaryStage);
		Scene scene = new Scene(contentRetraitDepot, 880, 500);
		scene.getStylesheets().add(getClass().getResource("/css/batis.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public RetraitDepotFond populateTable() {
		RetraitDepotFond rd = new RetraitDepotFond();
		String dateValue = "";
		if (getDateDuJour().getValue() != null) {
			dateValue = getDateDuJour().getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		}
		String nomValue = getNom().getText();
		String prenomValue = getPrenom().getText();
		String motifValue = "";
		if (getMotifOperation().getSelectionModel() != null) {
			motifValue = getMotifOperation().getSelectionModel().getSelectedItem().toString();
		}

		String montantValue = getMontant().getText();
		// String _adresse = getAdresse().getText();
		String deviseValue = getDevise().getSelectionModel().getSelectedItem().toString();
		String typeOpValue = getTypeOperation().getSelectionModel().getSelectedItem().toString();
		montantValue = BatisUtils.deleteWhiteSpace(montantValue);

		Double montant = Double.valueOf(montantValue);
		montantValue = BatisUtils.getNumberFormatter(montant);
		String telValue = getTelephone().getText();

		String typeBenValue = " - ";
		if (TypeRetraitDepot.RETRAIT.getDescription().equals(typeOpValue)
				&& getTypeBeneficiaire().getSelectionModel().getSelectedItem() != null
				&& !getTypeBeneficiaire().getSelectionModel().getSelectedItem().toString().isEmpty()) {
			typeBenValue = getTypeBeneficiaire().getSelectionModel().getSelectedItem().toString();
		}

		if (TypeRetraitDepot.RETRAIT.getDescription().equals(typeOpValue)) {
			beneficiaire = new Beneficiaire(nomValue, prenomValue, telValue, typeBenValue);
			rd.setBeneficiaire(beneficiaire);

		}

		if (listePhases.getSelectionModel().getSelectedItem() != null
				&& listeDevisForPhase.getSelectionModel().getSelectedItem() != null) {

			Devis devis = getSelectedDevis();

			if (MotifRetrait.ACHATMAT.getDescription()
					.equals(getMotifOperation().getSelectionModel().getSelectedItem().toString())) {
				Double moPaye = devis.getMainOeuvrePaye() != null ? devis.getMainOeuvrePaye() + montant : montant;
				devis.setMainOeuvrePaye(moPaye);
			}

			beneficiaire = new Beneficiaire();
			beneficiaire.setNom(devis.getReponsable().getNom());
			beneficiaire.setPrenom(devis.getReponsable().getPrenom());
			beneficiaire.setTelephone(devis.getReponsable().getTelephone());
			rd.setBeneficiaire(beneficiaire);
			getDevisService().saveOrUpdate(devis);
		}

		// Employe gerant = rd.getComptableEntity();

		rd.setMotif(motifValue);
		rd.setMontant(montant);
		rd.setComptable(getComptable());
		rd.setDevise(deviseValue);
		rd.setType(typeOpValue);
		rd.setDateOperation(dateValue);
		rd.setTypeBeneficiaire(typeBenValue);
		rd.setChantier(chantier);

		// BatisUtils.addEditingLabels(data);

		return rd;
	}

	/**
	 * @return the nom
	 */
	public TextField getNom() {
		return nom;
	}

	/**
	 * @param nom the nom to set
	 */
	public void setNom(TextField nom) {
		this.nom = nom;
	}

	/**
	 * @return the prenom
	 */
	public TextField getPrenom() {
		return prenom;
	}

	/**
	 * @param prenom the prenom to set
	 */
	public void setPrenom(TextField prenom) {
		this.prenom = prenom;
	}

	/**
	 * @return the devise
	 */
	public ComboBox<String> getDevise() {
		return devise;
	}

	/**
	 * @param devise the devise to set
	 */
	public void setDevise(ComboBox<String> devise) {
		this.devise = devise;
	}

	/**
	 * @return the adresse
	 */
	public TextArea getAdresse() {
		return adresse;
	}

	/**
	 * @param adresse the adresse to set
	 */
	public void setAdresse(TextArea adresse) {
		this.adresse = adresse;
	}

	/**
	 * @return the montant
	 */
	public DoubleTextField getMontant() {
		return montant;
	}

	/**
	 * @param montant the montant to set
	 */
	public void setMontant(DoubleTextField montant) {
		this.montant = montant;
	}

	/**
	 * @return the type
	 */
	public ComboBox<String> getTypeOperation() {
		return typeOperation;
	}

	/**
	 * @param type the type to set
	 */
	public void setTypeOperation(ComboBox<String> type) {
		this.typeOperation = type;
	}

	/**
	 * @return the motifOperation
	 */
	public ComboBox<String> getMotifOperation() {
		return motifOperation;
	}

	/**
	 * @param motifOperation the motifOperation to set
	 */
	public void setMotifOperation(ComboBox<String> motifOperation) {
		this.motifOperation = motifOperation;
	}

	/**
	 * @return the gerant
	 */
	public Employe getGerant() {
		return gerant;
	}

	/**
	 * @param gerant the gerant to set
	 */
	public void setGerant(Employe gerant) {
		this.gerant = gerant;
	}

	/**
	 * @return the beneficiaire
	 */
	public Beneficiaire getBeneficiaire() {
		return beneficiaire;
	}

	/**
	 * @param beneficiaire the beneficiaire to set
	 */
	public void setBeneficiaire(Beneficiaire beneficiaire) {
		this.beneficiaire = beneficiaire;
	}

	/**
	 * @return the telephone
	 */
	public TextField getTelephone() {
		return telephone;
	}

	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(TextField telephone) {
		this.telephone = telephone;
	}

	/**
	 * @return the date
	 */
	public JDatePickerImpl getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(JDatePickerImpl date) {
		this.date = date;
	}

	/**
	 * @return the typeBeneficiaire
	 */
	public ComboBox<String> getTypeBeneficiaire() {
		return typeBeneficiaire;
	}

	/**
	 * @param typeBeneficiaire the typeBeneficiaire to set
	 */
	public void setTypeBeneficiaire(ComboBox<String> typeBeneficiaire) {
		this.typeBeneficiaire = typeBeneficiaire;
	}

	/**
	 * @return the retraitDepot
	 */
	public RetraitDepotFond getRetraitDepot() {
		return retraitDepot;
	}

	/**
	 * @param retraitDepot the retraitDepot to set
	 */
	public void setRetraitDepot(RetraitDepotFond retraitDepot) {
		this.retraitDepot = retraitDepot;
	}

	/**
	 * @return the operationsList
	 */
	public Set<RetraitDepotFond> getOperationsList() {
		return operationsList;
	}

	/**
	 * @param operationsList the operationsList to set
	 */
	public void setOperationsList(Set<RetraitDepotFond> operationsList) {
		this.operationsList = operationsList;
	}

	/**
	 * @return the stockService
	 */
	/*
	 * public StockService getStockService() { return stockService; }
	 */
	/**
	 * @param stockService the stockService to set
	 */
	/*
	 * public void setStockService(StockService stockService) { this.stockService =
	 * stockService; }
	 */

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
	 * @return the dateDuJour
	 */
	public DatePicker getDateDuJour() {
		return dateDuJour;
	}

	/**
	 * @param dateDuJour the dateDuJour to set
	 */
	public void setDateDuJour(DatePicker dateDuJour) {
		this.dateDuJour = dateDuJour;
	}

	/**
	 * @return the nomComptable
	 */
	public TextField getNomComptable() {
		return nomComptable;
	}

	/**
	 * @param nomComptable the nomComptable to set
	 */
	public void setNomComptable(TextField nomComptable) {
		this.nomComptable = nomComptable;
	}

	/**
	 * @return the prenomComptable
	 */
	public TextField getPrenomComptable() {
		return prenomComptable;
	}

	/**
	 * @param prenomComptable the prenomComptable to set
	 */
	public void setPrenomComptable(TextField prenomComptable) {
		this.prenomComptable = prenomComptable;
	}

	/**
	 * @return the fonction
	 */
	public TextField getFonction() {
		return fonction;
	}

	/**
	 * @param fonction the fonction to set
	 */
	public void setFonction(TextField fonction) {
		this.fonction = fonction;
	}

	/**
	 * @return the retraitDepotFondData
	 */
	public ObservableList<RetraitDepotFond> getRetraitDepotFondData() {
		return retraitDepotFondData;
	}

	/**
	 * @param retraitDepotFondData the retraitDepotFondData to set
	 */
	public void setRetraitDepotFondData(ObservableList<RetraitDepotFond> retraitDepotFondData) {
		this.retraitDepotFondData = retraitDepotFondData;
	}

	/**
	 * @return the comptable
	 */
	public Employe getComptable() {
		return comptable;
	}

	/**
	 * @param comptable the comptable to set
	 */
	public void setComptable(Employe comptable) {
		this.comptable = comptable;
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

}

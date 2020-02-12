package main.java.fr.batis.components.tabs;

import main.java.fr.batis.components.common.CommonUtils;
import main.java.fr.batis.components.common.BatisUtils;
import main.java.fr.batis.entites.Chantier;
import main.java.fr.batis.entites.Devis;
import main.java.fr.batis.entites.NomsMateriaux;
import main.java.fr.batis.entites.Parametres;
import main.java.fr.batis.entites.PhaseConstruction;
import main.java.fr.batis.service.DevisService;
import main.java.fr.batis.service.NomsMaterieuxService;

import org.controlsfx.tools.Borders;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class ParametresTab {
	
	private CommonUtils commonUtils;
	private Parametres parametres;
	private TextField pourcentageField;
	private ComboBox<String> listePhases;
	private ComboBox<String> listeDevisForPhase; 
	private PhaseConstruction selectedPhase;
	private DevisService devisService;
	private Devis selectedDevis;
	private TextField nomField;
	private  NomsMaterieuxService nomsMaterieuxService;
	private  Long lastCode;
	private  NomsMateriaux lastName;
	
	ParametresTab() {
		this.commonUtils = new CommonUtils();
		this.devisService = new DevisService();
		this.nomsMaterieuxService = new NomsMaterieuxService();
		this.lastName = nomsMaterieuxService.getLast();
		this.lastCode = lastName != null? (lastName.getCode() !=null ? lastName.getCode()+1 : 1) : 1;
	}

	
	/**
	 * 
	 * @param chantier
	 * @return
	 */
	public Tab getParametresTab(Chantier chantier) {
		
		
		Tab parametresTab = new Tab("Paramètres");
		Image tabPlanningImg = new Image(getClass().getResourceAsStream("/images/parametres.png"));
		parametresTab.setGraphic(new ImageView(tabPlanningImg));
       
		BorderPane parametresContent = new BorderPane();
		BorderPane parametresContentTmp = new BorderPane();
		Node panHead = commonUtils.getHeader("Gestion des paramètres");
		parametresContent.setPadding(new Insets(0,10,10,10));
		
		
		Label selectedPhaseLabel = new Label("Séléctionnez la phase :");
		Label selectedDevisLabel = new Label("Séléctionnez le devis :");
		listeDevisForPhase = new ComboBox<String>();
		GridPane contentPhaseDevisPane = getCommonUtils().getGridPanel();
		
		listePhases = new ComboBox<String>();
		listePhases.getItems().add("");
		for (PhaseConstruction phase : chantier.getPhasesConstruction()) {
			listePhases.getItems().add(phase.getNom());
		}

		Label pourcentageMo = new Label("Pourcentage Main d'Oeuvre :");
		pourcentageField = new TextField();
		
		contentPhaseDevisPane.add(selectedPhaseLabel, 0, 0);
		contentPhaseDevisPane.add(listePhases, 1, 0);
		contentPhaseDevisPane.add(selectedDevisLabel, 2, 0);
		contentPhaseDevisPane.add(listeDevisForPhase, 3, 0);
		
		Button savePourcentage = new Button("Enregistrer");
		savePourcentage.setDisable(true);

		contentPhaseDevisPane.add(pourcentageMo, 0, 1);
		contentPhaseDevisPane.add(pourcentageField, 1, 1);
		contentPhaseDevisPane.add(savePourcentage, 2, 1);
		
	
		Node wraperContentPhaseDevis = Borders.wrap(contentPhaseDevisPane)

				.lineBorder()
				.color(Color.rgb(168, 211, 255))
				.outerPadding(20, 10, 10, 10)
				.title("Pourcentage Main d'Oeuvre")
				.buildAll();
	
		
		Label nom = new Label("Nom matériel :");
		nomField = new TextField();
		Label codeLabel = new Label("Code :");
		TextField codeField = new TextField(String.valueOf(lastCode));
		codeField.setEditable(false);
		Button saveMateriel = new Button("Enregistrer");
		saveMateriel.setDisable(true);
		GridPane contentMateriel = getCommonUtils().getGridPanel();
	
		contentMateriel.add(nom, 0, 0);
		contentMateriel.add(nomField, 1, 0);
		contentMateriel.add(codeLabel, 2, 0);
		contentMateriel.add(codeField, 3, 0);
		contentMateriel.add(saveMateriel, 4, 0);
		
		Node wraperContentMateriel = Borders.wrap(contentMateriel)

				.lineBorder()
				.color(Color.rgb(168, 211, 255))
				.outerPadding(20, 10, 10, 10)
				.title("Gestion des noms des Matériaux")
				.buildAll();
		

		parametresContentTmp.setLeft(wraperContentPhaseDevis);
		parametresContentTmp.setCenter(wraperContentMateriel);
		
		parametresContent.setTop(panHead);
		parametresContent.setCenter(parametresContentTmp);
	
		
		
		
		final GridPane buttonsBar =  getCommonUtils().getGridPanel();
		
		Image editImg = new Image(getClass().getResourceAsStream("/images/edit.png"));
		
		Button modifier = new Button("Modifier");
		modifier.setGraphic(new ImageView(editImg));

		buttonsBar.add(modifier, 2, 2);
		
		parametresContent.setBottom(buttonsBar);
		parametresTab.setContent(parametresContent);

		/**
		 * 
		 */
		listePhases.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				 getPourcentageField().setText("");
				PhaseConstruction selectedPhase = BatisUtils.getPhaseByName(chantier, newValue);
				if (selectedPhase != null) {
					setSelectedPhase(selectedPhase);

					listeDevisForPhase.getItems().clear();
					listeDevisForPhase.getItems().add("");
					for (Devis devis : selectedPhase.getListDevis()) {
						listeDevisForPhase.getItems().add(devis.getNom());
					}

				} 
			}

		});
		
		/**
		 * 
		 */
		listeDevisForPhase.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				 getPourcentageField().setText("");
					Devis devis = getDevisService().getDevisByNameAndPhase(
							listeDevisForPhase.getSelectionModel().getSelectedItem(), getSelectedPhase());
				
					if (devis != null) {
						if(devis.getPourcentageMO() != null) {
							 getPourcentageField().setText(String.valueOf(devis.getPourcentageMO()));
						}
						
						setSelectedDevis(devis);						
						savePourcentage.setDisable(false);
					}else {
						savePourcentage.setDisable(true);
					}

				}
		});
		
		/**
		 * 
		 */
		savePourcentage.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				updateOrCreateParametres();
			}

			private void updateOrCreateParametres() {
				String pourcentage = pourcentageField.getText();				
				 if (!pourcentage.isBlank()) {
					Double  pourcent = Double.valueOf(BatisUtils.deleteWhiteSpace(pourcentage));
				
						if(getSelectedDevis() != null ) {
							getSelectedDevis().setPourcentageMO(pourcent);
							devisService.saveOrUpdate(getSelectedDevis());
						}
					
				 }
			}
			
		});
 
		 nomField.setOnMouseExited(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
			
				if(!nomField.getText().isEmpty() && !codeField.getText().isEmpty()) {
					saveMateriel.setDisable(false);
				}	
			}
		});
		 
		 saveMateriel.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				String name = nomField.getText();
				String code = codeField.getText();
				NomsMateriaux  nomsMateriaux= new NomsMateriaux();
				nomsMateriaux.setCode(Long.valueOf(code.trim()));
				nomsMateriaux.setNom(name);
				nomsMaterieuxService.saveOrUpdate(nomsMateriaux);
				lastCode  = lastCode +1;
				codeField.setText(String.valueOf(lastCode));
				nomField.clear();
				saveMateriel.setDisable(true);
			}
		});
		return parametresTab;
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
	 * @return the parametres
	 */
	public Parametres getParametres() {
		return parametres;
	}

	/**
	 * @param parametres the parametres to set
	 */
	public void setParametres(Parametres parametres) {
		this.parametres = parametres;
	}

	/**
	 * @return the pourcentageField
	 */
	public TextField getPourcentageField() {
		return pourcentageField;
	}

	/**
	 * @param pourcentageField the pourcentageField to set
	 */
	public void setPourcentageField(TextField pourcentageField) {
		this.pourcentageField = pourcentageField;
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
	 * @return the lastCode
	 */
	public Long getLastCode() {
		return lastCode;
	}



	/**
	 * @param lastCode the lastCode to set
	 */
	public void setLastCode(Long lastCode) {
		this.lastCode = lastCode;
	}



	/**
	 * @return the lastName
	 */
	public NomsMateriaux getLastName() {
		return lastName;
	}



	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(NomsMateriaux lastName) {
		this.lastName = lastName;
	}
   
	
}

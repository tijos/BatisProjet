package main.java.fr.batis.components.tabs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.controlsfx.tools.Borders;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.java.fr.batis.components.AjoutMaterielWindow;
import main.java.fr.batis.components.DevisWindow;
import main.java.fr.batis.components.NewDevisWindow;
import main.java.fr.batis.components.NewPhaseWindow;
import main.java.fr.batis.components.PhaseWindow;
import main.java.fr.batis.components.common.BatisUtils;
import main.java.fr.batis.components.common.CommonUtils;
import main.java.fr.batis.components.common.ImageUtils;
import main.java.fr.batis.components.custom.CustomLabel;
import main.java.fr.batis.entites.Chantier;
import main.java.fr.batis.entites.Devis;
import main.java.fr.batis.entites.Employe;
import main.java.fr.batis.entites.Materiel;
import main.java.fr.batis.entites.PhaseConstruction;
import main.java.fr.batis.service.ChantierService;
import main.java.fr.batis.service.MaterielService;

public class TableauDeBord {

	private CommonUtils commonUtils;
	private MaterielService materielService;
	private static ImageUtils imageUtils = new ImageUtils();
	private ObservableList<Materiel> devisTableData;
	private TextArea descripValue;
	private ChantierService chantierService;

	public TableauDeBord() {
		commonUtils = new CommonUtils();
		materielService = new MaterielService();
		chantierService = new ChantierService();
	}

	/**
	 * 
	 * @param chantier
	 * @return
	 */
	public Tab getTableauDeBord(Chantier chantier) {

		Image rootImg = new Image(getClass().getResourceAsStream("/images/pRoot.png"));
		Image childImg = new Image(getClass().getResourceAsStream("/images/itemChild.png"));
		Image tabBordImg = new Image(getClass().getResourceAsStream("/images/home.png"));

		Tab tableauDebord = new Tab("Tableau de bord");
		tableauDebord.setGraphic(new ImageView(tabBordImg));
		BorderPane borderPanePrincipale = new BorderPane();
		borderPanePrincipale.setPadding(new Insets(5));
		VBox centerPane = new VBox();

		Node wraperBorderPaneCenter = getVueGlobalChantier(chantier);
		Node wraperCompatable = getComptable(chantier);
		Node wraperChefChantier = getChefChantier(chantier);

		centerPane.getChildren().add(wraperBorderPaneCenter);
		centerPane.getChildren().add(wraperChefChantier);
		centerPane.getChildren().add(wraperCompatable);

		// ARBRE

		Node wraperBorderPaneLeft = getTreePanel(chantier, rootImg, childImg);
		// synthese compta
		Node wraperborderPaneRight = getSyntheseComptabilite(chantier);
		borderPanePrincipale.setLeft(wraperBorderPaneLeft);
		borderPanePrincipale.setCenter(centerPane);
		borderPanePrincipale.setRight(wraperborderPaneRight);

		tableauDebord.setContent(borderPanePrincipale);
		return tableauDebord;
	}

	/**
	 * 
	 * @param chantier
	 * @param gridTableauBord
	 * @return
	 */
	private Node getVueGlobalChantier(Chantier chantier) {
		// centre
		BorderPane borderPaneCenter = new BorderPane();
		// borderPaneCenter.setPrefSize(400, 200);

		GridPane gridTableauBord = getCommonUtils().getGridPanel();

		Label descriptionLabel = new Label("Description : ");
		descripValue = getTextAreaDescription(chantier);

		Label lieuLabel = new Label("Lieu d'exécution : ");
		Label lieuLabelValue = new CustomLabel(chantier.getSiteProjet());

		Label statutLabel = new Label("Statut travaux : ");
		Label statutLabelValue = new CustomLabel(chantier.getStatut());

		Label phaseLabel = new Label("Phase en cours : ");
		Label phaseValue = new CustomLabel(
				BatisUtils.getPhaseEncours(chantier) != null ? BatisUtils.getPhaseEncours(chantier).getNom() : "");

		Label typeTravauxLabel = new Label("Type  travaux : ");
		Label typeTravauxLabelValue = new CustomLabel(chantier.getTypeTravaux());

		Label typeChantierLabel = new Label("Type  chantier : ");
		Label typeChantierLabelValue = new CustomLabel(chantier.getTypeChantier());

		Label deteDebutLabel = new Label("Date de début des travaux : ");
		Label deteDebutValue = new CustomLabel(chantier.getDateDebutChantier());

		Label deteFinLabel = new Label("Date de fin des travaux (Livraison) : ");
		Label deteFinValue = new CustomLabel(chantier.getDateLivraison());

		Label nbreEmployeLabel = new Label("Nombre d'employé : ");

		Label executionLabel = new Label("Exécution : ");
		Label executionValue = new CustomLabel(chantier.getExecution());

		Label financementLabel = new Label("Financement : ");
		Label financementValue = new CustomLabel(chantier.getFinancement());

		Label nombrePhaseLabel = new Label("Nombre de phase : ");
		Label nombrePhaseValue = new CustomLabel(String.valueOf(chantier.getPhasesConstruction().size()));

		Label nbreEmployeValue = new CustomLabel(String.valueOf(chantier.getListeEmployes().size()));

		// ADD
		gridTableauBord.add(descriptionLabel, 0, 0);
		gridTableauBord.add(descripValue, 1, 0, 6, 1);

		gridTableauBord.add(lieuLabel, 0, 3);
		gridTableauBord.add(lieuLabelValue, 1, 3);

		gridTableauBord.add(statutLabel, 0, 4);
		gridTableauBord.add(statutLabelValue, 1, 4);

		gridTableauBord.add(nombrePhaseLabel, 3, 3);
		gridTableauBord.add(nombrePhaseValue, 4, 3);

		gridTableauBord.add(phaseLabel, 3, 4);
		gridTableauBord.add(phaseValue, 4, 4);

		gridTableauBord.add(executionLabel, 3, 5);
		gridTableauBord.add(executionValue, 4, 5);

		gridTableauBord.add(financementLabel, 3, 6);
		gridTableauBord.add(financementValue, 4, 6);

		gridTableauBord.add(typeTravauxLabel, 0, 5);
		gridTableauBord.add(typeTravauxLabelValue, 1, 5);

		gridTableauBord.add(typeChantierLabel, 0, 6);
		gridTableauBord.add(typeChantierLabelValue, 1, 6);

		gridTableauBord.add(deteDebutLabel, 0, 7);
		gridTableauBord.add(deteDebutValue, 1, 7);

		gridTableauBord.add(deteFinLabel, 0, 8);
		gridTableauBord.add(deteFinValue, 1, 8);

		gridTableauBord.add(nbreEmployeLabel, 0, 9);
		gridTableauBord.add(nbreEmployeValue, 1, 9);

		borderPaneCenter.setCenter(gridTableauBord);
		//
		Node wraperBorderPaneCenter = Borders.wrap(borderPaneCenter)

				.lineBorder().color(Color.rgb(168, 211, 255)).outerPadding(20, 5, 5, 5).innerPadding(5)
				.title("Vue global du chantier " + chantier.getNomProjet()).buildAll();
		return wraperBorderPaneCenter;
	}

	private TextArea getTextAreaDescription(Chantier chantier) {
		TextArea descripValue = new TextArea(chantier.getDescription());
		descripValue.setMaxSize(350, 70);
		descripValue.setWrapText(true);
		descripValue.setEditable(false);
		return descripValue;
	}

	private Node getChefChantier(Chantier chantier) {
		BorderPane borderChefChantier = new BorderPane();
		GridPane gridChefChantier = getCommonUtils().getGridPanel();

		Employe chefChantier = BatisUtils.getChefChantier(chantier);
		Label chefChantierLabel = new Label("Nom : ");
		Label chefChantierValue = new CustomLabel(" ");
		Label telphoneValue = new CustomLabel("");
		Label telphoneLabel = new Label("Téléphone : ");
		Label emailLabel = new Label("Email : ");
		Label emailValue = new CustomLabel("");

		if (chefChantier != null) {
			chefChantierValue.setText(chefChantier.getNom() + " " + chefChantier.getPrenom());
			telphoneValue.setText(chefChantier.getTelephone());
			emailValue.setText(chefChantier.getEmail());
		}

		gridChefChantier.add(chefChantierLabel, 0, 0);
		gridChefChantier.add(chefChantierValue, 1, 0);
		gridChefChantier.add(telphoneLabel, 2, 0);
		gridChefChantier.add(telphoneValue, 3, 0);

		gridChefChantier.add(emailLabel, 4, 0);
		gridChefChantier.add(emailValue, 5, 0);

		borderChefChantier.setTop(gridChefChantier);

		Node wraperBorderChefChantier = Borders.wrap(borderChefChantier).lineBorder().color(Color.rgb(168, 211, 255))
				.outerPadding(10, 5, 5, 5).innerPadding(5).title("Chef de chantier ").buildAll();
		return wraperBorderChefChantier;
	}

	/**
	 * 
	 * @param chantier
	 * @return
	 */

	private Node getSyntheseComptabilite(Chantier chantier) {
		VBox borderPaneRightTmp = new VBox();

		BorderPane borderIndicateurs = new BorderPane();
		BorderPane syntheseComptabilite = new BorderPane();
		syntheseComptabilite.setPrefSize(400, 200);

		borderIndicateurs.setPrefSize(350, 350);

		Label montantPrevuLabel = new Label("Montant Total Prévu :");
		String montantPrevu = BatisUtils.getMontantTotalPrevu(chantier, true);
		Label montantPrevuValue = new CustomLabel(montantPrevu);

		Label montantReelLabel = new Label("Montant Total Réel :");
		String montantReel = BatisUtils.getMontantTotalReel(chantier);

		Label montantReelValue = new CustomLabel(montantReel);

		Label indiceLabel = new Label("Indice (DTP) :");
		Label indicePartielLabel = new Label("Indice (DPP) :");
		Label indiceValueGlobal = new Label();
		Label indiceValuePartiel = new Label();

		Label montantPartielPrevuLabel = new Label("Montant Partiel prévu :");
		String montantPartielPrevu = BatisUtils.getMontantPartielPrevu(chantier);
		Label montantPrevuValueLabel = new CustomLabel(montantPartielPrevu);

		Label diffPrixLabel = new Label("Gain / Dépassement (DTP) :");
		Double montant = BatisUtils.getDifferencePrixAchatMat(chantier);
		Label diffPrixValue = new CustomLabel(BatisUtils.getNumberFormatter(montant * -1));

		if (montant < 0) {
			diffPrixValue.setTextFill(Color.web("#FF0000"));
		} else {
			diffPrixValue.setTextFill(Color.web("#008000"));
		}

		// Partiel
		Label diffPrixPartielLabel = new Label("Gain / Dépassement (DPP) :");
		Double diffMontant = BatisUtils.getDifferencePrixPartielAchatMat(chantier);
		Label diffPrixPartielValue = new CustomLabel(BatisUtils.getNumberFormatter(diffMontant * -1));

		if (montant < 0) {
			diffPrixValue.setTextFill(Color.web("#FF0000"));
		} else {
			diffPrixValue.setTextFill(Color.web("#008000"));
		}

		if (diffMontant < 0) {
			diffPrixPartielValue.setTextFill(Color.web("#FF0000"));
		} else {
			diffPrixPartielValue.setTextFill(Color.web("#008000"));
		}

		Double montantPre = Double.valueOf(BatisUtils.deleteWhiteSpace(montantPrevu));
		Double pourcentageGlobal = (BatisUtils.getPourcentage(montantPre, montant)) * -1;

		indiceValueGlobal = getIndiceProcessing(montantReel, indiceValueGlobal, montantPre, pourcentageGlobal);

		Double montantPartielPrevuValue = Double.valueOf(BatisUtils.deleteWhiteSpace(montantPartielPrevu));
		Double pourcentagePartiel = (BatisUtils.getPourcentage(montantPartielPrevuValue, diffMontant)) * -1;
		indiceValuePartiel = getIndiceProcessing(BatisUtils.getMontantTotalReel(chantier), indiceValuePartiel,
				montantPartielPrevuValue, pourcentagePartiel);

		String pourcentGlobal = BatisUtils.getNumberFormatter(pourcentageGlobal);
		Label pourcentageLabel = new Label("( " + pourcentGlobal + " % )");

		String pourcentPartiel = BatisUtils.getNumberFormatter(pourcentagePartiel);
		Label pourcentagePartielLabel = new Label("( " + pourcentPartiel + " % )");

		GridPane gridMontant = getCommonUtils().getGridPanel();

		gridMontant.add(montantPrevuLabel, 0, 0);
		gridMontant.add(montantPrevuValue, 1, 0);

		gridMontant.add(montantReelLabel, 0, 1);
		gridMontant.add(montantReelValue, 1, 1);

		gridMontant.add(diffPrixLabel, 0, 2);
		gridMontant.add(diffPrixValue, 1, 2);

		gridMontant.add(indiceLabel, 0, 3);
		gridMontant.add(indiceValueGlobal, 1, 3);
		gridMontant.add(pourcentageLabel, 2, 3);

		gridMontant.add(montantPartielPrevuLabel, 0, 4);
		gridMontant.add(montantPrevuValueLabel, 1, 4);

		gridMontant.add(diffPrixPartielLabel, 0, 5);
		gridMontant.add(diffPrixPartielValue, 1, 5);

		gridMontant.add(indicePartielLabel, 0, 6);
		gridMontant.add(indiceValuePartiel, 1, 6);
		gridMontant.add(pourcentagePartielLabel, 2, 6);

		Label dpp = new Label("DPP* : Dépenses Prévues Partieles");
		Label dtp = new Label("DTP* : Dépenses Totales Prévues ");

		gridMontant.add(dtp, 0, 7);
		gridMontant.add(dpp, 0, 8);

		syntheseComptabilite.setCenter(gridMontant);

		List<String> datesPrevus = new ArrayList<String>();
		List<Integer> valeursPrevu = new ArrayList<Integer>();

		List<String> datesReelles = new ArrayList<String>();
		List<Integer> valeursReelles = new ArrayList<Integer>();

		Set<Map.Entry<String, Integer>> datesPre = BatisUtils.getDatesPrevusPhase(chantier).entrySet();
		Set<Map.Entry<String, Integer>> datesReel = BatisUtils.getDatesReelsPhase(chantier).entrySet();

		for (Map.Entry<String, Integer> datesP : datesPre) {
			datesPrevus.add(datesP.getKey());
			valeursPrevu.add(datesP.getValue());
		}

		for (Map.Entry<String, Integer> datesR : datesReel) {
			datesReelles.add(datesR.getKey());
			valeursReelles.add(datesR.getValue());
		}

		Node wraperIndicDepenses = Borders.wrap(syntheseComptabilite).lineBorder().color(Color.rgb(168, 211, 255))
				.outerPadding(20, 5, 5, 5).title("Indicateurs de depenses ").buildAll();

		borderPaneRightTmp.getChildren().add(wraperIndicDepenses);
		return borderPaneRightTmp;
	}

	private Label getIndiceProcessing(String montantReel, Label indiceValue, Double montantPrevu, Double pourcentage) {

		if (montantPrevu >= Double.valueOf(BatisUtils.deleteWhiteSpace(montantReel))) {
			indiceValue.setGraphic(new ImageView(imageUtils.getEmpouleVert()));
		} else {

			if (0.0 <= pourcentage && pourcentage <= 5.0) {
				indiceValue.setGraphic(new ImageView(imageUtils.getEmpouleVert()));
			}

			if (5.0 <= pourcentage && pourcentage <= 10.0) {
				indiceValue.setGraphic(new ImageView(imageUtils.getEmpouleJaune()));
			}

			if (10.0 <= pourcentage && pourcentage <= 15.0) {
				indiceValue.setGraphic(new ImageView(imageUtils.getEmpouleOrange()));
			}
			if (15.0 <= pourcentage) {
				indiceValue.setGraphic(new ImageView(imageUtils.getEmpouleRouge()));
			}
		}
		return indiceValue;
	}

	/**
	 * @param chantier
	 * @return
	 */
	private Node getComptable(Chantier chantier) {
		GridPane gridComptable = getCommonUtils().getGridPanel();

		Label comptableLabel = new Label("Nom :");
		Label comptableValue = new CustomLabel("");
		Label comptableEmailLabel = new Label("Email :");
		Label comptableEmailValue = new CustomLabel("");

		Label comptableTelLabel = new Label("Téléphone :");
		Label comptableTelValue = new CustomLabel("");

		Employe comptable = BatisUtils.getChefComptable(chantier);

		if (comptable != null) {
			comptableValue.setText(comptable.getNom() + " " + comptable.getPrenom());
			comptableTelValue.setText(comptable.getTelephone());
			comptableEmailValue.setText(comptable.getEmail());
		}

		gridComptable.add(comptableLabel, 0, 0);
		gridComptable.add(comptableValue, 1, 0);
		gridComptable.add(comptableTelLabel, 2, 0);
		gridComptable.add(comptableTelValue, 3, 0);
		gridComptable.add(comptableEmailLabel, 4, 0);
		gridComptable.add(comptableEmailValue, 5, 0);

		Node wraperCompatable = Borders.wrap(gridComptable).lineBorder().color(Color.rgb(168, 211, 255))
				.outerPadding(10, 5, 5, 5).innerPadding(5).title("Comptable ").buildAll();
		return wraperCompatable;
	}

	/**
	 * 
	 * @param chantier
	 * @param rootImg
	 * @param childImg
	 * @return
	 */
	private Node getTreePanel(Chantier chantier, Image rootImg, Image childImg) {
		BorderPane borderPaneLeft = new BorderPane();
		borderPaneLeft.setPrefSize(180, 280);

		// String border_styles2 = "-fx-border-width:1 1 1 1; -fx-border-color: blue";
		// BorderPane.setMargin(borderPaneLeft,new Insets(10));
		// borderPaneLeft.setStyle(border_styles2);

		// Root Item
		TreeItem<String> rootItem = new TreeItem<String>(chantier.getNomProjet(), new ImageView(rootImg));
		rootItem.setExpanded(false);

		// TODO PHAS CSCTION DE DOIT PAS ETRE NULL
		Image devisItemImg = new Image(getClass().getResourceAsStream("/images/itemDevis2.png"));

		for (PhaseConstruction phase : chantier.getPhasesConstruction()) {
			TreeItem<String> itemPhase = new TreeItem<String>(phase.getNom(), new ImageView(childImg));

			for (Devis devis : phase.getListDevis()) {
				TreeItem<String> itemDevis = new TreeItem<String>(devis.getNom(), new ImageView(devisItemImg));
				itemPhase.getChildren().add(itemDevis);
			}

			rootItem.getChildren().add(itemPhase);
		}

		TreeView<String> tree = new TreeView<String>(rootItem);

		EventHandler<MouseEvent> mouseEventHandle = (MouseEvent event) -> {
			handleMouseClicked(event, tree, chantier);
		};

		tree.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEventHandle);
		// tree.setPadding(new Insets(10) );
		// tree.setPrefSize(180, 350);
		tree.setEditable(false);

		// BorderPane.setMargin(tree,new Insets(20,0,0,10));

		borderPaneLeft.setCenter(tree);

		Node wraperBorderPaneLeft = Borders.wrap(borderPaneLeft).lineBorder().color(Color.rgb(168, 211, 255))
				.outerPadding(20, 0, 0, 0).innerPadding(0)
				// .title("Synthèse du chantier "+chantier.getNomProjet())
				.buildAll();
		return wraperBorderPaneLeft;
	}

	/**
	 * 
	 * @param event
	 * @param treeView
	 * @param chantier
	 */
	private void handleMouseClicked(MouseEvent event, TreeView<String> treeView, Chantier chantier) {
		Node node = event.getPickResult().getIntersectedNode();
		// Accept clicks only on node cells, and not on empty spaces of the TreeView
		if (node instanceof Text || (node instanceof TreeCell && ((TreeCell<?>) node).getText() != null)) {
			TreeItem<?> item = treeView.getSelectionModel().getSelectedItem();

			String name = (String) (item).getValue();

			// TODO CHERCHER LA PAHE EN BASE OU DANS LA LISTE
			PhaseConstruction phase = BatisUtils.getPhaseByName(chantier, name);

			int level = treeView.getTreeItemLevel(item);
			if (level == 1) {

				if (event.getButton() == MouseButton.PRIMARY) {

					PhaseWindow phaseWindow = new PhaseWindow(phase, chantier, false);
					phaseWindow.start(new Stage());
				} else {
					// Create ContextMenu
					// TODO Faire une fonction
					ContextMenu cxtMenu = new ContextMenu();

					Menu addMenu = new Menu("Ajouter...");
					MenuItem addDevis = new MenuItem("Devis");
					MenuItem addDescription = new MenuItem("Description");
					MenuItem addCmpteRendu = new MenuItem("Rapport journalier");
					addMenu.getItems().add(addDevis);
					addMenu.getItems().add(new SeparatorMenuItem());
					addMenu.getItems().add(addDescription);
					addMenu.getItems().add(new SeparatorMenuItem());
					addMenu.getItems().add(addCmpteRendu);

					/*
					 * addMenu.setOnAction(new EventHandler<ActionEvent>() {
					 * 
					 * @Override public void handle(ActionEvent event) {
					 * System.out.println("Ajouter..."); } });
					 */

					MenuItem editPhase = new MenuItem("Modifier");
					editPhase.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							new PhaseWindow(phase, chantier, true);
						}
					});

					MenuItem deteletePhase = new MenuItem("supprimer");

					addDescription.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent arg0) {
							new PhaseWindow(phase, chantier, true);
						}
					});
					deteletePhase.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {

						}
					});

					// Add MenuItem to ContextMenu
					cxtMenu.getItems().addAll(addMenu, new SeparatorMenuItem(), editPhase, new SeparatorMenuItem(),
							deteletePhase);

					// When user right-click on Circle
					treeView.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

						@Override
						public void handle(ContextMenuEvent event) {

							cxtMenu.show(treeView, event.getScreenX(), event.getScreenY());
						}
					});

					addDevis.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							NewDevisWindow nDevisWdw = new NewDevisWindow(phase, chantier);
							nDevisWdw.initSignatureWindow(new Stage(), chantier);
						}
					});

				}

			} else if (level == 0) {

				if (event.getButton() == MouseButton.PRIMARY) {

				} else {
					ContextMenu contextPhaseMenu = new ContextMenu();
					MenuItem addNewPhase = new MenuItem("Ajouter une Phase");
					addNewPhase.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							NewPhaseWindow phaseWindow = new NewPhaseWindow(chantier);
							phaseWindow.initSignatureWindow(new Stage(), chantier);
						}
					});

					MenuItem editProject = new MenuItem("Modifier...");
					editProject.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							getDescripValue().setEditable(true);
						}
					});

					MenuItem disable = new MenuItem("Désactiver");
					MenuItem enable = new MenuItem("Activer");
					disable.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							chantier.setActif(false);
							chantierService.saveOrUpdate(chantier);
						}
					});

					enable.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							chantier.setActif(true);
							chantier.setClosed(false);
							chantierService.saveOrUpdate(chantier);
						}
					});

					descripValue.setOnMouseExited(new EventHandler<Event>() {

						@Override
						public void handle(Event arg0) {
							chantier.setDescription(descripValue.getText());
						}
					});

					MenuItem cloturer = new MenuItem("Cloturer");
					cloturer.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							chantier.setClosed(true);
							chantier.setActif(false);
							chantierService.saveOrUpdate(chantier);
						}
					});

					// Add MenuItem to ContextMenu

					contextPhaseMenu.getItems().add(addNewPhase);
					contextPhaseMenu.getItems().addAll(new SeparatorMenuItem(), editProject);
					if (!chantier.isClosed()) {
						contextPhaseMenu.getItems().addAll(new SeparatorMenuItem(), cloturer);
					}
					if (chantier.isActif()) {
						contextPhaseMenu.getItems().addAll(new SeparatorMenuItem(), disable);
					} else {
						contextPhaseMenu.getItems().addAll(new SeparatorMenuItem(), enable);
					}

					// When user right-click on Circle
					treeView.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

						@Override
						public void handle(ContextMenuEvent event) {

							contextPhaseMenu.show(treeView, event.getScreenX(), event.getScreenY());
						}
					});
				}

			} else if (level == 2) {

				devisTableData = FXCollections.observableArrayList(new ArrayList<Materiel>());
				devisTableData.clear();
				TreeItem<?> devisItem = treeView.getSelectionModel().getSelectedItem();
				String nom = (String) (devisItem).getValue();
				String phaseName = (String) devisItem.getParent().getValue();
				PhaseConstruction phaseCst = BatisUtils.getPhaseByNameAndChantier(phaseName, chantier);
				Devis devis = BatisUtils.getDevisByNameAndPhase(nom, phaseCst);

				if (event.getButton() == MouseButton.PRIMARY) {
					new DevisWindow(devisTableData, devis);
				} else {

					ContextMenu contextDevisMenu = new ContextMenu();
					MenuItem itemDevisConsulter = new MenuItem("Consulter");
					itemDevisConsulter.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {

							new DevisWindow(devisTableData, devis);
						}
					});

					MenuItem itemDevisAjouter = new MenuItem("Ajouter un matériel");
					itemDevisAjouter.setOnAction(openAjoutMaterielWindow(devisTableData, devis));

					MenuItem itemDevisDelete = new MenuItem("supprimer");

					itemDevisDelete.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							BatisUtils.deleteDevis(name, phase);
						}
					});
					MenuItem itemDevisRename = new MenuItem("Renommer");

					itemDevisRename.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {

						}
					});

					contextDevisMenu.getItems().addAll(itemDevisConsulter, new SeparatorMenuItem(), itemDevisAjouter,
							new SeparatorMenuItem(), itemDevisDelete, new SeparatorMenuItem(), itemDevisRename,
							new SeparatorMenuItem());
					treeView.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

						@Override
						public void handle(ContextMenuEvent event) {
							contextDevisMenu.show(treeView, event.getScreenX(), event.getScreenY());
						}
					});

				}

			}

		}
	}

	/**
	 * 
	 * @param materielTableData2
	 * @param devis
	 * @return
	 */
	public EventHandler<ActionEvent> openAjoutMaterielWindow(ObservableList<Materiel> materielTableData2, Devis devis) {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				materielTableData2.addAll(BatisUtils.getMateriauxDevisInitial(devis));
				AjoutMaterielWindow ajoutMat = new AjoutMaterielWindow(materielTableData2, devis, true);

				try {
					ajoutMat.initComponent(new Stage(), materielTableData2, devis, true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
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
	 * @return the materielService
	 */
	public MaterielService getMaterielService() {
		return materielService;
	}

	/**
	 * @param materielService the materielService to set
	 */
	public void setMaterielService(MaterielService materielService) {
		this.materielService = materielService;
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

}

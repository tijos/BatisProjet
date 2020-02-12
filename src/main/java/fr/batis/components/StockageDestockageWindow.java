package main.java.fr.batis.components;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.java.fr.batis.components.common.BatisUtils;
import main.java.fr.batis.components.common.CommonUtils;
import main.java.fr.batis.components.common.DateConverterHelper;
import main.java.fr.batis.components.common.ImageUtils;
import main.java.fr.batis.components.custom.DoubleTextField;
import main.java.fr.batis.entites.Chantier;
import main.java.fr.batis.entites.Inventaire;
import main.java.fr.batis.entites.StockageDestockage;
import main.java.fr.batis.enumeration.UniteMesure;
import main.java.fr.batis.service.StockageDestockageService;

public class StockageDestockageWindow {
	private ComboBox<String> materielCombox;
	private ObservableList<StockageDestockage> stockDestockDataTable;
	private ObservableList<Inventaire> inventaireDataTable;
	private TextField qualite;
	private DoubleTextField quantite;
	private ComboBox<String> unite;
	private ComboBox<String> statut;
	private StockageDestockage stockDestock;
	private TextField motif;
	private Inventaire inventaire;
	private DatePicker dateOperation;
	private StockageDestockageService stockDestockService;
	private CommonUtils commonUtils;
	private ImageUtils imageUtils;

	private static final Logger LOGGER = LogManager.getLogger(StockageDestockageWindow.class);

	public StockageDestockageWindow() {
		super();
		this.stockDestockService = new StockageDestockageService();
		this.commonUtils = new CommonUtils();
		this.imageUtils = new ImageUtils();
	}

	public StockageDestockageWindow(ObservableList<StockageDestockage> stockDestockdata,
			ObservableList<Inventaire> inventaireData, Chantier chantier, boolean stockage, boolean destockage,
			boolean isInventaire) {

		// Image icon = Toolkit.getDefaultToolkit().getImage("images/argent.png");
		// this.setIconImage(icon);
		this.stockDestockService = new StockageDestockageService();
		this.commonUtils = new CommonUtils();
		this.imageUtils = new ImageUtils();
		this.stockDestockDataTable = stockDestockdata;
		this.inventaireDataTable = inventaireData;
		this.initComponent(new Stage(), chantier, stockage, destockage, isInventaire);
	}

	public void initComponent(Stage primaryStage, Chantier chantier, boolean isStockage, boolean isDestockage,
			boolean isInventaire) {

		Image undoImg = imageUtils.getUndoImg();
		Image okImg = imageUtils.getOkImg();
		Image exitImg = imageUtils.getExitImg();

		String title = "";
		if (isStockage) {
			title = "Stockage des matériaux";
		} else if (isDestockage) {
			title = "Destockage des matériaux";
		} else {
			title = "Inventaire";
		}

		Node panHead = commonUtils.getHeader(title);

		BorderPane contentMteriel = new BorderPane();
		GridPane contentMterielTmp = commonUtils.getGridPanel();

		Label dateLabel = new Label("Date :");
		dateOperation = DateConverterHelper.getConvetedDate();

		Callback<DatePicker, DateCell> dayCellFactory = getCommonUtils().getDayCellFactory();
		dateOperation.setDayCellFactory(dayCellFactory);

		Label nomTypenLabel = new Label("Nom / Type :");
		materielCombox = new ComboBox<String>();

		materielCombox.getItems().add("");
		// La liste provient des du stock lui meme.
		/*
		 * List<StockageDestockage> listStock =
		 * stockDestockService.getEtatStock(chantier.getId());
		 * 
		 * for(Object materiel :listStock) { Object[] object = (Object[]) materiel;
		 * String nom = object[0].toString()+ " / "+object[1].toString();
		 * materielCombox.getItems().add(nom); }
		 */

		materielCombox = BatisUtils.getListMateriaux();

		Label qualiteLabel = new Label("Qualite / Cathégorie:");
		qualite = new TextField();

		Label quantiteLabel = new Label("Quantité :");
		NumberFormat doubleFormat = NumberFormat.getNumberInstance(Locale.FRANCE);
		doubleFormat.setMaximumFractionDigits(2);
		quantite = new DoubleTextField();

		Label uniteLabel = new Label("Unité de mésure :");
		unite = new ComboBox<String>();
		unite.getItems().add("");
		for (UniteMesure uniteItem : UniteMesure.values()) {
			unite.getItems().add(uniteItem.getDescription());
		}

		Label statutLabel = new Label("Statut :");
		statut = new ComboBox<String>();
		statut.getItems().add("En Stock");
		statut.getItems().add("En Rupture de stock");

		Label motifLabel = new Label("Motif :");
		motif = new TextField();

		// save
		ButtonBar control = new ButtonBar();
		Button ajouter = new Button("OK", new ImageView(okImg));
		Button cancel = new Button("Annuler", new ImageView(undoImg));
		// TODO
		Button fermer = new Button("Fermer", new ImageView(exitImg));

		control.getButtons().add(ajouter);
		control.getButtons().add(cancel);
		control.getButtons().add(fermer);
		control.setPadding(new Insets(10));

		contentMterielTmp.add(dateLabel, 0, 0);
		contentMterielTmp.add(dateOperation, 1, 0);

		contentMterielTmp.add(nomTypenLabel, 2, 0);
		contentMterielTmp.add(materielCombox, 3, 0);

		contentMterielTmp.add(quantiteLabel, 0, 1);
		contentMterielTmp.add(quantite, 1, 1);

		contentMterielTmp.add(uniteLabel, 2, 1);
		contentMterielTmp.add(unite, 3, 1);

		contentMterielTmp.add(qualiteLabel, 0, 2);
		contentMterielTmp.add(qualite, 1, 2);

		contentMterielTmp.add(motifLabel, 2, 2);
		contentMterielTmp.add(motif, 3, 2);

		if (isInventaire) {
			contentMterielTmp.add(statutLabel, 0, 3);
			contentMterielTmp.add(statut, 1, 3);
		}

		if (isInventaire) {

			ajouter.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					boolean hasError = valiateStockestock();
					if (!hasError) {

						populateTable(chantier, false, false, isInventaire);
						primaryStage.close();
					}
				}
			});
		}

		if (isStockage) {

			ajouter.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					boolean hasError = valiateStockestock();
					if (!hasError) {

						populateTable(chantier, isStockage, false, false);
						primaryStage.close();
					}
				}
			});
		}

		if (isDestockage) {

			ajouter.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					boolean hasError = valiateStockestock();
					if (!hasError) {

						populateTable(chantier, false, isDestockage, false);
						primaryStage.close();
					}
				}
			});
		}

		fermer.setOnAction(fermerStage(primaryStage));

		// contentMterielTmp.add(panIcon);
		contentMteriel.setTop(panHead);
		contentMteriel.setCenter(contentMterielTmp);
		contentMteriel.setBottom(control);

		BatisUtils.setIconImage(primaryStage);
		Scene scene = new Scene(contentMteriel, 600, 300);
		scene.getStylesheets().add(getClass().getResource("/css/batis.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	protected EventHandler<ActionEvent> fermerStage(Stage primaryStage) {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				primaryStage.close();
			}
		};

	}

	/**
	 * 
	 * @param nom
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getNomMateriel(String nom, boolean name) {
		String qualite = "";
		if (!nom.isEmpty()) {
			if (name) {
				qualite = nom.split("/")[0];
			} else {
				qualite = nom.split("/")[1];
			}

		}

		return qualite.trim();
	}

	/**
	 * 
	 * @param notSaved
	 * @param inventaire
	 * @param typeOp
	 * @return
	 */
	private void populateTable(Chantier chantier, boolean isStockage, boolean isDestockage, boolean isInventaire) {

		NumberFormat doubleFormat = NumberFormat.getNumberInstance();
		doubleFormat.setMaximumFractionDigits(2);

		String nomType = getMateriel().getSelectionModel().getSelectedItem();
		Long code = BatisUtils.getCodeMaterielByname(nomType);

		String dateOp = getDateOperation() != null
				? getDateOperation().getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
				: "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String qualite = getQualite().getText();
		String quantite = getQuantite().getText();
		String statut = getStatut().getSelectionModel().getSelectedItem();
		String unite = getUnite().getSelectionModel().getSelectedItem();
		String motif = null;

		if (getMotif() != null) {
			motif = getMotif().getText();
		}
		quantite = BatisUtils.deleteWhiteSpace(quantite);
		Double quant = Double.valueOf(quantite);

		if (isInventaire) {
			inventaire = new Inventaire();
			Date date;
			try {
				date = sdf.parse(dateOp);
				inventaire.setDateInventaire(date);
			} catch (ParseException e) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(" Erreur : {} ", e.getCause());
				}
			}
			// inventaire.setDateInventaire(new Date(dateOp));
			inventaire.setNomMateriel(nomType);
			inventaire.setQualite(qualite);
			inventaire.setStatut(statut);
			inventaire.setUniteMesure(unite);
			inventaire.setQuantite(quant);
			inventaire.setMotif(motif);
			inventaire.setIdChantier(chantier.getId());
			setInventaire(inventaire);
			inventaireDataTable.add(inventaire);
		}
		// Double quantiteDispo = null;

		if (isDestockage || isStockage) {

			quant = isDestockage ? quant * -1 : quant;
			// quantiteDispo = getQunatiteDisponible(_code,_qualite);
			stockDestock = new StockageDestockage();

			Date date;
			try {
				date = sdf.parse(dateOp);
				stockDestock.setDate(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// stockDestock.setDate(new Date(dateOp));
			stockDestock.setTypeOperation(isDestockage ? "Destockage" : "Stockage");
			stockDestock.setNomMateriel(nomType);
			stockDestock.setQualite(qualite);
			stockDestock.setStatut(statut);
			stockDestock.setUniteMesure(unite);
			stockDestock.setQuantite(quant);
			stockDestock.setCodeMateriel(code);
			stockDestock.setMotif(motif);
			stockDestock.setIdChantier(chantier.getId());
			setStockDestock(stockDestock);
			stockDestockDataTable.add(stockDestock);
		}

		// TODO FAIRE SETTER DESTOCKAGE
		// inventaireService.saveOrUpdate(inventaire);

		// return inventaire;
	}

	private boolean valiateStockestock() {

		boolean hasError = false;
		if (quantite.getText().isEmpty()) {
			// TODO COLOR
			// quantite.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
			/// JOptionPane.showMessageDialog(new BaseJframe(), "Le champ quantité est
			// obligatoire !", "Erreur",
			// JOptionPane.ERROR_MESSAGE);
			hasError = true;
		}
		if (qualite.getText().isEmpty()) {
			// quantite.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
			// JOptionPane.showMessageDialog(new BaseJframe(), "Le champ qualité est
			// obligatoire !", "Erreur",
			// JOptionPane.ERROR_MESSAGE);
			hasError = true;
		}

		return hasError;
	}

	@SuppressWarnings("unused")
	private Double getQunatiteDisponible(Long codeMat, String qualite) {

		Double qutite = stockDestockService.getQunatiteDispoByCode(codeMat, qualite);
		return qutite;
	}

	/**
	 * 
	 * @param table
	 * @param chantier
	 * @param b
	 * @param stockage
	 * @param frais
	 * @return
	 *//*
		 * public StockageDestockage populateTable(StockageDestockage
		 * stockDestock,boolean destockage,String typeOp, Chantier chantier) {
		 * 
		 * StockageDestockage stockageDestockage = new StockageDestockage();
		 * NumberFormat doubleFormat = NumberFormat.getNumberInstance();
		 * doubleFormat.setMaximumFractionDigits(2);
		 * 
		 * 
		 * String _nomType = getMateriel().getSelectionModel().getSelectedItem(); String
		 * _motif = null; String _quantite = getQuantite().getText(); _quantite =
		 * BatisUtils.deleteWhiteSpace(_quantite); String nomType =
		 * getNomMateriel(_nomType,true); String qualite =
		 * getNomMateriel(_nomType,false); Long _code =
		 * BatisUtils.getCodeMaterielByname(nomType); Double _quant = new
		 * Double(_quantite); String _unite =
		 * getUnite().getSelectionModel().getSelectedItem();
		 * 
		 * Double quantiteDispo = null; if (destockage) { _motif = getMotif().getText();
		 * _quant = _quant*-1;
		 * 
		 * quantiteDispo = getQunatiteDisponible(_code,qualite);
		 * 
		 * }
		 * 
		 * if((destockage && quantiteDispo > 0 && quantiteDispo > _quant*-1) ||
		 * !destockage ) {
		 * 
		 * String _date = getDateOperation() != null ?
		 * getDateOperation().getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"
		 * )): "";
		 * 
		 * _date = _date.replaceAll("-", "/"); String _qualite = getQualite().getText();
		 * 
		 * String _statut = getStatut().getSelectionModel().getSelectedItem();
		 * 
		 * 
		 * 
		 * Date dateOp = BatisUtils.formatBatisDate(_date);
		 * 
		 * stockDestock.setDate(dateOp); stockDestock.setTypeOperation(typeOp);
		 * stockDestock.setNomMateriel(nomType); stockDestock.setQualite(_qualite);
		 * stockDestock.setStatut(_statut); stockDestock.setUniteMesure(_unite);
		 * stockDestock.setQuantite(_quant); stockDestock.setCodeMateriel(_code);
		 * stockDestock.setMotif(_motif); stockDestock.setIdChantier(chantier.getId());
		 * setStockDestock(stockDestock);
		 * 
		 * }else { //JOptionPane.showMessageDialog(new BaseJframe(),
		 * "Il ne reste que "+quantiteDispo+" "+nomType+" "+_unite+" dans le stock", ///
		 * "Erreur", JOptionPane.ERROR_MESSAGE); //TODO }
		 * 
		 * return stockageDestockage; }
		 */

	/**
	 * @return the materiel
	 */
	public ComboBox<String> getMateriel() {
		return materielCombox;
	}

	/**
	 * @param materiel the materiel to set
	 */
	public void setMateriel(ComboBox<String> materiel) {
		this.materielCombox = materiel;
	}

	/**
	 * @return the qualite
	 */
	public TextField getQualite() {
		return qualite;
	}

	/**
	 * @param qualite the qualite to set
	 */
	public void setQualite(TextField qualite) {
		this.qualite = qualite;
	}

	/**
	 * @return the quantite
	 */
	public TextField getQuantite() {
		return quantite;
	}

	/**
	 * @param quantite the quantite to set
	 */
	public void setQuantite(DoubleTextField quantite) {
		this.quantite = quantite;
	}

	/**
	 * @return the unite
	 */
	public ComboBox<String> getUnite() {
		return unite;
	}

	/**
	 * @param unite the unite to set
	 */
	public void setUnite(ComboBox<String> unite) {
		this.unite = unite;
	}

	/**
	 * @return the statut
	 */
	public ComboBox<String> getStatut() {
		return statut;
	}

	/**
	 * @param statut the statut to set
	 */
	public void setStatut(ComboBox<String> statut) {
		this.statut = statut;
	}

	/**
	 * @return the stockDestock
	 */
	public StockageDestockage getStockDestock() {

		return stockDestock;
	}

	/**
	 * @param stockDestock the stockDestock to set
	 */
	public void setStockDestock(StockageDestockage stockDestock) {
		this.stockDestock = stockDestock;
	}

	/**
	 * @return the motif
	 */
	public TextField getMotif() {
		return motif;
	}

	/**
	 * @param motif the motif to set
	 */
	public void setMotif(TextField motif) {
		this.motif = motif;
	}

	/**
	 * @return the inventaire
	 */
	public Inventaire getInventaire() {
		return inventaire;
	}

	/**
	 * @param inventaire the inventaire to set
	 */
	public void setInventaire(Inventaire inventaire) {
		this.inventaire = inventaire;
	}

	/**
	 * @return the dateOperation
	 */
	public DatePicker getDateOperation() {
		return dateOperation;
	}

	/**
	 * @return the stockDestockService
	 */
	public StockageDestockageService getStockDestockService() {
		return stockDestockService;
	}

	/**
	 * @param stockDestockService the stockDestockService to set
	 */
	public void setStockDestockService(StockageDestockageService stockDestockService) {
		this.stockDestockService = stockDestockService;
	}

	/**
	 * @return the materielCombox
	 */
	public ComboBox<String> getMaterielCombox() {
		return materielCombox;
	}

	/**
	 * @param materielCombox the materielCombox to set
	 */
	public void setMaterielCombox(ComboBox<String> materielCombox) {
		this.materielCombox = materielCombox;
	}

	/**
	 * @return the stockDestockDataTable
	 */
	public ObservableList<StockageDestockage> getStockDestockDataTable() {
		return stockDestockDataTable;
	}

	/**
	 * @param stockDestockDataTable the stockDestockDataTable to set
	 */
	public void setStockDestockDataTable(ObservableList<StockageDestockage> stockDestockDataTable) {
		this.stockDestockDataTable = stockDestockDataTable;
	}

	/**
	 * @return the stockDestockTable
	 */
	/*
	 * public TableView<StockageDestockage> getStockDestockTable() { return
	 * stockDestockTable; }
	 * 
	 *//**
		 * @param stockDestockTable the stockDestockTable to set
		 *//*
			 * public void setStockDestockTable(TableView<StockageDestockage>
			 * stockDestockTable) { this.stockDestockTable = stockDestockTable; }
			 */

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
	 * @param dateOperation the dateOperation to set
	 */
	public void setDateOperation(DatePicker dateOperation) {
		this.dateOperation = dateOperation;
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
	 * @return the inventaireDataTable
	 */
	public ObservableList<Inventaire> getInventaireDataTable() {
		return inventaireDataTable;
	}

	/**
	 * @param inventaireDataTable the inventaireDataTable to set
	 */
	public void setInventaireDataTable(ObservableList<Inventaire> inventaireDataTable) {
		this.inventaireDataTable = inventaireDataTable;
	}

}

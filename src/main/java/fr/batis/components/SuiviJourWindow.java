package main.java.fr.batis.components;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.java.fr.batis.components.common.CommonUtils;
import main.java.fr.batis.components.common.DateConverterHelper;
import main.java.fr.batis.components.common.BatisUtils;
import main.java.fr.batis.entites.CompteRenduJournalier;
import main.java.fr.batis.entites.PhaseConstruction;
import main.java.fr.batis.enumeration.TypeSuivi;

public class SuiviJourWindow {

	TableView<CompteRenduJournalier> suiviTable;
	private CommonUtils commonUtils;
	private DatePicker dateDujour;
	private TableView<?> table;
	private TextField titre;
	private Label dateLabel;
	private TextField date;
	private ComboBox<String> typeSuivi;
	private TextArea description;
	@SuppressWarnings("unused")
	private ObservableList<CompteRenduJournalier> suiviData;

	public SuiviJourWindow() {
		super();
		commonUtils = new CommonUtils();
	}

	public SuiviJourWindow(ObservableList<CompteRenduJournalier> suiviTableData, PhaseConstruction slsctedPhase) {
		commonUtils = new CommonUtils();

		try {
			initComponent(new Stage(), suiviTableData, slsctedPhase);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void initComponent(Stage primaryStage, ObservableList<CompteRenduJournalier> suiviTableData,
			PhaseConstruction phase) {

		Image closeImg = new Image(getClass().getResourceAsStream("/images/exit.png"));
		Image editImg = new Image(getClass().getResourceAsStream("/images/edit.png"));
		Image addImg = new Image(getClass().getResourceAsStream("/images/plus.png"));

		Node panIcon = commonUtils.getHeader("Ajout d'un suivi journalier");

		GridPane contentSuiviTmp = commonUtils.getGridPanel();
		BorderPane contentSuivi = new BorderPane();

		dateLabel = new Label("Date :");
		dateDujour = DateConverterHelper.getConvetedDate();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dateJ = dateFormat.format(new Date());
		date = new TextField(dateJ);

		Label titreLabel = new Label("Titre :");
		titre = new TextField();
		typeSuivi = new ComboBox<String>();

		Label typeLabel = new Label("Type :");
		for (TypeSuivi type : TypeSuivi.values()) {
			typeSuivi.getItems().add(type.getDescription());
		}

		Label descriptionLabel = new Label("Type :");
		description = new TextArea();

		contentSuiviTmp.add(dateLabel, 0, 0);
		contentSuiviTmp.add(date, 1, 0);

		contentSuiviTmp.add(titreLabel, 0, 1);
		contentSuiviTmp.add(titre, 1, 1);

		contentSuiviTmp.add(typeLabel, 0, 2);
		contentSuiviTmp.add(typeSuivi, 1, 2);

		contentSuiviTmp.add(descriptionLabel, 0, 3);
		contentSuiviTmp.add(description, 1, 3);
		// save
		ButtonBar buttonsBar = new ButtonBar();

		Button ajouter = new Button("Ajouter", new ImageView(addImg));
		Button fermer = new Button("Fermer", new ImageView(closeImg));
		Button modifier = new Button("Modifier", new ImageView(editImg));

		buttonsBar.getButtons().add(ajouter);
		buttonsBar.getButtons().add(modifier);
		buttonsBar.getButtons().add(fermer);

		buttonsBar.setPadding(new Insets(10));

		/*
		 * panTableContent.add(getTable().getTableHeader(), BorderLayout.NORTH);
		 * panTableContent.add(scrollPane, BorderLayout.CENTER);
		 */

		contentSuivi.setTop(panIcon);
		contentSuivi.setCenter(contentSuiviTmp);
		contentSuivi.setBottom(buttonsBar);

		BatisUtils.setIconImage(primaryStage);
		Scene scene = new Scene(contentSuivi, 600, 370);
		scene.getStylesheets().add(getClass().getResource("/css/batis.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();

		ajouter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				suiviTableData.add(populateTable(phase));
				primaryStage.close();
			}

		});

		fermer.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				primaryStage.close();
			}
		});

	}

	/**
	 * 
	 * @param phase
	 * @return
	 */
	private CompteRenduJournalier populateTable(PhaseConstruction phase) {

		CompteRenduJournalier cpteRendu = new CompteRenduJournalier();
		cpteRendu.setDate(getDate().getText());
		cpteRendu.setTitre(getTitre().getText());
		cpteRendu.setDescription(getDescription().getText());
		cpteRendu.setTypeSuivi(getTypeSuivi().getSelectionModel().getSelectedItem());
		phase.getCompteRenduJournalier().add(cpteRendu);
		cpteRendu.setPhaseConstruction(phase);
		return cpteRendu;
	}

	/**
	 * @return the suiviTable
	 */
	public TableView<CompteRenduJournalier> getSuiviTable() {
		return suiviTable;
	}

	/**
	 * @param suiviTable the suiviTable to set
	 */
	public void setSuiviTable(TableView<CompteRenduJournalier> suiviTable) {
		this.suiviTable = suiviTable;
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
	 * @return the table
	 */
	public TableView<?> getTable() {
		return table;
	}

	/**
	 * @param table the table to set
	 */
	public void setTable(TableView<?> table) {
		this.table = table;
	}

	/**
	 * @return the dateDujour
	 */
	public DatePicker getDateDujour() {
		return dateDujour;
	}

	/**
	 * @param dateDujour the dateDujour to set
	 */
	public void setDateDujour(DatePicker dateDujour) {
		this.dateDujour = dateDujour;
	}

	/**
	 * @return the titre
	 */
	public TextField getTitre() {
		return titre;
	}

	/**
	 * @param titre the titre to set
	 */
	public void setTitre(TextField titre) {
		this.titre = titre;
	}

	/**
	 * @return the dateLabel
	 */
	public Label getDateLabel() {
		return dateLabel;
	}

	/**
	 * @param dateLabel the dateLabel to set
	 */
	public void setDateLabel(Label dateLabel) {
		this.dateLabel = dateLabel;
	}

	/**
	 * @return the date
	 */
	public TextField getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(TextField date) {
		this.date = date;
	}

	/**
	 * @return the typeSuivi
	 */
	public ComboBox<String> getTypeSuivi() {
		return typeSuivi;
	}

	/**
	 * @param typeSuivi the typeSuivi to set
	 */
	public void setTypeSuivi(ComboBox<String> typeSuivi) {
		this.typeSuivi = typeSuivi;
	}

	/**
	 * @return the description
	 */
	public TextArea getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(TextArea description) {
		this.description = description;
	}

}

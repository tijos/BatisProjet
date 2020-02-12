package main.java.fr.batis.components.tabs;

import org.controlsfx.tools.Borders;

import main.java.fr.batis.components.common.CommonUtils;
import main.java.fr.batis.components.common.BatisUtils;
import main.java.fr.batis.components.tables.EmployersTable;
import main.java.fr.batis.entites.Chantier;
import main.java.fr.batis.entites.Employe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class Administration {

	private CommonUtils commonUtils;
	private TableView<Employe> employeTable;
	private ObservableList<Employe> employeData;
	
	public Administration() {
		this.commonUtils = new CommonUtils();
	}
	
	/**
	 * 
	 * @param chantier
	 * @return
	 */
	public Tab getVoletAdminTab(Chantier chantier) {
		Image tabAdminImg = new Image(getClass().getResourceAsStream("/images/pilotageTab.png"));
		Image tabEmployeImg = new Image(getClass().getResourceAsStream("/images/employes.png"));
		Tab adminTab = new Tab("Administration");
		adminTab.setGraphic(new ImageView(tabAdminImg));
		
		TabPane adminTabPane = new TabPane();
		Tab employeTab = getEmployesTab(chantier);
		employeTab.setGraphic(new ImageView(tabEmployeImg));
		
		Tab parametresTab = new ParametresTab().getParametresTab(chantier);
		
		adminTabPane.getTabs().add(employeTab);
		adminTabPane.getTabs().add(parametresTab);
		adminTab.setContent(adminTabPane);
		
		return adminTab;
	}
	
	

	/**
	 * @param chantier
	 * @param employeTab
	 */
	private Tab getEmployesTab(Chantier chantier) {
		
		Image editImg = new Image(getClass().getResourceAsStream("/images/edit.png"));
		Image saveImg = new Image(getClass().getResourceAsStream("/images/save.png"));
		Image addImg = new Image(getClass().getResourceAsStream("/images/plus.png"));
		
		Tab employeTab = new Tab("Gestion des employés");
		BorderPane employePanel = new BorderPane();
		Node panIcon = commonUtils.getHeader("Liste des employés");
	
		employeTable = new EmployersTable().getTableauEmploye();
		
		employeData = FXCollections.observableArrayList(
				chantier.getListeEmployes()
				);

		employeTable.setEditable(true);
		employeTable.setItems(employeData);

		final GridPane buttonsBar =  getCommonUtils().getGridPanel();
		
		Button modifier = new Button("Modifier");
		modifier.setGraphic(new ImageView(editImg));

		Button enregistrer = new Button("Enregistrer");
		enregistrer.setGraphic(new ImageView(saveImg));
		
		Button ajouter = new Button("Ajouter un employé");
		ajouter.setGraphic(new ImageView(addImg));
		
		buttonsBar.add(modifier, 2, 2);
		buttonsBar.add(enregistrer, 4, 2);
		buttonsBar.add(ajouter, 6, 2);

		Node wraperEmployesTable = Borders.wrap(employeTable)
				.etchedBorder()         	
	  	    	.innerPadding(0)
        		. buildAll();
		employePanel.setTop(panIcon);
		employePanel.setCenter(wraperEmployesTable);
		employePanel.setBottom(buttonsBar);
		employeTab.setContent(employePanel);
		
		ajouter.setOnAction(BatisUtils.openAjoutEmployeWindow(chantier,employeData));
		enregistrer.setOnAction(BatisUtils.saveChantier(chantier));
		
		return employeTab;
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
	 * @return the employeTable
	 */
	public TableView<Employe> getEmployeTable() {
		return employeTable;
	}

	/**
	 * @param employeTable the employeTable to set
	 */
	public void setEmployeTable(TableView<Employe> employeTable) {
		this.employeTable = employeTable;
	}

	/**
	 * @return the employeData
	 */
	public ObservableList<Employe> getEmployeData() {
		return employeData;
	}

	/**
	 * @param employeData the employeData to set
	 */
	public void setEmployeData(ObservableList<Employe> employeData) {
		this.employeData = employeData;
	}

	
}

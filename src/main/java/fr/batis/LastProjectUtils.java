package main.java.fr.batis;

import java.util.Optional;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.java.fr.batis.components.ChantierWindow;
import main.java.fr.batis.components.common.BatisUtils;
import main.java.fr.batis.entites.Chantier;
import main.java.fr.batis.entites.CurrentChantier;
import main.java.fr.batis.entites.LastOpened;
import main.java.fr.batis.service.ChantierService;

/**
 * 
 * @author tijos
 *
 */
public class LastProjectUtils {
	private ChantierService chantierService = new ChantierService();

	public void getLastOpenedProjetct(LastOpened lastOpenedChantier, TabPane tabPanePrincipal,
			CurrentChantier currentChantier, Image icon, Image logo) {

		ChantierService chanteirService = new ChantierService();
		Chantier chantier = chanteirService.findById(lastOpenedChantier.getProjectId());

		if (chantier != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("");
			alert.setHeaderText(" ");

			alert.setGraphic(new ImageView(logo));
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(icon);
			String s = "Voulez-vous ouvrir le chantier << " + chantier.getNomProjet() + " >> ouvert précédemment ?";
			alert.setContentText(s);
			alert.setResizable(true);
			alert.getDialogPane().setPrefSize(600, 200);

			alert.getDialogPane().getStylesheets().add(getClass().getResource("/css/dialogue.css").toExternalForm());
			alert.getDialogPane().getStyleClass().add("myDialog");
			Optional<ButtonType> result = alert.showAndWait();

			if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
				// Hibernate.initialize(chantier.getPhasesConstruction());
				new ChantierWindow().getChantierWindow(chantier, tabPanePrincipal);
				currentChantier.setChantier(chantier);
				currentChantier.setId(chantier.getId());
			}
		}
	}

	public void refleshWindow(MenuItem menuItem, CurrentChantier currentChantier, TabPane tabPanePrincipal) {
		menuItem.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

				if (currentChantier.getChantier() != null) {
					Chantier reflechedChantier = chantierService.findById(currentChantier.getChantier().getId());
					BatisUtils.reflesh(reflechedChantier, tabPanePrincipal);
				}

			}
		});
	}

}

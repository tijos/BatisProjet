package main.java.fr.batis.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import main.java.fr.batis.components.tabs.Administration;
import main.java.fr.batis.components.tabs.Comptabilite;
import main.java.fr.batis.components.tabs.PilotageTab;
import main.java.fr.batis.components.tabs.TableauDeBord;
import main.java.fr.batis.entites.Chantier;

/**
 * @author admin
 *
 */
public class ChantierWindow {

	private Chantier chantier;

	/**
	 * 
	 */

	public ChantierWindow() {

	}

	public ChantierWindow(Chantier chantier) {
		this.chantier = chantier;
	}

	/**
	 * 
	 * @param tabPanePrincipale
	 * @return
	 */
	public TabPane getChantierWindow(Chantier chantier, TabPane tabPanePrincipale) {

		this.chantier = chantier;
		tabPanePrincipale.getTabs().clear();

		Tab tableauDebord = new TableauDeBord().getTableauDeBord(chantier);

		// TABLEAU DE BORD

		tabPanePrincipale.getTabs().add(tableauDebord);

		// PILOTAGE

		Tab pilotageTab = new PilotageTab().getPilotageTab(chantier);

		// ADMINISTARTION

		Tab adminTab = new Administration().getVoletAdminTab(chantier);

		// COMPTABILITE

		Tab comptabiliteTab = new Comptabilite().getComptabiliteTab(chantier);

		// ADD
		tabPanePrincipale.getTabs().add(adminTab);
		tabPanePrincipale.getTabs().add(pilotageTab);
		tabPanePrincipale.getTabs().add(comptabiliteTab);
		return tabPanePrincipale;

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
	 * 
	 * @return
	 */
	public EventHandler<ActionEvent> diminuer() {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

			}
		};
	}

}

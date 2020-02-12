package main.java.fr.batis.components.common;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.java.fr.batis.components.AjoutEmployeWindow;
import main.java.fr.batis.components.ChantierWindow;
import main.java.fr.batis.entites.Chantier;
import main.java.fr.batis.entites.Devis;
import main.java.fr.batis.entites.Employe;
import main.java.fr.batis.entites.EmployeRole;
import main.java.fr.batis.entites.Frais;
import main.java.fr.batis.entites.Materiel;
import main.java.fr.batis.entites.NomsMateriaux;
import main.java.fr.batis.entites.PhaseConstruction;
import main.java.fr.batis.entites.Role;
import main.java.fr.batis.entites.VoletAdmnistratif;
import main.java.fr.batis.enumeration.ComparatorFactory;
import main.java.fr.batis.enumeration.RoleCode;
import main.java.fr.batis.enumeration.StatutAchatMateriel;
import main.java.fr.batis.enumeration.StatutFrais;
import main.java.fr.batis.enumeration.StatutPhase;
import main.java.fr.batis.service.ChantierService;
import main.java.fr.batis.service.DevisService;
import main.java.fr.batis.service.MaterielService;
import main.java.fr.batis.service.NomsMaterieuxService;
import main.java.fr.batis.service.PhaseService;

public class BatisUtils {
	private static ChantierService chantierService = new ChantierService();
	private static DevisService devisService = new DevisService();
	private static PhaseService phaseService = new PhaseService();
	private static ImageUtils imageUtils = new ImageUtils();
	private static MaterielService materielService = new MaterielService();
	private static NomsMaterieuxService nomsMaterieuxService = new NomsMaterieuxService();

	public static void setIconImage(Stage primaryStage) {
		Image icon = imageUtils.getIcon();
		ImageView editView = new ImageView(icon);
		editView.setFitWidth(15);
		editView.setFitHeight(15);
		primaryStage.getIcons().add(icon);
	}

	/**
	 * @param tabPanePrincipale
	 */
	public static void refleshProject(TabPane tabPanePrincipale, Long idProjet) {
		if (idProjet != null) {
			Chantier reflechedChantier = chantierService.findById(idProjet);
			reflesh(reflechedChantier, tabPanePrincipale);
		}
	}

	/**
	 * 
	 * @param name
	 */
	public static void deleteDevis(String name, PhaseConstruction phase) {
		Devis devis = devisService.getDevisByNameAndPhase(name, phase);
		if (devis != null) {
			List<Devis> listeDevis = new ArrayList<Devis>();
			for (Devis devi : phase.getListDevis()) {
				if (!devi.equals(devis)) {
					listeDevis.add(devi);
				}
			}
			phase.getListDevis().clear();
			phase.setListDevis(listeDevis);
			devis.setPhase(null);
			devisService.evict(devis);
			devisService.delete(devis.getId());
			phaseService.saveOrUpdate(phase);
		}

	}

	/**
	 * 
	 * @param name
	 * @param phase
	 * @return
	 */
	public static Devis getDevisByNameAndPhase(String name, PhaseConstruction phase) {
		Devis devis = devisService.getDevisByNameAndPhase(name, phase);
		return devis;
	}

	/**
	 * 
	 * @param liste
	 * @param type
	 * @return
	 */
	public static EventHandler<ActionEvent> validatateIfPhaseIsSelected(ComboBox<String> liste, String type) {
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (liste.getSelectionModel().getSelectedItem() == null
						|| liste.getSelectionModel().getSelectedItem().isEmpty()) {
					BatisUtils.showAlert(Alert.AlertType.ERROR, null, "Erreur!",
							"Veuillez séléctionner " + type + " !");
					return;
				}
			}
		};
	}

	/**
	 */
	public static Devis createNewDevis(PhaseConstruction phase, String nomDevis, String description) {
		Devis devis = new Devis();
		devis.setNom(nomDevis);
		devis.setDescription(description);
		devis.setPhase(phase);
		return devis;
	}

	/**
	 * 
	 * @param chantier
	 * @param employeData
	 * @return
	 */
	public static EventHandler<ActionEvent> openAjoutEmployeWindow(Chantier chantier,
			ObservableList<Employe> employeData) {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				AjoutEmployeWindow ajoutEmp = new AjoutEmployeWindow(chantier, employeData);
				ajoutEmp.initComponent(new Stage());
			}
		};
	}

	/**
	 * 
	 * @param devis
	 * @return
	 */
	public static List<Materiel> getListeMaterielAchete(Devis devis) {
		List<Materiel> listeMater = devis.getListMateriaux();
		List<Materiel> listeMateriauxAAcheter = new ArrayList<Materiel>();
		if (!listeMater.isEmpty()) {

			for (Materiel materiel : listeMater) {
				if (StatutAchatMateriel.ACHETE_T.getCode().equals(materiel.getCodeStatutAchat())
						|| StatutAchatMateriel.ACHETE_P.getCode().equals(materiel.getCodeStatutAchat())) {
					listeMateriauxAAcheter.add(materiel);
				}
			}
		}
		return listeMateriauxAAcheter;
	}

	/**
	 * 
	 * @param devis
	 * @return
	 */
	public static EventHandler<ActionEvent> saveDevis(Devis devis) {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				devisService.saveOrUpdate(devis);
			}
		};

	}

	/**
	 * 
	 * @param devis
	 * @return
	 */
	public static EventHandler<ActionEvent> saveListMateriel(List<Materiel> devis) {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				for (Materiel mat : devis) {
					materielService.saveOrUpdate(mat);
				}

			}
		};

	}

	/**
	 * 
	 * @return
	 */
	public static EventHandler<ActionEvent> saveChantier(Chantier chantier) {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// Chantier chantier =
				// chantierService.findById(ChantierWindow.chantier.getId());
				chantierService.saveOrUpdate(chantier);
			}
		};

	}

	/**
	 * 
	 * @param phase
	 * @param newDescription
	 * @return
	 */
	public static boolean chechModification(PhaseConstruction phase, String newDescription) {
		boolean response = newDescription.equals(phase.getDescription());
		if (!response) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Information");
			alert.setHeaderText("Hop !");
			alert.setContentText("Les dernières modifications n'ont pas été sauvegardées!");
			alert.showAndWait();
		}
		return response;
	}

	/**
	 * 
	 * @param alertType
	 * @param owner
	 * @param title
	 * @param message
	 */
	public static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
		Image icon = imageUtils.getIcon();
		// Image logo = imageUtils.getLogo();
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		// alert.setGraphic(new ImageView(logo));

		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(icon);
		alert.initOwner(owner);
		alert.show();
	}

	/**
	 * 
	 * @param chantier
	 * @return
	 */
	public static PhaseConstruction getPhaseEncours(Chantier chantier) {
		List<PhaseConstruction> listPhase = chantier.getPhasesConstruction();
		PhaseConstruction phaseEncours = null;
		if (listPhase != null && !listPhase.isEmpty()) {
			phaseEncours = listPhase.get(0);
			for (PhaseConstruction phase : listPhase) {
				if (phase.getCodeStatut() != null
						&& (StatutPhase.ENCOURS.getCode().equals(phase.getCodeStatut().getCode()))) {
					phaseEncours = phase;
				}
			}
		}
		return phaseEncours;
	}

	/**
	 * 
	 * @param chantier
	 * @return
	 */
	public static PhaseConstruction getDernierPhase(Chantier chantier) {
		List<PhaseConstruction> listPhase = chantier.getPhasesConstruction();
		PhaseConstruction dernierPhase = null;
		if (listPhase != null && !listPhase.isEmpty()) {
			dernierPhase = listPhase.get(listPhase.size() - 1);
		}
		return dernierPhase;
	}

	/**
	 * 
	 * @param chantier
	 * @param phaseCst
	 */
	public static void demarrerPhase(Chantier chantier, PhaseConstruction phaseCst) {

		PhaseConstruction phasePrecedente = getPhasePrecedente(chantier, phaseCst);
		if (phasePrecedente == null || (phasePrecedente != null
				&& StatutPhase.TERMINE.getCode().equals(phasePrecedente.getCodeStatut().getCode()))) {
			// cloturerPhase(phasePrecedente);
			demarrerLaPhase(phaseCst);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Information");
			alert.setHeaderText("Alert");
			alert.setContentText("La phase précédente n'est pas encore terminée !");
			alert.showAndWait();
		}

	}

	private static void demarrerLaPhase(PhaseConstruction phaseCst) {
		String dateOp = getFormatedDate(new Date());
		phaseCst.setDateDebutReel(dateOp);
		phaseCst.setCodeStatut(StatutPhase.ENCOURS);
		phaseService.saveOrUpdate(phaseCst);

	}

	public static void demmarerSelectedPhase(Chantier chantier, Button demarrerPhase, PhaseConstruction phase) {

		if (phase != null) {
			if (StatutPhase.ENPREPARATION.getCode().equals(phase.getCodeStatut().getCode())) {
				BatisUtils.demarrerPhase(chantier, phase);
				demarrerPhase.setDisable(true);
			} else {

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information");
				alert.setHeaderText("Alert");
				alert.setContentText("La phase " + phase.getNom() + " a été déjà demarrée");
				alert.showAndWait();

				if (demarrerPhase != null) {
					demarrerPhase.setDisable(true);
				}
			}
		}
	}

	/**
	 * 
	 * @param chantier
	 */
	public static void demmarrerTravaux(Chantier chantier) {
		List<PhaseConstruction> listPhase = chantier.getPhasesConstruction();
		if (listPhase != null && !listPhase.isEmpty()) {
			if (StatutPhase.ENPREPARATION.getDescription().equals(chantier.getStatut())
					|| StatutPhase.TERMINE.getDescription().equals(chantier.getStatut())) {

				if (StatutPhase.TERMINE.getDescription().equals(chantier.getStatut())) {
					// String motif = "Ajout d'une phase supplémentaire";
					// TODO
					// setHistorique(chantier, StatutPhase.REOUVERT.getDescription(), motif);
				}
				if (StatutPhase.ENPREPARATION.getDescription().equals(chantier.getStatut())) {
					// String motif = "Fin et debut de la phase";
					// TODO
					// setHistorique(chantier, StatutPhase.ENCOURS.getDescription(), motif);
				}

				// TODO
				// chantierService.saveOrUpdate(chantier);
			}
		}
	}
	/*
	 * public static void setHistorique(Chantier chantier, String statut, String
	 * motif) { HistoriqueStatutChantier historique = new
	 * HistoriqueStatutChantier(); String dateOp = getFormatedDate(new Date());
	 * historique.setStatut(statut); historique.setDateChangement(dateOp);
	 * historique.setChantier(chantier); historique.setMotifChangement(motif);
	 * chantier.setStatut(statut); if (chantier.getListeHistorique() != null) {
	 * chantier.getListeHistorique().add(historique); } else {
	 * List<HistoriqueStatutChantier> listHistorique = new
	 * ArrayList<HistoriqueStatutChantier>(); listHistorique.add(historique);
	 * chantier.setListeHistorique(listHistorique); } }
	 */

	public static String getFormatedDate(Date date) {
		String dateOp = "";
		if (date != null) {
			SimpleDateFormat formater = null;
			formater = new SimpleDateFormat("dd/MM/yyyy");
			dateOp = formater.format(date);
		}

		return dateOp;
	}

	public static Employe getChefChantier(Chantier chantier) {
		Set<Employe> listeEmployes = chantier.getListeEmployes();
		Employe chefChantier = null;
		if (listeEmployes != null && !listeEmployes.isEmpty()) {
			for (Employe employe : listeEmployes) {
				if (isChefChantier(employe)) {
					chefChantier = employe;
					break;
				}

			}
		}

		return chefChantier;
	}

	/**
	 * @param chefChantier
	 * @param employe
	 * @return
	 */
	public static Boolean isChefChantier(Employe employe) {
		Set<Role> listeRoles = employe.getListRole();
		Boolean chefChantier = false;
		for (Role role : listeRoles) {
			if (RoleCode.CHEF_CHATIER.getCode().equals(role.getCode())
					|| RoleCode.GESTIONNAIRE.getCode().equals(role.getCode())) {
				chefChantier = true;
				break;
			}
		}

		return chefChantier;
	}

	public static Employe getChefComptable(Chantier chantier) {

		Set<Employe> listeEmployes = chantier.getListeEmployes();
		Employe chefChantier = null;
		if (listeEmployes != null && !listeEmployes.isEmpty()) {
			for (Employe employe : listeEmployes) {
				if (isComptable(employe)) {
					chefChantier = employe;
					break;
				}
			}
		}

		return chefChantier;
	}

	/**
	 * @param chefChantier
	 * @param employe
	 * @return
	 */
	public static Boolean isComptable(Employe employe) {
		Set<Role> listeRoles = employe.getListRole();
		Boolean isCptable = false;
		for (Role role : listeRoles) {
			if (RoleCode.COMPTABLE.getCode().equals(role.getCode())
					|| RoleCode.GESTIONNAIRE.getCode().equals(role.getCode())) {
				isCptable = true;
				break;
			}
		}

		return isCptable;
	}

	public static Chantier getChantierById(Long id) {
		Chantier chantier = null;
		chantier = chantierService.findById(id);
		return chantier;
	}

	public static Chantier getChantierByName(String nom) {
		Chantier chantier = null;
		chantier = chantierService.getChantierByName(nom);
		return chantier;
	}

	/**
	 * 
	 * @param listChantier
	 * @param nom
	 * @return
	 */
	public static Chantier getChantierByName(List<Chantier> listChantier, String nom) {
		Chantier chantier = null;
		if (listChantier != null && !listChantier.isEmpty()) {
			for (Chantier ch : listChantier) {
				if (ch.getNomProjet() != null && ch.getNomProjet().equals(nom)) {
					chantier = ch;
					break;
				}
			}
		}

		return chantier;
	}

	/**
	 * Permet de cloturer une phase
	 * 
	 * @param phaseCst
	 */
	public static void cloturerPhase(PhaseConstruction phaseCst) {
		if (phaseCst != null) {
			phaseCst.setCodeStatut(StatutPhase.TERMINE);
			String dateOp = getFormatedDate(new Date());
			phaseCst.setDateFinReel(dateOp);
			phaseService.saveOrUpdate(phaseCst);
		}
	}

	/**
	 * 
	 * @param chantier
	 * @param number
	 * @return une phase correspondant au nom
	 */
	public static PhaseConstruction getPhaseByNumber(Chantier chantier, Integer number) {
		List<PhaseConstruction> listPhase = chantier.getPhasesConstruction();
		PhaseConstruction phase = null;
		if (listPhase != null && !listPhase.isEmpty()) {
			for (PhaseConstruction phaseItem : listPhase) {

				if (phaseItem.getNumero() != null && number.equals(phaseItem.getNumero())) {
					phase = phaseItem;
				}
			}
		}
		return phase;
	}

	/***
	 * verifie si il y a encore un materiel qui n'est pas encore acheté
	 * 
	 * @param devis
	 * @return
	 */
	public static boolean isAllMateriauxAchetes(Devis devis) {
		List<Materiel> listMat = devis.getListMateriaux();
		for (Materiel materiel : listMat) {
			if (materiel.getCodeStatutAchat().equals(StatutAchatMateriel.A_ACHETER.getCode())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public static PhaseConstruction getPhaseByName(String name) {

		PhaseConstruction phase = null;
		phase = phaseService.getPhaseByName(name);
		return phase;
	}

	/**
	 * 
	 * @param chantier
	 * @param name
	 * @return
	 */
	public static PhaseConstruction getPhaseByName(Chantier chantier, String name) {
		List<PhaseConstruction> listPhase = chantier.getPhasesConstruction();
		PhaseConstruction phase = null;
		if (name != null && !name.trim().isEmpty()) {
			if (listPhase != null && !listPhase.isEmpty()) {
				for (PhaseConstruction phaseItem : listPhase) {
					if (phaseItem.getNom() != null && name.equals(phaseItem.getNom())) {
						phase = phaseItem;
					}
				}
			}
		}
		return phase;
	}

	public static Materiel getMaterielByNameAndDevis(String name, Devis devis) {

		Materiel mat = null;
		boolean found = false;

		if (devis != null && !found) {
			List<Materiel> listeMat = devis.getListMateriaux();
			for (Materiel materiel : listeMat) {
				if (name.equals(materiel.getNom())
						&& StatutAchatMateriel.A_ACHETER.getCode().equals(materiel.getCodeStatutAchat())) {
					mat = materiel;
					found = true;
				}
			}
		}

		return mat;
	}

	/**
	 * 
	 * @param chantier
	 * @param name
	 * @param phase
	 * @return
	 */
	public static Materiel getMaterielByNameAnd(Chantier chantier, String name, String phase) {
		List<PhaseConstruction> listPhase = chantier.getPhasesConstruction();
		Materiel mat = null;
		boolean found = false;
		// TODO passer par le service et cherche dans la bdd
		// Materiel materiel = materielService.findByNameIdChantierAndStatut(name,
		// chantier.getId(), StatutMat.A_ACHETER.getCode());

		if (listPhase != null && !listPhase.isEmpty()) {
			for (PhaseConstruction phaseItem : listPhase) {
				for (Devis devis : phaseItem.getListDevis()) {
					if (devis != null && !found) {
						List<Materiel> listeMat = devis.getListMateriaux();
						for (Materiel materiel : listeMat) {
							if (name.equals(materiel.getNom())
									&& StatutAchatMateriel.A_ACHETER.getCode().equals(materiel.getCodeStatutAchat())) {
								mat = materiel;
								found = true;
							}
						}
					}
				}
			}
		}
		return mat;
	}

	public static void setRole(Chantier projet, Employe chefChantier, Role role) {
		EmployeRole empRoleChantier = new EmployeRole();
		empRoleChantier.setIdChantier(projet.getId());
		empRoleChantier.setIdEmploye(chefChantier.getId());
		empRoleChantier.setIdRole(role.getId());
		// TODO
		// employeRoleService.saveOrUpdate(empRoleChantier);
	}

	/**
	 * 
	 * @param chantier
	 * @return
	 */
	public static Double getDifferencePrixAchatMat(Chantier chantier) {
		Double montant = Double.valueOf(0);
		for (PhaseConstruction phase : chantier.getPhasesConstruction()) {
			for (Devis devis : phase.getListDevis()) {
				montant = montant + getExecedantManquant(devis);

			}
		}

		return montant;
	}

	/**
	 * 
	 * @param chantier
	 * @return
	 */
	public static Double getDifferencePrixPartielAchatMat(Chantier chantier) {
		Double montantPrevu = getMontantPartielTotal(chantier);
		Double montantReel = initMontantTotalReel(chantier);
		// return getNumberFormatter(montantPrevu-montantReel);
		return montantPrevu - montantReel;
	}

	/**
	 * 
	 * @param materiel
	 * @return
	 */
	public static Double getExecedantManquantByMateriel(Materiel materiel) {
		Double diff = 0.0;
		Double prixTotPrevu = getPrixTotalPrevu(materiel);
		Double prixTotReel = getPrixTotalReel(materiel);
		if (prixTotReel != null && prixTotPrevu != null) {
			diff = prixTotPrevu - prixTotReel;
		}

		return diff;
	}

	/**
	 * retourne la difference totale des prix
	 * 
	 * @param devis
	 * @return
	 */
	public static Double getExecedantManquant(Devis devis) {
		Double diff = 0.0;
		Double mo = 0.0;
		if (devis.getMainOeuvrePaye() != null) {
			mo = devis.getMainOeuvrePaye();
		}
		List<Materiel> listeMater = devis.getListMateriaux();
		if (!listeMater.isEmpty()) {
			for (Materiel materiel : listeMater) {

				// Double prixTotPrevu = getPrixTotalPrevu(materiel);
				Double prixTotPrevu = getPrixTotalPrevu(materiel);
				Double prixTotReel = getPrixTotalReel(materiel);

				prixTotPrevu = prixTotPrevu + getMainOeuvreDevis(prixTotPrevu, devis);
				if (prixTotReel != null && prixTotPrevu != null) {
					diff = diff + (prixTotPrevu - prixTotReel);
				}
			}

		}

		return diff - mo;
	}

	/**
	 * 
	 * @param qualification
	 * @return
	 */
	public static Integer getCodeQualification(String qualification) {
		Integer code = null;
		for (RoleCode value : RoleCode.values()) {

			if (value.getDescription().equals(qualification)) {
				code = value.getCode();
				break;
			}
		}
		return code;
	}

	/**
	 * 
	 * @param chantier
	 * @param tabPanePrincipale
	 */
	public static void reflesh(Chantier chantier, TabPane tabPanePrincipale) {

		new ChantierWindow().getChantierWindow(chantier, tabPanePrincipale);
	}

	// TODO UTILISER LA REQUETTE
	public static String getMontantTotalPrevu(Chantier chantier, Boolean fromTabBord) {
		Double montant = 0.0;
		for (PhaseConstruction phase : chantier.getPhasesConstruction()) {
			for (Devis devis : phase.getListDevis()) {
				montant = montant + getSommeTotalPrevuDevis(devis, fromTabBord);
			}
		}
		return getNumberFormatter(montant);
	}

	/**
	 * 
	 * @param devis
	 * @param fromTabBord
	 * @return
	 */
	public static Double getSommeTotalPrevuDevis(Devis devis, Boolean fromTabBord) {
		Double montant = 0.0;
		if (devis != null && devis.getListMateriaux() != null) {
			for (Materiel mat : devis.getListMateriaux()) {
				montant = montant + getPrixTotalPrevu(mat);
			}
		}
		if (fromTabBord) {
			montant = montant + getMainOeuvreDevis(montant, devis);
		}

		return montant;
	}

	public static Double getMainOeuvreDevis(Double montant2, Devis devis) {
		Double montant = 0.0;
		if (devis != null && devis.getPourcentageMO() != null) {
			Double pourc = devis.getPourcentageMO();
			montant = montant2 * pourc / 100;
		}
		return montant;
	}

	/**
	 * 
	 * @param materiel
	 * @return
	 */
	private static Double getPrixTotalPrevu(Materiel materiel) {
		// TODO SUPPRIMER LES VIDES
		Double prixTotPrevu = 0.0;
		if (StatutAchatMateriel.DEVIS_INIT.getCode().equals(materiel.getCodeDevisInitial())
				&& StatutAchatMateriel.A_ACHETER.getCode().equals(materiel.getCodeStatutAchat())) {
			Integer quantitePrevu = Integer.valueOf(deleteWhiteSpace(materiel.getQuantitePrevu()));
			Double prixUPrevu = Double.valueOf(deleteWhiteSpace(materiel.getPrixUnitairePrevu()));

			if (quantitePrevu != null && prixUPrevu != null) {
				prixTotPrevu = Double.valueOf(quantitePrevu * prixUPrevu);
			}

		}

		return prixTotPrevu;
	}

	/**
	 * 
	 * @param chantier
	 * @return
	 */
	public static String getMontantTotalReel(Chantier chantier) {
		Double montant = initMontantTotalReel(chantier);
		return getNumberFormatter(montant);
	}

	private static Double initMontantTotalReel(Chantier chantier) {
		Double montant = 0.0;
		for (PhaseConstruction phase : chantier.getPhasesConstruction()) {
			for (Devis devis : phase.getListDevis()) {
				montant = montant + getSommeTotaleDevis(devis);
				if (devis.getMainOeuvrePaye() != null) {
					Double moReel = devis.getMainOeuvrePaye();
					montant = montant + moReel;
				}
			}

		}
		return montant;
	}

	/**
	 * 
	 * @param devis
	 * @return
	 */
	public static Double getSommeTotaleDevis(Devis devis) {
		Double somme = 0.0;
		for (Materiel mat : devis.getListMateriaux()) {
			somme = somme + getPrixTotalReel(mat);
		}
		return somme;
	}

	private static Double getPrixPartielPrevu(Materiel materiel) {
		// TODO SUPPRIMER LES VIDES
		Double prixPartielP = 0.0;
		if (materiel.getQuantiteReel() != null) {
			Integer quantiteReel = Integer.valueOf(BatisUtils.deleteWhiteSpace(materiel.getQuantiteReel()));
			Double prixUPrevu = Double.valueOf(BatisUtils.deleteWhiteSpace(materiel.getPrixUnitairePrevu()));

			if (prixUPrevu != null) {
				prixPartielP = Double.valueOf(quantiteReel * prixUPrevu);
			}
		}

		return prixPartielP;
	}

	/**
	 * 
	 * @param chantier
	 * @return
	 */

	private static Double getMontantPartielTotal(Chantier chantier) {
		Double montant = 0.0;
		for (PhaseConstruction phase : chantier.getPhasesConstruction()) {
			for (Devis devis : phase.getListDevis()) {
				for (Materiel mat : devis.getListMateriaux()) {
					montant = montant + getPrixPartielPrevu(mat);
				}

				montant = montant + getMainOeuvreDevisApayer(devis);
			}

		}
		return montant;
	}

	/**
	 * 
	 * @param chantier
	 * @return
	 */
	public static String getMontantPartielPrevu(Chantier chantier) {

		Double montant = getMontantPartielTotal(chantier);
		return getNumberFormatter(montant);
	}

	public static Double getMainOeuvreDevisApayer(Devis devis) {
		Double montant = 0.0;
		// Double moPaye = 0.0;
		if (devis != null && devis.getListMateriaux() != null) {
			for (Materiel mat : devis.getListMateriaux()) {
				montant = montant + getPrixTotalPrevu(mat);
			}

			montant = getMainOeuvreDevis(montant, devis);
			// moPaye = devis.getMainOeuvrePaye();

		}
		return montant;
	}

	/**
	 * 
	 * @param montantPrevu
	 * @param diff
	 * @return
	 */
	public static Double getPourcentage(Double montantPrevu, Double diff) {
		return (diff * 100) / montantPrevu;
	}

	/**
	 * 
	 * @param materiel
	 * @return
	 */
	private static Double getPrixTotalReel(Materiel materiel) {
		// TODO SUPPRIMER LES VIDES
		Double prixTotReel = 0.0;
		if (materiel.getQuantiteReel() != null) {
			Integer quantiteReel = Integer.valueOf(BatisUtils.deleteWhiteSpace(materiel.getQuantiteReel()));
			Double prixUReel = Double.valueOf(BatisUtils.deleteWhiteSpace(materiel.getPrixUnitaireReel()));

			if (prixUReel != null && quantiteReel != null) {
				prixTotReel = Double.valueOf(quantiteReel * prixUReel);
			}
		}

		return prixTotReel;
	}

	/**
	 * Retoune le nombre total des materiaux
	 * 
	 * @param devis
	 * @return
	 */
	public static Integer getMateriauxTotal(Devis devis) {
		Integer nbreMat = 0;
		List<Materiel> listeMater = devis.getListMateriaux();
		if (!listeMater.isEmpty()) {
			nbreMat = listeMater.size();
		}

		return nbreMat;
	}

	/**
	 * retourne le nombre des materiaux encore à acheter
	 * 
	 * @param devis
	 * @return
	 */
	public static Integer getMateriauxAacheter(Devis devis) {
		Integer nbreMat = 0;
		List<Materiel> listeMater = devis.getListMateriaux();
		if (!listeMater.isEmpty()) {
			int i = 0;
			for (Materiel materiel : listeMater) {
				if (StatutAchatMateriel.A_ACHETER.getCode().equals(materiel.getCodeStatutAchat())) {
					i++;
					nbreMat = i;
				}
			}
		}

		return nbreMat;
	}

	/**
	 * Supprime les vide dans un String
	 * 
	 * @param text
	 * @return
	 */
	public static String deleteWhiteSpace(String text) {
		String result = "";
		String[] tab = null;
		if (text != null) {
			tab = text.split("\\u00A0");
			if (tab.length > 1) {
				for (int i = 0; i < tab.length; i++) {
					result = result.concat(tab[i]);
				}
			} else {
				result = text;
			}
		}

		return result;
	}

	/**
	 * 
	 * @param text
	 * @return
	 */

	public static Integer getPhaseNumber(String text) {
		Integer result = null;
		String[] tab = text.split("\\s+");

		if (tab.length > 1) {
			result = Integer.valueOf(tab[1]);
		}
		return result;
	}

	public static PhaseConstruction getPhasePrecedente(Chantier chantier, PhaseConstruction currentPhase) {
		List<PhaseConstruction> listPhase = chantier.getPhasesConstruction();
		int numero = currentPhase.getNumero() - 1;

		PhaseConstruction phasePrecendent = null;
		if (listPhase != null && !listPhase.isEmpty()) {

			for (PhaseConstruction phase : listPhase) {
				if (phase.getNumero() == numero) {
					phasePrecendent = phase;
				}
			}
		}
		return phasePrecendent;
	}

	public static int[] getNumerosPhase(Chantier chantier) {
		int numeros[] = new int[chantier.getPhasesConstruction().size()];

		for (int i = 0; i < chantier.getPhasesConstruction().size(); i++) {
			numeros[i] = i;
		}

		return numeros;
	}

	public static HashMap<String, Integer> getDatesPrevusPhase(Chantier chantier) {

		HashMap<String, Integer> numerosPhase = new HashMap<String, Integer>();
		List<PhaseConstruction> listePhase = chantier.getPhasesConstruction();
		// Collections.reverse(listePhase);

		for (PhaseConstruction phase : listePhase) {
			String dateDebut = phase.getDateDebutPrevu();
			String dateFin = phase.getDateFinPrevu();
			numerosPhase.put(dateDebut, phase.getNumero());
			numerosPhase.put(dateFin, phase.getNumero());
		}

		if (!numerosPhase.containsValue(null)) {
			numerosPhase = sortByValue(numerosPhase);
		}

		return numerosPhase;
	}

	/**
	 * 
	 * @param alist
	 * @return
	 */
	public static ArrayList<Integer> reverseIntegerArrayList(List<Integer> alist) {
		ArrayList<Integer> revArrayList = new ArrayList<Integer>();
		for (int i = alist.size() - 1; i >= 0; i--) {
			revArrayList.add(alist.get(i));
		}

		return revArrayList;
	}

	public static ArrayList<String> reverseStringArrayList(List<String> alist) {
		ArrayList<String> revArrayList = new ArrayList<String>();
		for (int i = alist.size() - 1; i >= 0; i--) {
			revArrayList.add(alist.get(i));
		}

		return revArrayList;
	}

	/**
	 * 
	 * @param hm
	 * @return
	 */
	public static HashMap<String, Integer> sortByKey(HashMap<String, Integer> hm) {
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(hm.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o1.getKey()).compareTo(o2.getKey());
			}
		});

		HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> aa : list) {
			temp.put(aa.getKey(), aa.getValue());
		}
		return temp;
	}

	/**
	 * 
	 * @param hm
	 * @return
	 */
	public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm) {
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(hm.entrySet());

		// Sort the list
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return ((o1.getValue()).compareTo(o2.getValue()));
			}
		});

		// put data from sorted list to hashmap
		HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> aa : list) {
			temp.put(aa.getKey(), aa.getValue());
		}
		return temp;
	}

	/**
	 * 
	 * @param chantier
	 * @return
	 */
	public static HashMap<String, Integer> getDatesReelsPhase(Chantier chantier) {

		HashMap<String, Integer> numerosPhase = new HashMap<String, Integer>();
		List<PhaseConstruction> listePhase = chantier.getPhasesConstruction();
		Collections.reverse(listePhase);
		for (PhaseConstruction phase : listePhase) {
			String dateDebut = phase.getDateDebutReel();
			String dateFin = phase.getDateFinReel();

			if (dateDebut != null) {
				numerosPhase.put(dateDebut, phase.getNumero());
			}

			if (dateFin != null) {
				numerosPhase.put(dateFin, phase.getNumero());
			}
		}
		if (!numerosPhase.containsValue(null)) {
			numerosPhase = sortByValue(numerosPhase);
		}
		// numerosPhase = sortByValue(numerosPhase);
		return numerosPhase;
	}

	/**
	 * Retourne le nmbre de matériaux achéte en partie
	 * 
	 * @param devis
	 * @return
	 */
	public static Integer getMateriauxAcheteEnPartie(Devis devis) {
		Integer nbreMat = 0;
		List<Materiel> listeMater = devis.getListMateriaux();
		if (!listeMater.isEmpty()) {
			int i = 0;
			for (Materiel materiel : listeMater) {
				if (StatutAchatMateriel.ACHETE_P.getCode().equals(materiel.getCodeStatutAchat())) {
					i++;
					nbreMat = i;
				}
			}
		}

		return nbreMat;
	}

	public static List<Materiel> getMateriauxDevisInitial(Devis devis) {
		List<Materiel> listeMateriaux = new ArrayList<Materiel>();
		List<Materiel> listeMater = devis.getListMateriaux();
		if (!listeMater.isEmpty()) {
			for (Materiel materiel : listeMater) {
				if (StatutAchatMateriel.A_ACHETER.getCode().equals(materiel.getCodeStatutAchat())
						&& StatutAchatMateriel.DEVIS_INIT.getCode().equals(materiel.getCodeDevisInitial())) {
					listeMateriaux.add(materiel);
				}
			}
		}

		return listeMateriaux;
	}

	/**
	 * Retourne la liste des materiaux achetés en totalité (statut achete_F)
	 * 
	 * @param devis
	 * @return
	 */

	public static Integer getMateriauxAcheteTotalite(Devis devis) {
		Integer nbreMat = 0;
		List<Materiel> listeMater = devis.getListMateriaux();
		if (!listeMater.isEmpty()) {
			int i = 0;
			for (Materiel materiel : listeMater) {
				if (StatutAchatMateriel.ACHETE_T.getCode().equals(materiel.getCodeStatutAchat())) {
					i++;
					nbreMat = i;
				}
			}
		}

		return nbreMat;
	}

	/**
	 * 
	 * @param table
	 * @param frais
	 * @return
	 */
	/*
	 * public static TableView populateTableSuiviAchatOnStart(TableView table, Devis
	 * devis) { //TODO SUPPRIMER LES VIDES //TODO REMOUVE ALLL ELEMENT IN THE TABLE
	 * List<Materiel> listeMater = devis.getListMateriaux();
	 * 
	 * for (Materiel materiel : listeMater) { if
	 * (_StatutAchatMateriel.ACHETE_T.getCode().equals(materiel.getCodeStatutAchat()
	 * )) { Vector<Object> data = new Vector<Object>(); String nomType =
	 * materiel.getNom(); Double _prixU_prevu = new
	 * Double(materiel.getPrixUnitairePrevu()); Double _prixU_Reel = new
	 * Double(materiel.getPrixUnitaireReel()); String _devise =
	 * materiel.getDevise(); String _qualite_prevu = materiel.getQualitePrevu();
	 * String _qualiteReel = materiel.getQualiteReel(); Integer _quantitePrevu =
	 * Integer.valueOf(materiel.getQuantitePrevu()); Integer _quantiteReel =
	 * Integer.valueOf(materiel.getQuantiteReel()); String _statut =
	 * materiel.getStatutAchat(); String _unitePrevu = materiel.getUniteDemesure();
	 * Double _prixTotReel = getPrixTotalReel(materiel); // Double _prixTotPrevu =
	 * getPrixTotalPrevu(materiel); Double prixTotPrevuReel =
	 * getPrixTotalPrevuReel(materiel); String _obeservation =
	 * materiel.getObeservation(); Double diff = 0.0;
	 * 
	 * if (prixTotPrevuReel != null && _prixTotReel != null) { diff = _prixTotReel -
	 * prixTotPrevuReel; }
	 * 
	 * String _diff = BatisUtils.getFormattedNumber(diff); String prixU_prevu =
	 * getFormattedNumber(_prixU_prevu); String prixTotPrevu =
	 * getFormattedNumber(prixTotPrevuReel); String prixTotReel =
	 * getFormattedNumber(_prixTotReel); String prixU_Reel =
	 * getFormattedNumber(_prixU_Reel); setObservation(data, _obeservation);
	 * data.add(materiel.getDateAchat()); data.add(nomType);
	 * data.add(_qualite_prevu); data.add(_qualiteReel); data.add(_quantitePrevu);
	 * data.add(_quantiteReel); data.add(_unitePrevu); data.add(prixU_prevu);
	 * data.add(prixU_Reel); data.add(_devise); data.add(prixTotPrevu);
	 * data.add(prixTotReel); data.add(_statut); data.add(_diff); //TOTO AJOUTER LES
	 * DONNES // model.addRow(data); } }
	 * 
	 * //JTableObservationRenderer render = new JTableObservationRenderer();
	 * //table.getColumnModel().getColumn(0).setCellRenderer(render);
	 * //table.updateUI(); return table; }
	 */

	/**
	 * 
	 * @param data
	 * @param obeservation
	 */
	public static void setObservation(Vector<Object> data, String obeservation) {
		Image eyesImg = new Image("/images/yeux.png");
		if (obeservation != null && !obeservation.isEmpty() && obeservation != null) {
			Label observLabel = new Label();
			observLabel.setGraphic(new ImageView(eyesImg));
			data.add(observLabel);

		} else {
			data.add("");
		}
	}

	/**
	 * 
	 * @param _prixTotPrevu
	 * @return
	 */
	/*
	 * public static String getFormattedNumber(Double _prixTotPrevu) { Format
	 * general = NumberFormat.getInstance(); TextField input = new
	 * TextField(general); input.setValue(_prixTotPrevu); new
	 * JTexfieldNumberRenderer(input).setBatisFielFormat(_prixTotPrevu); return
	 * input.getText(); }
	 */

	/**
	 * 
	 * @param excedManquant
	 * @return
	 */
	/*
	 * public static BaseJFormattedTextField setFormattedBatisField(Double
	 * excedManquant) { Format general = NumberFormat.getInstance();
	 * BaseJFormattedTextField input = new BaseJFormattedTextField(general);
	 * input.setValue(excedManquant); new
	 * JTexfieldNumberRenderer(input).setBatisFielFormat(excedManquant); return
	 * input; }
	 */
	public static Date formatBatisDate(String date) {
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		Date dateOp = null;
		try {
			dateOp = formater.parse(date);
		} catch (ParseException e) {
			// TODO
			e.printStackTrace();
		}
		return dateOp;
	}

	/*
	 * public static JFormattedTextField updateDateField(JDatePickerImpl datePicker)
	 * { Component[] comp = datePicker.getComponents(); JFormattedTextField
	 * champDate = (JFormattedTextField) comp[0];
	 * champDate.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
	 * champDate.setPreferredSize(new Dimension(100, 25)); JButton fbutton =
	 * (JButton) comp[1]; fbutton.setText(""); fbutton.setBackground(new
	 * BaseJButton().getColor()); fbutton.setIcon(new
	 * ImageIcon("images/calendrier.png")); fbutton.updateUI(); return champDate; }
	 */

	/*
	 * public static JTable populateTableRetraitDepotOnStart(JTable tableauRD,
	 * List<RetraitDepot> listOperations) {
	 * 
	 * DefaultTableModel model = (DefaultTableModel) tableauRD.getModel();
	 * model.getDataVector().removeAllElements();
	 * 
	 * for (RetraitDepot op : listOperations) {
	 * 
	 * Vector<Object> data = new Vector<Object>(); String dateOp =
	 * op.getDateOperation(); String _typeOp = op.getType(); Double _montant =
	 * op.getMontant(); String _devise = op.getDevise(); String _motif =
	 * op.getMotif();
	 * 
	 * Beneficiaire beneficiaire = op.getBeneficiaire(); String _beneficiaire = "";
	 * String _tyBeneficiaire = ""; Employe gerant = op.getComptable();
	 * data.add(dateOp); data.add(_typeOp); data.add(_montant); data.add(_devise);
	 * data.add(_motif); // Hibernate.initialize(op.getBeneficiaire()); //
	 * Hibernate.initialize(op.getDeposant()); if (beneficiaire != null) {
	 * 
	 * _beneficiaire = beneficiaire.getNom() + " " + beneficiaire.getPrenom();
	 * _tyBeneficiaire = beneficiaire.getType(); data.add(_beneficiaire);
	 * data.add(_tyBeneficiaire); } Deposant deposant = op.getDeposant(); String
	 * _deposant = ""; if (deposant != null) { _deposant = deposant.getNom() + " " +
	 * deposant.getPrenom(); data.add(_deposant); data.add(" - "); }
	 * 
	 * addGerant(data, gerant); addEditingLabels(data); model.addRow(data); }
	 */

	/*
	 * JTableButtonRenderer render = new JTableButtonRenderer();
	 * tableauRD.getColumnModel().getColumn(8).setCellRenderer(render);
	 * tableauRD.getColumnModel().getColumn(9).setCellRenderer(render);
	 * tableauRD.updateUI();
	 */
	// return tableauRD;
	// }

	/**
	 * 
	 * @param chantier
	 * @return
	 */
	public static Double getFraisApayer(Chantier chantier) {
		Double montantTot = 0.0;
		VoletAdmnistratif voletAdmin = chantier.getVoletAdministratif();
		for (Frais frais : voletAdmin.getListeFrais()) {
			if (StatutFrais.APAYER.getCode().equals(frais.getCodeStatut())) {
				montantTot = montantTot + frais.getMontant();
			}
		}
		return montantTot;
	}

	/**
	 * 
	 * @param chantier
	 * @return
	 */
	public static Double getFraisPayes(Chantier chantier) {
		Double montantTot = 0.0;
		VoletAdmnistratif voletAdmin = chantier.getVoletAdministratif();
		for (Frais frais : voletAdmin.getListeFrais()) {

			if (StatutFrais.PAYE.getCode().equals(frais.getCodeStatut())) {
				montantTot = montantTot + frais.getMontant();
			}
		}
		return montantTot;
	}

	/**
	 * 
	 * @param prixTot
	 * @return
	 */
	public static String getNumberFormatter(Double prixTot) {

		NumberFormat doubleFormat = NumberFormat.getNumberInstance();
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.FRANCE);
		currencyFormat.setGroupingUsed(true);

		doubleFormat.setMaximumFractionDigits(2);
		String prixTotStringValue = doubleFormat.format(prixTot);
		return prixTotStringValue;
	}

	/**
	 * 
	 * @return
	 */
	/*
	 * public static NumberFormatter getNumberFormatter() { NumberFormat
	 * doubleFormat = NumberFormat.getNumberInstance(Locale.FRANCE);
	 * doubleFormat.setMaximumFractionDigits(2); doubleFormat.setGroupingUsed(true);
	 * NumberFormatter numberFormatter = new NumberFormatter(doubleFormat);
	 * numberFormatter.setFormat(doubleFormat); return numberFormatter; }
	 */

	/**
	 * Ajoute le panel date de naissance
	 * 
	 * @param panAge
	 * @param dateNaissance
	 */
	/*
	 * public static JFormattedTextField addDatePanel(JPanel panAge) {
	 * JFormattedTextField dateNaissance; JLabel dateNaissanceLabel = new
	 * JLabel("Date de Naissance :", JLabel.LEFT);
	 * dateNaissanceLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
	 * JDatePickerImpl datePicker = GetBatisDateFormat(); dateNaissance =
	 * updateDateField(datePicker); panAge.add(dateNaissanceLabel);
	 * panAge.add(datePicker); return dateNaissance; }
	 */

	/**
	 * 
	 * @param data
	 * @param gerant
	 */
	public static void addGerant(Vector<Object> data, Employe gerant) {
		if (gerant != null) {
			data.add(gerant.getNom() + " " + gerant.getPrenom());
		} else {
			data.add("");
		}
	}

	/**
	 * 
	 * @param stockDestTableau
	 * @param listStock
	 * @return
	 */
	/*
	 * public static JTable populateTableOperationOnStart(JTable stockDestTableau,
	 * List<StockDestockage> listStock) { DefaultTableModel model =
	 * (DefaultTableModel) stockDestTableau.getModel();
	 * model.getDataVector().removeAllElements(); SimpleDateFormat formater = new
	 * SimpleDateFormat("dd/MM/yyyy"); for (StockDestockage sock : listStock) {
	 * 
	 * Vector<Object> data = new Vector<Object>(); String _dateOp = null; if
	 * (sock.getDate() != null) { _dateOp = formater.format(sock.getDate()); }
	 * 
	 * String _typeOp = sock.getTypeOperation(); String _nomMat =
	 * sock.getNomMateriel(); String _qualite = sock.getQualite(); Double _quantite
	 * = sock.getQuantite(); String _uniteMesure = sock.getUniteMesure(); String
	 * _motifDest = sock.getMotifDestockage(); String statut = sock.getStatut();
	 * 
	 * // String _beneficiaire =""; // String _tyBeneficiaire ="";
	 * 
	 * data.add(_dateOp); data.add(_nomMat); data.add(_qualite);
	 * data.add(_quantite); data.add(_uniteMesure); data.add(_typeOp);
	 * data.add(_motifDest); data.add(statut); // BatisUtils.addEditingLabels(data);
	 * model.addRow(data); }
	 * 
	 * // JTableButtonRenderer render = new JTableButtonRenderer(); //
	 * tableauRD.getColumnModel().getColumn(8).setCellRenderer(render); //
	 * tableauRD.getColumnModel().getColumn(9).setCellRenderer(render);
	 * stockDestTableau.updateUI(); return stockDestTableau; }
	 */

	/**
	 * Ajoute les bouton pour editer le tableau
	 * 
	 * @param data
	 */
	public static void addEditingLabels(Vector<Object> data) {
		// Image editImg = new Image("/images/_edit.png");
		// Image trashImg = new Image("/images/trash.png");
		Label editLabel = new Label();
		Label deleteLabel = new Label();
		data.add(editLabel);
		data.add(deleteLabel);
	}

	/**
	 * 
	 * @param nom
	 * @return
	 */
	public static Long getCodeMaterielByname(String nom) {
		NomsMateriaux listMat = nomsMaterieuxService.getNomByName(nom);
		return listMat != null ? listMat.getCode() : null;
	}

	/**
	 * @return the chantierService
	 */
	public static ChantierService getChantierService() {
		return chantierService;
	}

	/**
	 * @param chantierService the chantierService to set
	 */
	public static void setChantierService(ChantierService chantierService) {
		BatisUtils.chantierService = chantierService;
	}

	public static PhaseConstruction getPhaseByNameAndChantier(String phaseName, Chantier chantier) {
		PhaseConstruction phase = null;
		phase = phaseService.getPhaseByNameAndChantier(phaseName, chantier);
		return phase;
	}

	/**
	 * 
	 * @return
	 */
	public static ComboBox<String> getListMateriaux() {
		ComboBox<String> typeMateriel = new ComboBox<String>();
		List<NomsMateriaux> listMat = nomsMaterieuxService.findAll();
		Collections.sort(listMat, new ComparatorFactory());
		typeMateriel.getItems().add("");

		for (NomsMateriaux mat : listMat) {
			typeMateriel.getItems().add(mat.getNom());
		}
		return typeMateriel;
	}

}

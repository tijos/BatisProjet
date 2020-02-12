package main.java.fr.batis.service;

import java.util.List;

import main.java.fr.batis.entites.Inventaire;
import main.java.fr.batis.model.dao.InventaireDAO;

public class InventaireService {

	private InventaireDAO inventaireDao;

	public InventaireService() {
		inventaireDao = new InventaireDAO();
	}

	/**
	 * 
	 * @param entity
	 */
	public void saveOrUpdate(Inventaire entity) {
		inventaireDao.openCurrentSessionwithTransaction();
		inventaireDao.saveOrUpdate(entity);
		inventaireDao.closeCurrentSessionwithTransaction();
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Inventaire findById(Long id) {
		inventaireDao.openCurrentSession();
		Inventaire stockDestock = inventaireDao.findById(id);
		inventaireDao.closeCurrentSession();
		return stockDestock;
	}

	/**
	 * 
	 * @param id
	 */
	public void delete(Long id) {
		inventaireDao.openCurrentSessionwithTransaction();
		Inventaire inventaire = inventaireDao.findById(id);
		inventaireDao.delete(inventaire);
		inventaireDao.closeCurrentSessionwithTransaction();
	}

	/**
	 * 
	 * @return
	 */
	public List<Inventaire> findAll() {
		inventaireDao.openCurrentSession();
		List<Inventaire> inventaires = inventaireDao.findAll();
		inventaireDao.closeCurrentSession();
		return inventaires;
	}

	public List<Inventaire> findTenFirst() {
		inventaireDao.openCurrentSession();
		List<Inventaire> inventaires = inventaireDao.findFirstTen();
		inventaireDao.closeCurrentSession();
		return inventaires;
	}

	/**
	 * 
	 */
	public void deleteAll() {
		inventaireDao.openCurrentSessionwithTransaction();
		inventaireDao.deleteAll();
		inventaireDao.closeCurrentSessionwithTransaction();
	}

	/**
	 * @return the inventaire
	 */
	public InventaireDAO getInventaireDAO() {
		return inventaireDao;
	}

	/**
	 * @param inventaire the inventaire to set
	 */
	public void setInventaireDAO(InventaireDAO inventaireDao) {
		this.inventaireDao = inventaireDao;
	}

	/**
	 * 
	 * @param nom
	 * @return
	 */
	public Inventaire getinventaireByName(Long idChantier) {
		inventaireDao.openCurrentSession();
		Inventaire inventaire = inventaireDao.findByName(idChantier);
		inventaireDao.closeCurrentSession();
		return inventaire;

	}

	/*
	 * public List<Inventaire> getListRD(String texte, boolean retrait, boolean
	 * depot){ inventaireDao.openCurrentSession(); List<Inventaire> inventaires =
	 * inventaireDao.getListRD(texte,retrait,depot);
	 * inventaireDao.closeCurrentSession(); return inventaires; }
	 */

}

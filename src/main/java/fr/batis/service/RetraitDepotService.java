package main.java.fr.batis.service;

import java.util.List;

import main.java.fr.batis.entites.Chantier;
import main.java.fr.batis.entites.RetraitDepotFond;
import main.java.fr.batis.model.dao.RetraitDepotFondDAO;

public class RetraitDepotService {

	private RetraitDepotFondDAO retraitDepotDao;

	public RetraitDepotService() {
		retraitDepotDao = new RetraitDepotFondDAO();
	}

	/**
	 * 
	 * @param entity
	 */
	public void saveOrUpdate(RetraitDepotFond entity) {
		retraitDepotDao.openCurrentSessionwithTransaction();
		retraitDepotDao.saveOrUpdate(entity);
		retraitDepotDao.closeCurrentSessionwithTransaction();
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public RetraitDepotFond findById(Long id) {
		retraitDepotDao.openCurrentSession();
		RetraitDepotFond retraitDepot = retraitDepotDao.findById(id);
		retraitDepotDao.closeCurrentSession();
		return retraitDepot;
	}

	public List<RetraitDepotFond> findByIdChantier(Chantier chantier) {
		retraitDepotDao.openCurrentSession();
		List<RetraitDepotFond> retraitDepot = retraitDepotDao.findByChantier(chantier);
		retraitDepotDao.closeCurrentSession();
		return retraitDepot;
	}

	/**
	 * 
	 * @param id
	 */
	public void delete(Long id) {
		retraitDepotDao.openCurrentSessionwithTransaction();
		RetraitDepotFond retraitDepot = retraitDepotDao.findById(id);
		retraitDepotDao.delete(retraitDepot);
		retraitDepotDao.closeCurrentSessionwithTransaction();
	}

	/**
	 * 
	 * @return
	 */
	public List<RetraitDepotFond> findAll() {
		retraitDepotDao.openCurrentSession();
		List<RetraitDepotFond> retraitDepots = retraitDepotDao.findAll();
		retraitDepotDao.closeCurrentSession();
		return retraitDepots;
	}

	public List<RetraitDepotFond> findTenFirst() {
		retraitDepotDao.openCurrentSession();
		List<RetraitDepotFond> retraitDepots = retraitDepotDao.findFirstTen();
		retraitDepotDao.closeCurrentSession();
		return retraitDepots;
	}

	/**
	 * 
	 */
	public void deleteAll() {
		retraitDepotDao.openCurrentSessionwithTransaction();
		retraitDepotDao.deleteAll();
		retraitDepotDao.closeCurrentSessionwithTransaction();
	}

	/**
	 * @return the retraitDepotDao
	 */
	public RetraitDepotFondDAO getRetraitDepotDao() {
		return retraitDepotDao;
	}

	/**
	 * @param retraitDepotDao the retraitDepotDao to set
	 */
	public void setRetraitDepotDao(RetraitDepotFondDAO retraitDepotDao) {
		this.retraitDepotDao = retraitDepotDao;
	}

	/**
	 * 
	 * @param nom
	 * @return
	 */
	public RetraitDepotFond getRetraitDepotByName(String nom) {
		retraitDepotDao.openCurrentSession();
		RetraitDepotFond retraitDepot = retraitDepotDao.findByName(nom);
		retraitDepotDao.closeCurrentSession();
		return retraitDepot;

	}

	/*
	 * public List<RetraitDepotFond> getListRD(String texte, boolean retrait,
	 * boolean depot){ retraitDepotDao.openCurrentSession(); List<RetraitDepotFond>
	 * retraitDepots = retraitDepotDao.getListRD(texte,retrait,depot);
	 * retraitDepotDao.closeCurrentSession(); return retraitDepots; }
	 */

}

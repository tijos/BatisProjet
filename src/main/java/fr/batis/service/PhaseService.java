package main.java.fr.batis.service;

import java.util.List;

import main.java.fr.batis.entites.Chantier;
import main.java.fr.batis.entites.PhaseConstruction;
import main.java.fr.batis.model.dao.PhaseDAO;

public class PhaseService {

	private PhaseDAO phaseDao;

	public PhaseService() {
		phaseDao = new PhaseDAO();
	}

	/**
	 * 
	 * @param entity
	 */
	public void saveOrUpdate(PhaseConstruction entity) {
		phaseDao.openCurrentSessionwithTransaction();
		phaseDao.saveOrUpdate(entity);
		phaseDao.closeCurrentSessionwithTransaction();
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public PhaseConstruction findById(Long id) {
		phaseDao.openCurrentSession();
		PhaseConstruction phaseConstruction = phaseDao.findById(id);
		phaseDao.closeCurrentSession();
		return phaseConstruction;
	}

	/**
	 * 
	 * @param id
	 */
	public void delete(Long id) {
		phaseDao.openCurrentSessionwithTransaction();
		PhaseConstruction phaseConstruction = phaseDao.findById(id);
		phaseDao.delete(phaseConstruction);
		phaseDao.closeCurrentSessionwithTransaction();
	}

	/**
	 * 
	 * @return
	 */
	public List<PhaseConstruction> findAll() {
		phaseDao.openCurrentSession();
		List<PhaseConstruction> phaseConstructions = phaseDao.findAll();
		phaseDao.closeCurrentSession();
		return phaseConstructions;
	}

	public List<PhaseConstruction> findTenFirst() {
		phaseDao.openCurrentSession();
		List<PhaseConstruction> phaseConstructions = phaseDao.findFirstTen();
		phaseDao.closeCurrentSession();
		return phaseConstructions;
	}

	/**
	 * 
	 */
	public void deleteAll() {
		phaseDao.openCurrentSessionwithTransaction();
		phaseDao.deleteAll();
		phaseDao.closeCurrentSessionwithTransaction();
	}

	/**
	 * @return the stockDestockage
	 */
	public PhaseDAO getPhaseDao() {
		return phaseDao;
	}

	/**
	 * @param stockDestockage the stockDestockage to set
	 */
	public void setPhaseDao(PhaseDAO phaseDao) {
		this.phaseDao = phaseDao;
	}

	/**
	 * 
	 * @param nom
	 * @return
	 */
	public PhaseConstruction getPhaseByName(String nom) {
		phaseDao.openCurrentSession();
		PhaseConstruction phaseConstruction = phaseDao.findByName(nom);
		phaseDao.closeCurrentSession();
		return phaseConstruction;

	}

	// TODO
	public List<PhaseConstruction> getEtatStock() {
		phaseDao.openCurrentSession();
		List<PhaseConstruction> phaseConstructions = phaseDao.getEtatStock();
		phaseDao.closeCurrentSession();
		return phaseConstructions;
	}

	public PhaseConstruction getPhaseByNameAndChantier(String phaseName, Chantier chantier) {
		phaseDao.openCurrentSession();
		PhaseConstruction phaseConstruction = phaseDao.findByNameAndChantier(phaseName, chantier);
		phaseDao.closeCurrentSession();
		return phaseConstruction;
	}

}

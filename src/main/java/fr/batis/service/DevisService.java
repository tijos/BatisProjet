package main.java.fr.batis.service;

import java.util.List;

import main.java.fr.batis.entites.Devis;
import main.java.fr.batis.entites.PhaseConstruction;
import main.java.fr.batis.model.dao.DevisDAO;

public class DevisService {

	private DevisDAO devisDao;

	public DevisService() {
		devisDao = new DevisDAO();
	}

	/**
	 * 
	 * @param entity
	 */
	public void saveOrUpdate(Devis entity) {
		devisDao.openCurrentSessionwithTransaction();
		devisDao.saveOrUpdate(entity);
		devisDao.closeCurrentSessionwithTransaction();
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Devis findById(Long id) {
		devisDao.openCurrentSession();
		Devis devis = devisDao.findById(id);
		devisDao.closeCurrentSession();
		return devis;
	}

	/**
	 * 
	 * @param id
	 */
	public void delete(Long id) {
		devisDao.openCurrentSessionwithTransaction();
		Devis devis = devisDao.findById(id);
		devisDao.delete(devis);
		devisDao.closeCurrentSessionwithTransaction();
	}

	public void evict(Devis devis) {
		devisDao.openCurrentSessionwithTransaction();
		devisDao.evict(devis);
		devisDao.closeCurrentSessionwithTransaction();
	}

	/**
	 * 
	 * @return
	 */
	public List<Devis> findAll() {
		devisDao.openCurrentSession();
		List<Devis> devisList = devisDao.findAll();
		devisDao.closeCurrentSession();
		return devisList;
	}

	public List<Devis> findTenFirst() {
		devisDao.openCurrentSession();
		List<Devis> devisList = devisDao.findFirstTen();
		devisDao.closeCurrentSession();
		return devisList;
	}

	/**
	 * 
	 */
	public void deleteAll() {
		devisDao.openCurrentSessionwithTransaction();
		devisDao.deleteAll();
		devisDao.closeCurrentSessionwithTransaction();
	}

	/**
	 * @return the devisDao
	 */
	public DevisDAO getDevisDao() {
		return devisDao;
	}

	/**
	 * @param devisDao the devisDao to set
	 */
	public void setDevisDao(DevisDAO devisDao) {
		this.devisDao = devisDao;
	}

	/**
	 * 
	 * @param nom
	 * @return
	 */
	public Devis getDevisByName(String nom) {
		devisDao.openCurrentSession();
		Devis devis = devisDao.findByName(nom);
		devisDao.closeCurrentSession();
		return devis;

	}

	public Devis getDevisByNameAndPhase(String nom, PhaseConstruction phase) {
		devisDao.openCurrentSession();
		Devis devis = devisDao.getDevisByNameAndPhase(nom, phase);
		devisDao.closeCurrentSession();
		return devis;

	}
}

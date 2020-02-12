package main.java.fr.batis.service;

import java.util.List;

import main.java.fr.batis.entites.Chantier;
import main.java.fr.batis.model.dao.ChantierDAO;


public class ChantierService {

	private ChantierDAO chantierDao;

	public ChantierService() {
		this.chantierDao = new ChantierDAO();
	}

	/**
	 * 
	 * @param entity
	 */
	public void saveOrUpdate(final Chantier entity) {
		chantierDao.openCurrentSessionwithTransaction();
		chantierDao.saveOrUpdate(entity);
		chantierDao.closeCurrentSessionwithTransaction();
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Chantier findById(Long id) {
		chantierDao.openCurrentSession();
		Chantier chantier = chantierDao.findById(id);
		chantierDao.closeCurrentSession();
		return chantier;
	}

	/**
	 * 
	 * @param id
	 */
	public void delete(Long id) {
		chantierDao.openCurrentSessionwithTransaction();
		Chantier chantier = chantierDao.findById(id);
		chantierDao.delete(chantier);
		chantierDao.closeCurrentSessionwithTransaction();
	}

	/**
	 * 
	 * @param d 
	 * @param c 
	 * @param b 
	 * @param e 
	 * @return
	 */
	public List<Chantier> findAll(boolean actif, boolean closed, boolean desactive, boolean all) {
		chantierDao.openCurrentSession();
		List<Chantier> chantiers = chantierDao.findAll(actif,closed,desactive,all);
		chantierDao.closeCurrentSession();
		return chantiers;
	}

	/**
	 * 
	 */
	public void deleteAll() {
		chantierDao.openCurrentSessionwithTransaction();
		chantierDao.deleteAll();
		chantierDao.closeCurrentSessionwithTransaction();
	}

	/**
	 * @return the chantierDao
	 */
	public ChantierDAO getChantierDao() {
		return chantierDao;
	}

	/**
	 * @param chantierDao
	 *            the chantierDao to set
	 */
	public void setChantierDao(ChantierDAO chantierDao) {
		this.chantierDao = chantierDao;
	}
	
	/**
	 * 
	 * @param nom
	 * @return
	 */
	public Chantier getChantierByName(String nom) {
		chantierDao.openCurrentSession();
		Chantier chantier = chantierDao.findByName(nom);
		chantierDao.closeCurrentSession();
		return chantier;
		
	}
}

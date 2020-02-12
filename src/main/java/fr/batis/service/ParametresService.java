package main.java.fr.batis.service;

import java.util.List;

import main.java.fr.batis.entites.Parametres;
import main.java.fr.batis.model.dao.ParametresDAO;

public class ParametresService {

	private ParametresDAO parametresDAO;

	public ParametresService() {
		parametresDAO = new ParametresDAO();
	}

	/**
	 * 
	 * @param entity
	 */
	public void saveOrUpdate(Parametres entity) {
		parametresDAO.openCurrentSessionwithTransaction();
		parametresDAO.saveOrUpdate(entity);
		parametresDAO.closeCurrentSessionwithTransaction();
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Parametres findById(Long id) {
		parametresDAO.openCurrentSession();
		Parametres parametres = parametresDAO.findById(id);
		parametresDAO.closeCurrentSession();
		return parametres;
	}

	/**
	 * 
	 * @param id
	 */
	public void delete(Long id) {
		parametresDAO.openCurrentSessionwithTransaction();
		Parametres parametres = parametresDAO.findById(id);
		parametresDAO.delete(parametres);
		parametresDAO.closeCurrentSessionwithTransaction();
	}

	/**
	 * 
	 * @return
	 */
	public List<Parametres> findAll() {
		parametresDAO.openCurrentSession();
		List<Parametres> parametress = parametresDAO.findAll();
		parametresDAO.closeCurrentSession();
		return parametress;
	}

	public List<Parametres> findTenFirst() {
		parametresDAO.openCurrentSession();
		List<Parametres> parametress = parametresDAO.findFirstTen();
		parametresDAO.closeCurrentSession();
		return parametress;
	}

	/**
	 * 
	 */
	public void deleteAll() {
		parametresDAO.openCurrentSessionwithTransaction();
		parametresDAO.deleteAll();
		parametresDAO.closeCurrentSessionwithTransaction();
	}

	/**
	 * @return the stockDestockage
	 */
	public ParametresDAO getParametresDAO() {
		return parametresDAO;
	}

	/**
	 * @param stockDestockage the stockDestockage to set
	 */
	public void setParametresDAO(ParametresDAO parametresDAO) {
		this.parametresDAO = parametresDAO;
	}

}

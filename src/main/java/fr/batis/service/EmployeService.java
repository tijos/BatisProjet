package main.java.fr.batis.service;

import java.util.List;

import main.java.fr.batis.entites.Employe;
import main.java.fr.batis.model.dao.EmployeDAO;

public class EmployeService {

	private EmployeDAO employeDao;

	public EmployeService() {
		employeDao = new EmployeDAO();
	}

	/**
	 * 
	 * @param entity
	 */
	public void saveOrUpdate(Employe entity) {
		employeDao.openCurrentSessionwithTransaction();
		employeDao.saveOrUpdate(entity);
		employeDao.closeCurrentSessionwithTransaction();
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Employe findById(Long id) {
		employeDao.openCurrentSession();
		Employe employe = employeDao.findById(id);
		employeDao.closeCurrentSession();
		return employe;
	}

	/**
	 * 
	 * @param id
	 */
	public void delete(Long id) {
		employeDao.openCurrentSessionwithTransaction();
		Employe employe = employeDao.findById(id);
		employeDao.delete(employe);
		employeDao.closeCurrentSessionwithTransaction();
	}

	/**
	 * 
	 * @return
	 */
	public List<Employe> findAll() {
		employeDao.openCurrentSession();
		List<Employe> employes = employeDao.findAll();
		employeDao.closeCurrentSession();
		return employes;
	}

	public List<Employe> findTenFirst() {
		employeDao.openCurrentSession();
		List<Employe> employes = employeDao.findFirstTen();
		employeDao.closeCurrentSession();
		return employes;
	}

	/**
	 * 
	 */
	public void deleteAll() {
		employeDao.openCurrentSessionwithTransaction();
		employeDao.deleteAll();
		employeDao.closeCurrentSessionwithTransaction();
	}

	/**
	 * @return the stockDestockage
	 */
	public EmployeDAO getEmployeDao() {
		return employeDao;
	}

	/**
	 * @param stockDestockage the stockDestockage to set
	 */
	public void setEmployeDao(EmployeDAO employeDao) {
		this.employeDao = employeDao;
	}

	/**
	 * 
	 * @param nom
	 * @return
	 */
	public Employe getEmployeByName(String nom) {
		employeDao.openCurrentSession();
		Employe employe = employeDao.findByName(nom);
		employeDao.closeCurrentSession();
		return employe;

	}

}

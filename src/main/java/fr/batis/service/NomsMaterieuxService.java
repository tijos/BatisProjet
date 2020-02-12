package main.java.fr.batis.service;

import java.util.List;

import main.java.fr.batis.entites.Devis;
import main.java.fr.batis.entites.NomsMateriaux;
import main.java.fr.batis.model.dao.NomsMateriauxDAO;

/**
 * 
 * @author tijos
 *
 */
public class NomsMaterieuxService {

	private NomsMateriauxDAO nomsMateriauxDao;

	public NomsMaterieuxService() {
		nomsMateriauxDao = new NomsMateriauxDAO();
	}

	/**
	 * 
	 * @param entity
	 */
	public void saveOrUpdate(NomsMateriaux entity) {
		nomsMateriauxDao.openCurrentSessionwithTransaction();
		nomsMateriauxDao.saveOrUpdate(entity);
		nomsMateriauxDao.closeCurrentSessionwithTransaction();
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public NomsMateriaux findById(Long id) {
		nomsMateriauxDao.openCurrentSession();
		NomsMateriaux nomMat = nomsMateriauxDao.findById(id);
		nomsMateriauxDao.closeCurrentSession();
		return nomMat;
	}

	/**
	 * 
	 * @param id
	 */
	public void delete(Long id) {
		nomsMateriauxDao.openCurrentSessionwithTransaction();
		NomsMateriaux nomsMateriaux = nomsMateriauxDao.findById(id);
		nomsMateriauxDao.delete(nomsMateriaux);
		nomsMateriauxDao.closeCurrentSessionwithTransaction();
	}

	public void evict(Devis devis) {
		nomsMateriauxDao.openCurrentSessionwithTransaction();
		nomsMateriauxDao.evict(devis);
		nomsMateriauxDao.closeCurrentSessionwithTransaction();
	}

	/**
	 * 
	 * @return
	 */
	public List<NomsMateriaux> findAll() {
		nomsMateriauxDao.openCurrentSession();
		List<NomsMateriaux> nameList = nomsMateriauxDao.findAll();
		nomsMateriauxDao.closeCurrentSession();
		return nameList;
	}

	/**
	 * 
	 * @return
	 */
	public NomsMateriaux findLast() {
		nomsMateriauxDao.openCurrentSession();
		NomsMateriaux name = nomsMateriauxDao.getLast();
		nomsMateriauxDao.closeCurrentSession();
		return name;
	}

	/**
	 * 
	 */
	public void deleteAll() {
		nomsMateriauxDao.openCurrentSessionwithTransaction();
		nomsMateriauxDao.deleteAll();
		nomsMateriauxDao.closeCurrentSessionwithTransaction();
	}

	/**
	 * @return the nomsMateriauxDao
	 */
	public NomsMateriauxDAO getNomsMateriauxDao() {
		return nomsMateriauxDao;
	}

	/**
	 * @param nomsMateriauxDao the nomsMateriauxDao to set
	 */
	public void setNomsMateriauxDao(NomsMateriauxDAO nomsMateriauxDao) {
		this.nomsMateriauxDao = nomsMateriauxDao;
	}

	/**
	 * 
	 * @param nom
	 * @return
	 */
	public NomsMateriaux getNomByName(String nom) {
		nomsMateriauxDao.openCurrentSession();
		NomsMateriaux name = nomsMateriauxDao.findByName(nom);
		nomsMateriauxDao.closeCurrentSession();
		return name;

	}

	public NomsMateriaux getLast() {
		nomsMateriauxDao.openCurrentSession();
		NomsMateriaux name = nomsMateriauxDao.getLast();
		nomsMateriauxDao.closeCurrentSession();
		return name;

	}

	public NomsMateriaux getNomByCode(Long code) {
		nomsMateriauxDao.openCurrentSession();
		NomsMateriaux name = nomsMateriauxDao.getNomByCode(code);
		nomsMateriauxDao.closeCurrentSession();
		return name;

	}
}

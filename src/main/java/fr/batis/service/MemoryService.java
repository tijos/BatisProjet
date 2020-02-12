package main.java.fr.batis.service;

import main.java.fr.batis.entites.LastOpened;
import main.java.fr.batis.model.dao.MemoryDAO;

public class MemoryService {

	private MemoryDAO memoryDao;

	public MemoryService() {
		memoryDao = new MemoryDAO();
	}

	/**
	 * 
	 * @param entity
	 */
	public void saveOrUpdate(LastOpened entity) {
		memoryDao.openCurrentSessionwithTransaction();
		memoryDao.saveOrUpdate(entity);
		memoryDao.closeCurrentSessionwithTransaction();
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public LastOpened findById(Long id) {
		memoryDao.openCurrentSession();
		LastOpened stockDestock = memoryDao.findById(id);
		memoryDao.closeCurrentSession();
		return stockDestock;
	}

	/**
	 * 
	 * @param id
	 */
	public void delete(Long id) {
		memoryDao.openCurrentSessionwithTransaction();
		LastOpened lastOpened = memoryDao.findById(id);
		memoryDao.delete(lastOpened);
		memoryDao.closeCurrentSessionwithTransaction();
	}

	/**
	 * 
	 */
	public void deleteAll() {
		memoryDao.openCurrentSessionwithTransaction();
		memoryDao.deleteAll();
		memoryDao.closeCurrentSessionwithTransaction();
	}

	public MemoryDAO getMemoryDao() {
		return memoryDao;
	}

	public void setMemoryDao(MemoryDAO memoryDao) {
		this.memoryDao = memoryDao;
	}

	public LastOpened getFirst() {
		memoryDao.openCurrentSession();
		LastOpened lastChantier = memoryDao.getFirst();
		memoryDao.closeCurrentSession();
		return lastChantier;
	}
}

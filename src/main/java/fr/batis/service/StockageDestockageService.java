package main.java.fr.batis.service;

import java.util.List;

import main.java.fr.batis.entites.StockageDestockage;
import main.java.fr.batis.model.dao.StockageDestockageDAO;

public class StockageDestockageService {

	private StockageDestockageDAO stockDestockDao;

	public StockageDestockageService() {
		stockDestockDao = new StockageDestockageDAO();
	}

	/**
	 * 
	 * @param entity
	 */
	public void saveOrUpdate(StockageDestockage entity) {
		stockDestockDao.openCurrentSessionwithTransaction();
		stockDestockDao.saveOrUpdate(entity);
		stockDestockDao.closeCurrentSessionwithTransaction();
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public StockageDestockage findById(Long id) {
		stockDestockDao.openCurrentSession();
		StockageDestockage stockDestock = stockDestockDao.findById(id);
		stockDestockDao.closeCurrentSession();
		return stockDestock;
	}

	/**
	 * 
	 * @param id
	 */
	public void delete(Long id) {
		stockDestockDao.openCurrentSessionwithTransaction();
		StockageDestockage stockDestockage = stockDestockDao.findById(id);
		stockDestockDao.delete(stockDestockage);
		stockDestockDao.closeCurrentSessionwithTransaction();
	}

	/**
	 * 
	 * @return
	 */
	public List<StockageDestockage> findAll() {
		stockDestockDao.openCurrentSession();
		List<StockageDestockage> stockDestockages = stockDestockDao.findAll();
		stockDestockDao.closeCurrentSession();
		return stockDestockages;
	}

	public List<StockageDestockage> findTenFirst() {
		stockDestockDao.openCurrentSession();
		List<StockageDestockage> stockDestockages = stockDestockDao.findFirstTen();
		stockDestockDao.closeCurrentSession();
		return stockDestockages;
	}

	/**
	 * 
	 */
	public void deleteAll() {
		stockDestockDao.openCurrentSessionwithTransaction();
		stockDestockDao.deleteAll();
		stockDestockDao.closeCurrentSessionwithTransaction();
	}

	/**
	 * @return the stockDestockage
	 */
	public StockageDestockageDAO getStockDao() {
		return stockDestockDao;
	}

	/**
	 * @param stockDestockage the stockDestockage to set
	 */
	public void setStockDao(StockageDestockageDAO stockDestockageDao) {
		this.stockDestockDao = stockDestockageDao;
	}

	/**
	 * 
	 * @param nom
	 * @return
	 */
	public StockageDestockage getStockDestockageByName(String nom) {
		stockDestockDao.openCurrentSession();
		StockageDestockage stockDestockage = stockDestockDao.findByName(nom);
		stockDestockDao.closeCurrentSession();
		return stockDestockage;

	}

	public List<StockageDestockage> getEtatStock(Long idChantier) {
		stockDestockDao.openCurrentSession();
		List<StockageDestockage> stockDestockages = stockDestockDao.getEtatStock(idChantier);
		stockDestockDao.closeCurrentSession();
		return stockDestockages;
	}

	public Double getQunatiteDispoByCode(long code, String qualite) {
		stockDestockDao.openCurrentSession();
		Double stockDestockages = stockDestockDao.getQunatiteDispoByCode(code, qualite);
		stockDestockDao.closeCurrentSession();
		return stockDestockages;
	}

	/*
	 * public List<StockageDestockage> getMaterielBySearch(String texte) {
	 * stockDestockDao.openCurrentSession(); List<StockageDestockage>
	 * stockDestockage = stockDestockDao.getMaterielBySearch(texte);
	 * stockDestockDao.closeCurrentSession(); return stockDestockage;
	 * 
	 * }
	 */

}

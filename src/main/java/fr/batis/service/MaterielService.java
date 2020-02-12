package main.java.fr.batis.service;

import java.util.List;

import main.java.fr.batis.entites.Materiel;
import main.java.fr.batis.model.dao.MaterielDAO;

public class MaterielService {

	private MaterielDAO materielDao;

	public MaterielService() {
		materielDao = new MaterielDAO();
	}

	public void saveOrUpdate(Materiel entity) {
		// Session session =
		materielDao.openCurrentSessionwithTransaction();
		// session.merge(entity);
		materielDao.saveOrUpdate(entity);
		materielDao.closeCurrentSessionwithTransaction();
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Materiel findById(Long id) {
		materielDao.openCurrentSession();
		Materiel materiel = materielDao.findById(id);
		materielDao.closeCurrentSession();
		return materiel;
	}

	/**
	 * 
	 * @param id
	 */
	public void delete(Long id) {
		materielDao.openCurrentSessionwithTransaction();
		Materiel materiel = materielDao.findById(id);
		materielDao.delete(materiel);
		materielDao.closeCurrentSessionwithTransaction();
	}

	/**
	 * 
	 * @return
	 */
	public List<Materiel> findAll() {
		materielDao.openCurrentSession();
		List<Materiel> listeMateriaux = materielDao.findAll();
		materielDao.closeCurrentSession();
		return listeMateriaux;
	}

	public List<Materiel> findTenFirst() {
		materielDao.openCurrentSession();
		List<Materiel> materieaux = materielDao.findFirstTen();
		materielDao.closeCurrentSession();
		return materieaux;
	}

	/**
	 * 
	 */
	public void deleteAll() {
		materielDao.openCurrentSessionwithTransaction();
		materielDao.deleteAll();
		materielDao.closeCurrentSessionwithTransaction();
	}

	/**
	 * @return the Employe
	 */
	public MaterielDAO getMaterielDao() {
		return materielDao;
	}

	/**
	 * @param Employe the Employe to set
	 */
	public void setmaterielDao(MaterielDAO materielDao) {
		this.materielDao = materielDao;
	}

	/*
	 * public List<Materiel> searchEmploye(String texte){
	 * materielDao.openCurrentSession(); List<Materiel> listMateriel = new
	 * ArrayList<Materiel>(); if(texte != null && !texte.isEmpty()) {
	 * listMateriel.addAll(materielDao.searchMateriel(texte)); }else {
	 * listMateriel.addAll(materielDao.findAll()); }
	 * 
	 * materielDao.closeCurrentSession(); return listMateriel; }
	 */

	/**
	 * 
	 * @param idChantier
	 * @return
	 */
	public List<Materiel> getMontantPrevu(Long idChantier) {
		materielDao.openCurrentSession();
		List<Materiel> listeMateriaux = materielDao.getMontantPrevu(idChantier);
		materielDao.closeCurrentSession();
		return listeMateriaux;
	}

	/**
	 * 
	 * @param idChantier
	 * @return
	 */
	public List<Materiel> getMontantReel(Long idChantier, Integer codeAcheteP, Integer codaAcheteT) {
		materielDao.openCurrentSession();
		List<Materiel> listeMateriaux = materielDao.getMontantReel(idChantier, codeAcheteP, codaAcheteT);
		materielDao.closeCurrentSession();
		return listeMateriaux;
	}

	/**
	 * 
	 * @param idChantier
	 * @param codeAcheteP
	 * @param codaAcheteT
	 * @return
	 */
	public List<Materiel> getMontantPrevuReel(Long idChantier, Integer codeAcheteP, Integer codaAcheteT) {
		materielDao.openCurrentSession();
		List<Materiel> listeMateriaux = materielDao.getMontantPrevuReel(idChantier, codeAcheteP, codaAcheteT);
		materielDao.closeCurrentSession();
		return listeMateriaux;
	}

	/*
	 * 
	 */
	/*
	 * public List<Materiel> getMaterielAacheterByIdDevis(Long idDevis){
	 * materielDao.openCurrentSession(); List<Materiel> listeMateriaux =
	 * materielDao.getMaterielAacheterByIdDevis(idDevis);
	 * materielDao.closeCurrentSession(); return listeMateriaux; }
	 * 
	 * public List<Materiel> getMaterielAacheterByIdDevisAndName(Long idDevis,String
	 * nom){ materielDao.openCurrentSession(); List<Materiel> listeMateriaux =
	 * materielDao.getMaterielAacheterByIdDevis(idDevis);
	 * materielDao.closeCurrentSession(); return listeMateriaux; }
	 */

	/**
	 * 
	 * @param name
	 * @param idChantier
	 * @param codeStatut
	 * @return
	 */

	/*
	 * public Materiel findByNameIdChantierAndStatut(String name, Long idChantier,
	 * Integer codeStatut){ materielDao.openCurrentSession(); Materiel materiel =
	 * materielDao.findByNameIdChantierAndStatut(name,idChantier,codeStatut);
	 * materielDao.closeCurrentSession(); return materiel; }
	 */
}

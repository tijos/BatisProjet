package main.java.fr.batis.model.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import main.java.fr.batis.entites.StockageDestockage;

public class StockageDestockageDAO implements BatisDaoInterface<StockageDestockage, Long> {
	private SessionFactory sessionFactory;
	private Session currentSession = null;
	private Transaction currentTransaction;

	public StockageDestockageDAO() {

	}

	public Session openCurrentSession() {
		sessionFactory = UtilsHelper.createSessionFactory();
		currentSession = sessionFactory.openSession();
		return currentSession;
	}

	/**
	 * 
	 * @return
	 */
	public Session openCurrentSessionwithTransaction() {
		sessionFactory = UtilsHelper.createSessionFactory();
		currentSession = sessionFactory.openSession();
		currentTransaction = currentSession.beginTransaction();
		return currentSession;
	}

	/**
	 * 
	 */
	public void closeCurrentSession() {
		currentSession.close();
	}

	/**
	 * 
	 */
	public void closeCurrentSessionwithTransaction() {
		currentTransaction.commit();
		currentSession.close();
	}

	/**
	 * 
	 * @return
	 */
	public Session getCurrentSession() {

		return currentSession;
	}

	public void setCurrentSession(Session currentSession) {
		this.currentSession = currentSession;
	}

	/**
	 * 
	 * @return
	 */
	public Transaction getCurrentTransaction() {
		return currentTransaction;
	}

	/**
	 * 
	 * @param currentTransaction
	 */
	public void setCurrentTransaction(Transaction currentTransaction) {
		this.currentTransaction = currentTransaction;
	}

	/**
	 * 
	 */
	@Override
	public void saveOrUpdate(StockageDestockage entity) {
		getCurrentSession().saveOrUpdate(entity);
	}

	/**
	 * 
	 */
	@Override
	public StockageDestockage findById(Long id) {
		StockageDestockage stock = (StockageDestockage) getCurrentSession().get(StockageDestockage.class, id);
		return stock;
	}

	/**
	 * 
	 */
	@Override
	public void delete(StockageDestockage entity) {
		getCurrentSession().delete(entity);
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StockageDestockage> findAll() {
		List<StockageDestockage> stockageDestockages = getCurrentSession().createQuery("from StockageDestockage")
				.list();
		return stockageDestockages;
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public List<StockageDestockage> findFirstTen() {
	 * List<StockageDestockage> StockageDestockage = null;
	 * 
	 * @SuppressWarnings("rawtypes") Query query =
	 * getCurrentSession().createQuery("from StockageDestockage");
	 * query.setFirstResult(0); query.setMaxResults(10); StockageDestockage =
	 * query.list(); return StockageDestockage; }
	 */

	/**
	 * 
	 */
	@Override
	public void deleteAll() {
		List<StockageDestockage> entityList = findAll();
		for (StockageDestockage entity : entityList) {
			delete(entity);
		}
	}

	/*
	 * @Override public StockageDestockage findByName(String name) {
	 * 
	 * @SuppressWarnings("rawtypes") Query query = getCurrentSession().
	 * createQuery("from StockageDestockage where nomProjet = :nom ");
	 * query.setParameter("nom", name); StockageDestockage stockdest = null;
	 * 
	 * @SuppressWarnings("unchecked") List<StockageDestockage>
	 * StockageDestockagesList = query.list();
	 * 
	 * if(!StockageDestockagesList.isEmpty()) { stockdest =
	 * StockageDestockagesList.get(0); } return stockdest; }
	 */

	@SuppressWarnings({ "unchecked" })
	// TODO
	public List<StockageDestockage> getEtatStock(Long idChantier) {

		Criteria criteria = getCurrentSession().createCriteria(StockageDestockage.class);

		criteria.setProjection(Projections.projectionList().add(Projections.property("nomMateriel"))
				.add(Projections.property("qualite")).add(Projections.sum("quantite"))
				.add(Projections.property("uniteMesure")).add(Projections.groupProperty("codeMateriel"))
				.add(Projections.groupProperty("qualite"))).add(Restrictions.eq("idChantier", idChantier))
		// .add( Property.forName("chantier").eq(idChantier) )
		;
		// criteria.add(Restrictions.eq("chantier", idChantier));

		// criteria.setProjection(Projections.groupProperty("codeMateriel"));
		return criteria.list();
	}

	public Double getQunatiteDispoByCode(long code, String qualite) {

		Criteria criteria = getCurrentSession().createCriteria(StockageDestockage.class);
		criteria.add(Restrictions.eq("codeMateriel", code));
		criteria.add(Restrictions.eq("qualite", qualite));
		criteria.setProjection(Projections.projectionList().add(Projections.sum("quantite")));
		Double quantite = (Double) criteria.uniqueResult();
		return quantite;
	}

	@Override
	public StockageDestockage findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StockageDestockage> findFirstTen() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * public List<StockageDestockage> getMaterielBySearch(String texte) {
	 * CriteriaBuilder criteriaBulder = getCurrentSession().getCriteriaBuilder();
	 * CriteriaQuery<StockageDestockage> criteriaQuery =
	 * criteriaBulder.createQuery(StockageDestockage.class);
	 * Root<StockageDestockage> StockageDestockage =
	 * criteriaQuery.from(StockageDestockage.class);
	 * criteriaQuery.select(StockageDestockage); Predicate nomPredicate =
	 * criteriaBulder.like(StockageDestockage.get("nomMateriel"), "%"+texte+"%");
	 * Predicate qualitePredicate =
	 * criteriaBulder.like(StockageDestockage.get("qualite"), "%"+texte+"%");
	 * Predicate orPredicate = criteriaBulder.or(nomPredicate, qualitePredicate);
	 * criteriaQuery.where(orPredicate); return
	 * getCurrentSession().createQuery(criteriaQuery).getResultList() ; }
	 */
}

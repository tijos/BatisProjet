package main.java.fr.batis.model.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import main.java.fr.batis.entites.Parametres;


public class ParametresDAO implements BatisDaoInterface<Parametres, Long> {
	private SessionFactory sessionFactory;
	private Session currentSession = null;
	private Transaction currentTransaction;

	public ParametresDAO() {

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
	public void saveOrUpdate(Parametres entity) {
		getCurrentSession().saveOrUpdate(entity);
	}

	/**
	 * 
	 */
	@Override
	public Parametres findById(Long id) {
		Parametres stock = (Parametres) getCurrentSession().get(Parametres.class, id);
		return stock;
	}

	/**
	 * 
	 */
	@Override
	public void delete(Parametres entity) {
		getCurrentSession().delete(entity);
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Parametres> findAll() {
		List<Parametres> retraitDepots = (List<Parametres>) getCurrentSession()
				.createQuery("from Parametres").list();
		return retraitDepots;
	}

	@Override
	public List<Parametres> findFirstTen() {
		List<Parametres> parametres = null;
	
		return parametres;
	}

	/**
	 * 
	 */
	@Override
	public void deleteAll() {
		List<Parametres> entityList = findAll();
		for (Parametres entity : entityList) {
			delete(entity);
		}
	}

	@Override
	public Parametres findByName(String name) {
		Parametres param = null;

		Criteria criteria = getCurrentSession().createCriteria(Parametres.class);
		criteria.add(Restrictions.eq("nom", name));
		param =  (Parametres) criteria.uniqueResult();
		return param;
	}
	


}

package main.java.fr.batis.model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import main.java.fr.batis.entites.LastOpened;


public class MemoryDAO implements BatisDaoInterface<LastOpened, Long> {
	private SessionFactory sessionFactory;
	private Session currentSession = null;
	private Transaction currentTransaction;

	public MemoryDAO() {
		
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
	public void saveOrUpdate(LastOpened entity) {
		getCurrentSession().saveOrUpdate(entity);
	}

	/**
	 * 
	 */
	@Override
	public LastOpened findById(Long id) {
		LastOpened stock = (LastOpened) getCurrentSession().get(LastOpened.class, id);
		return stock;
	}

	/**
	 * 
	 */
	@Override
	public void delete(LastOpened entity) {
		getCurrentSession().delete(entity);
	}

	
	/**
	 * 
	 */
	@Override
	public void deleteAll() {
		List<LastOpened> entityList = findAll();
		for (LastOpened entity : entityList) {
			delete(entity);
		}
	}

	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public LastOpened getFirst() {
		LastOpened lastChantier = null;
		List<LastOpened> lastOpened =  getCurrentSession().createQuery("from LastOpened").list();
		if(!lastOpened.isEmpty()) {
			lastChantier = lastOpened.get(0);
		}
		return lastChantier;
	}

	@Override
	public LastOpened findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LastOpened> findFirstTen() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LastOpened> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}

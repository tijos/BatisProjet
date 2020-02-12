package main.java.fr.batis.model.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import main.java.fr.batis.entites.Employe;


public class EmployeDAO implements BatisDaoInterface<Employe, Long> {
	private SessionFactory sessionFactory;
	private Session currentSession = null;
	private Transaction currentTransaction;

	public EmployeDAO() {

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
	public void saveOrUpdate(Employe entity) {
		getCurrentSession().saveOrUpdate(entity);
	}

	/**
	 * 
	 */
	@Override
	public Employe findById(Long id) {
		Employe employe = (Employe) getCurrentSession().get(Employe.class, id);
		return employe;
	}

	public void evict(Employe entity) {
		getCurrentSession().evict(entity);
	}
	/**
	 * 
	 */
	@Override
	public void delete(Employe entity) {
		getCurrentSession().delete(entity);
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Employe> findAll() {
		List<Employe> listeEmployes = (List<Employe>) getCurrentSession()
				.createQuery("from Employe").list();
		return listeEmployes;
	}

	

	/**
	 * 
	 */
	@Override
	public void deleteAll() {
		List<Employe> entityList = findAll();
		for (Employe entity : entityList) {
			delete(entity);
		}
	}



	@Override
	public List<Employe> findFirstTen() {
		// TODO Auto-generated method stub
		return null;
	}
	


	@Override
	public Employe findByName(String name) {
		Criteria criteria = getCurrentSession().createCriteria(Employe.class);
		criteria.add(Restrictions.eq("nom", name));
		Employe employe =  (Employe) criteria.uniqueResult();
		return employe;
	}

}

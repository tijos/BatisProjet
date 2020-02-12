package main.java.fr.batis.model.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import main.java.fr.batis.entites.Devis;
import main.java.fr.batis.entites.NomsMateriaux;


public class NomsMateriauxDAO implements BatisDaoInterface<NomsMateriaux, Long> {
	private SessionFactory sessionFactory;
	private Session currentSession = null;
	private Transaction currentTransaction;

	public NomsMateriauxDAO() {

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
	public void saveOrUpdate(NomsMateriaux entity) {
		getCurrentSession().saveOrUpdate(entity);
	}

	/**
	 * 
	 */
	@Override
	public NomsMateriaux findById(Long id) {
		NomsMateriaux nomsMateriel = (NomsMateriaux) getCurrentSession().get(NomsMateriaux.class, id);
		return nomsMateriel;
	}

	public void evict(Devis entity) {
		getCurrentSession().evict(entity);
	}
	/**
	 * 
	 */
	@Override
	public void delete(NomsMateriaux entity) {
		getCurrentSession().delete(entity);
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<NomsMateriaux> findAll() {
		List<NomsMateriaux> listeDevis = (List<NomsMateriaux>) getCurrentSession()
				.createQuery("from NomsMateriaux").list();
		return listeDevis;
	}

	

	/**
	 * 
	 */
	@Override
	public void deleteAll() {
		List<NomsMateriaux> entityList = findAll();
		for (NomsMateriaux entity : entityList) {
			delete(entity);
		}
	}


/**
 * 
 * @param code
 * @return
 */
	public NomsMateriaux getNomByCode(long code) {

		Criteria criteria = getCurrentSession().createCriteria(NomsMateriaux.class);
		criteria.add(Restrictions.eq("code", code));
		NomsMateriaux mat =  (NomsMateriaux) criteria.uniqueResult();
		return mat;
	}

	@Override
	public List<NomsMateriaux> findFirstTen() {
		// TODO Auto-generated method stub
		return null;
	}
	
   /**
    * 
    * @return
    */
	public NomsMateriaux getLast() {
		List<NomsMateriaux> list = this.findAll();
		return !list.isEmpty()? list.get(list.size()-1) : null;
	}
	
	@Override
	public NomsMateriaux findByName(String name) {
		Criteria criteria = getCurrentSession().createCriteria(NomsMateriaux.class);
		criteria.add(Restrictions.eq("nom", name));
		NomsMateriaux nom =  (NomsMateriaux) criteria.uniqueResult();
		return nom;
	}


}

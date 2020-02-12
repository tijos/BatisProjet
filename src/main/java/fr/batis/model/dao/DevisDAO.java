package main.java.fr.batis.model.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import main.java.fr.batis.entites.Devis;
import main.java.fr.batis.entites.PhaseConstruction;


public class DevisDAO implements BatisDaoInterface<Devis, Long> {
	private SessionFactory sessionFactory;
	private Session currentSession = null;
	private Transaction currentTransaction;

	public DevisDAO() {

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
	public void saveOrUpdate(Devis entity) {
		getCurrentSession().saveOrUpdate(entity);
	}

	/**
	 * 
	 */
	@Override
	public Devis findById(Long id) {
		Devis devis = (Devis) getCurrentSession().get(Devis.class, id);
		return devis;
	}

	public void evict(Devis entity) {
		getCurrentSession().evict(entity);
	}
	/**
	 * 
	 */
	@Override
	public void delete(Devis entity) {
		getCurrentSession().delete(entity);
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Devis> findAll() {
		List<Devis> listeDevis = (List<Devis>) getCurrentSession()
				.createQuery("from Devis").list();
		return listeDevis;
	}

	

	/**
	 * 
	 */
	@Override
	public void deleteAll() {
		List<Devis> entityList = findAll();
		for (Devis entity : entityList) {
			delete(entity);
		}
	}



	public Double getQunatiteDispoByCode(long code) {

		Criteria criteria = getCurrentSession().createCriteria(Devis.class);
		criteria.add(Restrictions.eq("codeMateriel", code));
		criteria.setProjection(Projections.projectionList().add(Projections.sum("quantite"))
		// .add(Projections.groupProperty("codeMateriel"))
		);
		Double quantite = (Double) criteria.uniqueResult();
		return quantite;
	}

	
	public Devis findDevisByNameAndIdPhase(String name,Long idPhase) {

		Criteria criteria = getCurrentSession().createCriteria(Devis.class);
		criteria.add(Restrictions.eq("nom", name));
		Devis devis =  (Devis) criteria.uniqueResult();
		return devis;
	}

	@Override
	public List<Devis> findFirstTen() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Devis getDevisByNameAndPhase(String name, PhaseConstruction phase) {

		Criteria criteria = getCurrentSession().createCriteria(Devis.class);
		criteria.add(Restrictions.eq("nom", name));
		criteria.add(Restrictions.eq("phase", phase));
		//criteria.setProjection(Projections.projectionList().add(Projections.sum("quantite"))
		// .add(Projections.groupProperty("codeMateriel"))
		//);
		Devis devis =  (Devis) criteria.uniqueResult();
		return devis;
	}

	@Override
	public Devis findByName(String name) {
		Criteria criteria = getCurrentSession().createCriteria(Devis.class);
		criteria.add(Restrictions.eq("nom", name));
		Devis devis =  (Devis) criteria.uniqueResult();
		return devis;
	}

}

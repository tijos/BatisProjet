package main.java.fr.batis.model.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import main.java.fr.batis.entites.Chantier;
import main.java.fr.batis.entites.PhaseConstruction;


public class PhaseDAO implements BatisDaoInterface<PhaseConstruction, Long> {
	private SessionFactory sessionFactory;
	private Session currentSession = null;
	private Transaction currentTransaction;

	public PhaseDAO() {

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
	public void saveOrUpdate(PhaseConstruction entity) {
		getCurrentSession().saveOrUpdate(entity);
	}

	/**
	 * 
	 */
	@Override
	public PhaseConstruction findById(Long id) {
		PhaseConstruction stock = (PhaseConstruction) getCurrentSession().get(PhaseConstruction.class, id);
		return stock;
	}

	/**
	 * 
	 */
	@Override
	public void delete(PhaseConstruction entity) {
		getCurrentSession().delete(entity);
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PhaseConstruction> findAll() {
		List<PhaseConstruction> retraitDepots = (List<PhaseConstruction>) getCurrentSession()
				.createQuery("from PhaseConstruction").list();
		return retraitDepots;
	}

	@Override
	public List<PhaseConstruction> findFirstTen() {
		List<PhaseConstruction> phaseConstruction = null;
		/*@SuppressWarnings("rawtypes")
		Query query = getCurrentSession().createQuery("from PhaseConstruction");
		query.setFirstResult(0);
		query.setMaxResults(10);
		phaseConstruction = query.list();*/
		return phaseConstruction;
	}

	/**
	 * 
	 */
	@Override
	public void deleteAll() {
		List<PhaseConstruction> entityList = findAll();
		for (PhaseConstruction entity : entityList) {
			delete(entity);
		}
	}

	@Override
	public PhaseConstruction findByName(String name) {
		PhaseConstruction phase = null;

		Criteria criteria = getCurrentSession().createCriteria(PhaseConstruction.class);
		criteria.add(Restrictions.eq("nom", name));
		 phase =  (PhaseConstruction) criteria.uniqueResult();
		return phase;
	}
	
	public PhaseConstruction findByNameAndChantier(String name, Chantier chantier) {
		PhaseConstruction phase = null;

		Criteria criteria = getCurrentSession().createCriteria(PhaseConstruction.class);
		criteria.add(Restrictions.eq("nom", name));
		criteria.add(Restrictions.eq("chantier", chantier));
		 phase =  (PhaseConstruction) criteria.uniqueResult();
		return phase;
	}

	@SuppressWarnings({ "unchecked" })

	public List<PhaseConstruction> getEtatStock() {

		Criteria criteria = getCurrentSession().createCriteria(PhaseConstruction.class);
		criteria.setProjection(Projections.projectionList()

				.add(Projections.property("nomMateriel")).add(Projections.property("qualite"))
				.add(Projections.sum("quantite")).add(Projections.property("uniteMesure"))
				.add(Projections.groupProperty("codeMateriel")));

		return criteria.list();
	}

	public Double getQunatiteDispoByCode(long code) {

		Criteria criteria = getCurrentSession().createCriteria(PhaseConstruction.class);
		criteria.add(Restrictions.eq("codeMateriel", code));
		criteria.setProjection(Projections.projectionList().add(Projections.sum("quantite"))
		// .add(Projections.groupProperty("codeMateriel"))
		);
		Double quantite = (Double) criteria.uniqueResult();
		return quantite;
	}

}

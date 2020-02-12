package main.java.fr.batis.model.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import main.java.fr.batis.entites.Inventaire;


public class InventaireDAO implements BatisDaoInterface<Inventaire, Long> {
	private SessionFactory sessionFactory;
	private Session currentSession = null;
	private Transaction currentTransaction;

	public InventaireDAO() {
		
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
	public void saveOrUpdate(Inventaire entity) {
		getCurrentSession().saveOrUpdate(entity);
	}

	/**
	 * 
	 */
	@Override
	public Inventaire findById(Long id) {
		Inventaire stock = (Inventaire) getCurrentSession().get(Inventaire.class, id);
		return stock;
	}

	/**
	 * 
	 */
	@Override
	public void delete(Inventaire entity) {
		getCurrentSession().delete(entity);
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Inventaire> findAll() {
		List<Inventaire> inventaires = (List<Inventaire>) getCurrentSession().createQuery("from Inventaire").list();
		return inventaires;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Inventaire> findFirstTen() {
		List<Inventaire> inventaires = null; 
				Query query = getCurrentSession().createQuery("from Inventaire");
				query.setFirstResult(0);
				query.setMaxResults(10);
				 inventaires = query.list();
		return inventaires;
	}
	
	/**
	 * 
	 */
	@Override
	public void deleteAll() {
		List<Inventaire> entityList = findAll();
		for (Inventaire entity : entityList) {
			delete(entity);
		}
	}

	

	public Inventaire findByName(Long id) {
		Query query = getCurrentSession().createQuery("from Inventaire where idChantier = :id order by dateInventaire");
		query.setParameter("id", id);
		Inventaire  invent = null;
		@SuppressWarnings("unchecked")
		List<Inventaire> inventaireList = query.list();
		
		if(!inventaireList.isEmpty()) {
			invent = inventaireList.get(0);
		}	
		return invent;
	}


	@Override
	public Inventaire findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}


	/*public List<Inventaire> getListRD(String texte, boolean retrait, boolean depot) {
		
		CriteriaBuilder criteriaBulder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Inventaire> criteriaQuery = criteriaBulder.createQuery(Inventaire.class);
		Root<Inventaire> inventaireRoot = criteriaQuery.from(Inventaire.class);		 
		criteriaQuery.select(inventaireRoot);	
		Predicate motifPredicate = criteriaBulder.like(inventaireRoot.get("motif"), "%"+texte+"%");
		Predicate typePredicate = criteriaBulder.like(inventaireRoot.get("typeBeneficiaire"), "%"+texte+"%");
		Predicate orPredicate = criteriaBulder.or(motifPredicate, typePredicate);
		criteriaQuery.where(orPredicate);
		return 	getCurrentSession().createQuery(criteriaQuery).getResultList() ;
	}*/
}

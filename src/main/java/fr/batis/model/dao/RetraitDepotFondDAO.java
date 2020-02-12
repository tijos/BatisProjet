package main.java.fr.batis.model.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;

import main.java.fr.batis.entites.Chantier;
import main.java.fr.batis.entites.Materiel;
import main.java.fr.batis.entites.RetraitDepotFond;

public class RetraitDepotFondDAO implements BatisDaoInterface<RetraitDepotFond, Long> {
	private SessionFactory sessionFactory;
	private Session currentSession = null;
	private Transaction currentTransaction;

	public RetraitDepotFondDAO() {

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
	public void saveOrUpdate(RetraitDepotFond entity) {
		getCurrentSession().saveOrUpdate(entity);
	}

	/**
	 * 
	 */
	@Override
	public RetraitDepotFond findById(Long id) {
		RetraitDepotFond chantier = (RetraitDepotFond) getCurrentSession().get(RetraitDepotFond.class, id);
		return chantier;
	}

	/**
	 * 
	 */
	@Override
	public void delete(RetraitDepotFond entity) {
		getCurrentSession().delete(entity);
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RetraitDepotFond> findAll() {
		List<RetraitDepotFond> retraitDepots = (List<RetraitDepotFond>) getCurrentSession().createQuery("from RetraitDepotFond").list();
		return retraitDepots;
	}

	
@SuppressWarnings("unchecked")
public List<RetraitDepotFond> getMontantDepose(Long idChantier) {
		
		Criteria criteria = getCurrentSession().createCriteria(Materiel.class);	
		criteria.setProjection(Projections.projectionList()
				.add(Projections.sum("prixTotPrevu"))
				)
		.add( Property.forName("idChantier").eq(idChantier) );//TODO
	//	.add( Property.forName("codeStatutAchat").in(codaAcheteP,codaAcheteT));
		return criteria.list() ;
	}
/*	
	@SuppressWarnings("unchecked")
	@Override
	public List<RetraitDepotFond> findFirstTen() {
		List<RetraitDepotFond> retraitDepots = null; 
				@SuppressWarnings("rawtypes")
				Query query = getCurrentSession().createQuery("from RetraitDepot");
				query.setFirstResult(0);
				query.setMaxResults(10);
				 retraitDepots = query.list();
		return retraitDepots;
	}*/
	
	/**
	 * 
	 */
	@Override
	public void deleteAll() {
		List<RetraitDepotFond> entityList = findAll();
		for (RetraitDepotFond entity : entityList) {
			delete(entity);
		}
	}

	@Override
	public RetraitDepotFond findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RetraitDepotFond> findFirstTen() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<RetraitDepotFond> findByChantier(Chantier chantier) {
	
		Query query = getCurrentSession().createQuery("from RetraitDepotFond where chantier = :chantier ");
		query.setParameter("chantier", chantier);
	
		@SuppressWarnings("unchecked")
		List<RetraitDepotFond> retraitDepotList = query.list();
		
		return retraitDepotList;
	}
	
/*	@Override
	public RetraitDepotFond findByName(String name) {
		@SuppressWarnings("rawtypes")
		Query query = getCurrentSession().createQuery("from RetraitDepotFond where nomProjet = :nom ");
		query.setParameter("nom", name);
		RetraitDepotFond  retraitDepot = null;
		@SuppressWarnings("unchecked")
		List<RetraitDepotFond> retraitDepotList = query.list();
		
		if(!retraitDepotList.isEmpty()) {
			retraitDepot = retraitDepotList.get(0);
		}	
		return retraitDepot;
	}*/


/*	public List<RetraitDepotFond> getListRD(String texte, boolean retrait, boolean depot) {
		Predicate typePredicate = null;
		CriteriaBuilder criteriaBulder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<RetraitDepotFond> criteriaQuery = criteriaBulder.createQuery(RetraitDepotFond.class);
		Root<RetraitDepotFond> retraitDepotRoot = criteriaQuery.from(RetraitDepotFond.class);		 
		criteriaQuery.select(retraitDepotRoot);	
		Predicate motifPredicate = criteriaBulder.like(retraitDepotRoot.get("motif"), "%"+texte+"%");
		Predicate typeBenPredicate = criteriaBulder.like(retraitDepotRoot.get("typeBeneficiaire"), "%"+texte+"%");
		Predicate orPredicate = criteriaBulder.or(motifPredicate, typeBenPredicate);
		
		if(retrait && !depot) {
			 typePredicate = criteriaBulder.equal(retraitDepotRoot.get("type"),  _Retrait_Depot_Type.RETRAIT.getDescription());
		}
		
		if(depot && !retrait) {
			 typePredicate = criteriaBulder.equal(retraitDepotRoot.get("type"),  _Retrait_Depot_Type.DEPOT.getDescription());
		}
		
		if(retrait && depot) {
			Predicate retraitPredicate = criteriaBulder.equal(retraitDepotRoot.get("type"),  _Retrait_Depot_Type.RETRAIT.getDescription());
			Predicate depotPredicate = criteriaBulder.equal(retraitDepotRoot.get("type"),  _Retrait_Depot_Type.DEPOT.getDescription());
			 typePredicate = criteriaBulder.or(retraitPredicate, depotPredicate);
		}
		
		criteriaQuery.where(orPredicate,typePredicate);
		return getCurrentSession().createQuery(criteriaQuery).getResultList() ;
	}*/
}

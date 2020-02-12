package main.java.fr.batis.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

//import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import main.java.fr.batis.entites.Chantier;

public class ChantierDAO implements BatisDaoInterface<Chantier, Long> {

	private SessionFactory sessionFactory;
	private Session currentSession = null;
	private Transaction currentTransaction;

	public ChantierDAO() {

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
	public void saveOrUpdate(Chantier entity) {
		// getCurrentSession().merge(entity);
		getCurrentSession().saveOrUpdate(entity);
	}

	/**
	 * 
	 */
	@Override
	public Chantier findById(Long id) {
		Chantier chantier = (Chantier) getCurrentSession().get(Chantier.class, id);
		return chantier;
	}

	/**
	 * 
	 */
	@Override
	public void delete(Chantier entity) {
		getCurrentSession().delete(entity);
	}

	/**
	 * @param d
	 * @param c
	 * @param b
	 * 
	 */
	@SuppressWarnings("unchecked")

	public List<Chantier> findAll(boolean actif, boolean closed, boolean desactive, boolean all) {
		String hql = "from Chantier chantier";
		List<Chantier> chantiers = new ArrayList<Chantier>();
		Query query = null;
		if (actif && !closed && !desactive && !all) {
			hql = "from Chantier chantier where chantier.actif = :actif";
			query = getCurrentSession().createQuery(hql);
			query.setParameter("actif", actif);
			chantiers = query.setCacheable(true).list();
		}

		if (!actif && closed && !desactive && !all) {
			hql = "from Chantier chantier where chantier.closed = :close ";
			query = getCurrentSession().createQuery(hql);
			query.setParameter("close", closed);
			chantiers = query.setCacheable(true).list();
		}

		if (!actif && !closed && desactive && !all) {
			hql = "from Chantier chantier where chantier.actif = :actif";
			query = getCurrentSession().createQuery(hql);
			query.setParameter("actif", actif);
			chantiers = query.setCacheable(true).list();
		}

		if (all) {
			query = getCurrentSession().createQuery(hql);
			chantiers = query.setCacheable(true).list();

		}

		return chantiers;
	}

	/**
	 * 
	 */
	@Override
	public void deleteAll() {
		List<Chantier> entityList = findAll();
		for (Chantier entity : entityList) {
			delete(entity);
		}
	}

	@Override
	public Chantier findByName(String name) {
		Chantier chantier = null;
		/*
		 * @SuppressWarnings("rawtypes") Query query =
		 * getCurrentSession().createQuery("from Chantier where nomProjet = :nom ");
		 * query.setParameter("nom", name); Chantier chantier = null;
		 * 
		 * @SuppressWarnings("unchecked") List<Chantier> chantierList =
		 * query.setCacheable(true).list();
		 * 
		 * if(!chantierList.isEmpty()) { chantier = chantierList.get(0); }
		 */
		return chantier;
	}

	@Override
	public List<Chantier> findFirstTen() {
		// TODO Auto-generated method stub
		return null;
	}
	/*
	 * public List<Employe> getListTotalEmployes(Long idChantier){
	 * 
	 * Query query = getCurrentSession().
	 * createQuery("from Employe emp inner join Employe_Chantier ec on emp.id = ec.id_employe and ec.id_chantier = :idchantier "
	 * ); query.setParameter("idchantier", 2L); List<Employe> list = query.list();
	 * CriteriaBuilder criteriaBulder = getCurrentSession().getCriteriaBuilder();
	 * CriteriaQuery<Employe> criteriaQuery =
	 * criteriaBulder.createQuery(Employe.class); Root<Employe> employeRoot =
	 * criteriaQuery.from(Employe.class); // final Root<Employe_Chantier>
	 * employe_ChantierRoot = criteriaQuery.from(Employe_Chantier.class);
	 * Join<Employe,Chantier> emp = employeRoot.join("listChantier",
	 * JoinType.INNER);
	 * criteriaQuery.multiselect(emp).where(criteriaBulder.equal(emp.get("id"),
	 * idChantier));
	 * 
	 * 
	 * List<Employe> employeList = null; //TODO // employeList =
	 * getCurrentSession().createQuery(criteriaQuery).getResultList(); return
	 * employeList; }
	 */

	@Override
	public List<Chantier> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}

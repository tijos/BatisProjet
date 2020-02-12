package main.java.fr.batis.model.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;

import main.java.fr.batis.entites.Materiel;

public class MaterielDAO implements BatisDaoInterface<Materiel, Long> {
	private SessionFactory sessionFactory;
	private Session currentSession = null;
	private Transaction currentTransaction;

	public MaterielDAO() {

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
	public void saveOrUpdate(Materiel entity) {
		getCurrentSession().saveOrUpdate(entity);
	}

	/**
	 * 
	 */
	@Override
	public Materiel findById(Long id) {
		Materiel materiel = (Materiel) getCurrentSession().get(Materiel.class, id);
		return materiel;
	}

	/**
	 * 
	 */
	@Override
	public void delete(Materiel entity) {
		getCurrentSession().delete(entity);
	}

	/**
	 * 
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<Materiel> findAll() {
		List<Materiel> materiaux = getCurrentSession().createQuery("from Materiel").list();
		return materiaux;
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public Materiel findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Materiel> findFirstTen() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @param idChantier
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Materiel> getMontantPrevu(Long idChantier) {

		Criteria criteria = getCurrentSession().createCriteria(Materiel.class);
		criteria.setProjection(Projections.projectionList().add(Projections.sum("prixTotPrevu")))
				.add(Property.forName("idChantier").eq(idChantier));
		return criteria.list();
	}

	/**
	 * 
	 * @param idChantier
	 * @param codaAcheteP
	 * @param codaAcheteT
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Materiel> getMontantPrevuReel(Long idChantier, Integer codaAcheteP, Integer codaAcheteT) {

		Criteria criteria = getCurrentSession().createCriteria(Materiel.class);
		criteria.setProjection(Projections.projectionList().add(Projections.sum("prixTotPrevu")))
				.add(Property.forName("idChantier").eq(idChantier));// TODO
		// .add( Property.forName("codeStatutAchat").in(codaAcheteP,codaAcheteT));
		return criteria.list();
	}

	/**
	 * 
	 * @param idChantier
	 * @param codaAcheteP
	 * @param codaAcheteT
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Materiel> getMontantReel(Long idChantier, Integer codaAcheteP, Integer codaAcheteT) {

		Criteria criteria = getCurrentSession().createCriteria(Materiel.class);

		criteria.setProjection(Projections.projectionList()

				.add(Projections.sum("prixTotReel"))

		).add(Property.forName("idChantier").eq(idChantier))
		// .add( Property.forName("codeStatutAchat").in(codaAcheteP,codaAcheteT))
		;
		// criteria.add(Restrictions.eq("chantier", idChantier));

		// criteria.setProjection(Projections.groupProperty("codeMateriel"));
		return criteria.list();
	}

	/*
	 * public List<Materiel> searchMateriel(String code){
	 * 
	 * CriteriaBuilder criteriaBulder = getCurrentSession().getCriteriaBuilder();
	 * CriteriaQuery<Materiel> criteriaQuery =
	 * criteriaBulder.createQuery(Materiel.class); Root<Materiel> materielRoot =
	 * criteriaQuery.from(Materiel.class); criteriaQuery.select(materielRoot);
	 * Predicate nomPredicate = criteriaBulder.like(materielRoot.get("nom"),
	 * "%"+code+"%"); Predicate prenomPredicate =
	 * criteriaBulder.like(materielRoot.get("prenom"), "%"+code+"%"); // Predicate
	 * dateNaissancePredicate =
	 * criteriaBulder.like(employeRoot.get("dateNaissance"), "%"+texte+"%");
	 * Predicate descQualifPredicate =
	 * criteriaBulder.like(materielRoot.get("descriptionQualification"),
	 * "%"+code+"%"); //TODO // Predicate codeQualifPredicate =
	 * criteriaBulder.like(employeRoot.get("codeQualification"), "%"+texte+"%"); //
	 * Predicate salairePredicate = criteriaBulder.like(employeRoot.get("salaire"),
	 * "%"+new Double(texte)+"%"); Predicate devisePredicate =
	 * criteriaBulder.like(materielRoot.get("devise"), "%"+code+"%"); Predicate
	 * freqRemPredicate =
	 * criteriaBulder.like(materielRoot.get("frequenceReunumeration"),
	 * "%"+code+"%"); // Predicate dateEmbPredicate =
	 * criteriaBulder.like(employeRoot.get("dateEmbauche"), "%"+texte+"%");
	 * Predicate autresInfosPredicate =
	 * criteriaBulder.like(materielRoot.get("autresInfos"), "%"+code+"%"); Predicate
	 * telephonePredicate = criteriaBulder.like(materielRoot.get("telephone"),
	 * "%"+code+"%"); Predicate emailPredicate =
	 * criteriaBulder.like(materielRoot.get("email"), "%"+code+"%");
	 * 
	 * Predicate orPredicate = criteriaBulder.or(nomPredicate,
	 * prenomPredicate,descQualifPredicate,
	 * devisePredicate,freqRemPredicate,autresInfosPredicate,telephonePredicate,
	 * emailPredicate);
	 * 
	 * criteriaQuery.where(orPredicate); return
	 * getCurrentSession().createQuery(criteriaQuery).getResultList() ; }
	 */

	/*
	 * public List<Materiel> getMaterielAacheterByIdDevis(Long idDevis){
	 * CriteriaBuilder criteriaBulder = getCurrentSession().getCriteriaBuilder();
	 * CriteriaQuery<Materiel> criteriaQuery =
	 * criteriaBulder.createQuery(Materiel.class); Root<Materiel> materielRoot =
	 * criteriaQuery.from(Materiel.class); criteriaQuery.select(materielRoot);
	 * 
	 * Predicate idDevisPredicate =
	 * criteriaBulder.equal(materielRoot.get("devis"),idDevis); Predicate
	 * statutPredicate1 = criteriaBulder.equal(materielRoot.get("codeTraitement"),
	 * -1); Predicate statutPredicate2 =
	 * criteriaBulder.equal(materielRoot.get("statutAchat"),
	 * _StatutAchat.A_ACHETER.getCode());
	 * 
	 * Predicate andPredicate = criteriaBulder.and(statutPredicate1,
	 * statutPredicate2); criteriaQuery.where(idDevisPredicate,andPredicate); return
	 * getCurrentSession().createQuery(criteriaQuery).getResultList() ; }
	 */

	/*
	 * public List<Materiel> getMaterielAacheterByIdDevisAndName(Long idDevis,String
	 * name){ CriteriaBuilder criteriaBulder =
	 * getCurrentSession().getCriteriaBuilder(); CriteriaQuery<Materiel>
	 * criteriaQuery = criteriaBulder.createQuery(Materiel.class); Root<Materiel>
	 * materielRoot = criteriaQuery.from(Materiel.class);
	 * criteriaQuery.select(materielRoot);
	 * 
	 * Predicate idDevisPredicate =
	 * criteriaBulder.equal(materielRoot.get("devis"),idDevis); Predicate
	 * nameDevisPredicate = criteriaBulder.equal(materielRoot.get("nom"),name);
	 * Predicate statutPredicate1 =
	 * criteriaBulder.equal(materielRoot.get("codeStatutAchat"),
	 * _StatutAchat.A_ACHETER.getCode()); Predicate statutPredicate2 =
	 * criteriaBulder.equal(materielRoot.get("codeStatutAchat"),
	 * _StatutAchat.ACHETE_P.getCode()); Predicate orPredicate =
	 * criteriaBulder.or(statutPredicate1, statutPredicate2);
	 * criteriaQuery.where(idDevisPredicate,nameDevisPredicate,orPredicate); return
	 * getCurrentSession().createQuery(criteriaQuery).getResultList() ; }
	 */

	/*
	 * public Materiel findByNameIdChantierAndStatut(String name, Long idChantier,
	 * Integer codeStatut) {
	 * 
	 * @SuppressWarnings("rawtypes") Query query = getCurrentSession().
	 * createQuery("from Materiel where nom = :nom and idChantier =:id and codeStatutAchat =: code"
	 * ); query.setParameter("nom", name); query.setParameter("id", idChantier);
	 * query.setParameter("code", codeStatut); Materiel materiel = (Materiel)
	 * query.uniqueResult(); return materiel; }
	 */
}

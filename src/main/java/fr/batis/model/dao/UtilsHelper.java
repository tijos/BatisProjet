package main.java.fr.batis.model.dao; 
import org.hibernate.SessionFactory;

import main.java.fr.batis.utils.HibernateUtil;


public class UtilsHelper {

	public static SessionFactory createSessionFactory() {
	
		SessionFactory sessionFactory =  HibernateUtil.getSessionFactory();
	    return sessionFactory;
	}
}

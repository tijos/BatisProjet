package main.java.fr.batis.utils;


import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
 
public class HibernateUtil
{
	
   private static SessionFactory sessionFactory = buildSessionFactory();
 //  private static final Logger logger = LogManager.getLogger(BatisUi.class);
   private static SessionFactory buildSessionFactory()
   {
    //  try
     // {
    	  
    	  if (sessionFactory == null) {
              // loads configuration and mappings
              Configuration configuration = new Configuration().configure();
              ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                      .applySettings(configuration.getProperties()).build();
               
              // builds a session factory from the service registry
              sessionFactory = configuration.buildSessionFactory(serviceRegistry);           
          }
         return sessionFactory;
     // } catch (Throwable ex)
     // {
        // logger.error("Initial SessionFactory creation failed", ex.getMessage());
        // throw new ExceptionInInitializerError(ex);
      //}
   }

   public static SessionFactory getSessionFactory()
   {
      return sessionFactory;
   }
 
   /**
    * 
    */
   public static void shutdown()
   {
      getSessionFactory().close();
   }
}


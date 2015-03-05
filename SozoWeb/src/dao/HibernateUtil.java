package dao;

import model.Funcionario;
import model.Viatura;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;
 
/**
* Hibernate Utility class with a convenient method to get Session Factory object.
*
* @author José Alexandre
*/
public class HibernateUtil {
 
	private static SessionFactory sessionFactory;
	 
	private HibernateUtil() {
	}
	 
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
		try {
			// Create the SessionFactory from standard (hibernate.cfg.xml)
			// config file.
			AnnotationConfiguration ac = new AnnotationConfiguration();
			ac.addAnnotatedClass(Viatura.class).addAnnotatedClass(Funcionario.class);
			sessionFactory = ac.configure().buildSessionFactory();
		} catch (Throwable ex) {
			// Log the exception.
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
			return sessionFactory;
		} else {
			return sessionFactory;
		}
	}
}

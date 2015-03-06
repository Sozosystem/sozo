package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerHelper {
	
	private static final String UNIT_NAME = "sozoweb";
	
	private EntityManagerFactory emf;
	private EntityManager em;
	
	public EntityManagerHelper() {
		if(emf == null) {
			emf = Persistence.createEntityManagerFactory(UNIT_NAME);
			em = emf.createEntityManager();
		}
	}
	
	public EntityManager getEntityManager() {
		return em;
	}
}

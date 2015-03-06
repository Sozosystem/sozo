package dao;


import java.lang.reflect.ParameterizedType;
import java.util.List;
 

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
 
@SuppressWarnings("unchecked")
public class GenericDAO<PK, T> {
    protected EntityManager entityManager;
 
    public GenericDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
 
    public T getById(PK pk) {
        return (T) entityManager.find(getTypeClass(), pk);
    }
 
    public void save(T entity) {
    	EntityTransaction transaction = entityManager.getTransaction();
    	try {
	    	transaction.begin();
	        entityManager.persist(entity);
	        transaction.commit();
    	} catch (Exception e) {
    		if(transaction.isActive()) {
    			transaction.rollback();
    		}
    	}
        
        //entityManager.
    }
 
    public void update(T entity) {
        EntityTransaction transaction = entityManager.getTransaction();
    	try {
	    	transaction.begin();
	    	 entityManager.merge(entity);
	        transaction.commit();
    	} catch (Exception e) {
    		if(transaction.isActive()) {
    			transaction.rollback();
    		}
    	}
    }
 
    public void delete(T entity) {
        EntityTransaction transaction = entityManager.getTransaction();
    	try {
	    	transaction.begin();
	    	entityManager.remove(entity);
	        transaction.commit();
    	} catch (Exception e) {
    		if(transaction.isActive()) {
    			transaction.rollback();
    		}
    	}
        
    }
 
    public List<T> findAll() {
        return entityManager.createQuery(("FROM " + getTypeClass().getName()))
                .getResultList();
    }
 
    private Class<?> getTypeClass() {
        Class<?> clazz = (Class<?>) ((ParameterizedType) this.getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1];
        return clazz;
    }
}
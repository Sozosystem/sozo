package dao;


import java.lang.reflect.ParameterizedType;
import java.util.List;
 





import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
 
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
 
    public void delete(T entity) throws Exception {
        EntityTransaction transaction = entityManager.getTransaction();
    	try {
	    	transaction.begin();
	    	entityManager.remove(entity);
	        transaction.commit();
    	} catch (Exception e) {
    		if(transaction.isActive()) {
    			transaction.rollback();
    		}
    		throw e;
    	}
    }
 
    public List<T> findAll() {
        return entityManager.createQuery(("FROM " + getTypeClass().getName()))
                .getResultList();
    }
    
    public List<T> findByObject(T entity) {
		Example example = Example.create(entity).enableLike(MatchMode.ANYWHERE).excludeZeroes().ignoreCase();
		Session s = (Session) entityManager.getDelegate();
		Criteria result = s.createCriteria(entity.getClass()).add(example);
		
		return result.list();
	}
    
    private Class<?> getTypeClass() {
        Class<?> clazz = (Class<?>) ((ParameterizedType) this.getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1];
        return clazz;
    }
}
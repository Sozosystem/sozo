package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import model.TipoFunc;
import dao.GenericDAO;

public class TipoFuncDAO extends GenericDAO<Integer,TipoFunc>{

	
	public TipoFuncDAO(EntityManager  entityManager){
		super( entityManager);
	}
	
	public TipoFunc pesquisarTipoFunc(Integer id) {
		TypedQuery<TipoFunc> query = entityManager.createQuery("from tipfunc t where t.id = :id", TipoFunc.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}

}

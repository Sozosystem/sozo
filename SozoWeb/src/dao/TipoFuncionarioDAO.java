package dao;

import javax.persistence.EntityManager;

import model.TipoFuncionario;

public class TipoFuncionarioDAO extends GenericDAO<Integer, TipoFuncionario>{

	public TipoFuncionarioDAO(EntityManager entityManager) {
		super(entityManager);
	}

}

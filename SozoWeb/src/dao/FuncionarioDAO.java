package dao;

import javax.persistence.EntityManager;

import model.Funcionario;

public class FuncionarioDAO extends GenericDAO<Long, Funcionario> {

	public FuncionarioDAO(EntityManager entityManager) {
		super(entityManager);
		// TODO Auto-generated constructor stub
	}

}
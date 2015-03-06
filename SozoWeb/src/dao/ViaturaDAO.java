package dao;

import javax.persistence.EntityManager;

import model.Viatura;

public class ViaturaDAO extends GenericDAO<Integer, Viatura> {

	public ViaturaDAO(EntityManager entityManager) {
		super(entityManager);
		// TODO Auto-generated constructor stub
	}

}

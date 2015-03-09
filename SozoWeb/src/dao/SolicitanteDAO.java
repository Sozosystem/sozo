package dao;

import javax.persistence.EntityManager;

import model.Solicitante;

public class SolicitanteDAO extends GenericDAO<Integer, Solicitante> {

	public SolicitanteDAO(EntityManager entityManager) {
		super(entityManager);
		// TODO Auto-generated constructor stub
	}

}

package dao;

import javax.persistence.EntityManager;

import model.Ocorrencia;

public class OcorrenciaDAO extends GenericDAO<Integer, Ocorrencia>{

	public OcorrenciaDAO(EntityManager entityManager) {
		super(entityManager);
	}

}

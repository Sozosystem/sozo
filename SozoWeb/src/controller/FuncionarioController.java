package controller;

import model.Funcionario;
import dao.FuncionarioDAO;
import dao.SimpleEntityManager;

public class FuncionarioController {
	private FuncionarioDAO dao;
	
	private SimpleEntityManager simpleEntityManager;
	
	public FuncionarioController(SimpleEntityManager simpleEntityManager) {
		this.simpleEntityManager = simpleEntityManager;
		//dao = new FuncionarioDAO(entityManager)
	}
	
	public void save(Funcionario f) {
		try {
			simpleEntityManager.beginTransaction();
            dao.save(f);
            simpleEntityManager.commit();
		} catch(Exception e) {
			e.printStackTrace();
            simpleEntityManager.rollBack();
		}
	}
}

package controller;

import model.Viatura;
import services.ViaturaServices;
import dao.SimpleEntityManager;
import dao.ViaturaDAO;

public class ViaturaController {

private ViaturaDAO dao;
	
	private SimpleEntityManager simpleEntityManager;
	
	public ViaturaController(SimpleEntityManager simpleEntityManager) {
		this.simpleEntityManager = simpleEntityManager;
	}
	
	public void salvar(Viatura v) {
		ViaturaServices srv = new ViaturaServices();
		
		if(srv.validarViatura(v)){
			simpleEntityManager.beginTransaction();
            dao.save(v);
            simpleEntityManager.commit();
		} else {
			System.out.println("erro");
		}
	}
	
	
	public void retornarNome(Viatura v){
		ViaturaServices srv = new ViaturaServices();
		srv.getViaturaFuncionario(v,simpleEntityManager);
		
	}
	
}

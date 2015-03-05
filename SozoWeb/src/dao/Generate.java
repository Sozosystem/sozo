package dao;

import controller.FuncionarioController;
import controller.ViaturaController;
import model.Funcionario;
import model.Viatura;

public class Generate {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		Funcionario f = new Funcionario("caio", "das", "chagas");
		Viatura v = new Viatura ("gabriel","terere","oi ");
		String persistenceUnitName = "default";
        
        SimpleEntityManager simpleEntityManager = new SimpleEntityManager(persistenceUnitName);
         
        /**
         * THE SERVICE LAYER ENCAPSULATES EVERY BEGIN/COMMIT/ROLLBACK
         */
		//FuncionarioDaoImp d = new FuncionarioDaoImp();
		//d.save(f);
		
		//f = d.getFuncionario(1);
		
		//d.remove(f);
		//System.out.println(d.list().size());
		
		//for (Funcionario fu : d.list()) {
		//	System.out.println(fu.getNome());
		//}
        
        
        //ViaturaController daoViatura = new ViaturaController(simpleEntityManager);
        //daoViatura.save(v);

	}

}

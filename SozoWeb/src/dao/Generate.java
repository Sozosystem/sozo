package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.jpa.internal.EntityManagerFactoryImpl;

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
		FuncionarioDaoImp fc = new FuncionarioDaoImp();
		fc.save(f);
		
		//f = d.getFuncionario(1);
		
		//d.remove(f);
		//System.out.println(d.list().size());
		
		//for (Funcionario fu : d.list()) {
		//	System.out.println(fu.getNome());
		//}
        
		
	//Session s = HibernateUtil.getSessionFactory();
       ViaturaDaoImp daoViatura = new ViaturaDaoImp();
       daoViatura.save(v);
       
       List<Viatura> tuaMae = new ArrayList<Viatura>();
       tuaMae = daoViatura.list();
       
       for (Viatura v2 : tuaMae){
    	   System.out.println(v2.getPlaca());
       }

	}

}

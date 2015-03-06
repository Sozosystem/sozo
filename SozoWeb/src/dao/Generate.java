package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.jpa.internal.EntityManagerFactoryImpl;

import controller.FuncionarioController;
import model.Funcionario;

public class Generate {

	public static void main(String[] args) {

        
        EntityManagerHelper emh = new EntityManagerHelper();
        
        
        FuncionarioDAO dao = new FuncionarioDAO(emh.getEntityManager());
        

	}

}

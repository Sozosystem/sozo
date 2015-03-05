package services;

import java.awt.Cursor;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.hibernate.Session;

import com.mysql.jdbc.PreparedStatement;

import dao.SimpleEntityManager;
import model.Viatura;

public class ViaturaServices {

	
	
	public boolean validarViatura(Viatura v){
		boolean resposta = true;
		
		if(v.getDescricao().equals(null)){
			resposta = false;
		}
		if (v.getPlaca().equalsIgnoreCase("AAAAA")){
			resposta = false;
		}
		if(v.getTipo().startsWith("J")){
			resposta = false;
		}
		
		return resposta;
	}
	
	public List<Viatura> getViaturaFuncionario(Viatura v,SimpleEntityManager sem){
		List lista = new ArrayList<Viatura>();
		String sql = "SELECT V.id,f.nome,v.placa from viatura v,funcionario f where v.id = f.id_viatura"
				+ " and v.id = :ID";
		EntityManager et = sem.getEntityManager();
		lista = et.createQuery(sql).getResultList();
		return lista;
		
	}
}

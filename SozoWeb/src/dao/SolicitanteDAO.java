package dao;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import model.Solicitante;

public class SolicitanteDAO extends GenericDAO<Integer, Solicitante> {

	public SolicitanteDAO(EntityManager entityManager) {
		super(entityManager);
		// TODO Auto-generated constructor stub
	}
	
	public List<Solicitante> consultar(Solicitante s) throws Exception {
		String jpql=null;
		boolean temTelefone = false;
		
			jpql= "from Solicitante s where s.nome like :nome";
		if(!s.getTelefone().trim().equals("")){
			
			jpql+= " and s.telefone like :telefone";
			temTelefone= true;
		
		}
		
		TypedQuery<Solicitante> tqry = getEntityManager().createQuery(jpql, Solicitante.class);
		
		
		tqry.setParameter("nome","%"+ s.getNome()+"%");
		System.out.println(jpql);
		if(temTelefone)
			tqry.setParameter("telefone","%"+s.getTelefone()+"%");
		
		return tqry.getResultList();
		
	}

}

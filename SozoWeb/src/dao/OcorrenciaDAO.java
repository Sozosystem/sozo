package dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import filter.LoginFilter;
import model.Ocorrencia;


public class OcorrenciaDAO extends GenericDAO<Integer, Ocorrencia>{

	public OcorrenciaDAO(EntityManager entityManager) {
		super(entityManager);
	}

	
	public List<Ocorrencia> consultar(Ocorrencia o, Date dI, Date dF) throws Exception {
		String jpql=null;
		boolean temSituacaoOcorrencia = false;
		boolean temDI = false;
		boolean temDF = false;
		
			jpql= "from Ocorrencia o where o.funcionario.nome like :nome";
			
		if(o.getSituacaoOcorrencia()!= null){
			jpql+= " and o.situacaoOcorrencia like :situacaoOcorrencia";
			temSituacaoOcorrencia= true;
		
		}
		
		if(dI!= null  && dF!=null ){
			jpql+= " and o.dataCriacao BETWEEN :di and :df";
			temDI = true;
			temDF = true;
			
		}
		
		
		TypedQuery<Ocorrencia> tqry = getEntityManager().createQuery(jpql, Ocorrencia.class);
		tqry.setParameter("nome","%"+ o.getFuncionario().getNome()+ "%");
		if(temSituacaoOcorrencia)
			tqry.setParameter("situacaoOcorrencia", o.getSituacaoOcorrencia());
		if(temDI)
			tqry.setParameter("di", dI);
		if(temDF)
			tqry.setParameter("df", dF);
		
		return tqry.getResultList();
		
	}
	
	public List<Ocorrencia> consultaOcorrenciaAcom(Ocorrencia o, Date dI, Date dF){
		String jpql =null;
		boolean temNome = false;
		boolean temDI=false;
		boolean temDF = false;
		boolean temID= false;
		
			if(o.getFuncionario().getNome()==null){
				  jpql= "Select o from Ocorrencia o where "
							+ "o.situacaoOcorrencia IN ('AMBULANCIA_PROBLEMA ','ATENDIMENTO_ENCAMINHADO', 'AMBULANCIA_A_CAMINHO')";
			}
			else{
				jpql= "Select o from Ocorrencia o where "
						+ "o.situacaoOcorrencia IN ('AMBULANCIA_PROBLEMA ','ATENDIMENTO_ENCAMINHADO', 'AMBULANCIA_A_CAMINHO')";
				
				if(o.getFuncionario().getNome()!=""){
					jpql+= " and o.funcionario.nome like :nome";
					temNome = true;
				}
			}
	
		
			if(!(LoginFilter.funcionarioLogado.getTipoFuncionario().getNome().equals("Administrador"))){
				//System.out.println("a");
				jpql+=" and o.funcionario.id =:id";
				temID = true;
			
			}
	
			if(dI!= null  && dF!=null ){
			jpql+= " and o.dataCriacao BETWEEN :di and :df";
			temDI = true;
			temDF = true;
			}
		
		TypedQuery<Ocorrencia> tqry = getEntityManager().createQuery(jpql, Ocorrencia.class);	
		
		if(temID)
			tqry.setParameter("id", LoginFilter.funcionarioLogado.getId());
		if(temNome)
			tqry.setParameter("nome","%"+ o.getFuncionario().getNome()+ "%");
		if(temDI)
			tqry.setParameter("di", dI);
		if(temDF)
			tqry.setParameter("df", dF);
				
		return tqry.getResultList();		
	}
}

package dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;

import filter.LoginFilter;
import model.AuxGrafico;
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
		System.out.println(dI);
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
	
	public List<AuxGrafico> consultaOcorrenciaDiaFunc(){
		String jpql = null;
	
		/* SimpleDateFormat formatador = new SimpleDateFormat(
					"dd-MM-yyyy");
			String date = formatador.format(new Date());*/
		jpql= "Select o.funcionario.id as id, o.funcionario.nome as nome , "
				+ "COUNT(o.funcionario.id )as quantidade from Ocorrencia o "
				+ "where o.dataCriacao BETWEEN CURRENT_DATE() AND CURRENT_DATE()+1  and "
				+ "o.situacaoOcorrencia IN('FINALIZADA') GROUP BY o.funcionario";
		
		
		TypedQuery<Object[]> tqry = (TypedQuery<Object[]>) getEntityManager().createQuery(jpql, Object[].class);
		//tqry.setParameter("data", date);
		List<Object[]> resultList = tqry.getResultList(); 
		List<AuxGrafico> retorno = new ArrayList<AuxGrafico>();
		
		for (Object[] objects : resultList) {  
			int codigo = (Integer)objects[0];  
			String nome = (String)objects[1].toString();
			String quantidade = (String) objects[2].toString();
			retorno.add(new AuxGrafico(codigo, nome, quantidade));  
	
		}
		return retorno;
		
	}
	
	public List<AuxGrafico> consultaOcorrenciaDiaStatus(){
		String jpql = null;
		Date date = new Date();
		System.out.println(date);
		jpql= "Select o.id as id , DATE(o.dataCriacao) as nome, COUNT(o.dataCriacao)as quantidade"
				+ " from Ocorrencia o where o.dataCriacao BETWEEN CURRENT_DATE()-8 AND CURRENT_DATE()+1 "
				+ "and o.situacaoOcorrencia IN('FINALIZADA') GROUP BY DATE(o.dataCriacao)";
		
		TypedQuery<Object[]> tqry = (TypedQuery<Object[]>) getEntityManager().createQuery(jpql, Object[].class);
		//tqry.setParameter("data", date);
		
		List<Object[]> resultList = tqry.getResultList(); 
		List<AuxGrafico> retorno = new ArrayList<AuxGrafico>();
		//tqry.setParameter("data", 1);
		
		for (Object[] objects : resultList) {  
			int codigo = (Integer)objects[0];  
			String nome = (String)objects[1].toString();
			String quantidade = (String) objects[2].toString();
			retorno.add(new AuxGrafico(codigo, nome, quantidade));  
	
		}
		return retorno;
		
	}
	
	public List<AuxGrafico> consultaOcorrenciaCidade(){
		String jpql = null;
		Date date = new Date();
		System.out.println(date);
		jpql= "Select o.id as id, DATE(o.dataCriacao) as nome, o.endereco.cidade as cidade, "
				+ "COUNT(o.dataCriacao)as quantidade, COUNT(o.endereco.cidade) as qtdCidade "
				+ "from Ocorrencia o where o.dataCriacao BETWEEN CURRENT_DATE()-8 AND CURRENT_DATE()+1 "
				+ "and o.situacaoOcorrencia IN('FINALIZADA') GROUP BY o.endereco.cidade";
		
		TypedQuery<Object[]> tqry = (TypedQuery<Object[]>) getEntityManager().createQuery(jpql, Object[].class);
		//tqry.setParameter("data", date);
		
		List<Object[]> resultList = tqry.getResultList(); 
		List<AuxGrafico> retorno = new ArrayList<AuxGrafico>();
		//tqry.setParameter("data", 1);
		
		for (Object[] objects : resultList) {  
			int codigo = (Integer)objects[0];  
			String nome = (String)objects[1].toString();
			String cidade = (String)objects[2].toString();
			String quantidade = (String) objects[3].toString();
			String qtdCidade  = (String) objects[4].toString();
			retorno.add(new AuxGrafico(codigo, nome, cidade, quantidade, qtdCidade));  
	
		}
		return retorno;
	}
}

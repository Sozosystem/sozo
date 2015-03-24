package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

import util.Mensagem;
import dao.EntityManagerHelper;
import dao.OcorrenciaDAO;
import filter.LoginFilter;
import model.Funcionario;
import model.Ocorrencia;
import model.SituacaoOcorrencia;

@ManagedBean(name="ocorrencia")
@ViewScoped
public class OcorrenciaController extends BaseBeanController implements ControllerI {
	
	private Ocorrencia ocorrenciaSelecionada;
	private Ocorrencia ocorrenciaAlterada;
	private Ocorrencia ocorrencia;
	private List<Ocorrencia> listaOcorrencias;
	private OcorrenciaDAO dao;
	
	public OcorrenciaController() {
		super();
		
		EntityManagerHelper emh = new EntityManagerHelper();            
        dao = new OcorrenciaDAO(emh.getEntityManager());
        
		ocorrencia = new Ocorrencia();
		ocorrenciaSelecionada = new Ocorrencia();
		ocorrenciaPendentes();
		String idOcorrencia = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("ocorrencia");  
		if(idOcorrencia != null) {
			ocorrencia = dao.getById(Integer.parseInt(idOcorrencia));
			if(ocorrencia.getSituacaoOcorrencia() != SituacaoOcorrencia.PENDENTE && LoginFilter.funcionarioLogado.getId() != ocorrencia.getFuncionario().getId()) {
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("ocorrencias.xhtml");
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			ocorrencia.setFuncionario(LoginFilter.funcionarioLogado);
			ocorrencia.setSituacaoOcorrencia(SituacaoOcorrencia.EM_ANALISE);
			dao.update(ocorrencia);
			
		}
		//mostrarTodos();
	}
	
	public void ocorrenciaPendentes() {
		Ocorrencia o = new Ocorrencia();
		o.setSituacaoOcorrencia(SituacaoOcorrencia.PENDENTE);
		this.listaOcorrencias = dao.findByObject(o);
	}
	
	public String ocorrenciasPendentesJSON() throws JSONException {
		JSONArray ocorrencias = new JSONArray();
		
		for (Ocorrencia o : listaOcorrencias) {
			JSONObject ocorrencia = new JSONObject(o);
			ocorrencia.put("id", o.getId());
			if(o.getDataCriacao() != null) {
				SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");  
				String data = formatador.format(o.getDataCriacao());
				ocorrencia.put("data", data);
			}else {
				ocorrencia.put("data", "null");
			}
			
			ocorrencias.put(ocorrencia);
		}
		return ocorrencias.toString();
	}
	
	@Override
	public void cadastrar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remover() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void alterar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void consultar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mostrarTodos() {
		listaOcorrencias = dao.findAll();
	}

	@Override
	public void alterarSelecionado() {
		if(ocorrenciaSelecionada == null) {
			Mensagem.alerta(Mensagem.INFO, "Selecione uma ocorrência para tratar", null);
			return;
		}
		ocorrencia = ocorrenciaSelecionada;
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("ocorrencia.xhtml?ocorrencia=" + ocorrencia.getId());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void cancelarAlteracao() {
		// TODO Auto-generated method stub
		
	}

	public Ocorrencia getOcorrenciaSelecionada() {
		return ocorrenciaSelecionada;
	}

	public void setOcorrenciaSelecionada(Ocorrencia ocorrenciaSelecionada) {
		this.ocorrenciaSelecionada = ocorrenciaSelecionada;
	}

	public Ocorrencia getOcorrenciaAlterada() {
		return ocorrenciaAlterada;
	}

	public void setOcorrenciaAlterada(Ocorrencia ocorrenciaAlterada) {
		this.ocorrenciaAlterada = ocorrenciaAlterada;
	}

	public Ocorrencia getOcorrencia() {
		return ocorrencia;
	}

	public void setOcorrencia(Ocorrencia ocorrencia) {
		this.ocorrencia = ocorrencia;
	}

	public List<Ocorrencia> getListaOcorrencias() {
		return listaOcorrencias;
	}

	public void setListaOcorrencias(List<Ocorrencia> listaOcorrencias) {
		this.listaOcorrencias = listaOcorrencias;
	}

}

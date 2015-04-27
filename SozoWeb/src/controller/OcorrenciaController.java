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
import dao.ViaturaDAO;
import filter.LoginFilter;
import model.Funcionario;
import model.Ocorrencia;
import model.SituacaoOcorrencia;
import model.Viatura;

@ManagedBean(name="ocorrencia")
@ViewScoped
public class OcorrenciaController extends BaseBeanController implements ControllerI {
	
	private Ocorrencia ocorrenciaSelecionada;
	private Ocorrencia ocorrenciaAlterada;
	private Ocorrencia ocorrencia;
	private List<Ocorrencia> listaOcorrencias;
	private List<Viatura> listaViaturas;
	private OcorrenciaDAO dao;
	private ViaturaDAO daoViatura;
	private boolean foto = true;
	private Viatura viaturaSelecionada;
	
	public OcorrenciaController() {
		super();
		
		EntityManagerHelper emh = new EntityManagerHelper();            
        dao = new OcorrenciaDAO(emh.getEntityManager());
        daoViatura = new ViaturaDAO(emh.getEntityManager());
        
		ocorrencia = new Ocorrencia();
		ocorrenciaSelecionada = new Ocorrencia();
		ocorrenciaPendentes();
		String idOcorrencia = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("ocorrencia");  
		if(idOcorrencia != null) {
			ocorrencia = dao.getById(Integer.parseInt(idOcorrencia));
			viaturasDisponiveis();
			if(ocorrencia.getSituacaoOcorrencia() != SituacaoOcorrencia.PENDENTE && LoginFilter.funcionarioLogado.getId() != ocorrencia.getFuncionario().getId()) {
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("ocorrencias.xhtml");
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(ocorrencia.getFoto().contains(".mp4")) {
				this.foto = false;
				System.out.println("video");
			}else {
				this.foto = true;
				System.out.println("foto");
			}
			ocorrencia.setFuncionario(LoginFilter.funcionarioLogado);
			ocorrencia.setSituacaoOcorrencia(SituacaoOcorrencia.EM_ANALISE);
			dao.update(ocorrencia);
			
		}
		//mostrarTodos();
	}
	
	public void tratarOcorrencia() {
		if(viaturaSelecionada == null) {
			Mensagem.alerta(Mensagem.INFO, "Selecione ao menos uma viatura", null);
			return;
		}
		List<Viatura> viaturas = ocorrencia.getViaturas();
		viaturas.add(viaturaSelecionada);
		ocorrencia.setSituacaoOcorrencia(SituacaoOcorrencia.ATENDIMENTO_ENCAMINHADO);
		dao.save(ocorrencia);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("ocorrencias.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void cancelarOcorrencia() {
		ocorrencia.setSituacaoOcorrencia(SituacaoOcorrencia.CANCELADA);
		dao.save(ocorrencia);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("ocorrencias.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void ocorrenciaPendentes() {
		Ocorrencia o = new Ocorrencia();
		o.setSituacaoOcorrencia(SituacaoOcorrencia.PENDENTE);
		this.listaOcorrencias = dao.findByObject(o);
	}
	
	public void viaturasDisponiveis() {
		Viatura v = new Viatura();
		v.setDisponivel(true);
		this.listaViaturas = daoViatura.findByObject(v);
	}
	
	public String ocorrenciasPendentesJSON() throws JSONException {
		viaturasDisponiveis();
		
		JSONObject data = new JSONObject();
		JSONArray ocorrenciasArray = new JSONArray();
		JSONArray viaturasArray = new JSONArray();
		for (Ocorrencia o : listaOcorrencias) {
			JSONObject ocorrencia = new JSONObject(o);
			ocorrencia.put("id", o.getId());
			if(o.getDataCriacao() != null) {
				SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");  
				String date = formatador.format(o.getDataCriacao());
				ocorrencia.put("data", date);
			}else {
				ocorrencia.put("data", "null");
			}
			
			ocorrenciasArray.put(ocorrencia);
		}
		for(Viatura v : listaViaturas) {
			JSONObject viatura = new JSONObject(v);
			viaturasArray.put(viatura);
		}
		data.put("ocorrencias", ocorrenciasArray);
		data.put("viaturas", viaturasArray);
		return data.toString();
	}
	
	public String viaturasDisponiveisJSON() throws JSONException {
		viaturasDisponiveis();
		JSONObject data = new JSONObject();
		JSONArray viaturasArray = new JSONArray();
		
		for(Viatura v : listaViaturas) {
			JSONObject viatura = new JSONObject(v);
			viaturasArray.put(viatura);
		}
		data.put("viaturas", viaturasArray);
		return data.toString();
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

	public boolean getFoto() {
		return foto;
	}

	public void setFoto(boolean foto) {
		this.foto = foto;
	}

	public List<Viatura> getListaViaturas() {
		return listaViaturas;
	}

	public void setListaViaturas(List<Viatura> listaViaturas) {
		this.listaViaturas = listaViaturas;
	}

	public Viatura getViaturaSelecionada() {
		return viaturaSelecionada;
	}

	public void setViaturaSelecionada(Viatura viaturaSelecionada) {
		this.viaturaSelecionada = viaturaSelecionada;
	}

}

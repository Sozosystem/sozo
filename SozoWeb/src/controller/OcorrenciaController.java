package controller;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import json.Http;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import util.Mensagem;
import dao.EntityManagerHelper;
import dao.OcorrenciaDAO;
import dao.ViaturaDAO;
import filter.LoginFilter;
import model.Endereco;
import model.Ocorrencia;
import model.SituacaoOcorrencia;
import model.Viatura;


@ManagedBean(name = "ocorrencia")
@ViewScoped
public class OcorrenciaController extends BaseBeanController implements
		ControllerI {

	private Ocorrencia ocorrenciaSelecionada;
	private Ocorrencia ocorrenciaAlterada;
	private Ocorrencia ocorrencia;
	private List<Ocorrencia> listaOcorrencias;
	private List<Ocorrencia> listaTodasOcorrencias;
	private List<Ocorrencia> listaOcorrenciasAcomp;
	private List<Viatura> listaViaturas;
	private OcorrenciaDAO dao;
	private ViaturaDAO daoViatura;
	private boolean foto = true;
	private Viatura viaturaSelecionada;
	private Date dataInicial;
	private Date dataFinal;
	private SituacaoOcorrencia[] situacoes = SituacaoOcorrencia.values();
	private String placa;
	private String tempo;
	private Endereco endereco;
 
	public OcorrenciaController() throws JSONException {
		super();
		
		EntityManagerHelper emh = new EntityManagerHelper();
		dao = new OcorrenciaDAO(emh.getEntityManager());
		daoViatura = new ViaturaDAO(emh.getEntityManager());
		ocorrencia = new Ocorrencia();
		ocorrenciaSelecionada = new Ocorrencia();
		ocorrenciaPendentes();
		todasOcorrencias();
		String idOcorrencia = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap()
				.get("ocorrencia");
		if (idOcorrencia != null) {
			ocorrencia = dao.getById(Integer.parseInt(idOcorrencia));
			viaturasDisponiveis();
			if (ocorrencia.getSituacaoOcorrencia() != SituacaoOcorrencia.PENDENTE
					&& LoginFilter.funcionarioLogado.getId() != ocorrencia
							.getFuncionario().getId() && 
							!LoginFilter.funcionarioLogado.getTipoFuncionario().getNome().equals("Administrador")) {
				try {
					
					FacesContext.getCurrentInstance().getExternalContext()
							.redirect("ocorrencias.xhtml");

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (ocorrencia.getFoto() != null
					&& ocorrencia.getFoto().contains(".mp4")) {
				this.foto = false;
			} else {
				this.foto = true;
			}

			if (ocorrencia.getSituacaoOcorrencia() == SituacaoOcorrencia.PENDENTE) {
				ocorrencia.setSituacaoOcorrencia(SituacaoOcorrencia.EM_ANALISE);
				chamadaHttp();
			}
			
			DateTime dtToday = new DateTime();
			List<String> observ = ocorrencia.getObservacao();
			Collections.reverse(observ);
			observ.add(dtToday.toString() + " - "
					+ LoginFilter.funcionarioLogado.toString() + " - "
					+ ocorrencia.getSituacaoOcorrencia());
			Collections.reverse(observ);
			if(LoginFilter.funcionarioLogado.getId() == ocorrencia
					.getFuncionario().getId() || ocorrencia
					.getFuncionario().getId()==null ){
				ocorrencia.setFuncionario(LoginFilter.funcionarioLogado);
			}
			dao.update(ocorrencia);
		}
		// mostrarTodos();
	}

	public void tratarOcorrencia() {
		if (viaturaSelecionada == null) {
			Mensagem.alerta(Mensagem.INFO, "Selecione ao menos uma viatura",
					null);
			return;
		}
		Viatura v = daoViatura.getById(viaturaSelecionada.getId());
		// System.out.println(v.getDisponivel());
		if (!v.getDisponivel()) {
			Mensagem.alerta(Mensagem.INFO,
					"A viatura selecionada não está mais disponível", null);
			return;
		}
		List<Viatura> viaturas = ocorrencia.getViaturas();
		viaturas.add(viaturaSelecionada);
		ocorrencia
				.setSituacaoOcorrencia(SituacaoOcorrencia.ATENDIMENTO_ENCAMINHADO);
		dao.save(ocorrencia);
		viaturaSelecionada.setDisponivel(false);
		daoViatura.save(viaturaSelecionada);
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("ocorrencias.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void cancelarOcorrencia() {
		ocorrencia.setSituacaoOcorrencia(SituacaoOcorrencia.CANCELADA);
		dao.save(ocorrencia);
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("ocorrencias.xhtml");
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

	public void todasOcorrencias() {
		this.listaTodasOcorrencias = dao.findAll();
		this.listaOcorrenciasAcomp = dao.consultaOcorrenciaAcom(ocorrencia,
				dataFinal, dataFinal);
		consultarAcomp();
		
	}

	public String ocorrenciasPendentesJSON() throws JSONException {
		viaturasDisponiveis();

		JSONObject data = new JSONObject();
		JSONArray ocorrenciasArray = new JSONArray();
		JSONArray viaturasArray = new JSONArray();
		for (Ocorrencia o : listaOcorrencias) {
			JSONObject ocorrencia = new JSONObject(o);
			ocorrencia.put("id", o.getId());
			if (o.getDataCriacao() != null) {
				SimpleDateFormat formatador = new SimpleDateFormat(
						"dd/MM/yyyy hh:mm:ss");
				String date = formatador.format(o.getDataCriacao());
				ocorrencia.put("data", date);
			} else {
				ocorrencia.put("data", "null");
			}

			ocorrenciasArray.put(ocorrencia);
		}
		for (Viatura v : listaViaturas) {
			JSONObject viatura = new JSONObject(v);
			viatura.put("id", v.getId());
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

		for (Viatura v : listaViaturas) {
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

	public void consultar() {
		try {
			List<Ocorrencia> aux = null;
			if (placa != "" && placa != null) {
				aux = dao.consultar(ocorrencia, dataInicial, dataFinal);
				listaTodasOcorrencias.clear();
				for (Ocorrencia o : aux) {
					if (o.getViaturas().toString().contains(placa.trim())) {
						listaTodasOcorrencias.add(o);
					}
				}
			} else {
				//System.out.println(ocorrencia.getFuncionario().getNome());
				if (ocorrencia.getFuncionario().getNome() != ""
						|| dataInicial != null || dataFinal != null) {
					// listaTodasOcorrencias = dao.findAll();
					listaTodasOcorrencias = dao.consultar(ocorrencia,
							dataInicial, dataFinal);
				} else {
					// listaTodasOcorrencias = dao.consultar(ocorrencia,
					// dataInicial,
					// dataFinal);
					listaTodasOcorrencias = dao.findAll();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block

		}
		dataInicial = null;
		dataFinal = null;
		placa = "";
		ocorrencia = new Ocorrencia();

	}

	public void consultarAcomp() {
		List<Ocorrencia> auxA = null;
		listaOcorrenciasAcomp.clear();
		// System.out.println(LoginFilter.funcionarioLogado);
		auxA = dao.consultaOcorrenciaAcom(ocorrencia, dataInicial, dataFinal);

		DateTime dtToday = new DateTime();
		for (Ocorrencia o : auxA) {
			DateTime dtOther = new DateTime(o.getDataCriacao());
			Duration dur = new Duration(dtOther, dtToday);
			long dtM = dur.getMillis();

			/*
			 * int dias = 0; int horas=0; int min = 0; int seg = 0; dias = (int)
			 * dtM /(24*60*60*1000); horas= ((int) dtM %
			 * (24*60*60*1000))/(60*60*1000); min = (((int) dtM %
			 * (24*60*60*1000))%(60*60*1000))/(60*1000); seg = ((((int) dtM %
			 * (24*60*60*1000))%(60*60*1000))%(60*1000))/1000;
			 * System.out.println(dias); System.out.println(horas);
			 * System.out.println(min); System.out.println(seg);
			 */

			long dtD = dur.getStandardDays();
			long difH = (dtM - (dtD * 24 * 60 * 60 * 1000)) / (1000 * 60 * 60);
			long difMin = (dur.getStandardMinutes() - ((difH * 60) + (dtD * 24 * 60)));
			long difSeg = (dur.getStandardSeconds() - ((difMin * 60)
					+ (dtD * 24 * 60 * 60) + (difH * 60 * 60)));
			o.setTempo(dtD + "d" + " " + difH + "H:" + difMin + "min" + difSeg
					+ "s");
			listaOcorrenciasAcomp.add(o);
		}
		dataInicial = null;
		dataFinal = null;
		ocorrencia = new Ocorrencia();
	}

	public void voltar() {

		if (ocorrencia.getSituacaoOcorrencia() == SituacaoOcorrencia.EM_ANALISE) {
			ocorrencia.setSituacaoOcorrencia(SituacaoOcorrencia.PENDENTE);
			dao.update(ocorrencia);
		}
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("ocorrencias.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void voltarAcomp() throws JSONException {
		//DateTime dtToday = new DateTime();

		if (ocorrencia.getSituacaoOcorrencia() != SituacaoOcorrencia.EM_ANALISE) {
			/*List<String> observ = ocorrencia.getObservacao();
			Collections.reverse(observ);
			observ.add(dtToday.toString() + " - "
					+ LoginFilter.funcionarioLogado.toString() + " - "
					+ ocorrencia.getSituacaoOcorrencia());
			Collections.reverse(observ);
			// observ = new ArrayList<String>();
			ocorrencia.setObservacao(observ);*/
			
			dao.update(ocorrencia);

		}

		try {
			if(LoginFilter.funcionarioLogado.getTipoFuncionario().equals("Administrador")){
				FacesContext.getCurrentInstance().getExternalContext()
				.redirect("acompanhamento.xhtml");
			}
			else{
				FacesContext.getCurrentInstance().getExternalContext()
				.redirect("acompanhamentoFunc.xhtml");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void mostrarTodos() {
		listaOcorrencias = dao.findAll();
		listaTodasOcorrencias = dao.findAll();
		consultarAcomp();
		
	}

	@Override
	public void alterarSelecionado() {
		if (ocorrenciaSelecionada == null) {
			Mensagem.alerta(Mensagem.INFO,
					"Selecione uma ocorr�ncia para tratar", null);
			return;
		}
		ocorrencia = ocorrenciaSelecionada;
		try {
			FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.redirect(
							"ocorrencia.xhtml?ocorrencia=" + ocorrencia.getId());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void detalheAcompanhamento() {
		/*
		 * if (codigo == null) { Mensagem.alerta(Mensagem.INFO,
		 * "Selecione uma ocorr�ncia para tratar", null); return; }
		 * if(Integer.parseInt(codigo) != 0){ System.out.println(codigo);
		 * ocorrencia = dao.getById(Integer.parseInt(codigo)); } else{
		 * ocorrencia = ocorrenciaSelecionada; }
		 */
			
		if (ocorrenciaSelecionada == null) {
			Mensagem.alerta(Mensagem.INFO,
					"Selecione uma ocorr�ncia para tratar", null);
			return;
		}
		ocorrencia = ocorrenciaSelecionada;
		try {
			FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.redirect(
							"acompanhamento-detalhe.xhtml?ocorrencia="
									+ ocorrencia.getId());
			
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

	public List<Ocorrencia> getListaTodasOcorrencias() {
		return listaTodasOcorrencias;
	}

	public void setListaTodasOcorrencias(List<Ocorrencia> listaTodasOcorrencias) {
		this.listaTodasOcorrencias = listaTodasOcorrencias;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public SituacaoOcorrencia[] getSituacoes() {
		return situacoes;
	}

	public void setSituacoes(SituacaoOcorrencia[] situacoes) {
		this.situacoes = situacoes;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public List<Ocorrencia> getListaOcorrenciasAcomp() {
		return listaOcorrenciasAcomp;
	}

	public void setListaOcorrenciasAcomp(List<Ocorrencia> listaOcorrenciasAcomp) {
		this.listaOcorrenciasAcomp = listaOcorrenciasAcomp;
	}

	public String getTempo() {
		return tempo;
	}

	public void setTempo(String tempo) {
		this.tempo = tempo;
	}
	

	public void chamadaHttp() throws JSONException  {
       
    	String url ="http://maps.googleapis.com/maps/api/geocode/json?latlng="
        		+ ocorrencia.getLatitude()+"," + ocorrencia.getLongitude() + "&sensor=false";
    	Http http = new Http();
    	System.out.println(url);
        String retornoJson = null;
			try {
				retornoJson = http.chamaUrl(url);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(retornoJson);
			JSONObject objList = new JSONObject(retornoJson);
			JSONArray objLists = objList.getJSONArray("results");
			JSONObject objEnd = objLists.getJSONObject(0);
 			JSONArray adressList = objEnd.getJSONArray("address_components");
 			JSONObject logJson = adressList.getJSONObject(1);
 			JSONObject bairroJson = adressList.getJSONObject(2);
			JSONObject cidadeJson = adressList.getJSONObject(3);
			JSONObject estadoJson = adressList.getJSONObject(5);
			System.out.println(adressList.toString());
			System.out.println(cidadeJson.getString("long_name"));
			endereco = new Endereco();
			endereco.setCidade(cidadeJson.getString("long_name"));
			endereco.setBairro(bairroJson.getString("long_name"));
			endereco.setEstado(estadoJson.getString("long_name"));
			endereco.setLogradouro(logJson.getString("long_name"));
			ocorrencia.setEndereco(endereco);
			
				
    }
    
   public void preProcessPDF(Object document) throws IOException, DocumentException {
	   
	   
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);

        SimpleDateFormat formatador = new SimpleDateFormat(
				"dd/MM/yyyy hh:mm:ss");
		String date = formatador.format(new Date());
		
		
		Font f = new Font(Font.BOLD, 20, Font.BOLD);
		Font f1 = new Font(Font.BOLD, 10, Font.NORMAL);
		
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String logo = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "images" + File.separator + "sozo-logo-tiny.png";
        
        Image img = Image.getInstance(logo);
        img.setAlignment(Element.ALIGN_CENTER);       
       
        Paragraph p1 = new Paragraph("Relatórios de Ocorrências",f);
        Paragraph p2 = new Paragraph("Realizado no dia: "+ date,f1);
   
        p1.setAlignment(Element.ALIGN_CENTER);
        p2.setAlignment(Element.ALIGN_CENTER);
        pdf.add(p1);
        pdf.add(p2);
        pdf.add(img);

   }	
     
}
    


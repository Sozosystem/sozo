package controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import util.Mensagem;
import model.Situacao;
import model.Solicitante;
import dao.EntityManagerHelper;
import dao.SolicitanteDAO;

@ManagedBean(name="solicitante")
@ViewScoped
public class SolicitanteController {
	
	private SolicitanteDAO dao;
	private Solicitante solicitante;
	private Solicitante solicitanteSelecionado;
	private Solicitante solicitanteConsulta;
	private List<Solicitante> listaSolicitantes;
	private Situacao[] situacoes = Situacao.values();
	private boolean podeAlterar;
	
	
	public SolicitanteController(){
		EntityManagerHelper emh = new EntityManagerHelper();            
		dao = new SolicitanteDAO(emh.getEntityManager());
		solicitante = new Solicitante();
		solicitanteConsulta = new Solicitante();
		mostrarTodosSolicitantes();
	}
	
	public void consultarSolicitante() {
		listaSolicitantes = dao.findByObject(solicitanteConsulta);
	}
	
	public void alterarSolicitante() {
		dao.update(solicitante);
		mostrarTodosSolicitantes();
		solicitante = new Solicitante();
		podeAlterar = false;
		Mensagem.alerta(Mensagem.INFO, "Situa��o do solicitante alterada com sucesso", null);
	}
	
	public void mostrarTodosSolicitantes() {
		this.listaSolicitantes = dao.findAll();
	}
	
	public void alterarSolicitanteSelecionado() {
		if(solicitanteSelecionado == null) {
			Mensagem.alerta(Mensagem.INFO, "Selecione um solicitante para alterar", null);
			return;
		}
		solicitante = solicitanteSelecionado;
		podeAlterar = true;
	}

	public Solicitante getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(Solicitante solicitante) {
		this.solicitante = solicitante;
	}

	public Solicitante getSolicitanteSelecionado() {
		return solicitanteSelecionado;
	}

	public Solicitante getSolicitanteConsulta() {
		return solicitanteConsulta;
	}

	public List<Solicitante> getListaSolicitantes() {
		return listaSolicitantes;
	}

	public void setSolicitanteSelecionado(Solicitante solicitanteSelecionado) {
		this.solicitanteSelecionado = solicitanteSelecionado;
	}

	public void setSolicitanteConsulta(Solicitante solicitanteConsulta) {
		this.solicitanteConsulta = solicitanteConsulta;
	}

	public void setListaSolicitantes(List<Solicitante> listaSolicitantes) {
		this.listaSolicitantes = listaSolicitantes;
	}

	public Situacao[] getSituacoes() {
		return situacoes;
	}

	public void setSituacoes(Situacao[] situacoes) {
		this.situacoes = situacoes;
	}

	public boolean getPodeAlterar() {
		return podeAlterar;
	}

	public void setPodeAlterar(boolean podeAlterar) {
		this.podeAlterar = podeAlterar;
	}


}

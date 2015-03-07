package controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.swing.text.html.ListView;

import model.Funcionario;
import model.Situacao;
import model.TipoFunc;
import dao.EntityManagerHelper;
import dao.FuncionarioDAO;
import dao.TipoFuncDAO;


@ManagedBean(name="funcionario")
@SessionScoped
public class FuncionarioController {
	private Funcionario funcionarioLogado;
	private Funcionario funcionario;
	private Funcionario funcionarioSelecionado;
	private FuncionarioDAO dao;
	private Situacao[] situacoes = Situacao.values();
	private List<TipoFunc> tipoFunc;
	private TipoFuncDAO daoTipoFunc;
	
	public void init(){
		listarTiposFunc();		
	}
	
	public List<TipoFunc> listarTiposFunc(){
		return daoTipoFunc.findAll();
	}

	
	public List<TipoFunc> getTipoFunc() {
		return tipoFunc;
	}


	public void setTipoFunc(List<TipoFunc> tipoFunc) {
		this.tipoFunc = tipoFunc;
	}


	public FuncionarioController() {
		EntityManagerHelper emh = new EntityManagerHelper();            
        dao = new FuncionarioDAO(emh.getEntityManager());
        
        funcionario = new Funcionario();
	}

	
	public List<Funcionario> getListaFuncionarios() {
		return dao.findAll();
	}
	
	public Situacao[] getSituacoes() {
		return situacoes;
	}


	public void setSituacoes(Situacao[] situacoes) {
		this.situacoes = situacoes;
	}


	public void setFuncionarioLogado(Funcionario funcionarioLogado) {
		this.funcionarioLogado = funcionarioLogado;
	}


	public void salvarFuncionario() {
		System.out.println(funcionario);
		Funcionario f = new Funcionario();
		f.setNome(funcionario.getNome());
		f.setUsuario(funcionario.getUsuario());
		f.setSenha(funcionario.getSenha());
		f.setDataUltimaAtualizacao(Calendar.getInstance());
		f.setSituacao(funcionario.getSituacao());
		dao.save(f);
	}
	
	public String alterarFuncionario() {
		dao.update(funcionario);
		return "index.xhtml";
	}
	
	public String removerFuncionario() {
		dao.delete(funcionarioSelecionado);
		//funcionario = new Funcionario();
		return "index.xhtml";
	}
	
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
	public Funcionario getFuncionarioLogado() {
		return this.funcionarioLogado;
	}
	
	public void logar(ActionEvent actionEvent) throws Exception {
        Funcionario f = new Funcionario();
        f.setUsuario(funcionario.getUsuario());
        f.setSenha(funcionario.getSenha());
        try {
        	this.funcionarioLogado = dao.verificarLogin(f);
        	System.out.println("Funcionário logado com sucesso");
        	FacesContext.getCurrentInstance().getExternalContext().redirect("restrito/index.xhtml");
        	funcionario = new Funcionario();
        }catch(Exception e) {
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um problema", e.getMessage()));
        }
    }
	public void deslogar() {
		System.out.println("Usuário deslogado");
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		//this.logado = false;
		//this.cliente = null;
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info!", "Logoff efetuado com sucesso"));
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("login-funcionario.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Funcionario getFuncionarioSelecionado() {
        return funcionarioSelecionado;
    }
 
    public void setFuncionarioSelecionado(Funcionario f) {
        this.funcionarioSelecionado = f;
    }
    
 
}

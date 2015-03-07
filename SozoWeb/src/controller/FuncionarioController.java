package controller;

import java.io.IOException;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import model.Funcionario;
import dao.EntityManagerHelper;
import dao.FuncionarioDAO;

@ManagedBean(name="funcionario")
@SessionScoped
public class FuncionarioController {
	private Funcionario funcionarioLogado;
	private Funcionario funcionario;
	private Funcionario funcionarioSelecionado;
	private FuncionarioDAO dao;

	
	public FuncionarioController() {
		EntityManagerHelper emh = new EntityManagerHelper();            
        dao = new FuncionarioDAO(emh.getEntityManager());
        
        funcionario = new Funcionario();
	}

	
	public List<Funcionario> getListaFuncionarios() {
		return dao.findAll();
	}
	
	public void salvarFuncionario() {
		System.out.println(funcionario);
		Funcionario f = new Funcionario();
		f.setNome(funcionario.getNome());
		f.setUsuario(funcionario.getUsuario());
		f.setSenha(funcionario.getSenha());
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

package controller;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import util.Mensagem;
import dao.EntityManagerHelper;
import dao.FuncionarioDAO;
import model.Funcionario;

@SessionScoped
@ManagedBean(name="login")
public class LoginController {
	private Funcionario funcionario;
	private Funcionario funcionarioLogado;
	private FuncionarioDAO funcionarioDAO;
	
	public LoginController() {
		funcionario = new Funcionario();
		EntityManagerHelper emh = new EntityManagerHelper();  
		funcionarioDAO = new FuncionarioDAO(emh.getEntityManager());
	}
	
	public void logar(ActionEvent actionEvent) throws Exception {
        Funcionario f = new Funcionario();
        f.setUsuario(funcionario.getUsuario());
        f.setSenha(funcionario.getSenha());
        try {
        	this.funcionarioLogado = funcionarioDAO.verificarLogin(f);
        	FacesContext.getCurrentInstance().getExternalContext().redirect("restrito/pagina/index.xhtml");
        	funcionario = new Funcionario();
        }catch(Exception e) {
        	Mensagem.alerta(Mensagem.ERRO, "Ocorreu um problema", e.getMessage());
        }
    }
	
	public void deslogar() {
		System.out.println("Usuário deslogado");
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		this.funcionarioLogado = null;
		
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("login-funcionario.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public Funcionario getFuncionarioLogado() {
		return funcionarioLogado;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public void setFuncionarioLogado(Funcionario funcionarioLogado) {
		this.funcionarioLogado = funcionarioLogado;
	}


}

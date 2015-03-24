package controller;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import util.Mensagem;
import dao.EntityManagerHelper;
import dao.FuncionarioDAO;
import dao.TipoFuncionarioDAO;
import model.Funcionario;
import model.Situacao;
import model.TipoFuncionario;

@SessionScoped
@ManagedBean(name="login")
public class LoginController {
	private Funcionario funcionario;
	private Funcionario funcionarioLogado;
	private FuncionarioDAO funcionarioDAO;
	private TipoFuncionarioDAO tipoFuncionarioDAO;
	private boolean primeiraVez = false;
	TipoFuncionario tipoAdministrador;
	
	public LoginController() {
		funcionario = new Funcionario();
		EntityManagerHelper emh = new EntityManagerHelper();  
		funcionarioDAO = new FuncionarioDAO(emh.getEntityManager());
		tipoFuncionarioDAO = new TipoFuncionarioDAO(emh.getEntityManager());
		
		if(funcionarioDAO.findAll().size() == 0) {
			setPrimeiraVez(true);
			tipoAdministrador = new TipoFuncionario();
			tipoAdministrador.setNome("Administrador");
			tipoAdministrador.setDescricao("Responsável pela administraçãp geral do sistema");
			tipoAdministrador.setSituacao(Situacao.ATIVO);
			tipoFuncionarioDAO.save(tipoAdministrador);
		}
		
	}
	
	public void salvarFuncionario() {
		Funcionario f = new Funcionario();
		f.setNome(funcionario.getNome());
		f.setUsuario(funcionario.getUsuario());
		f.setSenha(funcionario.getSenha());
		f.setTipoFuncionario(tipoAdministrador);
		f.setSituacao(Situacao.ATIVO);
		
		funcionarioDAO.save(f);
		funcionario = new Funcionario();
		primeiraVez = false;
		Mensagem.alerta(Mensagem.INFO, "Funcionário Administrador adicionado com sucesso", null);
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
        	Mensagem.alerta(Mensagem.ERRO, e.getMessage(), null);
        }
    }
	
	public void deslogar() {
		System.out.println("Usuário deslogado");
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		this.funcionarioLogado = null;
		
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/SozoWeb/login-funcionario.xhtml");
		} catch (IOException e) {
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

	public boolean getPrimeiraVez() {
		return primeiraVez;
	}

	public void setPrimeiraVez(boolean primeiraVez) {
		this.primeiraVez = primeiraVez;
	}


}

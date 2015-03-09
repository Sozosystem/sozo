package controller;


import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import util.Mensagem;
import model.Funcionario;
import dao.EntityManagerHelper;
import dao.FuncionarioDAO;

@ManagedBean(name="funcionario")
@RequestScoped
public class FuncionarioController {
	private Funcionario funcionario;
	private Funcionario funcionarioSelecionado;
	private Funcionario funcionarioConsulta;
	private FuncionarioDAO dao;
	private List<Funcionario> listaFuncionarios;
	
	public FuncionarioController() {
		EntityManagerHelper emh = new EntityManagerHelper();            
        dao = new FuncionarioDAO(emh.getEntityManager());
        
        funcionario = new Funcionario();
        funcionarioConsulta = new Funcionario();
        mostrarTodosFuncionarios();
	}

	
	public List<Funcionario> getListaFuncionarios() {
		return listaFuncionarios;
	}
	
	public void salvarFuncionario() {
		System.out.println(funcionario);
		Funcionario f = new Funcionario();
		f.setNome(funcionario.getNome());
		f.setUsuario(funcionario.getUsuario());
		f.setSenha(funcionario.getSenha());
		dao.save(f);
		mostrarTodosFuncionarios();
		funcionario = new Funcionario();
		Mensagem.alerta(Mensagem.INFO, "titulo", "Funcionário adicionado com sucesso");
		FacesContext.getCurrentInstance().addMessage("teste", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um problema", "Funcionário adicionado com sucesso"));
	}
	
	public void alterarFuncionario() {
		System.out.println(funcionario.getId() + " " + funcionario.getNome());
		dao.update(funcionario);
		mostrarTodosFuncionarios();
		funcionario = new Funcionario();
	}
	
	public void removerFuncionario() {
		dao.delete(funcionarioSelecionado);
		mostrarTodosFuncionarios();
	}
	
	public void consultarFuncionario() {
		listaFuncionarios = dao.findByObject(funcionarioConsulta);
	}
	
	public void funcionarioAlterarSelecionado() {
		if(funcionarioSelecionado == null) return;
		funcionario = funcionarioSelecionado;
	}
	
	public void mostrarTodosFuncionarios() {
		listaFuncionarios = dao.findAll();
	}
	
	public Funcionario getFuncionario() {
		return funcionario;
	}
	
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
	public Funcionario getFuncionarioSelecionado() {
        return funcionarioSelecionado;
    }
 
    public void setFuncionarioSelecionado(Funcionario f) {
        this.funcionarioSelecionado = f;
    }


	public Funcionario getFuncionarioConsulta() {
		return funcionarioConsulta;
	}


	public void setFuncionarioConsulta(Funcionario funcionarioConsulta) {
		this.funcionarioConsulta = funcionarioConsulta;
	}
    
 
}

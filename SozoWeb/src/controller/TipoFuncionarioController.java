package controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import util.Mensagem;
import model.TipoFuncionario;
import dao.EntityManagerHelper;
import dao.TipoFuncionarioDAO;

@ManagedBean(name="tipoFuncionario")
@ViewScoped
public class TipoFuncionarioController {
	
	private TipoFuncionarioDAO dao;
	private TipoFuncionario tipoFunc;
	private TipoFuncionario tipoFuncSelecionado;
	private List<TipoFuncionario> listaTiposFunc;
	private boolean podeAlterar;
	
	public TipoFuncionarioController() {
		EntityManagerHelper emh = new EntityManagerHelper();            
        dao = new TipoFuncionarioDAO(emh.getEntityManager());
        tipoFunc = new TipoFuncionario();
        mostrarTodosTipos();
	}
	
	public List<TipoFuncionario> getTipos() {
		return dao.findAll();
	}
	
	public void salvarTipo() {
		TipoFuncionario tipoConsulta = new TipoFuncionario();
		tipoConsulta.setNome(tipoFunc.getNome());
		if(dao.findByObject(tipoConsulta).size() > 0) {
			Mensagem.alerta(Mensagem.ERRO, "Já existe um tipo de funcionário com este nome", null);
			return;
		}
		TipoFuncionario tipo = new TipoFuncionario();
		tipo.setNome(tipoFunc.getNome());
		tipo.setDescricao(tipoFunc.getDescricao());
		dao.save(tipo);
		mostrarTodosTipos();
		tipoFunc = new TipoFuncionario();
		Mensagem.alerta(Mensagem.INFO, "Tipo de funcionário cadastrado com sucesso", null);
	}
	
	public void mostrarTodosTipos() {
		this.setListaTiposFunc(dao.findAll());
	}
	
	public void alterarTipo() {
		dao.update(tipoFunc);
		mostrarTodosTipos();
		tipoFunc = new TipoFuncionario();
		Mensagem.alerta(Mensagem.INFO, "Tipo de funcionário alterado com sucesso", null);
		podeAlterar = false;
	}
	
	public void removerTipo() {
		if(tipoFuncSelecionado == null) {
			Mensagem.alerta(Mensagem.INFO, "Selecione um Tipo de Funcionário para remover", null);
			return;
		}
		dao.delete(tipoFuncSelecionado);
		mostrarTodosTipos();
		Mensagem.alerta(Mensagem.INFO, "Tipo de funcionário removido com sucesso", null);
	}
	
	public void alterarTipoFuncSelecionado() {
		if(tipoFuncSelecionado == null) {
			Mensagem.alerta(Mensagem.INFO, "Selecione um Tipo de Funcionário para alterar", null);
			return;
		}
		tipoFunc = tipoFuncSelecionado;
		podeAlterar = true;
	}
	
	public void cancelarAlteracao() {
		tipoFuncSelecionado = null;
		tipoFunc = new TipoFuncionario();
		podeAlterar = false;
		RequestContext.getCurrentInstance().reset("form2");
	}
	
	public TipoFuncionario getTipoFunc() {
		return tipoFunc;
	}

	public void setTipoFunc(TipoFuncionario tipo) {
		this.tipoFunc = tipo;
	}
	
	public TipoFuncionario getTipoFuncSelecionado() {
        return tipoFuncSelecionado;
    }
 
    public void setTipoFuncSelecionado(TipoFuncionario tipo) {
        this.tipoFuncSelecionado = tipo;
    }

	
    public List<TipoFuncionario> getListaTiposFunc() {
		return listaTiposFunc;
	}
    

	public void setListaTiposFunc(List<TipoFuncionario> listaTiposFunc) {
		this.listaTiposFunc = listaTiposFunc;
	}

	public boolean getPodeAlterar() {
		return podeAlterar;
	}

	public void setPodeAlterar(boolean podeAlterar) {
		this.podeAlterar = podeAlterar;
	}

}

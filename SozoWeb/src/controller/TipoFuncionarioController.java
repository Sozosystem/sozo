package controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import model.TipoFuncionario;
import dao.EntityManagerHelper;
import dao.TipoFuncionarioDAO;

@ManagedBean(name="tipoFuncionario")
@RequestScoped
public class TipoFuncionarioController {
	
	private TipoFuncionarioDAO dao;
	private TipoFuncionario tipoFunc;
	private TipoFuncionario tipoFuncSelecionado;
	private List<TipoFuncionario> listaTiposFunc;
	
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
		TipoFuncionario tipo = new TipoFuncionario();
		tipo.setNome(tipoFunc.getNome());
		tipo.setDescricao(tipoFunc.getDescricao());
		dao.save(tipo);
		mostrarTodosTipos();
		tipoFunc = new TipoFuncionario();
	}
	
	public void mostrarTodosTipos() {
		this.setListaTiposFunc(dao.findAll());
	}
	
	public void alterarTipo() {
		dao.update(tipoFunc);
		mostrarTodosTipos();
		tipoFunc = new TipoFuncionario();
	}
	
	public void removerTipo() {
		dao.delete(tipoFuncSelecionado);
		mostrarTodosTipos();
	}
	
	public void alterarTipoFuncSelecionado() {
		if(tipoFunc == null) return;
		tipoFunc = tipoFuncSelecionado;
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

}

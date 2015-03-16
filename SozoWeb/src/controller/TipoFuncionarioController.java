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
		List<TipoFuncionario> tipos = dao.findByObject(tipoConsulta);
		if(tipos.size() > 0) {
			for (TipoFuncionario t : tipos) {
				if(t.getNome().equals(tipoFunc.getNome())) {
					Mensagem.alerta(Mensagem.ERRO, "J� existe um tipo de funcion�rio com este nome", null);
					return;
				}
			}
		}
		TipoFuncionario tipo = new TipoFuncionario();
		tipo.setNome(tipoFunc.getNome());
		tipo.setDescricao(tipoFunc.getDescricao());
		dao.save(tipo);
		mostrarTodosTipos();
		tipoFunc = new TipoFuncionario();
		Mensagem.alerta(Mensagem.INFO, "Tipo de funcion�rio cadastrado com sucesso", null);
	}
	
	public void mostrarTodosTipos() {
		this.setListaTiposFunc(dao.findAll());
	}
	
	public void alterarTipo() {
		dao.update(tipoFunc);
		mostrarTodosTipos();
		tipoFunc = new TipoFuncionario();
		Mensagem.alerta(Mensagem.INFO, "Tipo de funcion�rio alterado com sucesso", null);
		podeAlterar = false;
	}
	
	public void removerTipo() {
		if(tipoFuncSelecionado == null) {
			Mensagem.alerta(Mensagem.INFO, "Selecione um Tipo de Funcion�rio para remover", null);
			return;
		}
		
		try {
			dao.delete(tipoFuncSelecionado);
			mostrarTodosTipos();
			Mensagem.alerta(Mensagem.INFO, "Tipo de funcion�rio removido com sucesso", null);
		} catch (Exception e) {
			Mensagem.alerta(Mensagem.INFO, "N�o � poss�vel remover um tipo de funcion�rio que j� esteja relacionado com um funcion�rio", null);
		}
	}
	
	public void alterarTipoFuncSelecionado() {
		if(tipoFuncSelecionado == null) {
			Mensagem.alerta(Mensagem.INFO, "Selecione um Tipo de Funcion�rio para alterar", null);
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

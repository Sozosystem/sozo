package controller;

import java.util.List;

import javax.faces.bean.ManagedBean;

import model.TipoFuncionario;
import dao.EntityManagerHelper;
import dao.TipoFuncionarioDAO;

@ManagedBean(name="tipo_funcionario")
public class TipoFuncionarioController {
	
	private TipoFuncionarioDAO dao;
	private TipoFuncionario tipoFunc;
	private TipoFuncionario tipoFuncSelecionado;
	
	public TipoFuncionarioController() {
		EntityManagerHelper emh = new EntityManagerHelper();            
        dao = new TipoFuncionarioDAO(emh.getEntityManager());
        tipoFunc = new TipoFuncionario();
	}
	
	public List<TipoFuncionario> getTipos() {
		return dao.findAll();
	}
	
	public void salvarTipo() {
		TipoFuncionario tipo = new TipoFuncionario();
		tipo.setNome(tipoFunc.getNome());
		tipo.setDescricao(tipoFunc.getDescricao());
		dao.save(tipo);
	}
	
	public String alterarTipo() {
		dao.update(tipoFunc);
		return "index.xhtml";
	}
	
	public String removerTipo() {
		dao.delete(tipoFuncSelecionado);
		//viatura = new viatura();
		return "index.xhtml";
	}
	
	public TipoFuncionario getViatura() {
		return tipoFunc;
	}

	public void setViatura(TipoFuncionario tipo) {
		this.tipoFunc = tipo;
	}
	
	public TipoFuncionario getViaturaSelecionada() {
        return tipoFuncSelecionado;
    }
 
    public void setViaturaSelecionada(TipoFuncionario tipo) {
        this.tipoFuncSelecionado = tipo;
    }

}

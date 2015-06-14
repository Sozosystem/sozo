package controller;

import java.io.IOException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import model.Funcionario;
import model.Situacao;
import model.TipoFuncionario;
import util.Mensagem;
import dao.EntityManagerHelper;
import dao.FuncionarioDAO;
import dao.TipoFuncionarioDAO;

@ManagedBean(name = "funcionario")
@ViewScoped
public class FuncionarioController {
	private Funcionario funcionario;
	private Funcionario funcionarioSelecionado;
	private Funcionario funcionarioConsulta;
	private FuncionarioDAO dao;
	private List<Funcionario> listaFuncionarios;
	private Situacao[] situacoes = Situacao.values();
	private List<TipoFuncionario> tipoFuncionario;
	private TipoFuncionarioDAO tipoFuncionarioDao;
	private boolean podeAlterar;

	public FuncionarioController() {
		EntityManagerHelper emh = new EntityManagerHelper();
		dao = new FuncionarioDAO(emh.getEntityManager());
		tipoFuncionarioDao = new TipoFuncionarioDAO(emh.getEntityManager());

		funcionario = new Funcionario();
		funcionarioConsulta = new Funcionario();
		listarTiposFunc();
		mostrarTodosFuncionarios();
	}

	public List<Funcionario> getListaFuncionarios() {
		return listaFuncionarios;
	}

	public void listarTiposFunc() {
		tipoFuncionario = tipoFuncionarioDao.findAll();
	}

	public void salvarFuncionario() {
		Funcionario f = new Funcionario();
		f.setNome(funcionario.getNome());
		f.setUsuario(funcionario.getUsuario());
		f.setSenha(funcionario.getSenha());
		f.setTipoFuncionario(funcionario.getTipoFuncionario());
		f.setSituacao(funcionario.getSituacao());
		if (dao.verificaUsuarioFunc(funcionario.getUsuario())) {
			dao.save(f);
			mostrarTodosFuncionarios();
			funcionario = new Funcionario();
			Mensagem.alerta(Mensagem.INFO, "Funcionário adicionado com sucesso", null);
		} else {
			Mensagem.alerta(Mensagem.ERRO, "Usuário já existente", "");
		}
	}

	public void limparCampos() {
		funcionario = new Funcionario();
	}

	public void alterarFuncionario() {
		if(funcionarioSelecionado == null) {
			Mensagem.alerta(Mensagem.INFO, "Selecione um funcionário para alterar", null);
			return;
		}
		System.out.println("TENTANDO ALTERAR");
		dao.update(funcionario);
		mostrarTodosFuncionarios();
		funcionario = new Funcionario();
		podeAlterar = false;
		Mensagem.alerta(Mensagem.INFO, "Funcionário alterado com sucesso", null);
	}

	public void removerFuncionario() {
		if(funcionarioSelecionado == null) {
			Mensagem.alerta(Mensagem.INFO, "Selecione um funcionário para remover", null);
			return;
		}
		try {
			dao.delete(funcionarioSelecionado);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mostrarTodosFuncionarios();
		Mensagem.alerta(Mensagem.INFO, "Funcionário removido com sucesso", null);
	}

	public void consultarFuncionario() {
		if(funcionarioConsulta.getNome() == "" && funcionarioConsulta.getUsuario() == "" && funcionarioConsulta.getSituacao() == null && funcionarioConsulta.getTipoFuncionario() == null) {
			Mensagem.alerta(Mensagem.INFO, "Preencha ao menos um campo para realizar a consulta", null);
			return;
		}
		listaFuncionarios = dao.consultar(funcionarioConsulta);
	}

	public void funcionarioAlterarSelecionado() {
		if(funcionarioSelecionado == null) {
			Mensagem.alerta(Mensagem.INFO, "Selecione um funcionário para alterar", null);
			return;
		}
		funcionario = funcionarioSelecionado;
		podeAlterar = true;
		RequestContext.getCurrentInstance().scrollTo("form2");
	}

	public void mostrarTodosFuncionarios() {
		listaFuncionarios = dao.findAll();
	}
	
	public void cancelarAlteracao() {
		funcionarioSelecionado = null;
		funcionario = new Funcionario();
		podeAlterar = false;
		RequestContext.getCurrentInstance().reset("form2");
	}
	
	public void voltar() {
		funcionarioSelecionado = null;
		funcionario = new Funcionario();
		podeAlterar = false;
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("ocorrencias.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public Situacao[] getSituacoes() {
		return situacoes;
	}

	public void setSituacoes(Situacao[] situacoes) {
		this.situacoes = situacoes;
	}

	public List<TipoFuncionario> getTipoFuncionario() {
		return tipoFuncionario;
	}

	public void setTipoFuncionario(List<TipoFuncionario> tipoFuncionario) {
		this.tipoFuncionario = tipoFuncionario;
	}

	public boolean getPodeAlterar() {
		return podeAlterar;
	}

	public void setPodeAlterar(boolean podeAlterar) {
		this.podeAlterar = podeAlterar;
	}

}

package controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import model.Funcionario;
import model.Situacao;
import model.TipoFuncionario;
import util.Mensagem;
import dao.EntityManagerHelper;
import dao.FuncionarioDAO;
import dao.TipoFuncionarioDAO;

@ManagedBean(name = "funcionario")
@RequestScoped
public class FuncionarioController {
	private Funcionario funcionario;
	private Funcionario funcionarioSelecionado;
	private Funcionario funcionarioConsulta;
	private FuncionarioDAO dao;
	private List<Funcionario> listaFuncionarios;
	private Situacao[] situacoes = Situacao.values();
	private List<TipoFuncionario> tipoFuncionario;
	private TipoFuncionarioDAO tipoFuncionarioDao;

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
		} else {
			Mensagem.alerta(Mensagem.ERRO, "Usuário já existente", "");
		}

	}

	public void limparCampos() {
		funcionario = new Funcionario();
	}

	public void alterarFuncionario() {
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
		if (funcionarioSelecionado == null)
			return;
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

}

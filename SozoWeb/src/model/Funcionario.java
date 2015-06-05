package model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class Funcionario extends ObjetoGeral{
	
	private String nome;
	private String usuario;
	private String senha;
	@ManyToOne(fetch = FetchType.EAGER)
	private TipoFuncionario tipoFuncionario;
	
	public Funcionario() {
		
	}

	public Funcionario(String nome, String usuario, String senha) {
		super();
		this.nome = nome;
		this.usuario = usuario;
		this.senha = senha;
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public TipoFuncionario getTipoFuncionario() {
		return tipoFuncionario;
	}

	public void setTipoFuncionario(TipoFuncionario tipoFuncionario) {
		this.tipoFuncionario = tipoFuncionario;
	}

	@Override
	public String toString() {
		return nome;
	}
	
	
	
}

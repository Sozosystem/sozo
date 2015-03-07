package model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class Funcionario extends ObjetoGeral{
	
	private String nome;
	private String usuario;
	private String senha;
	@ManyToOne(fetch = FetchType.EAGER)
	private TipoFunc tipoFunc;
	
	/*public Funcionario() {
		super();
		tipoFunc = new TipoFunc();
		
	}*/
	
	public Funcionario(){
		
	}

	public Funcionario(String nome, String usuario, String senha,
			TipoFunc tipoFunc) {
		super();
		this.nome = nome;
		this.usuario = usuario;
		this.senha = senha;
		this.tipoFunc = tipoFunc;
	}
	
	public Funcionario(String nome, String usuario, String senha) {
		super();
		this.nome = nome;
		this.usuario = usuario;
		this.senha = senha;
	}

	public Funcionario(Integer codigo, Calendar dataUltimaAtualizacao,
			Situacao situacao) {
		super(codigo, dataUltimaAtualizacao, situacao);
		tipoFunc = new TipoFunc();
	}

	public TipoFunc getTipoFunc() {
		return tipoFunc;
	}

	public void setTipoFunc(TipoFunc tipoFunc) {
		this.tipoFunc = tipoFunc;
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
	
	
}

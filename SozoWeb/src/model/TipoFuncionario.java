package model;

import javax.persistence.Entity;

@Entity
public class TipoFuncionario extends ObjetoGeral {
	
	
	private String nome;
	private String descricao;
	
	public TipoFuncionario() {
		super();
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
	
	
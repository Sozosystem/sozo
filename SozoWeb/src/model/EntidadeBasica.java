package model;

import java.util.Calendar;

import javax.persistence.MappedSuperclass;
import javax.persistence.Column;


@MappedSuperclass
public abstract class EntidadeBasica extends ObjetoGeral {
	
	@Column(nullable=false)
	private String descricao;
	
	//****** CONSTRUTORES ******
	
	public EntidadeBasica() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public EntidadeBasica(String descricao) {
		super();
		this.descricao = descricao;
	}

	public EntidadeBasica(String descricao, Calendar dataUltimaAtualizacao, Situacao situacao) {
		super(dataUltimaAtualizacao, situacao);
		this.descricao = descricao;
		// TODO Auto-generated constructor stub
	}

	public EntidadeBasica(Integer codigo, String descricao, Calendar dataUltimaAtualizacao,
			Situacao situacao) {
		super(codigo, dataUltimaAtualizacao, situacao);
		this.descricao = descricao;
		// TODO Auto-generated constructor stub
	}
	
	
	
	// --- GETs e SETs

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	@Override
	public String toString() {
		return "EntidadeBasica [descricao=" + descricao + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof EntidadeBasica))
			return false;
		EntidadeBasica other = (EntidadeBasica) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		return true;
	}

	
	
}

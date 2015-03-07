package model;

import java.util.Calendar;
import javax.persistence.*;

@MappedSuperclass
public abstract class ObjetoGeral {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Temporal(TemporalType.DATE)
	private Calendar dataUltimaAtualizacao;
	@Enumerated(EnumType.STRING)
	private Situacao situacao;
	
		
	public ObjetoGeral() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ObjetoGeral(Calendar dataUltimaAtualizacao, Situacao situacao) {
		this(null, dataUltimaAtualizacao, situacao);
	}

	public ObjetoGeral(Integer id, Calendar dataUltimaAtualizacao,
			Situacao situacao) {
		super();
		this.id = id;
		this.dataUltimaAtualizacao = dataUltimaAtualizacao;
		this.situacao = situacao;
	}


	public Integer getId() {
		return id;
	}
	public void setCodigo(Integer id) {
		this.id = id;
	}
	public Calendar getDataUltimaAtualizacao() {
		return dataUltimaAtualizacao;
	}
	public void setDataUltimaAtualizacao(Calendar dataUltimaAtualizacao) {
		this.dataUltimaAtualizacao = dataUltimaAtualizacao;
	}
	public Situacao getSituacao() {
		return situacao;
	}
	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ObjetoGeral other = (ObjetoGeral) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}

package model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.*;

@MappedSuperclass
public abstract class ObjetoGeral {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataUltimaAtualizacao;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCriacao;
	@Enumerated(EnumType.STRING)
	private Situacao situacao;
	
		
	public ObjetoGeral() {
		super();
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
	
	@PrePersist
	protected void onCreate() {
		dataCriacao = Calendar.getInstance().getTime();
	}
	
	@PreUpdate
	protected void onUpdate() {
		dataUltimaAtualizacao = Calendar.getInstance();
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
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

	public Date getDataCriacao() {
		return dataCriacao;
	}
	

	
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
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
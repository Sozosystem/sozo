package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ocorrencia")
public class Ocorrencia extends ObjetoGeral{
	
	private String tempo;
	private double latitude;
	private double longitude;
	private String imagem;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataHoraFinal;
	@ManyToOne(fetch = FetchType.EAGER)
	private Funcionario funcionario;
	@ManyToOne(fetch = FetchType.EAGER)
	private Solicitante solicitante;
	@ManyToMany
	private List<Viatura> viaturas = new ArrayList<Viatura>();
	@Enumerated(EnumType.STRING)
	private SituacaoOcorrencia situacaoOcorrencia;
	private String foto;
	@ElementCollection
    private List<String > observacao = new ArrayList<String>();
	private Endereco endereco;
	
	public List<Viatura> getViaturas() {
		return viaturas;
	}
	public void setViaturas(List<Viatura> viaturas) {
		this.viaturas = viaturas;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getImagem() {
		return imagem;
	}
	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
	public Calendar getDataHoraFinal() {
		return dataHoraFinal;
	}
	public void setDataHoraFinal(Calendar dataHoraFinal) {
		this.dataHoraFinal = dataHoraFinal;
	}
	public Funcionario getFuncionario() {
		if (funcionario == null){  
		    funcionario = new Funcionario();  
		}  
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	public Solicitante getSolicitante() {
		return solicitante;
	}
	public void setSolicitante(Solicitante solicitante) {
		this.solicitante = solicitante;
	}
	public SituacaoOcorrencia getSituacaoOcorrencia() {
		return situacaoOcorrencia;
	}
	public void setSituacaoOcorrencia(SituacaoOcorrencia situacaoOcorrencia) {
		this.situacaoOcorrencia = situacaoOcorrencia;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}

	public List<String> getObservacao() {
		return observacao;
	}
	public void setObservacao(List<String> observacao) {
		this.observacao = observacao;
	}
	
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	@Override
	public String toString() {
		return "Ocorrencia latitude=" + latitude + ", longitude=" + longitude
				+ ", imagem=" + imagem + ", dataHoraFinal=" + dataHoraFinal
				+ ", funcionario=" + funcionario + ", solicitante="
				+ solicitante + ", viaturas=" + viaturas
				+ ", situacaoOcorrencia=" + situacaoOcorrencia + ", foto="
				+ foto;
	}
	public String getTempo() {
		return tempo;
	}
	public void setTempo(String tempo) {
		this.tempo = tempo;
	}
	
}

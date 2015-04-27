package model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="solicitante")
public class Solicitante extends ObjetoGeral{

	private String nome;
	private String telefone;
	private String imei;

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	
}

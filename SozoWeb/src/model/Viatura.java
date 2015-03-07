package model;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "viatura")
public class Viatura extends ObjetoGeral{

	private String tipo;
	private String placa;
	private String descricao;
	
	public Viatura() {
		
	}
	
	public Viatura(String tipo,String placa,String descricao){
		super();
		this.tipo = tipo;
		this.placa = placa;
		this.descricao = descricao;
	}
	
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
	
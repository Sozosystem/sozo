package model;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "viatura")
public class Viatura extends ObjetoGeral {

	

	private String tipo;
	private String placa;
	private String descricao;
	private double latitude;
	private double longitude;
	private boolean disponivel;
	
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

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public boolean isDisponivel() {
		return disponivel;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public void setDisponivel(boolean disponivel) {
		this.disponivel = disponivel;
	}
}
	
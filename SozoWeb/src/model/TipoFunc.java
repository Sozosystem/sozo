package model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class TipoFunc extends EntidadeBasica {

		
	//*** CONSTRUTORES ***
	public TipoFunc() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public TipoFunc(String nome) {
		this(nome, null, null);
	}
	
	public TipoFunc(String nome, Calendar dataUltimaAtualizacao, Situacao situacao) {
		super(nome, dataUltimaAtualizacao, situacao);
	}

	@Override
	public String toString() {
		return getDescricao();
	}	
	
	
}

package controller;

import java.util.List;

import javax.faces.bean.ManagedBean;

import model.Solicitante;
import dao.EntityManagerHelper;
import dao.SolicitanteDAO;

@ManagedBean(name="solicitante")
public class SolicitanteController {
	
	private SolicitanteDAO dao;
	private Solicitante solicitante;
	
	public SolicitanteController(){
		EntityManagerHelper emh = new EntityManagerHelper();            
		dao = new SolicitanteDAO(emh.getEntityManager());
		solicitante = new Solicitante();
	}
	
	public SolicitanteDAO getDao() {
		return dao;
	}

	public void setDao(SolicitanteDAO dao) {
		this.dao = dao;
	}

	public Solicitante getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(Solicitante solicitante) {
		this.solicitante = solicitante;
	}

	public List<Solicitante> getLista() {
			
		return dao.findAll();
	}

}

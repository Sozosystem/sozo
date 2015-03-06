package controller;

import java.util.List;

import javax.faces.bean.ManagedBean;

import dao.EntityManagerHelper;
import dao.ViaturaDAO;
import model.Viatura;

@ManagedBean(name="viatura")
public class ViaturaController {
	
	private ViaturaDAO dao;
	
	public ViaturaController() {
		EntityManagerHelper emh = new EntityManagerHelper();            
        dao = new ViaturaDAO(emh.getEntityManager());
	}
	
	public List<Viatura> getListaViaturas() {
		return dao.findAll();
	}
}

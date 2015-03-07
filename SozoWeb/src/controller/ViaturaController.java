package controller;

import java.util.List;

import javax.faces.bean.ManagedBean;

import dao.EntityManagerHelper;
import dao.ViaturaDAO;
import model.Viatura;

@ManagedBean(name="viatura")
public class ViaturaController {
	
	private ViaturaDAO dao;
	private Viatura viatura;
	
	public ViaturaController() {
		EntityManagerHelper emh = new EntityManagerHelper();            
        dao = new ViaturaDAO(emh.getEntityManager());
	}
	
	public List<Viatura> getListaViaturas() {
		return dao.findAll();
	}
	
	public void salvarViatura() {
		System.out.println(viatura);
		Viatura v = new Viatura();
		v.setTipo(viatura.getTipo());
		v.setPlaca(viatura.getPlaca());
		v.setDescricao(viatura.getDescricao());
		dao.save(v);
	}
	
	public String alterarViatura() {
		dao.update(viatura);
		return "index.xhtml";
	}
	
	public String removerViatura() {
		dao.delete(viatura);
		//viatura = new viatura();
		return "index.xhtml";
	}
	
	public Viatura getViatura() {
		return viatura;
	}

	public void setViatura(Viatura viatura) {
		this.viatura = viatura;
	}
	
}

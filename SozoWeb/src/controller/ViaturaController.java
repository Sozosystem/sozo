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
	private Viatura viaturaSelecionada;
	
	public ViaturaController() {
		EntityManagerHelper emh = new EntityManagerHelper();            
        dao = new ViaturaDAO(emh.getEntityManager());
        viatura = new Viatura();
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
		dao.delete(viaturaSelecionada);
		//viatura = new viatura();
		return "index.xhtml";
	}
	
	public Viatura getViatura() {
		return viatura;
	}

	public void setViatura(Viatura viatura) {
		this.viatura = viatura;
	}
	
	public Viatura getViaturaSelecionada() {
        return viaturaSelecionada;
    }
 
    public void setViaturaSelecionada(Viatura v) {
        this.viaturaSelecionada = v;
    }
	
}

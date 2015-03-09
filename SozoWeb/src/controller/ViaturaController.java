package controller;


import java.util.List;

import javax.faces.application.ProtectedViewException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import model.Viatura;
import dao.EntityManagerHelper;
import dao.ViaturaDAO;

@ManagedBean(name="viatura")
@RequestScoped
public class ViaturaController {
	private Viatura viatura;
	private Viatura viaturaSelecionada;
	private Viatura viaturaConsulta;
	private ViaturaDAO dao;
	private List<Viatura> listaViaturas;

	
	public ViaturaController() {
		EntityManagerHelper emh = new EntityManagerHelper();            
        dao = new ViaturaDAO(emh.getEntityManager());
        
        viatura = new Viatura();
        viaturaConsulta = new Viatura();
        mostrarTodasViaturas();
	}

	
	public void salvarViatura() {
		Viatura v = new Viatura();
		v.setPlaca(viatura.getPlaca());
		v.setTipo(viatura.getTipo());
		v.setDescricao(viatura.getDescricao());

		dao.save(v);
		mostrarTodasViaturas();
		viatura = new Viatura();
	}
	
	public void alterarViatura() {
		dao.update(viatura);
		mostrarTodasViaturas();
		viatura = new Viatura();
	}
	
	public void removerViatura() {
		dao.delete(viaturaSelecionada);
		mostrarTodasViaturas();
	}
	
	public void consultarViatura() {
		listaViaturas = dao.findByObject(viaturaConsulta);
	}
	
	public void viaturaAlterarSelecionada() {
		if(viaturaSelecionada == null) return;
		viatura = viaturaSelecionada;
	}
	
	public void mostrarTodasViaturas() {
		listaViaturas = dao.findAll();
	}


	public Viatura getViatura() {
		return viatura;
	}


	public Viatura getViaturaSelecionada() {
		return viaturaSelecionada;
	}


	public Viatura getViaturaConsulta() {
		return viaturaConsulta;
	}


	public List<Viatura> getListaViaturas() {
		return listaViaturas;
	}


	public void setViatura(Viatura viatura) {
		this.viatura = viatura;
	}


	public void setViaturaSelecionada(Viatura viaturaSelecionada) {
		this.viaturaSelecionada = viaturaSelecionada;
	}


	public void setViaturaConsulta(Viatura viaturaConsulta) {
		this.viaturaConsulta = viaturaConsulta;
	}


	public void setListaViaturas(List<Viatura> listaViaturas) {
		this.listaViaturas = listaViaturas;
	}

 
}

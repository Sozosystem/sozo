package controller;


import java.util.List;




import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import util.Mensagem;
import model.Viatura;
import dao.EntityManagerHelper;
import dao.ViaturaDAO;

@ManagedBean(name="viatura")
@ViewScoped
public class ViaturaController {
	private Viatura viatura;
	private Viatura viaturaSelecionada;
	private Viatura viaturaConsulta;
	private ViaturaDAO dao;
	private List<Viatura> listaViaturas;
	private boolean podeAlterar;

	
	public ViaturaController() {
		EntityManagerHelper emh = new EntityManagerHelper();            
        dao = new ViaturaDAO(emh.getEntityManager());
        
        viatura = new Viatura();
        viaturaConsulta = new Viatura();
        mostrarTodasViaturas();
	}

	
	public void salvarViatura() {
		Viatura vituraPlaca = new Viatura();
		vituraPlaca.setPlaca(viatura.getPlaca());
		List<Viatura> l = dao.findByObject(vituraPlaca);
		if(l.size() != 0) {
			Mensagem.alerta(Mensagem.ERRO, "Uma viatura com esta placa já está cadastrada", null);
			return;
		}
		
		Viatura v = new Viatura();
		v.setPlaca(viatura.getPlaca().toUpperCase());
		v.setTipo(viatura.getTipo());
		v.setDescricao(viatura.getDescricao());

		dao.save(v);
		mostrarTodasViaturas();
		viatura = new Viatura();
		Mensagem.alerta(Mensagem.INFO, "Viatura cadastrada com sucesso", null);
	}
	
	public void alterarViatura() {
		dao.update(viatura);
		mostrarTodasViaturas();
		viatura = new Viatura();
		podeAlterar = false;
		Mensagem.alerta(Mensagem.INFO, "Viatura adicionada com sucesso", null);
	}
	
	public void removerViatura() {
		if(viaturaSelecionada == null) {
			Mensagem.alerta(Mensagem.INFO, "Selecione uma viatura para remover", null);
			return;
		}
		try {
			dao.delete(viaturaSelecionada);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mostrarTodasViaturas();
		Mensagem.alerta(Mensagem.INFO, "Viatura removida com sucesso", null);
	}
	
	public void consultarViatura() {
		if(viaturaConsulta.getPlaca() == "" && viaturaConsulta.getTipo() == "") {
			Mensagem.alerta(Mensagem.INFO, "Preencha ao menos um campo para realizar a consulta", null);
			return;
		}
		listaViaturas = dao.findByObject(viaturaConsulta);
	}
	
	public void viaturaAlterarSelecionada() {
		if(viaturaSelecionada == null) {
			Mensagem.alerta(Mensagem.INFO, "Selecione uma viatura para alterar", null);
			return;
		}
		viatura = viaturaSelecionada;
		podeAlterar = true;
	}
	
	public void mostrarTodasViaturas() {
		listaViaturas = dao.findAll();
	}

	public void cancelarAlteracao() {
		viaturaSelecionada = null;
		viatura = new Viatura();
		podeAlterar = false;
		RequestContext.getCurrentInstance().reset("form2");
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


	public boolean getPodeAlterar() {
		return podeAlterar;
	}


	public void setPodeAlterar(boolean podeAlterar) {
		this.podeAlterar = podeAlterar;
	}

 
}

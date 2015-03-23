package controller;

public abstract class BaseBeanController{
	
	protected boolean poedAlterar = false;
	protected String tituloDaPagina = "Titulo Da Pagina";
	
	public boolean isPoedAlterar() {
		return poedAlterar;
	}
	
	public void setPoedAlterar(boolean poedAlterar) {
		this.poedAlterar = poedAlterar;
	}
	
	public String getTituloDaPagina() {
		return tituloDaPagina;
	}
	
	public void setTituloDaPagina(String tituloDaPagina) {
		this.tituloDaPagina = tituloDaPagina;
	}
	
	
}

package model;

public class AuxGrafico {
	
	private int codigo;
	private String nome;
	private String cidade;
	private String quantidade;
	private String qtdCidade;
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}
	
	
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	
	
	public String getQtdCidade() {
		return qtdCidade;
	}
	public void setQtdCidade(String qtdCidade) {
		this.qtdCidade = qtdCidade;
	}
	public AuxGrafico(int codigo, String nome, String quantidade) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.quantidade = quantidade;
	}
	public AuxGrafico(int codigo, String nome, String cidade,
			String quantidade, String qtdCidade) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.cidade = cidade;
		this.quantidade = quantidade;
		this.qtdCidade = qtdCidade;
	}
	public AuxGrafico(int codigo, String nome, String cidade, String quantidade) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.cidade = cidade;
		this.quantidade = quantidade;
	}
	public AuxGrafico(int codigo, String nome) {
		super();
		this.codigo = codigo;
		this.nome = nome;
	}
	
	
	
	
}

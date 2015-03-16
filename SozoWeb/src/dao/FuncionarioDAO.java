package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import model.Funcionario;

public class FuncionarioDAO extends GenericDAO<Integer, Funcionario> {

	public FuncionarioDAO(EntityManager entityManager) {
		super(entityManager);
	}

	public Funcionario verificarLogin(Funcionario funcionario) throws Exception {

		Funcionario funcionarioLogado = new Funcionario();
		try {
			Funcionario f = (Funcionario) this.entityManager.createQuery(
					"from Funcionario WHERE usuario LIKE '%"
							+ funcionario.getUsuario()
							+ "%' AND senha LIKE '%"
							+ funcionario.getSenha() + "%'")
					.getSingleResult();
			if(!f.getSenha().equals(funcionario.getSenha())) throw new Exception();

			funcionarioLogado = f;
		} catch (Exception e) {
			throw new Exception("Login ou senha inválidos");
		}
		return funcionarioLogado;

	}

	public boolean verificaUsuarioFunc(String usuario) {
		boolean resposta = false;
		String usuarioF = usuario.toUpperCase();
		try {
			this.entityManager.createQuery(
				"from Funcionario WHERE upper(usuario) ='" + usuarioF + "'")
				.getSingleResult();
		} catch (NoResultException e){
			resposta = true;
		}
		return resposta;

	}

	public List<Funcionario> consultar(Funcionario f) {
		String q = "FROM Funcionario f WHERE f.nome LIKE :nome AND f.usuario LIKE :usuario";
		if(f.getSituacao() != null) q +=  " AND f.situacao LIKE :situacao";
		if(f.getTipoFuncionario() != null) q +=  " AND f.tipoFuncionario LIKE :tipoFuncionario";

		TypedQuery<Funcionario> query = entityManager.createQuery(q, Funcionario.class);
		
		query.setParameter("nome", "%" + f.getNome() + "%");
		query.setParameter("usuario", "%" + f.getUsuario() + "%");
		if(f.getTipoFuncionario() != null) query.setParameter("tipoFuncionario", f.getTipoFuncionario());
		if(f.getSituacao() != null) query.setParameter("situacao", f.getSituacao());
		
		return query.getResultList();
	}
	
}
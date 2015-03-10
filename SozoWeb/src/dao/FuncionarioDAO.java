package dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import model.Funcionario;

public class FuncionarioDAO extends GenericDAO<Integer, Funcionario> {

	public FuncionarioDAO(EntityManager entityManager) {
		super(entityManager);
	}

	public Funcionario verificarLogin(Funcionario funcionario) throws Exception {

		Funcionario funcionarioLogado = new Funcionario();
		try {
			funcionarioLogado = (Funcionario) this.entityManager.createQuery(
					"from Funcionario WHERE usuario='"
							+ funcionario.getUsuario().toString()
							+ "' AND senha='"
							+ funcionario.getSenha().toString() + "'")
					.getSingleResult();
			System.out.println(funcionarioLogado.getNome());
		} catch (NoResultException nre) {
			throw new Exception("Login ou senha inválidos.");
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

}
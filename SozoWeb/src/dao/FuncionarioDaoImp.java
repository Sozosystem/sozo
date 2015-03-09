package dao;

import java.util.List;

import model.Funcionario;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class FuncionarioDaoImp {
	
	public void save(Funcionario funcionario) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(funcionario);
		t.commit();
	}
	public Funcionario getFuncionario(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return (Funcionario) session.load(Funcionario.class, id);
	}
	
	public List<Funcionario> list() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List<Funcionario> lista = session.createQuery("from funcionario").list();
		t.commit();
		return lista;
	}
	
	public void remove(Funcionario funcionario) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.delete(funcionario);
		t.commit();
	}
	
	public void update(Funcionario funcionario) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.update(funcionario);
		t.commit();
	}
}
package dao;

import java.util.List;

import model.TipoFuncionario;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class TipoFuncionarioDaoImp {
	
	public void save(TipoFuncionario tipo) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(tipo);
		t.commit();
	}
	public TipoFuncionario getTipoFuncionario(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return (TipoFuncionario) session.load(TipoFuncionario.class, id);
	}
	
	public List<TipoFuncionario> list() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<TipoFuncionario> lista = session.createQuery("from tipofuncionario").list();
		t.commit();
		return lista;
	}
	
	public void remove(TipoFuncionario tipo) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.delete(tipo);
		t.commit();
	}
	
	public void update(TipoFuncionario tipo) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.update(tipo);
		t.commit();
	}

}

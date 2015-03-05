package dao;

import java.util.List;

import model.Funcionario;
import model.Viatura;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class ViaturaDaoImp  {
	
	public void save(Viatura v) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(v);
		t.commit();
	}
	public Viatura getFuncionario(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return (Viatura) session.load(Viatura.class, id);
	}
	
	public List<Funcionario> list() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List<Funcionario> lista = session.createQuery("from viatura").list();
		t.commit();
		return lista;
	}
	
	public void remove(Viatura v) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.delete(v);
		t.commit();
	}
	
	public void update(Viatura v) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.update(v);
		t.commit();
	}
}

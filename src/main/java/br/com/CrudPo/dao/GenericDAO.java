package br.com.CrudPo.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.Transaction;
import br.com.CrudPo.util.HibernateUtil;

public class GenericDAO<Entidade> {
	private Class<Entidade> classe; // Objeto do tipo class//

	public GenericDAO() { // Construtor//
		this.classe = (Class<Entidade>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	public void salvar(Entidade entidade) {
		Session sessao = HibernateUtil.getfabrica().openSession();
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();// iniciei a transa��o//
			sessao.save(entidade); // salvei a entidade//
			transacao.commit(); // dei o commit//
		} catch (RuntimeException e) { // Exception em tempo, runtime//
			if (transacao != null) {
				transacao.rollback();
			}
			throw e;
		} finally {
			sessao.close();
		}
	}

	public void excluir(Entidade entidade) {
		Session sessao = HibernateUtil.getfabrica().openSession();// Conex�o com o banco//
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(entidade);
			transacao.commit();
		} catch (RuntimeException e) {
			if (transacao != null) {
				transacao.rollback();
			}
			throw e;
		} finally {
			sessao.close();
		}

	}

	public void editar(Entidade entidade) {
		Session sessao = HibernateUtil.getfabrica().openSession();// Conex�o com o banco//
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();
			sessao.update(entidade);
			transacao.commit();
		} catch (RuntimeException e) {
			if (transacao != null) {
				transacao.rollback();
			}
			throw e;
		} finally {
			sessao.close();
		}
	}

	public ArrayList<Entidade> listar() { // Famoso Select//
		Session sessao = HibernateUtil.getfabrica().openSession();// conex�o com o banco//
		try {
			CriteriaBuilder builder = sessao.getCriteriaBuilder(); // Construtor pra consulta//
			CriteriaQuery<Entidade> consulta = builder.createQuery(classe);// Cria a consulta//
			consulta.from(classe);// Na classe//
			ArrayList<Entidade> resultado = (ArrayList<Entidade>) sessao.createQuery(consulta).getResultList();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}

	public Entidade buscar(Long codigo) {// Procura uma entidade em espec�fico//
		Session sessao = HibernateUtil.getfabrica().openSession();
		Entidade resultado = null;
		try {
			resultado = sessao.find(classe, codigo); // Condi��o no select//
			return resultado;

		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}
}

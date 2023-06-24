package br.com.JavaWeb.DAO;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.JavaWeb.util.HibernateUtil;

public class GenericDAO<Entidade> {
	
	private Class<Entidade> classe;
	
	@SuppressWarnings("unchecked")
	public GenericDAO() {
		this.classe = (Class<Entidade>) ((ParameterizedType) getClass().getGenericSuperclass())
					.getActualTypeArguments()[0];
	}
	
	public void salvar(Entidade entidade) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();
			sessao.merge(entidade);
			transacao.commit();
		
		}catch (RuntimeException erro) {
			if (transacao != null) {
				transacao.rollback();
			}
			throw erro;
		} finally {
			sessao.close();
		}		
	}
	
	public Entidade buscar(Long codigo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Entidade resultado = null;
		try {
			resultado = sessao.find(classe, codigo);
			return resultado;
		}catch(RuntimeException erro) {
			throw erro;
		}finally {
			sessao.close();
		}
	}
	
	public void excluir(Entidade entidade) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(entidade);
			transacao.commit();
		}catch (RuntimeException erro) {
			if (transacao != null) {
				transacao.rollback();
			}
			throw erro;
		}finally {
			sessao.close();
		}
	}
	
	public void editar(Entidade entidade) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();
			sessao.update(entidade);
			transacao.commit();
		}catch (RuntimeException erro) {
			if (transacao != null) {
				transacao.rollback();
			}
			throw erro;
		}finally {
			sessao.close();
		}
	}
	
	public ArrayList<Entidade> listar(){
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			CriteriaBuilder builder = sessao.getCriteriaBuilder();
			CriteriaQuery<Entidade> consulta = builder.createQuery(classe);
			consulta.from(classe);
			ArrayList<Entidade> resultado = (ArrayList<Entidade>) sessao.createQuery(consulta).getResultList();
			return resultado;
		}catch (RuntimeException erro) {
			throw erro;
		}finally {
			sessao.close();
		}
	}

}

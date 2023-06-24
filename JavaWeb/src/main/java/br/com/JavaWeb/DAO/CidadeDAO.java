package br.com.JavaWeb.DAO;

import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.JavaWeb.domain.Cidade;
import br.com.JavaWeb.util.HibernateUtil;

public class CidadeDAO extends GenericDAO<Cidade> {

	@SuppressWarnings("deprecation")
	public ArrayList<Cidade> buscarPorEstado(Long estadoCodigo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Cidade.class);
			consulta.add(Restrictions.eq("estado.codigo", estadoCodigo));
			consulta.addOrder(Order.asc("nome"));
			ArrayList<Cidade> resultado = (ArrayList<Cidade>) consulta.list();
			return resultado;
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			sessao.close();
		}
	}
}

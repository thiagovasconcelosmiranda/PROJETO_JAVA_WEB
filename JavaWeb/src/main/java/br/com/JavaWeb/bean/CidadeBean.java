package br.com.JavaWeb.bean;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.JavaWeb.DAO.CidadeDAO;
import br.com.JavaWeb.DAO.EstadoDAO;
import br.com.JavaWeb.domain.Cidade;
import br.com.JavaWeb.domain.Estado;


@SuppressWarnings("serial")
@ViewScoped
@ManagedBean
public class CidadeBean implements Serializable {
	
	private Cidade cidade;
	
	private ArrayList<Cidade> cidades;
	private ArrayList<Estado> estados;
	
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	public ArrayList<Cidade> getCidades() {
		return cidades;
	}
	public void setCidades(ArrayList<Cidade> cidades) {
		this.cidades = cidades;
	}
	public ArrayList<Estado> getEstados() {
		return estados;
	}
	public void setEstados(ArrayList<Estado> estados) {
		this.estados = estados;
	}
	
	@PostConstruct
	public void listar() {
		
		try {
			CidadeDAO cidadeDAO = new CidadeDAO();
			cidades = cidadeDAO.listar();
			
		}
		catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Erro ao tentar listar!");
		}
		
	}
	
	public void novo() {
		try {
			cidade = new Cidade();
			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listar();
		
		}
		catch(RuntimeException ex){
			ex.printStackTrace();
			Messages.addGlobalError("Erro ao tentar gerar uma nova cidade!");
			
		}
	}
	
	public void excluir(ActionEvent evento) {
		try {
			cidade = (Cidade)evento.getComponent().getAttributes().get("cidadeSelecionada");
			CidadeDAO cidadeDAO = new CidadeDAO();
			cidadeDAO.excluir(cidade);
			cidades = cidadeDAO.listar();
			Messages.addGlobalInfo("Removido com sucesso!");
		}
		catch(RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Erro ao tentar excluir!");
			
		}
	}
	
	public void salvar() {
		try {
			CidadeDAO cidadeDAO = new CidadeDAO();
			cidadeDAO.salvar(cidade);
			cidade = new Cidade();
			cidades = cidadeDAO.listar();
			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listar();
			Messages.addGlobalInfo("Salvo com sucesso!");
		}
		catch(RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Erro ao tentar salvar!");
			
		}
	}
	
	public void editar(ActionEvent evento) {
		try {
			cidade = (Cidade)evento.getComponent().getAttributes().get("cidadeSelecionada");
			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listar();
		}
		catch(RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Erro ao tentar editar!");
			
		}
	
	}


}

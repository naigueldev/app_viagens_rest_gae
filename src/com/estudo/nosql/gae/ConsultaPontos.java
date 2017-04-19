package com.estudo.nosql.gae;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.estudo.nosql.gae.model.PontoDeInteresse;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;


public class ConsultaPontos extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String nomeCidade = req.getParameter("nome");
		
		DatastoreService datastore = 
				DatastoreServiceFactory.getDatastoreService();
		
//		predicados de filtros
		Filter filtro = 
				new FilterPredicate(
						"nomeCidade", // nome da propriedade a ser comparada 
						FilterOperator.EQUAL, 
						nomeCidade); // valor a ser comparado
		
		
		Query query = 
				new Query(PontoDeInteresse.class.getSimpleName())
					.setFilter(filtro);
		
		List<PontoDeInteresse> pontos = new ArrayList();
		PreparedQuery pQuery = datastore.prepare(query);
		
//		transforma a busca em um iteravel
		for (Entity entidade : pQuery.asIterable()) {
			PontoDeInteresse ponto = new PontoDeInteresse();
			ponto.nomeCidade = (String)entidade.getProperty("nomeCidade");
			ponto.nome = (String)entidade.getProperty("nome");
			ponto.descricao = (String)entidade.getProperty("descricao");
			ponto.latitude = Float.parseFloat("" + entidade.getProperty("latitude"));
			ponto.longitude = Float.parseFloat("" + entidade.getProperty("longitude"));
			
			pontos.add(ponto);
		}
		
//		JSON = Formato de representação de dados
//		Gson = Biblioteca da Google para uso de JSON
		resp.setContentType("text/json");
		com.google.appengine.repackaged.com.google.gson.Gson gson = 
				new com.google.appengine.repackaged.com.google.gson.Gson();
//		transforma o pontos em JSON e retorna
		resp.getWriter().write(gson.toJson(pontos));
	}
}

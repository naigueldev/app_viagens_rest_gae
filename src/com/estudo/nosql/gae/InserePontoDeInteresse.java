package com.estudo.nosql.gae;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.estudo.nosql.gae.model.Cidade;
import com.estudo.nosql.gae.model.PontoDeInteresse;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

public class InserePontoDeInteresse extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
//		Inserir dados
		PontoDeInteresse poi = new PontoDeInteresse();
		poi.nomeCidade = req.getParameter("nomeCidade");
		poi.nome = req.getParameter("nome");
		poi.descricao = req.getParameter("descricao");
		poi.latitude = Float.parseFloat(req.getParameter("latitude"));
		poi.longitude = Float.parseFloat(req.getParameter("longitude"));
		
//		criar entity
//		Parametro do construtor, o primeiro é o tipo e o segundo é a chave
		Entity entidadePoi = new Entity(
				PontoDeInteresse.class.getSimpleName(), poi.nomeCidade + "|" + poi.nome);
		
		entidadePoi.setProperty("nomeCidade", poi.nomeCidade);
		entidadePoi.setProperty("nome", poi.nome);
		entidadePoi.setProperty("descricao", poi.descricao);
		entidadePoi.setProperty("latitude", poi.latitude);
		entidadePoi.setProperty("longitude", poi.longitude);

		
		DatastoreService datastore = 
				DatastoreServiceFactory.getDatastoreService();
		datastore.put(entidadePoi);
		
		resp.getWriter().write("Ponto de interesse inserido com sucesso!");
	}

}

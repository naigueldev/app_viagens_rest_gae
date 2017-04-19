package com.estudo.nosql.gae;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.estudo.nosql.gae.model.Cidade;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

import com.google.appengine.api.datastore.Entity;


public class InsereCidade extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
//		Inserir dados
		Cidade cidade = new Cidade();
//		buscar parametro com o nome = nome , nome do input
		cidade.nome = req.getParameter("nome");
		
//		criar entity
//		Parametro do construtor, o primeiro é o tipo e o segundo é a chave
		Entity entidadeCidade = new Entity(
				Cidade.class.getSimpleName(), cidade.nome);
		
		entidadeCidade.setProperty("nome", cidade.nome);
		
		DatastoreService datastore = 
				DatastoreServiceFactory.getDatastoreService();
		datastore.put(entidadeCidade);
		
		resp.getWriter().write("Cidade inserida com sucesso!");
	}
}

package com.leonardoz.client.estado;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import com.leonardoz.client.ApiLink;
import com.leonardoz.client.Filme;
import com.leonardoz.client.Recurso;
import com.leonardoz.client.Recursos;

public class FilmeServices {

	public static String BASE_PATH = "http://localhost:8080";
	public static String RESOURCE_PATH = "/filmes";

	public final static GenericType<Recurso<Filme>> TIPO_ENTIDADE = new GenericType<Recurso<Filme>>() {
	};
	public final static GenericType<Recursos<Recurso<Filme>>> TIPO_LISTA = new GenericType<Recursos<Recurso<Filme>>>() {
	};
	public final static GenericType<List<ApiLink>> TIPO_LINKS = new GenericType<List<ApiLink>>() {
	};

	public Client client;
	public Function<Filme, FilmeState> classificaFilme;
	public Function<String, FilmesState> removeFilme;
	public Function<Filme, FilmeState> salvaFilme;
	public Function<String, FilmeState> porNome;
	public Function<Integer, FilmesState> melhoresPorNota;
	public Supplier<FilmesState> todosFilmes;
	public Supplier<FilmeState> self;

	public FilmeServices() {
		client = ClientBuilder.newClient();
	}

	public List<ApiLink> opcoesIniciais() {
		// setup da comunicação com o servidor
		WebTarget target = client.target(BASE_PATH + RESOURCE_PATH);
		Invocation invocation = target.request(MediaType.APPLICATION_JSON).build("OPTIONS");
		// recupera os pontos de entradas da api
		List<ApiLink> invoke = invocation.invoke(TIPO_LINKS);
		return invoke;
	}

	public void configuraClassficaFilme(ApiLink link) {
		classificaFilme = (filme) -> {
			Recurso<Filme> recurso = client.target(link.getHref()).request(MediaType.APPLICATION_JSON)
					.buildPut(Entity.entity(filme, MediaType.APPLICATION_JSON)).invoke(TIPO_ENTIDADE);
			return FilmeState.transitar(recurso);
		};
	}

	public void configuraRemoveFilme(ApiLink link) {
		removeFilme = (nome) -> {
			List<ApiLink> links = client.target(link.getHref()).resolveTemplate("nome", nome)
					.request(MediaType.APPLICATION_JSON).buildDelete().invoke(TIPO_LINKS);
			return FilmesState.transitarComLinks(links);
		};
	}

	public void configuraBuscaPorNome(ApiLink link) {
		porNome = (nome) -> {
			Recurso<Filme> recurso = client.target(link.getHref()).resolveTemplate("nome", nome)
					.request(MediaType.APPLICATION_JSON).buildGet().invoke(TIPO_ENTIDADE);
			return FilmeState.transitar(recurso);
		};
	}

	public void configuraBuscaPorMelhoresNotas(ApiLink link) {
		melhoresPorNota = (nota) -> {
			System.out.println(link.getHref());
			Recursos<Recurso<Filme>> recursos = client
					.target(link.getHref())
					.resolveTemplate("nota", nota)
					.request(MediaType.APPLICATION_JSON).buildGet().invoke(TIPO_LISTA);
			return FilmesState.transitar(recursos);
		};
	}

	public void configuraTodosFilmes(ApiLink link) {
		todosFilmes = () -> {
			Recursos<Recurso<Filme>> recursos = client.target(link.getHref())
					.request(MediaType.APPLICATION_JSON)
					.get(TIPO_LISTA);
			return FilmesState.transitar(recursos);
		};
	}

	public void configuraSelf(ApiLink link) {
		self = () -> {
			Recurso<Filme> recurso = client.target(link.getHref()).request(MediaType.APPLICATION_JSON)
					.get(TIPO_ENTIDADE);
			return FilmeState.transitar(recurso);
		};
	}

	public void configuraSalvarFilme(ApiLink link) {
		salvaFilme = (f) -> {
			Recurso<Filme> recurso = client.target(link.getHref()).request(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON).buildPost(Entity.entity(f, MediaType.APPLICATION_JSON))
					.invoke(TIPO_ENTIDADE);
			return FilmeState.transitar(recurso);
		};
	}

	public Optional<Supplier<FilmeState>> getSelf() {
		return Optional.ofNullable(self);
	}

	public Optional<Function<Filme, FilmeState>> getSalvaFilme() {
		return Optional.ofNullable(salvaFilme);
	}

	public Optional<Function<Integer, FilmesState>> getMelhoresPorNota() {
		return Optional.ofNullable(melhoresPorNota);
	}

	public Optional<Function<String, FilmeState>> getPorNome() {
		return Optional.ofNullable(porNome);
	}

	public Optional<Supplier<FilmesState>> getTodosFilmes() {
		return Optional.ofNullable(todosFilmes);
	}

	public Optional<Function<Filme, FilmeState>> getClassificaFilme() {
		return Optional.ofNullable(classificaFilme);
	}

	public Optional<Function<String, FilmesState>> getRemoveFilme() {
		return Optional.ofNullable(removeFilme);
	}

}

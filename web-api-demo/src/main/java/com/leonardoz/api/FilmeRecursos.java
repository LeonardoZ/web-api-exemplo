package com.leonardoz.api;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * 
 * @author Leonardo Classe responsável pelos métodos que disponibilizam os
 *         recursos da API. Especifica endpoints, tipos de entrada/saída e
 *         contem algumas lógicas de transformação.
 *         
 *         Dois estados estão previsos: 
 *         1º Filmes e ações gerais
 *         2º Filme e ações específicas
 */
@Path("/filmes")
@Produces(MediaType.APPLICATION_JSON)
public class FilmeRecursos {

	// Utilizada para obter informações detalhadas da URL
	@Context
	private UriInfo uriInfo;

	// Função que transforma um Filme em um Recurso<Filme>
	private Function<Filme, Recurso<Filme>> mapper = (a) -> Recurso.build(a, gerarLinksParaEstadoIndividual(a));

	// Repositorio de Filmes. (ver padrão Repository - DDD)
	public static Filmes filmes = new Filmes();

	/*
	 * Método responsável por listar os links iniciais da coleção.
	 */
	@OPTIONS
	@Path("")
	public Response opcoes() {
		List<ApiLink> links = geralLinksParaEstadoTodos();
		return Response.ok(links).encoding("UTF-8").build();
	}

	@GET
	@Path("")
	public Response retornaTodos() {
		return produzirResposta(filmes.listar());
	}

	@GET
	@Path("/por/nome/{nome}")
	public Response retornaPorNome(@PathParam("nome") String nome) {
		Filme filme = filmes.porNome(nome).get();
		return produzirResposta(filme);
	}

	@GET
	@Path("/melhores/avaliados/a/partir/{nota}")
	public Response retornaMaisBemAvaliados(@PathParam("nota") int nota) {
		List<Filme> filmesBemAvaliados = filmes.listaPorMaisBemAvaliados(nota);
		return produzirResposta(filmesBemAvaliados);
	}

	@POST
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response adicionarFilme(Filme filme) {
		filmes.adicionar(filme);
		return produzirResposta(filme);
	}

	@PUT
	@Path("/classifica")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response classificaFilme(Filme filme) {
		Optional<Filme> porNome = filmes.porNome(filme.getNome());
		if (porNome.isPresent()) {
			porNome.get().setNota(filme.getNota());
			return produzirResposta(porNome.get());
		} else {
			return Response.noContent().build();
		}
	}

	@DELETE
	@Path("/{nome}")
	public Response removeFilme(@PathParam("nome") String nome) {
		filmes.remover(nome);
		return Response.ok(geralLinksParaEstadoTodos()).build();
	}

	private List<ApiLink> geralLinksParaEstadoTodos() {
		URI base = uriInfo.getBaseUriBuilder().path(getClass()).build();

		URI urlAdiciona = uriInfo.getBaseUriBuilder().uri(base).path(FilmeRecursos.class, "adicionarFilme").build();
		Link adiciona = Link.fromUri(urlAdiciona).rel("adicionarFilme").title("Adiciona filme").type("application/json")
				.build();

		URI urlTodos = uriInfo.getBaseUriBuilder().uri(base).path(FilmeRecursos.class, "retornaTodos").build();
		Link todos = Link.fromUri(urlTodos).rel("todosFilmes").title("Retorna todos os filmes").type("application/json")
				.build();

		URI urlPorNome = uriInfo
				.getBaseUriBuilder()
				.uri(base)
				.path(getClass(), "retornaPorNome")
				.build();
		
		Link porNome = Link.fromUri(urlPorNome)
				.rel("retornaPorNome")
				.title("Busca filme por nome")
				.type("application/json").build();

		URI urlNota = uriInfo.getBaseUriBuilder().uri(base).path(getClass(), "retornaMaisBemAvaliados").buildFromEncoded();
		Link melhoresPorNota = Link.fromUri(urlNota)
				.rel("melhoresPorNota")
				.title("Retorna mais bem avaliados por nota")
				.type("application/json")
				.build();

		List<ApiLink> links = ApiLinkBuilder.de(adiciona, todos, porNome, melhoresPorNota);
		return links;
	}

	public List<ApiLink> gerarLinksParaEstadoIndividual(Filme filme) {

		URI base = uriInfo.getBaseUriBuilder().path(getClass()).build();

		// Estado individual
		URI urlSelf = uriInfo.getBaseUriBuilder().uri(base).path(getClass(), "retornaPorNome")
				.build(filme.getNome());
		Link self = Link.fromUri(urlSelf).rel("self").title("Busca filme por nome").type("application/json")
				.build();
		
		URI urlPorNome = uriInfo.getBaseUriBuilder().uri(base).path(getClass(), "retornaPorNome")
				.build(filme.getNome());
		Link porNome = Link.fromUri(urlPorNome).rel("self").title("Busca filme por nome").type("application/json")
				.build();

		URI urlAdiciona = uriInfo.getBaseUriBuilder().uri(base).path(getClass(), "adicionarFilme").build();
		Link adiciona = Link.fromUri(urlAdiciona).rel("adicionarFilme").title("Adiciona filme").type("application/json")
				.build();

		URI urlClassifica = uriInfo.getBaseUriBuilder().uri(base).path(getClass(), "classificaFilme")
				.build(filme.getNome(), filme.getNota());
		Link classifica = Link.fromUri(urlClassifica).rel("classificarFilme").title("Classificar filme")
				.type("application/json").build();

		URI urlRemove = uriInfo.getBaseUriBuilder().uri(base).path(getClass(), "removeFilme").build(filme.getNome());
		Link remove = Link.fromUri(urlRemove).rel("removerFilme").title("Remove filme").type("application/json")
				.build();

		// Estado todos
		URI urlTodos = uriInfo.getBaseUriBuilder().uri(base).path(getClass(), "retornaTodos").build();
		Link todos = Link.fromUri(urlTodos).rel("todosFilmes").title("Retorna todos os filmes").type("application/json")
				.build();

		URI urlMelhores = uriInfo.getBaseUriBuilder().uri(base).path(getClass(), "retornaMaisBemAvaliados")
				.build(filme.getNota());
		Link melhoresPorNota = Link.fromUri(urlMelhores).rel("melhoresPorNota")
				.title("Retorna mais bem avaliados por nota").type("application/json").build(filme.getNota());
		return ApiLinkBuilder.de(self, adiciona, porNome, classifica, remove, melhoresPorNota, todos);

	}

	public Response produzirResposta(List<Filme> filmes) {
		List<Recurso<Filme>> recursos = filmes.stream().map(mapper).collect(Collectors.toList());
		Recursos<Recurso<Filme>> recursosComLinks = Recursos.build(recursos, geralLinksParaEstadoTodos());
		return Response.ok(recursosComLinks).encoding("UTF-8").build();
	}

	public Response produzirResposta(Filme filme) {
		Recurso<Filme> recurso = mapper.apply(filme);
		return Response.ok(recurso).encoding("UTF-8").build();
	}

}

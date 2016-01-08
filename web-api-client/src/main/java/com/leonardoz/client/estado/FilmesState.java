package com.leonardoz.client.estado;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.leonardoz.client.ApiLink;
import com.leonardoz.client.Filme;
import com.leonardoz.client.Recurso;
import com.leonardoz.client.Recursos;
/**
 * 
 * @author Leonardo
 *
 *	Estado referente a todos os recursos, estado inicial da API
 *	Cada interação com os métodos direciona para outro estado.
 *
 */
public class FilmesState {

	private List<Filme> filmes;
	private List<FilmeState> filmesEmEstado;
	private FilmeServices services;

	public static FilmesState inicar() {
		FilmesState filmesState = new FilmesState();
		filmesState.configurarSimples();
		return filmesState;
	}

	public static FilmesState transitar(Recursos<Recurso<Filme>> recursos) {
		FilmesState filmesState = new FilmesState();
		filmesState.configurarSimples();
		filmesState.iniciarCom(recursos);
		return filmesState;
	}

	public static FilmesState transitarComLinks(List<ApiLink> links) {
		FilmesState filmesState = new FilmesState();
		filmesState.iniciarComLinks(links);
		return filmesState;
	}

	public FilmesState() {
		services = new FilmeServices();
		filmes = Collections.emptyList();
	}

	private void iniciarCom(Recursos<Recurso<Filme>> recursos) {
		this.filmes = recursos.getEntidades().stream().map(Recurso::getEntidade).collect(Collectors.toList());
		this.filmesEmEstado = recursos.getEntidades().stream().map(FilmeState::transitar).collect(Collectors.toList());
	}

	private void iniciarComLinks(List<ApiLink> opcoes) {
		opcoes.forEach(l -> configuraServicosDeAcordo(l));
	}

	private void configurarSimples() {
		services.opcoesIniciais().forEach(this::configuraServicosDeAcordo);
	}

	/*
	 * Configura conforme o atributo rel dos links
	 */
	private void configuraServicosDeAcordo(ApiLink link) {
		FilmeRels porValor = FilmeRels.porValor(link.getRel());
		switch (porValor) {
		case REL_ADICIONAR_FILME:
			services.configuraSalvarFilme(link);
			break;
		case REL_MELHOR_NOTA:
			services.configuraBuscaPorMelhoresNotas(link);
			break;
		case REL_POR_NOME:
			services.configuraBuscaPorNome(link);
			break;
		case REL_TODOS:
			services.configuraTodosFilmes(link);
			break;
		default:
			break;
		}
	}

	public Optional<Function<Filme, FilmeState>> getSalvaFilme() {
		return services.getSalvaFilme();
	}

	public Optional<Function<Integer, FilmesState>> getMelhoresPorNota() {
		return services.getMelhoresPorNota();
	}

	public Optional<Function<String, FilmeState>> getPorNome() {
		return services.getPorNome();
	}

	public Optional<Supplier<FilmesState>> getTodosFilmes() {
		return services.getTodosFilmes();
	}

	public List<Filme> getFilmes() {
		return filmes;
	}

	public List<FilmeState> getFilmesEmEstado() {
		return filmesEmEstado;
	}
}

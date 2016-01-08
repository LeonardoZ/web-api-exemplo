package com.leonardoz.client.estado;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import com.leonardoz.client.ApiLink;
import com.leonardoz.client.Filme;
import com.leonardoz.client.Recurso;

/**
 * 
 * @author Leonardo
 *
 *	Estado referente um recurso (filme) específico
 *	Cada interação com os métodos direciona para outro estado.
 *
 */
public class FilmeState {

	private FilmeServices services;
	private Recurso<Filme> filme;

	public static FilmeState transitar(Recurso<Filme> filme) {
		FilmeState filmeResource = new FilmeState(filme);
		return filmeResource;
	}

	private FilmeState(Recurso<Filme> filme) {
		this.filme = filme;
		this.services = new FilmeServices();
		configuraServicosDeAcordo();
	}

	/*
	 * Configura conforme o atributo rel dos links
	 */
	public void configuraServicosDeAcordo() {
		List<ApiLink> links = filme.getLinks();
		for (ApiLink link : links) {

			FilmeRels porValor = FilmeRels.porValor(link.getRel());
			switch (porValor) {
			case REL_SELF:
				services.configuraSelf(link);
				break;
			case REL_ADICIONAR_FILME:
				services.configuraSalvarFilme(link);
				break;
			case REL_CLASSIFICA:
				services.configuraClassficaFilme(link);
				break;
			case REL_MELHOR_NOTA:
				services.configuraBuscaPorMelhoresNotas(link);
				break;
			case REL_POR_NOME:
				services.configuraBuscaPorNome(link);
				break;
			case REL_REMOVER:
				services.configuraRemoveFilme(link);
				break;
			case REL_TODOS:
				services.configuraTodosFilmes(link);
				break;
			default:
				break;
			}
		}
	}
	
	/*
	 * 
	 * Os Optional retornam diferentes tipos de SAM.
	 * Eles são instanciados conforme a disponibilidade do atributo REL,
	 * referente a requisição.
	 * 
	 */

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

	public Optional<Function<Filme, FilmeState>> getClassificaFilme() {
		return services.getClassificaFilme();
	}

	public Optional<Function<String, FilmesState>> getRemoveFilme() {
		return services.getRemoveFilme();
	}
	
	public Optional<Supplier<FilmeState>> getSelf(){
		return services.getSelf();
	}
	
	public Filme getFilme() {
		return filme.getEntidade();
	}

}

package com.leonardoz.client;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import com.leonardoz.client.estado.FilmeState;
import com.leonardoz.client.estado.FilmesState;

public class Run {

	public static void main(String[] args) {
		System.out.println("INICIO");
		FilmesState estado1 = FilmesState.inicar();
		Supplier<FilmesState> todosFilmes = estado1.getTodosFilmes().get();
		System.out.println("ESTADO 1");
		
		FilmesState estado2 = todosFilmes.get();
		List<Filme> filmes = estado2.getFilmes();
		System.out.println("Resultado de todos: " + filmes.size());
		System.out.println("ESTADO 2");
		
		FilmesState estado3 = estado2.getMelhoresPorNota().get().apply(10);
		List<Filme> filmesComNota10 = estado3.getFilmes();
		System.out.println(filmesComNota10.size());
		System.out.println("ESTADO 3");
		
		Filme filme = new Filme("Kill Bill", "Tarantino", "Blood", 10);
		Function<Filme, FilmeState> salvaFilme = estado3.getSalvaFilme().get();
		FilmeState estado4 = salvaFilme.apply(filme);
		Filme filme2 = estado4.getFilme();
		System.out.println(filme2.getNome());
		System.out.println(filme2.getDescricao());
		System.out.println("ESTADO 4");

		filme2.setNota(0);
		FilmeState estado5 = estado4.getClassificaFilme().get().apply(filme2);
		Filme filme3 = estado5.getFilme();
		System.out.println(filme3.getNome());
		System.out.println(filme3.getNota());
		System.out.println("ESTADO 5");	
	
		FilmesState estado6 = estado5.getRemoveFilme().get().apply("KILL BILL");
		System.out.println("ESTADO 6");	
		
		FilmesState estado7 = estado6.getTodosFilmes().get().get();
		System.out.println("ESTADO 7");	
		List<FilmeState> filmesEmEstado = estado7.getFilmesEmEstado();
		for (FilmeState filmeState : filmesEmEstado) {
			Supplier<FilmeState> getSelf = filmeState.getSelf().get();
			FilmeState filmeStateSelf = getSelf.get();
			System.out.println(filmeState.getFilme().getNome()+" - "+filmeStateSelf.getFilme().getNome());
		}
	}

}

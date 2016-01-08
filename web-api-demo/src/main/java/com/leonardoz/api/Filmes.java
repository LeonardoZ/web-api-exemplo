package com.leonardoz.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Filmes {

	private List<Filme> filmes = new ArrayList<>(
			Arrays.asList(
					new Filme("Incepetion","Christopher Nolan","Muito bom!",10),
					new Filme("O Senhor dos Aneis 1", "Peter Jackson", "A sociedade do anel", 7),
					new Filme("O Senhor dos Aneis 2", "Peter Jackson", "As Duas Torres", 9),
					new Filme("O Senhor dos Aneis 3", "Peter Jackson", "O Retorno do Rei", 10),
					new Filme("Star Wars I", "George Lucas", "A Ameaça fantasma", 5),
					new Filme("Star Wars II", "George Lucas", "O Ataque dos Clones", 7),
					new Filme("Star Wars III", "George Lucas", "O Retorno dos Sith", 8)));

	public void adicionar(Filme f) {
		filmes.add(f);
	}

	public void remover(String nome) {
		filmes.removeIf(f -> f.getNome().equals(nome));
	}

	public Optional<Filme> porNome(String nome) {
		return filmes.stream().filter(f -> f.getNome().equals(nome)).findAny();
	}

	public List<Filme> listar() {
		return filmes;
	}

	public List<Filme> listaPorTituloOrdemAlfabetica() {
		return filmes.stream().sorted((a, b) -> a.getNome().compareTo(b.getDescricao())).collect(Collectors.toList());
	}

	public List<Filme> listaPorMaisBemAvaliados(int nota) {
		return filmes.stream()
				.filter(f -> f.getNota() >= nota)
				.sorted((a, b) -> Integer.valueOf(a.getNota()).compareTo(b.getNota()))
				.collect(Collectors.toList());
	}

}

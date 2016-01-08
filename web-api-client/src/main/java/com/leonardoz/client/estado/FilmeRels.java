package com.leonardoz.client.estado;

import java.util.Arrays;

public enum FilmeRels {

	REL_TODOS("todosFilmes"), REL_SELF("self"), REL_ADICIONAR_FILME("adicionarFilme"), REL_MELHOR_NOTA(
			"melhoresPorNota"), REL_POR_NOME("retornaPorNome"), REL_CLASSIFICA("classificarFilme"), REL_REMOVER(
					"removerFilme");

	private String valor;

	FilmeRels(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}

	public static FilmeRels porValor(String valor) {
		return Arrays.asList(values()).stream().filter(v -> v.getValor().equals(valor)).findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}
}

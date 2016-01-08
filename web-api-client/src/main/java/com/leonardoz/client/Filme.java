package com.leonardoz.client;

public class Filme {

	private String nome;
	private String diretor;
	private String descricao;
	private int nota;
	
	public Filme() {

	}

	public Filme(String nome, String diretor, String descricao, int nota) {
		super();
		this.nome = nome;
		this.diretor = diretor;
		this.descricao = descricao;
		this.nota = nota;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDiretor() {
		return diretor;
	}

	public void setDiretor(String diretor) {
		this.diretor = diretor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getNota() {
		return nota;
	}

	public void setNota(int nota) {
		this.nota = nota;
	}

	@Override
	public String toString() {
		return nome;
	}
	
}

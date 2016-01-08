package com.leonardoz.api;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Recurso<T> {

	private List<ApiLink> links;

	private T entidade;

	public Recurso() {

	}
	
	public Recurso(T entidade, ApiLink... links) {
		super();
		this.links = Arrays.asList(links);
		this.entidade = entidade;
	}

	public Recurso(T entidade, List<ApiLink> links) {
		super();
		this.links = links;
		this.entidade = entidade;
	}

	public T getEntidade() {
		return entidade;
	}

	public List<ApiLink> getLinks() {
		return links;
	}

	public void setEntidade(T entidade) {
		this.entidade = entidade;
	}

	public void setLinks(List<ApiLink> links) {
		this.links = links;
	}

	public Optional<ApiLink> temLink(String rel) {
		return getLinks().stream().filter(l -> l.getRel().equals(rel)).findFirst();
	}

	public static <T> Recurso<T> build(T entidade, ApiLink... links) {
		return new Recurso<T>(entidade, links);
	}

	public static <T> Recurso<T> build(T entidade, List<ApiLink> links) {
		return new Recurso<T>(entidade, links);
	}

}

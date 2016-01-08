package com.leonardoz.api;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Recursos<T> {

	private List<ApiLink> links;

	private List<T> entidades;

	public Recursos() {

	}

	public Recursos(List<T> entidades, ApiLink... links) {
		super();
		this.links = Arrays.asList(links);
		this.entidades = entidades;
	}

	public Recursos(List<T> entidades, List<ApiLink> links) {
		super();
		this.links = links;
		this.entidades = entidades;
	}

	public List<T> getEntidades() {
		return entidades;
	}

	public void setEntidades(List<T> entidades) {
		this.entidades = entidades;
	}

	public List<ApiLink> getLinks() {
		return links;
	}

	public void setLinks(List<ApiLink> links) {
		this.links = links;
	}

	public Optional<ApiLink> temLink(String rel) {
		return getLinks().stream().filter(l -> l.getRel().equals(rel)).findFirst();
	}

	public static <T> Recursos<T> build(List<T> entidades, ApiLink... links) {
		return new Recursos<T>(entidades, links);
	}

	public static <T> Recursos<T> build(List<T> entidades, List<ApiLink> links) {
		return new Recursos<T>(entidades, links);
	}

}

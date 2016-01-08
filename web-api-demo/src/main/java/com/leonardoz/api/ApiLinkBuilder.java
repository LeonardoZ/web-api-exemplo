package com.leonardoz.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.Link;

public class ApiLinkBuilder {
	
	public static ApiLink de(Link linkJaxRs){
		return new ApiLink(linkJaxRs.getRel(), linkJaxRs.getUri().toString());
	}
	
	public static List<ApiLink> de(Link...linksJaxRs){
		return Arrays
				.asList(linksJaxRs)
				.stream()
				.map(ApiLinkBuilder::de)
				.collect(Collectors.toList());
	}

}

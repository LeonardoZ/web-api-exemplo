package com.leonardoz.api;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class RestClient {
	
	private Client client;
	private WebTarget target;

	public RestClient(String path) {
		client = ClientBuilder.newClient();
		target = client.target(path);
	}
	
	public <T> void post(String url){
	}

}

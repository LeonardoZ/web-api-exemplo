package com.leonardoz.client;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class ApiLink {

	private String rel;
	private String href;

	public ApiLink() {
	}

	public ApiLink(String rel, String href) {
		super();
		this.rel = rel;
		this.href = href;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public String getHref() {
		try {
			return URLDecoder.decode(href,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			return href;
		}
	}

	public void setHref(String href) {
		this.href = href;
	}

	@Override
	public String toString() {
		return "[rel=" + rel + ", href=" + href + "]";
	}

}

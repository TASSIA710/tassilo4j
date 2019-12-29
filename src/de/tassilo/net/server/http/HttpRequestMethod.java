package de.tassilo.net.server.http;

public enum HttpRequestMethod {
	
	GET("GET"),
	HEAD("HEAD"),
	POST("POST"),
	PUT("PUT"),
	DELETE("DELETE"),
	CONNECT("CONNECT"),
	OPTIONS("OPTIONS"),
	TRACE("TRACE"),
	PATCH("PATCH");
	
	private String str;
	private HttpRequestMethod(String str) {
		this.str = str;
	}
	
	@Override
	public String toString() {
		return str;
	}
	
	public static HttpRequestMethod parse(String str) {
		for (HttpRequestMethod e : values()) if (e.toString().equals(str)) return e;
		return null;
	}
	
}
package de.tassilo.net.server.http;

public enum HttpVersion {
	
	HTTP_0_9("HTTP/0.9"),
	HTTP_1_0("HTTP/1.0"),
	HTTP_1_1("HTTP/1.1"),
	HTTP_2_0("HTTP/2.0"),
	HTTP_3_0("HTTP/3.0");
	
	private String str;
	private HttpVersion(String str) {
		this.str = str;
	}
	
	@Override
	public String toString() {
		return str;
	}
	
	public static HttpVersion parse(String str) {
		for (HttpVersion e : values()) if (e.toString().equals(str)) return e;
		return null;
	}
	
}
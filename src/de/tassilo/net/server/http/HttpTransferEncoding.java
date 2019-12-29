package de.tassilo.net.server.http;

public enum HttpTransferEncoding {
	
	CHUNKED("chunked"),
	COMPRESS("compress"),
	DEFLATE("deflate"),
	GZIP("gzip"),
	IDENTITY("identity"),
	X_COMPRESS("x-compress"),
	X_GZIP("x-gzip");
	
	private String str;
	private HttpTransferEncoding(String str) {
		this.str = str;
	}
	
	@Override
	public String toString() {
		return str;
	}
	
	public static HttpTransferEncoding parse(String str) {
		for (HttpTransferEncoding e : values()) if (e.toString().equals(str)) return e;
		return null;
	}
	
}
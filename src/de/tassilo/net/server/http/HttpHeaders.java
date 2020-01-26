package de.tassilo.net.server.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpHeaders {
	public static final String CONNECTION = "Connection";
	public static final String CONTENT_LENGTH = "Content-Length";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String DATE = "Date";
	public static final String SERVER = "Server";
	public static final String TRANSFER_ENCODING = "Transfer-Encoding";
	protected final Map<String, String> headers;

	public HttpHeaders() {
		this.headers = new HashMap<String, String>();
	}

	public HttpHeaders(HttpHeaders src) {
		this.headers = new HashMap<String, String>(src.headers);
	}



	public boolean hasHeader(String header) {
		return headers.containsKey(header);
	}

	public Set<String> getHeaders() {
		return headers.keySet();
	}

	public String getHeader(String header) {
		return headers.get(header);
	}

	public void setHeader(String header, String value) {
		headers.put(header, value);
	}

	public void removeHeader(String header) {
		headers.remove(header);
	}

}

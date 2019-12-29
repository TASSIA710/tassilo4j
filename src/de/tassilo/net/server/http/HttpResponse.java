package de.tassilo.net.server.http;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class HttpResponse {
	private HttpVersion httpVersion;
	private HttpStatus httpStatus;
	private HttpResponseHeaders headers;
	private ByteArrayOutputStream content;
	
	
	
	public HttpResponse(HttpVersion version) {
		this(version, 1024);
	}
	
	public HttpResponse(HttpVersion version, int capacity) {
		httpVersion = version;
		httpStatus = HttpStatus.STATUS_200;
		headers = new HttpResponseHeaders();
		content = new ByteArrayOutputStream(capacity);
		
		getHeaders().setConnection(false);
		getHeaders().setDate();
		getHeaders().setServer();
	}
	
	
	
	public HttpVersion getVersion() {
		return httpVersion;
	}
	
	public HttpStatus getStatus() {
		return httpStatus;
	}
	
	public void setStatus(HttpStatus status) {
		httpStatus = status != null ? status : httpStatus;
	}
	
	public void setStatus(int status) {
		setStatus(HttpStatus.getByCode(status));
	}
	
	public HttpResponseHeaders getHeaders() {
		return headers;
	}
	
	public void setHeaders(HttpHeaders headers) {
		this.headers = new HttpResponseHeaders(headers);
	}
	
	
	
	public void clearContent() {
		content.reset();
	}
	
	public int contentSize() {
		return content.size();
	}
	
	public void writeContent(int b) {
		content.write(b);
	}
	
	public void writeContent(byte[] buf) {
		writeContent(buf, 0, buf.length);
	}
	
	public void writeContent(byte[] buf, int off, int len) {
		content.write(buf, off, len);
	}
	
	public void writeString(String str, Charset ch) {
		writeContent(str.getBytes(ch));
	}
	
	public void writeFile(File file) throws IOException {
		if (!file.exists()) return;
		FileInputStream fis = new FileInputStream(file);
		int read;
		while ((read = fis.read()) != -1) writeContent(read);
		fis.close();
	}
	
	public void writeError(int error, boolean highend) throws IOException {
		HttpStatus status = HttpStatus.getByCode(error);
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(HttpResponse.class.getResourceAsStream("/resource/de/tassilo/html/" + (highend ? "high" : "low") +"end/error.html")));
		String line;
		while ((line = br.readLine()) != null) sb.append(line).append("\n");
		
		String str = sb.toString();
		str = str.replace("{error_code}", Integer.toString(status.getCode()));
		str = str.replace("{error_name}", status.getInfo());
		str = str.replace("{error_description}", status.getDescription());
		str = str.replace("{server_name}", HttpServer.SERVER_NAME);

		setStatus(status);
		getHeaders().setContentType("text/html");
		clearContent();
		writeString(str, StandardCharsets.UTF_8);
	}
	
	public void outputContent(OutputStream out) throws IOException {
		content.writeTo(out);
	}
	
}
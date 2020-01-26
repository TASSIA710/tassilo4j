package de.tassilo.net.server.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;

public class HttpRequest {
	private HttpRequestMethod requestMethod;
	private String requestTarget;
	private HttpVersion httpVersion;
	private HttpRequestHeaders headers;


	private HttpRequest() {
		requestMethod = null;
		requestTarget = null;
		httpVersion = null;
		headers = new HttpRequestHeaders();
	}



	public HttpRequestMethod getMethod() {
		return requestMethod;
	}

	public String getTarget() {
		return requestTarget;
	}

	public HttpVersion getVersion() {
		return httpVersion;
	}

	public HttpRequestHeaders getHeaders() {
		return headers;
	}



	public boolean isHighEnd() {
		return true; // TODO
	}



	public static HttpRequest readHttpRequest(InputStream in) throws IOException, HttpException {
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		HttpRequest request = new HttpRequest();

		readStartLine(br, request);

		if (request.httpVersion == HttpVersion.HTTP_1_0 || request.httpVersion == HttpVersion.HTTP_1_1) {
			readHeaders(br, request);
			request.headers.verify();
			if (request.headers.hasContentLength()) {
				readBodyViaContentLength(in, request);
			} else if (request.headers.getTransferEncoding() == HttpTransferEncoding.CHUNKED) {
				readBodyViaChunkedTransferEncoding(br, request);
			}
			return request;
		}

		throw new HttpException(HttpException.ERROR_CODE_UNSUPPORTED_HTTP_VERSION, request.httpVersion.toString());
	}

	private static void readStartLine(BufferedReader br, HttpRequest request) throws IOException, HttpException {
		String line = br.readLine();
		if (line == null) throw new HttpException(HttpException.ERROR_CODE_UNEXPECTED_END_OF_STREAM);

		String[] segments = line.split(" ");

		if (segments.length != 3) throw new HttpException(HttpException.ERROR_CODE_MALFORMED_STARTLINE, line);

		request.requestMethod = HttpRequestMethod.parse(segments[0]);
		if (request.requestMethod == null) throw new HttpException(HttpException.ERROR_CODE_UNKNOWN_REQUEST_METHOD, segments[0]);

		request.httpVersion = HttpVersion.parse(segments[2]);
		if (request.httpVersion == null) throw new HttpException(HttpException.ERROR_CODE_UNKNOWN_HTTP_VERSION, segments[2]);

		request.requestTarget = URLDecoder.decode(segments[1], "UTF-8");
	}

	private static void readHeaders(BufferedReader br, HttpRequest request) throws IOException, HttpException {
		String line = br.readLine();
		if (line == null) throw new HttpException(HttpException.ERROR_CODE_UNEXPECTED_END_OF_STREAM);
		if (line.isEmpty()) return;

		String[] segments = line.split(":", 2);
		if (segments.length != 2) throw new HttpException(HttpException.ERROR_CODE_MALFORMED_HEADER, line);

		String key = URLDecoder.decode(segments[0].trim(), "UTF-8");
		String value = URLDecoder.decode(segments[1].trim(), "UTF-8");
		request.getHeaders().setHeader(key, value);

		readHeaders(br, request);
	}

	private static void readBodyViaContentLength(InputStream in, HttpRequest request) throws IOException, HttpException {
		int contentLength = request.headers.getContentLength();
		byte[] buffer = new byte[contentLength];
		in.read(buffer, 0, buffer.length);
	}

	private static void readBodyViaChunkedTransferEncoding(BufferedReader br, HttpRequest request) throws IOException, HttpException {
		// TODO
	}

}

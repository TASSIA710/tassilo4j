package de.tassilo.net.server.http;

public class HttpRequestHeaders extends HttpHeaders {

	public HttpRequestHeaders() {
		super();
	}

	public HttpRequestHeaders(HttpHeaders src) {
		super(src);
	}

	// See: https://en.wikipedia.org/wiki/List_of_HTTP_header_fields#Standard_request_fields



	// TODO: A-IM

	// TODO: Accept

	// TODO: Accept-Charset

	// TODO: Accept-Datetime

	// TODO: Accept-Encoding

	// TODO: Accept-Language

	// TODO: Access-Control-Request-Method

	// TODO: Access-Control-Request-Headers

	// TODO: Authorization

	// TODO: Cache-Control



	public boolean hasConnection() {
		return hasHeader(CONNECTION);
	}

	public boolean isConnectionClose() {
		return !isConnectionKeepAlive();
	}

	public boolean isConnectionKeepAlive() {
		String con = getConnectionString();
		if (con == null) return false;
		return con.toLowerCase() == "keep-alive";
	}

	public String getConnectionString() {
		return getHeader(CONNECTION);
	}

	public void verifyConnection() throws HttpException {
		String value = getHeader(CONNECTION);
		if (value == null) return;
		value = value.toLowerCase();
		if (value.equals("keep-alive") || value.equals("close")) return;
		throw new HttpException(HttpException.ERROR_CODE_ILLEGAL_HEADER, CONNECTION + ": " + getHeader(CONNECTION));
	}



	public boolean hasContentLength() {
		return getContentLength() > 0;
	}

	public int getContentLength() {
		String cl = getHeader(CONTENT_LENGTH);
		if (cl == null) return 0;
		try {
			return Integer.decode(cl);
		} catch (NumberFormatException ex) {
			return -1;
		}
	}

	public void verifyContentLength() throws HttpException {
		String value = getHeader(CONTENT_LENGTH);
		if (value == null) return;
		try {
			int len = Integer.decode(value);
			if (len > 0) return;
			throw new HttpException(HttpException.ERROR_CODE_ILLEGAL_HEADER, CONTENT_LENGTH + ": " + value);
		} catch (NumberFormatException ex) {
			throw new HttpException(HttpException.ERROR_CODE_ILLEGAL_HEADER, CONTENT_LENGTH + ": " + value);
		}
	}



	// TODO: Content-MD5



	public boolean hasContentType() {
		return hasHeader(CONTENT_TYPE);
	}

	public String getContentType() {
		return getHeader(CONTENT_TYPE);
	}

	public void setContentType(String contentType) {
		setHeader(CONTENT_TYPE, contentType);
	}

	public void verifyContentType() throws HttpException {
	}



	// TODO: Cookie

	// TODO: Date

	// TODO: Expect

	// TODO: Forwarded

	// TODO: From

	// TODO: Host

	// TODO: HTTP2-Settings

	// TODO: If-Match

	// TODO: If-Modified-Since

	// TODO: If-None-Match

	// TODO: If-Range

	// TODO: If-Unmodified-Since

	// TODO: Max-Forwards

	// TODO: Origin

	// TODO: Pragma

	// TODO: Proxy-Authorization

	// TODO: Range

	// TODO: Referer

	// TODO: TE

	// TODO: Trailer



	public boolean hasTransferEncoding() {
		return getTransferEncoding() != null;
	}

	public HttpTransferEncoding getTransferEncoding() {
		String value = getHeader(TRANSFER_ENCODING);
		if (value == null) return null;
		return HttpTransferEncoding.parse(value);
	}

	public void verifyTransferEncoding() throws HttpException {
		String value = getHeader(TRANSFER_ENCODING);
		if (value == null) return;
		if (HttpTransferEncoding.parse(value) == null) {
			throw new HttpException(HttpException.ERROR_CODE_ILLEGAL_HEADER, TRANSFER_ENCODING + ": " + value);
		}
	}



	// TODO: User-Agent

	// TODO: Upgrade

	// TODO: Via

	// TODO: Warning



	public void verify() throws HttpException {
		verifyConnection();
		verifyContentLength();
		verifyContentType();
		verifyTransferEncoding();
	}

}

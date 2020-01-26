package de.tassilo.net.server.http;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class HttpResponseHeaders extends HttpHeaders {

	public HttpResponseHeaders() {
		super();
	}

	public HttpResponseHeaders(HttpHeaders src) {
		super(src);
	}

	// See: https://en.wikipedia.org/wiki/List_of_HTTP_header_fields#Standard_response_fields



	// TODO: Access-Control-Allow-Origin

	// TODO: Access-Control-Allow-Credentials

	// TODO: Access-Control-Expose-Headers

	// TODO: Access-Control-Max-Age

	// TODO: Access-Control-Allow-Methods

	// TODO: Access-Control-Allow-Headers

	// TODO: Accept-Patch

	// TODO: Accept-Ranges

	// TODO: Age

	// TODO: Allow

	// TODO: Alt-Svc

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

	public void setConnection(boolean keepAlive) {
		setConnectionString(keepAlive ? "Keep-Alive" : "Close");
	}

	public void setConnectionString(String connection) {
		setHeader(CONNECTION, connection);
	}



	// TODO: Content-Disposition

	// TODO: Content-Encoding

	// TODO: Content-Language



	public boolean hasContentLength() {
		return getContentLength() > 0;
	}

	public int getContentLength() {
		String cl = getContentLengthString();
		if (cl == null) return 0;
		try {
			return Integer.decode(cl);
		} catch (NumberFormatException ex) {
			return -1;
		}
	}

	public String getContentLengthString() {
		return getHeader(CONTENT_LENGTH);
	}

	public void setContentLength(int length) {
		setContentLengthString(Integer.toString(length));
	}

	public void setContentLengthString(String length) {
		setHeader(CONTENT_LENGTH, length);
	}



	// TODO: Content-Location

	// TODO: Content-MD5

	// TODO: Content-Range



	public boolean hasContentType() {
		return hasHeader(CONTENT_TYPE);
	}

	public String getContentType() {
		return getHeader(CONTENT_TYPE);
	}

	public void setContentType(String contentType) {
		setHeader(CONTENT_TYPE, contentType);
	}



	public boolean hasDate() {
		return hasHeader(DATE);
	}

	public long getDate() {
		String date = getDateString();
		if (date == null) return 0;
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyy HH:mm:ss z", Locale.US);
		try {
			return sdf.parse(date).getTime();
		} catch (ParseException ex) {
			return -1;
		}
	}

	public String getDateString() {
		return getHeader(DATE);
	}

	public void setDate() {
		setDate(System.currentTimeMillis());
	}

	public void setDate(long date) {
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyy HH:mm:ss z", Locale.US);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		setDateString(sdf.format(new Date(date)));
	}

	public void setDateString(String date) {
		setHeader(DATE, date);
	}



	// TODO: Delta-Base

	// TODO: ETag

	// TODO: Expires

	// TODO: IM

	// TODO: Last-Modified

	// TODO: Link

	// TODO: Location

	// TODO: P3P

	// TODO: Pragma

	// TODO: Proxy-Authenticate

	// TODO: Public-Key-Pins

	// TODO: Retry-After



	public boolean hasServer() {
		return hasHeader(SERVER);
	}

	public String getServer() {
		return getHeader(SERVER);
	}

	public void setServer() {
		setHeader(SERVER, HttpServer.SERVER_NAME);
	}

	public void setServer(String server) {
		setHeader(SERVER, server);
	}



	// TODO: Set-Cookie

	// TODO: Strict-Transport-Security

	// TODO: Trailer

	// TODO: Transfer-Encoding

	// TODO: Tk

	// TODO: Upgrade

	// TODO: Vary

	// TODO: Via

	// TODO: Warning

	// TODO: WWW-Authenticate

	// TODO: X-Frame-Options

}

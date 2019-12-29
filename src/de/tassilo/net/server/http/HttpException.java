package de.tassilo.net.server.http;

public class HttpException extends Exception {
	public static final int ERROR_CODE_UNKNOWN_ERROR			= 0x00;
	public static final int ERROR_CODE_UNEXPECTED_END_OF_STREAM	= 0x01;
	public static final int ERROR_CODE_MALFORMED_STARTLINE		= 0x02;
	public static final int ERROR_CODE_UNKNOWN_REQUEST_METHOD	= 0x03;
	public static final int ERROR_CODE_UNKNOWN_HTTP_VERSION		= 0x04;
	public static final int ERROR_CODE_MALFORMED_HEADER			= 0x05;
	public static final int ERROR_CODE_ILLEGAL_HEADER			= 0x06;
	public static final int ERROR_CODE_UNSUPPORTED_HTTP_VERSION	= 0x07;
	private static final long serialVersionUID = -5758446679928885510L;
	private final int errorCode;
	
	
	
	public HttpException(int errorCode) {
		super();
		this.errorCode = errorCode;
	}
	
	public HttpException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public HttpException(int errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
	}
	
	public HttpException(int errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}
	
	
	
	public int getErrorCode() {
		return errorCode;
	}
	
}
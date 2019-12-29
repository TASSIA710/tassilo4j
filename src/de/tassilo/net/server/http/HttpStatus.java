package de.tassilo.net.server.http;

public enum HttpStatus {
	
	STATUS_100(100, "Continue", ""),
	STATUS_101(101, "Switching Protocols", ""),
	STATUS_102(102, "Processing", ""),
	
	STATUS_200(200, "OK", ""),
	STATUS_201(201, "Created", ""),
	STATUS_202(202, "Accepted", ""),
	STATUS_203(203, "Non-authoritive Information", ""),
	STATUS_204(204, "No Content", ""),
	STATUS_205(205, "Reset Content", ""),
	STATUS_206(206, "Partial Content", ""),
	STATUS_207(207, "Multi-Status", ""),
	STATUS_208(208, "Already Reported", ""),
	STATUS_226(226, "IM Used", ""),
	
	STATUS_300(300, "Multiple Choices", ""),
	STATUS_301(301, "Moved Permanently", ""),
	STATUS_302(302, "Found", ""),
	STATUS_303(303, "See Other", ""),
	STATUS_304(304, "Not Modified", ""),
	STATUS_305(305, "Use Proxy", ""),
	STATUS_307(307, "Temporary Redirect", ""),
	STATUS_308(308, "Permanent Redirect", ""),
	
	STATUS_400(400, "Bad Request", ""),
	STATUS_401(401, "Unauthorized", ""),
	STATUS_402(402, "Payment Required", ""),
	STATUS_403(403, "Forbidden", ""),
	STATUS_404(404, "Not Found", "The origin server did not find a current representation for the target resource or is not willing to disclose that one exists."),
	STATUS_405(405, "Method Not Allowed", ""),
	STATUS_406(406, "Not Acceptable", ""),
	STATUS_407(407, "Proxy Authentication Required", ""),
	STATUS_408(408, "Request Timeout", ""),
	STATUS_409(409, "Conflict", ""),
	STATUS_410(410, "Gone", ""),
	STATUS_411(411, "Length Required", ""),
	STATUS_412(412, "Precondition Failed", ""),
	STATUS_413(413, "Payload Too Large", ""),
	STATUS_414(414, "Request-URI Too Long", ""),
	STATUS_415(415, "Unsupported Media Type", ""),
	STATUS_416(416, "Requested Range Not Satisfiable", ""),
	STATUS_417(417, "Expectation Failed", ""),
	STATUS_418(418, "I'm a teapot", ""),
	STATUS_421(421, "Misdirected Request", ""),
	STATUS_422(422, "Unprocessable Entity", ""),
	STATUS_423(423, "Locked", "The source or destination resource of a method is locked."),
	STATUS_424(424, "Failed Dependency", ""),
	STATUS_426(426, "Upgrade Required", ""),
	STATUS_428(428, "Precondition Required", ""),
	STATUS_429(429, "Too Many Requests", ""),
	STATUS_431(431, "Request Header Fields Too Large", ""),
	STATUS_444(444, "Connection Closed Without Response", ""),
	STATUS_451(451, "Unavailable For Legal Reasons", ""),
	STATUS_499(499, "Client Closed Request", ""),
	
	STATUS_500(500, "Internal Server Error", ""),
	STATUS_501(501, "Not Implemented", ""),
	STATUS_502(502, "Bad Gateway", ""),
	STATUS_503(503, "Service Unavailable", ""),
	STATUS_504(504, "Gateway Timeout", ""),
	STATUS_505(505, "HTTP Version Not Supported", ""),
	STATUS_506(506, "Variant Also Negotiates", ""),
	STATUS_507(507, "Insufficient Storage", ""),
	STATUS_508(508, "Loop Detected", ""),
	STATUS_510(510, "Not Extended", ""),
	STATUS_511(511, "Network Authentication Required", ""),
	STATUS_599(599, "Netword Connect Timeout Error", "");
	
	private final int code;
	private final String info;
	private final String description;
	
	private HttpStatus(int code, String info, String description) {
		this.code = code;
		this.info = info;
		this.description = description;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getInfo() {
		return info;
	}
	
	public String getDescription() {
		return description;
	}
	
	@Override
	public String toString() {
		return getCode() + " " + getInfo();
	}
	
	public static HttpStatus parse(String str) {
		for (HttpStatus e : values()) if (e.toString().equals(str)) return e;
		return null;
	}
	
	public static HttpStatus getByCode(int status) {
		for (HttpStatus e : values()) if (e.getCode() == status) return e;
		return null;
	}
	
}
package de.tassilo.net.server.http;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import de.tassilo.net.server.StandardClient;

public class HttpClient extends StandardClient {
	private HttpServer server;

	public HttpClient(HttpServer server, Socket socket, int userID) throws IOException {
		super(server, socket, userID);
		this.server = server;
	}

	@Override
	public HttpServer getServer() {
		return server;
	}



	public void sendResponse(HttpResponse response) throws IOException {
		PrintStream out = new PrintStream(getOutputStream());

		// Start-line
		out.print(response.getVersion().toString());
		out.print(" ");
		out.print(response.getStatus().toString());
		out.println();

		// Headers
		for (String key : response.getHeaders().getHeaders()) {
			String value = response.getHeaders().getHeader(key);
			if (value == null) continue;
			out.print(key);
			out.print(": ");
			out.print(value);
			out.println();
		}
		out.println();

		// Content
		response.outputContent(out);
	}

}

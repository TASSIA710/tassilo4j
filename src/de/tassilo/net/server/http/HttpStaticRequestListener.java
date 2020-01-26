package de.tassilo.net.server.http;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;

import de.tassilo.io.json.JsonObject;
import de.tassilo.io.json.JsonReader;

public class HttpStaticRequestListener implements HttpRequestListener {
	private final File source;
	private final String listenTo;

	public HttpStaticRequestListener(File source) {
		this(source, "/");
	}

	public HttpStaticRequestListener(File source, String listenTo) {
		if (!source.isDirectory()) throw new IllegalArgumentException("source must be a folder");

		if (!listenTo.startsWith("/")) listenTo = "/" + listenTo;
		if (!listenTo.endsWith("/")) listenTo = listenTo + "/";

		this.source = source;
		this.listenTo = listenTo;
	}

	@Override
	public void onHttpRequest(HttpRequest request, HttpResponse response, HttpClient client, HttpServer server) {

		try {

			// Check if this listener listens to this request
			String target = request.getTarget();
			if (!target.startsWith("/")) target = "/" + target;
			if (!target.startsWith(listenTo)) return;
			if (target.endsWith("/")) target = target + "index.html";
			target = target.substring(listenTo.length());
			if (!target.startsWith("/")) target = "/" + target;

			// Examine the path out of the requested target and loop through everything to check access
			String[] paths = target.split("/");
			File file = source;
			for (int i = 0; i < paths.length; i++) {
				file = new File(file, paths[i] + "/");

				// Throw 404 if the requested resource does not exist
				if (!file.exists()) {
					response.writeError(404, request.isHighEnd());
					response.getHeaders().setContentLength(response.contentSize());
					return;
				}

				// Check if requested file is locked
				if (file.isFile()) {
					File meta = new File(file.getParent(), ".meta");
					if (!meta.exists()) continue;
					if (!meta.canRead()) {
						server.getLogger().severe("Cannot read " + meta.getPath());
						response.writeError(500, request.isHighEnd());
						response.getHeaders().setContentLength(response.contentSize());
						return;
					}

					JsonReader reader = new JsonReader(new FileReader(meta));
					JsonObject json;
					try {
						json = reader.readObject();
					} catch (ParseException ex) {
						server.getLogger().severe("File " + meta.getPath() + " could not be parsed.");
						reader.close();
						return;
					}
					reader.close();

					if (json.getBoolean(file.getName(), "locked")) {
						response.writeError(423, request.isHighEnd());
						response.getHeaders().setContentLength(response.contentSize());
						return;
					}

					// Break the for-loop, just in case something goes wrong
					break;

				}

				// Check if any of the folders the file is located in is locked
				if (file.isDirectory()) {
					File meta = new File(file, ".meta");
					if (!meta.exists()) continue;
					if (!meta.canRead()) {
						server.getLogger().severe("Cannot read " + meta.getPath());
						response.writeError(500, request.isHighEnd());
						response.getHeaders().setContentLength(response.contentSize());
						return;
					}

					JsonReader reader = new JsonReader(new FileReader(meta));
					JsonObject json;
					try {
						json = reader.readObject();
					} catch (ParseException ex) {
						server.getLogger().severe("File " + meta.getPath() + " could not be parsed.");
						reader.close();
						return;
					}
					reader.close();

					if (json.getBoolean("locked")) {
						response.writeError(423, request.isHighEnd());
						response.getHeaders().setContentLength(response.contentSize());
						return;
					}

				}

			}



			// Write file
			response.getHeaders().setContentType(Files.probeContentType(file.toPath()));
			response.writeFile(file);
			response.getHeaders().setContentLength(response.contentSize());



		} catch (IOException ex) {
			if (ex.getMessage() != null) {
				server.getLogger().severe("Client " + client.getUserID() + " caused an IOException: " + ex.getMessage());
			} else {
				server.getLogger().severe("Client " + client.getUserID() + " caused an IOException.");
			}
			ex.printStackTrace();

		}




		/*
		File f = new File(source, request.getTarget());

		if (!f.exists()) {
			// Show 404
			try {
				response.writeError(404, false);
				response.getHeaders().setContentLength(response.contentSize());
			} catch (IOException ex) {
				if (ex.getMessage() != null) server.getLogger().severe("Client " + client.getUserID() + " caused an IOException: " + ex.getMessage());
				else server.getLogger().severe("Client " + client.getUserID() + " caused an IOException.");
				ex.printStackTrace();
			}
			return;
		}

		if (f.isDirectory()) {
			File f2 = new File(f, "index.html");
			if (f2.exists()) {
				// Show index
				response.clearContent();
				try {
					response.getHeaders().setContentType(Files.probeContentType(f2.toPath()));
					response.writeFile(f2);
					response.getHeaders().setContentLength(response.contentSize());
				} catch (IOException ex) {
					if (ex.getMessage() != null) server.getLogger().severe("Client " + client.getUserID() + " caused an IOException: " + ex.getMessage());
					else server.getLogger().severe("Client " + client.getUserID() + " caused an IOException.");
					ex.printStackTrace();
				}
				return;
			} else {
				// Show 404
				try {
					response.writeError(404, false);
					response.getHeaders().setContentLength(response.contentSize());
				} catch (IOException ex) {
					if (ex.getMessage() != null) server.getLogger().severe("Client " + client.getUserID() + " caused an IOException: " + ex.getMessage());
					else server.getLogger().severe("Client " + client.getUserID() + " caused an IOException.");
					ex.printStackTrace();
				}
				return;
			}
		}

		if (f.isFile()) {
			// Show file
			response.clearContent();
			try {
				response.getHeaders().setContentType(Files.probeContentType(f.toPath()));
				response.writeFile(f);
				response.getHeaders().setContentLength(response.contentSize());
			} catch (IOException ex) {
				if (ex.getMessage() != null) server.getLogger().severe("Client " + client.getUserID() + " caused an IOException: " + ex.getMessage());
				else server.getLogger().severe("Client " + client.getUserID() + " caused an IOException.");
				server.getLogger().throwing("HttpStaticRequestListener", "onHttpRequest", ex);
			}
			return;
		}
		*/
	}

}

package de.tassilo.net.server.http;

@FunctionalInterface
public interface HttpRequestListener {
	void onHttpRequest(HttpRequest request, HttpResponse response, HttpClient client, HttpServer server);
}
package net.tassia.net.server.http;

@FunctionalInterface
public interface HttpCallback {

	void onHttpRequest(HttpRequest request, HttpResponse response, String[] regexResult);

}

package net.tassia.net.server.http;

import java.util.Map;

public interface HttpRequest {

    HttpProtocol getProtocolVersion();
    HttpMethod getMethod();
    Map<String, String> getHeaders();
    byte[] getContent();

    HttpServer<? extends HttpClient> getServer();
    HttpClient getClient();



    default String getHeader(String name) {
        return getHeaders().get(name);
    }

    default boolean hasHeader(String name) {
        return getHeader(name) != null;
    }

}

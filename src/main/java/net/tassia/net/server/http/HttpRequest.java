package net.tassia.net.server.http;

import net.tassia.net.server.Client;

import java.util.Map;

public interface HttpRequest {

    HttpProtocol getProtocolVersion();
    HttpMethod getMethod();
    Map<String, String> getHeaders();
    byte[] getContent();

    HttpServer<? extends Client> getServer();
    Client getClient();



    default String getHeader(String name) {
        return getHeaders().get(name);
    }

    default boolean hasHeader(String name) {
        return getHeader(name) != null;
    }

}

package net.tassia.net.server.light.http;

import net.tassia.net.server.http.HttpMethod;
import net.tassia.net.server.http.HttpProtocol;
import net.tassia.net.server.http.HttpRequest;
import net.tassia.net.server.light.LightClient;

import java.util.Map;

public class LightHttpRequest implements HttpRequest {
    private final HttpProtocol protocolVersion;
    private final HttpMethod method;
    private final Map<String, String> headers;
    private final String requestURI;
    private final byte[] content;
    private final LightHttpServer server;
    private final LightClient client;

    public LightHttpRequest(HttpProtocol protocolVersion, HttpMethod method, Map<String, String> headers,
                            String requestURI, byte[] content, LightHttpServer server, LightClient client) {
        this.protocolVersion = protocolVersion;
        this.method = method;
        this.headers = headers;
        this.requestURI = requestURI;
        this.content = content;
        this.server = server;
        this.client = client;
    }

    @Override
    public HttpProtocol getProtocolVersion() {
        return protocolVersion;
    }

    @Override
    public HttpMethod getMethod() {
        return method;
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public String getRequestURI() {
        return requestURI;
    }

    @Override
    public byte[] getContent() {
        return content;
    }

    @Override
    public LightHttpServer getServer() {
        return server;
    }

    @Override
    public LightClient getClient() {
        return client;
    }
}

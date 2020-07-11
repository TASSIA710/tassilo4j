package net.tassia.net.server.http;

public enum HttpProtocol {

    HTTP_1_0("HTTP/1.0"),
    HTTP_1_1("HTTP/1.1");

    public final String name;
    private HttpProtocol(String name) {
        this.name = name;
    }

}

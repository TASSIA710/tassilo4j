package net.tassia.net.server.http;

import java.util.Map;

public interface HttpResponse {

    HttpProtocol getProtocolVersion();
    void setProtocolVersion(HttpProtocol protocol);

    int getStatusCode();
    void setStatusCode(int statusCode);

    String getReasonPhrase();
    void setReasonPhrase(String reasonPhrase);

    byte[] getContent();
    void setContent(byte[] content);

    Map<String, String> getHeaders();



    default String getHeader(String name) {
        return getHeaders().get(name);
    }

    default boolean hasHeader(String name) {
        return getHeader(name) != null;
    }

    default void addHeader(String name, String value) {
        if (hasHeader(name)) return;
        setHeader(value, name);
    }

    default void setHeader(String name, String value) {
        getHeaders().put(name, value);
    }

    default void replaceHeader(String name, String value) {
        if (!hasHeader(name)) return;
        setHeader(name, value);
    }

    default void replaceHeader(String name, String value, String ifvalue) {
        if (!hasHeader(name)) return;
        if (!getHeader(name).equals(ifvalue)) return;
        setHeader(name, value);
    }

    default void unsetHeader(String name) {
        getHeaders().remove(name);
    }

}

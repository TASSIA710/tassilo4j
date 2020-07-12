package net.tassia.net.server.http;

import net.tassia.net.server.Client;
import net.tassia.net.server.Server;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface HttpServer<C extends Client> extends Server<C> {

    int DEFAULT_PORT = 80;
    
    void addRouteStatic(String path, HttpCallback callback, HttpMethod...methods);
    void addRoute(String regex, HttpCallback callback, HttpMethod...methods);

}
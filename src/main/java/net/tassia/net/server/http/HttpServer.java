package net.tassia.net.server.http;

import net.tassia.net.server.Server;

public interface HttpServer<C extends HttpClient> extends Server<C> {

    int DEFAULT_PORT = 80;

}

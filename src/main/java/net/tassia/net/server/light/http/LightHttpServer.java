package net.tassia.net.server.light.http;

import net.tassia.net.server.http.HttpServer;
import net.tassia.net.server.light.LightClient;
import net.tassia.net.server.light.LightServer;

import java.net.InetAddress;

public class LightHttpServer extends LightServer implements HttpServer<LightClient> {

    public LightHttpServer() {
        this(DEFAULT_PORT);
    }

    public LightHttpServer(int port) {
        super(port);
    }

    public LightHttpServer(int port, int backlog) {
        super(port, backlog);
    }

    public LightHttpServer(int port, int backlog, InetAddress bind) {
        super(port, backlog, bind);
    }

}

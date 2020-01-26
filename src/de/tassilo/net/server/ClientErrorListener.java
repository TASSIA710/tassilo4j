package de.tassilo.net.server;

@FunctionalInterface
public interface ClientErrorListener {
	void onClientError(Client client, Exception exception, Server server);
}

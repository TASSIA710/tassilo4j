package de.tassilo.net.server;

@FunctionalInterface
public interface ClientConnectedListener {
	void onClientConnected(Client client, Server server);
}
package de.tassilo.net.server;

@FunctionalInterface
public interface ClientDisconnectedListener {
	void onClientDisconnected(Client client, Server server);
}
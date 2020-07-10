package net.tassia.net.server.event;

import net.tassia.net.server.Client;
import net.tassia.net.server.Server;

public class ServerClientEvent extends ServerEvent {
	private final Client client;

	public ServerClientEvent(Server<? extends Client> server, Client client) {
		super(server);
		this.client = client;
	}

	public Client getClient() {
		return client;
	}

}

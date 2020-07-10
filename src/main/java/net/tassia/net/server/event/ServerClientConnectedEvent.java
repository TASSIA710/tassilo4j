package net.tassia.net.server.event;

import net.tassia.net.server.Client;
import net.tassia.net.server.Server;

public class ServerClientConnectedEvent extends ServerClientEvent {

	public ServerClientConnectedEvent(Server<? extends Client> server, Client client) {
		super(server, client);
	}

}

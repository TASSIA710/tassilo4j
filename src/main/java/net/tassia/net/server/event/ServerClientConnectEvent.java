package net.tassia.net.server.event;

import net.tassia.net.server.Client;
import net.tassia.net.server.Server;

public class ServerClientConnectEvent extends ServerClientEvent {

	public ServerClientConnectEvent(Server<? extends Client> server, Client client) {
		super(server, client);
	}

}

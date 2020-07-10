package net.tassia.net.server.event;

import net.tassia.event.Event;
import net.tassia.net.server.Client;
import net.tassia.net.server.Server;

public class ServerEvent extends Event {
	private final Server<? extends Client> server;

	public ServerEvent(Server<? extends Client> server) {
		this.server = server;
	}

	public Server<? extends Client> getServer() {
		return server;
	}

}

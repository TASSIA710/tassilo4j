package net.tassia.net.server.event;

import net.tassia.net.server.Client;
import net.tassia.net.server.Server;

public class ServerStartEvent extends ServerEvent {

	public ServerStartEvent(Server<? extends Client> server) {
		super(server);
	}

}

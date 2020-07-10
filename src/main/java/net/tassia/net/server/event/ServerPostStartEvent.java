package net.tassia.net.server.event;

import net.tassia.net.server.Client;
import net.tassia.net.server.Server;

public class ServerPostStartEvent extends ServerEvent {

	public ServerPostStartEvent(Server<? extends Client> server) {
		super(server);
	}

}

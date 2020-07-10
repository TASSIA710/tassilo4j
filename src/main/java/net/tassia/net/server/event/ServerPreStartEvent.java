package net.tassia.net.server.event;

import net.tassia.net.server.Client;
import net.tassia.net.server.Server;

public class ServerPreStartEvent extends ServerEvent {

	public ServerPreStartEvent(Server<? extends Client> server) {
		super(server);
	}

}

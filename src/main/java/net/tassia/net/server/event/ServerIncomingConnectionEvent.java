package net.tassia.net.server.event;

import net.tassia.net.server.Client;
import net.tassia.net.server.Server;

import java.net.InetSocketAddress;

public class ServerIncomingConnectionEvent extends ServerEvent {
	private final InetSocketAddress address;
	private boolean reject;

	public ServerIncomingConnectionEvent(Server<? extends Client> server, InetSocketAddress address) {
		super(server);
		this.address = address;
		this.reject = reject;
	}

	public InetSocketAddress getAddress() {
		return address;
	}

	public boolean getRejectConnection() {
		return reject;
	}

	public void setRejectConnection(boolean reject) {
		this.reject = reject;
	}

}

package net.tassia.net.server.light;

import net.tassia.net.server.event.ServerIncomingConnectionEvent;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;

class BouncerRunnable implements Runnable {
	private LightServer server;
	private int clientIDcounter;

	BouncerRunnable(LightServer server) {
		this.server = server;
		this.clientIDcounter = 1;
	}

	@Override
	public void run() {
		while (true) {
			// Accept the actual socket
			Socket s;
			try {
				s = server.getServerSocket().accept();
			} catch (SocketException ex) {
				ex.printStackTrace();
				return;
			} catch (IOException ex) {
				server.getLogger().log(Level.WARNING, "Error while accepting incoming socket.", ex);
				continue;
			}

			// Is the socket connecting via IP?
			if (!(s.getRemoteSocketAddress() instanceof InetSocketAddress)) {
				server.getLogger().warning("Received an incoming connection not using the IP protocol form " + s.getRemoteSocketAddress().toString());
				continue;
			}

			// Dispatch ServerIncomingConnectionEvent
			server.getLogger().info("Incoming connection from " + s.getInetAddress().getHostAddress());
			ServerIncomingConnectionEvent event = new ServerIncomingConnectionEvent(server, (InetSocketAddress) s.getRemoteSocketAddress());
			server.getEventManager().dispatchEvent(ServerIncomingConnectionEvent.class, event);

			// Reject the connection?
			if (event.getRejectConnection()) {
				try {
					s.close();
				} catch (IOException ex) {
					server.getLogger().log(Level.WARNING, "Error while closing incoming socket.", ex);
				}
				continue;
			}

			// Create the client
			LightClient client;
			try {
				client = new LightClient(s, clientIDcounter++);
			} catch (IOException ex) {
				server.getLogger().log(Level.WARNING, "Error while creating client.", ex);
				continue;
			}

			// Dispatch ServerClientConnectEvent

			// Dispatch ServerClientConnectedEvent
		}
	}

}

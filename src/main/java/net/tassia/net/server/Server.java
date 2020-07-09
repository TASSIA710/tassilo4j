package net.tassia.net.server;

import net.tassia.event.EventManager;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

public interface Server<C extends Client> {

	Logger getLogger();
	ExecutorService getExecutor();
	EventManager getEventManager();
	Collection<C> getClients();

	int getPort();
	InetAddress getBind();

	void start() throws IOException;
	void stop() throws IOException;
	boolean isReady();
	boolean isClosed();

}

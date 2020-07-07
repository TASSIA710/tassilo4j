package net.tassia.net.server;

import net.tassia.event.EventManager;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

public interface Server<C extends Client> {

	Logger getLogger();
	ExecutorService getExecutor();
	EventManager getEventManager();
	Collection<C> getClients();

	int getPort();

	void start();
	void stop();
	boolean isReady();
	boolean isClosed();

}

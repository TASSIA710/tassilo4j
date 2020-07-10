package net.tassia.net.server.light;

import net.tassia.event.Event;
import net.tassia.event.EventManager;
import net.tassia.net.server.Server;
import net.tassia.net.server.event.ServerPostStartEvent;
import net.tassia.net.server.event.ServerPreStartEvent;
import net.tassia.net.server.event.ServerStartEvent;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class LightServer implements Server<LightClient> {
	private final int port, backlog;
	private final InetAddress bind;
	private final Logger logger;
	private final ExecutorService executorService;
	private final EventManager eventManager;
	private final Collection<LightClient> clients;
	private volatile ServerSocket serverSocket;
	protected volatile Thread threadBouncer;

	public LightServer(int port) {
		this(port, 50);
	}

	public LightServer(int port, int backlog) {
		this(port, backlog, null);
	}

	public LightServer(int port, int backlog, InetAddress bind) {
		this.port = port;
		this.backlog = backlog;
		this.bind = bind;
		this.logger = Logger.getLogger(this.getClass().getSimpleName() + "@" + port);
		this.executorService = Executors.newCachedThreadPool();
		this.eventManager = new EventManager();
		this.clients = new ArrayList<>();
		this.serverSocket = null;
		this.threadBouncer = null;
	}



	@Override
	public int getPort() {
		return port;
	}

	@Override
	public InetAddress getBind() {
		return bind;
	}



	@Override
	public void start() throws IOException {
		logger.info("Starting...");

		// Dispatch ServerPreStartEvent
		eventManager.dispatchEvent(ServerPreStartEvent.class, new ServerPreStartEvent(this));

		// Create ServerSocket
		serverSocket = new ServerSocket(port, backlog, bind);

		// Dispatch ServerStartEvent
		eventManager.dispatchEvent(ServerStartEvent.class, new ServerStartEvent(this));

		// Create bouncer
		threadBouncer = new Thread(new BouncerRunnable(this), logger.getName() + "-Bouncer");
		threadBouncer.start();

		logger.info("Done! Listening on port " + getPort() + " for incoming connections.");
		if (getBind() != null) logger.info("Bound to: " + getBind().getHostAddress());

		// Dispatch ServerPostStartEvent
		eventManager.dispatchEvent(ServerPostStartEvent.class, new ServerPostStartEvent(this));
	}



	@Override
	public void stop() throws IOException {
		logger.info("Shutting down...");

		// Close ServerSocket
		serverSocket.close();

		// Interrupt bouncer
		threadBouncer.interrupt();

		logger.info("Good bye!");
	}



	@Override
	public boolean isReady() {
		return !isClosed();
	}

	@Override
	public boolean isClosed() {
		return serverSocket == null || serverSocket.isClosed();
	}



	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public ExecutorService getExecutor() {
		return executorService;
	}

	@Override
	public EventManager getEventManager() {
		return eventManager;
	}

	@Override
	public Collection<LightClient> getClients() {
		return clients;
	}

	protected ServerSocket getServerSocket() {
		return serverSocket;
	}

}

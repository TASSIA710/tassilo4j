package de.tassilo.net.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class StandardServer extends Server {
	protected ServerSocket socket;
	protected List<StandardClient> clients;
	protected List<ClientConnectedListener> clientConnectedListeners;
	protected List<ClientDisconnectedListener> clientDisconnectedListeners;
	protected List<ClientErrorListener> clientErrorListeners;
	protected ExecutorService socketListenerService;
	protected Logger logger;

	public StandardServer() {
		socket = null;
		clients = new ArrayList<StandardClient>();
		clientConnectedListeners = new ArrayList<ClientConnectedListener>();
		clientDisconnectedListeners = new ArrayList<ClientDisconnectedListener>();
		clientErrorListeners = new ArrayList<ClientErrorListener>();
		socketListenerService = null;

		logger = Logger.getLogger("Tassilo/Server");
		logger.setUseParentHandlers(false);
		logger.addHandler(new Handler() {
			@Override
			public void publish(LogRecord record) {
				StringBuilder sb = new StringBuilder();
				sb.append("[");
				sb.append(record.getLevel().getLocalizedName());
				sb.append("] ");
				sb.append(new SimpleDateFormat("hh:mm:ss.SSS").format(new Date(record.getMillis())));
				sb.append(": ");
				sb.append(record.getMessage());
				System.out.println(sb.toString());
			}
			@Override
			public void flush() {
				System.out.flush();
			}
			@Override
			public void close() throws SecurityException {
				// Don't close System.out
			}
		});
	}



	@Override
	public List<? extends Client> getClients() {
		return clients;
	}

	@Override
	public int getPort() {
		return isOpen() ? socket.getLocalPort() : -1;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}



	@Override
	public void addClientConnectedListener(ClientConnectedListener listener) {
		clientConnectedListeners.add(listener);
	}

	@Override
	public void removeClientConnectedListener(ClientConnectedListener listener) {
		clientConnectedListeners.remove(listener);
	}

	@Override
	public void removeClientConnectedListeners() {
		clientConnectedListeners.clear();
	}



	@Override
	public void addClientDisconnectedListener(ClientDisconnectedListener listener) {
		clientDisconnectedListeners.add(listener);
	}

	@Override
	public void removeClientDisconnectedListener(ClientDisconnectedListener listener) {
		clientDisconnectedListeners.remove(listener);
	}

	@Override
	public void removeClientDisconnectedListeners() {
		clientDisconnectedListeners.clear();
	}



	@Override
	public void addClientErrorListener(ClientErrorListener listener) {
		clientErrorListeners.add(listener);
	}

	@Override
	public void removeClientErrorListener(ClientErrorListener listener) {
		clientErrorListeners.remove(listener);
	}

	@Override
	public void removeClientErrorListeners() {
		clientErrorListeners.clear();
	}



	@Override
	public void open(int port) throws IOException {
		if (isOpen()) throw new IllegalStateException("Server is already open.");
		if (port < 0 || port > 65535) throw new IllegalArgumentException("Port must be within [0;65535]");
		logger.info("Opening server...");
		socket = new ServerSocket(port);
		clients = new ArrayList<StandardClient>();

		socketListenerService = Executors.newSingleThreadExecutor();
		socketListenerService.execute(new SocketListener(this));

		logger.info("Done! Listening on port " + getPort() + "!");
	}

	@Override
	public void close() throws IOException {
		if (!isOpen()) return;
		logger.info("Closing server listening on port " + getPort() + "...");

		logger.fine("Shutting down socket listener...");
		socketListenerService.shutdownNow();
		try {
			socketListenerService.awaitTermination(1, TimeUnit.SECONDS);
		} catch (InterruptedException ignored) {}
		socketListenerService = null;

		logger.fine("Shutting down client handlers...");

		logger.fine("Disconnecting clients...");
		for (Client c : getClients()) {
			c.disconnect();
		}
		clients = new ArrayList<StandardClient>();

		logger.fine("Closing server socket...");
		socket.close();

		logger.info("Server closed.");
	}

	@Override
	public void terminate() throws IOException {
		if (!isOpen()) return;
		logger.info("Terminating server listening on port " + getPort() + "...");

		logger.fine("Shutting down socket listener...");
		socketListenerService.shutdownNow();
		socketListenerService = null;

		logger.fine("Shutting down client handlers...");

		logger.fine("Closing server socket...");
		socket.close();

		logger.fine("Terminating remaining clients...");
		for (Client c : getClients()) {
			c.terminate();
		}
		clients = new ArrayList<StandardClient>();

		logger.info("Server terminated.");
	}

	@Override
	public boolean isOpen() {
		return socket != null && socket.isBound() && !socket.isClosed();
	}



	protected void addSocket(Socket clientSocket, int userID) throws IOException {
		StandardClient client = new StandardClient(this, clientSocket, userID);
		addClient(client);
	}

	protected void addClient(StandardClient client) {
		// Insert the client into our clients list
		clients.add(client);

		// Run the ClientConnected listeners
		for (ClientConnectedListener listener : clientConnectedListeners)
			listener.onClientConnected(client, this);

		logger.fine("Client " + client.getUserID() + " connected from " + client.getAddress().getHostAddress() + ":" + client.getPort());
	}



	private static class SocketListener implements Runnable {
		private volatile StandardServer server;
		private int userIDcounter;

		private SocketListener(StandardServer server) {
			this.server = server;
			this.userIDcounter = 1;
		}

		@Override
		public void run() {
			// Don't even start if the server isn't open
			if (!server.isOpen()) return;

			try {
				// Accept the socket and create a new client
				Socket clientSocket = server.socket.accept();
				server.addSocket(clientSocket, userIDcounter);
				userIDcounter++;

			} catch (SocketException ex) {
				// This is thrown if we close our server, so just stop the run here.
				return;

			} catch (IOException ex) {
				for (ClientErrorListener listener : server.clientErrorListeners)
					listener.onClientError(null, ex, server);
				return; // Stop the run here, because I'm scared we'll get an infinite loop of errors if we don't

			}

			// Accept the next socket
			run();
		}

	}

}

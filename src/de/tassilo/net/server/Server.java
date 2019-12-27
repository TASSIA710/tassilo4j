package de.tassilo.net.server;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public abstract class Server {
	
	public abstract List<? extends Client> getClients();
	public abstract int getPort();
	public abstract Logger getLogger();
	
	public abstract void addClientConnectedListener(ClientConnectedListener listener);
	public abstract void removeClientConnectedListener(ClientConnectedListener listener);
	public abstract void removeClientConnectedListeners();
	
	public abstract void addClientDisconnectedListener(ClientDisconnectedListener listener);
	public abstract void removeClientDisconnectedListener(ClientDisconnectedListener listener);
	public abstract void removeClientDisconnectedListeners();
	
	public abstract void addClientErrorListener(ClientErrorListener listener);
	public abstract void removeClientErrorListener(ClientErrorListener listener);
	public abstract void removeClientErrorListeners();
	
	public abstract void open(int port) throws IOException;
	public abstract void close() throws IOException;
	public abstract void terminate() throws IOException;
	public abstract boolean isOpen();
	
}
package de.tassilo.net.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class StandardClient extends Client {
	private StandardServer server;
	protected Socket socket;
	protected int userID;
	protected DataInputStream inputStream;
	protected DataOutputStream outputStream;
	
	public StandardClient(StandardServer server, Socket socket, int userID) throws IOException {
		this.server = server;
		this.socket = socket;
		this.userID = userID;
		this.inputStream = new DataInputStream(socket.getInputStream());
		this.outputStream = new DataOutputStream(socket.getOutputStream());
	}
	
	
	
	@Override
	public StandardServer getServer() {
		return server;
	}
	
	@Override
	public int getUserID() {
		return userID;
	}
	
	@Override
	public InetAddress getAddress() {
		return socket.getInetAddress();
	}
	
	@Override
	public int getPort() {
		return socket.getPort();
	}
	
	
	
	@Override
	public DataInputStream getInputStream() {
		return inputStream;
	}
	
	@Override
	public DataOutputStream getOutputStream() {
		return outputStream;
	}
	
	
	
	@Override
	public void disconnect() throws IOException {
		if (!isConnected()) return;
		socket.shutdownInput();
		socket.shutdownOutput();
		socket.close();
		for (ClientDisconnectedListener listener : server.clientDisconnectedListeners)
			listener.onClientDisconnected(this, server);
		getServer().clients.remove(this);
		getServer().logger.fine("Client " + getUserID() + " has been disconnected (closed).");
	}
	
	@Override
	public void terminate() throws IOException {
		if (!isConnected()) return;
		socket.close();
		for (ClientDisconnectedListener listener : server.clientDisconnectedListeners)
			listener.onClientDisconnected(this, server);
		getServer().clients.remove(this);
		getServer().logger.fine("Client " + getUserID() + " has been disconnected (terminated).");
	}
	
	@Override
	public boolean isConnected() {
		return socket != null && socket.isConnected() && socket.isBound() && !socket.isClosed();
	}
	
}
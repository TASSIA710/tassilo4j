package de.tassilo.net.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;

public abstract class Client {

	public abstract Server getServer();
	public abstract int getUserID();
	public abstract InetAddress getAddress();
	public abstract int getPort();
	
	public abstract DataInputStream getInputStream();
	public abstract DataOutputStream getOutputStream();
	
	public abstract void disconnect() throws IOException;
	public abstract void terminate() throws IOException;
	public abstract boolean isConnected();
	
}
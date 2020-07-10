package net.tassia.net.server;

import net.tassia.io.DataInputStream;
import net.tassia.io.DataOutputStream;

import java.io.IOException;
import java.net.InetSocketAddress;

public interface Client {

	DataInputStream getInputStream();
	DataOutputStream getOutputStream();

	int getClientID();
	InetSocketAddress getAddress();

	void disconnect() throws IOException;
	boolean isAvailable();

}

package net.tassia.net.server.light;

import net.tassia.io.DataInputStream;
import net.tassia.io.DataOutputStream;
import net.tassia.net.server.Client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class LightClient implements Client {
	protected final Socket socket;
	private final int clientID;
	private final DataInputStream inputStream;
	private final DataOutputStream outputStream;
	private final InetSocketAddress inetSocketAddress;

	protected LightClient(Socket socket, int clientID) throws IOException {
		this.socket = socket;
		this.clientID = clientID;
		this.inputStream = new DataInputStream(socket.getInputStream());
		this.outputStream = new DataOutputStream((socket.getOutputStream()));

		if (socket.getRemoteSocketAddress() instanceof InetSocketAddress) {
			this.inetSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
		} else {
			this.inetSocketAddress = null;
		}
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
	public int getClientID() {
		return clientID;
	}

	@Override
	public InetSocketAddress getAddress() {
		return inetSocketAddress;
	}



	@Override
	public void disconnect() throws IOException {
		socket.close();
	}

	@Override
	public boolean isAvailable() {
		return socket != null && socket.isConnected();
	}

}

package de.tassilo.net.server.http;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import de.tassilo.Tassilo;
import de.tassilo.net.server.ClientDisconnectedListener;
import de.tassilo.net.server.StandardServer;

public class HttpServer extends StandardServer {
	public static final int VERSION_MAJOR = 1;
	public static final int VERSION_MINOR = 0;
	public static final int VERSION_PATCH = 0;
	public static final int VERSION_BUILD = Tassilo.VERSION_BUILD;
	public static final String SERVER_NAME = "Tassilo / " + VERSION_MAJOR + "." + VERSION_MINOR + "." + VERSION_PATCH + "." + VERSION_BUILD;
	protected List<HttpRequestListener> httpRequestListeners;
	protected ExecutorService clientHandlingService;
	protected boolean reportClientErrors, reportServerErrors;
	
	public HttpServer() {
		super();
		httpRequestListeners = new ArrayList<HttpRequestListener>();
		clientHandlingService = null;
		reportClientErrors = false;
		reportServerErrors = true;
	}
	
	
	
	public boolean doReportClientErrors() {
		return reportClientErrors;
	}
	
	public void setReportClientErrors(boolean reportClientErrors) {
		this.reportClientErrors = reportClientErrors;
	}
	
	public boolean doReportServerErrors() {
		return reportServerErrors;
	}
	
	public void setReportServerErrors(boolean reportServerErrors) {
		this.reportServerErrors = reportServerErrors;
	}
	
	
	
	public void open() throws IOException {
		open(80);
	}
	
	@Override
	public void open(int port) throws IOException {
		super.open(80);
		clientHandlingService = Executors.newCachedThreadPool();
	}
	
	
	
	@Override
	public void close() throws IOException {
		clientHandlingService.shutdownNow();
		try {
			clientHandlingService.awaitTermination(1, TimeUnit.SECONDS);
		} catch (InterruptedException ignored) {}
		clientHandlingService = null;
		super.close();
	}
	
	@Override
	public void terminate() throws IOException {
		clientHandlingService.shutdownNow();
		clientHandlingService = null;
		super.terminate();
	}
	
	
	
	public void addHttpRequestListener(HttpRequestListener listener) {
		httpRequestListeners.add(listener);
	}
	
	public void removeHttpRequestListener(HttpRequestListener listener) {
		httpRequestListeners.remove(listener);
	}
	
	public void removeHttpRequestListeners() {
		httpRequestListeners.clear();
	}
	
	
	
	@Override
	protected void addSocket(Socket clientSocket, int userID) throws IOException {
		HttpClient client = new HttpClient(this, clientSocket, userID);
		addClient(client);
		clientHandlingService.execute(new ClientHandler(this, client));
	}
	
	
	
	private static class ClientHandler implements Runnable {
		private volatile HttpServer server;
		private volatile HttpClient client;
		
		private ClientHandler(HttpServer server, HttpClient client) {
			this.server = server;
			this.client = client;
		}
		
		@Override
		public void run() {
			try {
				HttpRequest request = HttpRequest.readHttpRequest(client.getInputStream());
				HttpResponse response = new HttpResponse(request.getVersion());
				
				server.logger.fine("Client " + client.getUserID() + " made request: " + request.getMethod().toString() + " " + request.getTarget() + " " + request.getVersion().toString());
				
				for (HttpRequestListener listener : server.httpRequestListeners)
					listener.onHttpRequest(request, response, client, server);
				
				client.sendResponse(response);
				client.getOutputStream().flush();
				client.disconnect();
				
			} catch (HttpException ex) {
				switch (ex.getErrorCode()) {
				
				case HttpException.ERROR_CODE_UNKNOWN_ERROR:
					ex.printStackTrace();
					break;
				
				case HttpException.ERROR_CODE_UNEXPECTED_END_OF_STREAM:
					// Client most-likely closed the connection
					for (ClientDisconnectedListener listener : server.clientDisconnectedListeners)
						listener.onClientDisconnected(client, server);
					server.clients.remove(client);
					server.logger.fine("Client " + client.getUserID() + " has disconnected.");
					break;
					
				case HttpException.ERROR_CODE_MALFORMED_STARTLINE:
					server.logger.warning("Client " + client.getUserID() + " performed a malformed request:\n" + ex.getMessage());
					break;
					
				case HttpException.ERROR_CODE_UNKNOWN_REQUEST_METHOD:
					server.logger.warning("Client " + client.getUserID() + " performed a used an unknown request method: " + ex.getMessage());
					break;
					
				case HttpException.ERROR_CODE_UNKNOWN_HTTP_VERSION:
					server.logger.warning("Client " + client.getUserID() + " used an unknown HTTP version: " + ex.getMessage());
					break;
					
				case HttpException.ERROR_CODE_MALFORMED_HEADER:
					server.logger.warning("Client " + client.getUserID() + " supplied a malformed header:\n" + ex.getMessage());
					break;
					
				case HttpException.ERROR_CODE_ILLEGAL_HEADER:
					server.logger.warning("Client " + client.getUserID() + " supplied an illegal header:\n" + ex.getMessage());
					break;
					
				case HttpException.ERROR_CODE_UNSUPPORTED_HTTP_VERSION:
					HttpResponse response = new HttpResponse(HttpVersion.HTTP_1_0);
					response.setStatus(HttpStatus.STATUS_505);
					try {
						client.sendResponse(response);
						client.getOutputStream().flush();
					} catch (IOException ex2) {
						ex2.printStackTrace();
					}
					try {
						client.disconnect();
					} catch (IOException ex2) {
						ex2.printStackTrace();
					}
					break;
					
				default:
					ex.printStackTrace();
					break;
				}
				
			} catch (SocketException ex) {
				// This is thrown if we close our server, ignore
				
			} catch (IOException ex) {
				ex.printStackTrace();
				
			}
		}
		
	}
	
}
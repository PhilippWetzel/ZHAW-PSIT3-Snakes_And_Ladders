package com.snakesAndLadders.network.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * ClientHandler for a simple server to handle all the connected clients.
 * There will be one instance of a cliendhandler for each client connected to the server.
 *
 * Handles incoming messages as well as sending them to the client
 *
 * Datum: 20.10.2017
 * @author jan
 * @version 0.0
 */
public class ClientHandler implements Runnable {
	private final static Logger logger = Logger.getLogger(ClientHandler.class.getName());
	private Socket clientSocket = null;
	private GameServer server = null;
	private InputStream in = null;
	private OutputStream out = null;
	private boolean running = true;
	private int id;

	public ClientHandler(GameServer server, Socket clntSocket){
		this.clientSocket = clntSocket;
		this.server = server;
		try {
			in = clientSocket.getInputStream();
			out = clientSocket.getOutputStream();
		} catch (IOException e) {
			logger.warning(e.getMessage());
		}
	}

	/**
	 * run Method of this Thread
	 *
	 * Listens for Messages from Clients
	 * handling of this messages will be done by the server
	 *
	 */
	@Override
	public void run() {
		try {
			byte[] echoBuffer = new byte[150]; // Receive Buffer
			int recvMsgSize = 0;
			do {
				if ((recvMsgSize = in.read(echoBuffer)) != -1) {
					server.handle(clientSocket.getPort(), echoBuffer);
				}
			} while (recvMsgSize != -1 && running);
			logger.info("Clienthandler will be closed");
			in.close();
			out.close();
			clientSocket.close();
		} catch (IOException e) {
			logger.warning(e.getMessage());
		}
	}

	public void closeHandler(){
		this.running = false;
	}

	public int getPortNumber(){
		return clientSocket.getPort();
	}

	/**
	 * Returns id for indentification
	 * @return id
	 */
	public int getId(){
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * sends messages to its client
	 * sends only messages for the client this instance is serving
	 * there is one instance per client in existence
	 *
	 * @param message command as String
	 */
	public void sendToClient(String message){
		try {
			OutputStream out = clientSocket.getOutputStream();
			out.write(message.getBytes());
			out.flush();

		} catch (IOException e) {
			logger.warning(e.getMessage());
		}
	}

}

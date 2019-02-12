package com.snakesAndLadders.network.client;

import com.snakesAndLadders.network.ClientNetworkException;

/**
 * Client-Program that tries to connect to a Simple Server The Client can only
 * send Messages to or recieve messages from a server
 *
 * Based on a chatserver-Client from PROG2
 *
 * ---------Version log --------------
 * 05.11.2017: Changed Client to not being a main-Class
 * 				New members and getter/setter implemented.
 *
 * @author jan
 * @version 0.1
 * @date 20.10.2017
 *
 */
public class Client {

	
	private String serverAdress = "";
	private ClientNetworkHandler networkHandler = null;

	public Client(String serverAdress, int port) {
		this.serverAdress = serverAdress;
		createNetworkHandler();
	}



	public ClientNetworkHandler getNetwortHandler(){
		return this.networkHandler;
	}

	/**
	 * starts it's networkHandler thread
	 * 
	 * @throws ClientNetworkException
	 */
	public void startNetworkHandler() throws ClientNetworkException {
		if (networkHandler != null) {
			new Thread(networkHandler,"NetworkHandler").start();
		} else {
			throw new ClientNetworkException("Error starting Networkhandler");
		}
	}

	private void setNetworkHandler(ClientNetworkHandler handler) {
		this.networkHandler = handler;
	}
	
	private void createNetworkHandler(){
		if(this.networkHandler == null){
			ClientNetworkHandler handler = new ClientNetworkHandler(serverAdress);
			setNetworkHandler(handler);
		}
	}

}
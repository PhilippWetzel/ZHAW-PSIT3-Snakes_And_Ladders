package com.snakesAndLadders.network;

/**
 * Exception that indicates errors regarding the Client.
 * More specifically setting up the Networkhandler of each Client.
 *
 * @author jan
 * @version 0.0
 * @date 06.11.2017
 */

public class ClientNetworkException extends Exception{

	private static final long serialVersionUID = 1L;

	public ClientNetworkException(String message){
		super(message);
	}
}

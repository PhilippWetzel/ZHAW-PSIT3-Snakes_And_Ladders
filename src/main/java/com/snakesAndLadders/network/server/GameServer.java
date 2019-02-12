package com.snakesAndLadders.network.server;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

import com.snakesAndLadders.game.Field;
import com.snakesAndLadders.game.Game;
import com.snakesAndLadders.game.Jumpfield;
import com.snakesAndLadders.game.Questionfield;

/**
 * ChatServer class that listens for Clients on a chosen Port.
 * The Server handles up to 10 Clients simultaneously and forwards all
 * Messages one Client sends to all the other Clients connected to the Server
 *
 * --------------Version Log -------------
 *
 * 15.11.2017 extended addThread method to make sure a player instance is created
 * 			  Player id (and color) must be given to the Clients
 *
 * 07.11.2017 handle-method extended to recognize game related commands.
 *
 * 17.10.2017 Simple Echo-Echo Server implemented (partly copied from PROG2 exercise) 
 *
 * @Date 17.10.2017
 * @author jan
 * @version 0.2
 *
 */

public class GameServer implements Runnable{
	private final static Logger logger = Logger.getLogger(GameServer.class.getName());
	private int expectedPlayers = 1;
	private ClientHandler[] clients = null;
	private int threadcount = 0;
	private boolean quit = false;
	private int serverPort = 40000;
	private ServerSocket servSock = null;
	private Game game = null;
	private String name = "";
	private String serverIP = "";

	public GameServer(int port, Game game){
		this.serverPort = port;
		try {
			serverIP = Inet4Address.getLocalHost().getHostAddress();
			this.expectedPlayers = game.getNumberOfPlayers();
			InetAddress addr = InetAddress.getByName(serverIP);
			this.servSock = new ServerSocket(serverPort,this.expectedPlayers,addr);
			clients = new ClientHandler[expectedPlayers];
			this.game = game;
		} catch (IOException e) {
			logger.warning("Error while setting up server " + e.getMessage());
		}
	}
	

	public GameServer(int port,String name, Game game){
		this(port,game);

		this.name = name;
	}

	public String getServerIP(){
		return serverIP;
	}

	@Override
	/**
	 * Run method of the server thread
	 * the server thread will constantly listen for clients to connect
	 * Creates a Clienthandler for every client and calls addThread method which
	 * will decide if the client can still join the game
	 * 
	 * 
	 */
	public void run() {
		while (!quit){
			Socket clntSock = null;
			try {
				logger.info("Listening " + this.servSock.getLocalSocketAddress().toString());
				clntSock = servSock.accept();
				logger.info("Accepted");
				ClientHandler c = new ClientHandler(this,clntSock);
				addThread(c);

			} catch (IOException e) {
				logger.warning(e.getMessage());
			} catch (Exception e) {
				try {
					clntSock.close();
				} catch (Exception e1) {
					logger.warning("Error while closing socket " +e1.getMessage());
				}
				logger.warning("Unexpected Error: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method handles the commands incoming from clients
	 * If a command word is recognized, it calls the corresponding methods of the game
	 *
	 * @param input
	 */
	public synchronized void handle(int portNumber, byte[] input){

		String command = new String(input);
		logger.info("GameServer - handle: " +command);
		command = command.substring(0, command.indexOf(";"));

		if(command.startsWith("NewTurn")){
			handleTurn(portNumber);
		}

		if(command.startsWith("AnswerToQuestion")){
			String[] tokens = command.split(",");
			String answer = "";
			if(tokens.length > 1){
				answer = tokens[1];
			}
			handleAnswerToQuestion(portNumber, answer);
		}

	}

	/**
	 * Method to stop the server thread.
	 * 
	 *This method closes all the existing handler-threads, then closes the Socked
	 *and then finally sets the quit flag for the server to break out of it's infinite loop
	 *
	 */
	public void quitServer(){
		resetServer();
		try {
			servSock.close();
		} catch (IOException e) {
			logger.warning(e.getMessage());
		}
		setQuitFlag();
	}
	
	
	public String getServerName(){
		return this.name;
	}

	private void sendtoClient(String message, int handlerId){
		ClientHandler clientHandler = getClientHandler(handlerId);
		clientHandler.sendToClient(message + ";");
		logger.info("sent to Client" + clientHandler.getId() + "Message: " + message);
	}

	private synchronized void sendtoAllClients(String message){
		for(int i = 0; i < getClientArray().length; i++){
			if(getClientArray()[i] != null){
				sendtoClient(message, getClientArray()[i].getId());
			}
		}
	}

	private void setQuitFlag(){
		this.quit = true;
	}

	/**
	 *
	 * Method to handle the players Turn
	 * this method only calls method of the class Game to start the processes needed
	 * After every important subprocess of the turn, the gameserver notifies all clients about changes.
	 *
	 *
	 */
	private void handleTurn(int portNumber){

		int playerId = getClientHandlerForPort(portNumber).getId();

		int number = game.makePlayerThrowDice(playerId);
		sendtoAllClients("ShowNumber," + playerId + "," + number);
		Field field = game.forwardPlayer(playerId, number);
		sendtoAllClients("MovePlayer," + playerId + "," + field.getNumber());


		if(field instanceof Questionfield){
			sendtoClient("Question," + ((Questionfield)field).getQuestion(), playerId);
			logger.info("QuestionField reached!");
		}else if(field instanceof Jumpfield){
			
			try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
			
			game.movePlayerTo(playerId, ((Jumpfield)field).getGoal().getNumber());
			sendtoAllClients("MovePlayer," + playerId + "," + ((Jumpfield)field).getGoal().getNumber());
			game.setNextPlayerActive();
			notifyNextPlayer();
		}else {
			game.setNextPlayerActive();
			notifyNextPlayer();
			logger.info("No special field reached");
		}

		if(!game.isExtended() && game.isPlayerFinished(playerId)){
			sendFinished(playerId);
		}

	}

	private void sendFinished(int playerId){
		sendtoAllClients("Finished," + playerId);
		resetServer();
	}

	private void resetServer() {
		for (int i = 0; i < getClientArray().length; i++){
			ClientHandler handler = getClientHandler(i);
			handler.closeHandler();
		}
		this.threadcount = 0;
		game.resetGame();
		logger.info("Reset Server, rejoin now possible");
	}

	/**
	 * 
	 * Validates the answer sent to the server by the client
	 * If the answer is incorrect, the player will be set back to his old position
	 * 
	 * @param portNumber
	 * @param answer
	 */
	private void handleAnswerToQuestion(int portNumber, String answer){

		int playerId = getClientHandlerForPort(portNumber).getId();
		if(game.checkPlayersAnswer(playerId, answer)){
			sendtoClient("PositiveAnswer", playerId);
			if(game.isPlayerFinished(playerId)){
				sendFinished(playerId);
			}

		} else {
			int fieldNr = game.setToOldPosition(playerId);
			sendtoAllClients("MovePlayer," + playerId + "," + fieldNr);
			sendtoClient("NegativeAnswer", playerId);
		}

		game.setNextPlayerActive();
		notifyNextPlayer();
	}

	private void notifyNextPlayer(){
		sendtoClient("YourTurn", game.getActivePlayerId());
		logger.info("Next Turn: Player" + game.getActivePlayerId());
	}

	/**
	 *
	 * Method to get Clienthandler for a given id
	 * Since the List of ClientHandlers is possibly accessed by multiple other threads
	 * this method must be synchronized
	 *
	 * @param id
	 * @return ClientHandler
	 */
	private synchronized ClientHandler getClientHandler(int id){

		for(int i = 0; i < getClientArray().length; i++){

			ClientHandler currentHandler = getClientArray()[i];
			if(currentHandler.getId() == id){
				return currentHandler;
			}
		}
		return null;
	}

	private synchronized ClientHandler getClientHandlerForPort(int portNr){

		for(int i = 0; i < getClientArray().length; i++){

			ClientHandler currentHandler = getClientArray()[i];
			if(currentHandler.getPortNumber() == portNr){
				return currentHandler;
			}
		}
		return null;
	}

	private ClientHandler[] getClientArray(){
		return this.clients;
	}

	private synchronized void addThread(ClientHandler clientHandler) throws Exception{
		if(threadcount < expectedPlayers){

			ClientHandler[] clients = getClientArray();
			clientHandler.setId(threadcount);
			clients[threadcount] = clientHandler; 
			Thread t = new Thread(clientHandler,"ClientHandler");
			t.start();
			
			sendPlayerInformation(clientHandler);
			
			logger.info("Added Clienthandler");
		}
		else {
			logger.warning("Max number of Threads exceeded");
			throw new Exception("Maximal number of clients exceeded");
		}
	}
	
	/**
	 * creates a playerInstance 
	 * sends the information to the player as well as to all the other players connected
	 * makes sure the player also knows all the other players connected
	 * (setup, basically)
	 * 
	 * 
	 * @param clientHandler
	 */
	private void sendPlayerInformation(ClientHandler clientHandler){
		int playerId = createPlayerForClient();

		if(getGame().isExtended()){
			sendtoClient("ExtendedMode", clientHandler.getId());
		}
		
		sendPlayersToNewClient(clientHandler);
		sendtoAllClients("NewPlayer," +playerId);
		sendtoClient("YouAre," + playerId, clientHandler.getId());
		threadcount++;
		if(expectedPlayers == threadcount){
			sendtoAllClients("GameReady");
			logger.info("Sent ready command");
			notifyNextPlayer();
		}
	}

	private void sendPlayersToNewClient(ClientHandler handler){
		for(int i = 0; i < handler.getId(); i++){
			sendtoClient("NewPlayer,"+getClientArray()[i].getId(), handler.getId());
		}
	}

	private int createPlayerForClient(){

		return this.getGame().createPlayer("Player");
	}

	private Game getGame(){
		return this.game;
	}

}

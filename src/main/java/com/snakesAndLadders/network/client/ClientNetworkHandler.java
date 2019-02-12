package com.snakesAndLadders.network.client;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Observable;
import java.util.logging.Logger;

/**
 * NetworkHandler for the simple Chat-Client.
 * This class establishes a connection to the server or closes the connection
 * and handles the communication between the two.
 *
 * Based on Prog2
 *
 * Datum: 17.10.2017
 * @author jan
 * @version 0.0
 */
public class ClientNetworkHandler extends Observable implements Runnable {
    private final static Logger logger = Logger.getLogger(ClientNetworkHandler.class.getName());
    private boolean running = true; // flag to stop thread
    private String message = "";    // Never modify yourself! use printMessage
    private String serverAddress;
    private int serverPort;
    private Socket socket = new Socket();


    public ClientNetworkHandler(String ip) {
        this.serverAddress = ip;
        this.serverPort = 40000 ;
    }

    /**
     *
     * Run Method of this Thread
     *
     * If a Connection is established successfully, this Thread will listen to new messages
     * as long as its running-flag is not set to zero
     *
     */
    public void run() {

        try {

            connect();
            logger.info("connected");
            InputStream in = getSocket().getInputStream();
            byte[] dataBuffer = new byte[200];

            while (running) {

                if(in.available() >= 1) {
                    if(in.read(dataBuffer) != -1){
                        String message = new String(dataBuffer,StandardCharsets.UTF_8.name());
                        String command = "";

                        for(int i = 0; i < message.length();i++){
                            char c = message.charAt(i);
                            command += c;
                            dataBuffer[i] = 0;

                            if(c == ';'){
                                setMessage(command);
                                command = "";
                            }
                        }
                    }
                }


            }
            disconnect(socket);
            logger.info("ClientHandler stopped");

        } catch(IOException e){
            logger.info(this.getServerAddress() + "::" + this.getServerPort());
            logger.warning(e.getMessage());
        }
    }

    /**
     * Method to stop the Thread.
     * The running flag of this Thread will be set to false and the Thread will be closed properly
     *
     */
    public void stop() {
        running = false;
    }

    /**
     * This method is called by the GUI to send a message
     *
     * @param message Game internal command as String
     */
    public synchronized void sendMessage(String message) {
        message = message + ";";
        try {
            if(getSocket().isConnected()){
                OutputStream out = getSocket().getOutputStream();
                byte[] dataBuffer = message.getBytes();

                out.write(dataBuffer);
                out.flush();
            }
        } catch(IOException e){
            logger.warning(e.getMessage());
        }
    }

    /**
     * public getter-Method for GUI
     * Makes Commands accessible for the GUI to change itself
     *
     * @return message
     */
    public synchronized String getMessage() {
        return message;
    }

    /* 
     * Call this to print a received message in the GUI text pane
     */
    private synchronized void setMessage(String message) {
        this.message = message;
        logger.info("#####recieved: " + message + " ######");
        setChanged();
        notifyObservers();
    }

    private void connect() throws IOException{
        InetSocketAddress endpoint  = new InetSocketAddress(getServerAddress(),getServerPort());
        getSocket().connect(endpoint);
    }

    private void disconnect(Socket socket){
        try {

            socket.shutdownOutput();

            socket.shutdownInput();
            socket.close();
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }

    }

    private String getServerAddress(){
        return this.serverAddress;
    }

    private int getServerPort(){
        return this.serverPort;
    }

    private Socket getSocket(){
        return this.socket;
    }

}

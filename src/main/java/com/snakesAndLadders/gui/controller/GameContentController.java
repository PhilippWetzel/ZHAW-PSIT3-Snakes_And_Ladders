package com.snakesAndLadders.gui.controller;

import com.snakesAndLadders.gui.GameContentPane;
import com.snakesAndLadders.network.client.ClientNetworkHandler;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

public class GameContentController implements Observer {

    private GameContentPane gui;
    private final static Logger logger = Logger.getLogger(GameContentController.class.getName());
    private ClientNetworkHandler handler;

    public GameContentController(GameContentPane pane, ClientNetworkHandler handler){
        gui = pane;
        this.handler = handler;
    }

    /**
     *
     * this method will be invoked whenever the clientNetworkHandler has
     * received a new Message. This Class acts as an Observer on the
     * networkhandler therefore will always be notified about changes.
     *
     * @param model observable
     * @param arg arguments
     * @author jan
     */
    public synchronized void update(Observable model, Object arg) {
        logger.info("called update");
        final String message = handler.getMessage();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (message != null) {

                    String extractedMessage = message.substring(0, message.indexOf(";"));
                    String[] token = null;

                    if(extractedMessage.startsWith("ExtendedMode")){
                    	gui.setExtendedMode(true);
                    	gui.changeGamefieldToQuestions();
                    }

                    if (extractedMessage.startsWith("NewPlayer")) {
                        gui.addNewPlayer(extractedMessage);
                    }

                    if(extractedMessage.startsWith("YouAre")){
                        token = splitMessage(extractedMessage);
                        if(token.length > 1){
                            gui.changeTitleToName(token[1]);
                        }
                    }

                    if (extractedMessage.equals("GameReady")) {
                        gui.setReadyFlag();
                        gui.piecesInit();
                        logger.info("recieved ready command");
                    }

                    if (extractedMessage.equals("YourTurn")) {
                        gui.setRollTheDiceBTNEnable(true);
                        logger.info("Got it, it's my turn");
                    }

                    if (extractedMessage.startsWith("ShowNumber")) {
                        gui.showNumberOfDice(extractedMessage);
                    }

                    if (extractedMessage.startsWith("MovePlayer")) {
                        gui.handleMovePlayerMessage(extractedMessage);
                    }

                    if (extractedMessage.startsWith("Question")) {
                        token = splitMessage(extractedMessage);
                        String answer = gui.askQuestion(token[1]);
                        handler.sendMessage("AnswerToQuestion," + answer);
                    }

                    if (extractedMessage.startsWith("NegativeAnswer")) {
                        gui.showResultOfQuestion(false);
                    }

                    if (extractedMessage.startsWith("PositiveAnswer")) {
                        gui.showResultOfQuestion(true);
                    }

                    if(extractedMessage.startsWith("Finished")){
                        token = splitMessage(extractedMessage);
                        String id = token[1];
                        gui.winInformation(id);
                        resetConnection();
                    }
                }
            }
        });
    }

    public String[] splitMessage(String message) {
        return message.split(",");
    }

    private void resetConnection() {
        handler.stop();
    }

    public void sendNewTurn() {
        handler.sendMessage("NewTurn");
    }

    public void shutdownHandler() {
        handler.sendMessage("Stopped");
        logger.warning("ShuttingDown Handler");
        handler.stop();
    }
}

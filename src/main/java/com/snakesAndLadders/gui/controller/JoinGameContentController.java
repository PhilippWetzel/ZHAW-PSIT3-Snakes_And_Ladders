package com.snakesAndLadders.gui.controller;

import com.snakesAndLadders.gui.GameContentPane;
import com.snakesAndLadders.gui.SnakesAndLaddersUI;
import com.snakesAndLadders.network.ClientNetworkException;
import com.snakesAndLadders.network.client.Client;

public class JoinGameContentController {

    public JoinGameContentController(){
    }

    public GameContentPane joingameAndGetNewPane(String serverIP, SnakesAndLaddersUI parentFrame) {
        Client client = new Client(serverIP, 40000);
        GameContentPane pane = new GameContentPane(parentFrame,  client.getNetwortHandler());
        try {
            client.startNetworkHandler();
        } catch (ClientNetworkException e) {
            e.printStackTrace();
        }
        return pane;
    }
}

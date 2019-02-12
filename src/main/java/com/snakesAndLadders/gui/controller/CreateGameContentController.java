package com.snakesAndLadders.gui.controller;

import com.snakesAndLadders.game.Game;
import com.snakesAndLadders.network.server.GameServer;
import com.snakesAndLadders.questionlist.QuestionList;

public class CreateGameContentController {

	static GameServer gameServer;
	
    public static void startServer(String name, boolean extendedMode, int playerNumber, QuestionList questionList) {
        Game game = new Game(questionList, playerNumber,extendedMode);
        gameServer = new GameServer(40000,name, game);

        Thread t = new Thread(gameServer,"ServerThread");
        t.start();
    }

	public String getServerIP() {
		return gameServer.getServerIP();
	}
}

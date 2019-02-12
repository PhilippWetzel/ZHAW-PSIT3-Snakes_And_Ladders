package com.snakesAndLadders.game;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class GameTest {
	private Game game;
	private int playerid;
	private int playerid2;
	private int playerid3;
	
	
	@Before
	public void setup(){
		game = new Game(null, 3,false);
		playerid = game.createPlayer("p1");
		playerid2 = game.createPlayer("p2");
		playerid3 = game.createPlayer("p3");
	}
	
	
	@Test
	public void createPlayer(){
		
		assertTrue(game.getNumberOfPlayers() == 3);
		assertTrue(game.getFieldOfPlayer(playerid).getNumber() == 1);
		assertTrue(game.getFieldOfPlayer(playerid2).getNumber() == 1);
		assertTrue(game.getFieldOfPlayer(playerid3).getNumber() == 1);
		
	}
	
	@Test
	public void testforwardPlayer(){
		game.forwardPlayer(playerid, 4);
		game.forwardPlayer(playerid2, 6);
		game.forwardPlayer(playerid3, 1);
		
		assertTrue(game.getFieldOfPlayer(playerid).getNumber() == 5);
		assertTrue(game.getFieldOfPlayer(playerid2).getNumber() == 7);
		assertTrue(game.getFieldOfPlayer(playerid3).getNumber() == 2);
		
		game.forwardPlayer(playerid, 2);
		game.forwardPlayer(playerid2, 3);
		game.forwardPlayer(playerid3, 4);
		
		assertTrue(game.getFieldOfPlayer(playerid).getNumber() == 7);
		assertTrue(game.getFieldOfPlayer(playerid2).getNumber() == 10);
		assertTrue(game.getFieldOfPlayer(playerid3).getNumber() == 6);
		
	}
	
	@Test
	public void testThrowDice(){
		game.movePlayerTo(playerid, 1);
		game.movePlayerTo(playerid2, 1);
		game.movePlayerTo(playerid3, 1);
		
		int numberp1 = game.makePlayerThrowDice(playerid);
		int numberp2 = game.makePlayerThrowDice(playerid);
		int numberp3 = game.makePlayerThrowDice(playerid);
		
		assertTrue((numberp1 <= 6) && (numberp1 > 0));
		assertTrue((numberp2 <= 6) && (numberp2 > 0));
		assertTrue((numberp3 <= 6) && (numberp3 > 0));
		
		game.forwardPlayer(playerid, numberp1);
		game.forwardPlayer(playerid2, numberp2);
		game.forwardPlayer(playerid3, numberp3);
		
		assertTrue(game.getFieldOfPlayer(playerid).getNumber() == (1+numberp1));
		assertTrue(game.getFieldOfPlayer(playerid2).getNumber() == (1+numberp2));
		assertTrue(game.getFieldOfPlayer(playerid3).getNumber() == (1+numberp3));
		
	}
	
	@Test
	public void resetPosition(){
		game.movePlayerTo(playerid, 1);
		game.movePlayerTo(playerid2, 1);
		game.movePlayerTo(playerid3, 1);
		
		game.forwardPlayer(playerid, 3);
		game.forwardPlayer(playerid2, 6);
		game.forwardPlayer(playerid3, 1);
		
		game.setToOldPosition(playerid);
		game.setToOldPosition(playerid2);
		game.setToOldPosition(playerid3);
		
		assertTrue(game.getFieldOfPlayer(playerid).getNumber() == 1);
		assertTrue(game.getFieldOfPlayer(playerid2).getNumber() == 1);
		assertTrue(game.getFieldOfPlayer(playerid3).getNumber() == 1);
	}
}

package com.snakesAndLadders.game;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
	private Player player;
	
	@Before
	public void setup(){
		player = new Player(1,"name","farbe");
	}
	
	@Test
	public void testMoving(){
		assertTrue(player.getFieldNr() == 1);
		player.move(3);
		assertTrue(player.getFieldNr() == 4);
		player.move(-2);
		assertTrue(player.getFieldNr() == 2);
		player.move(98);
		assertTrue(player.getFieldNr() == 100);
		player.move(-10);
		assertTrue(player.getFieldNr() == 90);
	}
	
	@Test
	public void testRollDie(){
		int upperLimitDie = 6;
		int lowerLimitDie = 1;
		for(int i = 0; i < 20; i++){
			player.rollDie();
			assertTrue(player.getDieNr() >= lowerLimitDie && player.getDieNr() <= upperLimitDie);
		}
	}
}

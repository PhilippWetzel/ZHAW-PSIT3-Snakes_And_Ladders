package com.snakesAndLadders.game;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.snakesAndLadders.questionlist.QuestionList;
import com.snakesAndLadders.questionlist.QuestionListLoader;

public class GameFieldTest {
	Gamefield gamefield = null;
	private static final String PATH = "C:/tmp";
	QuestionList questionlist = null;

	@Before
	public void setup(){
		
		String filename = "SchweizerGeschichte.csv";
		questionlist = QuestionListLoader.loadQuestionList(PATH+"/"+filename);
		this.gamefield = new Gamefield(GamefieldDetail.getSize(),questionlist,GamefieldDetail.getQuestionFieldList(), GamefieldDetail.getJumpFieldList());
	}
	
	@Test
	public void hasPositiveJumps(){
		assertTrue(gamefield.getFieldList()[8] instanceof Jumpfield);
		assertTrue(((Jumpfield)gamefield.getFieldList()[8]).getGoal().getNumber() == 49);
		assertTrue(gamefield.getFieldList()[36] instanceof Jumpfield);
		assertTrue(((Jumpfield)gamefield.getFieldList()[36]).getGoal().getNumber() == 64);
		assertTrue(gamefield.getFieldList()[55] instanceof Jumpfield);
		assertTrue(((Jumpfield)gamefield.getFieldList()[55]).getGoal().getNumber() == 76);
		
	}
	
	@Test
	public void hasNegativeJumps(){
		assertTrue(gamefield.getFieldList()[21] instanceof Jumpfield);
		assertTrue(((Jumpfield)gamefield.getFieldList()[21]).getGoal().getNumber() == 3);
		assertTrue(gamefield.getFieldList()[61] instanceof Jumpfield);
		assertTrue(((Jumpfield)gamefield.getFieldList()[61]).getGoal().getNumber() == 18);
		assertTrue(gamefield.getFieldList()[93] instanceof Jumpfield);
		assertTrue(((Jumpfield)gamefield.getFieldList()[93]).getGoal().getNumber() == 74);
		
	}
	
	@Test
	public void hasQuestionFields(){
		assertTrue(gamefield.getFieldList()[3] instanceof Questionfield);
		assertTrue(gamefield.getFieldList()[15] instanceof Questionfield);
		assertTrue(gamefield.getFieldList()[34] instanceof Questionfield);
		assertTrue(gamefield.getFieldList()[49] instanceof Questionfield);
		assertTrue(gamefield.getFieldList()[71] instanceof Questionfield);
	}
	
	@Test
	public void questionHasAnswer(){
		assertTrue(((Questionfield)gamefield.getFieldList()[3]).getQuestion() != null);
		assertTrue(((Questionfield)gamefield.getFieldList()[15]).getQuestion() != null);
		assertTrue(((Questionfield)gamefield.getFieldList()[34]).getQuestion() != null);
		assertTrue(((Questionfield)gamefield.getFieldList()[49]).getQuestion() != null);
		assertTrue(((Questionfield)gamefield.getFieldList()[71]).getQuestion() != null);
	}
	
	@Test
	public void jumpsOverFinishLine(){
		Game game = new Game(questionlist, 1, true);
		int playerId = game.createPlayer("P1");
		game.movePlayerTo(playerId, 98);
		game.forwardPlayer(playerId, 5);
		assertTrue(game.getFieldOfPlayer(playerId).getNumber() == 97);
		
		game.forwardPlayer(playerId, 4);
		assertTrue(game.getFieldOfPlayer(playerId).getNumber() == 99);
		
		game.forwardPlayer(playerId, 6);
		assertTrue(game.getFieldOfPlayer(playerId).getNumber() == 95);
	}
	

}

package com.snakesAndLadders.game;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.snakesAndLadders.questionlist.QuestionList;
import com.snakesAndLadders.util.ImgIcon;

public class Game {
	private final static Logger logger = Logger.getLogger(Game.class.getName());
	private Gamefield gamefield = null;
	private ArrayList<Player> playerList = new ArrayList<>();
	private int activePlayer = 0;
	private int numberOfPlayers = 0;
	private boolean extended = false;

	public Game(QuestionList questionList, int numberOfPlayers, boolean extended) {
		this.setNumberOfPlayers(numberOfPlayers);
		this.gamefield = new Gamefield(GamefieldDetail.getSize(),questionList,GamefieldDetail.getQuestionFieldList(), GamefieldDetail.getJumpFieldList());
		this.extended = extended;
	}

	/**
	 * Method to Create a new Player 
	 * returns the Players id when finished
	 *
	 * @return playerId
	 */
	public int createPlayer(String playerName){
		String colorCode = ImgIcon.getColorList().get(playerList.size());
		Player newPlayer = new Player(playerList.size(), playerName,colorCode );
		addPlayer(newPlayer);
		return newPlayer.getPlayerID();
	}


	/*two calls for number?*/

	/**
	 * Throws a dice for a player
	 * @param id playerid
	 * @return rolled number
	 */
	public int makePlayerThrowDice(int id){
		getPlayer(id).rollDie();
		return getPlayer(id).getDieNr();
	}

	/**
	 * Move a player forward
	 * @param playerId playerId
	 * @param numberOfFields number of fields to move forward
	 * @return new field of the player
	 */
	public Field forwardPlayer(int playerId, int numberOfFields){
		int playersField = getFieldOfPlayer(playerId).getNumber();

		if(playersField + numberOfFields > gamefield.getMaxFieldNumber()){
			numberOfFields = -((playersField + numberOfFields)%gamefield.getMaxFieldNumber()-(gamefield.getMaxFieldNumber()-playersField));
		}

		getPlayer(playerId).move(numberOfFields);
		return getFieldOfPlayer(playerId);
	}

	/**
	 * moves a player to a specific fieldnumber
	 * @param playerId playerid
	 * @param fieldNumber number of the field
	 */
	public void movePlayerTo(int playerId, int fieldNumber){
		if(fieldNumber > gamefield.getMaxFieldNumber()){
			return;
		}

		getPlayer(playerId).setFieldNr(fieldNumber);
	}

	/**
	 * get field on which the player is standing
	 * @param id playerid
	 * @return field
	 */
	public Field getFieldOfPlayer(int id){
		int fieldNr = getPlayer(id).getFieldNr();
		return gamefield.getSpecificField(fieldNr);
	}

	public int getActivePlayerId(){
		return this.activePlayer;
	}

	public void setNextPlayerActive(){
		activePlayer += 1;
		if(activePlayer == playerList.size()){
			activePlayer = 0;
		}
	}

	/**
	 * check if a player is finished
	 * @param pid playerid
	 * @return whether he is finished or not
	 */
	public boolean isPlayerFinished(int pid){
		Player p = getPlayer(pid);
		if(extended){			
			return p.getFieldNr() == 100 && p.isAnswerFinalQuestionCorrect();
		}
		return (p.getFieldNr() == 100);
	}

	public boolean isExtended(){
		return this.extended;
	}


	/**
	 * checks the answer of a player (if the field is a questionfield)
	 * @param id playerid
	 * @param answer answer of player
	 * @return if answered correct
	 */
	public boolean checkPlayersAnswer(int id, String answer){
		Player player = getPlayer(id);
		Field field = gamefield.getSpecificField(player.getFieldNr());

		if(field instanceof Questionfield){
			boolean isAnswerCorrect =  ((Questionfield)field).checkAnswer(answer);
			if (isAnswerCorrect && player.getFieldNr() == 100){
				player.setAnswerFinalQuestionCorrect(true);
			}
			return isAnswerCorrect;
		}else {
			logger.warning("Error, expected Questionfield!");
		}
		return true;
	}

	/**
	 * move a player to the old position
	 * @param id playerid
	 * @return new fieldID
	 */
	public int setToOldPosition(int id){
		Player player = getPlayer(id);
		movePlayerTo(id, player.getOldFieldNr());
		return player.getFieldNr();
	}

	public void resetGame(){
		playerList.clear();
	}

	private void addPlayer(Player player) {
		playerList.add(player);
	}

	private Player getPlayer(int id){
		for (int i = 0; i < playerList.size(); i++){
			Player currentPlayer = playerList.get(i);

			if(currentPlayer.getPlayerID() == id){
				return currentPlayer;
			}
		}
		return null;
	}

	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}

	private void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}
}

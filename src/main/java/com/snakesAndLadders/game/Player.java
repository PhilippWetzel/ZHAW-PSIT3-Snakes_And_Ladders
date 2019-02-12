package com.snakesAndLadders.game;

public class Player {

	private int playerID;
	private String playerName;
	private String pieceColor;

	private int fieldNr;
	private int oldFieldNr;

	private int dieNr;
	private boolean answerFinalQuestionCorrect = false; //Frage von Feld 100 korrekt beantwortet

	public Player(int playerID, String playerName, String pieceColor) {
		this.playerID = playerID;
		this.playerName = playerName;
		this.pieceColor = pieceColor;
		this.dieNr = 0;
		setFieldNr(1);
	}

	public int getPlayerID() {
		return playerID;
	}

	public String getPlayerName() {
		return playerName;
	}

	public String getPieceColor() {
		return pieceColor;
	}

	public int getFieldNr() {
		return fieldNr;
	}

	/**
	 * Set oldFieltNr = FieldNr and after FieldNr to new Nr
	 * @param fieldNr the fieldNr to set
	 */
	public void setFieldNr(int fieldNr) {
		this.oldFieldNr = this.fieldNr;
		this.fieldNr = fieldNr;
	}

	public int getOldFieldNr() {
		return oldFieldNr;
	}

	public int getDieNr() {
		return dieNr;
	}
	
	/**
	 * Change the FieldNr to new FieldNr = old FieldNr + numberOfFields
	 * @param numberOfFields
	 */
	public void move(int numberOfFields){
		setFieldNr(this.fieldNr + numberOfFields);
	}

	/**
	 * Rolls a die and saves the number
	 */
	public void rollDie(){
		double number = Math.random()*6.0;
		dieNr = (int) number + 1;
	}

	public boolean isAnswerFinalQuestionCorrect() {
		return answerFinalQuestionCorrect;
	}

	public void setAnswerFinalQuestionCorrect(boolean answerFinalQuestionCorrect) {
		this.answerFinalQuestionCorrect = answerFinalQuestionCorrect;
	}

}
package com.snakesAndLadders.gui;

import javax.swing.JLabel;

import com.snakesAndLadders.util.ImgIcon;

public class Piece {
	private int playerId = 0;
	private String playerName;
	private JLabel pieceLBL = new JLabel();
	private int fieldNr = 1;

	/**
	 * initialise the player with an ID, a Name and a Color
	 * @param playerID continuous number
	 * @param playerName
	 * @param playerColor color of the piece
	 */
	public Piece(int playerID, String playerName, String playerColor) {
		this.playerId = playerID;
		this.playerName =playerName;
		pieceLBL.setIcon(ImgIcon.getImageIconByColor(playerColor));
	}

	public int getPlayerId() {
		return playerId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public JLabel getPieceLBL() {
		return pieceLBL;
	}

	public int getFieldNr() {
		return fieldNr;
	}

	public void setFieldNr(int fieldNr) {
		this.fieldNr = fieldNr;
	}
}

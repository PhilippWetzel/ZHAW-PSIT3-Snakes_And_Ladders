package com.snakesAndLadders.game;

public class Jumpfield extends Field {

	private Field goal;

	public Jumpfield(int number, Field goal) {
		super(number);
		this.goal = goal;
	}

	public Field getGoal() {
		return goal;
	}
}

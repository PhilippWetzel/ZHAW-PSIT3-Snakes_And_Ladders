package com.snakesAndLadders.game;

public class Questionfield extends Field {

	private String question = "";
	private String answer = "";

	public Questionfield(int number, String question, String answer) {
		super(number);
		this.question = question;
		this.answer = answer;
	}

	public String getQuestion(){
		return this.question;
	}

	public boolean checkAnswer(String answer){
		return this.answer.equals(answer);
	}

}

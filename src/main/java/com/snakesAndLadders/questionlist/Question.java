package com.snakesAndLadders.questionlist;

public class Question {
	private String question;
	private String answer;
	private String hint;

	public Question(String question, String answer) {
		this.question = question;
		this.answer = answer;
	}

	public Question(String question, String answer, String hint) {
		this(question, answer);
		this.hint = hint;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

}

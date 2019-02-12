package com.snakesAndLadders.questionlist;

public enum QuestionListColumn {
	QUESTION(0, "Frage"), ANSWER(1, "Antwort"), HINT(2, "Hinweis");

	private int index;
	private String description;

	QuestionListColumn(int index, String bezeichnung) {
		this.index = index;
		this.description = bezeichnung;
	}

	public int getIndex() {
		return index;
	}

	public String getBezeichnung() {
		return description;
	}
}

package com.snakesAndLadders.questionlist;

import java.util.ArrayList;
import java.util.List;

public class QuestionList {
	private ArrayList<Question> questions;

	public QuestionList() {
		questions = new ArrayList<>();
	}

	public void addQuestion(Question question) {
		questions.add(question);
	}

	public List<Question> getQuestions() {
		return questions;
	}
}

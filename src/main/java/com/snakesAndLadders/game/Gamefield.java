package com.snakesAndLadders.game;

import java.util.List;

import com.snakesAndLadders.questionlist.Question;
import com.snakesAndLadders.questionlist.QuestionList;

public class Gamefield {

	private Field[] fieldList;
	
	/**
	 * Create a new Gamefield with the details of size, questionlist for questionfields and jumpfields
	 * For an example the data for the default field are in the class GamefieldDetail.java
	 * 
	 * The fields coud be type of Field.java (normalfield) or Jumpfield.java or Questionfield.java
	 * 
	 * @param size
	 * @param questionList
	 * @param questionFieldList
	 * @param jumpFieldList
	 */
	public Gamefield(int size, QuestionList questionList, int[] questionFieldList, int[][] jumpFieldList) {
		fieldList = new Field[size];
		for (int i = 0; i < size; i++) {
			fieldList[i] = new Field(i + 1);
		}
		if (questionList != null) {
			List<Question> questions = questionList.getQuestions();
			int numberOfQuestions = questions.size();
			for (int i = 0; i < questionFieldList.length; i++) {
				double randomNumber = Math.random() * numberOfQuestions;
				int number = (int) randomNumber;
				Question randomQuestion = questions.get(number);
				String question = randomQuestion.getQuestion();
				String answer = randomQuestion.getAnswer();
				fieldList[questionFieldList[i] - 1] = new Questionfield(questionFieldList[i], question, answer);
			}
		}
		for (int i = 0; i < jumpFieldList.length; i++) {
			fieldList[jumpFieldList[i][0] - 1] = new Jumpfield(jumpFieldList[i][0], fieldList[jumpFieldList[i][1] - 1]);
		}
	}

	public Field[] getFieldList() {
		return fieldList;
	}

	public Field getSpecificField(int fieldNumber) {
		return fieldList[fieldNumber - 1];
	}

	public int getMaxFieldNumber(){
		return fieldList.length;
	}
}

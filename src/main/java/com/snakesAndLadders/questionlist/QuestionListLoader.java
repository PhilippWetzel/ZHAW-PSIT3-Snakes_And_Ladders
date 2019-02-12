package com.snakesAndLadders.questionlist;

import java.util.ArrayList;

import com.snakesAndLadders.util.FileUtils;

public class QuestionListLoader {
	private static final String CSV_DELIMITER = ";";

	/**
	 * Loads a questionlist from a path into the system
	 * @param path path to questionlist
	 * @return questionlist object
	 */
	public static QuestionList loadQuestionList(String path) {
		ArrayList<String> lines = (ArrayList<String>) FileUtils.getFileContent(path);
		QuestionList questions = new QuestionList();
		boolean isRowTheTitle = true;
		for (String line : lines) {
			String[] properties = line.split(CSV_DELIMITER);
			Question question = new Question(properties[QuestionListColumn.QUESTION.getIndex()], properties[QuestionListColumn.ANSWER.getIndex()]);

			if (isRowTheTitle){
				//Title of CSV-File, ignore it
				isRowTheTitle = false;
			} else {
				questions.addQuestion(question);
			}
		}
		return questions;
	}

	/**
	 * creates a template of questionlist at given path
	 * @param path path for template
	 */
	public static void createTemplateList(String path) {
		String firstLine = "";
		ArrayList<String> content = new ArrayList<>();

		for (QuestionListColumn column : QuestionListColumn.values()) {
			firstLine += column.getBezeichnung() + CSV_DELIMITER;
		}
		content.add(firstLine);
		FileUtils.writeTextFile(path, "SnakesAndLaddersQuestionTemplate.csv", content);
	}

}

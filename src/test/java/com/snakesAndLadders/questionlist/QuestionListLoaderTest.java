package com.snakesAndLadders.questionlist;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

import com.snakesAndLadders.util.FileUtils;

public class QuestionListLoaderTest {
	private static final String PATH = "C:/tmp";
	
	@Test
	public void testLoadQuestionList(){
		String filename = "testCreateTemplate.csv";
		String question = "frage";
		String answer = "antwort";
		
		ArrayList<String> content = new ArrayList<>();
		content.add(question+";"+answer+";");
		FileUtils.writeTextFile(PATH,filename , content);
		
		QuestionList questionList = QuestionListLoader.loadQuestionList(PATH+"/"+filename);
		assertTrue(questionList.getQuestions().size() > 0);
		assertTrue(question.equals(questionList.getQuestions().get(0).getQuestion()));
		assertTrue(answer.equals(questionList.getQuestions().get(0).getAnswer()));
		File file = new File(PATH+"/"+filename);
		assertTrue(file.delete());
	}
	
	@Test
	public void testCreateTemplateList(){
		String filename = "SnakesAndLaddersQuestionTemplate.csv";
		File file = new File(PATH+"/"+filename);
		assertFalse(file.exists());
		QuestionListLoader.createTemplateList(PATH);
		assertTrue(file.exists());
		assertTrue(file.length() > 0);
		assertTrue(file.delete());
	}
}

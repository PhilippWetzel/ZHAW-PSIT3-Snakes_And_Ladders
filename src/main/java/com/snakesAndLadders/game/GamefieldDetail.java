package com.snakesAndLadders.game;

/**
 * This class defines a first gamefield 
 * with the size of 100,
 * with all the question fields according to GameField_01_question.png
 * with all jumpfields according to GameField_01_question.png
 * 
 * Any changes here have an impact on the gaming behavior of the class game
 * 
 * @author wetzel_philipp
 *
 */
public class GamefieldDetail {
	public static int getSize() {
		return 100;
	}
	
	/**
	 * 
	 * @return Predefined list of fields for which a question must be answered.
	 */
	public static int[] getQuestionFieldList() {
		int[] questionFieldList = { 4,6,11,16,21,28,35,39,45,50,54,58,61,68,72,78,82,88,95,100};
		return questionFieldList;
	}

	/**
	 * [startfield , targetfield]
	 * @return Predefined list of jumfields for which the player moved to a other target field
	 */
	public static int[][] getJumpFieldList() {
		int[][] jumpFieldList = {
				{ 9, 49 }
				, { 15, 47 }
				, { 25, 74 }
				, { 37, 64 }
				, { 48, 67 }
				, { 41, 79 }
				, { 56, 76 }
				, { 70, 92 }
				, { 77, 96 }
				, { 13, 8 }
				, { 22, 3 }
				, { 62, 18 }
				, { 73, 51 }
				, { 86, 65 }
				, { 94, 74 }
				, { 97, 85 }
		};
		
		return jumpFieldList;
	}
}

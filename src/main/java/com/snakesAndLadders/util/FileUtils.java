package com.snakesAndLadders.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Util functions that have do to something with files
 */
public class FileUtils {
	private final static Logger logger = Logger.getLogger(FileUtils.class.getName());

	/**
	 * loads content of file into a list
	 * @param path path to file
	 */
	public static List<String> getFileContent(String path){
		ArrayList<String> lines = new ArrayList<>();

		try(BufferedReader br = new BufferedReader(new FileReader(path))){
			String line = br.readLine();
			while(line != null){
				lines.add(line);
				line = br.readLine();
			}
		} catch (FileNotFoundException e) {
			logger.warning(e.getMessage());
		} catch (IOException e) {
			logger.warning(e.getMessage());
		}
		return lines;
	}

	/**
	 * writes a file with content at given path
	 */
	public static void writeTextFile(String path, String filename, List<String> content){
		try {
			PrintWriter writer = new PrintWriter(path+"/"+filename);
			for(String line: content){
				writer.print(line);
			}
			writer.close();
		} catch (FileNotFoundException e) {
			logger.warning(e.getMessage());
		}

	}
}

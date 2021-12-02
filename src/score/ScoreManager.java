package score;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class ScoreManager {
	
	private static ScoreManager scoreManager = new ScoreManager();
	
	public static ScoreManager getScoreManager() {
    	return scoreManager;
    }
	
	public ArrayList<Integer> getScoreList(){
		
		ArrayList<Integer> SCORE_LIST = new ArrayList<Integer>();
		
		try {
			File SCORE_FILE = new File("score.txt");
			SCORE_FILE.createNewFile();
			Scanner readFile = new Scanner(SCORE_FILE);
			while(readFile.hasNextLine()) {
				String data = readFile.nextLine();
				int score = Integer.parseInt(data);
				SCORE_LIST.add(score);
			}
			readFile.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		SCORE_LIST = trimScore(
					 fillScoreZeroes(SCORE_LIST)
					 );
		
		return SCORE_LIST;
		
	}
	
	public String getScoreString(){
		
		int i = 1;
		String text_string = "";
		
		try {
			File SCORE_FILE = new File("score.txt");
			SCORE_FILE.createNewFile();
			Scanner readFile = new Scanner(SCORE_FILE);
			while(i<=5) {
				String data = readFile.nextLine();
				int score = Integer.parseInt(data);
				text_string += String.format("%d.\t\t\t\t%d\n", i,score);
				i+=1;
			}
			readFile.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return text_string;
		
	}
	
	public ArrayList<Integer> fillScoreZeroes(ArrayList<Integer> score_list){
		while(score_list.size()<5) {
			score_list.add(0);
		}
		return score_list;
	}
	
	public ArrayList<Integer> trimScore(ArrayList<Integer> score_list){
		
		while(score_list.size()>5) {
			score_list.remove(score_list.size()-1);
		}
		
		return score_list;
	}
	
}

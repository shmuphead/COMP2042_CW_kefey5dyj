package score;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Score manager class handles the high score implementation and loading/saving the data in file
 * Score/score file will be trimmed down to highest 5 scores.
 * @author User
 *
 */

public class ScoreManager {
	
	private static ScoreManager scoreManager = new ScoreManager();
	private String PATH = "src/score/assets/score.txt";
	
	/**
	 * Allow access to the static instances of ScoreManager by other classes
	 * @return a global instance of scoreManager constructor 'scoreManager()'
	 */
	
	public static ScoreManager getScoreManager() {
    	return scoreManager;
    }
	
	/**
	 * Create or load a local savefile for high score list if exist within PATH and return an ArrayList of Integers
	 * @return high scores in form of ArrayList of Integer with 5 elements
	 */
	
	public ArrayList<Integer> getScoreList(){
		
		ArrayList<Integer> SCORE_LIST = new ArrayList<Integer>();
		
		try {
			File SCORE_FILE = new File(PATH);
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
	
	/**
	 * Create or load a local savefile for high score list if exist within PATH and return the a string of high score
	 * @return String containing readable list of high scores
	 */
	
	public String getScoreString(){
		
		int i=1;
		ArrayList<Integer> SCORE_LIST = getScoreList();
		String text_string = "";
	
		for(int j:SCORE_LIST) {
			text_string += String.format("%d.\t\t\t\t%d\n", i,j);
			i++;
		}
		return text_string;
		
	}
	
	/**
	 * A utility function that ensures the array used for the high scores if less than size of 5, be filled with '0' until size of 5
	 * @param score_list ArrayList of high scores which should be less than size of 5 and to be filled with zeroes
	 * @return ArrayList of high scores with size of 5 that partially/completely filled with zeroes.
	 */
	
	public ArrayList<Integer> fillScoreZeroes(ArrayList<Integer> score_list){
		while(score_list.size()<5) {
			score_list.add(0);
		}
		return score_list;
	}
	
	/**
	 * A utility function that check if the ArrayList for the high score larger than size of 5, trim it down until size of 5
	 * @param score_list ArrayList of high scores which will be checked and trimmed until it reached the size of 5.
	 * @return ArrayList of high scores with size of 5.
	 */
	
	public ArrayList<Integer> trimScore(ArrayList<Integer> score_list){
		
		while(score_list.size()>5) {
			score_list.remove(score_list.size()-1);
		}
		
		return score_list;
	}
	
	/**
	 * A function that write the elements of ArrayList of high scores into a local file on the PATH in descending order.
	 * @param SCORE_LIST ArrayList of high scores to be write into a local file on the PATH
	 */
	
	public void exportScore(ArrayList<Integer> SCORE_LIST) {
		
		Collections.sort(SCORE_LIST, Collections.reverseOrder());
    	SCORE_LIST = trimScore(SCORE_LIST);
    	
    	try {
    		File file = new File(PATH);
    		FileWriter fileWriter = new FileWriter(file,false);
    		for(int i:SCORE_LIST) {
        		fileWriter.write(String.valueOf(i)+"\n");
        	}
    		fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

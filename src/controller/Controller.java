package controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import score.ScoreManager;

public class Controller {
	
	public Button scoreButton;
	public Label scoreLabel;
	
	public void handleButtonClick() {
		ScoreManager scoreManager = ScoreManager.getScoreManager();
		String string = scoreManager.getScoreString();
		scoreLabel.setText(string);
	}
	
}

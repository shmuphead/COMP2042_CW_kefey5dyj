package controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import score.ScoreManager;

/**
 * Controller for the JavaFX Windows
 * @author kefey5dyj
 *
 */

public class Controller {
	
	public Button scoreButton;
	public Label scoreLabel;
	
	/**
	 * Handler for the JavaFX program when the 'Refresh' button is clicked, high scores will be displayed within the application.
	 */
	
	public void handleButtonClick() {
		ScoreManager scoreManager = ScoreManager.getScoreManager();
		String string = scoreManager.getScoreString();
		scoreLabel.setText(string);
	}
	
}

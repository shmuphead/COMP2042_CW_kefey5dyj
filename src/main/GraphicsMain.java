/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package main;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import game.GameFrame;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import score.ScoreManager;


public class GraphicsMain extends Application {

	private ScoreManager scoreManager = score.ScoreManager.getScoreManager();
	ArrayList<Integer> SCORE;
    Stage window;
    Button button;
	
	public static void main(String[] args){
        EventQueue.invokeLater(() -> new GameFrame().initialize());
        launch(args);
    }

	@Override
	public void start(Stage stage) throws Exception {
		
		Text text = new Text();
		
		for(int i=1;i<=5;i++) {
			// text_string = text_string + String.valueOf(SCORE.get(i-1)) + "\n";
		}
		
		text.setLayoutX(50);
		text.setLayoutY(10);
		
		
		text.setText("Testing");
		
		button = new javafx.scene.control.Button("Refresh");
		button.setLayoutX(200);
		button.setLayoutY(300);
		
		
		button.setOnAction(e->{
			String string = scoreManager.getScoreString();
			text.setText(string);		
		});
		
		Pane root = new Pane();
		root.getChildren().addAll(button,text);
		
		Scene scene = new Scene(root,400,400);
		stage.setScene(scene);
		stage.show();
	}

}

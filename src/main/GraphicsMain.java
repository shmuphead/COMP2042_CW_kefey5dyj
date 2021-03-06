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

import game.GameFrame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main Function which start the game and JavaFX program
 * @author kefey5dyj (Addition)
 *
 */


public class GraphicsMain extends Application {

	
	public static void main(String[] args){
        EventQueue.invokeLater(() -> new GameFrame().initialize());
        launch(args);
    }
	
	/**
	 * Start function used by JavaFX library at launch for the scoreboard.
	 */
	
	@Override
	public void start(Stage stage) throws Exception {
		
		Parent root = FXMLLoader.load(getClass().getResource("../controller/Layout.fxml"));
		
		Scene scene = new Scene(root,400,400);
		
		stage.setScene(scene);
		stage.show();
	}

}

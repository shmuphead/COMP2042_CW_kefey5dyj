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
package game;

import javax.swing.*;

import ball.Ball;
import brick.Brick;
import debug.DebugConsole;
import player.Player;
import wall.Wall;
import score.ScoreManager;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.util.ArrayList;


public class GameBoard extends JComponent implements KeyListener,MouseListener,MouseMotionListener {

    /**
	 * Addition - serialVersionUID added for error suppression.
	 */
	private static final long serialVersionUID = -7685494269022321959L;
	private static final String CONTINUE = "Continue";
    private static final String RESTART = "Restart";
    private static final String EXIT = "Exit";
    private static final String PAUSE = "Pause Menu";
    private static final int TEXT_SIZE = 30;
    private static final Color MENU_COLOR = new Color(0,255,0);
    
    /**
     * Addition - Counter for current level brick to use in high score calculation.
     */
    private static int CURRENT_LEVEL_BRICK_COUNT;
    /**
     * Addition - Current score.
     */
    private static int CURRENT_SCORE = 0;
    /**
     * Addition - ArrayList for high scores tracking.
     */
    private static ArrayList<Integer> SCORE_LIST = new ArrayList<Integer>();
    /**
     * Addition - Getting the global instance of ScoreManager class.
     */
    private static ScoreManager scoreManager = score.ScoreManager.getScoreManager();

    private static final int DEF_WIDTH = 600;
    private static final int DEF_HEIGHT = 450;

    private static final Color BG_COLOR = Color.WHITE;

    private Timer gameTimer;

    private Wall wall;

    private String message;

    private boolean showPauseMenu;

    private Font menuFont;

    private Rectangle continueButtonRect;
    private Rectangle exitButtonRect;
    private Rectangle restartButtonRect;
    private int strLen;

    private DebugConsole debugConsole;
    
    /**
     * Main Window for game
     * @param owner JFrame main window
     */
    public GameBoard(JFrame owner){
        super();

        strLen = 0;
        showPauseMenu = false;



        menuFont = new Font("Monospaced",Font.PLAIN,TEXT_SIZE);


        this.initialize();
        
        // Addition - Aggregation relationship with ScoreManager class.
        SCORE_LIST = scoreManager.getScoreList();
        
        message = "";
        wall = new Wall(new Rectangle(0,0,DEF_WIDTH,DEF_HEIGHT),30,3,6/2,new Point(300,430));

        debugConsole = new DebugConsole(owner,wall,this);
        
        // Initialize the first level and record the total brick count for current level.
        wall.nextLevel();
        setTotalBrick(wall.getBrickCount());
        
        // Previously directly accessed variables were changed to getter function equivalent.
        gameTimer = new Timer(10,e ->{
            wall.move();
            wall.findImpacts();
            message = String.format("Bricks: %d Balls %d",wall.getBrickCount(),wall.getBallCount());
            if(wall.isBallLost()){
                if(wall.ballEnd()){
                	setScore();
                	exportScore();
                    wall.wallReset();
                    message = "Game over";
                }
                wall.ballReset();
                gameTimer.stop();
            }
            else if(wall.isDone()){
                if(wall.hasLevel()){
                	setScore();
                    message = "Go to Next Level";
                    gameTimer.stop();
                    wall.ballReset();
                    wall.wallReset();
                    wall.nextLevel();
                    setTotalBrick(wall.getBrickCount());
                }
                else{
                	setScore();
                	exportScore();
                    message = "ALL WALLS DESTROYED";
                    gameTimer.stop();
                }
            }

            repaint();
        });

    }



    private void initialize(){
        this.setPreferredSize(new Dimension(DEF_WIDTH,DEF_HEIGHT));
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    
    /**
     * Responsible of drawing the screen/displaying content
     */
    
    public void paint(Graphics g){

        Graphics2D g2d = (Graphics2D) g;

        clear(g2d);

        g2d.setColor(Color.BLUE);
        g2d.drawString(message,250,225);
        
        // wall.ball is replaced by wall.getBall()

        drawBall(wall.getBall(),g2d);

        
        
        for(Brick b : wall.getBricks())
            if(!b.isBroken())
                drawBrick(b,g2d);

        drawPlayer(wall.getPlayer(),g2d);

        if(showPauseMenu)
            drawMenu(g2d);

        Toolkit.getDefaultToolkit().sync();
    }

    private void clear(Graphics2D g2d){
        Color tmp = g2d.getColor();
        g2d.setColor(BG_COLOR);
        g2d.fillRect(0,0,getWidth(),getHeight());
        g2d.setColor(tmp);
    }

    private void drawBrick(Brick brick,Graphics2D g2d){
        Color tmp = g2d.getColor();

        g2d.setColor(brick.getInnerColor());
        g2d.fill(brick.getBrick());

        g2d.setColor(brick.getBorderColor());
        g2d.draw(brick.getBrick());


        g2d.setColor(tmp);
    }

    private void drawBall(Ball ball,Graphics2D g2d){
        Color tmp = g2d.getColor();

        Shape s = ball.getBallFace();

        g2d.setColor(ball.getInnerColor());
        g2d.fill(s);

        g2d.setColor(ball.getBorderColor());
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    private void drawPlayer(Player p,Graphics2D g2d){
        Color tmp = g2d.getColor();

        Shape s = p.getPlayerFace();
        g2d.setColor(Player.INNER_COLOR);
        g2d.fill(s);

        g2d.setColor(Player.BORDER_COLOR);
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    private void drawMenu(Graphics2D g2d){
        obscureGameBoard(g2d);
        drawPauseMenu(g2d);
    }

    private void obscureGameBoard(Graphics2D g2d){

        Composite tmp = g2d.getComposite();
        Color tmpColor = g2d.getColor();

        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.55f);
        g2d.setComposite(ac);

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0,DEF_WIDTH,DEF_HEIGHT);

        g2d.setComposite(tmp);
        g2d.setColor(tmpColor);
    }

    private void drawPauseMenu(Graphics2D g2d){
        Font tmpFont = g2d.getFont();
        Color tmpColor = g2d.getColor();


        g2d.setFont(menuFont);
        g2d.setColor(MENU_COLOR);

        if(strLen == 0){
            FontRenderContext frc = g2d.getFontRenderContext();
            strLen = menuFont.getStringBounds(PAUSE,frc).getBounds().width;
        }

        int x = (this.getWidth() - strLen) / 2;
        int y = this.getHeight() / 10;

        g2d.drawString(PAUSE,x,y);

        x = this.getWidth() / 8;
        y = this.getHeight() / 4;


        if(continueButtonRect == null){
            FontRenderContext frc = g2d.getFontRenderContext();
            continueButtonRect = menuFont.getStringBounds(CONTINUE,frc).getBounds();
            continueButtonRect.setLocation(x,y-continueButtonRect.height);
        }

        g2d.drawString(CONTINUE,x,y);

        y *= 2;

        if(restartButtonRect == null){
            restartButtonRect = (Rectangle) continueButtonRect.clone();
            restartButtonRect.setLocation(x,y-restartButtonRect.height);
        }

        g2d.drawString(RESTART,x,y);

        y *= 3.0/2;

        if(exitButtonRect == null){
            exitButtonRect = (Rectangle) continueButtonRect.clone();
            exitButtonRect.setLocation(x,y-exitButtonRect.height);
        }

        g2d.drawString(EXIT,x,y);
        g2d.setFont(tmpFont);
        g2d.setColor(tmpColor);
    }
    
    /**
     * Key press action listener
     */
    
    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }
    
    /**
     * Key press (hold) action listener
     * for player panel movement and entering/exiting pause menu
     */
    
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()){
            case KeyEvent.VK_A:
                wall.getPlayer().moveLeft();
                break;
            case KeyEvent.VK_D:
                wall.getPlayer().moveRight();
                break;
            case KeyEvent.VK_ESCAPE:
                showPauseMenu = !showPauseMenu;
                repaint();
                gameTimer.stop();
                break;
            case KeyEvent.VK_SPACE:
                if(!showPauseMenu)
                    if(gameTimer.isRunning())
                        gameTimer.stop();
                    else
                        gameTimer.start();
                break;
            case KeyEvent.VK_F1:
                if(keyEvent.isAltDown() && keyEvent.isShiftDown())
                    debugConsole.setVisible(true);
            default:
                wall.getPlayer().stop();
        }
    }
    
    /**
     * Key released action listener
     */
    
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        wall.getPlayer().stop();
    }

    /**
     * Mouse clicking action listener for pause menu with corresponding reaction code for each buttons
     * (Continue, Restart, Exit)
     */
    
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(!showPauseMenu)
            return;
        if(continueButtonRect.contains(p)){
            showPauseMenu = false;
            repaint();
        }
        else if(restartButtonRect.contains(p)){
            message = "Restarting Game...";
            wall.ballReset();
            wall.wallReset();
            showPauseMenu = false;
            repaint();
        }
        else if(exitButtonRect.contains(p)){
            System.exit(0);
        }

    }
    
    /**
     * Mouse holding action listener
     */
    
    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }
    
    /**
     * Mouse releasing action listener
     */
    
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }
    
    /**
     * Mouse hover on windows action listener
     */
    
    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }
    
    /**
     * Mouse move out of windows action listener
     */
    
    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
    
    /**
     * Mouse dragging action listener
     */
    
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    /**
     * Changing mouse cursor/icon when hover into the menu buttons
     */
    
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(exitButtonRect != null && showPauseMenu) {
            if (exitButtonRect.contains(p) || continueButtonRect.contains(p) || restartButtonRect.contains(p))
                this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            else
                this.setCursor(Cursor.getDefaultCursor());
        }
        else{
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    /**
     * When game windows became inactive, pausing the game and display the "Focus Lost" message.
     */

    public void onLostFocus(){
        gameTimer.stop();
        message = "Focus Lost";
        repaint();
    }

    /**
     * Setter function for bricks counter which will be used for scoring evaluation.
     * @param brick Amount of the bricks remained within the game.
     */
    
    private void setTotalBrick(int brick) {
    	CURRENT_LEVEL_BRICK_COUNT = brick;
    }
    
    /**
     * Setter function for total score evaluation 
     */
    
    private void setScore() {
    	CURRENT_SCORE += CURRENT_LEVEL_BRICK_COUNT - wall.getBrickCount();
    	System.out.println(CURRENT_SCORE);
    }
    
    /**
     * Function to write the scores into the local file
     */
    
    private void exportScore() {
    	// Resetting the score before export
    	SCORE_LIST.add(CURRENT_SCORE);
    	CURRENT_SCORE = 0;
    	// End of Section
    	
    	scoreManager.exportScore(SCORE_LIST);
    }
}

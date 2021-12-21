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
package player;

import java.awt.*;

import ball.Ball;

/**
 * Unused libraries and minor typos are removed from the class
 * @author kefey5dyj
 *
 */

public class Player {


    public static final Color BORDER_COLOR = Color.GREEN.darker().darker();
    public static final Color INNER_COLOR = Color.GREEN;

    private static final int DEF_MOVE_AMOUNT = 5;

    private Rectangle playerFace;
    private Point ballPoint;
    private int moveAmount;
    private int min;
    private int max;

    /**
     * Player entity/class initialization
     * @param ballPoint Player coordinate which shared with ball's
     * @param width Width of the player panel
     * @param height Height of the player panel
     * @param container Layout of the player panel
     */
    public Player(Point ballPoint,int width,int height,Rectangle container) {
        this.ballPoint = ballPoint;
        moveAmount = 0;
        playerFace = makeRectangle(width, height);
        min = container.x + (width / 2);
        max = min + container.width - width;

    }

    private Rectangle makeRectangle(int width,int height){
        Point p = new Point((int)(ballPoint.getX() - (width / 2)),(int)ballPoint.getY());
        return  new Rectangle(p,new Dimension(width,height));
    }
    
    /**
     * Function for Ball-Player collision detection.
     * @param ball Originally 'b', changed for readability.
     * @return True value is returned if collision is detected.
     */
    public boolean impact(Ball ball){
        return playerFace.contains(ball.getPosition()) && playerFace.contains(ball.getBallDown()) ;
    }

    /**
     * Player move function
     */
    public void move(){
        double x = ballPoint.getX() + moveAmount;
        if(x < min || x > max)
            return;
        ballPoint.setLocation(x,ballPoint.getY());
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth()/2,ballPoint.y);
    }

    /**
     * Player left movement function
     */
    public void moveLeft(){
        moveAmount = -DEF_MOVE_AMOUNT;
    }

    /**
     * Maintenance - Typo fixed. (movRight -> moveRight)
     * Player right movement function
     */
    public void moveRight(){
        moveAmount = DEF_MOVE_AMOUNT;
    }

    /**
     * Player stop movement function
     */
    public void stop(){
        moveAmount = 0;
    }

    /**
     * Getter function for player Shape/layout
     * @return Player Shape/layout
     */
    public Shape getPlayerFace(){
        return  playerFace;
    }

    /**
     * Maintenance - Changed parameter (p -> position) for readability.
     * Setter function for both player and ball.
     * @param position coordinates of the player + ball
     */
    public void moveTo(Point position){
        ballPoint.setLocation(position);
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth()/2,ballPoint.y);
    }
}

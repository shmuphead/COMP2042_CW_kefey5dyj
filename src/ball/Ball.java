package ball;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;

/**
 * Created by filippo on 04/09/16.
 *
 */
abstract public class Ball {

    private Shape ballFace;

    private Point2D center;

    private Point2D up;
    private Point2D down;
    private Point2D left;
    private Point2D right;

    private Color border;
    private Color inner;
    
    private int speedX;
    private int speedY;
    
    /**
     * Addition - Collision counter for gradual increasing ball speed 
     */
    
    private int collision = 0;

    /**
     * Creating Ball Instance
     * Maintenance - Additional radius parameter was changed and edited (radiusA, radiusB -> radius)
     * @param center Initial position for the rubber ball in Point2D (Middle of the player panel)
     * @param radius Radius for the ball
     * @param inner Fill color for the ball
     * @param border Border color for the ball
     */
    public Ball(Point2D center,int radius,Color inner,Color border){
        this.center = center;

        up = new Point2D.Double();
        down = new Point2D.Double();
        left = new Point2D.Double();
        right = new Point2D.Double();

        up.setLocation(center.getX(),center.getY()-(radius / 2));
        down.setLocation(center.getX(),center.getY()+(radius / 2));

        left.setLocation(center.getX()-(radius /2),center.getY());
        right.setLocation(center.getX()+(radius /2),center.getY());


        ballFace = makeBall(center,radius,radius);
        this.border = border;
        this.inner  = inner;
        speedX = 0;
        speedY = 0;
    }

    protected abstract Shape makeBall(Point2D center,int radiusA,int radiusB);

    /**
     * Ball setter function for movement
     */
    
    public void move(){
        RectangularShape tmp = (RectangularShape) ballFace;
        center.setLocation((center.getX() + speedX),(center.getY() + speedY));
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((center.getX() -(w / 2)),(center.getY() - (h / 2)),w,h);
        setPoints(w,h);


        ballFace = tmp;
    }

    /**
     * Ball movement speed setter function
     * @param x X-axis speed value
     * @param y Y-axis speed value
     */
    
    public void setSpeed(int x,int y){
        speedX = x;
        speedY = y;
    }

    /**
     * Ball X-axis speed setter function used by debug panel
     * @param speedX Ball X-axis movement speed
     */
    
    public void setXSpeed(int speed){
        speedX = speed;
    }

    /**
     * Ball Y-axis speed setter function used by debug panel
     * @param speed Ball Y-axis movement speed
     */
    
    public void setYSpeed(int speed){
        speedY = speed;
    }

    /**
     * Setter function used when collection detected, reverse ball movement in X-axis
     * Addition - when collision count to 10, reset the collision counter and increase speed in X-axis by 1
     */
    
    public void reverseX(){
        speedX *= -1;
        collision++;
        if(collision==10) {
        	collision=0;
        	speedX += 1;
        }
    }

    /**
     * Setter function used when collision detected, reverse ball movement in Y-axis
     * Addition - when collision count to 10, reset the collision counter and increase speed in Y-axis by 1
     */
    
    public void reverseY(){
        speedY *= -1;
        collision++;
        if(collision==10) {
        	collision=0;
        	speedY += 1;
        }
    }

    /**
     * Getter function for border color
     * @return Border Color type variable
     */
    public Color getBorderColor(){
        return border;
    }

    /**
     * Getter function for fill color
     * @return Fill Color type variable
     */
    public Color getInnerColor(){
        return inner;
    }

    /**
     * Getter function for the ball position
     * @return Point2D coordinate for the ball
     */
    public Point2D getPosition(){
        return center;
    }

    /**
     * Getter function for ball layout
     * @return Shape data type for the ball
     */
    public Shape getBallFace(){
        return ballFace;
    }

    /**
     * Maintenance - Variable (w, h) edited to width, height to improve readability
     * Setter function for ball's initial position
     * @param position
     */
    
    public void moveTo(Point position){
        center.setLocation(position);

        RectangularShape tmp = (RectangularShape) ballFace;
        double width = tmp.getWidth();
        double height = tmp.getHeight();

        tmp.setFrame((center.getX() -(width / 2)),(center.getY() - (height / 2)),width,height);
        ballFace = tmp;
    }

    private void setPoints(double width,double height){
        up.setLocation(center.getX(),center.getY()-(height / 2));
        down.setLocation(center.getX(),center.getY()+(height / 2));

        left.setLocation(center.getX()-(width / 2),center.getY());
        right.setLocation(center.getX()+(width / 2),center.getY());
    }

    /**
     * Ball speed getter function in X-axis
     * @return Ball speed value in X-axis
     */
    
    public int getSpeedX(){
        return speedX;
    }
    
    /**
     * Ball speed getter function in Y-axis
     * @return Ball speed value in Y-axis
     */
    
    public int getSpeedY(){
        return speedY;
    }
    
    // Following are the addition of getter function to reinforce encapsulation
    /**
     * Addition - Getter function added for previously public Point2D variable 'down'
     * @return Ball's downward 2D directional value (Point2D)
     */
    
    public Point2D getBallDown() {
    	return down;
    }
    
    /**
     * Addition - Getter function added for previously public Point2D variable 'up'
     * @return Ball's upward 2D directional value (Point2D)
     */
    
    public Point2D getBallUp() {
    	return up;
    }
    
    /**
     * Addition - Getter function added for previously public Point2D variable 'left'
     * @return Ball's left 2D directional value (Point2D)
     */
    
    public Point2D getBallLeft() {
    	return left;
    }
    
    /**
     * Addition - Getter function added for previously public Point2D variable 'right'
     * @return Ball's right 2D directional value (Point2D)
     */
    
    public Point2D getBallRight() {
    	return right;
    }
}

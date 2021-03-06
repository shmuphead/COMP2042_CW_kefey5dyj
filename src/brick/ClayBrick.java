package brick;

import java.awt.*;
import java.awt.Point;
import java.awt.geom.Point2D;


/**
 * Created by filippo on 04/09/16.
 *
 */
public class ClayBrick extends Brick {

    private static final String NAME = "Clay Brick";
    private static final Color DEF_INNER = new Color(178, 34, 34).darker();
    private static final Color DEF_BORDER = Color.GRAY;
    private static final int CLAY_STRENGTH = 1;


    /**
     * Creating Clay Brick instance
     * @param point Initial position for brick instance
     * @param size Dimension/Size of the brick
     */
    public ClayBrick(Point point, Dimension size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,CLAY_STRENGTH);
    }

    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

    /**
     * Getter function for brick layout
     */
    @Override
    public Shape getBrick() {
        return super.brickFace;
    }


}

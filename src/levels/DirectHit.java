// ID 318646072
package levels;
import base.classes.Point;
import base.classes.Velocity;
import logic.LevelInformation;
import sprites.Block;
import sprites.DirectHitBackground;
import sprites.Sprite;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * class that holds the information of Direct Hit level.
 *
 * @author Asaf Mesilaty
 */
public class DirectHit implements LevelInformation {

    @Override
    public int numberOfBalls() {
        return 1;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        Velocity v = Velocity.fromAngleAndSpeed(360, 7);
        List<Velocity> velocities = new ArrayList<>();
        velocities.add(v);
        return velocities;
    }

    @Override
    public List<Point> initialBallCenters() {
        return null;
    }

    @Override
    public int paddleSpeed() {
        return 8;
    }

    @Override
    public int paddleWidth() {
        return 90;
    }

    @Override
    public String levelName() {
        return "Direct Hit";
    }

    @Override
    public Sprite getBackground() {
        return new DirectHitBackground();
    }

    @Override
    public List<Block> blocks() {
        List<Block> collisionBlocks = new ArrayList<>();
        Point upperLeft = new Point(386, 146);
        collisionBlocks.add(new Block(upperLeft, 30, 30, Color.RED));
        return collisionBlocks;

        }

    @Override
    public int numberOfBlocksToRemove() {
        return 1;
    }
}

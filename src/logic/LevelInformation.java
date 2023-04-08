// ID 318646072
package logic;
import base.classes.Point;
import base.classes.Velocity;
import sprites.Block;
import sprites.Sprite;
import java.util.List;

/**
 * A LevelInformation interface.
 *
 * @author Asaf Mesilaty
 */
public interface LevelInformation {

    /**
     * @return The number of balls that the player start with at the beginning of the level..
     * */
    int numberOfBalls();

    /**
     * @return The initial velocity of each ball.
     * */
    List<Velocity> initialBallVelocities();

    /**
     * @return The initial Centers points of each ball.
     * */
    List<Point> initialBallCenters();

    /**
     * @return The paddle speed.
     * */
    int paddleSpeed();

    /**
     * @return The paddle width
     * */
    int paddleWidth();

    /**
     * @return The level name that will be displayed at the top of the screen.
     * */
    String levelName();

    /**
     * @return a sprite with the background of the level.
     * */
    Sprite getBackground();

    /**
     * @return The Blocks that make up this level, each block contains its size, color and location.
     * */
    List<Block> blocks();

    /**
     * @return The number of blocks that should be removed before the level is considered to be "cleared".
     * This number should be <= blocks.size();
     * */
    int numberOfBlocksToRemove();
}

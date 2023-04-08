// ID 318646072
package levels;

import base.classes.Point;
import base.classes.Velocity;
import logic.LevelInformation;
import sprites.Block;
import sprites.Sprite;
import sprites.WideEasyAbstractSprite;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * class that holds the information of WideEasy level.
 *
 * @author Asaf Mesilaty
 */
public class WideEasy implements LevelInformation {
    private static final int BLOCKS_STARTING_X = 23;
    private static final int BLOCKS_Y_AXIS = 200;
    private static final int BLOCK_WIDTH = 50;
    private static final int BLOCK_HEIGHT = 25;

    @Override
    public int numberOfBalls() {
        return 10;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> velocities = new ArrayList<>();
        int angle = 310;
        for (int i = 0; i < 5; i++) {
            velocities.add(Velocity.fromAngleAndSpeed(angle, 6));
            angle += 10;
        }
        angle = 50;
        for (int i = 0; i < 5; i++) {
            velocities.add(Velocity.fromAngleAndSpeed(angle, 6));
            angle -= 10;
        }

        return velocities;
    }

    @Override
    public List<Point> initialBallCenters() {
        return null;
    }

    @Override
    public int paddleSpeed() {
        return 1;
    }

    @Override
    public int paddleWidth() {
        return 700;
    }

    @Override
    public String levelName() {
        return "Wide Easy";
    }

    @Override
    public Sprite getBackground() {
        return new WideEasyAbstractSprite();
    }

    @Override
    public List<Block> blocks() {
        List<Block> collisionBlocks = new ArrayList<>();
        int x = BLOCKS_STARTING_X;

        // Red blocks
        collisionBlocks.add(new Block(new Point(x, BLOCKS_Y_AXIS), (BLOCK_WIDTH + 2), BLOCK_HEIGHT, Color.RED));
        x += BLOCK_WIDTH + 2;
        collisionBlocks.add(new Block(new Point(x, BLOCKS_Y_AXIS), BLOCK_WIDTH, BLOCK_HEIGHT, Color.red));
        x += BLOCK_WIDTH;
        int n = 0;

        // Orange & Yellow
        Color[] colors = new Color[]{Color.ORANGE, Color.YELLOW};
        for (; (n / 2) < colors.length; n++) {
            collisionBlocks.add(new Block(new Point(x, BLOCKS_Y_AXIS), BLOCK_WIDTH, BLOCK_HEIGHT, colors[n / 2]));
            x += BLOCK_WIDTH;
        }

        // Green
        for (int i = 0; i < 3; i++) {
            collisionBlocks.add(new Block(new Point(x, BLOCKS_Y_AXIS), BLOCK_WIDTH, BLOCK_HEIGHT, Color.green));
            x += BLOCK_WIDTH;
        }
        n = 0;

        // Blue & Pink
        colors = new Color[]{Color.BLUE, Color.PINK};
        for (; (n / 2) < colors.length; n++) {
            collisionBlocks.add(new Block(new Point(x, BLOCKS_Y_AXIS), BLOCK_WIDTH, BLOCK_HEIGHT, colors[n / 2]));
            x += BLOCK_WIDTH;
        }

        // Cyan
        collisionBlocks.add(new Block(new Point(x, BLOCKS_Y_AXIS), BLOCK_WIDTH, BLOCK_HEIGHT, Color.cyan));
        x += BLOCK_WIDTH;
        collisionBlocks.add(new Block(new Point(x, BLOCKS_Y_AXIS), (BLOCK_WIDTH + 2), BLOCK_HEIGHT, Color.cyan));

        return collisionBlocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return 15;
    }
}

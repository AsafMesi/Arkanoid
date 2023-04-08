package levels;

import base.classes.Point;
import base.classes.Velocity;
import logic.LevelInformation;
import sprites.Block;
import sprites.Sprite;
import sprites.ThugMickeyBackground;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * class that holds the information of ThugMickey level.
 *
 * @author Asaf Mesilaty
 */
public class ThugMickey implements LevelInformation {
    private static final int BLOCK_WIDTH = 40;
    private static final int BLOCK_HEIGHT = 25;


    @Override
    public int numberOfBalls() {
        return 8;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> velocities = new ArrayList<>();
        int angle1 = 300;
        int angle2 = 330;
        int angle3 = 390;
        int angle4 = 420;
        for (int i = 0; i < 2; i++) {
            velocities.add(Velocity.fromAngleAndSpeed(angle1, 6));
            velocities.add(Velocity.fromAngleAndSpeed(angle2, 6));
            velocities.add(Velocity.fromAngleAndSpeed(angle3, 6));
            velocities.add(Velocity.fromAngleAndSpeed(angle4, 6));
        }


        return velocities;
    }

    @Override
    public List<Point> initialBallCenters() {
        List<Point> centers = new ArrayList<>();

        // adding centers in the ears.
        for (int i = 0; i < 4; i++) {
            centers.add(new Point(200, 140));
        }
        for (int i = 0; i < 4; i++) {
            centers.add(new Point(600, 140));
        }
        return centers;
    }

    @Override
    public int paddleSpeed() {
        return 8;
    }

    @Override
    public int paddleWidth() {
        return 120;
    }

    @Override
    public String levelName() {
        return "Thug Mickey";
    }

    @Override
    public Sprite getBackground() {
        return new ThugMickeyBackground();
    }

    @Override
    public List<Block> blocks() {
        List<Block> collisionBlocks = new ArrayList<>();

        //add mouth
        int n = 520;
        for (int y = 350; y < 400; y += BLOCK_HEIGHT) {
            for (int x = 280; x < n; x += BLOCK_WIDTH) {
                collisionBlocks.add(new Block(new Point(x, y), BLOCK_WIDTH, BLOCK_HEIGHT, Color.WHITE));
            }
        }

        //add earring - upsideDown block
        collisionBlocks.add(new Block(new Point(575, 200), BLOCK_HEIGHT,  BLOCK_WIDTH, Color.MAGENTA));
        collisionBlocks.add(new Block(new Point(600, 200), BLOCK_HEIGHT,  BLOCK_WIDTH, Color.MAGENTA));
        collisionBlocks.add(new Block(new Point(625, 200), BLOCK_HEIGHT,  BLOCK_WIDTH, Color.MAGENTA));

        return collisionBlocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return 13;
    }
}

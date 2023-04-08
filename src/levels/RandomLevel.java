// ID 318646072
package levels;

import base.classes.GeometryUtils;
import base.classes.Line;
import base.classes.Point;
import base.classes.Velocity;
import logic.GameEnvironment;
import logic.GameLevel;
import logic.LevelInformation;
import sprites.Block;
import sprites.RandomLevelBackground;
import sprites.Sprite;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * class that holds the information of RandomLevel level.
 *
 * @author Asaf Mesilaty
 */
public class RandomLevel implements LevelInformation {
    private static final int BLOCK_WIDTH = 50;
    private static final int BLOCK_HEIGHT = 30;
    private GeometryUtils gu = new GeometryUtils();
    private int numberOfBalls;
    private GameEnvironment ge = new GameEnvironment();

    /**
     * Construct random level - we work with random methods - we have to call each one of them only once.
     * */
    public RandomLevel() {
        this.numberOfBalls = this.gu.randInt(3, 20);
        this.setEnvironment();
    }

    @Override
    public int numberOfBalls() {
        return this.numberOfBalls;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> velocities = new ArrayList<>();
        for (int i = 0; i < this.numberOfBalls; i++) {

        // create a velocity with game speed and random angle - we want the ball to go up at start.
        int angle = gu.randInt(-80, 80);

        //create random speed.
        int speed = gu.randInt(3, 10);

        velocities.add(Velocity.fromAngleAndSpeed(angle, speed));
        }
        return velocities;
    }

    @Override
    public List<Point> initialBallCenters() {
        List<Point> centers = new ArrayList<>();

        for (int i = 0; i < this.numberOfBalls; i++) {

            // create random ball center
            centers.add(this.createRandomBallCenter());
        }
        return centers;
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
        return "Random Level";
    }

    @Override
    public Sprite getBackground() {

            return new RandomLevelBackground();
        }

    @Override
    public List<Block> blocks() {
        List<Block> collisionBlocks = new ArrayList<>();

        // game blocks colors.
        Color[] colors = new Color[] {Color.GREEN, Color.PINK, Color.BLUE, Color.YELLOW, Color.RED, Color.GRAY};
        int rightWall = GameLevel.RIGHT_WALL;

        // We start by adding green Blocks at the firstUpperLeft BaseClasses.Point.
        int firstUpperLeft = rightWall - 7 * BLOCK_WIDTH;
        int y =  GameLevel.HEIGHT /  2 - BLOCK_HEIGHT;
        for (Color color : colors) {
            for (int i = firstUpperLeft; i < rightWall; i += BLOCK_WIDTH) {
                collisionBlocks.add(new Block(i, y, color));
            }
            firstUpperLeft -= BLOCK_WIDTH;
            y -= BLOCK_HEIGHT;
        }

        return collisionBlocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return 57;
    }

    /**
     * copy the blocks to gameEnvironment var - in order to run some checks on the ball's centers.
     * */
    private void setEnvironment() {
        for (Block b : this.blocks()) {
            this.ge.addCollidable(b);
        }
    }

    /**
     * @return a game ball in random point inside the gui.
     * */
    public Point createRandomBallCenter() {
        Line insideTheGui = new Line(50, 50, 750, 550);
        return this.getClearPoint(insideTheGui);
    }

    /**
     * This method is working in recursion.
     * 1. generate a random point inside an area.
     * 1.1 if this point is in clear space - return it.
     * 2. else - if this point is inside a collidable object, the method calls herself again.
     * @param area the area in which we take a point.
     * @return a point in clear space inside the area.
     *
     * notice that this method is private because if the gui is totally full - the method will cause overflow.
     * */
    private Point getClearPoint(Line area) {
        Point p =  gu.randIntPoint(area);
        if (isClear(p)) {
            return p;
        } else {
            return getClearPoint(area);
        }
    }

    /**
     * This method goes throw the collidables and checks whether the point is in one of them or not.
     * @param p the point.
     * @return true if p is in clear space and false if p is inside any of the collidables.
     * */
    private boolean isClear(Point p) {
        return !this.ge.isInsideCollidable(p);
    }

}
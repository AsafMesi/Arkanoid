// ID 318646072
package logic;

import animations.Animation;
import animations.AnimationRunner;
import animations.CountdownAnimation;
import animations.KeyPressStoppableAnimation;
import animations.PauseScreen;
import base.classes.Point;
import base.classes.Velocity;
import base.classes.Counter;
import biuoop.KeyboardSensor;
import observers.HitListener;
import observers.BallRemover;
import observers.BlockRemover;
import observers.ScoreTrackingListener;
import biuoop.DrawSurface;
import sprites.Ball;
import sprites.Block;
import sprites.Paddle;
import sprites.ScoreIndicator;
import sprites.Sprite;
import sprites.SpriteCollection;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;

/**
 * A GameLevel class.
 *
 * @author Asaf Mesilaty
 */
public class GameLevel implements Animation {

    // The level's information.
    private LevelInformation levelInformation;
    private KeyboardSensor keyboardSensor;
    private Paddle paddle = null;

    // The gui size and boundaries.
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int LEFT_WALL = 22;
    public static final int RIGHT_WALL = 778; // 800-22

    // default ball radius.
    private static final int BALL_RADIUS = 6;

    // number of points the player get when getting to the next level.
    private static final int LEVEL_UP = 100;

    // sprites that would show up on the screen.
    private SpriteCollection sprites = new SpriteCollection();

    // mainly the game blocks collection.
    private GameEnvironment environment = new GameEnvironment();

    private AnimationRunner runner;
    private boolean running = false;

    // Counters to keep track on the player's status.
    private Counter remainingBlocks = new Counter();
    private Counter remainingBalls = new Counter();
    private Counter score;

    /**
     * a GameLevel constructor.
     * @param levelInformation the information for this level.
     * @param ks the game keyboard sensor.
     * @param ar the game animation runner.
     * @param score the score counter of the player.
     * */
    public GameLevel(LevelInformation levelInformation, KeyboardSensor ks, AnimationRunner ar, Counter score) {
        this.levelInformation = levelInformation;
        this.keyboardSensor = ks;
        this.runner = ar;
        this.score = score;
    }

    /**
     * Add a given collidable to the environment field.
     * @param c a Collidable.
     * */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Add a given Sprite to the SpriteCollection field.
     * @param s a Sprite.
     * */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * @return GameEnvironment of the game.
     * */
    public GameEnvironment getEnvironment() {
        return this.environment;
    }

    /**
     * @return paddle of the gameLevel.
     * */
    public Paddle getPaddle() {
        return this.paddle;
    }

    /**
     * This method creates 4 boundaries blocks and adds them to the gameLevel..
     * */
    public void addBoundaries() {
        HitListener ballRemover = new BallRemover(this, remainingBalls);
        int scoreSpace = 18;
        int size = LEFT_WALL;
        Block upperBound = new Block(new Point(0, scoreSpace), WIDTH, size, Color.GRAY);
        Block leftBound = new Block(new Point(0, (size + scoreSpace)), size, HEIGHT - size, Color.GRAY);
        Block rightBound = new Block(new Point(WIDTH - size, (size + scoreSpace)), size, HEIGHT - size, Color.GRAY);
        upperBound.addToGame(this);
        leftBound.addToGame(this);
        rightBound.addToGame(this);

        // This block is at the bottom of the screen, also called the death region.
        Block deathRegion = new Block(new Point(0, HEIGHT), WIDTH, 20, Color.BLUE);
        deathRegion.addHitListener(ballRemover);
        deathRegion.addToGame(this);
    }

    /**
     * Add the game paddle between the game boundaries.
     * */
    private void addPaddle() {
        double paddleSpeed = this.levelInformation.paddleSpeed();
        double width = this.levelInformation.paddleWidth();
        this.paddle = new Paddle(this.keyboardSensor, LEFT_WALL, RIGHT_WALL, paddleSpeed, width);
        this.paddle.addToGame(this);
    }

    /**
     * This method create balls on top of the paddle.
     * All the balls are white, we get their velocities from the level information.
     * */
    private void createBallsOnTopOfPaddle() {
        int x = (int) ((this.paddle.getA().getX() + this.paddle.getB().getX()) / 2);
        int y = (int) (this.paddle.getA().getY() - 3 * BALL_RADIUS);
        Point center = new Point(x, y);
        List<Velocity> velocities = this.levelInformation.initialBallVelocities();
        for (Velocity v: velocities) {
            Ball ball = new Ball(center, BALL_RADIUS, Color.WHITE, v);
            ball.addToGame(this);
        }
        this.remainingBalls.increase(this.levelInformation.numberOfBalls());
    }

    /**
     * This method create balls on the screen.
     * All the balls are white, we get their velocities and locations from the level information.
     * */
    private void createBalls() {
        List<Velocity> velocities = this.levelInformation.initialBallVelocities();
        List<Point> centers = this.levelInformation.initialBallCenters();

        // we want the go throw both list at the same time in the most efficient way.
        Iterator<Point> center = centers.iterator();
        Iterator<Velocity> v = velocities.iterator();
        while (center.hasNext() && v.hasNext()) {
            Ball ball = new Ball(center.next(), BALL_RADIUS, Color.WHITE, v.next());
            ball.addToGame(this);
        }

        this.remainingBalls.increase(this.levelInformation.numberOfBalls());
    }

    /**
     * Add blocks using addGameBlocks with the default block sizes.
     * */
    private void addGameBlocks() {
        HitListener blockRemover = new BlockRemover(this, this.remainingBlocks);
        HitListener scoreTrackingListener = new ScoreTrackingListener(this.score);
        List<Block> blocks = this.levelInformation.blocks();
        for (Block block : blocks) {
            block.addHitListener(blockRemover);
            block.addHitListener(scoreTrackingListener);
            block.addToGame(this);
        }
        this.remainingBlocks.increase(this.levelInformation.numberOfBlocksToRemove());
    }

    /**
     * Adds a background to the game.
     * */
    private void addBackground() {
        this.sprites.addSprite(this.levelInformation.getBackground());
    }

    /**
     * This method creates a score indicator and adds it to the game.
     * */
    private void addScoreIndicator() {
        ScoreIndicator scoreIndicator = new ScoreIndicator(score, new Point(300, 15));
        scoreIndicator.addToGame(this);
    }

    /**
     * Initialize a new game: create the Blocks and Ball (and Paddle) and add them to the game.
     * */
    public void initialize() {

        addBackground();

        // define boundaries blocks.
        addBoundaries();

        // define Paddle - we want it to be at the center and right above the bottom bound.
        addPaddle();

        // define GameLevel blocks.
        addGameBlocks();

        // define score indicator - this method must be last so the score will be shown on the draw surface.
        addScoreIndicator();

        // add game balls.
        if (this.levelInformation.initialBallCenters() == null) {
            createBallsOnTopOfPaddle();
        } else {
            createBalls();
        }
    }

    /**
     * Run the game -- start the animation loop.
     * */
    public void run() {

        // display countdown from 3 that lasts 2 seconds
        this.runner.run(new CountdownAnimation(2, 3, this.sprites));

        // this.createBallsOnTopOfPaddle(); // or a similar method
        this.running = true;

        // use our runner to run the current animation -- which is one turn of the game.
        this.runner.run(this);
        }

    /**
     * This method removes a given collidable from the game environment.
     * @param c a collidable to remove from this game.
     * */
     public void removeCollidable(Collidable c) {
         this.environment.removeCollidable(c);
        }

    /**
     * This method removes a given sprite from the Sprite Collection.
     * @param s a sprite to remove from this game.
     * */
     public void removeSprite(Sprite s) {
         this.sprites.removeSprite(s);
        }

    /**
     * @return true if the game is not running anymore.
     */
    public boolean shouldStop() {
            return !this.running; }

    @Override
    public void doOneFrame(DrawSurface d) {
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed();
        if (!gameIsOn()) {
            this.running = false;
        }
        if (this.keyboardSensor.isPressed("p")) {
            this.runner.run(
                    new KeyPressStoppableAnimation(this.keyboardSensor, KeyboardSensor.SPACE_KEY, new PauseScreen()));
        }
    }
    /**
     * This method will notify her caller if the game should be over.
     * @return true if the player can still play and false otherwise.
     * */
    public boolean gameIsOn() {

        if (this.remainingBlocks.getValue() == 0) {
            return false;
        }

        return this.remainingBalls.getValue() != 0;
    }

    /**
     * @return true if all the blocks that had to be destroyed has been destroyed.
     * */
    public boolean wonLevel() {
        if (this.remainingBlocks.getValue() == 0) {
            this.score.increase(LEVEL_UP);
            return true;
        }
        return false;
    }

    /**
     * @return true if all the balls has been removed due to Poor playing abilities of the player.
     * */
    public boolean lostLevel() {
        return this.remainingBalls.getValue() == 0;
    }

}

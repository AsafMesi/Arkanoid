// ID 318646072
package sprites;
import base.classes.Line;
import base.classes.Point;
import base.classes.Rectangle;
import base.classes.Velocity;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import logic.Collidable;
import logic.GameLevel;

import java.awt.Color;

/**
 * A Paddle class.
 *
 * @author Asaf Mesilaty
 */
public class Paddle implements Sprite, Collidable {
    public static final int DEFAULT_HEIGHT = 18;
    private KeyboardSensor keyboard;
    private double speed;
    private Rectangle paddle;
    private double leftWall;
    private double rightWall;


    /**
     * Construct a Paddle given parameters below.
     *
     * @param keyboard the gui KeyboardSensor that controls the paddle.
     * @param leftWall the left bound of the paddle.
     * @param rightWall the right bound of the paddle.
     * @param speed the distance (in pixels) that the paddle moves every key press.
     * @param width the width of the paddle.
     * @param height the height of the paddle.
     * @param color the color of the paddle.
     */
    public Paddle(KeyboardSensor keyboard, double leftWall, double rightWall, double speed,
                  double width, double height, Color color) {
        if (keyboard == null || color == null) {
            throw new RuntimeException("At new paddle: null pointer as been sent!");
        }
        double x = ((leftWall + rightWall - width) / 2.0);
        double y = (GameLevel.HEIGHT - 20 - height);
        this.keyboard = keyboard;
        this.leftWall = leftWall;
        this.rightWall = rightWall;
        this.speed = speed;
        this.paddle = new Rectangle(new Point(x, y), width, height, color);
    }

    /**
     * Construct a default Paddle given parameters below.
     * @param keyboard the gui KeyboardSensor that controls the paddle.
     * @param leftWall the left bound of the paddle.
     * @param rightWall the right bound of the paddle.
     * @param speed the distance (in pixels) that the paddle moves every key press.
     * @param width the paddle's width.
     * */
    public Paddle(KeyboardSensor keyboard, double leftWall, double rightWall, double speed, double width) {
        this(keyboard, leftWall, rightWall, speed, width, DEFAULT_HEIGHT, Color.YELLOW);
    }

    /**
     *  The paddle is a rectangle with A, B, C, D Vertices.
     * @return the A Vertex.
     * */
    public Point getA() {
        return this.paddle.getUpperLeft();
    }

    /**
     * @return the B Vertex.
     * */
    public Point getB() {
        return this.paddle.getUpperRight();
    }

    /**
     * @return the C Vertex.
     * */
    public Point getC() {
        return this.paddle.getDownLeft();
    }

    /**
     * @return the D Vertex.
     * */
    public Point getD() {
        return this.paddle.getDownRight();
    }

    /**
     * The left wall is a value that represent an X value on the cartesian system, which the paddle can't pass.
     * @return the left wall value.
     * */
    public double leftWall() {
        return this.leftWall;
    }

    /**
     * The right wall is a value that represent an X value on the cartesian system, which the paddle can't pass.
     * @return the right wall value.
     * */
    public double rightWall() {
        return this.rightWall;
    }

    /**
     * This method relocate the paddle speed pixels to the left.
     * */
    public void moveLeft() {

        // get the x, y values of the new location.
        double x = this.paddle.getUpperLeft().getX() - speed;
        double y = this.paddle.getUpperLeft().getY();

        // If the paddle is about to cross the left wall attach it to the wall.
        if (x < leftWall) {
            x = leftWall;
        }

        // update the paddle location.
        this.paddle = this.paddle.relocate(new Point(x, y));

    }

    /**
     * This method relocate the paddle speed pixels to the right.
     * */
    public void moveRight() {

        // get the x, y values of the new location.
        double x = this.paddle.getUpperLeft().getX() + speed;
        double y = this.paddle.getUpperLeft().getY();

        // If the paddle is about to cross the right wall attach it to the wall.
        if ((x + this.paddle.getWidth()) > rightWall) {
            x = rightWall - this.paddle.getWidth();
        }

        // update the paddle location.
        this.paddle = this.paddle.relocate(new Point(x, y));
    }

    @Override
    public void timePassed() {
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            this.moveLeft();
        }
        if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            this.moveRight();
        }
    }

    @Override
    public void drawOn(DrawSurface d) {
        this.paddle.drawOn(d);
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.paddle;
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {

        // The top line of the paddle construct different types of speed.
        if (collisionPoint.isOn(this.paddle.getTop())) {
            return topVelocityChange(this.paddle.getTop(), collisionPoint, currentVelocity);
        }

        // regular types of hit.
        if (collisionPoint.isOn(this.paddle.getRight())) {
            return (new Velocity(Math.abs(currentVelocity.getDx()), currentVelocity.getDy()));
        }
        if (collisionPoint.isOn(this.paddle.getLeft())) {
            return (new Velocity((-1) * Math.abs(currentVelocity.getDx()), currentVelocity.getDy()));
        }
        return currentVelocity;
    }

    /**
     * This method return different velocities in accordance to the collision point with the top of the paddle.
     * For every fifth of the line we define different angle.
     * The right fifth will create a velocity with angle of 60 (which is equivalent to 420).
     * The left fifth will create a velocity with angle of 300 and so on, as you see in the method.
     * @param top the top line of the paddle.
     * @param collisionPoint the collision point which is on "top".
     * @param currentV the velocity we want to adjust.
     * @return new BaseClasses.Velocity as noted in the explanation above.
     * */
    public Velocity topVelocityChange(Line top, Point collisionPoint, Velocity currentV) {
        double regStep = top.length() / 5.0;
        double region = top.end().getX() - regStep;
        double collisionX = collisionPoint.getX();
        int deg = 420;
        for (; deg >= 300; deg -= 30, region -= regStep) {
            if (region <= collisionX) {
                break;
            }
        }
        if (deg == 360) {
            return new Velocity(currentV.getDx(), (-1) * Math.abs(currentV.getDy()));
        }
        return Velocity.fromAngleAndSpeed(deg, currentV.getSpeed());
    }

    /**
     * This method adds the paddle to the gameLevel.
     * The paddle is a sprite and a collidable.
     * @param g the GameLevel we add the paddle to.
     * */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
}
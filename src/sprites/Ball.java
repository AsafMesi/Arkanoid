// ID 318646072
package sprites;
import base.classes.Line;
import base.classes.Point;
import base.classes.Velocity;
import biuoop.DrawSurface;
import logic.CollisionInfo;
import logic.GameLevel;
import logic.GameEnvironment;

import java.awt.Color;

/**
 * A Ball class.
 *
 * @author Asaf Mesilaty
 */
public class Ball implements Sprite {
    private Point center;
    private int r;
    private Color color;
    private Velocity v;
    private GameEnvironment gameEnvironment;
    private Paddle paddle;
    private double trajectoryLength;

    /**
     * Construct a Ball given parameters below.
     *
     * @param center the center point of the Ball.
     * @param r the Ball's radius.
     * @param color the color of the Ball.
     * @param v a velocity for the ball.
     *
     */
    public Ball(Point center, int r, Color color, Velocity v) {

        if (center == null || color == null || v == null) {
            throw new RuntimeException("At new Ball: Can't create Ball from null object!");
        }

        // check for valid radius
        if (r < 0) {
            System.out.println("At new Ball: can't create a ball with non positive value!");
            System.out.println("The radius has been set to zero.");
            this.r = 0;
        } else {
            this.r = r;
        }
        this.center = new Point(center.getX(), center.getY());
        this.color = color;
        this.v = new Velocity(v.getDx(), v.getDy());
        this.gameEnvironment = null;
        this.paddle = null;

        // Trajectory length is defined by the velocity and radius.
        this.setTrajectoryLength();
    }

    /**
     * This method define the Trajectory length.
     * It checks which is larger - radius or velocity speed, and multiply it by 2.
     * We want to know about Trajectory intersection one step before it happens.
     * */
    private void setTrajectoryLength() {
        this.trajectoryLength =  2 * Math.max(this.r, this.v.getSpeed());
    }

    /**
     * @return the x value of the ball's center point.
     * */
    public int getX() {
        return (int) Math.round(this.center.getX());
    }

    /**
     * @return the y value of the ball's center point.
     * */
    public int getY() {
        return (int) Math.round(this.center.getY());
    }

    /**
     * @return the ball's radius (which represent his size).
     * */
    public int getSize() {
        return this.r;
    }

    /**
     * @return the ball's color.
     * */
    public Color getColor() {
        return this.color;
    }

    /**
     * @return the ball's velocity..
     * */
    public Velocity getVelocity() {
        return this.v;
    }

    /**
     * @param radius is the new radius of the ball.
     * */
    public void setR(int radius) {
        this.r = radius;
        this.setTrajectoryLength();
    }

    /**
     * Set the ball's color to a given color.
     * @param c the given color.
     * */
    public void setColor(Color c) {
        this.color = c;
    }

    /**
     * @param newV is velocity object.
     * */
    public void setVelocity(Velocity newV) {
        this.v = new Velocity(newV.getDx(), newV.getDy());
        this.setTrajectoryLength();
    }

    /**
     * Set the game environment field.
     * @param ge the GameLevel environment object.
     * */
    public void setGameEnvironment(GameEnvironment ge) {
        this.gameEnvironment = ge;
    }

    /**
     * Set the paddle field.
     * @param p the paddle that the ball would hit.
     * */
    public void setPaddle(Paddle p) {
        this.paddle = p;
    }

    /**
     * This method adds the ball to the game.
     * In order for the ball to be part of the game we should define game environment and paddle.
     * In order for the ball to be shown on the game surface we should add him to the game sprite collection.
     * @param g the GameLevel object we add the ball to.
     * */
    public void addToGame(GameLevel g) {
        this.setGameEnvironment(g.getEnvironment());
        this.setPaddle(g.getPaddle());
        g.addSprite(this);
    }

    /**
     * This method is responsible of moving the ball. it spots a coming hit and change the ball's location and velocity.
     * First it checks if the ball is about to hit the paddle. hitting the paddle may be problematic.
     * Then it goes throw the game environment blocks and check if the ball is about to hit one of them.
     * */
    public void moveOneStep() {

        // The ball hits the side of the paddle - while the paddle is moving.
        if (sidePaddleHit(this.paddle)) {
            sideHitMove(this.paddle);
            verifyBoundaries(this.paddle);
            return;
        }

        // The trajectory length is two times the maximum length between speed and radius.
        Point endOfTrajectory = (Velocity.fromAngleAndSpeed(v.getAngle(), trajectoryLength).applyToPoint(center));
        Line trajectory = new Line(this.center, endOfTrajectory);

        // Check if the ball hits any of the game collidables.
        CollisionInfo collisionInfo = gameEnvironment.getClosestCollision(trajectory);

        // If the ball didn't hit any object, move it to the point where the velocity takes it.
        if (collisionInfo == null) {
            this.center = this.v.applyToPoint(this.center);
        } else {

            /* we want the ball to be radius away from the collision point. */
            /* partWay is the velocity that can take the collision point radius backwards on the trajectory line. */
            Velocity partWay = Velocity.fromAngleAndSpeed(this.v.getAngle(), (-1) * this.r);
            this.center = partWay.applyToPoint(collisionInfo.collisionPoint());

            // Now we adjust the velocity in accordance to the hitting point.
            this.v = collisionInfo.collisionObject().hit(this, collisionInfo.collisionPoint(), this.v);

        }
    }

    /**
     * @param pad the paddle we check collision with.
     * @return true if the ball hits the side of the paddle.
     * */
    private boolean sidePaddleHit(Paddle pad) {
        return pad.getCollisionRectangle().isInside(this.center);
    }

    /**
     * @param pad the moving paddle
     * This method is being called after a moving paddle hit.
     * The method change the ball's location and velocity, considering the hit point.
     * If the ball came from the left - he will be sent at 300 degrees away.
     * If the ball came from the right - he will be sent at 60 degrees away.
     * */
   private void sideHitMove(Paddle pad) {

       // AB is the paddle top rib, H is the ball's center.
       double ah = pad.getA().distance(this.center);
       double bh = pad.getB().distance(this.center);
       if (ah < bh) {

           // AH is smaller than BH means that the hit came from the left side.
           this.v = pad.hit(this, pad.getA(), this.v);
           this.center.setX(pad.getA().getX() - this.r);
       } else {

           // otherwise it came from the right.
           this.v = pad.hit(this, pad.getB(), this.v);
           this.center.setX(pad.getB().getX() + this.r);
       }
       this.center = this.v.applyToPoint(this.center);
   }

   /**
    * @param pad the boundaries of the current paddle.
    * If the ball is about to be pushed into a corner - the method elegantly corrects the situation.
    * */
    private void verifyBoundaries(Paddle pad) {

        // check if the edge of the ball hits the left / right bound. we call this method only in case of side hit.
        if (this.center.getX() - this.r <= pad.leftWall()) {
            this.center.setX(pad.leftWall() + this.r);
            this.center.setY(pad.getA().getY() - this.r);
        } else if (this.center.getX() + this.r >= pad.rightWall()) {
            this.center.setX(pad.rightWall() - this.r);
            this.center.setY(pad.getB().getY() - this.r);
        }
    }

    /**
     * Draw the ball on given DrawSurface object.
     * @param surface the surface to draw the ball on.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle(this.getX(), this.getY(), this.r);
        surface.setColor(Color.BLACK);
        surface.drawCircle(this.getX(), this.getY(), this.r);
    }

    @Override
    public void timePassed() {
        this.moveOneStep();
    }

    /**
     * @return A string that represents the Ball as follows:
     * "[Center: center, Radius: r, Color: color, BaseClasses.Velocity: v]".
     * I want the ball center to be printed as int.
     */
    public String toString() {
        return ("[Center: (" + this.getX() + ", " + this.getY() + ") " + ", Radius: " + this.r
                + ", Color: " + this.color.toString() + ", BaseClasses.Velocity: " + this.v + "]");
    }

    /**
     * This method gets a GameLevel object that contains this ball as a sprite,
     * and remove it from the gameLevel sprite collection.
     * @param gameLevel the GameLevel object.
     * notice that if the ball is not a member of the gameLevel's sprite collection, nothing will happen.
     * */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeSprite(this);
    }
}
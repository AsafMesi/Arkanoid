// ID 318646072
package logic;
import base.classes.Point;
import base.classes.Rectangle;
import base.classes.Velocity;
import sprites.Ball;

/**
 * A Collidable interface.
 *
 * @author Asaf Mesilaty
 */
public interface Collidable {

    /**
     * @return the "collision shape" of the object.
     * */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that we collided with it at collisionPoint with a given velocity.
     * @param hitter the Ball that hits the collision rectangle.
     * @param collisionPoint the point where the hitting object hit the collision rectangle.
     * @param currentVelocity the velocity of the hitting object.
     * @return new velocity expected after the hit (based on the force the object inflicted on us).
     * */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
// ID 318646072
package logic;
import base.classes.Point;

/**
 * A CollisionInfo class.
 *
 * @author Asaf Mesilaty
 */
public class CollisionInfo {
    private Collidable collisionObject;
    private Point collisionPoint;

    /**
     * Construct a CollisionInfo object given parameters below.
     * @param collisionObject The Collidable that has been hit.
     * @param collisionPoint The point on the collisionObject scope where the collision occurred.
     * */
    public CollisionInfo(Collidable collisionObject, Point collisionPoint) {
        if (collisionObject == null || collisionPoint == null) {
            throw new RuntimeException("At new CollisionInfo: got a null pointer!");
        }
        this.collisionObject = collisionObject;
        this.collisionPoint = collisionPoint;
    }

    /**
     * @return the point at which the collision occurs.
     * */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * @return the collidable object involved in the collision.
     * */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}
// ID 318646072
package logic;
import base.classes.Line;
import base.classes.Point;

import java.util.LinkedList;

/**
 * A GameEnvironment class.
 *
 * @author Asaf Mesilaty
 */
public class GameEnvironment {
    private LinkedList<Collidable> collidables;

    /**
     * Construct GameEnvironment object.
     * the method create new collidables Linked List.
     * */
    public GameEnvironment() {
        this.collidables = new LinkedList<>();
    }

    /**
     * @return the number of collidables in the list.
     * */
    public int getSize() {
        return this.collidables.size();
    }

    /**
     * Add a given collidable to the environment.
     * @param c a Collidable.
     * */
    public void addCollidable(Collidable c) {
        if (c == null) {
            System.out.println("c is null");
            return;
        }
        collidables.add(c);
    }

    /**
     * remove a given collidable from the environment.
     * @param c a Collidable.
     * */
    public void removeCollidable(Collidable c) {
        if (c == null) {
            System.out.println("c is null");
            return;
        }
        if (!this.collidables.remove(c)) {
            System.out.println("At remove collidable: The collidable is not found");
        }
    }

    /**
     * Assume an object moving from line.start() to line.end().
     * If this object will not collide with any of the collidables in this collection, return null.
     * Else, return the information about the closest collision that is going to occur.
     * @param trajectory is the line we talked about in the above explanation.
     * @return CollisionInfo of the closest object the trajectory collide with.
     * */
    public CollisionInfo getClosestCollision(Line trajectory) {
        Collidable closest = null;
        Point collision = null;
        double minDistance = trajectory.length() + 1;
        for (Collidable c: collidables) {

            // closest collision of the object c and the BaseClasses.Line trajectory.
           Point currentCollision = trajectory.closestIntersectionToStartOfLine(c.getCollisionRectangle());

           // if there is a collision - check if it is closer to the last we got.
           if (currentCollision != null) {
                if (trajectory.first().distance(currentCollision) < minDistance) {
                    minDistance = trajectory.first().distance(currentCollision);
                    collision = currentCollision;
                    closest = c;
                }
           }
        }
        if (closest == null) {
            return null;
        }
        return new CollisionInfo(closest, collision);
    }

    /**
     * @param p a point.
     * @return true if p is inside any of the collidables.
     * */
    public boolean isInsideCollidable(Point p) {
        for (Collidable c: collidables) {
            if (c.getCollisionRectangle().isInside(p)) {
                return true;
            }
        }
        return false;
    }
}
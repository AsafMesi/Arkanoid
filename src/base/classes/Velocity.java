// ID 318646072
package base.classes;

/**
 * A Velocity class.
 *
 * @author Asaf Mesilaty
 */
public class Velocity {
    private static final double EPSILON = Math.pow(10, -7);
    private double dx;
    private double dy;

    /**
     * Construct a velocity given dx and dy values.
     * @param dx the dx value.
     * @param dy the dy value.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Construct a new BaseClasses.Velocity with default values (0, 0).
     * */
    public Velocity() {
        this(0, 0);
    }

    /**
     * @return the dx value.
     * */
    public double getDx() {
        return this.dx;
    }

    /**
     * @return the dy value.
     * */
    public double getDy() {
        return this.dy;
    }

    /**
     * @param newDx the dx value to set for the velocity.
     * */
    public void setDx(double newDx) {
        this.dx = newDx;
    }

    /**
     @param newDy the dy value to set for the velocity.
     * */
    public void setDy(double newDy) {
        this.dy = newDy;
    }

    /**
     * @param p a point with (x, y) values.
     * @return a new point with (x+dx, y+dy) values.
     * */
    public Point applyToPoint(Point p) {
        if (p == null) {
            System.err.println("applyToPoint got a null point to apply");
            return null;
        }
        return new Point((p.getX() + this.dx), (p.getY() + this.dy));
    }

    /**
     * This method create a new BaseClasses.Velocity object given an angle and speed.
     * Since the Cartesian coordinate system here is upside down - we put (-1) on the cos calculation.
     * @param angle an angle which will determine the direction.
     * @param speed the length at that direction.
     * @return new velocity with dx and dy from angle and speed.
     * */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        angle = Math.toRadians(angle);
        double dx = speed * Math.sin(angle);
        double dy = (-1) * speed * Math.cos(angle);
        if (Math.abs(dx - Math.round(dx)) < EPSILON) {
            dx = Math.round(dx);
        }
        if (Math.abs(dy - Math.round(dy)) < EPSILON) {
            dy = Math.round(dy);
        }
        return new Velocity(dx, dy);
    }

    /**
     * @return the angle that the velocity's angle.
     * */
    public double getAngle() {

        //  arc tan calc the angle from the x-axis.
        double angle = (90 + (Math.toDegrees(Math.atan2(this.dy, this.dx)))) % 360;

        // I want the angle to be positive.
        while (0 > angle) {
            angle += 360;
        }
        return angle;
    }

    /**
     * @return the distance a point goes after applying on her the velocity change.
     * */
    public double getSpeed() {

        // use Pythagoras in order to get the speed.
        double a = Math.abs(this.dx);
        double b = Math.abs(this.dy);
        return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }

    /**
     * @param v a velocity to compare the dx and dy values.
     * @return true if the velocities are equal and false otherwise.
     */
    public boolean equals(Velocity v) {
        return ((Math.abs(this.dx - v.getDx()) < EPSILON) && (Math.abs(this.dy) - v.getDy() < EPSILON));
    }

    /**
     * @return A string that represents the velocity as follows: "[Dx: dx, Dy: dy]".
     */
    public String toString() {
        return ("[Dx: " + this.dx + ", Dy: " + this.dy + "]");
    }
}
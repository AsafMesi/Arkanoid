// ID 318646072
package base.classes;

/**
 * A Point class.
 *
 * @author Asaf Mesilaty
 */
public class Point {
    private static final double EPSILON = Math.pow(10, -7);
    private double x;
    private double y;

    /**
     * Construct a point given x and y coordinates.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Construct a point by default coordinates - zero.
     */
    public Point() {
        this(0, 0);
    }

    /**
     * @return the x coordinate
     */
    public double getX() {
        return this.x;
    }

    /**
     * @return the y coordinate
     */
    public double getY() {
        return this.y;
    }

    /**
     * @param newX the x coordinate to set for the point.
     */
    public void setX(double newX) {
        this.x = newX;
    }

    /**
     * @param newY the y coordinate to set for the point.
     */
    public void setY(double newY) {
        this.y = newY;
    }


    /**
     * @param other the other point to compare with.
     * @return return the left point or the bottom point if x values are equal.
     */
    public Point min(Point other) {
        if (other == null) {
            return this;
        }
        if (this.x < other.getX()) {
            return this;
        } else if (this.x == other.getX()) {

            // If the x values are equal the Start point of the line will be the lower.
            if (this.y < other.getY()) {
                return this;
            } else {
                return other;
            }
        } else {
            return other;
        }
    }

    /**
     * @param other the other point to compare with.
     * @return return the right point or the top point if x values are equal.
     */
    public Point max(Point other) {
        if (other == null) {
            return this;
        }
        if (this.x > other.getX()) {
            return this;
        } else if (this.x == other.getX()) {

            // If the x values are equal the Start point of the line will be the lower.
            if (this.y > other.getY()) {
                return this;
            } else {
                return other;
            }
        } else {
            return other;
        }
    }

    /**
     * @param other a point to measure the distance to.
     * @return the distance to the other point.
     */
    public double distance(Point other) {
        if (other == null) {
            System.err.println("distance method got null BaseClasses.Point");
            return -1;
        }

        // Calculate the difference between the current point coordinates to the other point coordinates.
        double dx = this.x - other.getX();
        double dy = this.y - other.getY();

        // Use the distance between two point formula.
        return Math.sqrt((dx * dx) + (dy * dy));
    }

    /**
     * Consider AB is the line and C is the point.
     * We would like to know if point C is on line AB.
     * We use the mathematical fact that two ribs of the triangle are always greater than one.
     * @param line a line.
     * @return true if this point is on line.
     * */
    public boolean isOn(Line line) {
        double ac = line.start().distance(this);
        double bc = line.end().distance(this);
        double ab = line.length();
        return (Math.abs((ac + bc) - ab) < EPSILON);
    }

    /**
     * @param other a point to compare the x and y coordinates
     * @return true if the point are identical and false otherwise.
     */
    public boolean equals(Point other) {
        if (other == null) {
            System.err.println("equals method got null BaseClasses.Point");
            return false;
        }

        // Calculates the difference between the X values by an absolute value.
        double dx = Math.abs(this.x - other.getX());

        // Calculates the difference between the Y values by an absolute value.
        double dy = Math.abs(this.y - other.getY());

        /* Check if those differences are smaller than epsilon.*/
        /* If so then the point coordinates are probably equal. */
        return (dx < EPSILON && dy < EPSILON);
    }

    /**
     *
     * @return A string that represents a point as follows: "(x, y)"
     */
    public String toString() {
        return ("(" + this.x + ", " + this.y + ")");
    }
}

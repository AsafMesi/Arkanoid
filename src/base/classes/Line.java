// ID 318646072
package base.classes;
import biuoop.DrawSurface;

/**
 * A Line class.
 *
 * @author Asaf Mesilaty
 */
public class Line {
    private static final double EPSILON = Math.pow(10, -7);
    private final Point first;
    private final Point second;
    private final Point start;
    private final Point end;
    private final LinearEquation le;

    /**
     * Construct a line given start and end points.
     *
     * @param start the start point
     * @param end the end point
     */
    public Line(Point start, Point end) {
        if (start == null || end == null) {
            throw new RuntimeException("Line got a null Point");
        }

        // We want to know which BaseClasses.Point sent first to the constructor to avoid unnecessary testing.
        this.first = new Point(start.getX(), start.getY());
        this.second = new Point(end.getX(), end.getY());

        /* The Start point of the line will be the point to the left */
        /* If the x values are equal the Start point of the line will be the lower. */
        this.start = this.first.min(this.second);
        this.end = this.first.max(this.second);
        this.le = new LinearEquation(this.start, this.end);
    }

    /**
     * Construct a line given x1, y1 and x2, y2 coordinates.
     *
     * @param x1 the x coordinate of the first point.
     * @param y1 the y coordinate of the first point.
     * @param x2 the x coordinate of the second point.
     * @param y2 the y coordinate of the second point.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this(new Point(x1, y1), new Point(x2, y2));
    }

    /**
     * @return the length of the line.
     */
    public double length() {
        return this.start.distance(this.end);
    }

    /**
     * @return the middle BaseClasses.Point of the line.
     */
    public Point middle() {
        double x = (this.start.getX() + this.end.getX()) / 2.0;
        double y = (this.start.getY() + this.end.getY()) / 2.0;
        return new Point(x, y);
    }

    /**
     * @return the first point of the line.
     */
    public Point first() {
        return this.first;
    }

    /**
     * @return the second point of the line.
     */
    public Point second() {
        return this.second;
    }

    /**
     * @return the start point of the line.
     */
    public Point start() {
        return this.start;
    }

    /**
     * @return the end point of the line.
     */
    public Point end() {
        return this.end;
    }

    /**
     * @return the representative equation of the line.
     */
    public LinearEquation getEq() {
        return this.le;
    }

    /**
     * @return true if the line is a point and false otherwise.
     */
    public boolean isPoint() {
        return (this.start.equals(this.end));
    }

    /**
     * @param other the possible greater line.
     * @return true if this is sub line of other.
     */
    public boolean isSubLine(Line other) {
        if (this.isPoint()) {
            return this.start.isOn(other);
        }
        if (!this.getEq().isParallel(other.getEq())) {
            return false;
        }

        // Check if other.start is smaller and if other.end is larger.
        return ((other.start.equals(this.start.min(other.start()))) && (other.end.equals(this.end.max(other.end()))));
    }

    /**
     * If they have an intersection point - then they intersect.
     * @param other a line to check intersection with.
     * @return true if the lines intersect.
     */
    public boolean isIntersecting(Line other) {
        return (this.intersectionWith(other) != null);
    }

    /**
     * If the length of the common area between two lines is zero - then they have one intersection point.
     * @param other a line to check intersection with.
     * @return the intersection point if the lines intersect, and null otherwise.
     */
    public Point intersectionWith(Line other) {
        Line common = this.getCommonArea(other);
        if (common != null) {
            if (common.length() < EPSILON) {
                return common.start();
            } else {
                return null;
            }
        }
        return null;
    }

    /**
     * @param p a point to search on the line.
     * @return true if the point on the line and false otherwise.
     */
    public boolean onLine(Point p) {
        if (p == null) {
            return false;
        }

        // If the line is a point - compare the points.
        if (this.isPoint()) {
            return (p.equals(this.start));
        }

        // If the point is a solution - check if it is between start and end.
        if (this.getEq().isSol(p)) {
            double startX = this.start.getX();
            double endX = this.end.getX();
            double startY = this.start.getY();
            double endY = this.end.getY();

            // startX <= pX <= endX  &  startY <= pY <= endY
            return ((Math.abs(startX - p.getX()) < EPSILON || startX < p.getX())
                    && (Math.abs(endX - p.getX()) < EPSILON || p.getX() < endX)
                    && (Math.abs(startY - p.getY()) < EPSILON || startY < p.getY())
                    && (Math.abs(endY - p.getY()) < EPSILON || p.getY() < endY));
        }
        return false;
    }

    /**
     * Check two cases in order to get common area between two lines:
     * 1. non parallel lines. - possible intersection point.
     * 2. lines with the same representative equation. - possible common area:
     * 2.1 subLine.
     * 2.2 not subLine
     * @param other line the check for common area.
     * @return the intersection line between the two lines. (maximum sub line of both lines).
     */
    public Line getCommonArea(Line other) {
        LinearEquation l1 = this.getEq();
        LinearEquation l2 = other.getEq();

        // Common area with the length 0, which means one point. (or no common area)
        if (!l1.isParallel(l2)) {
            Point intersection = l1.getIntersection(l2);
            if (intersection.isOn(this) && intersection.isOn(other)) {
                return new Line(intersection, intersection);
            } else {
                return null;
            }
        }

        // Common area with the length of 0 or more. (or no common area).
        if (this.isSubLine(other)) {
            return this;
        }
        if (other.isSubLine(this)) {
            return other;
        }

        // If the case above is not the case, the following will solve the problem.
        if (other.start().isOn(this)) {
            return new Line(other.start(), this.end);
        } else if (this.start.isOn(other)) {
            return new Line(this.start, other.end());
        }
        return null;
    }

    /**
     * This method goes throw a list of intersection points of this line the "rect".
     * The method checks which point is closest to this first point and return it.
     * @param rect the rectangle that which may have been intersect with the line.
     * @return the closest intersection point to this first. null if no intersection point exists.
     * */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {

        if (rect == null) {
            System.err.println("rect is null");
            return null;
        }
        java.util.List<Point> intersectionPoints = rect.intersectionPoints(this);

        Point closest = null;
        double min = this.length() + 1;
        for (Point p : intersectionPoints) {
            if (p.distance(this.first) < min) {
                min = p.distance(this.first);
                closest = p;
            }
        }
        return closest;
    }

    /**
     * @param d the surface to draw on.
     * */
    public void drawOn(DrawSurface d) {
        int x1 = (int) this.start().getX();
        int y1 = (int) this.start().getY();
        int x2 = (int) this.end().getX();
        int y2 = (int) this.end().getY();
        d.drawLine(x1, y1, x2, y2);
    }

    /**
     * @param other a line to compare the start and end points.
     * @return true if the lines are identical and false otherwise.
     */
    public boolean equals(Line other) {
        if (other == null) {
            return false;
        }
        return (this.start.equals(other.start) && this.end.equals(other.end));
    }

    /**
     * @return A string that represents a line as follows: "(x1, y1) -> (x2, y2)".
     */
    public String toString() {
        return (this.start.toString() + " -> " + this.end.toString());
    }
}

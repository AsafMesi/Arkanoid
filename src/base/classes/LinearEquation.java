// ID 318646072
package base.classes;

/**
 * A Linear equation class.
 *
 * @author Asaf Mesilaty
 */
public class LinearEquation {
    private static final double EPSILON = Math.pow(10, -7);
    private final double a;
    private final double b;
    private final double c;

    /**
     * construct equation given a, b, c.
     * @param a is a of ax.
     * @param b is b of by.
     * @param c is c for ax + by = c.
     */
    public LinearEquation(double a, double b, double c) {
        this.a = a;
        this.b = b;

        if (a == 0 && b == 0 && c != 0) {
            this.c = 0;
            System.err.println("c cant be " + c + "c has been set to zero");
        } else {
            this.c = c;
        }
    }

    /**
     * get a, b, c for the representative equation ax + by = c.
     * @param start the first point.
     * @param end the second point.
     */
    public LinearEquation(Point start, Point end) {
        if (start == null || end == null) {
            throw new RuntimeException("LinerEquation got a null point");
        }
        a = end.getY() - start.getY();
        b = start.getX() - end.getX();
        c = a * start.getX() + b * start.getY();
    }

    /**
     * @return the equation a value.
     */
    public double getA() {
        return this.a;
    }

    /**
     * @return the equation b value.
     */
    public double getB() {
        return this.b;
    }

    /**
     * @return the equation c value.
     */
    public double getC() {
        return this.c;
    }

    /**
     /* The only case in which to linear equation has more then one intersection is when they parallel.
     * @param other the other equation to check if parallel to.
     * @return true if they are parallel and false otherwise.
     */
    public boolean isParallel(LinearEquation other) {

        // Check if the determinant is equal to 0.
        return (Math.abs((this.a * other.getB()) - (this.b * other.getA())) < EPSILON);
    }

    /**
     * @param p a point.
     * @return true if the point is a solution to that linear equation.
     */
    public boolean isSol(Point p) {
        if (p == null) {
            return false;
        }
        return (Math.abs(this.a * p.getX() + this.b * p.getY() - this.c) < EPSILON);
    }

    /**
     * this: a1x + b1x.
     * other: a2x + b2x.
     *
     * | a1 b1 |
     * | a2 b2 |
     *
     * determinant = (a1*b2) - (b1*a2).
     *
     * @param other the other equation the compare with.
     * @return null if the equations are parallel and the intersection point otherwise.
     */
    public Point getIntersection(LinearEquation other) {

        // If the equations are parallel then they have infinity common points.
        if (this.isParallel(other)) {
            return null;
        }

        /* If the lines aren't parallel - they got to have exactly one intersection */
        /* Now we want to solve the system of equation: */
        /* (1) a1x + b1y = c1
        /* (2) a2x + b2y = c2
         */
        double a1 = this.a;
        double b1 = this.b;
        double c1 = this.c;
        double a2 = other.getA();
        double b2 = other.getB();
        double c2 = other.getC();
        double determinant = (a1 * b2) - (a2 * b1);

        /* Now we found the general solution to this system of equations. */
        /* It is not easy to see, but by using WIN (wouldn't it be nice) we get that the following is correct */
        double x = ((b2 * c1) - (b1 * c2)) / determinant;
        double y = ((a1 * c2) - (a2 * c1)) / determinant;
        return new Point(x, y);
    }

    /**
     * if one y-axis intersection exists, return the intersection point.
     * @return intersection point.
     */
    public Point yIntersection() {
        if (this.b == 0) {
            return null;
        }
        return new Point(0, this.c / this.b);
    }

    /**
     * if one x-axis intersection exists, return the intersection point.
     * @return intersection point.
     */
    public Point xIntersection() {
        if (this.a == 0) {
            return null;
        }
        return new Point(this.c / this.a, 0);
    }

    /**
     * Check if the equation was built by two identical points - which means 0x + 0y = 0.
     * @return true if the equation is of the form 0x + 0y = 0 and false otherwise.
     */
    public boolean isNan() {
        return (this.a == 0 && this.b == 0);
    }

    /**
     * We have three stages when dealing with this problem:
     * 1. one of the equations is Nan - which means y, x intersection is null.
     * 2. one of the x, y intersection is null
     * 3. non of the intersections are null.
     * @param other BaseClasses.LinearEquation to compare with.
     * @return true of the equations are equal and false otherwise.
     */
    public boolean equals(LinearEquation other) {

        // 1 - If one of them is Nan - then the other has to be Nan.
        if (this.isNan() || other.isNan()) {
            return (this.isNan() && other.isNan());
        }

        // 2 - Only one x or y intersection is null.
        Point x = this.xIntersection();
        Point y = this.yIntersection();
        if (x == null) {
            if (other.xIntersection() != null) {
                return false;
            } else {
                return (other.isSol(y));
            }
        }
        if (y == null) {
            if (other.yIntersection() != null) {
                return false;
            } else {
                return (other.isSol(x));
            }
        }

        // 3 - Check if x, y are solutions to other.
        return (other.isSol(x) && other.isSol(y));
    }

    /**
     * @return A string that represents the equation as follows: "ax + by = c".
     */
    public String toString() {
        return this.a + "x + " + this.b + "y = " + this.c;
    }
}

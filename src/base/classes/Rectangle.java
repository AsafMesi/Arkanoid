// ID 318646072
package base.classes;
import biuoop.DrawSurface;
import java.awt.Color;
import java.util.LinkedList;

/**
 * A Rectangle class.
 *
 * @author Asaf Mesilaty
 */
public class Rectangle {
    private Point upperLeft;
    private double width;
    private double height;
    private Line left;
    private Line right;
    private Line top;
    private Line bottom;
    private Color color;

    /**
     * Construct a Rectangle given parameters below.
     * @param upperLeft the upper left point of the Rectangle.
     * @param width the Rectangle width.
     * @param height the Rectangle height.
     * @param color the color of the ball.
     */
    public Rectangle(Point upperLeft, double width, double height, Color color) {
        if (upperLeft == null || color == null) {
            throw new RuntimeException("At new Rectangle: Can't create Rectangle from null point or color!");
        }
        this.color = color;
        double x = upperLeft.getX();
        double y = upperLeft.getY();
        this.upperLeft = new Point(x, y);
        this.width = width;
        this.height = height;

        // construct the lines that define the rectangle.
        this.right = new Line(x + width, y, x + width, y + height);
        this.left = new Line(x, y, x, y + height);
        this.top = new Line(x, y, x + width, y);
        this.bottom = new Line(x, y + height, x + width, y + height);
    }

    /**
     * @return the rectangle width.
     * */
    public double getWidth() {
        return this.width;
    }

    /**
     * @return the rectangle height.
     * */
    public double getHeight() {
        return this.height;
    }

    /**
     * The lines looks like this:
     * upperLeft ---------> upperRight
     *  .                      .
     *  .                      .
     *  .                      .
     * \/                      \/
     * downLeft ---------> downRight
     *
     * @return The rectangle upper left point.
     * */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * @return The rectangle upper right point.
     * */
    public Point getUpperRight() {
        return this.top.second();
    }

    /**
     * @return The rectangle lower left point.
     * */
    public Point getDownLeft() {
        return this.bottom.first();
    }

    /**
     * @return The rectangle lower right point.
     * */
    public Point getDownRight() {
        return this.bottom.second();
    }


    /**
     * @return the top line of the rectangle.
     * */
    public Line getTop() {
        return this.top;
    }

    /**
     * @return the left line of the rectangle.
     * */
    public Line getLeft() {
        return this.left;
    }

    /**
     * @return the right line of the rectangle.
     * */
    public Line getRight() {
        return this.right;
    }

    /**
     * @return the bottom line of the rectangle.
     * */
    public Line getBottom() {
        return this.bottom;
    }

    /**
     * set this rectangle color to newColor.
     * @param newColor the new color.
     * */
    public void setColor(Color newColor) {
        this.color = newColor;
    }

    /**
     * @param p the point to check if it is a corner of the rectangle.
     * @return true if a given point is a corner of the rectangle.
     * */
    public boolean isCorner(Point p) {
        return p.equals(this.getUpperLeft())
                || p.equals(this.getUpperRight())
                || p.equals(this.getDownLeft())
                || p.equals(this.getDownRight());
    }

    /**
     * This method could be relevant if we put random balls on random locations and
     * we want to verify there locations doesn't harm the game.
     * @param p the point the check if it is inside the rectangle.
     * @return true if a given point is inside the rectangle.
     *
     * we want distance of at least two pixel from the rectangle so we don't have any bugs.
     * */
    public boolean isInside(Point p) {
       double leftX = this.upperLeft.getX() - 2;
       double rightX = this.getUpperRight().getX() + 2;
       double topY = this.upperLeft.getY() - 2;
       double bottomY = this.getDownLeft().getY() + 2;
       return (leftX <= p.getX() && p.getX() <= rightX && topY <= p.getY() && p.getY() <= bottomY);
    }

    /**
     * @param newUpperLeft the new upper left point of the relocated rectangle.
     * @return new rectangle with the same size, and different upperLeft point.
     * */
    public Rectangle relocate(Point newUpperLeft) {
        return new Rectangle(newUpperLeft, this.width, this.height, this.color);
    }

    /**
     * @param line a line to check with intersection.
     * @return a (possibly empty) List of intersection points.
     * */
    public java.util.List<Point> intersectionPoints(Line line) {
        java.util.List<Point> intersectionPoints = new LinkedList<>();
        Point intersection;

        intersection = line.intersectionWith(this.left);
        if (intersection != null) {
            intersectionPoints.add(intersection);
        }

        intersection = line.intersectionWith(this.right);
        if (intersection != null) {
            intersectionPoints.add(intersection);
        }

        intersection = line.intersectionWith(this.top);
        if (intersection != null) {
            intersectionPoints.add(intersection);
        }

        intersection = line.intersectionWith(this.bottom);
        if (intersection != null) {
            intersectionPoints.add(intersection);
        }
        return intersectionPoints;
    }

    /**
     * Draw the rectangle in the given DrawSurface object.
     * @param d the DrawSurface object.
     * */
    public void drawOn(DrawSurface d) {

        // Draw the Rectangle.
        int x = (int) this.getUpperLeft().getX();
        int y = (int) this.getUpperLeft().getY();
        d.setColor(this.color);
        d.fillRectangle(x, y, (int) this.width, (int) this.height);

        // Draw the lines within the boundaries of the rectangle.
        d.setColor(Color.BLACK);
        this.left.drawOn(d);
        this.right.drawOn(d);
        this.top.drawOn(d);
        this.bottom.drawOn(d);
    }
}
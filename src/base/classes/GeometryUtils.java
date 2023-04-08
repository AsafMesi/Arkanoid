// ID 318646072
package base.classes;
import java.awt.Color;
import java.util.Random;

/**
 * A GeometryUtils class.
 * This class contains useful geometric methods.
 * @author Asaf Mesilaty
 */
public class GeometryUtils {

    /**
     * @param end is the max / min value of the random integer.
     * @return a random integer between 0 and end.
     * */
    public int randInt(int end) {
        return new Random().nextInt(end + 1);
    }

    /**
     * @param start the min / max value of the random integer.
     * @param end the max / min value of the random integer.
     * @return a random integer between start and end.
     * */
    public int randInt(int start, int end) {
        if (start < end) {
            return randInt(end - start) + start;
        } else {
            return randInt(start - end) + end;
        }
    }

    /**
     * @param domain an object that contains start and end points.
     * @return a random point between start and end.
     * */
    public Point randIntPoint(Line domain) {
        if (domain == null) {
            System.err.println("randIntPoint method got a null Line.");
            return null;
        }
        return randIntPoint(domain.start(), domain.end());
    }

    /**
     * @param start is the first point.
     * @param end is the second point.
     * @return a random point between start and end.
     * */
    public Point randIntPoint(Point start, Point end) {
        if (start == null || end == null) {
            System.err.println("randIntPoint method got a null Point.");
            return null;
        }
        int x = randInt((int) start.getX(), (int) end.getX());
        int y = randInt((int) start.getY(), (int) end.getY());
        return new Point(x, y);
    }

    /**
     * Creating pleasing, pastel color, by using the HLS system.
     * @return a random color.
     * This method has been pretty much copied from stack overflow
     * */
    public static Color randColor() {
        Random rand = new Random();
        final float hue = rand.nextFloat();
        final float saturation = 0.9f; // 1.0 for brilliant, 0.0 for dull
        final float luminance = 1.0f; // 1.0 for brighter, 0.0 for black
        return Color.getHSBColor(hue, saturation, luminance);

    }
}

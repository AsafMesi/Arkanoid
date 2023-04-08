// ID 318646072
package sprites;
import biuoop.DrawSurface;

/**
 * A Sprite interface.
 *
 * @author Asaf Mesilaty
 */
public interface Sprite {

    /**
     * Draw the sprite to the screen.
     * @param d the DrawSurface that we draw on.
     * */
    void drawOn(DrawSurface d);

    /**
     * Notify the sprite that time has passed.
     * */
    void timePassed();
}
// ID 318646072
package animations;

import biuoop.DrawSurface;

/**
 * An Animation interface.
 *
 * @author Asaf Mesilaty
 */
public interface Animation {

    /**
     * @param d the DrawSurface object we adjust.
     * */
    void doOneFrame(DrawSurface d);

    /**
     * @return true if the animation should stop and false otherwise.
     * */
    boolean shouldStop();
}

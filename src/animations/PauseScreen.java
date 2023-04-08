// ID 318646072
package animations;
import biuoop.DrawSurface;

/**
 * An PauseScreen class.
 *
 * @author Asaf Mesilaty
 */
public class PauseScreen implements Animation {
    @Override
    public void doOneFrame(DrawSurface d) {
        d.drawText(10, d.getHeight() / 2, "paused -- press space to continue", 32);
    }

    @Override
    public boolean shouldStop() {
        return false;
    }
}

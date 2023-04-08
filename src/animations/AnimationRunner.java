// ID 318646072
package animations;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
 * An AnimationRunner class.
 *
 * @author Asaf Mesilaty
 */
public class AnimationRunner {
    private GUI gui;
    private int framesPerSecond = 60;
    private Sleeper sleeper = new Sleeper();

    /**
     * @param gui the gui we put the animation on.
     * */
    public AnimationRunner(GUI gui) {
        this.gui = gui;
    }

    /**
     * @param animation an object that we can animate.
     * Run the gameLevel -- start the animation loop.
     * */
    public void run(Animation animation) {
        if (animation == null) {
            return;
        }

        int millisecondsPerFrame = 1000 / framesPerSecond;
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();
            animation.doOneFrame(d);
            gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}

// ID 318646072
package animations;

import biuoop.DrawSurface;
import biuoop.Sleeper;
import sprites.SpriteCollection;
import java.awt.Color;

/**
 * An CountdownAnimation class.
 *
 * @author Asaf Mesilaty
 *
 * The CountdownAnimation will display the given gameScreen, for numOfSeconds seconds,
 * and on top of them it will show a countdown from countFrom back to 1,
 * where each number will appear on the screen for (numOfSeconds / countFrom) seconds,
 * before it is replaced with the next one.
 */

public class CountdownAnimation implements Animation {
    private double numOfSeconds;
    private int countFrom;
    private int countDown;
    private SpriteCollection gameScreen;
    private boolean stop = false;

    /**
     * a CountdownAnimation constructor.
     * @param numOfSeconds the number of seconds to count down.
     * @param countFrom the number which we start the countdown from.
     * @param gameScreen the sprite collection that we need to draw in order to get the game screen.
     * */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom;
        this.countDown = countFrom;
        this.gameScreen = gameScreen;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        if (countDown <= 0) {
            this.stop = true;
        }
        gameScreen.drawAllOn(d);
        d.setColor(Color.GRAY);
        d.drawText(400, d.getHeight() / 2, (Integer.valueOf(countDown)).toString(), 80);

        if (countDown != countFrom) {

            // sleep for 1 second.
            new Sleeper().sleepFor((long) (1000 * (numOfSeconds / countFrom)));
        }
        this.countDown--;
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}

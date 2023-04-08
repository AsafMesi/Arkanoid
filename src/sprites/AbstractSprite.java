// ID 318646072
package sprites;

import biuoop.DrawSurface;

/**
 * An AbstractSprite class.
 *
 * This class was built mainly to deal with objects that has no effect on the game.
 * @author Asaf Mesilaty
 */
public abstract class AbstractSprite implements Sprite {

    @Override
    public abstract void drawOn(DrawSurface d);

    @Override
    public void timePassed() {

    }
}

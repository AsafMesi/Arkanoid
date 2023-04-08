// ID 318646072
package observers;
import sprites.Ball;
import sprites.Block;

/**
 * A HitListener interface.
 *
 * @author Asaf Mesilaty
 */
public interface HitListener {

    /**
     * This method is called whenever the beingHit object is hit.
     * The hitter parameter is the Ball that's doing the hitting.
     * @param beingHit the block that being hit.
     * @param hitter the ball that hits the block.
     * */
    void hitEvent(Block beingHit, Ball hitter);
}
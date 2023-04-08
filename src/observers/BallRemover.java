// ID 318646072
package observers;
import base.classes.Counter;
import sprites.Ball;
import sprites.Block;
import logic.GameLevel;

/**
 * A BallGenerator class.
 *
 * BallRemover is in charge of removing balls from the gameLevel,
 * as well as keeping count of the number of balls that remain.
 *
 * @author Asaf Mesilaty
 */
public class BallRemover implements HitListener {
    private GameLevel gameLevel;
    private Counter remainingBalls;

    /**
     * Construct a BallRemover listener given parameters below.
     * @param gameLevel the gameLevel we remove balls from.
     * @param remainingBalls is a counter that keep track of the number of balls during the gameLevel.
     * */
    public BallRemover(GameLevel gameLevel, Counter remainingBalls) {
        this.gameLevel = gameLevel;
        this.remainingBalls = remainingBalls;
    }

    /**
     * This method removes the hitter ball when being called.
     * @param beingHit the block that has been hit.
     * @param hitter the ball that hit the block.
     * Balls that hit the death region will be removed from the gameLevel.
     * */
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.gameLevel);
        this.remainingBalls.decrease();
    }
}

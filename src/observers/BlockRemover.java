// ID 318646072
package observers;
import base.classes.Counter;
import logic.GameLevel;
import sprites.Ball;
import sprites.Block;

/**
 * A BlockRemover class.
 *
 * BlockRemover is in charge of removing blocks from the gameLevel,
 * as well as keeping count of the number of blocks that remain.
 *
 * @author Asaf Mesilaty
 */
public class BlockRemover implements HitListener {
    private GameLevel gameLevel;
    private Counter remainingBlocks;

    /**
     * Construct a BlockRemover listener given parameters below.
     * @param gameLevel the gameLevel we add balls to.
     * @param remainingBlocks is a counter that keep track of the number of blocks during the gameLevel.
     * */
    public BlockRemover(GameLevel gameLevel, Counter remainingBlocks) {
        this.gameLevel = gameLevel;
        this.remainingBlocks = remainingBlocks;
    }

    /**
     * This method removes a block that has been hit.
     * @param beingHit the block the has been hit.
     * @param hitter the ball that hit the block.
     * */
    public void hitEvent(Block beingHit, Ball hitter) {
        beingHit.removeHitListener(this);
        beingHit.removeFromGame(this.gameLevel);
        this.remainingBlocks.decrease();
    }
}
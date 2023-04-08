// ID 318646072
package observers;
import base.classes.Counter;
import sprites.Ball;
import sprites.Block;

/**
 * A Score Tracking Listener class.
 *
 * @author Asaf Mesilaty
 * */
public class ScoreTrackingListener implements HitListener {

    // This number is the number of point the player get for destroying a block.
    private static final int DESTROY_BLOCK_POINTS = 5;
    private Counter currentScore;

    /**
     * Construct a score tracking listener given a scoreCounter (from a gameLevel).
     * @param scoreCounter the score counter.
     * */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * When being called - the player gets points for destroying the block.
     * @param beingHit the block being hit.
     * @param hitter the hitting ball.
     * */
    public void hitEvent(Block beingHit, Ball hitter) {
       this.currentScore.increase(DESTROY_BLOCK_POINTS);
       beingHit.removeHitListener(this);
    }
}
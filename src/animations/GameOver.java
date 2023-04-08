// ID 318646072
package animations;
import biuoop.DrawSurface;
import java.awt.Color;

/**
 * An PauseScreen class.
 *
 * @author Asaf Mesilaty
 */
public class GameOver implements Animation {
    private boolean won;
    private int score;

    /**
     * a PauseScreen constructor.
     * @param won the state of the player.
     * @param score the score the player ended the game with.
     * */
    public GameOver(Boolean won, int score) {
        this.won = won;
        this.score = score;
    }
    @Override
    public void doOneFrame(DrawSurface d) {
        String text;
        if (won) {
            d.setColor(Color.GREEN);
            text = "You Win! Your score is " + this.score;
        } else {
            d.setColor(Color.RED);
            text = "Game Over. Your score is " + this.score;
        }
        d.drawText(40, d.getHeight() / 4, text, 40);
        d.setColor(Color.BLACK);
        d.drawText(30, d.getHeight() / 2, "press space to end game", 20);
    }

    @Override
    public boolean shouldStop() {
        return false;
    }
}

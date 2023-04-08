// ID 318646072
package sprites;
import base.classes.Counter;
import base.classes.Point;
import biuoop.DrawSurface;
import logic.GameLevel;
import java.awt.Color;

/**
 * A Score Indicator class.
 *
 * @author Asaf Mesilty
 * */
public class ScoreIndicator implements Sprite {
    public static final int DEFAULT_FONT_SIZE = 15;
    private Counter currentScore;
    private Point scoreLocation;
    private int fontSize;

    /**
     * Construct a ScoreIndicator object given parameters below.
     * @param scoreCounter a Counter that holds the score,
     * @param scoreLocation a point that represent the start of the text.
     * @param fontSize the size of the font.
     * */
    public ScoreIndicator(Counter scoreCounter, Point scoreLocation, int fontSize) {
        this.currentScore = scoreCounter;
        this.scoreLocation = scoreLocation;
        this.fontSize = fontSize;
    }

    /**
     * A constructor with default font size.
     * @param scoreCounter a Counter that holds the score,
     * @param scoreLocation a point that represent the start of the text.
     * */
    public ScoreIndicator(Counter scoreCounter, Point scoreLocation) {
        this(scoreCounter, scoreLocation, DEFAULT_FONT_SIZE);
    }

    @Override
    public void drawOn(DrawSurface d) {
        String score = this.currentScore.toString();
        int x = (int) scoreLocation.getX();
        int y = (int) scoreLocation.getY();
        String text = "Score: " + score;
        d.setColor(Color.BLACK);
        d.drawText(x, y, text, fontSize);
    }

    /**
     * This method adds the score indicator the a given gameLevel.
     * @param g the gameLevel with the score indicator to.
     * */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    @Override
    public void timePassed() {
    }
}

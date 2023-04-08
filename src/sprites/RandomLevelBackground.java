// ID 318646072
package sprites;

import base.classes.GeometryUtils;
import biuoop.DrawSurface;
import java.awt.Color;

/**
 * A RandomLevelBackground class.
 *
 * @author Asaf Mesilaty
 */
public class RandomLevelBackground extends AbstractSprite {
    private Color c1, c2, c3, c4;

    /**
     * RandomLevelBackground Constructor.
     * We want random colors when creating the background, but we don't want them to change during the game.
     * So we have to create them only once.
     * */
    public RandomLevelBackground() {
        c1 = GeometryUtils.randColor().darker();
        c2 = GeometryUtils.randColor().darker();
        c3 = GeometryUtils.randColor().darker();
        c4 = GeometryUtils.randColor().darker();
    }
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(c1);
        d.fillRectangle(0, 30, 400, 300);
        d.setColor(c2);
        d.fillRectangle(400, 30, 800, 300);
        d.setColor(c3);
        d.fillRectangle(0, 300, 400, 600);
        d.setColor(c4);
        d.fillRectangle(400, 300, 400, 600);

        String title = "Level Name: RandomLevel";
        int y = 15;
        int titleX = 500;
        d.setColor(Color.BLACK);
        d.drawText(titleX, y, title, ScoreIndicator.DEFAULT_FONT_SIZE);
    }
}

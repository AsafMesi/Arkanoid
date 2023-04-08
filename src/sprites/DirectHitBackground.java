// ID 318646072
package sprites;

import biuoop.DrawSurface;
import java.awt.Color;

/**
 * A DirectHitBackground class.
 *
 * @author Asaf Mesilaty
 */
public class DirectHitBackground extends AbstractSprite {

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.fillRectangle(0, 18, d.getWidth(), d.getHeight());
        d.setColor(Color.BLUE);
        d.drawCircle(400, 162, 60);
        d.drawCircle(400, 162, 90);
        d.drawCircle(400, 162, 120);
        d.drawLine(400, 182, 400, 302);
        d.drawLine(420, 162, 540, 162);
        d.drawLine(380, 162, 260, 162);
        d.drawLine(400, 142, 400, 22);

        String title = "Level Name: Direct Hit";
        int y = 15;
        int titleX = 500;
        d.setColor(Color.BLACK);
        d.drawText(titleX, y, title, ScoreIndicator.DEFAULT_FONT_SIZE);

    }
}

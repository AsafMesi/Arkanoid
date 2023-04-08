// ID 318646072
package sprites;

import biuoop.DrawSurface;
import java.awt.Color;

/**
 * A WideEasyAbstractSprite class.
 *
 * @author Asaf Mesilaty
 */
public class WideEasyAbstractSprite extends AbstractSprite {

    @Override
    public void drawOn(DrawSurface d) {
        Color sunFirstLayer = new Color(248, 237, 179);
        Color sunMidLayer = new Color(236, 215, 73);
        Color sunBackLayer = new Color(255, 225, 24);
        d.setColor(Color.lightGray.brighter());
        d.fillRectangle(0, 0, 800, 600);
        int rightLineX = 20;

        for (int i = 0; i < 100; ++i) {
            d.setColor(sunFirstLayer);
            d.drawLine(120, 100, rightLineX, 200);
            rightLineX += 8;
        }

        d.setColor(sunFirstLayer);
        d.fillCircle(120, 100, 48);
        d.setColor(sunMidLayer);
        d.fillCircle(120, 100, 38);
        d.setColor(sunBackLayer);
        d.fillCircle(120, 100, 30);

        String title = "Level Name: Wide Easy";
        int y = 15;
        int titleX = 500;
        d.setColor(Color.BLACK);
        d.drawText(titleX, y, title, ScoreIndicator.DEFAULT_FONT_SIZE);
    }
}

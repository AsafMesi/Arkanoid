// ID 318646072
package sprites;

import biuoop.DrawSurface;
import java.awt.Color;

/**
 * A ThugMickeyBackground class.
 *
 * @author Asaf Mesilaty
 */
public class ThugMickeyBackground extends AbstractSprite {

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.WHITE.darker());
        d.fillRectangle(0, 0, 800, 600);

        // draw note lines - makes it fancy.
        d.setColor(Color.BLUE.brighter());
        for (int y = 60; y <= 740; y += 40) {
            d.drawLine(0, y, 800, y);
        }

        // draw mickey's face
        d.setColor(Color.BLACK);
        d.fillCircle(200, 140, 80);
        d.fillCircle(600, 140, 80);
        d.fillCircle(400, 280, 180);

        // draw mickey's eyes:
        d.setColor(Color.WHITE);
        d.fillCircle(310, 200, 30);
        d.fillCircle(490, 200, 30);
        d.setColor(Color.BLACK);
        d.fillCircle(310, 205, 10);
        d.setColor(Color.BLACK);
        d.fillCircle(490, 205, 10);

        // draw mickey's nose
        d.setColor(Color.RED);
        d.fillOval(380, 280, 50, 20);

        // draw mickey's ear circle
        d.setColor(Color.MAGENTA);
        d.fillCircle(200, 140, 20);

        String title = "Level Name: Thug Mickey";
        int y = 15;
        int titleX = 500;
        d.setColor(Color.BLACK);
        d.drawText(titleX, y, title, ScoreIndicator.DEFAULT_FONT_SIZE);

    }
}

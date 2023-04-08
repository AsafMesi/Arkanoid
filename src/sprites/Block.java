// ID 318646072
package sprites;
import base.classes.Point;
import base.classes.Rectangle;
import base.classes.Velocity;
import biuoop.DrawSurface;
import logic.Collidable;
import logic.GameLevel;
import observers.HitListener;
import observers.HitNotifier;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * A Block class.
 *
 * @author Asaf Mesilaty
 */
public class Block implements Collidable, Sprite, HitNotifier {
    public static final int DEFAULT_WIDTH = 50;
    public static final int DEFAULT_HEIGHT = 20;
    private Rectangle block;
    private List<HitListener> hitListeners = new ArrayList<>();

    /**
     * Construct a Block given parameters below.
     * @param upperLeft the upper left point of the Block.
     * @param width the Block width.
     * @param height the Block height.
     * @param color the color of the Ball.
     */
    public Block(Point upperLeft, double width, double height, Color color) {
        if (upperLeft == null || color == null) {
            throw new RuntimeException("At new Block: Can't create Block from null point or color!");
        }
        this.block = new Rectangle(upperLeft,  width,  height, color);
    }

    /**
     * Construct a Block given upper left point and color with default width and height.
     * @param x the x value of the upper left of the rectangle.
     * @param y the y value of the upper left of the rectangle.
     * @param color the color of the rectangle.
     * */
    public Block(int x, int y, Color color) {
        this(new Point(x, y), DEFAULT_WIDTH, DEFAULT_HEIGHT, color);
    }

    /**
     * This method add the block to the game.
     * The block is a collidable and a sprite.
     * @param g the GameLevel object we add this block to.
     * */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * set this block color to newColor.
     * @param newColor the new color.
     * */
    public void setColor(Color newColor) {
        this.block.setColor(newColor);
    }

    /**
     * Draw the block.
     * @param d the DrawSurface object we draw on.
     * */
    public void drawOn(DrawSurface d) {
        this.block.drawOn(d);
    }

    @Override
    public void timePassed() {
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.block;
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {

        this.notifyHit(hitter);

        // Hitting the left / right change dx. Hitting top / bottom change dy.
        Velocity newVelocity = new Velocity(currentVelocity.getDx(), currentVelocity.getDy());
        if (collisionPoint.isOn(this.block.getLeft())) {
            newVelocity.setDx((-1) * Math.abs(currentVelocity.getDx()));
        }
        if (collisionPoint.isOn(this.block.getRight())) {
            newVelocity.setDx(Math.abs(currentVelocity.getDx()));
        }
        if (collisionPoint.isOn(this.block.getTop())) {
            newVelocity.setDy((-1) * Math.abs(currentVelocity.getDy()));
        }
        if (collisionPoint.isOn(this.block.getBottom())) {
            newVelocity.setDy(Math.abs(currentVelocity.getDy()));
        }
        return newVelocity;
    }

    /**
     * This. method removes a block from the gameLevel.
     * @param gameLevel the gameLevel from which we remove the block.
     * */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeCollidable(this);
        gameLevel.removeSprite(this);
    }

    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * This method tells the block's listeners that the block was hit.
     * @param hitter the ball that hit the block.
     * */
    public void notifyHit(Ball hitter) {

        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);

        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }
}

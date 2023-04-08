// ID 318646072
package sprites;
import biuoop.DrawSurface;
import java.util.LinkedList;
import java.util.List;

/**
 * A SpriteCollection class.
 *
 * @author Asaf Mesilaty
 */
public class SpriteCollection {

    private LinkedList<Sprite> spriteCollection;

    /**
     * Construct SpriteCollection object.
     * the method create new Sprite Linked List.
     * */
    public SpriteCollection() {
        this.spriteCollection = new LinkedList<>();
    }

    /**
     * Add a given Sprite to the sprite collection.
     * @param s a Sprite.
     * */
    public void addSprite(Sprite s) {
        if (s == null) {
            System.out.println("s is null");
            return;
        }
        this.spriteCollection.add(s);
    }

    /**
     * remove a given Sprite from the sprite collection.
     * @param s a Sprite.
     * */
    public void removeSprite(Sprite s) {
        if (s == null) {
            System.out.println("s is null");
            return;
        }
        if (!this.spriteCollection.remove(s)) {
            System.out.println("At remove sprite: The sprite is not found");
        }
    }

    /**
     * Call timePassed() on all sprites.
     * */
    public void notifyAllTimePassed() {
        // Make a copy of the spriteCollection before iterating over them.
        List<Sprite> sprites = new LinkedList<>(this.spriteCollection);

        for (Sprite s : sprites) {
            s.timePassed();
        }
    }

    /**
     * Call drawOn(d) on all sprites.
     * @param d the DrawSurface we draw on.
     * */
    public void drawAllOn(DrawSurface d) {
        for (Sprite s : this.spriteCollection) {
            s.drawOn(d);
        }
    }
}
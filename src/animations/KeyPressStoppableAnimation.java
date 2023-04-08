package animations;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * An KeyPressStoppableAnimation class.
 *
 * @author Asaf Mesilaty
 */
public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor keyboard;
    private String key;
    private Animation animation;
    private Boolean stop = false;
    private boolean isAlreadyPressed = true;

    /**
     * @param animation the animation that will stop by key press.
     * @param key the key that if we press it - the animation will stop.
     * @param sensor the keyboard sensor.
     * */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
    this.keyboard = sensor;
    this.key = key;
    this.animation = animation;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        this.animation.doOneFrame(d);
        if (this.keyboard.isPressed(this.key)) {
            if (isAlreadyPressed) {
                return;
            }
            this.stop = true;
        } else {
            isAlreadyPressed = false;
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}

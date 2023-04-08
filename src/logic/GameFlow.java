// ID 318646072
package logic;

import animations.Animation;
import animations.AnimationRunner;
import animations.GameOver;
import animations.KeyPressStoppableAnimation;
import base.classes.Counter;
import biuoop.KeyboardSensor;
import java.util.List;

/**
 * A Ass6Game class.
 *
 * @author Asaf Mesilaty
 */
public class GameFlow {
    private KeyboardSensor keyboardSensor;
    private AnimationRunner animationRunner;
    private Counter score = new Counter();

    /**
     * GameFlow constructor.
     * @param ar the animation runner.
     * @param ks the keyboard sensor.
     * */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks) {
        this.animationRunner = ar;
        this.keyboardSensor = ks;
    }

    /**
     * This method foes throw levels, creates a gameLevel and runs it until the game is over.
     * @param levels the game levels information.
     * */
    public void runLevels(List<LevelInformation> levels) {
        Animation endScreen = null;
        for (LevelInformation levelInfo : levels) {
            GameLevel level = new GameLevel(levelInfo, this.keyboardSensor, this.animationRunner, this.score);
            level.initialize();
            level.run();

            if (level.wonLevel()) {
                endScreen = new GameOver(true, this.score.getValue());
            }

            if (level.lostLevel()) {
                endScreen = new GameOver(false, this.score.getValue());
                break;
            }
        }
        animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, KeyboardSensor.SPACE_KEY, endScreen));
    }
}
// ID 318646072
import animations.AnimationRunner;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import levels.DirectHit;
import levels.RandomLevel;
import levels.ThugMickey;
import levels.WideEasy;
import logic.GameFlow;
import logic.LevelInformation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

/**
 * A Ass6Game class.
 *
 * @author Asaf Mesilaty
 */
public class Ass6Game {
    /**
     * This method create game object, initialize it and run the game.
     * @param args is a string array that in this case doesn't get any value.
     * */
    public static void main(String[] args) {
       GUI gui = new GUI("AM Arkanoid", 800, 600);
        AnimationRunner ar = new AnimationRunner(gui);
        KeyboardSensor ks = gui.getKeyboardSensor();
        GameFlow gameFlow = new GameFlow(ar, ks);

        TreeMap<String, LevelInformation> levels = new TreeMap<>();
        levels.put("1", new DirectHit());
        levels.put("2", new WideEasy());
        levels.put("3", new ThugMickey());
        levels.put("4", new RandomLevel());

        List<LevelInformation> lvls = new LinkedList<>();
        for (String lvl : args) {
            if (levels.containsKey(lvl)) {
                lvls.add(levels.get(lvl));
            }
        }
        if (lvls.size() == 0) {
            gameFlow.runLevels(new ArrayList<>(levels.values()));
        } else {
            gameFlow.runLevels(lvls);
        }
        gui.close();

    }
}

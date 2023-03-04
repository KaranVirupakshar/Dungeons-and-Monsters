package view;

import controller.GameSwingController;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * KeyBoardAdapterImpl class represents a container to handle all the keyboard presses in the game.
 * Actions include player movement, player picking rubies, sapphires, diamonds, arrows. It also
 * includes shooting of arrows with a series of keys.
 */
class KeyBoardAdapterImpl extends KeyAdapter {

  private final GameSwingController controller;
  private final GameView view;
  private boolean direction;
  private boolean movement;
  private String directionUtil;

  /**
   * A constructor to initialize the Listener with the GUI controller and the GUI view.
   *
   * @param controller represents the controller of the GUI game.
   * @param view represents the GUI view.
   */
  public KeyBoardAdapterImpl(GameSwingController controller, GameView view) {
    this.controller = controller;
    this.view = view;
    direction = false;
    movement = true;
    directionUtil = "";
  }

  /**
   * Captures key press and performs actions.
   *
   * @param e represents a key event.
   */
  @Override
  public void keyPressed(KeyEvent e) {
    if ((e.getKeyCode() == KeyEvent.VK_RIGHT) && movement) {
      controller.move("Right");
    }
    if ((e.getKeyCode() == KeyEvent.VK_LEFT) && movement) {
      controller.move("Left");
    }
    if ((e.getKeyCode() == KeyEvent.VK_UP) && movement) {
      controller.move("Up");
    }
    if ((e.getKeyCode() == KeyEvent.VK_DOWN) && movement) {
      controller.move("Down");
    }
    if ((e.getKeyCode() == KeyEvent.VK_LEFT) && !movement) {
      direction = true;
      directionUtil = "Left";
      view.displayPlayerAction("Shooting direction set as left. Pick distance now", 1200);
    }
    if ((e.getKeyCode() == KeyEvent.VK_RIGHT) && !movement) {
      direction = true;
      directionUtil = "Right";
      view.displayPlayerAction("Shooting direction set as right. Pick distance now", 1200);
    }
    if ((e.getKeyCode() == KeyEvent.VK_DOWN) && !movement) {
      direction = true;
      directionUtil = "Down";
      view.displayPlayerAction("Shooting direction set as down. Pick distance now", 1200);
    }
    if ((e.getKeyCode() == KeyEvent.VK_UP) && !movement) {
      direction = true;
      directionUtil = "Up";
      view.displayPlayerAction("Shooting direction set as up. Pick distance now", 1200);
    }

    if ((e.getKeyCode() > KeyEvent.VK_0) && (e.getKeyCode() < KeyEvent.VK_9) && direction) {
      movement = true;
      controller.shootArrow(directionUtil, Integer.parseInt(String.valueOf(e.getKeyChar())));
      directionUtil = "";
    }

    if (e.getKeyCode() == KeyEvent.VK_R) {
      controller.pickTreasure("Ruby");
    }
    if (e.getKeyCode() == KeyEvent.VK_D) {
      controller.pickTreasure("Diamond");
    }
    if (e.getKeyCode() == KeyEvent.VK_S) {
      controller.pickTreasure("Sapphire");

    }
    if (e.getKeyCode() == KeyEvent.VK_A) {
      controller.pickArrow();
    }
  }

  /**
   * Captures key released and performs actions.
   *
   * @param e represents a key event.
   */
  @Override
  public void keyReleased(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
      direction = false;
      movement = false;
      view.displayPlayerAction("All Set To Shoot. Pick direction now", 1200);
    }
  }
}

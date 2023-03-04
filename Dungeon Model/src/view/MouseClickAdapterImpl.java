package view;

import controller.GameSwingController;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * MouseClickAdapterImpl class represents a container to handle all the mouse clicks in the game.
 * Actions include player movement, player picking rubies, sapphires, diamonds, arrows. It also
 * includes shooting of arrows.
 */
class MouseClickAdapterImpl extends MouseAdapter {

  private final GameSwingController controller;

  /**
   * A constructor to initialize the Listener with the GUI controller.
   *
   * @param controller represents the controller of the GUI game.
   */
  public MouseClickAdapterImpl(GameSwingController controller) {
    this.controller = controller;
  }

  /**
   * Captures mouse click and performs actions.
   *
   * @param e represents a mouse click event.
   */
  @Override
  public void mouseReleased(MouseEvent e) {
    super.mouseReleased(e);
    switch (e.getComponent().getName()) {
      case "Up" :
        controller.move("Up");
        break;

      case "Down" :
        controller.move("Down");
        break;

      case "Left" :
        controller.move("Left");
        break;

      case "Right" :
        controller.move("Right");
        break;

      case "Ruby" :
        controller.pickTreasure("Ruby");
        break;

      case "Diamond" :
        controller.pickTreasure("Diamond");
        break;

      case "Sapphire" :
        controller.pickTreasure("Sapphire");
        break;

      case "Arrow" :
        controller.pickArrow();
        break;

      case "Current Location" :
        if (e.getX() > 0 && e.getX() < 25 && e.getY() > 120 && e.getY() < 180) {
          controller.move("Left");
        }
        if (e.getX() > 113 && e.getX() < 177 && e.getY() > 270 && e.getY() < 290) {
          controller.move("Down");
        }
        if (e.getX() > 264 && e.getX() < 289 && e.getY() > 123 && e.getY() < 184) {
          controller.move("Right");
        }
        if (e.getX() > 115 && e.getX() < 175 && e.getY() > 5 && e.getY() < 35) {
          controller.move("Up");
        }
        if (e.getX() > 49 && e.getX() < 63 && e.getY() > 92 && e.getY() < 107) {
          controller.pickTreasure("Ruby");
        }
        if (e.getX() > 49 && e.getX() < 63 && e.getY() > 208 && e.getY() < 222) {
          controller.pickTreasure("Diamond");
        }
        if (e.getX() > 191 && e.getX() < 205 && e.getY() > 85 && e.getY() < 104) {
          controller.pickTreasure("Sapphire");
        }
        if (e.getX() > 198 && e.getX() < 220 && e.getY() > 226 && e.getY() < 238) {
          controller.pickArrow();
        }
        break;

      case "Grid Location" :
        if (e.getX() > 50 && e.getX() < 78 && e.getY() > 120 && e.getY() < 130) {
          controller.move("Down");
        }
        if (e.getX() > 50 && e.getX() < 78 && e.getY() > 1 && e.getY() < 10) {
          controller.move("Up");
        }
        if (e.getX() > 120 && e.getX() < 130 && e.getY() > 53 && e.getY() < 80) {
          controller.move("Right");
        }
        if (e.getX() > 1 && e.getX() < 10 && e.getY() > 53 && e.getY() < 80) {
          controller.move("Left");
        }
        if (e.getX() > 23 && e.getX() < 31 && e.getY() > 38 && e.getY() < 46) {
          controller.pickTreasure("Ruby");
        }
        if (e.getX() > 23 && e.getX() < 31 && e.getY() > 90 && e.getY() < 98) {
          controller.pickTreasure("Diamond");
        }
        if (e.getX() > 86 && e.getX() < 92 && e.getY() > 35 && e.getY() < 45) {
          controller.pickTreasure("Sapphire");
        }
        if (e.getX() > 85 && e.getX() < 99 && e.getY() > 98 && e.getY() < 108) {
          controller.pickArrow();
        }
        break;

      default:
        /*default block has been left empty on purpose so that view does "nothing" when the user
        clicks on an invalid space. This provides for a better user experience when compared to
        showing an error message for a click on an invalid space.
         */
    }
  }
}

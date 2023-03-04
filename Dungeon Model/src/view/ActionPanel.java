package view;

import controller.GameSwingController;

/**
 * ActionPanel interface represents the framework for building a Panel that can control player's
 * actions. It is responsible for configuring the mouse click to different components in the panel.
 * Since the classes using this framework exists in the package, this class is made as
 * package-private.
 */
interface ActionPanel {

  /**
   * Configures the mouse click events for player actions panel. This allows the player
   * to make moves in north, south, east or west. It also allows for player to pick up
   * sapphires, diamonds, rubies, arrows. It also allows for the player to shoot the arrow
   * in a particular direction at a particular distance.
   *
   * @param controller represents the controller for the view.
   * @param view represents the GUI representation of the game.
   */
  void configurePlayerActions(GameSwingController controller, GameView view);

}

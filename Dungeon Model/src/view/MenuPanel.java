package view;

import controller.GameSwingController;

/**
 * MenuPanel interface represents a framework for creation of menubar to handle different aspects
 * of the game. Since the classes using this framework exists in the package, this class is made as
 * package-private.
 */
interface MenuPanel {

  /**
   * Reads the menu items entered by the user through different JMenu components.
   *
   * @param controller represents the controller for the GUI game.
   * @param row represents the number of rows in the grid.
   * @param column represents the number of columns in the grid.
   * @param treasureNumber represents the treasure percentage in the game.
   * @param degree represents the interconnectivity degree.
   * @param otyugh represents the number of monster.
   * @param thieves represents the number of thieves.
   * @param pits represents the number of pits in the game.
   * @param status represents the wrapped status.
   */
  void menuOptions(GameSwingController controller, int row, int column,
      int treasureNumber, int degree, int otyugh, int thieves, int pits, boolean status);

  /**
   * Starts a new game by creating a new model with a new seed.
   *
   * @param controller represents the GUI controller.
   */
  void startNewGame(GameSwingController controller);

  /**
   * Restarts the game with the same seed.
   *
   * @param controller represents the GUI controller.
   */
  void restartGame(GameSwingController controller);
}

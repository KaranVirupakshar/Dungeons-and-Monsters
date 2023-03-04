package view;

import controller.GameSwingController;
import model.dungeonsanddragons.ReadOnlyGame;

/**
 * GameView interface represents the framework for creating a Graphical User Interface (GUI) based
 * view for the dungeons and dragons game. Player can use the view to adjust the settings of the
 * game, move around the dungeon, pick items of interest and navigate around the dungeon by
 * dodging the monsters, pits and thieves. Player wins the game by reaching the end location. Since
 * this acts as a view to play out the fantasy-based dungeon game, decision was made to keep the
 * interface public.
 */
public interface GameView {

  /**
   * Get the set of feature callbacks that the view can use. This provides means for view to be
   * 'wired' with the controller. Callback is cascading onto the panels that exist in the frame.
   *
   * @param controller the set of feature callbacks as a Features object
   */
  void setFeatures(GameSwingController controller);

  /**
   * Resets the focus on the appropriate part of the view that has the keyboard listener attached
   * to it, so that keyboard events will still flow through.
   */
  void resetFocus();

  /**
   * Generates the dungeon panel with the grid size entered by the user.
   *
   * @param rows represents the number of rows in the dungeon grid.
   * @param columns represents the number of columns in the dungeon grid.
   * @param readOnlyGame represents the read-only mode of the game.
   */
  void generateDungeonPanel(int rows, int columns, ReadOnlyGame readOnlyGame);

  /**
   * Updates the dungeon panel to display the nodes that have only been visited by the player.
   *
   * @param readOnlyGame represents the read-only model of the game.
   */
  void updateDungeonPanel(ReadOnlyGame readOnlyGame);

  /**
   * Sets the toast message with the given string.
   *
   * @param message represents the string that needs to be displayed.
   */
  void displayPlayerAction(String message);

  /**
   * Sets the toast message with the given string.
   *
   * @param message represents the string that needs to be displayed.
   * @param delay represents the delay of the toast message.
   */
  void displayPlayerAction(String message, int delay);

  /**
   * Pop up message when the game gets over. Game gets over either when the player reaches the end
   * cave or gets killed or falls into pit.
   *
   * @param controller represents the GUI controller.
   * @param readOnlyGame represents the readOnly model of the game.
   * @param title represents the title of the Dialog box.
   */
  void gameOver(GameSwingController controller, ReadOnlyGame readOnlyGame, String title);

}

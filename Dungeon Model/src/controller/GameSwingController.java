package controller;

/**
 * GameSwingController interface represents a GameSwingController for Dungeons and Dragons game. It
 * can handle player moving through the dungeon, picking up treasure and arrows, killing the
 * monsters, escaping pits and thieves. It also conveys the state of the location at each move
 * and allows the player to quit the game, restart the game with same settings or new settings.
 * Since this acts as a controller to play out the fantasy-based dungeon game, a decision was made
 * to keep the interface public.
 */
public interface GameSwingController {

  /**
   * Plays a game of dungeons and dragons.
   */
  void playGame();

  /**
   * Takes the mouse click or keyboard input and calls the move method of the model.
   *
   * @param direction represents the direction of the move.
   */
  void move(String direction);

  /**
   * Takes the mouse click or keyboard input and calls the pick treasure of the model.
   *
   * @param treasure represents the treasure item to be picked.
   */
  void pickTreasure(String treasure);

  /**
   * Takes the mouse click or keyboard input and calls the pick arrow method of the model.
   */
  void pickArrow();

  /**
   * Takes the mouse click or keyboard input and calls the shoot arrow method of the model.
   *
   * @param direction represents the direction entered by the user.
   * @param distance represents the distance entered by the user.
   */
  void shootArrow(String direction, int distance);

  /**
   * Creates a model based on the inputs from the view.
   *
   * @param row represents the number of rows in the grid.
   * @param column represents the number of columns in the grid.
   * @param treasure represents the treasure percentage in the game.
   * @param interconnectivity represents the interconnectivity degree.
   * @param monster represents the number of monster.
   * @param thieves represents the number of thieves.
   * @param pits represents the number of pits in the game.
   * @param wrapping represents the wrapped status.
   */
  void createModel(int row, int column, boolean wrapping, int interconnectivity,
      int treasure, int monster, int thieves, int pits);

  /**
   * Resets the model based on the inputs from the view. Model is reset using the same seed to
   * retain the node locations, and contents.
   *
   * @param row represents the number of rows in the grid.
   * @param column represents the number of columns in the grid.
   * @param treasure represents the treasure percentage in the game.
   * @param interconnectivity represents the interconnectivity degree.
   * @param monster represents the number of monster.
   * @param thieves represents the number of thieves.
   * @param pits represents the number of pits in the game.
   * @param wrapping represents the wrapped status.
   */
  void resetModel(int row, int column, boolean wrapping,
      int interconnectivity, int treasure, int monster, int thieves, int pits);

}

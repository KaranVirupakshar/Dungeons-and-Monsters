package controller;

import model.dungeonsanddragons.Direction;
import model.dungeonsanddragons.Game;

/**
 * MovePlayer command represents the execution of the player movement, by the controller, in the
 * specified direction. movePlayer method of the model is called to make the player direction in the
 * dungeon. Class is kept as package-private since this class is used only internally by the
 * controller.
 */
class MovePlayer implements GameCommand {

  private final Direction direction;

  /**
   * Initializes the command with the specified direction.
   *
   * @param move represents the direction of the direction.
   * @throws IllegalArgumentException if the move value is invalid.
   */
  public MovePlayer(String move) {

    if (move.equals("U") || move.equals("Up")) {
      this.direction = Direction.UP;
    }
    else if (move.equals("D") || move.equals("Down")) {
      this.direction = Direction.DOWN;
    }
    else if (move.equals("L") || move.equals("Left")) {
      this.direction = Direction.LEFT;
    }
    else if (move.equals("R") || move.equals("Right")) {
      this.direction = Direction.RIGHT;
    }
    else {
      throw new IllegalArgumentException("Didn't offer that Option!");
    }
  }

  /**
   * Executes the specified command by calling on the specified method of the model.
   *
   * @param model represents the game model that'll interact with the controller.
   * @throws IllegalStateException depending on the exact command that is executed.
   * @throws IllegalArgumentException if the model is invalid.
   */
  @Override
  public void execute(Game model)
      throws IllegalStateException, IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Null arguments not allowed");
    }
    model.movePlayer(direction);
  }
}

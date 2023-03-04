package controller;

import model.dungeonsanddragons.Direction;
import model.dungeonsanddragons.Game;

/**
 * Shoot Arrow command represents the execution of the player shooting the arrow, by the
 * controller, in the specified distance and direction. pickArrow method of the model is called
 * to make the player add the arrow to its armory. Class is kept as package-private since this
 * class is used only internally by the controller.
 */
class ShootArrow implements GameCommand {

  private final int distance;
  private final Direction direction;

  /**
   * Initializes the command with the specified distance and the direction.
   *
   * @param distance represents the distance at which the arrow needs to be shot.
   * @param direction represents the direction at which the arrow needs to be shot.
   * @throws IllegalArgumentException if the distance or direction is invalid.
   */
  public ShootArrow(int distance, String direction) {
    this.distance = distance;
    if (direction.equals("U") || direction.equals("Up")) {
      this.direction = Direction.UP;
    }
    else if (direction.equals("D") || direction.equals("Down")) {
      this.direction = Direction.DOWN;
    }
    else if (direction.equals("L") || direction.equals("Left")) {
      this.direction = Direction.LEFT;
    }
    else if (direction.equals("R") || direction.equals("Right")) {
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
    model.shootArrow(distance, direction);
  }
}

package controller;

import model.dungeonsanddragons.Game;
import model.dungeonsanddragons.Treasure;

/**
 * PickTreasure command represents the execution of the player picking up treasures from current
 * location, by the controller. pickTreasure method of the model is called to make the player add
 * the arrow to its armory. Class is kept as package-private since this class is used only
 * internally by the controller.
 */
class PickTreasure implements GameCommand {

  private final Treasure treasure;

  /**
   * Initializes the command with the specified treasure that needs to be picked.
   *
   * @param treasure represents specified treasure that needs to be picked.
   * @throws IllegalArgumentException if the treasure value is invalid.
   */
  public PickTreasure(String treasure) {

    if (treasure.equals("R") || treasure.equals("Ruby")) {
      this.treasure = Treasure.RUBY;
    }
    else if (treasure.equals("D") || treasure.equals("Diamond")) {
      this.treasure = Treasure.DIAMOND;
    }
    else if (treasure.equals("S") || treasure.equals("Sapphire")) {
      this.treasure = Treasure.SAPPHIRE;
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
    model.pickTreasure(treasure);
  }
}

package controller;

import model.dungeonsanddragons.Game;

/**
 * PickArrow command represents the execution of the player picking up arrows from current
 * location, by the controller. pickArrow method of the model is called to make the player add
 * the arrow to its armory. Class is kept as package-private since this class is used only
 * internally by the controller.
 */
class PickArrow implements GameCommand {

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
    model.pickArrow();
  }
}

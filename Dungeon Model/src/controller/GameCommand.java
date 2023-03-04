package controller;

import model.dungeonsanddragons.Game;

/**
 * GameCommand represents the framework for building the different commands that the game supports.
 * GameConsoleControllerImpl can make use of these commands to interact with the model. Since the
 * classes using this framework exists in the package, this class is made as package-private.
 */
interface GameCommand {

  /**
   * Executes the specified command by calling on the specified method of the model.
   *
   * @param model represents the game model that'll interact with the controller.
   * @throws IllegalStateException depending on the exact command that is executed.
   * @throws IllegalArgumentException if the model is invalid.
   */
  void execute(Game model) throws IllegalStateException, IllegalArgumentException;
}

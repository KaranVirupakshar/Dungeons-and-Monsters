package view;

import model.dungeonsanddragons.ReadOnlyGame;

/**
 * DescriptionPanel interface represents the framework for building a Panel that displays player's
 * description. Description contains the number of sapphires, rubies, diamonds, and arrows that the
 * player currently has. Since the classes using this framework exists in the package, this class
 * is made as package-private.
 */
interface DescriptionPanel {

  /**
   * Initialize the player description at the start of the game.
   *
   * @param readOnlyGame represents a read-only model of the game.
   */
  void initializePlayerDescription(ReadOnlyGame readOnlyGame);

  /**
   * Update the player description as and when the description gets updated.
   *
   * @param readOnlyGame represents a read-only model of the game.
   */
  void updatePlayerDescription(ReadOnlyGame readOnlyGame);

}

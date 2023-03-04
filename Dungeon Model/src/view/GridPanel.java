package view;

import model.dungeonsanddragons.ReadOnlyGame;

import java.awt.image.BufferedImage;

/**
 * GridPanel interface represents the dungeon in a grid. All the locations bar the players current
 * location is not visible. Locations become visible as and when the player explores them. Since
 * the classes using this framework exists in the package, this class is made as package-private.
 */
interface GridPanel {

  /**
   * Initialises the dungeon grid with given number of rows and columns. Cascaded down from the
   * main game panel.
   *
   * @param rows represents the number of rows in the dungeon.
   * @param columns represents the number of columns in the dungeon.
   */
  void initializeDungeon(int rows, int columns);

  /**
   * Draws the player's current location. It also returns the same image representing the players
   * current location to further set in the player location panel.
   *
   * @param readOnlyGame represents the read-only version of the model.
   * @returns the image representing the players current location.
   */
  BufferedImage dungeonNodeAtPlayersLocation(ReadOnlyGame readOnlyGame);
}

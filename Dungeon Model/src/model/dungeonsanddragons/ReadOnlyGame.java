package model.dungeonsanddragons;

import java.util.List;

/**
 * ReadOnlyGame is an interface that provides a framework for building a read only endpoint for the
 * view/user to read the state of the game. View/User can get details about the dungeon node,
 * player details, game status throughout the game. Since this is supposed to be a read-only
 * endpoint for users/view, this class is kept as public.
 */
public interface ReadOnlyGame {

  /**
   * Gets the ID of the vertex representing a single location (cave or a tunnel). Id starts
   * from 0 and goes up to (rows * columns) - 1.
   *
   * @return the ID of the vertex representing a single location (cave or a tunnel). Id starts
   *          from 0 and goes up to (rows * columns) - 1.
   */
  int getLocationId();

  /**
   * Gets the possible moves from the location. UP, DOWN, LEFT and RIGHT are four possible moves.
   * Format that is followed is '[ list of moves ]'. String is used since this is a read-only
   * endpoint for the customers and the data is already structured before passing the String.
   *
   * @return the possible moves from the location. UP, DOWN, LEFT and RIGHT are four possible
   *          moves.
   */
  List<String> getPossibleMoves();

  /**
   * Gets the treasure housed in a particular location. Only a cave can house the treasure.
   * Format that is followed is '[ list of treasure ]', '[ ]' if empty. 'Tunnel can't hold
   * treasure' if it's a tunnel. String is used since this is a read-only endpoint for the
   * customers and the data is already structured before passing the String.
   *
   * @return the treasure housed in a particular location. Only a cave can house the treasure.
   */
  List<Treasure> getTreasure();

  /**
   * Gets the type of the location. ReadOnlyGame can be of 2 types, either a cave or a tunnel.
   * String is used since this is a read-only endpoint for the customers and the data is already
   * structured before passing the String.
   *
   * @return the type of the location. ReadOnlyGame can be of 2 types, either a cave or a tunnel.
   */
  String getLocationType();

  /**
   * Gets the monsters that are located them in the dungeon.
   *
   * @return the monsters that are located them in the dungeon.
   */
  String getMonster();

  /**
   * Gets the health of the monster.
   *
   * @return the health of the monster.
   */
  int getMonsterHealth();

  /**
   * Gets the monsters that are located them in the dungeon.
   *
   * @return the monsters that are located them in the dungeon.
   */
  List<Weapon> getArrows();

  /**
   * Gets the row number of the location in 2d grid.
   *
   * @return the row number of the location in 2d grid.
   */
  int getRowIn2D();

  /**
   * Gets the column number of the location in 2d grid.
   *
   * @return the column number of the location in 2d grid.
   */
  int getColIn2D();

  /**
   * Gets the type of dungeon node. It can be a cave or a tunnel.
   *
   * @return the type of dungeon node. It can be a cave or a tunnel.
   */
  String nodeT();

  /**
   * Gets the count of rubies in the node.
   *
   * @return the count of rubies in the node.
   */
  int getRubyCount();

  /**
   * Gets the count of diamonds in the node.
   *
   * @return the count of diamonds in the node.
   */
  int getDiamondCount();

  /**
   * Gets the count of sapphires in the node.
   *
   * @return the count of sapphires in the node.
   */
  int getSapphireCount();

  /**
   * Gets the count of arrows in the node.
   *
   * @return the count of arrows in the node.
   */
  int getArrowCount();

  /**
   * Gets the count of rubies from the player's bag.
   *
   * @return the count of rubies from the player's bag.
   */
  int getPlayerRubyCount();

  /**
   * Gets the count of diamonds from the player's bag.
   *
   * @return the count of diamonds from the player's bag.
   */
  int getPlayerDiamondCount();

  /**
   * Gets the count of sapphires from the player's bag.
   *
   * @return the count of sapphires from the player's bag.
   */
  int getPlayerSapphireCount();

  /**
   * Gets the count of arrows from the player's bag.
   *
   * @return the count of arrows from the player's bag.
   */
  int getPlayerArrowCount();

  /**
   * Gets the type of smell that the player is currently experiencing.
   *
   * @return the type of smell that the player is currently experiencing.
   */
  String getSmell();

  /**
   * Gets the type of sound that the player is currently hearing.
   *
   * @return the type of sound that the player is currently hearing.
   */
  String getSound();

  /**
   * Gets the thief in the given node, null otherwise.
   *
   * @return the thief in the given node, null otherwise.
   */
  String getThief();

  /**
   * Gets the pit in the given node, null otherwise.
   *
   * @return the pit in the given node, null otherwise.
   */
  String getPit();
}

package model.dungeonsanddragons;

import java.util.List;

/**
 * Player interface represents a framework for setting up and managing player actions in the
 * fantasy-based role-playing game. It allows for movement, pick treasure or arrows and shoot
 * arrows once the player is in the dungeon. Since the classes using this framework exists in the
 * package, this class is made as package-private.
 */
interface Player {

  /**
   * Gets the name of the player.
   *
   * @return the name of the player.
   */
  String getName();

  /**
   * Gets the current location of the player. It can either be a cave or a tunnel.
   *
   * @return the current location of the player. It can either be a cave or a tunnel.
   */
  DungeonNode getLocation();

  /**
   * Sets the location of the player. It can either be a cave or a tunnel.
   *
   * @param location is the location, either a cave or tunnel, that the player's location must
   *                 be updated to.
   * @throws IllegalArgumentException if the location is null.
   */
  void setLocation(DungeonNode location) throws IllegalArgumentException;

  /**
   * Gets the last location that the player has been to.
   *
   * @return the last location that the player has been to.
   */
  DungeonNode getPreviousLocation();

  /**
   * Sets the last location that the player has been to.
   *
   * @param previousLocation represents last location that the player has been to.
   */
  void setPreviousLocation(DungeonNode previousLocation);

  /**
   * Clears the player's treasure.
   */
  void clearTreasure();

  /**
   * Clears the player's arrows from the armory.
   */
  void clearArrows();

  /**
   * Gets the ruby count in the player's bag.
   *
   * @return the ruby count from the player's bag.
   */
  int getPlayerRubyCount();

  /**
   * Gets the diamond count in the player's bag.
   *
   * @return the diamond count from the player's bag.
   */
  int getPlayerDiamondCount();

  /**
   * Gets the sapphire count in the player's bag.
   *
   * @return the sapphire count from the player's bag.
   */
  int getPlayerSapphireCount();

  /**
   * Gets the list of treasure collected by the player.
   *
   * @return the list of the treasure collected by the player.
   */
  List<Treasure> getPlayerTreasures();

  /**
   * Adds the treasure  to player's bag. A player can pick treasure only from the location that a
   * player is present in.
   *
   * @param treasure represents the treasure that the player wants to pick.
   * @throws IllegalArgumentException if the treasure that is being added is null.
   */
  void addTreasure(Treasure treasure);

  /**
   * Gets the arrows that the player has in his armoury.
   *
   * @return the arrows that the player has in his armoury.
   */
  List<Weapon> getPlayerArrows();

  /**
   * Picks an arrow from its current location, that can be used to slay a monster.
   */
  void addArrow();

  /**
   * Removes an arrow from the player's bag.
   */
  void removeArrow();

  /**
   * Sets the player's health to 0. Player starts with health 100.
   */
  void setPlayerHealth();

  /**
   * Gets the player's health.
   */
  int getPlayerHealth();
}

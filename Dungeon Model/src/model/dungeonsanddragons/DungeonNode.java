package model.dungeonsanddragons;

import java.util.List;

/**
 * DungeonNode interface provides a framework for creation of locations in the dungeon. Tunnel and
 * caves are 2 types of locations that are available. ReadOnlyGame is decided by the number of
 * connections. 1, 3 and 4 connections represent a cave. 2 connections represent a tunnel. Each
 * location has to be initialized with treasures/arrows/monsters/pits/thieves depending on the
 * creation of dungeon. Since the classes using this framework exists in the package, this class is
 * made as package-private.
 */
interface DungeonNode {

  /**
   * Gets the identifier of the dungeon. Starts with 0 and goes on till vertices - 1.
   *
   * @return the identifier of the dungeon. Starts with 0 and goes on till vertices - 1.
   */
  int getDungeonNodeIdentifier();

  /**
   * Gets the type of the node. It can be a cave or a tunnel.
   *
   * @return the type of the node.
   */
  DungeonNodeType getNodeIsA();

  /**
   * Sets the type of node as tunnel. Initially each node is set as cave.
   */
  void setAsATunnel();

  /**
   * Sets the east connection to 1 indicating that the node is connected to the adjacent node
   * on the right.
   */
  void setEastConnection();

  /**
   * Sets the north connection to 1 indicating that the node is connected to the adjacent node
   * on the north.
   */
  void setNorthConnection();

  /**
   * Sets the west connection to 1 indicating that the node is connected to the adjacent node
   * on the left.
   */
  void setWestConnection();

  /**
   * Sets the south connection to 1 indicating that the node is connected to the adjacent node
   * on the south.
   */
  void setSouthConnection();

  /**
   * Gets the status of the connection of the node on the east side.
   *
   * @return 1 if node towards east is connected, 0 otherwise.
   */
  int getEastConnection();

  /**
   * Gets the status of the connection of the node on the north side.
   *
   * @return 1 if node towards north is connected, 0 otherwise.
   */
  int getNorthConnection();

  /**
   * Gets the status of the connection of the node on the south side.
   *
   * @return 1 if node towards south is connected, 0 otherwise.
   */
  int getSouthConnection();

  /**
   * Gets the status of the connection of the node on the west side.
   *
   * @return 1 if node towards west is connected, 0 otherwise.
   */
  int getWestConnection();

  /**
   * Add a treasure to the node. Treasure can only be added to the node.
   *
   * @param treasure represents the treasure that is to be added to the node.
   */
  void addCaveTreasure(Treasure treasure) throws IllegalArgumentException ;

  /**
   * Removes a treasure to the node. Treasure can only be removed to the cave.
   *
   * @param treasure represents the treasure that is to be removed to the cave.
   */
  void removeCaveTreasure(Treasure treasure)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * Gets all the treasures that are contained in the given node.
   *
   * @return the treasures that are contained in the given node.
   */
  List<Treasure> getCaveTreasures();

  /**
   * Adds a monster to the node.
   */
  void addMonster();

  /**
   * Sets the health of the monster when it's hit by an arrow.
   */
  void updateMonster();

  /**
   * Gets the monster that is existing in the given node.
   *
   * @return the monster that exists in the node, null otherwise.
   */
  Monster getMonster();

  /**
   * Gets the thief that is existing in the given node.
   *
   * @return the thief that exists in the node, null otherwise.
   */
  Enemy getThief();

  /**
   * Adds a thief to the given dungeon node.
   */
  void addThief();

  /**
   * Gets the pit that is existing in the given node.
   *
   * @return the pit that exists in the node, null otherwise.
   */
  Obstacle getPit();

  /**
   * Adds the pit to the dungeon.
   */
  void addPit();

  /**
   * Adds the arrow to the dungeon node.
   */
  void addArrows();

  /**
   * Removes a single arrow from the dungeon node.
   */
  void removeArrow();

  /**
   * Gets all the arrows that are existing in the given node.
   *
   * @return the arrows that are existing in the given node.
   */
  List<Weapon> getArrows();
}

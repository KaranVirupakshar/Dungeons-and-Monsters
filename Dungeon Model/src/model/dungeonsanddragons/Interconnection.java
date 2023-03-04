package model.dungeonsanddragons;

/**
 * Interconnection interface provides a framework for creation of edges between a source dungeon
 * node and a destination node. Edges can exist between any two caves or two tunnels or between a
 * tunnel or a cave. Since the classes using this framework exists in the package, this class is
 * made as package-private.
 */
interface Interconnection {

  /**
   * Gets the source node of the interconnection pair.
   *
   * @return the source node of the interconnection pair.
   */
  DungeonNode getSource();

  /**
   * Gets the destination node of the interconnection pair.
   *
   * @return the destination node of the interconnection pair.
   */
  DungeonNode getDestination();
}

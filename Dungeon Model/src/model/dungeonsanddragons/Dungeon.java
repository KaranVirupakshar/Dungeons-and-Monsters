package model.dungeonsanddragons;

import java.util.List;

/**
 * Dungeon represents an interface that provides the framework for creation of Dungeon. The dungeon
 * is represented by a 2 dimensional grid of given size.  Each location in the grid represents a
 * location in the dungeon where a player can explore and can be connected to at most four (4)
 * other locations: one to the north, one to the east, one to the south, and one to the west. Since
 * the classes using this framework exists in the package, this class is made as package-private.
 */
interface Dungeon {

  /**
   * Creates a wrapped or an unwrapped dungeon with given degree of interconnectivity. It also
   * adds random number of treasures to the given percentage of caves in the dungeon.
   *
   * @param interconnectivity represents the degree of interconnectivity of dungeon.
   * @param treasurePercentage represents the percentage of caves that the treasure needs to be
   *                           allocated.
   * @param numberOfMonsters represents the number of monsters in the dungeon.
   * @param thieves represents the number of thieves in the game.
   * @param pits represents the number of pits that should be added to the dungeon.
   * @throws IllegalStateException if the dungeon size is too small to enforce 5 as minimum
   *          distance between execute and end. It also considers a scenario where the dungeon
   *          cannot be created with incorrect degree of interconnectivity
   * @throws IllegalArgumentException if the parameters are invalid
   */
  void create(int interconnectivity, int treasurePercentage, int numberOfMonsters,
      int thieves, int pits) throws IllegalStateException, IllegalArgumentException;

  /**
   * Gets all the edges when N x M nodes are created initially.
   *
   * @return the list of horizontal and vertical edges between all the nodes.
   */
  List<Interconnection> getAllEdges();

  /**
   * Gets the spanning tree edges after the graph is created with dungeon nodes. Spanning tree
   * edges have total of nodes-1 edges.
   *
   * @return the list of edges that form the spanning tree.
   */
  List<Interconnection> getSpanningTreeEdges();

  /**
   * Gets the remaining edges that are leftover after the spanning edges are created. These edges
   * are used to modify the degree of interconnectivity.
   *
   * @return the remaining edges that are leftover after the spanning edges are created.
   */
  List<Interconnection> getRemainingEdges();

  /**
   * Gets all the nodes, can either be a tunnel depending on the number of doors, of the dungeon.
   *
   * @return all the nodes of the dungeon.
   */
  List<DungeonNode> getDungeonNodes();

  /**
   * Gets all the possible execute and end cave pairs that can be chosen as the execute or end cave.
   * Minimum distance between execute and end cave is 5.
   *
   * @return the all possible execute and end cave pairs.
   */
  List<Interconnection> getPossibleStartEndPairs();

  /**
   * Gets the cave that is picked as the starting cave.
   *
   * @return the cave that is picked as the starting cave.
   */
  DungeonNode getStartingCave();

  /**
   * Gets the cave that is picked as the ending cave.
   *
   * @return the cave that is picked as the ending cave.
   */
  DungeonNode getEndingCave();

  /**
   * Returns the dungeon as a 2d grid of caves, tunnels and interconnecting edges.
   */
  String printDungeon();

  /**
   * Gets the 2d representation of the grid.
   *
   * @return the 2d representation of the grid.
   */
  DungeonNode[][] getDungeonAs2D();

  /**
   * Gets the number of rows in the dungeon grid.
   *
   * @return the number of rows in the dungeon grid.
   */
  int getNoOfRows();

  /**
   * Gets the number of columns in the dungeon grid.
   *
   * @return the number of columns in the dungeon grid.
   */
  int getNoOfColumns();

  /**
   * Adds random treasure random number of times to the allocated percentage of caves.
   *
   * @param treasurePercentage represents the percentage of caves to which the treasure should be
   *                           added.
   * @throws IllegalArgumentException if the treasure percentage is lesser than 0 or greater than
   *                            100.
   */
  void allocateTreasureToCaves(int treasurePercentage) throws IllegalArgumentException;

  /**
   * Adds monsters to the dungeon. Monsters can occupy only the caves and exist in isolation.
   * Atleast one monster exists in the end location of the dungeon.
   *
   * @param numberOfMonsters represents the number of monsters that needs to be added in the game.
   *                         If the number of monsters specified are greater than the caves, then
   *                         number of monsters is considered equal to the number of caves.
   * @throws IllegalArgumentException if the number of monsters lesser than 1 or greater than 20.
   */
  void allocateMonsterToCaves(int numberOfMonsters) throws IllegalArgumentException;

  /**
   * Adds arrows to the dungeon. Arrows can be placed in either tunnels or caves.
   *
   * @param arrowPercentage represents the percentage of tunnels/ caves that the arrows needs to be
   *                        added to. Percentage is same as treasure percentage.
   * @throws IllegalArgumentException if the treasure percentage is lesser than 0 or greater than
   *                            100.
   */
  void allocateArrowsToDungeon(int arrowPercentage) throws IllegalArgumentException;

  /**
   * Gets all the locations of all the caves that are one spot away.
   *
   * @return the locations of all the caves that are one spot away.
   */
  List<Interconnection> getOneLocationAway();

  /**
   * Gets all the locations of all the caves that are two spots away.
   *
   * @return the locations of all the caves that are two spots away.
   */
  List<Interconnection> getTwoLocationAway();

  /**
   * Adds thieves to the dungeon. Thieves can only exist in the tunnels and one tunnel can
   * occupy only one thief.
   *
   * @param thieves represents the number of thieves that needs to be added to the dungeon.
   */
  void allocateThievesToTunnels(int thieves);

  /**
   * Adds pits to the dungeon. Pits can only exist in the caves and one cave can
   * have only one pit at a given time.
   *
   * @param pits represents the number of pits that needs to be added to the dungeon.
   */
  void allocatePitsToCaves(int pits);

}

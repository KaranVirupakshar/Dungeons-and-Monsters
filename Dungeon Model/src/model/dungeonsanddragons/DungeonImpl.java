package model.dungeonsanddragons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Vector;

/**
 * DungeonImpl class represents the dungeon in the fantasy-based role-playing game. In the game,
 * this dungeon is generated at random following some set of constraints, which includes the size,
 * wrapping, interconnectivity, resulting in a different network each time the game begins. There
 * should be a path from every cave in the dungeon to every other cave in the dungeon and the path
 * between the start and the end locations should be at least of length 5. Since the creation of
 * dungeon is an internal implementation and should not be exposed to the user, this is kept as
 * package-private.
 */
class DungeonImpl implements Dungeon {

  private final int locations;
  private final DungeonNodeFactory dungeonNodeFactory;
  private final List<DungeonNode> allDungeonNodes;
  private final List<Interconnection> allEdges;
  private final List<Interconnection> spanningTreeEdges;
  private final List<Interconnection> remainingEdges;
  private final Random random;
  private final List<Interconnection> possibleStartEndPairs;
  private final List<Integer>[] adjacentEdges;
  private int minimumEdges;
  private int edgeCount;
  private DungeonNode startingCave;
  private DungeonNode endingCave;
  private final DungeonNode[][] dungeonAs2D;
  private final int noOfRows;
  private final int noOfColumns;
  private final boolean wrapping;
  private final List<Interconnection> oneLocationAway;
  private final List<Interconnection> twoLocationAway;

  /**
   * A constructor to initialize the Dungeon with size N rows x M columns. This also creates a
   * player named 'Marcus' who will enter the dungeon from the starting node.
   *
   * @param noOfRows represents the number of rows in the dungeon.
   * @param noOfColumns represents the number of columns in the dungeon.
   * @param wrapping represents the status of wrap with which the dungeon needs to be created.
   * @throws IllegalArgumentException if rows, columns or wrapping status is illegal.
   */
  public DungeonImpl(int noOfRows, int noOfColumns, boolean wrapping, Random random) {

    if (!(noOfRows >= 5 && noOfRows <= 100) || !(noOfColumns >= 5 && noOfColumns <= 100)
        || Objects.isNull(random) || !(!wrapping || wrapping)) {
      throw new IllegalArgumentException("Invalid data in the parameters!");
    }

    this.locations = noOfRows * noOfColumns;
    this.noOfRows = noOfRows;
    this.noOfColumns = noOfColumns;
    this.wrapping = wrapping;
    allDungeonNodes = new ArrayList<>();
    this.random = random;
    allEdges = new ArrayList<>();
    spanningTreeEdges = new ArrayList<>();
    remainingEdges = new ArrayList<>();
    dungeonNodeFactory = new DungeonNodeFactory();
    possibleStartEndPairs = new ArrayList<>();
    adjacentEdges = new Vector[locations];
    dungeonAs2D = new DungeonNodeImpl[noOfRows][noOfColumns];
    minimumEdges = 0;
    edgeCount = 0;
    oneLocationAway = new ArrayList<>();
    twoLocationAway = new ArrayList<>();

    //Initializing vector

    for (int i = 0; i < locations; i++) {
      adjacentEdges[i] = new Vector<>();
    }
  }

  /**
   * Creates Dungeon nodes using a factory method. Each Dungeon node is assigned an identifier,
   * beginning from 0 and incrementing by 1.
   *
   * @param vertices represents the number of nodes that need to be created.
   */
  private void createDungeonNodes(int vertices) {
    if (vertices < 25) {
      throw new IllegalArgumentException("Illegal Number of vertices!");
    }

    for (int i = 0; i < vertices; i++) {
      allDungeonNodes.add(dungeonNodeFactory.createDungeonNode(i));
    }
  }

  /**
   * Creates horizontal and vertical edges between each node. Since this is an undirected graph
   * only 1 edge exists between 2 nodes.
   *
   * @param wrapping represents if the dungeon has to created with wrapping status true or false.
   */
  private void createEdgesBetweenNodes(boolean wrapping) {

    if (!(!wrapping || wrapping)) {
      throw new IllegalArgumentException("Illegal wrapping status!");
    }

    if (wrapping) {
      int j = 0;

      for (int i = 0; i < noOfRows; i++) {
        addEdge(allDungeonNodes.get(j), allDungeonNodes.get(j + (noOfColumns - 1)));
        j = j + noOfColumns;
      }

      int vertical = noOfColumns * (noOfRows - 1);

      for (int k = 0; k < noOfColumns; k++) {
        addEdge(allDungeonNodes.get(k), allDungeonNodes.get(k + vertical));
      }
    }

    int i = 0;
    int k = 0;
    int j = 0;

    while (k < noOfRows) {
      while (i < noOfColumns - 1) {
        addEdge(allDungeonNodes.get(j), allDungeonNodes.get(j + 1));
        i++;
        j++;
      }
      k++;
      i = 0;
      j++;
    }

    k = 0;
    j = 0;

    while (k < noOfColumns) {
      j = k;
      while (i < noOfRows - 1) {
        addEdge(allDungeonNodes.get(j), allDungeonNodes.get(j + noOfColumns));
        i++;
        j = j + noOfColumns;
      }
      i = 0;
      k++;
    }
  }

  /**
   * Adds edge between given source node and a destination node.
   *
   * @param source represents the source Dungeon DungeonNode.
   * @param destination represents the destination Dungeon DungeonNode.
   * @throws IllegalArgumentException if Dungeon cannot be created with given degree of"
   *            interconnectivity
   */
  private void addEdge(DungeonNode source, DungeonNode destination) {

    if (Objects.isNull(source) || Objects.isNull(destination)) {
      throw new IllegalArgumentException("Illegal edges!");
    }

    allEdges.add(new InterconnectionImpl(source, destination));
  }

  /**
   * Using Kruskal's algorithm to build the edges in the dungeon.
   *
   * @param interconnectivity represents the degree with which the interconnectivity needs to
   *                          be increased.
   */
  private void randomKruskalSpanningTree(int interconnectivity) {

    if (interconnectivity < 0) {
      throw new IllegalArgumentException("Illegal interconnectivity!");
    }

    int[] parent = new int[locations];
    setParents(parent);


    while (allEdges.size() > 0) {
      int randomPicker = (random.nextInt(allEdges.size()));
      Interconnection edge = allEdges.get(randomPicker);
      int setOfX = findParent(parent, edge.getSource().getDungeonNodeIdentifier());
      int setOfY = findParent(parent, edge.getDestination().getDungeonNodeIdentifier());

      if (setOfX == setOfY) {
        remainingEdges.add(allEdges.get(randomPicker));
      } else {
        spanningTreeEdges.add(edge);
        union(parent, setOfX, setOfY);
      }
      allEdges.remove(randomPicker);
    }

    if (interconnectivity <= remainingEdges.size()) {

      int addFromLeftover = 1;

      while (addFromLeftover <= interconnectivity) {
        int randomPicker = (random.nextInt(remainingEdges.size()));
        Interconnection edge = remainingEdges.get(randomPicker);
        spanningTreeEdges.add(edge);
        remainingEdges.remove(randomPicker);
        addFromLeftover++;
      }
    } else {
      throw new IllegalStateException("Dungeon cannot be created with this degree of"
          + " interconnectivity!");
    }
  }

  /**
   * Sets all the nodes as parents individually. This is done in the beginning before spanning
   * tree is created.
   *
   * @param parent represents all the nodes in the graph.
   */
  private void setParents(int[] parent) {

    if (parent.length < 0) {
      throw new IllegalArgumentException("Illegal number of parents");
    }

    for (int i = 0; i < locations; i++) {
      parent[i] = i;
    }
  }

  /**
   * Finds the parent of the given node.
   *
   * @param parent represents all the nodes in the graph.
   * @param location represents the node whose parent needs to be found.
   * @return the parent of the given node.
   */
  private int findParent(int[] parent, int location) {

    if (parent.length < 0 || location < 0) {
      throw new IllegalArgumentException("Illegal arguments");
    }

    if (parent[location] != location) {
      return findParent(parent, parent[location]);
    }
    return location;
  }

  /**
   * Forms a set with the parents of 2 given nodes.
   *
   * @param parent represents all the nodes in the graph.
   * @param src represents the source node whose parent needs to be combined with destination.
   * @param dst represents the destination node whose parent needs to be combined with source.
   */
  private void union(int[] parent, int src, int dst) {

    if (parent.length < 0 || src < 0 || dst < 0) {
      throw new IllegalArgumentException("Illegal arguments");
    }

    int setParentOfX = findParent(parent, src);
    int setParentOfY = findParent(parent, dst);
    parent[setParentOfY] = setParentOfX;
  }

  /**
   * Sets the north, east, west, south connections of each node.
   *
   * @param edgeList represents the spanning tree with given degree of interconnectivity.
   * @throws IllegalArgumentException if the edgeList is empty.
   */
  private void setDirections(List<Interconnection> edgeList) {

    if (edgeList.size() == 0) {
      throw new IllegalArgumentException("MST Edge Tree is empty!");
    }

    for (int i = 0; i < edgeList.size(); i++) {
      if (edgeList.get(i).getDestination().getDungeonNodeIdentifier()
          - edgeList.get(i).getSource().getDungeonNodeIdentifier() == 1) {
        edgeList.get(i).getSource().setEastConnection();
        edgeList.get(i).getDestination().setWestConnection();
      } else if (edgeList.get(i).getDestination().getDungeonNodeIdentifier()
          - edgeList.get(i).getSource().getDungeonNodeIdentifier() == (noOfColumns - 1)) {
        edgeList.get(i).getDestination().setEastConnection();
        edgeList.get(i).getSource().setWestConnection();
      } else if (edgeList.get(i).getDestination().getDungeonNodeIdentifier()
          - edgeList.get(i).getSource().getDungeonNodeIdentifier()
          == (noOfColumns * (noOfRows - 1))) {
        edgeList.get(i).getDestination().setSouthConnection();
        edgeList.get(i).getSource().setNorthConnection();
      } else {
        edgeList.get(i).getSource().setSouthConnection();
        edgeList.get(i).getDestination().setNorthConnection();
      }
    }
  }

  /**
   * Sets each node as cave or tunnel depending on the number of connections. 1, 3, 4 represents
   * cave and 2 connections represents a tunnel.
   *
   * @param dungeonNodes represents all the dungeonNodes of the dungeon.
   */
  private void setNodesAsCaveOrTunnel(List<DungeonNode> dungeonNodes) {

    if (dungeonNodes.size() == 0) {
      throw new IllegalArgumentException("Locations in the grid are empty!");
    }

    for (DungeonNode dungeonNode : dungeonNodes) {
      int count = dungeonNode.getEastConnection() + dungeonNode.getNorthConnection()
          + dungeonNode.getSouthConnection() + dungeonNode.getWestConnection();
      if (count == 2) {
        dungeonNode.setAsATunnel();
      }
    }
  }

  /**
   * Adds random treasure random number of times to the allocated percentage of caves.
   *
   * @param treasurePercentage represents the percentage of caves to which the treasure should be
   *                           added.
   * @throws IllegalArgumentException if the treasure percentage is lesser than 0 or greater than
   *                            100.
   */
  @Override
  public void allocateTreasureToCaves(int treasurePercentage) {

    if (treasurePercentage < 0 || treasurePercentage > 100) {
      throw new IllegalArgumentException("Illegal percentage of treasure!");
    }

    int i = 0;
    List<Treasure> treasures = new ArrayList<>(Arrays.asList(Treasure.values()));
    List<Integer> caveCount = new ArrayList<>();

    for (DungeonNode dungeonNode : allDungeonNodes) {
      if (dungeonNode.getNodeIsA().equals(DungeonNodeType.CAVE)) {
        caveCount.add(dungeonNode.getDungeonNodeIdentifier());
      }
    }

    int noOfCaves = caveCount.size();
    double treas = Math.ceil(((double) noOfCaves * (double) treasurePercentage) / 100);

    while (i < treas) {
      int randomCave = random.nextInt(caveCount.size());
      int noOfTreasuresPerCave = random.nextInt(5) + 1;

      for (int k = 0; k < noOfTreasuresPerCave; k++) {
        int randomTreasure = random.nextInt(treasures.size());
        allDungeonNodes.get(caveCount.get(randomCave))
            .addCaveTreasure(treasures.get(randomTreasure));
      }
      caveCount.remove(randomCave);
      i++;
    }
  }

  /**
   * Finds all the possible pair of execute and end caves which have a minimum distance as 5.
   */
  private void findStartEndPairsWithDepthFirstSearch() {

    for (Interconnection edge : spanningTreeEdges) {
      adjacentEdges[edge.getSource().getDungeonNodeIdentifier()]
          .add(edge.getDestination().getDungeonNodeIdentifier());
      adjacentEdges[edge.getDestination().getDungeonNodeIdentifier()]
          .add(edge.getSource().getDungeonNodeIdentifier());
    }


    List<Integer> caveNodes = new ArrayList<>();

    for (DungeonNode dungeonNode : allDungeonNodes) {
      if (dungeonNode.getNodeIsA().equals(DungeonNodeType.CAVE)) {
        caveNodes.add(dungeonNode.getDungeonNodeIdentifier());
      }
    }

    int i = 0;

    for (int k = 0; k < caveNodes.size(); k++) {

      for (int j = 0; j < caveNodes.size(); j++) {

        boolean[] node = new boolean[this.locations];
        Arrays.fill(node, false);
        int start = caveNodes.get(k);
        int end = caveNodes.get(j);

        minimumEdges = Integer.MAX_VALUE;
        edgeCount = 0;

        minimumEdgeDepthFirstSearchUtil(node, start, end);

        if (minimumEdges >= 5) {
          Interconnection edge = new InterconnectionImpl(allDungeonNodes.get(start),
              allDungeonNodes.get(end));
          possibleStartEndPairs.add(edge);
          break;
        }
      }
    }
  }

  /**
   * Finds the map containing each location and corresponding destination locations that are 1
   * positions and 2 positions away in 2 different lists.
   */
  private void findOneAndTwoSpotsAway() {
    for (int k = 0; k < allDungeonNodes.size(); k++) {

      if (allDungeonNodes.get(k).getMonster() != null || allDungeonNodes.get(k).getPit() != null) {

        for (int j = 0; j < allDungeonNodes.size(); j++) {

          boolean[] node = new boolean[this.locations];
          Arrays.fill(node, false);
          int start = allDungeonNodes.get(k).getDungeonNodeIdentifier();
          int end = allDungeonNodes.get(j).getDungeonNodeIdentifier();

          minimumEdges = Integer.MAX_VALUE;
          edgeCount = 0;
          minimumEdgeDepthFirstSearchUtilToFindSpots(node, start, end);

          if (minimumEdges == 1) {
            Interconnection edge = new InterconnectionImpl(allDungeonNodes.get(start),
                allDungeonNodes.get(end));
            oneLocationAway.add(edge);
          }

          if (minimumEdges == 2) {
            Interconnection edge = new InterconnectionImpl(allDungeonNodes.get(start),
                allDungeonNodes.get(end));
            twoLocationAway.add(edge);
          }
        }
      }
    }
  }

  /**
   * Sets a execute and end cave selected randomly from the list of possible execute end pairs.
   *
   * @throws IllegalStateException if Dungeon size is too small to enforce 5 as minimum distance
   *              between execute and end
   */
  private void setStartAndEndNode() {
    if (possibleStartEndPairs.size() > 0) {
      int r = random.nextInt(possibleStartEndPairs.size());

      startingCave = possibleStartEndPairs.get(r).getSource();
      endingCave = possibleStartEndPairs.get(r).getDestination();
    }

    else {
      throw new IllegalStateException("Dungeon size too small to enforce 5 as minimum distance "
          + "between execute and end");
    }
  }

  /**
   * Find minimum number of edges between all caves of the dungeon.
   * Util Source:
   * http://www.geeksforgeeks.org/minimum-number-of-edges-between-two-vertices-of-a-graph-using-dfs
   *
   * @param node represents the status of visitation of each nodes while traversing in DFS.
   * @param source represents the source cave identifier.
   * @param destination represents the destination cave identifier.
   */
  private void minimumEdgeDepthFirstSearchUtil(boolean[] node, int source, int destination) {

    if (source < 0 || destination < 0) {
      throw new IllegalArgumentException("Illegal percentage of treasure!");
    }

    node[source] = true;

    if (source == destination || edgeCount == 5) {
      if (minimumEdges > edgeCount) {
        minimumEdges = edgeCount;
      }
    } else {
      for (int i : adjacentEdges[source]) {
        int visitedNode = i;

        if (!node[visitedNode]) {
          edgeCount++;
          minimumEdgeDepthFirstSearchUtil(node, visitedNode, destination);
        }
      }
    }
    node[source] = false;
    edgeCount--;
  }

  /**
   * Find minimum number of edges between all caves of the dungeon that has a monster or a pit.
   * Util Source:
   * http://www.geeksforgeeks.org/minimum-number-of-edges-between-two-vertices-of-a-graph-using-dfs
   *
   * @param node represents the status of visitation of each nodes while traversing in DFS.
   * @param source represents the source cave identifier.
   * @param destination represents the destination cave identifier.
   */
  private void minimumEdgeDepthFirstSearchUtilToFindSpots(boolean[] node,
      int source, int destination) {

    if (source < 0 || destination < 0) {
      throw new IllegalArgumentException("Illegal percentage of treasure!");
    }


    node[source] = true;

    if (source == destination) {
      if (minimumEdges > edgeCount) {
        minimumEdges = edgeCount;
      }
    } else {
      for (int i : adjacentEdges[source]) {
        int visitedNode = i;

        if (!node[visitedNode]) {
          edgeCount++;
          minimumEdgeDepthFirstSearchUtilToFindSpots(node, visitedNode, destination);
        }
      }
    }
    node[source] = false;
    edgeCount--;
  }


  /**
   * Represent the initially created list of Dungeon Nodes as a 2d representation.
   */
  private void dungeonAs2D() {
    int count = 0;
    for (int i = 0; i < noOfRows; i++) {
      for (int j = 0; j < noOfColumns; j++) {
        if (count == allDungeonNodes.size()) {
          break;
        }
        dungeonAs2D[i][j] = allDungeonNodes.get(count);
        count++;
      }
    }
  }

  /**
   * Prints the dungeon as a 2d grid of caves, tunnels and interconnecting edges.
   */
  @Override
  public String printDungeon() {
    int m = 0;
    String vertical = "|";
    String horizontal = "—";
    String dungeonGrid = "";

    while (m < noOfRows) {

      if (this.wrapping && m == 0) {
        for (int l = 0; l < noOfColumns; l++) {
          if (dungeonAs2D[m][l].getNorthConnection() == 1) {
            dungeonGrid += String.format("%5s    ", vertical);
          } else {
            dungeonGrid += String.format("         ");
          }
        }
        dungeonGrid += "\n";
      }

      for (int j = 0; j < noOfColumns; j++) {
        if (dungeonAs2D[m][j].getWestConnection() == 1
            && dungeonAs2D[m][j].getEastConnection() == 1) {
          dungeonGrid += String.format("%s <%s%2d> %s", horizontal,
              dungeonAs2D[m][j].getNodeIsA().toString().charAt(0),
              dungeonAs2D[m][j].getDungeonNodeIdentifier(), horizontal);
        }

        if (dungeonAs2D[m][j].getWestConnection() == 1
            && dungeonAs2D[m][j].getEastConnection() == 0) {
          dungeonGrid += String.format("%s <%s%2d>  ", horizontal,
              dungeonAs2D[m][j].getNodeIsA().toString().charAt(0),
              dungeonAs2D[m][j].getDungeonNodeIdentifier());
        }

        if (dungeonAs2D[m][j].getWestConnection() == 0
            && dungeonAs2D[m][j].getEastConnection() == 1) {
          dungeonGrid += String.format("  <%s%2d> %s",
              dungeonAs2D[m][j].getNodeIsA().toString().charAt(0),
              dungeonAs2D[m][j].getDungeonNodeIdentifier(), horizontal);
        }

        if (dungeonAs2D[m][j].getWestConnection() == 0
            && dungeonAs2D[m][j].getEastConnection() == 0) {
          dungeonGrid += String.format("  <%s%2d>  ",
              dungeonAs2D[m][j].getNodeIsA().toString().charAt(0),
              dungeonAs2D[m][j].getDungeonNodeIdentifier());
        }
      }


      dungeonGrid += "\n";

      for (int l = 0; l < noOfColumns; l++) {
        if (dungeonAs2D[m][l].getSouthConnection() == 1) {
          dungeonGrid += String.format("%5s    ",vertical);
        }
        else {
          dungeonGrid += String.format("         ");
        }
      }
      dungeonGrid += "\n";
      m++;
    }

    return dungeonGrid;
  }


  /**
   * Gets all the edges when N x M nodes are created initially.
   *
   * @return the list of horizontal and vertical edges between all the nodes.
   */
  @Override
  public List<Interconnection> getAllEdges() {
    return allEdges;
  }

  /**
   * Gets the spanning tree edges after the graph is created with dungeon nodes. Spanning tree
   * edges have total of nodes-1 edges.
   *
   * @return the list of edges that form the spanning tree.
   */
  @Override
  public List<Interconnection> getSpanningTreeEdges() {
    return spanningTreeEdges;
  }

  /**
   * Gets the remaining edges that are leftover after the spanning edges are created. These edges
   * are used to modify the degree of interconnectivity.
   *
   * @return the remaining edges that are leftover after the spanning edges are created.
   */
  @Override
  public List<Interconnection> getRemainingEdges() {
    return remainingEdges;
  }

  /**
   * Gets all the nodes, can either be a tunnel depending on the number of doors, of the dungeon.
   *
   * @return all the nodes of the dungeon.
   */
  @Override
  public List<DungeonNode> getDungeonNodes() {
    return allDungeonNodes;
  }

  /**
   * Gets all the possible execute and end cave pairs that can be chosen as the execute or end cave.
   * Minimum distance between execute and end cave is 5.
   *
   * @return the all possible execute and end cave pairs.
   */
  @Override
  public List<Interconnection> getPossibleStartEndPairs() {
    return possibleStartEndPairs;
  }

  /**
   * Gets the cave that is picked as the starting cave.
   *
   * @return the cave that is picked as the starting cave.
   */
  @Override
  public DungeonNode getStartingCave() {
    return this.startingCave;
  }

  /**
   * Gets the cave that is picked as the ending cave.
   *
   * @return the cave that is picked as the ending cave.
   */
  @Override
  public DungeonNode getEndingCave() {
    return this.endingCave;
  }

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
  @Override
  public void create(int interconnectivity,
      int treasurePercentage, int numberOfMonsters, int thieves, int pits) {

    if (!(treasurePercentage >= 0 && treasurePercentage <= 100) || interconnectivity < 0) {
      throw new IllegalArgumentException("Invalid data in the parameters!");
    }

    createDungeonNodes(noOfRows * noOfColumns);
    createEdgesBetweenNodes(wrapping);

    randomKruskalSpanningTree(interconnectivity);

    setDirections(spanningTreeEdges);
    setNodesAsCaveOrTunnel(allDungeonNodes);
    dungeonAs2D();


    findStartEndPairsWithDepthFirstSearch();

    setStartAndEndNode();
    allocateTreasureToCaves(treasurePercentage);
    allocateMonsterToCaves(numberOfMonsters);
    allocateArrowsToDungeon(treasurePercentage);
    allocateThievesToTunnels(thieves);
    allocatePitsToCaves(pits);
    findOneAndTwoSpotsAway();
  }

  /**
   * Gets the 2d representation of the grid.
   *
   * @return the 2d representation of the grid.
   */
  @Override
  public DungeonNode[][] getDungeonAs2D() {
    return dungeonAs2D;
  }

  /**
   * Gets the number of rows in the dungeon grid.
   *
   * @return the number of rows in the dungeon grid.
   */
  @Override
  public int getNoOfRows() {
    return noOfRows;
  }

  /**
   * Gets the number of columns in the dungeon grid.
   *
   * @return the number of columns in the dungeon grid.
   */
  @Override
  public int getNoOfColumns() {
    return noOfColumns;
  }

  /**
   * Adds monsters to the dungeon. Monsters can occupy only the caves and exist in isolation.
   * Atleast one monster exists in the end location of the dungeon.
   *
   * @param numberOfMonsters represents the number of monsters that needs to be added in the game.
   *                         If the number of monsters specified are greater than the caves, then
   *                         number of monsters is considered equal to the number of caves.
   * @throws IllegalArgumentException if the number of monsters lesser than 1 or greater than 20.
   */
  @Override
  public void allocateMonsterToCaves(int numberOfMonsters) {
    if (numberOfMonsters < 1 || numberOfMonsters > 20) {
      throw new IllegalArgumentException("Illegal number of monsters!");
    }

    int count = numberOfMonsters;
    List<Integer> caveCount = new ArrayList<>();

    for (DungeonNode dungeonNode : allDungeonNodes) {
      if (dungeonNode.getNodeIsA().equals(DungeonNodeType.CAVE)
          && (dungeonNode.getDungeonNodeIdentifier() != startingCave.getDungeonNodeIdentifier())
          && (dungeonNode.getDungeonNodeIdentifier() != endingCave.getDungeonNodeIdentifier())) {
        caveCount.add(dungeonNode.getDungeonNodeIdentifier());
      }
    }

    endingCave.addMonster();
    count -= 1;

    if ((caveCount.size()) < count) {
      count = caveCount.size();
    }

    while (count > 0) {
      int randomCave = random.nextInt(caveCount.size());
      allDungeonNodes.get(caveCount.get(randomCave)).addMonster();
      caveCount.remove(randomCave);
      count--;
    }
  }

  /**
   * Adds arrows to the dungeon. Arrows can be placed in either tunnels or caves.
   *
   * @param arrowPercentage represents the percentage of tunnels/ caves that the arrows needs to be
   *                        added to. Percentage is same as treasure percentage.
   * @throws IllegalArgumentException when the percentage of arrows is lesser than 0 or greater
   *                          than 100.
   */
  @Override
  public void allocateArrowsToDungeon(int arrowPercentage) {

    if (arrowPercentage < 0 || arrowPercentage > 100) {
      throw new IllegalArgumentException("Illegal percentage of arrows!");
    }

    int i = 0;
    List<Integer> allNodes = new ArrayList<>();

    List<Integer> caveCount = new ArrayList<>();

    for (DungeonNode dungeonNode : allDungeonNodes) {
      if (dungeonNode.getNodeIsA().equals(DungeonNodeType.CAVE)) {
        caveCount.add(dungeonNode.getDungeonNodeIdentifier());
      }
    }

    int noOfCaves = caveCount.size();
    double arrPerc = Math.ceil(((double) noOfCaves * (double) arrowPercentage) / 100);

    for (DungeonNode dungeonNode : allDungeonNodes) {
      allNodes.add(dungeonNode.getDungeonNodeIdentifier());
    }

    while (i < arrPerc) {
      int randomNode = random.nextInt(allNodes.size());
      int noOfArrowsPerCave = random.nextInt(5) + 1;

      for (int k = 0; k < noOfArrowsPerCave; k++) {
        allDungeonNodes.get(allNodes.get(randomNode)).addArrows();
      }

      allNodes.remove(randomNode);
      i++;
    }
  }

  /**
   * Adds thieves to the dungeon. Thieves can only exist in the tunnels and one tunnel can
   * occupy only one thief.
   *
   * @param thieves represents the number of thieves that needs to be added to the dungeon.
   */
  @Override
  public void allocateThievesToTunnels(int thieves) {

    if (thieves < 0 || thieves > 10) {
      throw new IllegalArgumentException("Illegal number of thieves!");
    }

    int count = thieves;
    List<Integer> tunnelCount = new ArrayList<>();

    for (DungeonNode dungeonNode : allDungeonNodes) {
      if (dungeonNode.getNodeIsA().equals(DungeonNodeType.TUNNEL)) {
        tunnelCount.add(dungeonNode.getDungeonNodeIdentifier());
      }
    }

    if ((tunnelCount.size()) < count) {
      count = tunnelCount.size();
    }

    while (count > 0) {
      int randomCave = random.nextInt(tunnelCount.size());
      allDungeonNodes.get(tunnelCount.get(randomCave)).addThief();
      tunnelCount.remove(randomCave);
      count--;
    }
  }

  /**
   * Adds pits to the dungeon. Pits can only exist in the caves and one cave can
   * have only one pit at a given time.
   *
   * @param pits represents the number of pits that needs to be added to the dungeon.
   */
  @Override
  public void allocatePitsToCaves(int pits) {

    if (pits < 0 || pits > 10) {
      throw new IllegalArgumentException("Illegal number of pits!");
    }

    int count = pits;
    List<Integer> caves = new ArrayList<>();

    for (DungeonNode dungeonNode : allDungeonNodes) {
      if (dungeonNode.getDungeonNodeIdentifier() != startingCave.getDungeonNodeIdentifier()
          && dungeonNode.getNodeIsA().equals(DungeonNodeType.CAVE)) {
        caves.add(dungeonNode.getDungeonNodeIdentifier());
      }
    }

    if ((caves.size()) < count) {
      count = caves.size();
    }

    while (count > 0) {
      int randomCave = random.nextInt(caves.size());
      allDungeonNodes.get(caves.get(randomCave)).addPit();
      caves.remove(randomCave);
      count--;
    }
  }


  /**
   * Gets all the locations of all the caves that are one spot away.
   *
   * @return the locations of all the caves that are one spot away.
   */
  @Override
  public List<Interconnection> getOneLocationAway() {
    return oneLocationAway;
  }

  /**
   * Gets all the locations of all the caves that are two spots away.
   *
   * @return the locations of all the caves that are two spots away.
   */
  @Override
  public List<Interconnection> getTwoLocationAway() {
    return twoLocationAway;
  }

}

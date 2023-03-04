package model.dungeonsanddragons;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * DungeonNodeImpl class represents an individual location in the dungeon. Each location can either
 * be a tunnel or a cave with only cave having the capacity to hold treasures. Caves and tunnels,
 * both, can hold monsters, but weapons can exist in the tunnel as well. Each location is connected
 * with at least 1 edge in such a way that the player can traverse all the locations in the dungeon.
 * Since the creation of dungeon nodes is an internal implementation and should not be exposed to
 * the user, this is kept as package-private.
 */
class DungeonNodeImpl implements DungeonNode {

  private final int dungeonNodeIdentifier;
  private DungeonNodeType nodeIsA;
  private int northConnection;
  private int southConnection;
  private int eastConnection;
  private int westConnection;
  private final List<Treasure> caveTreasures;
  private Monster monster;
  private final List<Weapon> weapons;
  private Enemy thief;
  private Obstacle pit;


  /**
   * A constructor to initialize a Dungeon DungeonNode upon its creation.
   *
   * @param dungeonNodeIdentifier represents the id of the dungeon which starts from 0 and goes
   *                              up to vertices - 1.
   * @throws IllegalArgumentException if the dungeonNodeIdentifier is less than 0.
   */
  public DungeonNodeImpl(int dungeonNodeIdentifier) {

    if (dungeonNodeIdentifier < 0) {
      throw new IllegalArgumentException("Illegal Identifier!");
    }

    this.dungeonNodeIdentifier = dungeonNodeIdentifier;
    nodeIsA = DungeonNodeType.CAVE;
    northConnection = 0;
    southConnection = 0;
    eastConnection = 0;
    westConnection = 0;
    caveTreasures = new ArrayList<>();
    weapons = new ArrayList<>();
  }

  /**
   * Gets the identifier of the dungeon. Starts with 0 and goes on till vertices - 1.
   *
   * @return the identifier of the dungeon. Starts with 0 and goes on till vertices - 1.
   */
  @Override
  public int getDungeonNodeIdentifier() {
    return dungeonNodeIdentifier;
  }

  /**
   * Gets the type of the node. It can be a cave or a tunnel.
   *
   * @return the type of the node.
   */
  @Override
  public DungeonNodeType getNodeIsA() {
    return nodeIsA;
  }

  /**
   * Sets the type of node as tunnel. Initially each node is set as cave.
   */
  @Override
  public void setAsATunnel() {
    nodeIsA = DungeonNodeType.TUNNEL;
  }

  /**
   * Sets the east connection to 1 indicating that the node is connected to the adjacent node
   * on the right.
   */
  @Override
  public void setEastConnection() {
    this.eastConnection = 1;
  }

  /**
   * Sets the north connection to 1 indicating that the node is connected to the adjacent node
   * on the north.
   */
  @Override
  public void setNorthConnection() {
    this.northConnection = 1;
  }

  /**
   * Sets the west connection to 1 indicating that the node is connected to the adjacent node
   * on the left.
   */
  @Override
  public void setWestConnection() {
    this.westConnection = 1;
  }

  /**
   * Sets the south connection to 1 indicating that the node is connected to the adjacent node
   * on the south.
   */
  @Override
  public void setSouthConnection() {
    this.southConnection = 1;
  }

  /**
   * Add a treasure to the node. Treasure can only be added to the cave.
   *
   * @param treasure represents the treasure that is to be added to the cave.
   * @throws IllegalArgumentException if given treasure is empty.
   */
  @Override
  public void addCaveTreasure(Treasure treasure) throws IllegalArgumentException {
    if (Objects.isNull(treasure)) {
      throw new IllegalArgumentException("Treasure can't be null");
    }
    if (this.nodeIsA.equals(DungeonNodeType.CAVE)) {
      this.caveTreasures.add(treasure);
    }
    else {
      throw new IllegalArgumentException("Treasure cannot be added to a Tunnel!");
    }
  }

  /**
   * Removes a treasure to the node. Treasure can only be removed to the cave.
   *
   * @param treasure represents the treasure that is to be removed to the cave.
   * @throws IllegalArgumentException if given treasure is empty.
   * @throws IllegalStateException if there are no treasures to be removed from the cave.
   */
  @Override
  public void removeCaveTreasure(Treasure treasure) throws IllegalArgumentException,
      IllegalStateException {
    if (Objects.isNull(treasure)) {
      throw new IllegalArgumentException("Treasure can't be null");
    }

    if (this.caveTreasures.size() > 0) {
      this.caveTreasures.remove(treasure);
    }
    else {
      throw new IllegalStateException("They are no treasures remaining!");
    }
  }

  /**
   * Gets all the treasures that are contained in the given cave.
   *
   * @return the treasures that are contained in the given cave.
   */
  @Override
  public List<Treasure> getCaveTreasures() {
    return caveTreasures;
  }

  /**
   * Gets the status of the connection of the node on the east side.
   *
   * @return 1 if node towards east is connected, 0 otherwise.
   */
  @Override
  public int getEastConnection() {
    return eastConnection;
  }

  /**
   * Gets the status of the connection of the node on the north side.
   *
   * @return 1 if node towards north is connected, 0 otherwise.
   */
  @Override
  public int getNorthConnection() {
    return northConnection;
  }

  /**
   * Gets the status of the connection of the node on the south side.
   *
   * @return 1 if node towards south is connected, 0 otherwise.
   */
  @Override
  public int getSouthConnection() {
    return southConnection;
  }

  /**
   * Gets the status of the connection of the node on the west side.
   *
   * @return 1 if node towards west is connected, 0 otherwise.
   */
  @Override
  public int getWestConnection() {
    return westConnection;
  }

  /**
   * Gets the monster that is existing in the given node.
   *
   * @return the monster that exists in the node, null otherwise.
   */
  @Override
  public Monster getMonster() {
    return monster;
  }

  /**
   * Adds the monster to the cave.
   */
  @Override
  public void addMonster() {

    if (this.nodeIsA.equals(DungeonNodeType.CAVE)) {
      this.monster = new Otyugh();
    }
    else {
      throw new IllegalStateException("Monster cannot be added!");
    }
  }

  /**
   * Sets the health of the monster when it's hit by an arrow.
   */
  @Override
  public void updateMonster() {

    if (this.monster == null) {
      return;
    }

    if (this.monster.getHealth() == 50) {
      this.monster.getKilled();
    }

    if (this.monster.getHealth() == 100) {
      this.monster.firstHit();
    }
  }

  /**
   * Gets the thief that is existing in the given node.
   *
   * @return the thief that exists in the node, null otherwise.
   */
  @Override
  public Enemy getThief() {
    return thief;
  }

  /**
   * Adds a thief to the given dungeon node.
   */
  @Override
  public void addThief() {
    if (this.nodeIsA.equals(DungeonNodeType.TUNNEL)) {
      this.thief = Enemy.THIEF;
    }
    else {
      throw new IllegalStateException("Thief cannot be added!");
    }
  }

  /**
   * Gets the pit that is existing in the given node.
   *
   * @return the pit that exists in the node, null otherwise.
   */
  @Override
  public Obstacle getPit() {
    return pit;
  }

  /**
   * Adds the pit to the dungeon.
   */
  @Override
  public void addPit() {
    this.pit = Obstacle.PIT;
  }

  /**
   * Adds the arrow to the dungeon node.
   */
  public void addArrows() {
    this.weapons.add(Weapon.CROOKEDARROW);
  }

  /**
   * Removes a single arrow from the dungeon node.
   */
  public void removeArrow() {
    this.weapons.remove(Weapon.CROOKEDARROW);
  }

  /**
   * Gets all the weapons that are existing in the given node.
   *
   * @return the weapons that are existing in the given node.
   */
  public List<Weapon> getArrows() {
    return this.weapons;
  }

  /**
   * String representation of the dungeon node.
   *
   * @return the string representation of the dungeon node.
   */
  @Override
  public String toString() {

    String allocatedTreasures = "";
    String totalMonsters = "";
    String totalArrows = "";

    for (Treasure treasure : caveTreasures) {
      allocatedTreasures += (treasure.toString()) + ",";
    }

    if (allocatedTreasures.length() > 1) {
      allocatedTreasures = allocatedTreasures.substring(0,allocatedTreasures.length() - 1);
    }

    if (monster != null && monster.getHealth() > 0) {
      totalMonsters = "OTYUGH";
    }
    else {
      totalMonsters = "";
    }

    String thief = "";

    if (this.thief != null) {
      thief = "THIEF";
    }
    else {
      totalMonsters = "";
    }

    String pit = "";

    if (this.pit != null) {
      pit = "PIT";
    }
    else {
      totalMonsters = "";
    }


    for (Weapon weapon : weapons) {
      totalArrows += (weapon.toString()) + ",";
    }

    if (totalArrows.length() > 1) {
      totalArrows = totalArrows.substring(0,totalArrows.length() - 1);
    }

    return String.format("DungeonNodeImpl{dungeonNodeIdentifier=%d, nodeIsa=%s, Treasure=<%s> "
            + "Monster=<%s> Arrows=<%s> Enemy=<%s> Obstacles=<%s>", this.dungeonNodeIdentifier,
        this.nodeIsA.toString(), allocatedTreasures, totalMonsters, totalArrows, thief, pit);
  }
}

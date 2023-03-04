package model.dungeonsanddragons;

import java.util.ArrayList;
import java.util.List;

/**
 * ReadOnlyGameImpl class represents a readonly endpoint for the game of dungeonsAndDragons.
 * ReadOnlyGameImpl provides information about the location identifier, type of the dungeon,
 * possible moves that the player can make from this location, total treasure, arrows that the
 * node is currently holding. It also provides information about the monster, pits, thieves. It
 * also provides information about the player and game status. Since ReadOnlyGameImpl is a custom
 * read-only type created to represent the information about the dungeon node to the users, this
 * is kept as public.
 */
public class ReadOnlyGameImpl implements ReadOnlyGame {

  private final int dungeonId;
  private final String dungeonType;
  private List<String> possibleMoves;
  private final List<Treasure> treasureList;
  private final List<Treasure> playertreasureList;
  private final Monster monster;
  private final Enemy thief;
  private final Obstacle pit;
  private final List<Weapon> weapons;
  private final List<Weapon> playerWeapons;
  private final int north;
  private final int south;
  private final int east;
  private final int west;
  private final int rowIn2D;
  private final int colIn2D;
  private final String smell;
  private final String sound;

  /**
   * A constructor to initialize the read only object of the location. It is initialized using the
   * attributes of the Dungeon DungeonNode object.
   *
   * @param id           represents the location identifier.
   * @param type         represents the type of cave.
   * @param north        represents the connection to a northern node.
   * @param south        represents the connection to a southern node.
   * @param east         represents the connection to an eastern node.
   * @param west         represents the connection to a western node.
   * @param treasureList represents the list of treasure contained in the cave.
   * @param playerTreasureList represents the list of treasure that the player has collected.
   * @param monster represents the monster contained in the cave.
   * @param thief represents the thief contained in the node.
   * @param weapons represents the list of weapons contained in the node.
   * @param playerWeapons represents the list of weapons that the player has collected.
   * @param smell represents the type of smell that the player can smell from current location.
   * @param sound represents the sound that the player can hear from current location.
   * @throws IllegalArgumentException if the connections, id, type or treasure is null.
   */
  public ReadOnlyGameImpl(int id, String type, int north, int south, int east, int west,
      List<Treasure> treasureList, Monster monster, Enemy thief, Obstacle pit,
      List<Weapon> weapons, int rowIn2D, int colIn2d, String smell, String sound,
      List<Treasure> playerTreasureList, List<Weapon> playerWeapons) {

    if (id < 0 || north < 0 || south < 0 || east < 0 || west < 0
        || !(type.equals("CAVE") || type.equals("TUNNEL")) || treasureList == null) {
      throw new IllegalArgumentException("Illegal parameters in location!");
    }
    this.dungeonId = id;
    this.dungeonType = type;
    this.treasureList = treasureList;
    this.playertreasureList = playerTreasureList;
    this.monster = monster;
    this.thief = thief;
    this.pit = pit;
    this.weapons = weapons;
    this.playerWeapons = playerWeapons;
    this.north = north;
    this.south = south;
    this.east = east;
    this.west = west;
    possibleMoves = new ArrayList<>();
    this.rowIn2D = rowIn2D;
    this.colIn2D = colIn2d;
    this.smell = smell;
    this.sound = sound;
  }

  /**
   * Gets the ID of the vertex representing a single location (cave or a tunnel). Id starts from 0
   * and goes up to (rows * columns) - 1.
   *
   * @return the ID of the vertex representing a single location (cave or a tunnel). Id starts from
   *          0 and goes up to (rows * columns) - 1.
   */
  @Override
  public int getLocationId() {
    return dungeonId;
  }

  /**
   * Gets the type of the location. ReadOnlyGame can be of 2 types, either a cave or a tunnel.
   *
   * @return the type of the location. ReadOnlyGame can be of 2 types, either a cave or a tunnel.
   */
  @Override
  public String getLocationType() {
    return dungeonType;
  }

  /**
   * Gets the possible moves from the location. UP, DOWN, LEFT and RIGHT are four possible moves.
   * Format that is followed is '[ list of moves ]'.
   *
   * @return the possible moves from the location. UP, DOWN, LEFT and RIGHT are four possible moves.
   */
  @Override
  public List<String> getPossibleMoves() {
    if (north == 1) {
      possibleMoves.add("UP");
    }
    if (south == 1) {
      possibleMoves.add("DOWN");
    }
    if (east == 1) {
      possibleMoves.add("RIGHT");
    }
    if (west == 1) {
      possibleMoves.add("LEFT");
    }

    return possibleMoves;

  }

  /**
   * Gets the treasure housed in a particular location. Only a cave can house the treasure. Format
   * that is followed is '[ list of treasure ]', '[ ]' if empty. 'Tunnel can't hold treasure' if
   * it's a tunnel.
   *
   * @return the treasure housed in a particular location. Only a cave can house the treasure.
   */
  @Override
  public List<Treasure> getTreasure() {
    List<Treasure> copyTreasure = new ArrayList<>(this.treasureList);
    return copyTreasure;
  }

  /**
   * Gets the monsters that are located them in the dungeon.
   *
   * @return the monsters that are located them in the dungeon.
   */
  @Override
  public String getMonster() {
    if (this.monster != null && this.dungeonType.equals("CAVE")) {
      return monster.toString();
    } else {
      return null;
    }
  }

  /**
   * Gets the health of the monster.
   *
   * @return the health of the monster.
   */
  @Override
  public int getMonsterHealth() {
    if (this.monster != null) {
      return monster.getHealth();
    }
    else {
      return -1;
    }
  }

  /**
   * Gets the monsters that are located them in the dungeon.
   *
   * @return the monsters that are located them in the dungeon.
   */
  @Override
  public List<Weapon> getArrows() {
    List<Weapon> copyWeapons = new ArrayList<>(this.weapons);
    return copyWeapons;
  }

  /**
   * Gets the row number of the location in 2d grid.
   *
   * @return the row number of the location in 2d grid.
   */
  @Override
  public int getRowIn2D() {
    return rowIn2D;
  }

  /**
   * Gets the column number of the location in 2d grid.
   *
   * @return the column number of the location in 2d grid.
   */
  @Override
  public int getColIn2D() {
    return colIn2D;
  }

  /**
   * Gets the type of dungeon node. It can be a cave or a tunnel.
   *
   * @return the type of dungeon node. It can be a cave or a tunnel.
   */
  @Override
  public String nodeT() {
    if (north == 1 && south == 0 && east == 0 && west == 0) {
      return "N";
    }
    else if (north == 0 && south == 1 && east == 0 && west == 0) {
      return "S";
    }
    else if (north == 0 && south == 0 && east == 1 && west == 0) {
      return "E";
    }
    else if (north == 0 && south == 0 && east == 0 && west == 1) {
      return "W";
    }
    else if (north == 1 && south == 1 && east == 0 && west == 0) {
      return "NS";
    }
    else if (north == 1 && south == 0 && east == 1 && west == 0) {
      return "NE";
    }
    else if (north == 1 && south == 0 && east == 0 && west == 1) {
      return "WN";
    }
    else if (north == 0 && south == 1 && east == 1 && west == 0) {
      return "ES";
    }
    else if (north == 0 && south == 1 && east == 0 && west == 1) {
      return "SW";
    }
    else if (north == 0 && south == 0 && east == 1 && west == 1) {
      return "EW";
    }
    else if (north == 1 && south == 1 && east == 1 && west == 0) {
      return "NES";
    }
    else if (north == 1 && south == 0 && east == 1 && west == 1) {
      return "NEW";
    }
    else if (north == 0 && south == 1 && east == 1 && west == 1) {
      return "ESW";
    }
    else if (north == 1 && south == 1 && east == 0 && west == 1) {
      return "SWN";
    }
    else {
      return "NESW";
    }
  }

  /**
   * Gets the count of rubies in the node.
   *
   * @return the count of rubies in the node.
   */
  @Override
  public int getRubyCount() {
    return getIndividualTreasureCount("RUBY", this.treasureList);
  }

  /**
   * Gets the count of diamonds in the node.
   *
   * @return the count of diamonds in the node.
   */
  @Override
  public int getDiamondCount() {
    return getIndividualTreasureCount("DIAMOND", this.treasureList);
  }

  /**
   * Gets the count of sapphires in the node.
   *
   * @return the count of sapphires in the node.
   */
  @Override
  public int getSapphireCount() {
    return getIndividualTreasureCount("SAPPHIRE", this.treasureList);
  }

  /**
   * Gets the count of rubies from the player's bag.
   *
   * @return the count of rubies from the player's bag.
   */
  @Override
  public int getPlayerRubyCount() {
    return getIndividualTreasureCount("RUBY", this.playertreasureList);
  }

  /**
   * Gets the count of diamonds from the player's bag.
   *
   * @return the count of diamonds from the player's bag.
   */
  @Override
  public int getPlayerDiamondCount() {
    return getIndividualTreasureCount("DIAMOND", this.playertreasureList);
  }

  /**
   * Gets the count of sapphires from the player's bag.
   *
   * @return the count of sapphires from the player's bag.
   */
  @Override
  public int getPlayerSapphireCount() {
    return getIndividualTreasureCount("SAPPHIRE", this.playertreasureList);
  }


  private int getIndividualTreasureCount(String item, List<Treasure> treasureList) {
    int count = 0;
    for (Treasure treasure : treasureList) {
      if (treasure.toString().equals(item)) {
        count++;
      }
    }
    return count;
  }

  /**
   * Gets the count of arrows in the node.
   *
   * @return the count of arrows in the node.
   */
  @Override
  public int getArrowCount() {
    return this.weapons.size();
  }

  /**
   * Gets the count of arrows from the player's bag.
   *
   * @return the count of arrows from the player's bag.
   */
  @Override
  public int getPlayerArrowCount() {
    return this.playerWeapons.size();
  }

  /**
   * Gets the type of smell that the player is currently experiencing.
   *
   * @return the type of smell that the player is currently experiencing.
   */
  @Override
  public String getSmell() {
    return this.smell;
  }

  /**
   * Gets the type of sound that the player is currently hearing.
   *
   * @return the type of sound that the player is currently hearing.
   */
  @Override
  public String getSound() {
    return this.sound;
  }

  /**
   * Gets the thief in the given node, null otherwise.
   *
   * @return the thief in the given node, null otherwise.
   */
  @Override
  public String getThief() {
    if (this.thief != null) {
      return thief.toString();
    } else {
      return null;
    }
  }

  /**
   * Gets the pit in the given node, null otherwise.
   *
   * @return the pit in the given node, null otherwise.
   */
  @Override
  public String getPit() {
    if (this.pit != null && this.dungeonType.equals("CAVE")) {
      return pit.toString();
    } else {
      return null;
    }
  }

}

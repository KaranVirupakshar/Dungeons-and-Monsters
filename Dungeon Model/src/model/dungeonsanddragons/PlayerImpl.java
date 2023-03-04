package model.dungeonsanddragons;

import java.util.ArrayList;
import java.util.List;

/**
 * PlayerImpl represents the player that begins the game from the starting cave and traverses
 * the dungeon through interconnections of tunnels and caves, by picking up treasures along the
 * way. These actions are controlled by the main game interface. Since decision was made to create
 * the player internally without any inputs from the user, PlayerImpl is kept as package-private.
 */
class PlayerImpl implements Player {

  private final String name;
  private DungeonNode location;
  private DungeonNode previousLocation;
  private List<Treasure> playerTreasures;
  private List<Weapon> playerWeapons;
  private int playerHealth;

  /**
   * A constructor to initialize the player playing the game of dungeon and dragons. A player
   * can move around the dungeon with its location updating after every legal move and ability
   * to pick up treasure from the current node.
   *
   * @param name represents the name of the player.
   * @throws IllegalArgumentException if the name of the player is empty.
   */
  public PlayerImpl(String name) throws IllegalArgumentException {

    if (name.equals("")) {
      throw new IllegalArgumentException("Player Name can't be empty!");
    }

    this.name = name;
    location = null;
    previousLocation = null;
    playerHealth = 100;
    playerTreasures =  new ArrayList<>();
    playerWeapons = new ArrayList<>();
    this.playerWeapons.add(Weapon.CROOKEDARROW);
    this.playerWeapons.add(Weapon.CROOKEDARROW);
    this.playerWeapons.add(Weapon.CROOKEDARROW);
  }

  /**
   * Gets the name of the player.
   *
   * @return the name of the player.
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * Gets the current location of the player. It can either be a cave or a tunnel.
   *
   * @return the current location of the player. It can either be a cave or a tunnel.
   */
  @Override
  public DungeonNode getLocation() {
    return location;
  }

  /**
   * Sets the location of the player. It can either be a cave or a tunnel.
   *
   * @param location is the location, either a cave or tunnel, that the player's location must
   *                 be updated to.
   * @throws IllegalArgumentException if the location is null.
   */
  @Override
  public void setLocation(DungeonNode location) throws IllegalArgumentException {
    this.location = location;
  }

  /**
   * Gets the last location that the player has been to.
   *
   * @return the last location that the player has been to.
   */
  @Override
  public DungeonNode getPreviousLocation() {
    return previousLocation;
  }

  /**
   * Sets the last location that the player has been to.
   *
   * @param previousLocation represents last location that the player has been to.
   */
  @Override
  public void setPreviousLocation(DungeonNode previousLocation) throws IllegalArgumentException {
    this.previousLocation = location;
  }

  /**
   * Gets the list of treasure collected by the player.
   *
   * @return the list of the treasure collected by the player.
   */
  @Override
  public List<Treasure> getPlayerTreasures() {
    return playerTreasures;
  }

  /**
   * Adds the treasure to player's bag. A player can pick treasure only from the location that a
   * player is present in.
   *
   * @param treasure represents the treasure that the player wants to pick.
   * @throws IllegalArgumentException if the treasure that is being added is null.
   */
  @Override
  public void addTreasure(Treasure treasure) {
    if (treasure == null) {
      throw new IllegalArgumentException("Treasure cannot be null!");
    }
    playerTreasures.add(treasure);
  }

  /**
   * Clears the player's treasure from the bag.
   */
  @Override
  public void clearTreasure() {
    playerTreasures =  new ArrayList<>();
  }

  /**
   * Clears the player's arrows from the armory.
   */
  @Override
  public void clearArrows() {
    playerWeapons = new ArrayList<>();
  }

  /**
   * Gets the arrows that the player has in his armoury.
   *
   * @return the arrows that the player has in his armoury.
   */
  @Override
  public List<Weapon> getPlayerArrows() {
    return playerWeapons;
  }

  /**
   * Picks an arrow that can be used to slay a monster.
   */
  @Override
  public void addArrow() {
    this.playerWeapons.add(Weapon.CROOKEDARROW);
  }

  /**
   * Removes an arrow from the player's bag.
   */
  @Override
  public void removeArrow() {
    this.playerWeapons.remove(Weapon.CROOKEDARROW);
  }

  /**
   * Sets the player's health to 0. Player starts with health 100.
   */
  @Override
  public void setPlayerHealth() {
    this.playerHealth = 0;
  }

  /**
   * Gets the player's health.
   */
  @Override
  public int getPlayerHealth() {
    return playerHealth;
  }

  /**
   * Gets the ruby count in the player's bag.
   *
   * @return the ruby count from the player's bag.
   */
  @Override
  public int getPlayerRubyCount() {
    return getIndividualTreasureCount("RUBY", this.playerTreasures);
  }

  /**
   * Gets the diamond count in the player's bag.
   *
   * @return the diamond count from the player's bag.
   */
  @Override
  public int getPlayerDiamondCount() {
    return getIndividualTreasureCount("DIAMOND", this.playerTreasures);
  }

  /**
   * Gets the sapphire count in the player's bag.
   *
   * @return the sapphire count from the player's bag.
   */
  @Override
  public int getPlayerSapphireCount() {
    return getIndividualTreasureCount("SAPPHIRE", this.playerTreasures);
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
}

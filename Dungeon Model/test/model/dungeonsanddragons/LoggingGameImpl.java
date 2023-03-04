package model.dungeonsanddragons;

import java.util.ArrayList;
import java.util.List;

/**
 * LoggingGameImpl class represents the mock model of Game type. This serves as a mock to
 * assert that the controller is not manipulating the inputs by the user. Inputs include
 * move, shoot, or pick treasure/arrow.
 */
public class LoggingGameImpl implements Game {

  private final StringBuilder log;
  private final List<Treasure> t;
  private final List<Weapon> w;
  private final Monster m;
  private final Enemy e;
  private final Obstacle o;

  /**
   * A constructor to initialize the mock model with String builder, treasure, weapon and monster.
   *
   * @param log represents the string builder for generating output.
   */
  public LoggingGameImpl(StringBuilder log) {
    this.log = log;
    t = new ArrayList<>();
    t.add(Treasure.DIAMOND);
    t.add(Treasure.RUBY);

    w = new ArrayList<>();
    w.add(Weapon.CROOKEDARROW);
    w.add(Weapon.CROOKEDARROW);

    m = new Otyugh();
    e = Enemy.THIEF;
    o = Obstacle.PIT;
  }

  /**
   * Direction the player from the current location using the given direction. UP, DOWN,
   * RIGHT, LEFT are the possible moves for the player.
   *
   * @param direction represents UP, DOWN, RIGHT, LEFT are the possible moves for the player.
   * @throws IllegalStateException if an illegal direction is being attempted.
   * @throws IllegalArgumentException if direction in the parameter is not up, down, left or right.
   */
  @Override
  public void movePlayer(Direction direction)
      throws IllegalStateException, IllegalArgumentException {
    log.append("Player Direction: ").append(direction.toString()).append("\n");
  }

  /**
   * Pick the treasure that are available in the current node.
   *
   * @param treasure represents RUBY, EMERALD, DIAMOND, SAPPHIRE which are the possible treasures
   *                 that can be collected by the player.
   * @throws IllegalStateException if there is no treasure of given type in the current location.
   * @throws IllegalArgumentException if treasure is not one of sapphire, ruby or diamond.
   */
  @Override
  public void pickTreasure(Treasure treasure)
      throws IllegalStateException, IllegalArgumentException {
    log.append("Pick Treasure Type: ").append(treasure.toString()).append("\n");
  }

  /**
   * Gets the treasures collected by the player.
   *
   * @return the treasures collected by the player.
   */
  @Override
  public List<Treasure> getTreasureCollectedByPlayer() {
    return t;
  }

  /**
   * Gets the current location of the player. ReadOnlyGame can be a cave or a tunnel.
   *
   * @return the current location of the player. ReadOnlyGame can be a cave or a tunnel.
   */
  @Override
  public ReadOnlyGame gameState() {
    ReadOnlyGame l = new ReadOnlyGameImpl(1, "CAVE", 1, 1, 1, 1,
        t, m, e, o, w, 1, 1, "PUNGENT", "CRACKLING", t, w);
    return l;
  }

  /**
   * Gets the 2D representation of the dungeon. Rows and columns of the 2d grid represent an
   * individual location which can be either a cave or a tunnel. Edges represent the connection
   * with the adjacent nodes. Lines(| or —)  represent the path the player can take. This is
   * similar to toString() implementation that dumps the grid, hence structure was not required.
   *
   * @return the 2D representation of the dungeon.
   */
  @Override
  public String printDungeonAs2D() {
    return null;
  }

  /**
   * Checks if the player has reached the end cave. This is required to determine if the game is
   * over.
   *
   * @returns true if the player has reached the end cave, false otherwise.
   */
  @Override
  public boolean isReachedEnd() {
    return false;
  }

  /**
   * Checks if the player has been killed by the monster. This is required to determine if the game
   * is over.
   *
   * @returns true if the player has been eaten by the monster, false otherwise.
   */
  @Override
  public boolean isKilled() {
    return false;
  }

  /**
   * Shoots the arrow in the specified direction and distance.
   *
   * @param distance represents the distance the arrow must be shot.
   * @param direction represents the direction of the direction.
   * @throws IllegalArgumentException if the distance is a negative value or direction is null.
   * @throws IllegalStateException if there are no more arrows left with the player.
   */
  @Override
  public void shootArrow(int distance, Direction direction) {
    log.append("Shoot Arrow Distance: ").append(distance).append("Shoot Arrow Direction: ")
        .append(direction.toString()).append("\n");
  }

  /**
   * Picks an arrow and adds it to the player's bag so that it can be further used to slay a
   * monster.
   */
  @Override
  public void pickArrow() {
    return;
  }

  /**
   * Gets the arrows currently in the player bag.
   *
   * @return the arrows currently in the player bag.
   */
  @Override
  public List<Weapon> getArrowsInPlayerBag() {
    return w;
  }

  /**
   * Gets the type of smell that the player is currently smelling from their location. Smell is
   * given out by the monsters. Player can use it to their advantage to survive and traverse
   * through the dungeon. There are 2 types of smells, Pungent and MorePungent.
   *
   * @return the type of smell that the player is currently smelling from their location.
   */
  @Override
  public Smell getSmell() {
    return Smell.PUNGENT;
  }

  @Override
  public Sound getSound() {
    return null;
  }

  @Override
  public void checkTreasureStolen(Long time) {
    return;
  }

  @Override
  public boolean isFallen() {
    return false;
  }

  @Override
  public int getRows() {
    return 0;
  }

  @Override
  public int getColumns() {
    return 0;
  }


}

package model.dungeonsanddragons;

import java.util.List;

/**
 * Game interface represents a framework for creation of a fantasy-based dungeon game as well as
 * controlling the player in the dungeon. Dungeon is a network of tunnels and caves that are
 * interconnected so that player can explore the entire world by traveling from cave to cave
 * through the tunnels that connect them and collecting treasures trying to avoid monsters/thieves/
 * pits. Since this platform is used to play out the fantasy-based dungeon game, a decision was
 * made to keep the interface public.
 */
public interface Game {

  /**
   * Direction the player from the current location using the given direction. UP, DOWN,
   * RIGHT, LEFT are the possible moves for the player.
   *
   * @param direction represents UP, DOWN, RIGHT, LEFT are the possible moves for the player.
   * @throws IllegalStateException if an illegal direction is being attempted.
   * @throws IllegalArgumentException if direction in the parameter is not up, down, left or right.
   */
  void movePlayer(Direction direction) throws IllegalStateException, IllegalArgumentException;

  /**
   * Pick the treasure that are available in the current node.
   *
   * @param treasure represents RUBY, EMERALD, DIAMOND, SAPPHIRE which are the possible treasures
   *                 that can be collected by the player.
   * @throws IllegalStateException if there is no treasure of given type in the current location.
   * @throws IllegalArgumentException if treasure is not one of sapphire, ruby or diamond.
   */
  void pickTreasure(Treasure treasure) throws IllegalStateException, IllegalArgumentException;

  /**
   * Gets the treasures collected by the player.
   *
   * @return the treasures collected by the player.
   */
  List<Treasure> getTreasureCollectedByPlayer();

  /**
   * Gets the current game state of the player which can be accessed through a read-only end
   * point.
   *
   * @return the current game state of the player which can be accessed through a read-only end
   *          point.
   */
  ReadOnlyGame gameState();

  /**
   * Gets the 2D representation of the dungeon. Rows and columns of the 2d grid represent an
   * individual location which can be either a cave or a tunnel. Edges represent the connection
   * with the adjacent nodes. Lines(| or —)  represent the path the player can take. This is
   * similar to toString() implementation that dumps the grid, hence structure was not required.
   *
   * @return the 2D representation of the dungeon.
   */
  String printDungeonAs2D();

  /**
   * Checks if the player has reached the end cave. This is required to determine if the game is
   * over.
   *
   * @returns true if the player has reached the end cave, false otherwise.
   */
  boolean isReachedEnd();

  /**
   * Checks if the player has been killed by the monster. This is required to determine if the game
   * is over.
   *
   * @returns true if the player has been eaten by the monster, false otherwise.
   */
  boolean isKilled();

  /**
   * Shoots the arrow in the specified direction and distance.
   *
   * @param distance represents the distance the arrow must be shot.
   * @param direction represents the direction of the direction.
   * @throws IllegalArgumentException if the distance is a negative value or direction is null.
   * @throws IllegalStateException if there are no more arrows left with the player.
   */
  void shootArrow(int distance, Direction direction)
      throws IllegalStateException, IllegalArgumentException;

  /**
   * Picks an arrow and adds it to the player's bag so that it can be further used to slay a
   * monster.
   */
  void pickArrow();

  /**
   * Gets the arrows currently in the player bag.
   *
   * @return the arrows currently in the player bag.
   */
  List<Weapon> getArrowsInPlayerBag();

  /**
   * Gets the type of smell that the player is currently smelling from their location. Smell is
   * given out by the monsters. Player can use it to their advantage to survive and traverse
   * through the dungeon. There are 2 types of smells, Pungent and MorePungent which are
   * represented as an enumeration.
   *
   * @return the type of smell that the player is currently smelling from their location.
   */
  Smell getSmell();

  /**
   * Gets the type of sound that the player can hear to identify near-by pit. CRACKLING sound can
   * be heard by the player when the player is one location away from the pit. Since the user uses
   * hearing to traverse through the dungeon, this enumeration is kept as public.
   *
   * @return the type of sound that the player can hear to identify near-by pit.
   */
  Sound getSound();

  /**
   * Checks if the thief has stolen the treasure. The player can escape the thief if he can get
   * away from the tunnel in 5 seconds. If the player doesn't escape the tunnel in under 5 seconds,
   * then he loses all of the treasure.
   *
   * @param time represents the current system time.
   */
  void checkTreasureStolen(Long time);

  /**
   * Checks if the player has fallen into the pit. If the player does not have one treasure of each
   * type then the player falls in the pit and dies.
   *
   * @returns true of the player has fallen into the pit, false otherwise.
   */
  boolean isFallen();

  /**
   * Gets the number of rows of the dungeon.
   *
   * @returns the number of rows of the dungeon.
   */
  int getRows();

  /**
   * Gets the number of columns of the dungeon.
   *
   * @returns the number of columns of the dungeon.
   */
  int getColumns();
}

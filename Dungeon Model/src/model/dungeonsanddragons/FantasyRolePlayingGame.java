package model.dungeonsanddragons;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * FantasyRolePlayingGame class represents the platform to play the game of dungeon and dragons. The
 * system provides a way for the user of the program to create wrapping or a non-wrapping dungeon
 * represented as a 2d grid of caves and tunnels. Each location in the dungeon is connected in such
 * a way that the player can traverse all the nodes. The system allows for the treasure, monsters
 * and arrows allocation in caves and tunnels. It also provides a provision for a player to pick up
 * treasure or arrows and slay monsters as they traverse along the different locations of the
 * dungeon. The system also allows for player to win the game by reaching the end tunnel without
 * getting killed or falling in the pit. Since the user has to have access to the game to be able
 * to create the dungeon and control the player actions, this class is kept as public.
 */
public class FantasyRolePlayingGame implements Game {

  private final Dungeon dungeon;
  private final Player player;
  private final Random random;
  private DungeonNode currentLocation;
  private DungeonNode previousLocation;
  private long moveMadeAt;
  private final int rows;
  private final int columns;

  /**
   * A constructor to create a dungeon with given rows and columns. Dungeon is represented as a 2d
   * grid of given rows and columns. Dungeon also has a degree of interconnectivity that the user
   * can set, wrapping status and the percentage of caves that the treasure needs to be added to.
   *
   * @param rows               represents the number of rows with which the dungeon needs to be
   *                           created.
   * @param columns            represents the number of columns with which the dungeon needs to be
   *                           created.
   * @param wrapping           represents the wrapping status of the dungeon.
   * @param interconnectivity  represents the degree of interconnectivity of the dungeon.
   * @param treasurePercentage represents the percentage of caves that the treasure should be
   *                           added.
   * @param numberOfMonsters   represents the number of monsters in the game.
   * @param thieves represents the number of thieves in the game.
   * @param pits represents the number of pits that should be added to the dungeon.
   * @throws IllegalStateException    if the dungeon size is too small to enforce 5 as minimum
   *                                  distance between execute and end. It also considers a scenario
   *                                  where the dungeon cannot be created with incorrect degree of
   *                                  interconnectivity.
   * @throws IllegalArgumentException if the user enters illegal arguments.
   */
  public FantasyRolePlayingGame(int rows, int columns, boolean wrapping, int interconnectivity,
      int treasurePercentage, int numberOfMonsters, int thieves,
      int pits) throws IllegalArgumentException,
      IllegalStateException {

    if (!(rows >= 5 && rows <= 100) || !(columns >= 5 && columns <= 100)
        || !(treasurePercentage >= 0 && treasurePercentage <= 100)) {
      throw new IllegalArgumentException("Invalid data in the parameters!");
    }

    if (pits < 0 || pits > 10) {
      throw new IllegalArgumentException("Illegal number of pits!");
    }

    if (thieves < 0 || thieves > 10) {
      throw new IllegalArgumentException("Illegal number of thieves!");
    }

    if (numberOfMonsters < 1 || numberOfMonsters > 20) {
      throw new IllegalArgumentException("Illegal number of monsters!");
    }

    /*In a non-wrapping dungeon total Horizontal and Vertical Edges is equal to
     (rows * ((cols*2) - 1)) - col. Total number of 'leftover' edges are building mst of
     (rows * cols - 1) is (rows * cols - rows - cols + 1)
     */
    if (!wrapping && !(interconnectivity >= 0
        && interconnectivity <= rows * columns - rows - columns + 1)) {
      throw new IllegalArgumentException("Invalid Interconnectivity degree!");
    }

    /*In a wrapping dungeon total Horizontal and Vertical Edges is equal to
     (rows * ((cols*2) - 1)) - col + row + col. Total number of 'leftover' edges are building mst
      of (rows * cols - 1) is (rows * cols + 1)
     */
    if (wrapping && !(interconnectivity >= 0
        && interconnectivity <= rows * columns + 1)) {
      throw new IllegalArgumentException("Invalid Interconnectivity degree!");
    }

    this.rows = rows;
    this.columns = columns;
    dungeon = new DungeonImpl(rows, columns, wrapping, new Random());
    dungeon.create(interconnectivity, treasurePercentage, numberOfMonsters, thieves, pits);
    this.player = new PlayerImpl("Marcus");
    this.currentLocation = dungeon.getStartingCave();
    this.previousLocation = dungeon.getStartingCave();
    this.player.setLocation(this.currentLocation);
    this.random = new Random();
    moveMadeAt = System.currentTimeMillis();
  }

  /**
   * A constructor to create a dungeon with given rows and columns. Dungeon is represented as a 2d
   * grid of given rows and columns. Dungeon also has a degree of interconnectivity that the user
   * can set, wrapping status and the percentage of caves that the treasure needs to be added to.
   *
   * @param rows               represents the number of rows with which the dungeon needs to be
   *                           created.
   * @param columns            represents the number of columns with which the dungeon needs to be
   *                           created.
   * @param wrapping           represents the wrapping status of the dungeon.
   * @param interconnectivity  represents the degree of interconnectivity of the dungeon.
   * @param treasurePercentage represents the percentage of caves that the treasure should be
   *                           added.
   * @param numberOfMonsters   represents the number of monsters in the game.
   * @param thieves represents the number of thieves in the game.
   * @param pits represents the number of pits that should be added to the dungeon.
   * @param random             represents the 'fixed' random implementation that will be passed for
   *                           the run.
   * @throws IllegalStateException    if the dungeon size is too small to enforce 5 as minimum
   *                                  distance between execute and end. It also considers a scenario
   *                                  where the dungeon cannot be created with incorrect degree of
   *                                  interconnectivity.
   * @throws IllegalArgumentException if the user enters illegal arguments.
   */
  public FantasyRolePlayingGame(int rows, int columns, boolean wrapping, int interconnectivity,
      int treasurePercentage, int numberOfMonsters, int thieves, int pits,
      Random random) throws IllegalArgumentException,
      IllegalStateException {

    if (!(rows >= 5 && rows <= 100) || !(columns >= 5 && columns <= 100)
        || !(treasurePercentage >= 0 && treasurePercentage <= 100)) {
      throw new IllegalArgumentException("Invalid data in the parameters!");
    }

    if (pits < 0 || pits > 10) {
      throw new IllegalArgumentException("Illegal number of pits!");
    }

    if (thieves < 0 || thieves > 10) {
      throw new IllegalArgumentException("Illegal number of thieves!");
    }

    if (numberOfMonsters < 1 || numberOfMonsters > 20) {
      throw new IllegalArgumentException("Illegal number of monsters!");
    }

    /*In a non-wrapping dungeon total Horizontal and Vertical Edges is equal to
     (rows * ((cols*2) - 1)) - col. Total number of 'leftover' edges are building mst of
     (rows * cols - 1) is (rows * cols - rows - cols + 1)
     */
    if (!wrapping && !(interconnectivity >= 0
        && interconnectivity <= rows * columns - rows - columns + 1)) {
      throw new IllegalArgumentException("Invalid Interconnectivity degree!");
    }

    /*In a wrapping dungeon total Horizontal and Vertical Edges is equal to
     (rows * ((cols*2) - 1)) - col + row + col. Total number of 'leftover' edges are building mst
      of (rows * cols - 1) is (rows * cols + 1)
     */
    if (wrapping && !(interconnectivity >= 0
        && interconnectivity <= rows * columns + 1)) {
      throw new IllegalArgumentException("Invalid Interconnectivity degree!");
    }

    if (Objects.isNull(random)) {
      throw new IllegalArgumentException("Invalid random Object!");
    }

    this.rows = rows;
    this.columns = columns;
    dungeon = new DungeonImpl(rows, columns, wrapping, random);
    dungeon.create(interconnectivity, treasurePercentage, numberOfMonsters, thieves, pits);
    this.player = new PlayerImpl("Marcus");
    this.currentLocation = dungeon.getStartingCave();
    this.previousLocation = dungeon.getStartingCave();
    this.player.setLocation(this.currentLocation);
    this.random = random;
  }

  /**
   * Gets the number of rows of the dungeon.
   *
   * @returns the number of rows of the dungeon.
   */
  @Override
  public int getRows() {
    return rows;
  }

  /**
   * Gets the number of columns of the dungeon.
   *
   * @returns the number of columns of the dungeon.
   */
  @Override
  public int getColumns() {
    return columns;
  }

  /**
   * Direction the player from the current location using the given direction. UP, DOWN, RIGHT, LEFT
   * which are the possible moves for the player.
   *
   * @param direction represents UP, DOWN, RIGHT, LEFT which are the possible moves for the player.
   * @throws IllegalStateException    if an illegal direction is being attempted.
   * @throws IllegalArgumentException if direction in the parameter is not up, down, left or right.
   */
  @Override
  public void movePlayer(Direction direction)
      throws IllegalStateException, IllegalArgumentException {

    if (Objects.isNull(direction)) {
      throw new IllegalArgumentException("Direction can't be null!");
    }

    player.setPreviousLocation(this.currentLocation);

    int currentRow = 0;
    int currentColumn = 0;

    for (int i = 0; i < dungeon.getNoOfRows(); i++) {
      for (int j = 0; j < dungeon.getNoOfColumns(); j++) {
        if (dungeon.getDungeonAs2D()[i][j].getDungeonNodeIdentifier()
            == this.currentLocation.getDungeonNodeIdentifier()) {
          currentRow = i;
          currentColumn = j;
        }
      }
    }

    switch (direction) {

      case UP:
        if ((this.currentLocation.getNorthConnection() == 1)) {
          if (currentRow == 0) {
            this.currentLocation =
                dungeon.getDungeonAs2D()[dungeon.getNoOfRows() - 1][currentColumn];
            player.setLocation(this.currentLocation);
          } else {
            this.currentLocation = dungeon.getDungeonAs2D()[currentRow - 1][currentColumn];
            player.setLocation(this.currentLocation);
          }
        } else {
          throw new IllegalStateException("Can't go Up!");
        }
        break;

      case DOWN:
        if (this.currentLocation.getSouthConnection() == 1) {
          if (currentRow == (dungeon.getNoOfRows() - 1)) {
            this.currentLocation = dungeon.getDungeonAs2D()[0][currentColumn];
            player.setLocation(this.currentLocation);
          } else {
            this.currentLocation = dungeon.getDungeonAs2D()[currentRow + 1][currentColumn];
            player.setLocation(this.currentLocation);
          }
        } else {
          throw new IllegalStateException("Can't go Down!");
        }
        break;

      case LEFT:
        if (this.currentLocation.getWestConnection() == 1) {
          if (currentColumn == 0) {
            this.currentLocation =
                dungeon.getDungeonAs2D()[currentRow][dungeon.getNoOfColumns() - 1];
            player.setLocation(this.currentLocation);
          } else {
            this.currentLocation = dungeon.getDungeonAs2D()[currentRow][currentColumn - 1];
            player.setLocation(this.currentLocation);
          }
        } else {
          throw new IllegalStateException("Can't go Left!");
        }
        break;

      case RIGHT:
        if (this.currentLocation.getEastConnection() == 1) {
          if (currentColumn == (dungeon.getNoOfColumns() - 1)) {
            this.currentLocation = dungeon.getDungeonAs2D()[currentRow][0];
            player.setLocation(this.currentLocation);
          } else {
            this.currentLocation = dungeon.getDungeonAs2D()[currentRow][currentColumn + 1];
            player.setLocation(this.currentLocation);
          }
        } else {
          throw new IllegalStateException("Can't go Right!");
        }
        break;

      default:
        throw new IllegalStateException("Direction Unavailable!");
    }

  }

  /**
   * Checks if the player has reached the end cave. This is required to determine if the game is
   * over.
   *
   * @returns true if the player has reached the end cave, false otherwise.
   */
  @Override
  public boolean isReachedEnd() {
    return (this.player.getLocation().getDungeonNodeIdentifier()
        == dungeon.getEndingCave().getDungeonNodeIdentifier())
        && this.player.getPlayerHealth() != 0;
  }

  /**
   * Checks if the player has been killed by the monster. This is required to determine if the game
   * is over. If the player enters the cave with an injured monster, the player has 50% chance of
   * surviving the game.
   *
   * @returns true if the player has been eaten by the monster, false otherwise.
   */
  @Override
  public boolean isKilled() {
    int luck = random.nextInt(2);
    if (this.player.getLocation().getMonster() != null
        && this.player.getLocation().getMonster().getHealth() == 100) {
      this.player.setPlayerHealth();
      return true;
    } else if (this.player.getLocation().getMonster() != null
        && this.player.getLocation().getMonster().getHealth() == 50) {
      return (luck == 1);
    } else {
      return false;
    }
  }

  /**
   * Gets the current game state of the player which can be accessed through a read-only end
   * point.
   *
   * @return the current game state of the player which can be accessed through a read-only end
   *          point.
   */
  @Override
  public ReadOnlyGame gameState() {

    int row = 0;
    int column = 0;

    for (int i = 0; i < dungeon.getNoOfRows(); i++) {
      for (int j = 0; j < dungeon.getNoOfColumns(); j++) {
        if (dungeon.getDungeonAs2D()[i][j].getDungeonNodeIdentifier()
            == this.player.getLocation().getDungeonNodeIdentifier()) {
          row = i;
          column = j;
          break;
        }
      }
    }

    ReadOnlyGame copyReadOnlyGame = new ReadOnlyGameImpl(
        this.player.getLocation().getDungeonNodeIdentifier(),
        this.player.getLocation().getNodeIsA().toString(),
        this.player.getLocation().getNorthConnection(),
        this.player.getLocation().getSouthConnection(),
        this.player.getLocation().getEastConnection(),
        this.player.getLocation().getWestConnection(),
        this.player.getLocation().getCaveTreasures(),
        this.player.getLocation().getMonster(),
        this.player.getLocation().getThief(),
        this.player.getLocation().getPit(),
        this.player.getLocation().getArrows(),
        row, column, this.getSmell() != null ? this.getSmell().toString() : "",
        this.getSound() != null ? this.getSound().toString() : "",
        this.player.getPlayerTreasures(), this.player.getPlayerArrows());

    return copyReadOnlyGame;
  }

  /**
   * Pick the specified treasure if it is available in the current node.
   *
   * @param treasure represents RUBY, EMERALD, DIAMOND, SAPPHIRE which are the possible treasures
   *                 that can be collected by the player.
   * @throws IllegalArgumentException if there is no treasure of given type in the current
   *                                  location.
   * @throws IllegalStateException    if player tries to pick treasure that doesn't exist.
   */
  @Override
  public void pickTreasure(Treasure treasure) throws IllegalStateException,
      IllegalArgumentException {

    if (Objects.isNull(treasure)) {
      throw new IllegalArgumentException("Treasure can't be null!");
    }

    boolean found = false;
    for (Treasure treas : this.currentLocation.getCaveTreasures()) {
      if (treas.toString().equals(treasure.toString())) {
        found = true;
      }
    }

    if (found) {
      this.player.addTreasure(treasure);
      this.currentLocation.removeCaveTreasure(treasure);
    } else {
      throw new IllegalStateException("There is no treasure of that type in this cave!");
    }
  }

  /**
   * Gets the list of treasures collected by the player.
   *
   * @return the list of treasures collected by the player.
   */
  @Override
  public List<Treasure> getTreasureCollectedByPlayer() {
    List<Treasure> copyTreasure = new ArrayList<>(this.player.getPlayerTreasures());
    return copyTreasure;
  }

  /**
   * Gets the 2D representation of the dungeon. Rows and columns of the 2d grid represent an
   * individual location which can be either a cave or a tunnel. Edges represent the connection with
   * the adjacent nodes. Lines(| or —)  represent the path the player can take.
   *
   * @return the 2D representation of the dungeon.
   */
  @Override
  public String printDungeonAs2D() {
    return dungeon.printDungeon();
  }

  /**
   * Gets the type of smell that the player is currently smelling from their location. Smell is
   * given out by the monsters. Player can use it to their advantage to survive and traverse through
   * the dungeon. There are 2 types of smells, Pungent and MorePungent.
   *
   * @return the type of smell that the player is currently smelling from their location.
   */
  @Override
  public Smell getSmell() {
    Smell copySmell = null;

    int count = 0;
    for (Interconnection edge : dungeon.getTwoLocationAway()) {
      if (currentLocation.getDungeonNodeIdentifier()
          == edge.getDestination().getDungeonNodeIdentifier()) {
        if (edge.getSource().getMonster() != null
            && edge.getSource().getMonster().getHealth() != 0) {
          copySmell = Smell.PUNGENT;
          count += 1;
        }
      }
    }

    for (Interconnection edge : dungeon.getOneLocationAway()) {
      if (currentLocation.getDungeonNodeIdentifier()
          == edge.getDestination().getDungeonNodeIdentifier()) {
        if (edge.getSource().getMonster() != null
            && edge.getSource().getMonster().getHealth() != 0) {
          copySmell = Smell.MOREPUNGENT;
        }
      }
    }

    if (count > 1) {
      copySmell = Smell.MOREPUNGENT;
    }

    return copySmell;
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
  public void shootArrow(int distance, Direction direction)
      throws IllegalStateException, IllegalArgumentException {

    if (distance < 0 || direction == null) {
      throw new IllegalArgumentException("Invalid values to shoot an arrow!");
    }

    int currentRow = 0;
    int currentColumn = 0;
    String tempMove = direction.toString();

    for (int i = 0; i < dungeon.getNoOfRows(); i++) {
      for (int j = 0; j < dungeon.getNoOfColumns(); j++) {
        if (dungeon.getDungeonAs2D()[i][j].getDungeonNodeIdentifier()
            == this.currentLocation.getDungeonNodeIdentifier()) {
          currentRow = i;
          currentColumn = j;
        }
      }
    }

    if (this.player.getPlayerArrows().size() > 0) {
      while (distance > 0) {

        if (tempMove.equals("UP") && distance > 0) {
          //Move the arrow one position to north.
          if ((dungeon.getDungeonAs2D()[currentRow][currentColumn].getNorthConnection() == 1)) {
            if (currentRow == 0) {
              currentRow = dungeon.getNoOfRows() - 1;
            } else {
              currentRow = currentRow - 1;
            }
          } else {
            distance = 0;
          }

          //If there is no further path from a cave, arrow no longer travels.
          if (dungeon.getDungeonAs2D()[currentRow][currentColumn].getNodeIsA()
              .equals(DungeonNodeType.CAVE)) {
            distance -= 1;
          }

          /*If the arrow is coming from south, then it can exit only via east or west if
          it's a tunnel.
           */
          if (dungeon.getDungeonAs2D()[currentRow][currentColumn]
              .getNodeIsA().equals(DungeonNodeType.TUNNEL)
              && dungeon.getDungeonAs2D()[currentRow][currentColumn].getEastConnection() == 1) {
            tempMove = "RIGHT";
          }

          if (dungeon.getDungeonAs2D()[currentRow][currentColumn]
              .getNodeIsA().equals(DungeonNodeType.TUNNEL)
              && dungeon.getDungeonAs2D()[currentRow][currentColumn].getWestConnection() == 1) {
            tempMove = "LEFT";
          }
        }

        if (tempMove.equals("DOWN") && distance > 0) {
          //Move the arrow one position to south.
          if ((dungeon.getDungeonAs2D()[currentRow][currentColumn].getSouthConnection() == 1)) {
            if (currentRow == (dungeon.getNoOfRows() - 1)) {
              currentRow = 0;
            } else {
              currentRow = currentRow + 1;
            }
          } else {
            distance = 0;
          }

          //If there is no further path from a cave, arrow no longer travels.
          if (dungeon.getDungeonAs2D()[currentRow][currentColumn].getNodeIsA()
              .equals(DungeonNodeType.CAVE)) {
            distance -= 1;
          }

          /*If the arrow is coming from north, then it can exit only via east or west if
          it's a tunnel.
           */
          if (dungeon.getDungeonAs2D()[currentRow][currentColumn]
              .getNodeIsA().equals(DungeonNodeType.TUNNEL)
              && dungeon.getDungeonAs2D()[currentRow][currentColumn].getEastConnection() == 1) {
            tempMove = "RIGHT";
          }

          if (dungeon.getDungeonAs2D()[currentRow][currentColumn]
              .getNodeIsA().equals(DungeonNodeType.TUNNEL)
              && dungeon.getDungeonAs2D()[currentRow][currentColumn].getWestConnection() == 1) {
            tempMove = "LEFT";
          }
        }

        if (tempMove.equals("RIGHT") && distance > 0) {
          //Move the arrow one position to east.
          if ((dungeon.getDungeonAs2D()[currentRow][currentColumn].getEastConnection() == 1)) {
            if (currentColumn == (dungeon.getNoOfColumns() - 1)) {
              currentColumn = 0;
            } else {
              currentColumn = currentColumn + 1;
            }
          } else {
            distance = 0;
          }

          //If there is no further path from a cave, arrow no longer travels.
          if (dungeon.getDungeonAs2D()[currentRow][currentColumn].getNodeIsA()
              .equals(DungeonNodeType.CAVE)) {
            distance -= 1;
          }

          /*If the arrow is coming from west, then it can exit only via north or south if
          it's a tunnel.
           */
          if (dungeon.getDungeonAs2D()[currentRow][currentColumn]
              .getNodeIsA().equals(DungeonNodeType.TUNNEL)
              && dungeon.getDungeonAs2D()[currentRow][currentColumn].getNorthConnection() == 1) {
            tempMove = "UP";
          }

          if (dungeon.getDungeonAs2D()[currentRow][currentColumn]
              .getNodeIsA().equals(DungeonNodeType.TUNNEL)
              && dungeon.getDungeonAs2D()[currentRow][currentColumn].getSouthConnection() == 1) {
            tempMove = "DOWN";
          }
        }

        if (tempMove.equals("LEFT") && distance > 0) {
          //Move the arrow one position to west.
          if ((dungeon.getDungeonAs2D()[currentRow][currentColumn].getWestConnection() == 1)) {
            if (currentColumn == 0) {
              currentColumn = dungeon.getNoOfColumns() - 1;
            } else {
              currentColumn = currentColumn - 1;
            }
          } else {
            distance = 0;
          }

          //If there is no further path from a cave, arrow no longer travels.
          if (dungeon.getDungeonAs2D()[currentRow][currentColumn].getNodeIsA()
              .equals(DungeonNodeType.CAVE)) {
            distance -= 1;
          }

          /*If the arrow is coming from east, then it can exit only via north or south if
          it's a tunnel.
           */
          if (dungeon.getDungeonAs2D()[currentRow][currentColumn]
              .getNodeIsA().equals(DungeonNodeType.TUNNEL)
              && dungeon.getDungeonAs2D()[currentRow][currentColumn].getNorthConnection() == 1) {
            tempMove = "UP";
          }

          if (dungeon.getDungeonAs2D()[currentRow][currentColumn]
              .getNodeIsA().equals(DungeonNodeType.TUNNEL)
              && dungeon.getDungeonAs2D()[currentRow][currentColumn].getSouthConnection() == 1) {
            tempMove = "DOWN";
          }
        }
      }

      //Slay the monster only if the distance is exact
      if (distance != -1) {
        dungeon.getDungeonAs2D()[currentRow][currentColumn].updateMonster();
      }
      this.player.removeArrow();

    } else {
      throw new IllegalStateException("Oops. No more arrows!");
    }
  }

  /**
   * Picks an arrow and adds it to the player's bag so that it can be further used to slay a
   * monster.
   */
  @Override
  public void pickArrow() {
    if (currentLocation.getArrows().size() > 0) {
      player.addArrow();
      currentLocation.removeArrow();
    } else {
      throw new IllegalStateException("No arrow to pick!");
    }
  }

  /**
   * Gets the arrows currently in the player bag.
   *
   * @return the arrows currently in the player bag.
   */
  @Override
  public List<Weapon> getArrowsInPlayerBag() {
    List<Weapon> copyWeapon = new ArrayList<>(this.player.getPlayerArrows());
    return copyWeapon;
  }

  /**
   * Checks if the thief has stolen the treasure. The player can escape the thief if he can get
   * away from the tunnel in 5 seconds. If the player doesn't escape the tunnel in under 5 seconds,
   * then he loses all of the treasure.
   *
   * @param time represents the current system time.
   */
  @Override
  public void checkTreasureStolen(Long time) {
    if ((time - moveMadeAt) > 5000) {
      if (player.getPreviousLocation().getThief() != null) {
        player.clearTreasure();
        player.clearArrows();
      }
    }
    moveMadeAt = System.currentTimeMillis();
  }

  /**
   * Gets the type of sound that the player can hear to identify near-by pit. CRACKLING sound can
   * be heard by the player when the player is one location away from the pit. Since the user uses
   * hearing to traverse through the dungeon, this enumeration is kept as public.
   *
   * @return the type of sound that the player can hear to identify near-by pit.
   */
  @Override
  public Sound getSound() {
    Sound copySound = null;

    for (Interconnection edge : dungeon.getOneLocationAway()) {
      if (currentLocation.getDungeonNodeIdentifier()
          == edge.getDestination().getDungeonNodeIdentifier()) {
        if (edge.getSource().getPit() != null) {
          copySound = Sound.CRACKLING;
        }
      }
    }
    return copySound;
  }

  /**
   * Checks if the player has fallen into the pit. If the player does not have one treasure of each
   * type then the player falls in the pit and dies.
   *
   * @returns true of the player has fallen into the pit, false otherwise.
   */
  @Override
  public boolean isFallen() {
    return  (player.getLocation().getPit() != null && (player.getPlayerDiamondCount() < 1)
        && (player.getPlayerRubyCount() < 1) && (player.getPlayerSapphireCount() < 1));
  }
}
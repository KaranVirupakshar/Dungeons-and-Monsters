package model.dungeonsanddragons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import random.FixedRandom;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Testing framework for both dungeon creation and player actions which include movement,
 * picking treasure, arrows and shooting arrows to slay the monster which enables the player
 * to win the game.
 */
public class FantasyRolePlayingGameTest {

  private Game game;
  private Game fantasyGame;

  /**
   * Initializing the game object that is going to be used in the test suite.
   */
  @Before
  public void setUp() {
    fantasyGame = new FantasyRolePlayingGame(5, 5, false, 4, 50, 2, 2, 1, new FixedRandom());
  }

  /**
   * Testing dungeon creation with illegal rows.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testDungeonCreationIllegalRows() {
    fantasyGame = new FantasyRolePlayingGame(3, 5, false, 0, 50, 5, 2, 1, new FixedRandom());
    fantasyGame = new FantasyRolePlayingGame(120, 5, false, 0, 50, 5, 2, 1, new FixedRandom());
  }

  /**
   * Testing dungeon creation with illegal columns.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testDungeonCreationIllegalColumns() {
    game = new FantasyRolePlayingGame(5, 3, false, 0, 50, 5, 2, 1, new FixedRandom());
    game = new FantasyRolePlayingGame(5, 120, false, 0, 50, 5, 2,1, new FixedRandom());
  }

  /**
   * Testing dungeon creation with illegal interconnectivity degree.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testDungeonCreationIllegalDegree() {
    game = new FantasyRolePlayingGame(5, 5, false, 18, 50, 5, 2, 1, new FixedRandom());
  }

  /**
   * Testing dungeon creation with illegal treasure percentage is entered.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testDungeonCreationIllegalTreasureNegative() {
    game = new FantasyRolePlayingGame(5, 5, true, 16, -2, 5, 2, 1, new FixedRandom());
  }

  /**
   * Testing dungeon creation with illegal treasure percentage is entered.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testDungeonCreationIllegalTreasurePositive() {
    game = new FantasyRolePlayingGame(5, 5, true, 16, 120, 5, 2, 1, new FixedRandom());
  }

  /**
   * Testing dungeon creation with illegal number of monsters is entered.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testDungeonCreationIllegalMonsters() {
    game = new FantasyRolePlayingGame(5, 5, true, 16, 120, 0, 2, 1, new FixedRandom());
  }

  /**
   * Testing dungeon creation edges are not wrapping when dungeon is non-wrapping.
   */
  @Test(expected = IllegalStateException.class)
  public void testDungeonNonWrappingCreation() {
    game = new FantasyRolePlayingGame(5, 5, false, 0, 50, 5, 2, 1, new FixedRandom());

    String s = "";

    s = "  <T 0> —— <T 1> —— <T 2> —— <T 3> —— <C 4>  \n"
        + "    |                                        \n"
        + "  <C 5> —— <T 6> —— <T 7> —— <T 8> —— <C 9>  \n"
        + "    |                                        \n"
        + "  <C10> —— <T11> —— <T12> —— <T13> —— <C14>  \n"
        + "    |                                        \n"
        + "  <C15> —— <T16> —— <T17> —— <T18> —— <C19>  \n"
        + "    |                                        \n"
        + "  <T20> —— <T21> —— <T22> —— <T23> —— <C24>  \n"
        + "                                             ";

    s += "\n";

    //Asserting on string

    assertEquals(s, game.printDungeonAs2D());

    //Trying to make a wrapping move by the player

    String checkLoc = "";
    String actLoc = "";

    checkLoc = "Location: 4, Type: CAVE, Possible Moves: [LEFT] Treasure: [DIAMOND]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        game.gameState().getLocationId(),
        game.gameState().getLocationType(),
        game.gameState().getPossibleMoves(),
        game.gameState().getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
  }


  /**
   * Testing that mock random generator always return the fixed number set (it is set as zero).
   */
  @Test
  public void TestMockRandom() {
    Random r = new FixedRandom();
    int a = r.nextInt(3);

    assertEquals(0, a);

    a = r.nextInt();

    assertEquals(0, a);
  }

  /**
   * Testing that the player begins at the start location and finishes at the end location.
   */
  @Test
  public void testPlayerBeginningAtStartEndingAtEnd() {

    game = new FantasyRolePlayingGame(5, 5, false, 0, 50, 1, 2, 1, new FixedRandom());

    String s = "";

    s = "  <T 0> —— <T 1> —— <T 2> —— <T 3> —— <C 4>  \n"
        + "    |                                        \n"
        + "  <C 5> —— <T 6> —— <T 7> —— <T 8> —— <C 9>  \n"
        + "    |                                        \n"
        + "  <C10> —— <T11> —— <T12> —— <T13> —— <C14>  \n"
        + "    |                                        \n"
        + "  <C15> —— <T16> —— <T17> —— <T18> —— <C19>  \n"
        + "    |                                        \n"
        + "  <T20> —— <T21> —— <T22> —— <T23> —— <C24>  \n"
        + "                                             ";

    s += "\n";

    //Asserting on string

    assertEquals(s, game.printDungeonAs2D());

    /*
    By fixing the random and traversing the player, we know that the starting cave is 4 and ending
    cave is 5.
     */
    List<ReadOnlyGame> li = new ArrayList<>();
    String checkLoc = "";
    String actLoc = "";

    li.add(game.gameState());
    checkLoc = "Location: 4, Type: CAVE, Possible Moves: [LEFT] Treasure: [DIAMOND]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(0).getLocationId(),
        li.get(0).getLocationType(),
        li.get(0).getPossibleMoves(),
        li.get(0).getTreasure());
    assertEquals(checkLoc, actLoc);

    assertEquals(false, game.isReachedEnd());

    game.movePlayer(Direction.LEFT);
    li.add(game.gameState());
    checkLoc = "Location: 3, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(1).getLocationId(),
        li.get(1).getLocationType(),
        li.get(1).getPossibleMoves(),
        li.get(1).getTreasure());
    assertEquals(checkLoc, actLoc);

    assertEquals(false, game.isReachedEnd());

    game.movePlayer(Direction.LEFT);
    li.add(game.gameState());
    checkLoc = "Location: 2, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(2).getLocationId(),
        li.get(2).getLocationType(),
        li.get(2).getPossibleMoves(),
        li.get(2).getTreasure());
    assertEquals(checkLoc, actLoc);

    assertEquals(false, game.isReachedEnd());

    game.movePlayer(Direction.LEFT);
    li.add(game.gameState());
    checkLoc = "Location: 1, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(3).getLocationId(),
        li.get(3).getLocationType(),
        li.get(3).getPossibleMoves(),
        li.get(3).getTreasure());
    assertEquals(checkLoc, actLoc);

    assertEquals(false, game.isReachedEnd());

    game.movePlayer(Direction.LEFT);
    li.add(game.gameState());
    checkLoc = "Location: 0, Type: TUNNEL, Possible Moves: [DOWN, RIGHT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(4).getLocationId(),
        li.get(4).getLocationType(),
        li.get(4).getPossibleMoves(),
        li.get(4).getTreasure());
    assertEquals(checkLoc, actLoc);

    assertEquals(false, game.isReachedEnd());


    game.movePlayer(Direction.DOWN);
    li.add(game.gameState());
    checkLoc = "Location: 5, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT] Treasure: [DIAMOND]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(5).getLocationId(),
        li.get(5).getLocationType(),
        li.get(5).getPossibleMoves(),
        li.get(5).getTreasure());
    assertEquals(checkLoc, actLoc);

    assertEquals(true, game.isReachedEnd());
  }

  /**
   * Testing that the player is able to visit all locations in the dungeon grid.
   */
  @Test
  public void testPlayerVisitingAllLocation() {

    game = new FantasyRolePlayingGame(5, 5, false, 0, 50, 1, 2, 1, new FixedRandom());

    String s = "";

    s = "  <T 0> —— <T 1> —— <T 2> —— <T 3> —— <C 4>  \n"
        + "    |                                        \n"
        + "  <C 5> —— <T 6> —— <T 7> —— <T 8> —— <C 9>  \n"
        + "    |                                        \n"
        + "  <C10> —— <T11> —— <T12> —— <T13> —— <C14>  \n"
        + "    |                                        \n"
        + "  <C15> —— <T16> —— <T17> —— <T18> —— <C19>  \n"
        + "    |                                        \n"
        + "  <T20> —— <T21> —— <T22> —— <T23> —— <C24>  \n"
        + "                                             ";

    s += "\n";

    //Asserting on string

    assertEquals(s, game.printDungeonAs2D());

    /*Traversing the player through all the locations of the dungeon.
     */
    List<ReadOnlyGame> li = new ArrayList<>();
    String checkLoc = "";
    String actLoc = "";

    li.add(game.gameState());
    checkLoc = "Location: 4, Type: CAVE, Possible Moves: [LEFT] Treasure: [DIAMOND]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(0).getLocationId(),
        li.get(0).getLocationType(),
        li.get(0).getPossibleMoves(),
        li.get(0).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    li.add(game.gameState());
    checkLoc = "Location: 3, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(1).getLocationId(),
        li.get(1).getLocationType(),
        li.get(1).getPossibleMoves(),
        li.get(1).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    li.add(game.gameState());
    checkLoc = "Location: 2, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(2).getLocationId(),
        li.get(2).getLocationType(),
        li.get(2).getPossibleMoves(),
        li.get(2).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    li.add(game.gameState());
    checkLoc = "Location: 1, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(3).getLocationId(),
        li.get(3).getLocationType(),
        li.get(3).getPossibleMoves(),
        li.get(3).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    li.add(game.gameState());
    checkLoc = "Location: 0, Type: TUNNEL, Possible Moves: [DOWN, RIGHT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(4).getLocationId(),
        li.get(4).getLocationType(),
        li.get(4).getPossibleMoves(),
        li.get(4).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.DOWN);
    li.add(game.gameState());
    checkLoc = "Location: 5, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT] Treasure: [DIAMOND]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(5).getLocationId(),
        li.get(5).getLocationType(),
        li.get(5).getPossibleMoves(),
        li.get(5).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 6, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(6).getLocationId(),
        li.get(6).getLocationType(),
        li.get(6).getPossibleMoves(),
        li.get(6).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 7, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(7).getLocationId(),
        li.get(7).getLocationType(),
        li.get(7).getPossibleMoves(),
        li.get(7).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 8, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(8).getLocationId(),
        li.get(8).getLocationType(),
        li.get(8).getPossibleMoves(),
        li.get(8).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 9, Type: CAVE, Possible Moves: [LEFT] Treasure: [DIAMOND]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(9).getLocationId(),
        li.get(9).getLocationType(),
        li.get(9).getPossibleMoves(),
        li.get(9).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);

    game.movePlayer(Direction.DOWN);
    li.add(game.gameState());
    checkLoc = "Location: 10, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT] Treasure: [DIAMOND]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(10).getLocationId(),
        li.get(10).getLocationType(),
        li.get(10).getPossibleMoves(),
        li.get(10).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 11, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(11).getLocationId(),
        li.get(11).getLocationType(),
        li.get(11).getPossibleMoves(),
        li.get(11).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 12, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(12).getLocationId(),
        li.get(12).getLocationType(),
        li.get(12).getPossibleMoves(),
        li.get(12).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 13, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(13).getLocationId(),
        li.get(13).getLocationType(),
        li.get(13).getPossibleMoves(),
        li.get(13).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 14, Type: CAVE, Possible Moves: [LEFT] Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(14).getLocationId(),
        li.get(14).getLocationType(),
        li.get(14).getPossibleMoves(),
        li.get(14).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);

    game.movePlayer(Direction.DOWN);
    li.add(game.gameState());
    checkLoc = "Location: 15, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT] Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(15).getLocationId(),
        li.get(15).getLocationType(),
        li.get(15).getPossibleMoves(),
        li.get(15).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 16, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(16).getLocationId(),
        li.get(16).getLocationType(),
        li.get(16).getPossibleMoves(),
        li.get(16).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 17, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(17).getLocationId(),
        li.get(17).getLocationType(),
        li.get(17).getPossibleMoves(),
        li.get(17).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 18, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(18).getLocationId(),
        li.get(18).getLocationType(),
        li.get(18).getPossibleMoves(),
        li.get(18).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 19, Type: CAVE, Possible Moves: [LEFT] Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(19).getLocationId(),
        li.get(19).getLocationType(),
        li.get(19).getPossibleMoves(),
        li.get(19).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);

    game.movePlayer(Direction.DOWN);
    li.add(game.gameState());
    checkLoc = "Location: 20, Type: TUNNEL, Possible Moves: [UP, RIGHT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(20).getLocationId(),
        li.get(20).getLocationType(),
        li.get(20).getPossibleMoves(),
        li.get(20).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 21, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(21).getLocationId(),
        li.get(21).getLocationType(),
        li.get(21).getPossibleMoves(),
        li.get(21).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 22, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(22).getLocationId(),
        li.get(22).getLocationType(),
        li.get(22).getPossibleMoves(),
        li.get(22).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 23, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(23).getLocationId(),
        li.get(23).getLocationType(),
        li.get(23).getPossibleMoves(),
        li.get(23).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 24, Type: CAVE, Possible Moves: [LEFT] Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(24).getLocationId(),
        li.get(24).getLocationType(),
        li.get(24).getPossibleMoves(),
        li.get(24).getTreasure());
    assertEquals(checkLoc, actLoc);
  }

  /**
   * Testing rows, columns and interconnectivity degree while creating dungeon.
   */
  @Test
  public void testInterConnectivityRowsAndColumns() {
    game = new FantasyRolePlayingGame(5, 5, false, 7, 50, 1, 2, 1, new FixedRandom());

    String s = "";

    s = "  <T 0> —— <C 1> —— <C 2> —— <T 3> —— <C 4>  \n"
        + "    |        |        |                      \n"
        + "  <C 5> —— <C 6> —— <C 7> —— <T 8> —— <C 9>  \n"
        + "    |        |        |                      \n"
        + "  <C10> —— <C11> —— <C12> —— <T13> —— <C14>  \n"
        + "    |        |        |                      \n"
        + "  <C15> —— <C16> —— <C17> —— <T18> —— <C19>  \n"
        + "    |        |                               \n"
        + "  <T20> —— <C21> —— <T22> —— <T23> —— <C24>  \n"
        + "                                             ";

    s += "\n";

    //Asserting on string

    assertEquals(s, game.printDungeonAs2D());

    /*Asserting on attributes (row column numbers, treasures/arrows/monster allocated) of
    each node by traversing the player through all the locations.
     */

    List<ReadOnlyGame> li = new ArrayList<>();
    String checkLoc = "";
    String actLoc = "";

    li.add(game.gameState());
    checkLoc = "Location: 1, Type: CAVE, Possible Moves: [DOWN, RIGHT, LEFT] Treasure: [DIAMOND]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(0).getLocationId(),
        li.get(0).getLocationType(),
        li.get(0).getPossibleMoves(),
        li.get(0).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 2, Type: CAVE, Possible Moves: [DOWN, RIGHT, LEFT] Treasure: [DIAMOND]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(1).getLocationId(),
        li.get(1).getLocationType(),
        li.get(1).getPossibleMoves(),
        li.get(1).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 3, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(2).getLocationId(),
        li.get(2).getLocationType(),
        li.get(2).getPossibleMoves(),
        li.get(2).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 4, Type: CAVE, Possible Moves: [LEFT] Treasure: [DIAMOND]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(3).getLocationId(),
        li.get(3).getLocationType(),
        li.get(3).getPossibleMoves(),
        li.get(3).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);

    game.movePlayer(Direction.LEFT);
    li.add(game.gameState());
    checkLoc = "Location: 0, Type: TUNNEL, Possible Moves: [DOWN, RIGHT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(4).getLocationId(),
        li.get(4).getLocationType(),
        li.get(4).getPossibleMoves(),
        li.get(4).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.DOWN);
    li.add(game.gameState());
    checkLoc = "Location: 5, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT] Treasure: [DIAMOND]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(5).getLocationId(),
        li.get(5).getLocationType(),
        li.get(5).getPossibleMoves(),
        li.get(5).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 6, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT, LEFT] "
        + "Treasure: [DIAMOND]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(6).getLocationId(),
        li.get(6).getLocationType(),
        li.get(6).getPossibleMoves(),
        li.get(6).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 7, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT, LEFT] "
        + "Treasure: [DIAMOND]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(7).getLocationId(),
        li.get(7).getLocationType(),
        li.get(7).getPossibleMoves(),
        li.get(7).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 8, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(8).getLocationId(),
        li.get(8).getLocationType(),
        li.get(8).getPossibleMoves(),
        li.get(8).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 9, Type: CAVE, Possible Moves: [LEFT] Treasure: [DIAMOND]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(9).getLocationId(),
        li.get(9).getLocationType(),
        li.get(9).getPossibleMoves(),
        li.get(9).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);

    game.movePlayer(Direction.DOWN);
    li.add(game.gameState());
    checkLoc = "Location: 10, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT] Treasure: [DIAMOND]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(10).getLocationId(),
        li.get(10).getLocationType(),
        li.get(10).getPossibleMoves(),
        li.get(10).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 11, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT, LEFT] "
        + "Treasure: [DIAMOND]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(11).getLocationId(),
        li.get(11).getLocationType(),
        li.get(11).getPossibleMoves(),
        li.get(11).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 12, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT, LEFT] Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(12).getLocationId(),
        li.get(12).getLocationType(),
        li.get(12).getPossibleMoves(),
        li.get(12).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 13, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(13).getLocationId(),
        li.get(13).getLocationType(),
        li.get(13).getPossibleMoves(),
        li.get(13).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 14, Type: CAVE, Possible Moves: [LEFT] Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(14).getLocationId(),
        li.get(14).getLocationType(),
        li.get(14).getPossibleMoves(),
        li.get(14).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);

    game.movePlayer(Direction.DOWN);
    li.add(game.gameState());
    checkLoc = "Location: 15, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT] Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(15).getLocationId(),
        li.get(15).getLocationType(),
        li.get(15).getPossibleMoves(),
        li.get(15).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 16, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT, LEFT] Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(16).getLocationId(),
        li.get(16).getLocationType(),
        li.get(16).getPossibleMoves(),
        li.get(16).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 17, Type: CAVE, Possible Moves: [UP, RIGHT, LEFT] Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(17).getLocationId(),
        li.get(17).getLocationType(),
        li.get(17).getPossibleMoves(),
        li.get(17).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 18, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(18).getLocationId(),
        li.get(18).getLocationType(),
        li.get(18).getPossibleMoves(),
        li.get(18).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 19, Type: CAVE, Possible Moves: [LEFT] Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(19).getLocationId(),
        li.get(19).getLocationType(),
        li.get(19).getPossibleMoves(),
        li.get(19).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);

    game.movePlayer(Direction.DOWN);
    li.add(game.gameState());
    checkLoc = "Location: 20, Type: TUNNEL, Possible Moves: [UP, RIGHT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(20).getLocationId(),
        li.get(20).getLocationType(),
        li.get(20).getPossibleMoves(),
        li.get(20).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 21, Type: CAVE, Possible Moves: [UP, RIGHT, LEFT] Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(21).getLocationId(),
        li.get(21).getLocationType(),
        li.get(21).getPossibleMoves(),
        li.get(21).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 22, Type: TUNNEL, Possible Moves: [RIGHT, LEFT]"
        + " Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(22).getLocationId(),
        li.get(22).getLocationType(),
        li.get(22).getPossibleMoves(),
        li.get(22).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 23, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(23).getLocationId(),
        li.get(23).getLocationType(),
        li.get(23).getPossibleMoves(),
        li.get(23).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 24, Type: CAVE, Possible Moves: [LEFT] Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(24).getLocationId(),
        li.get(24).getLocationType(),
        li.get(24).getPossibleMoves(),
        li.get(24).getTreasure());
    assertEquals(checkLoc, actLoc);

    int count = 0;
    for (ReadOnlyGame readOnlyGame : li) {
      count += readOnlyGame.getPossibleMoves().size();
    }
    count = count / 2;

    /*
    At each node, we count the number of neighbors it has and add it to the counter. We then assert
    this to be equal to (row * columns - 1 + interconnectivity) * 2. The reason for this is we
    expect n-1 edges for a MST with n nodes. Then we add the interconnectivity number of edges.
    The reason we multiply by 2 is that each edge is counted twice (say an edge that connects a-b,
    you'll count that edge once from a, and that edge once from b).
     */
    assertEquals(62, count);

    //Asserting on number of nodes in the grid.

    assertEquals(25, li.size());
  }

  /**
   * Testing player picking existing treasure.
   */
  @Test
  public void testPickTreasureExisting() {

    game = new FantasyRolePlayingGame(5, 5, false, 0, 50, 1, 2, 1, new FixedRandom());

    String checkLoc = "";
    String actLoc = "";

    checkLoc = "Location: 4, Type: CAVE, Possible Moves: [LEFT] Treasure: [DIAMOND], "
        + "Arrow: [], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: %s",
        game.gameState().getLocationId(),
        game.gameState().getLocationType(),
        game.gameState().getPossibleMoves(),
        game.gameState().getTreasure(),
        game.gameState().getArrows(),
        game.gameState().getMonster());
    assertEquals(checkLoc, actLoc);

    String collectedTreasure = "";

    for (Treasure treasure : game.getTreasureCollectedByPlayer()) {
      collectedTreasure += (treasure.toString()) + ",";
    }

    assertEquals("", collectedTreasure);

    game.pickTreasure(Treasure.DIAMOND);

    collectedTreasure = "";

    for (Treasure treasure : game.getTreasureCollectedByPlayer()) {
      collectedTreasure += (treasure.toString()) + ",";
    }

    collectedTreasure = collectedTreasure.substring(0, collectedTreasure.length() - 1);

    assertEquals("DIAMOND", collectedTreasure);

    checkLoc = "Location: 4, Type: CAVE, Possible Moves: [LEFT] Treasure: [], "
        + "Arrow: [], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: %s",
        game.gameState().getLocationId(),
        game.gameState().getLocationType(),
        game.gameState().getPossibleMoves(),
        game.gameState().getTreasure(),
        game.gameState().getArrows(),
        game.gameState().getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);

    game.movePlayer(Direction.DOWN);
    checkLoc = "Location: 5, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT] Treasure: [DIAMOND]"
        + ", Arrow: [], Monster: OTYUGH";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: %s",
        game.gameState().getLocationId(),
        game.gameState().getLocationType(),
        game.gameState().getPossibleMoves(),
        game.gameState().getTreasure(),
        game.gameState().getArrows(),
        game.gameState().getMonster());
    assertEquals(checkLoc, actLoc);

    game.pickTreasure(Treasure.DIAMOND);

    collectedTreasure = "";

    for (Treasure treasure : game.getTreasureCollectedByPlayer()) {
      collectedTreasure += (treasure.toString()) + ",";
    }

    collectedTreasure = collectedTreasure.substring(0, collectedTreasure.length() - 1);

    assertEquals("DIAMOND,DIAMOND", collectedTreasure);

    checkLoc = "Location: 5, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT] Treasure: []"
        + ", Arrow: [], Monster: OTYUGH";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: %s",
        game.gameState().getLocationId(),
        game.gameState().getLocationType(),
        game.gameState().getPossibleMoves(),
        game.gameState().getTreasure(),
        game.gameState().getArrows(),
        game.gameState().getMonster());
    assertEquals(checkLoc, actLoc);

  }

  /**
   * Testing player picking existing treasure.
   */
  @Test(expected = IllegalStateException.class)
  public void testPickTreasureNonExisting() {

    game = new FantasyRolePlayingGame(5, 5, false, 0, 50, 1, 2, 1, new FixedRandom());

    String checkLoc = "";
    String actLoc = "";

    checkLoc = "Location: 4, Type: CAVE, Possible Moves: [LEFT] Treasure: [DIAMOND], "
        + "Arrow: [], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: %s",
        game.gameState().getLocationId(),
        game.gameState().getLocationType(),
        game.gameState().getPossibleMoves(),
        game.gameState().getTreasure(),
        game.gameState().getArrows(),
        game.gameState().getMonster());
    assertEquals(checkLoc, actLoc);

    String collectedTreasure = "";

    for (Treasure treasure : game.getTreasureCollectedByPlayer()) {
      collectedTreasure += (treasure.toString()) + ",";
    }

    assertEquals("", collectedTreasure);

    game.pickTreasure(Treasure.RUBY);
  }


  /**
   * Testing player picking existing treasure by asserting on the players treasure bag.
   */
  @Test
  public void testPlayerTreasureBag() {

    game = new FantasyRolePlayingGame(5, 5, false, 0, 50, 1, 2, 1, new FixedRandom());

    String checkLoc = "";
    String actLoc = "";

    checkLoc = "Location: 4, Type: CAVE, Possible Moves: [LEFT] Treasure: [DIAMOND], "
        + "Arrow: [], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: %s",
        game.gameState().getLocationId(),
        game.gameState().getLocationType(),
        game.gameState().getPossibleMoves(),
        game.gameState().getTreasure(),
        game.gameState().getArrows(),
        game.gameState().getMonster());
    assertEquals(checkLoc, actLoc);

    game.pickTreasure(Treasure.DIAMOND);

    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);

    game.movePlayer(Direction.DOWN);
    checkLoc = "Location: 5, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT] Treasure: [DIAMOND]"
        + ", Arrow: [], Monster: OTYUGH";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: %s",
        game.gameState().getLocationId(),
        game.gameState().getLocationType(),
        game.gameState().getPossibleMoves(),
        game.gameState().getTreasure(),
        game.gameState().getArrows(),
        game.gameState().getMonster());
    assertEquals(checkLoc, actLoc);

    game.pickTreasure(Treasure.DIAMOND);

    assertEquals("[DIAMOND, DIAMOND]", game.getTreasureCollectedByPlayer().toString());
  }

  /**
   * Testing adding of treasure when percentage is non-zero.
   */
  @Test
  public void testTreasurePercentageAllocation() {
    game = new FantasyRolePlayingGame(5, 5, false, 7, 50, 1, 2, 1, new FixedRandom());

    String s = "";

    s = "  <T 0> —— <C 1> —— <C 2> —— <T 3> —— <C 4>  \n"
        + "    |        |        |                      \n"
        + "  <C 5> —— <C 6> —— <C 7> —— <T 8> —— <C 9>  \n"
        + "    |        |        |                      \n"
        + "  <C10> —— <C11> —— <C12> —— <T13> —— <C14>  \n"
        + "    |        |        |                      \n"
        + "  <C15> —— <C16> —— <C17> —— <T18> —— <C19>  \n"
        + "    |        |                               \n"
        + "  <T20> —— <C21> —— <T22> —— <T23> —— <C24>  \n"
        + "                                             ";

    s += "\n";

    //Asserting on string

    assertEquals(s, game.printDungeonAs2D());

    /*Asserting on attributes (row column numbers, treasures/arrows/monster allocated) of
    each node by traversing the player through all the locations.
     */

    List<ReadOnlyGame> li = new ArrayList<>();
    String checkLoc = "";
    String actLoc = "";

    li.add(game.gameState());
    checkLoc = "Location: 1, Type: CAVE, Possible Moves: [DOWN, RIGHT, LEFT] Treasure: [DIAMOND]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(0).getLocationId(),
        li.get(0).getLocationType(),
        li.get(0).getPossibleMoves(),
        li.get(0).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 2, Type: CAVE, Possible Moves: [DOWN, RIGHT, LEFT] Treasure: [DIAMOND]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(1).getLocationId(),
        li.get(1).getLocationType(),
        li.get(1).getPossibleMoves(),
        li.get(1).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 3, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(2).getLocationId(),
        li.get(2).getLocationType(),
        li.get(2).getPossibleMoves(),
        li.get(2).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 4, Type: CAVE, Possible Moves: [LEFT] Treasure: [DIAMOND]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(3).getLocationId(),
        li.get(3).getLocationType(),
        li.get(3).getPossibleMoves(),
        li.get(3).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);

    game.movePlayer(Direction.LEFT);
    li.add(game.gameState());
    checkLoc = "Location: 0, Type: TUNNEL, Possible Moves: [DOWN, RIGHT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(4).getLocationId(),
        li.get(4).getLocationType(),
        li.get(4).getPossibleMoves(),
        li.get(4).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.DOWN);
    li.add(game.gameState());
    checkLoc = "Location: 5, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT] Treasure: [DIAMOND]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(5).getLocationId(),
        li.get(5).getLocationType(),
        li.get(5).getPossibleMoves(),
        li.get(5).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 6, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT, LEFT] "
        + "Treasure: [DIAMOND]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(6).getLocationId(),
        li.get(6).getLocationType(),
        li.get(6).getPossibleMoves(),
        li.get(6).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 7, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT, LEFT] "
        + "Treasure: [DIAMOND]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(7).getLocationId(),
        li.get(7).getLocationType(),
        li.get(7).getPossibleMoves(),
        li.get(7).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 8, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(8).getLocationId(),
        li.get(8).getLocationType(),
        li.get(8).getPossibleMoves(),
        li.get(8).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 9, Type: CAVE, Possible Moves: [LEFT] Treasure: [DIAMOND]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(9).getLocationId(),
        li.get(9).getLocationType(),
        li.get(9).getPossibleMoves(),
        li.get(9).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);

    game.movePlayer(Direction.DOWN);
    li.add(game.gameState());
    checkLoc = "Location: 10, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT] Treasure: [DIAMOND]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(10).getLocationId(),
        li.get(10).getLocationType(),
        li.get(10).getPossibleMoves(),
        li.get(10).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 11, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT, LEFT] "
        + "Treasure: [DIAMOND]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(11).getLocationId(),
        li.get(11).getLocationType(),
        li.get(11).getPossibleMoves(),
        li.get(11).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 12, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT, LEFT] Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(12).getLocationId(),
        li.get(12).getLocationType(),
        li.get(12).getPossibleMoves(),
        li.get(12).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 13, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(13).getLocationId(),
        li.get(13).getLocationType(),
        li.get(13).getPossibleMoves(),
        li.get(13).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 14, Type: CAVE, Possible Moves: [LEFT] Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(14).getLocationId(),
        li.get(14).getLocationType(),
        li.get(14).getPossibleMoves(),
        li.get(14).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);

    game.movePlayer(Direction.DOWN);
    li.add(game.gameState());
    checkLoc = "Location: 15, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT] Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(15).getLocationId(),
        li.get(15).getLocationType(),
        li.get(15).getPossibleMoves(),
        li.get(15).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 16, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT, LEFT] Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(16).getLocationId(),
        li.get(16).getLocationType(),
        li.get(16).getPossibleMoves(),
        li.get(16).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 17, Type: CAVE, Possible Moves: [UP, RIGHT, LEFT] Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(17).getLocationId(),
        li.get(17).getLocationType(),
        li.get(17).getPossibleMoves(),
        li.get(17).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 18, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(18).getLocationId(),
        li.get(18).getLocationType(),
        li.get(18).getPossibleMoves(),
        li.get(18).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 19, Type: CAVE, Possible Moves: [LEFT] Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(19).getLocationId(),
        li.get(19).getLocationType(),
        li.get(19).getPossibleMoves(),
        li.get(19).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);

    game.movePlayer(Direction.DOWN);
    li.add(game.gameState());
    checkLoc = "Location: 20, Type: TUNNEL, Possible Moves: [UP, RIGHT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(20).getLocationId(),
        li.get(20).getLocationType(),
        li.get(20).getPossibleMoves(),
        li.get(20).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 21, Type: CAVE, Possible Moves: [UP, RIGHT, LEFT] Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(21).getLocationId(),
        li.get(21).getLocationType(),
        li.get(21).getPossibleMoves(),
        li.get(21).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 22, Type: TUNNEL, Possible Moves: [RIGHT, LEFT]"
        + " Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(22).getLocationId(),
        li.get(22).getLocationType(),
        li.get(22).getPossibleMoves(),
        li.get(22).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 23, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(23).getLocationId(),
        li.get(23).getLocationType(),
        li.get(23).getPossibleMoves(),
        li.get(23).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 24, Type: CAVE, Possible Moves: [LEFT] Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(24).getLocationId(),
        li.get(24).getLocationType(),
        li.get(24).getPossibleMoves(),
        li.get(24).getTreasure());
    assertEquals(checkLoc, actLoc);


    int treasureCount = 0;

    for (ReadOnlyGame lr : li) {
      if ((lr.getTreasure().size() != 0)) {
        treasureCount += 1;
      }
    }

    //Asserting on number of caves that hold the treasure (50% of 17 is 9 when rounded to ceiling).
    assertEquals(9, treasureCount);
  }

  /**
   * Testing if treasures are only added in caves.
   */
  @Test
  public void testTreasureOnlyInCave() {
    game = new FantasyRolePlayingGame(5, 5, false, 0, 50, 1, 2, 1, new FixedRandom());

    List<ReadOnlyGame> li = new ArrayList<>();

    String checkLoc = "";
    String actLoc = "";

    /*Asserting on attributes (row column numbers, treasures/arrows/monster allocated) of each node
    by traversing the player through all the locations. Only node type CAVE should be holding the
    treasure.
     */
    li.add(game.gameState());
    checkLoc = "Location: 4, Type: CAVE, Possible Moves: [LEFT] Treasure: [DIAMOND]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(0).getLocationId(),
        li.get(0).getLocationType(),
        li.get(0).getPossibleMoves(),
        li.get(0).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    li.add(game.gameState());
    checkLoc = "Location: 3, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(1).getLocationId(),
        li.get(1).getLocationType(),
        li.get(1).getPossibleMoves(),
        li.get(1).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    li.add(game.gameState());
    checkLoc = "Location: 2, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(2).getLocationId(),
        li.get(2).getLocationType(),
        li.get(2).getPossibleMoves(),
        li.get(2).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    li.add(game.gameState());
    checkLoc = "Location: 1, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(3).getLocationId(),
        li.get(3).getLocationType(),
        li.get(3).getPossibleMoves(),
        li.get(3).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    li.add(game.gameState());
    checkLoc = "Location: 0, Type: TUNNEL, Possible Moves: [DOWN, RIGHT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(4).getLocationId(),
        li.get(4).getLocationType(),
        li.get(4).getPossibleMoves(),
        li.get(4).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.DOWN);
    li.add(game.gameState());
    checkLoc = "Location: 5, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT] Treasure: [DIAMOND]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(5).getLocationId(),
        li.get(5).getLocationType(),
        li.get(5).getPossibleMoves(),
        li.get(5).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 6, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(6).getLocationId(),
        li.get(6).getLocationType(),
        li.get(6).getPossibleMoves(),
        li.get(6).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 7, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(7).getLocationId(),
        li.get(7).getLocationType(),
        li.get(7).getPossibleMoves(),
        li.get(7).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 8, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(8).getLocationId(),
        li.get(8).getLocationType(),
        li.get(8).getPossibleMoves(),
        li.get(8).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 9, Type: CAVE, Possible Moves: [LEFT] Treasure: [DIAMOND]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(9).getLocationId(),
        li.get(9).getLocationType(),
        li.get(9).getPossibleMoves(),
        li.get(9).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);

    game.movePlayer(Direction.DOWN);
    li.add(game.gameState());
    checkLoc = "Location: 10, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT] Treasure: [DIAMOND]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(10).getLocationId(),
        li.get(10).getLocationType(),
        li.get(10).getPossibleMoves(),
        li.get(10).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 11, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(11).getLocationId(),
        li.get(11).getLocationType(),
        li.get(11).getPossibleMoves(),
        li.get(11).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 12, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(12).getLocationId(),
        li.get(12).getLocationType(),
        li.get(12).getPossibleMoves(),
        li.get(12).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 13, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(13).getLocationId(),
        li.get(13).getLocationType(),
        li.get(13).getPossibleMoves(),
        li.get(13).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 14, Type: CAVE, Possible Moves: [LEFT] Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(14).getLocationId(),
        li.get(14).getLocationType(),
        li.get(14).getPossibleMoves(),
        li.get(14).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);

    game.movePlayer(Direction.DOWN);
    li.add(game.gameState());
    checkLoc = "Location: 15, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT] Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(15).getLocationId(),
        li.get(15).getLocationType(),
        li.get(15).getPossibleMoves(),
        li.get(15).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 16, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(16).getLocationId(),
        li.get(16).getLocationType(),
        li.get(16).getPossibleMoves(),
        li.get(16).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 17, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(17).getLocationId(),
        li.get(17).getLocationType(),
        li.get(17).getPossibleMoves(),
        li.get(17).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 18, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(18).getLocationId(),
        li.get(18).getLocationType(),
        li.get(18).getPossibleMoves(),
        li.get(18).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 19, Type: CAVE, Possible Moves: [LEFT] Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(19).getLocationId(),
        li.get(19).getLocationType(),
        li.get(19).getPossibleMoves(),
        li.get(19).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);

    game.movePlayer(Direction.DOWN);
    li.add(game.gameState());
    checkLoc = "Location: 20, Type: TUNNEL, Possible Moves: [UP, RIGHT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(20).getLocationId(),
        li.get(20).getLocationType(),
        li.get(20).getPossibleMoves(),
        li.get(20).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 21, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(21).getLocationId(),
        li.get(21).getLocationType(),
        li.get(21).getPossibleMoves(),
        li.get(21).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 22, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(22).getLocationId(),
        li.get(22).getLocationType(),
        li.get(22).getPossibleMoves(),
        li.get(22).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 23, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(23).getLocationId(),
        li.get(23).getLocationType(),
        li.get(23).getPossibleMoves(),
        li.get(23).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 24, Type: CAVE, Possible Moves: [LEFT] Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(24).getLocationId(),
        li.get(24).getLocationType(),
        li.get(24).getPossibleMoves(),
        li.get(24).getTreasure());
    assertEquals(checkLoc, actLoc);
  }

  /**
   * Testing legal moves in a Non-Wrapping Dungeon.
   */
  @Test
  public void testMovePlayerLegalMoveInNonWrapping() {

    game = new FantasyRolePlayingGame(5, 5, false, 0, 50, 1, 2, 1, new FixedRandom());

    List<ReadOnlyGame> li = new ArrayList<>();
    String checkLoc = "";
    String actLoc = "";

    li.add(game.gameState());
    checkLoc = "Location: 4, Type: CAVE, Possible Moves: [LEFT] Treasure: [DIAMOND], "
        + "Arrow: [], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: %s",
        li.get(0).getLocationId(),
        li.get(0).getLocationType(),
        li.get(0).getPossibleMoves(),
        li.get(0).getTreasure(),
        li.get(0).getArrows(),
        li.get(0).getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    li.add(game.gameState());
    checkLoc = "Location: 3, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: [], Arrow: [CROOKEDARROW], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: %s",
        li.get(1).getLocationId(),
        li.get(1).getLocationType(),
        li.get(1).getPossibleMoves(),
        li.get(1).getTreasure(),
        li.get(1).getArrows(),
        li.get(1).getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    li.add(game.gameState());
    checkLoc = "Location: 2, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: [], Arrow: [CROOKEDARROW], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: %s",
        li.get(2).getLocationId(),
        li.get(2).getLocationType(),
        li.get(2).getPossibleMoves(),
        li.get(2).getTreasure(),
        li.get(2).getArrows(),
        li.get(2).getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    li.add(game.gameState());
    checkLoc = "Location: 1, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: [], Arrow: [CROOKEDARROW], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: %s",
        li.get(3).getLocationId(),
        li.get(3).getLocationType(),
        li.get(3).getPossibleMoves(),
        li.get(3).getTreasure(),
        li.get(3).getArrows(),
        li.get(3).getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    li.add(game.gameState());
    checkLoc = "Location: 0, Type: TUNNEL, Possible Moves: [DOWN, RIGHT] "
        + "Treasure: [], Arrow: [CROOKEDARROW], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: %s",
        li.get(4).getLocationId(),
        li.get(4).getLocationType(),
        li.get(4).getPossibleMoves(),
        li.get(4).getTreasure(),
        li.get(4).getArrows(),
        li.get(4).getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.DOWN);
    li.add(game.gameState());
    checkLoc = "Location: 5, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT] Treasure: [DIAMOND]"
        + ", Arrow: [], Monster: OTYUGH";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: %s",
        li.get(5).getLocationId(),
        li.get(5).getLocationType(),
        li.get(5).getPossibleMoves(),
        li.get(5).getTreasure(),
        li.get(5).getArrows(),
        li.get(5).getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 6, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: [], Arrow: [], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: %s",
        li.get(6).getLocationId(),
        li.get(6).getLocationType(),
        li.get(6).getPossibleMoves(),
        li.get(6).getTreasure(),
        li.get(6).getArrows(),
        li.get(6).getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    li.add(game.gameState());
    checkLoc = "Location: 5, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT] Treasure: [DIAMOND]"
        + ", Arrow: [], Monster: OTYUGH";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: %s",
        li.get(7).getLocationId(),
        li.get(7).getLocationType(),
        li.get(7).getPossibleMoves(),
        li.get(7).getTreasure(),
        li.get(7).getArrows(),
        li.get(7).getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.UP);
    li.add(game.gameState());
    checkLoc = "Location: 0, Type: TUNNEL, Possible Moves: [DOWN, RIGHT] "
        + "Treasure: [], Arrow: [CROOKEDARROW], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: %s",
        li.get(8).getLocationId(),
        li.get(8).getLocationType(),
        li.get(8).getPossibleMoves(),
        li.get(8).getTreasure(),
        li.get(8).getArrows(),
        li.get(8).getMonster());
    assertEquals(checkLoc, actLoc);

  }

  /**
   * Testing Illegal moves throws exception in a Non-Wrapping Dungeon.
   */
  @Test(expected = IllegalStateException.class)
  public void testMovePlayerIllegalMoveInNonWrapping() {

    game = new FantasyRolePlayingGame(5, 5, false, 0, 50, 1, 2, 1, new FixedRandom());

    List<ReadOnlyGame> li = new ArrayList<>();
    String checkLoc = "";
    String actLoc = "";

    li.add(game.gameState());
    checkLoc = "Location: 4, Type: CAVE, Possible Moves: [LEFT] Treasure: [DIAMOND], "
        + "Arrow: [], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: %s",
        li.get(0).getLocationId(),
        li.get(0).getLocationType(),
        li.get(0).getPossibleMoves(),
        li.get(0).getTreasure(),
        li.get(0).getArrows(),
        li.get(0).getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
  }

  /**
   * Testing legal moves in a Wrapping Dungeon.
   */
  @Test
  public void testMovePlayerLegalMoveInWrapping() {
    game = new FantasyRolePlayingGame(5, 5, true, 0, 50, 1, 2, 1, new FixedRandom());

    List<ReadOnlyGame> li = new ArrayList<>();

    String checkLoc = "";
    String actLoc = "";

    /*
    Traversing the player through tunnels and caves in a wrapping dungeon and asserting on the
    player's current node to verify that the player is being moved accurately.
     */
    li.add(game.gameState());
    checkLoc = "Location: 0, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT, LEFT] "
        + "Treasure: [DIAMOND], Arrow: [CROOKEDARROW], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: %s",
        li.get(0).getLocationId(),
        li.get(0).getLocationType(),
        li.get(0).getPossibleMoves(),
        li.get(0).getTreasure(),
        li.get(0).getArrows(),
        li.get(0).getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 1, Type: CAVE, Possible Moves: [UP, RIGHT, LEFT] Treasure: [DIAMOND], "
        + "Arrow: [CROOKEDARROW], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: %s",
        li.get(1).getLocationId(),
        li.get(1).getLocationType(),
        li.get(1).getPossibleMoves(),
        li.get(1).getTreasure(),
        li.get(1).getArrows(),
        li.get(1).getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.UP);
    li.add(game.gameState());
    checkLoc = "Location: 21, Type: CAVE, Possible Moves: [DOWN] Treasure: [], "
        + "Arrow: [], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: %s",
        li.get(2).getLocationId(),
        li.get(2).getLocationType(),
        li.get(2).getPossibleMoves(),
        li.get(2).getTreasure(),
        li.get(2).getArrows(),
        li.get(2).getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.DOWN);
    li.add(game.gameState());
    checkLoc = "Location: 1, Type: CAVE, Possible Moves: [UP, RIGHT, LEFT] Treasure: [DIAMOND], "
        + "Arrow: [CROOKEDARROW], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: %s",
        li.get(3).getLocationId(),
        li.get(3).getLocationType(),
        li.get(3).getPossibleMoves(),
        li.get(3).getTreasure(),
        li.get(3).getArrows(),
        li.get(3).getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    li.add(game.gameState());
    checkLoc = "Location: 0, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT, LEFT]"
        + " Treasure: [DIAMOND], Arrow: [CROOKEDARROW], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: %s",
        li.get(4).getLocationId(),
        li.get(4).getLocationType(),
        li.get(4).getPossibleMoves(),
        li.get(4).getTreasure(),
        li.get(4).getArrows(),
        li.get(4).getMonster());
    assertEquals(checkLoc, actLoc);
  }

  /**
   * Testing illegal moves in a Wrapping Dungeon.
   */
  @Test(expected = IllegalStateException.class)
  public void testMovePlayerIllegalMoveInWrapping() {
    game = new FantasyRolePlayingGame(5, 5, true, 0, 50, 1, 2, 1, new FixedRandom());

    List<ReadOnlyGame> li = new ArrayList<>();

    String checkLoc = "";
    String actLoc = "";

    /*
    Traversing the player through tunnels and caves in a wrapping dungeon and asserting on the
    player's current node to verify that the player is being moved accurately.
     */
    li.add(game.gameState());
    checkLoc = "Location: 0, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT, LEFT] "
        + "Treasure: [DIAMOND], Arrow: [CROOKEDARROW], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: %s",
        li.get(0).getLocationId(),
        li.get(0).getLocationType(),
        li.get(0).getPossibleMoves(),
        li.get(0).getTreasure(),
        li.get(0).getArrows(),
        li.get(0).getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.UP);
  }

  /**
   * Testing if the arrows are added with the same frequency as treasure as well as testing they
   * can be added in both caves and tunnels.
   */
  @Test
  public void testArrowAllocationInBothTunnelsCavesAndSameFrequencyAsTreasure() {
    game = new FantasyRolePlayingGame(5, 5, false, 7, 50, 1, 2, 1, new FixedRandom());

    String s = "";

    s = "  <T 0> —— <C 1> —— <C 2> —— <T 3> —— <C 4>  \n"
        + "    |        |        |                      \n"
        + "  <C 5> —— <C 6> —— <C 7> —— <T 8> —— <C 9>  \n"
        + "    |        |        |                      \n"
        + "  <C10> —— <C11> —— <C12> —— <T13> —— <C14>  \n"
        + "    |        |        |                      \n"
        + "  <C15> —— <C16> —— <C17> —— <T18> —— <C19>  \n"
        + "    |        |                               \n"
        + "  <T20> —— <C21> —— <T22> —— <T23> —— <C24>  \n"
        + "                                             ";

    s += "\n";

    //Asserting on string

    assertEquals(s, game.printDungeonAs2D());

    /*Asserting on attributes (row column numbers, treasures/arrows/monster allocated) of each node
    by traversing the player through all the locations.
     */

    List<ReadOnlyGame> li = new ArrayList<>();
    String checkLoc = "";
    String actLoc = "";

    li.add(game.gameState());
    checkLoc = "Location: 1, Type: CAVE, Possible Moves: [DOWN, RIGHT, LEFT] Treasure: [DIAMOND], "
        + "Arrow: [CROOKEDARROW]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s",
        li.get(0).getLocationId(),
        li.get(0).getLocationType(),
        li.get(0).getPossibleMoves(),
        li.get(0).getTreasure(),
        li.get(0).getArrows());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 2, Type: CAVE, Possible Moves: [DOWN, RIGHT, LEFT] Treasure: [DIAMOND], "
        + "Arrow: [CROOKEDARROW]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s",
        li.get(1).getLocationId(),
        li.get(1).getLocationType(),
        li.get(1).getPossibleMoves(),
        li.get(1).getTreasure(),
        li.get(1).getArrows());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 3, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: [], Arrow: [CROOKEDARROW]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s",
        li.get(2).getLocationId(),
        li.get(2).getLocationType(),
        li.get(2).getPossibleMoves(),
        li.get(2).getTreasure(),
        li.get(2).getArrows());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 4, Type: CAVE, Possible Moves: [LEFT] Treasure: [DIAMOND], "
        + "Arrow: [CROOKEDARROW]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s",
        li.get(3).getLocationId(),
        li.get(3).getLocationType(),
        li.get(3).getPossibleMoves(),
        li.get(3).getTreasure(),
        li.get(3).getArrows());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);

    game.movePlayer(Direction.LEFT);
    li.add(game.gameState());
    checkLoc = "Location: 0, Type: TUNNEL, Possible Moves: [DOWN, RIGHT] "
        + "Treasure: [], Arrow: [CROOKEDARROW]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s",
        li.get(4).getLocationId(),
        li.get(4).getLocationType(),
        li.get(4).getPossibleMoves(),
        li.get(4).getTreasure(),
        li.get(4).getArrows());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.DOWN);
    li.add(game.gameState());
    checkLoc = "Location: 5, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT] Treasure: [DIAMOND], "
        + "Arrow: [CROOKEDARROW]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s",
        li.get(5).getLocationId(),
        li.get(5).getLocationType(),
        li.get(5).getPossibleMoves(),
        li.get(5).getTreasure(),
        li.get(5).getArrows());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 6, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT, LEFT] "
        + "Treasure: [DIAMOND], Arrow: [CROOKEDARROW]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s",
        li.get(6).getLocationId(),
        li.get(6).getLocationType(),
        li.get(6).getPossibleMoves(),
        li.get(6).getTreasure(),
        li.get(6).getArrows());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 7, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT, LEFT] "
        + "Treasure: [DIAMOND], Arrow: [CROOKEDARROW]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s",
        li.get(7).getLocationId(),
        li.get(7).getLocationType(),
        li.get(7).getPossibleMoves(),
        li.get(7).getTreasure(),
        li.get(7).getArrows());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 8, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: [], Arrow: [CROOKEDARROW]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s",
        li.get(8).getLocationId(),
        li.get(8).getLocationType(),
        li.get(8).getPossibleMoves(),
        li.get(8).getTreasure(),
        li.get(8).getArrows());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 9, Type: CAVE, Possible Moves: [LEFT] Treasure: [DIAMOND], "
        + "Arrow: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s",
        li.get(9).getLocationId(),
        li.get(9).getLocationType(),
        li.get(9).getPossibleMoves(),
        li.get(9).getTreasure(),
        li.get(9).getArrows());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);

    game.movePlayer(Direction.DOWN);
    li.add(game.gameState());
    checkLoc = "Location: 10, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT] Treasure: [DIAMOND], "
        + "Arrow: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s",
        li.get(10).getLocationId(),
        li.get(10).getLocationType(),
        li.get(10).getPossibleMoves(),
        li.get(10).getTreasure(),
        li.get(10).getArrows());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 11, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT, LEFT] "
        + "Treasure: [DIAMOND], Arrow: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s",
        li.get(11).getLocationId(),
        li.get(11).getLocationType(),
        li.get(11).getPossibleMoves(),
        li.get(11).getTreasure(),
        li.get(11).getArrows());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 12, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT, LEFT] Treasure: [], "
        + "Arrow: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s",
        li.get(12).getLocationId(),
        li.get(12).getLocationType(),
        li.get(12).getPossibleMoves(),
        li.get(12).getTreasure(),
        li.get(12).getArrows());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 13, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: [], Arrow: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s",
        li.get(13).getLocationId(),
        li.get(13).getLocationType(),
        li.get(13).getPossibleMoves(),
        li.get(13).getTreasure(),
        li.get(13).getArrows());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 14, Type: CAVE, Possible Moves: [LEFT] Treasure: [], Arrow: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s",
        li.get(14).getLocationId(),
        li.get(14).getLocationType(),
        li.get(14).getPossibleMoves(),
        li.get(14).getTreasure(),
        li.get(14).getArrows());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);

    game.movePlayer(Direction.DOWN);
    li.add(game.gameState());
    checkLoc = "Location: 15, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT] Treasure: [], "
        + "Arrow: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s",
        li.get(15).getLocationId(),
        li.get(15).getLocationType(),
        li.get(15).getPossibleMoves(),
        li.get(15).getTreasure(),
        li.get(15).getArrows());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 16, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT, LEFT] Treasure: [], "
        + "Arrow: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s",
        li.get(16).getLocationId(),
        li.get(16).getLocationType(),
        li.get(16).getPossibleMoves(),
        li.get(16).getTreasure(),
        li.get(16).getArrows());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 17, Type: CAVE, Possible Moves: [UP, RIGHT, LEFT] Treasure: [], "
        + "Arrow: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s",
        li.get(17).getLocationId(),
        li.get(17).getLocationType(),
        li.get(17).getPossibleMoves(),
        li.get(17).getTreasure(),
        li.get(17).getArrows());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 18, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: [], Arrow: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s",
        li.get(18).getLocationId(),
        li.get(18).getLocationType(),
        li.get(18).getPossibleMoves(),
        li.get(18).getTreasure(),
        li.get(18).getArrows());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 19, Type: CAVE, Possible Moves: [LEFT] Treasure: [], Arrow: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s",
        li.get(19).getLocationId(),
        li.get(19).getLocationType(),
        li.get(19).getPossibleMoves(),
        li.get(19).getTreasure(),
        li.get(19).getArrows());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);

    game.movePlayer(Direction.DOWN);
    li.add(game.gameState());
    checkLoc = "Location: 20, Type: TUNNEL, Possible Moves: [UP, RIGHT] "
        + "Treasure: [], Arrow: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s",
        li.get(20).getLocationId(),
        li.get(20).getLocationType(),
        li.get(20).getPossibleMoves(),
        li.get(20).getTreasure(),
        li.get(20).getArrows());
    assertEquals(checkLoc, actLoc);


    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 21, Type: CAVE, Possible Moves: [UP, RIGHT, LEFT] Treasure: [], "
        + "Arrow: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s",
        li.get(21).getLocationId(),
        li.get(21).getLocationType(),
        li.get(21).getPossibleMoves(),
        li.get(21).getTreasure(),
        li.get(21).getArrows());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 22, Type: TUNNEL, Possible Moves: [RIGHT, LEFT]"
        + " Treasure: [], Arrow: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s",
        li.get(22).getLocationId(),
        li.get(22).getLocationType(),
        li.get(22).getPossibleMoves(),
        li.get(22).getTreasure(),
        li.get(22).getArrows());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 23, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: [], Arrow: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s",
        li.get(23).getLocationId(),
        li.get(23).getLocationType(),
        li.get(23).getPossibleMoves(),
        li.get(23).getTreasure(),
        li.get(23).getArrows());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 24, Type: CAVE, Possible Moves: [LEFT] Treasure: [], Arrow: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s",
        li.get(24).getLocationId(),
        li.get(24).getLocationType(),
        li.get(24).getPossibleMoves(),
        li.get(24).getTreasure(),
        li.get(24).getArrows());
    assertEquals(checkLoc, actLoc);

    int arrowCount = 0;

    for (ReadOnlyGame lr : li) {
      if ((lr.getArrows().size() != 0)) {
        arrowCount += 1;
      }
    }

    //Asserting on number of caves that hold the treasure (50% of 17 is 9 when rounded to ceiling).
    //Arrows are also found with the same frequency, which would be equal to 9.
    assertEquals(9, arrowCount);
  }


  /**
   * Testing that the there is always one monster in the end cave and no cave at the start.
   */
  @Test
  public void testMonsterAlwaysInEndCave() {

    game = new FantasyRolePlayingGame(5, 5, false, 0, 50, 1, 2, 1, new FixedRandom());

    String s = "";

    s = "  <T 0> —— <T 1> —— <T 2> —— <T 3> —— <C 4>  \n"
        + "    |                                        \n"
        + "  <C 5> —— <T 6> —— <T 7> —— <T 8> —— <C 9>  \n"
        + "    |                                        \n"
        + "  <C10> —— <T11> —— <T12> —— <T13> —— <C14>  \n"
        + "    |                                        \n"
        + "  <C15> —— <T16> —— <T17> —— <T18> —— <C19>  \n"
        + "    |                                        \n"
        + "  <T20> —— <T21> —— <T22> —— <T23> —— <C24>  \n"
        + "                                             ";

    s += "\n";

    //Asserting on string

    assertEquals(s, game.printDungeonAs2D());

    /*
    By fixing the random and traversing the player, we know that the starting cave is 4 and ending
    cave is 5. Since number of monsters is 1, it is located at the end.
     */
    List<ReadOnlyGame> li = new ArrayList<>();
    String checkLoc = "";
    String actLoc = "";

    li.add(game.gameState());
    checkLoc = "Location: 4, Type: CAVE, Possible Moves: [LEFT] Treasure: [DIAMOND], "
        + "Arrow: [], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: %s",
        li.get(0).getLocationId(),
        li.get(0).getLocationType(),
        li.get(0).getPossibleMoves(),
        li.get(0).getTreasure(),
        li.get(0).getArrows(),
        li.get(0).getMonster());
    assertEquals(checkLoc, actLoc);

    assertEquals(false, game.isReachedEnd());

    game.movePlayer(Direction.LEFT);
    li.add(game.gameState());
    checkLoc = "Location: 3, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: [], Arrow: [CROOKEDARROW], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: %s",
        li.get(1).getLocationId(),
        li.get(1).getLocationType(),
        li.get(1).getPossibleMoves(),
        li.get(1).getTreasure(),
        li.get(1).getArrows(),
        li.get(1).getMonster());
    assertEquals(checkLoc, actLoc);

    assertEquals(false, game.isReachedEnd());

    game.movePlayer(Direction.LEFT);
    li.add(game.gameState());
    checkLoc = "Location: 2, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: [], Arrow: [CROOKEDARROW], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: %s",
        li.get(2).getLocationId(),
        li.get(2).getLocationType(),
        li.get(2).getPossibleMoves(),
        li.get(2).getTreasure(),
        li.get(2).getArrows(),
        li.get(2).getMonster());
    assertEquals(checkLoc, actLoc);

    assertEquals(false, game.isReachedEnd());

    game.movePlayer(Direction.LEFT);
    li.add(game.gameState());
    checkLoc = "Location: 1, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: [], Arrow: [CROOKEDARROW], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: %s",
        li.get(3).getLocationId(),
        li.get(3).getLocationType(),
        li.get(3).getPossibleMoves(),
        li.get(3).getTreasure(),
        li.get(3).getArrows(),
        li.get(3).getMonster());
    assertEquals(checkLoc, actLoc);

    assertEquals(false, game.isReachedEnd());

    game.movePlayer(Direction.LEFT);
    li.add(game.gameState());
    checkLoc = "Location: 0, Type: TUNNEL, Possible Moves: [DOWN, RIGHT] "
        + "Treasure: [], Arrow: [CROOKEDARROW], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: %s",
        li.get(4).getLocationId(),
        li.get(4).getLocationType(),
        li.get(4).getPossibleMoves(),
        li.get(4).getTreasure(),
        li.get(4).getArrows(),
        li.get(4).getMonster());
    assertEquals(checkLoc, actLoc);

    assertEquals(false, game.isReachedEnd());


    game.movePlayer(Direction.DOWN);
    li.add(game.gameState());
    checkLoc = "Location: 5, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT] Treasure: [DIAMOND],"
        + " Arrow: [], Monster: OTYUGH";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: %s",
        li.get(5).getLocationId(),
        li.get(5).getLocationType(),
        li.get(5).getPossibleMoves(),
        li.get(5).getTreasure(),
        li.get(5).getArrows(),
        li.get(5).getMonster());
    assertEquals(checkLoc, actLoc);

    assertEquals(true, game.isReachedEnd());
  }


  /**
   * Testing that the there is no monster in the start cave.
   */
  @Test
  public void testMonsterNotAtStartCave() {

    game = new FantasyRolePlayingGame(5, 5, false, 0, 50, 1, 2, 1, new FixedRandom());

    String s = "";

    s = "  <T 0> —— <T 1> —— <T 2> —— <T 3> —— <C 4>  \n"
        + "    |                                        \n"
        + "  <C 5> —— <T 6> —— <T 7> —— <T 8> —— <C 9>  \n"
        + "    |                                        \n"
        + "  <C10> —— <T11> —— <T12> —— <T13> —— <C14>  \n"
        + "    |                                        \n"
        + "  <C15> —— <T16> —— <T17> —— <T18> —— <C19>  \n"
        + "    |                                        \n"
        + "  <T20> —— <T21> —— <T22> —— <T23> —— <C24>  \n"
        + "                                             ";

    s += "\n";

    //Asserting on string

    assertEquals(s, game.printDungeonAs2D());

    /*
    By fixing the random and traversing the player, we know that the starting cave is 4 and ending
    cave is 5. Since number of monsters is set at 1, it should only be located at the end cave.
     */
    List<ReadOnlyGame> li = new ArrayList<>();
    String checkLoc = "";
    String actLoc = "";

    li.add(game.gameState());
    checkLoc = "Location: 4, Type: CAVE, Possible Moves: [LEFT] Treasure: [DIAMOND], "
        + "Arrow: [], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: %s",
        li.get(0).getLocationId(),
        li.get(0).getLocationType(),
        li.get(0).getPossibleMoves(),
        li.get(0).getTreasure(),
        li.get(0).getArrows(),
        li.get(0).getMonster());
    assertEquals(checkLoc, actLoc);

    assertEquals(false, game.isReachedEnd());
  }

  /**
   * Testing number of monsters and testing that monsters are not in tunnels. Also testing that
   * other items like treasure or arrows can exist along with the monster.
   */
  @Test
  public void testMonsterNumberAndNotInTunnelsAndOtherItemsAlongWithMonster() {
    game = new FantasyRolePlayingGame(5, 5, false, 7, 50, 5, 2, 1, new FixedRandom());
    String s = "";

    s = "  <T 0> —— <C 1> —— <C 2> —— <T 3> —— <C 4>  \n"
        + "    |        |        |                      \n"
        + "  <C 5> —— <C 6> —— <C 7> —— <T 8> —— <C 9>  \n"
        + "    |        |        |                      \n"
        + "  <C10> —— <C11> —— <C12> —— <T13> —— <C14>  \n"
        + "    |        |        |                      \n"
        + "  <C15> —— <C16> —— <C17> —— <T18> —— <C19>  \n"
        + "    |        |                               \n"
        + "  <T20> —— <C21> —— <T22> —— <T23> —— <C24>  \n"
        + "                                             ";

    s += "\n";

    //Asserting on string

    assertEquals(s, game.printDungeonAs2D());

    /*Asserting on attributes (row column numbers, treasures/arrows/monster allocated) of each node
    by traversing the player through all the locations.
     */

    List<ReadOnlyGame> li = new ArrayList<>();
    String checkLoc = "";
    String actLoc = "";

    li.add(game.gameState());
    checkLoc = "Location: 1, Type: CAVE, Possible Moves: [DOWN, RIGHT, LEFT] Treasure: [DIAMOND], "
        + "Arrow: [CROOKEDARROW], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: null",
        li.get(0).getLocationId(),
        li.get(0).getLocationType(),
        li.get(0).getPossibleMoves(),
        li.get(0).getTreasure(),
        li.get(0).getArrows(),
        li.get(0).getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 2, Type: CAVE, Possible Moves: [DOWN, RIGHT, LEFT] Treasure: [DIAMOND], "
        + "Arrow: [CROOKEDARROW], Monster: [OTYUGH]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: [OTYUGH]",
        li.get(1).getLocationId(),
        li.get(1).getLocationType(),
        li.get(1).getPossibleMoves(),
        li.get(1).getTreasure(),
        li.get(1).getArrows(),
        li.get(1).getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 3, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: [], Arrow: [CROOKEDARROW], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: null",
        li.get(2).getLocationId(),
        li.get(2).getLocationType(),
        li.get(2).getPossibleMoves(),
        li.get(2).getTreasure(),
        li.get(2).getArrows(),
        li.get(2).getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 4, Type: CAVE, Possible Moves: [LEFT] Treasure: [DIAMOND], "
        + "Arrow: [CROOKEDARROW], Monster: [OTYUGH]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: [OTYUGH]",
        li.get(3).getLocationId(),
        li.get(3).getLocationType(),
        li.get(3).getPossibleMoves(),
        li.get(3).getTreasure(),
        li.get(3).getArrows(),
        li.get(3).getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);

    game.movePlayer(Direction.LEFT);
    li.add(game.gameState());
    checkLoc = "Location: 0, Type: TUNNEL, Possible Moves: [DOWN, RIGHT] "
        + "Treasure: [], Arrow: [CROOKEDARROW], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: null",
        li.get(4).getLocationId(),
        li.get(4).getLocationType(),
        li.get(4).getPossibleMoves(),
        li.get(4).getTreasure(),
        li.get(4).getArrows(),
        li.get(4).getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.DOWN);
    li.add(game.gameState());
    checkLoc = "Location: 5, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT] Treasure: [DIAMOND], "
        + "Arrow: [CROOKEDARROW], Monster: [OTYUGH]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: [OTYUGH]",
        li.get(5).getLocationId(),
        li.get(5).getLocationType(),
        li.get(5).getPossibleMoves(),
        li.get(5).getTreasure(),
        li.get(5).getArrows(),
        li.get(5).getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 6, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT, LEFT] "
        + "Treasure: [DIAMOND], Arrow: [CROOKEDARROW], Monster: [OTYUGH]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: [OTYUGH]",
        li.get(6).getLocationId(),
        li.get(6).getLocationType(),
        li.get(6).getPossibleMoves(),
        li.get(6).getTreasure(),
        li.get(6).getArrows(),
        li.get(6).getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 7, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT, LEFT] "
        + "Treasure: [DIAMOND], Arrow: [CROOKEDARROW], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: null",
        li.get(7).getLocationId(),
        li.get(7).getLocationType(),
        li.get(7).getPossibleMoves(),
        li.get(7).getTreasure(),
        li.get(7).getArrows(),
        li.get(7).getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 8, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: [], Arrow: [CROOKEDARROW], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: null",
        li.get(8).getLocationId(),
        li.get(8).getLocationType(),
        li.get(8).getPossibleMoves(),
        li.get(8).getTreasure(),
        li.get(8).getArrows(),
        li.get(8).getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 9, Type: CAVE, Possible Moves: [LEFT] Treasure: [DIAMOND], "
        + "Arrow: [], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: null",
        li.get(9).getLocationId(),
        li.get(9).getLocationType(),
        li.get(9).getPossibleMoves(),
        li.get(9).getTreasure(),
        li.get(9).getArrows(),
        li.get(9).getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);

    game.movePlayer(Direction.DOWN);
    li.add(game.gameState());
    checkLoc = "Location: 10, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT] Treasure: [DIAMOND], "
        + "Arrow: [], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: null",
        li.get(10).getLocationId(),
        li.get(10).getLocationType(),
        li.get(10).getPossibleMoves(),
        li.get(10).getTreasure(),
        li.get(10).getArrows(),
        li.get(10).getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 11, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT, LEFT] "
        + "Treasure: [DIAMOND], Arrow: [], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: null",
        li.get(11).getLocationId(),
        li.get(11).getLocationType(),
        li.get(11).getPossibleMoves(),
        li.get(11).getTreasure(),
        li.get(11).getArrows(),
        li.get(11).getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 12, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT, LEFT] Treasure: [], "
        + "Arrow: [], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: null",
        li.get(12).getLocationId(),
        li.get(12).getLocationType(),
        li.get(12).getPossibleMoves(),
        li.get(12).getTreasure(),
        li.get(12).getArrows(),
        li.get(12).getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 13, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: [], Arrow: [], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: null",
        li.get(13).getLocationId(),
        li.get(13).getLocationType(),
        li.get(13).getPossibleMoves(),
        li.get(13).getTreasure(),
        li.get(13).getArrows(),
        li.get(13).getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 14, Type: CAVE, Possible Moves: [LEFT] Treasure: [], Arrow: [],"
        + " Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: null",
        li.get(14).getLocationId(),
        li.get(14).getLocationType(),
        li.get(14).getPossibleMoves(),
        li.get(14).getTreasure(),
        li.get(14).getArrows(),
        li.get(14).getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);

    game.movePlayer(Direction.DOWN);
    li.add(game.gameState());
    checkLoc = "Location: 15, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT] Treasure: [],"
        + " Arrow: [], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: null",
        li.get(15).getLocationId(),
        li.get(15).getLocationType(),
        li.get(15).getPossibleMoves(),
        li.get(15).getTreasure(),
        li.get(15).getArrows(),
        li.get(15).getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 16, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT, LEFT] Treasure: [], "
        + "Arrow: [], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: null",
        li.get(16).getLocationId(),
        li.get(16).getLocationType(),
        li.get(16).getPossibleMoves(),
        li.get(16).getTreasure(),
        li.get(16).getArrows(),
        li.get(16).getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 17, Type: CAVE, Possible Moves: [UP, RIGHT, LEFT] Treasure: [], "
        + "Arrow: [], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: null",
        li.get(17).getLocationId(),
        li.get(17).getLocationType(),
        li.get(17).getPossibleMoves(),
        li.get(17).getTreasure(),
        li.get(17).getArrows(),
        li.get(17).getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 18, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: [], Arrow: [], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: null",
        li.get(18).getLocationId(),
        li.get(18).getLocationType(),
        li.get(18).getPossibleMoves(),
        li.get(18).getTreasure(),
        li.get(18).getArrows(),
        li.get(18).getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 19, Type: CAVE, Possible Moves: [LEFT] Treasure: [], Arrow: [], "
        + "Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: null",
        li.get(19).getLocationId(),
        li.get(19).getLocationType(),
        li.get(19).getPossibleMoves(),
        li.get(19).getTreasure(),
        li.get(19).getArrows(),
        li.get(19).getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);

    game.movePlayer(Direction.DOWN);
    li.add(game.gameState());
    checkLoc = "Location: 20, Type: TUNNEL, Possible Moves: [UP, RIGHT] "
        + "Treasure: [], Arrow: [], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: null",
        li.get(20).getLocationId(),
        li.get(20).getLocationType(),
        li.get(20).getPossibleMoves(),
        li.get(20).getTreasure(),
        li.get(20).getArrows(),
        li.get(20).getMonster());
    assertEquals(checkLoc, actLoc);


    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 21, Type: CAVE, Possible Moves: [UP, RIGHT, LEFT] Treasure: [], "
        + "Arrow: [], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: null",
        li.get(21).getLocationId(),
        li.get(21).getLocationType(),
        li.get(21).getPossibleMoves(),
        li.get(21).getTreasure(),
        li.get(21).getArrows(),
        li.get(21).getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 22, Type: TUNNEL, Possible Moves: [RIGHT, LEFT]"
        + " Treasure: [], Arrow: [], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: null",
        li.get(22).getLocationId(),
        li.get(22).getLocationType(),
        li.get(22).getPossibleMoves(),
        li.get(22).getTreasure(),
        li.get(22).getArrows(),
        li.get(22).getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 23, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: [], Arrow: [], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: null",
        li.get(23).getLocationId(),
        li.get(23).getLocationType(),
        li.get(23).getPossibleMoves(),
        li.get(23).getTreasure(),
        li.get(23).getArrows(),
        li.get(23).getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 24, Type: CAVE, Possible Moves: [LEFT] Treasure: [], Arrow: [], "
        + "Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: null",
        li.get(24).getLocationId(),
        li.get(24).getLocationType(),
        li.get(24).getPossibleMoves(),
        li.get(24).getTreasure(),
        li.get(24).getArrows(),
        li.get(24).getMonster());
    assertEquals(checkLoc, actLoc);

    int monsterCount = 0;

    for (ReadOnlyGame lr : li) {
      if (lr.getMonster() != null && (lr.getMonster().equals("OTYUGH"))) {
        monsterCount += 1;
      }
    }

    //Asserting on number of monsters in the cave
    assertEquals(5, monsterCount);
  }


  /**
   * Testing that the player initially starts with 3 arrows.
   */
  @Test
  public void testPlayerInitialArrows() {
    game = new FantasyRolePlayingGame(5, 5, false, 7, 50, 5, 2, 1, new FixedRandom());
    assertEquals(3, game.getArrowsInPlayerBag().size());
  }

  /**
   * Testing player picking arrows by asserting on the size of the player bag.
   */
  @Test
  public void testPlayerPickUpArrowsExisting() {
    game = new FantasyRolePlayingGame(5, 5, false, 7, 50, 5, 2, 1, new FixedRandom());
    assertEquals(3, game.getArrowsInPlayerBag().size());
    game.movePlayer(Direction.RIGHT);
    game.pickArrow();
    assertEquals(4, game.getArrowsInPlayerBag().size());
    game.movePlayer(Direction.RIGHT);
    game.movePlayer(Direction.RIGHT);
    game.pickArrow();
    assertEquals(5, game.getArrowsInPlayerBag().size());
  }

  /**
   * Testing that the player initially starts with 3 arrows.
   */
  @Test(expected = IllegalStateException.class)
  public void testPlayerPickUpArrowsNonExisting() {
    game = new FantasyRolePlayingGame(5, 5, false, 7, 50, 5, 2, 1, new FixedRandom());
    game.movePlayer(Direction.DOWN);
    game.movePlayer(Direction.DOWN);
    game.pickArrow();
  }

  /**
   * Testing that the player initially starts with 3 arrows.
   */
  @Test
  public void testInitialPlayerArrowBag() {
    game = new FantasyRolePlayingGame(5, 5, false, 7, 50, 5, 2, 1, new FixedRandom());
    assertEquals(3, game.getArrowsInPlayerBag().size());
    game.movePlayer(Direction.RIGHT);
    game.pickArrow();
    assertEquals(4, game.getArrowsInPlayerBag().size());
    game.movePlayer(Direction.RIGHT);
    game.movePlayer(Direction.RIGHT);
    game.pickArrow();
    assertEquals(5, game.getArrowsInPlayerBag().size());
    assertEquals("[CROOKEDARROW, CROOKEDARROW, CROOKEDARROW, "
        + "CROOKEDARROW, CROOKEDARROW]", game.getArrowsInPlayerBag().toString());

  }

  /**
   * Testing that the arrow shot misses the monsters if the distance is not exact.
   */
  @Test
  public void testShootArrowMissedDueToNonExactDistance() {
    game = new FantasyRolePlayingGame(5, 5, false, 7, 50, 7, 2, 1, new FixedRandom());

    String s = "";

    s = "  <T 0> —— <C 1> —— <C 2> —— <T 3> —— <C 4>  \n"
        + "    |        |        |                      \n"
        + "  <C 5> —— <C 6> —— <C 7> —— <T 8> —— <C 9>  \n"
        + "    |        |        |                      \n"
        + "  <C10> —— <C11> —— <C12> —— <T13> —— <C14>  \n"
        + "    |        |        |                      \n"
        + "  <C15> —— <C16> —— <C17> —— <T18> —— <C19>  \n"
        + "    |        |                               \n"
        + "  <T20> —— <C21> —— <T22> —— <T23> —— <C24>  \n"
        + "                                             ";

    s += "\n";

    //Asserting on string

    assertEquals(s, game.printDungeonAs2D());

    /*Asserting on attributes (row column numbers, treasures/arrows/monster allocated) of each node
    by traversing the player through all the locations. Monsters in 2, 4, 5, 6, 7, 9, 14
     */

    List<ReadOnlyGame> li = new ArrayList<>();
    String checkLoc = "";
    String actLoc = "";

    li.add(game.gameState());
    checkLoc = "Location: 1, Type: CAVE, Possible Moves: [DOWN, RIGHT, LEFT] Treasure: [DIAMOND], "
        + "Arrow: [CROOKEDARROW], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: null",
        li.get(0).getLocationId(),
        li.get(0).getLocationType(),
        li.get(0).getPossibleMoves(),
        li.get(0).getTreasure(),
        li.get(0).getArrows(),
        li.get(0).getMonster());
    assertEquals(checkLoc, actLoc);

    game.pickArrow();
    game.shootArrow(4, Direction.RIGHT);
    game.shootArrow(4, Direction.RIGHT);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 2, Type: CAVE, Possible Moves: [DOWN, RIGHT, LEFT] Treasure: [DIAMOND], "
        + "Arrow: [CROOKEDARROW], Monster: [OTYUGH]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: [OTYUGH]",
        li.get(1).getLocationId(),
        li.get(1).getLocationType(),
        li.get(1).getPossibleMoves(),
        li.get(1).getTreasure(),
        li.get(1).getArrows(),
        li.get(1).getMonster());
    assertEquals(checkLoc, actLoc);

    game.pickArrow();
    game.shootArrow(3, Direction.RIGHT);
    game.shootArrow(3, Direction.RIGHT);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 3, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: [], Arrow: [CROOKEDARROW], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: null",
        li.get(2).getLocationId(),
        li.get(2).getLocationType(),
        li.get(2).getPossibleMoves(),
        li.get(2).getTreasure(),
        li.get(2).getArrows(),
        li.get(2).getMonster());
    assertEquals(checkLoc, actLoc);

    game.pickArrow();


    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 4, Type: CAVE, Possible Moves: [LEFT] Treasure: [DIAMOND], "
        + "Arrow: [CROOKEDARROW], Monster: [OTYUGH]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: [OTYUGH]",
        li.get(3).getLocationId(),
        li.get(3).getLocationType(),
        li.get(3).getPossibleMoves(),
        li.get(3).getTreasure(),
        li.get(3).getArrows(),
        li.get(3).getMonster());
    assertEquals(checkLoc, actLoc);
  }

  /**
   * Testing that the arrow shot misses the monsters if the direction is incorrect.
   */
  @Test
  public void testShootArrowMissedDueToIncorrectDirection() {
    game = new FantasyRolePlayingGame(5, 5, false, 7, 50, 7, 2, 1, new FixedRandom());

    String s = "";

    s = "  <T 0> —— <C 1> —— <C 2> —— <T 3> —— <C 4>  \n"
        + "    |        |        |                      \n"
        + "  <C 5> —— <C 6> —— <C 7> —— <T 8> —— <C 9>  \n"
        + "    |        |        |                      \n"
        + "  <C10> —— <C11> —— <C12> —— <T13> —— <C14>  \n"
        + "    |        |        |                      \n"
        + "  <C15> —— <C16> —— <C17> —— <T18> —— <C19>  \n"
        + "    |        |                               \n"
        + "  <T20> —— <C21> —— <T22> —— <T23> —— <C24>  \n"
        + "                                             ";

    s += "\n";

    //Asserting on string

    assertEquals(s, game.printDungeonAs2D());

    /*Asserting on attributes (row column numbers, treasures/arrows/monster allocated) of each node
    by traversing the player through all the locations. Monsters in 2, 4, 5, 6, 7, 9, 14
     */

    List<ReadOnlyGame> li = new ArrayList<>();
    String checkLoc = "";
    String actLoc = "";

    li.add(game.gameState());
    checkLoc = "Location: 1, Type: CAVE, Possible Moves: [DOWN, RIGHT, LEFT] Treasure: [DIAMOND], "
        + "Arrow: [CROOKEDARROW], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: null",
        li.get(0).getLocationId(),
        li.get(0).getLocationType(),
        li.get(0).getPossibleMoves(),
        li.get(0).getTreasure(),
        li.get(0).getArrows(),
        li.get(0).getMonster());
    assertEquals(checkLoc, actLoc);

    game.pickArrow();
    game.shootArrow(1, Direction.UP);
    game.shootArrow(1, Direction.UP);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 2, Type: CAVE, Possible Moves: [DOWN, RIGHT, LEFT] Treasure: [DIAMOND], "
        + "Arrow: [CROOKEDARROW], Monster: [OTYUGH]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: [OTYUGH]",
        li.get(1).getLocationId(),
        li.get(1).getLocationType(),
        li.get(1).getPossibleMoves(),
        li.get(1).getTreasure(),
        li.get(1).getArrows(),
        li.get(1).getMonster());
    assertEquals(checkLoc, actLoc);

    game.pickArrow();
    game.shootArrow(3, Direction.UP);
    game.shootArrow(3, Direction.UP);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 3, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: [], Arrow: [CROOKEDARROW], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: null",
        li.get(2).getLocationId(),
        li.get(2).getLocationType(),
        li.get(2).getPossibleMoves(),
        li.get(2).getTreasure(),
        li.get(2).getArrows(),
        li.get(2).getMonster());
    assertEquals(checkLoc, actLoc);

    game.pickArrow();


    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 4, Type: CAVE, Possible Moves: [LEFT] Treasure: [DIAMOND], "
        + "Arrow: [CROOKEDARROW], Monster: [OTYUGH]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: [OTYUGH]",
        li.get(3).getLocationId(),
        li.get(3).getLocationType(),
        li.get(3).getPossibleMoves(),
        li.get(3).getTreasure(),
        li.get(3).getArrows(),
        li.get(3).getMonster());
    assertEquals(checkLoc, actLoc);
  }


  /**
   * Testing that monster only gets killed when it's hit by 2 arrows.
   */
  @Test
  public void testTwoHitsKillsMonster() {
    game = new FantasyRolePlayingGame(5, 5, false, 7, 50, 7, 2, 1, new FixedRandom());

    String s = "";

    s = "  <T 0> —— <C 1> —— <C 2> —— <T 3> —— <C 4>  \n"
        + "    |        |        |                      \n"
        + "  <C 5> —— <C 6> —— <C 7> —— <T 8> —— <C 9>  \n"
        + "    |        |        |                      \n"
        + "  <C10> —— <C11> —— <C12> —— <T13> —— <C14>  \n"
        + "    |        |        |                      \n"
        + "  <C15> —— <C16> —— <C17> —— <T18> —— <C19>  \n"
        + "    |        |                               \n"
        + "  <T20> —— <C21> —— <T22> —— <T23> —— <C24>  \n"
        + "                                             ";

    s += "\n";

    //Asserting on string

    assertEquals(s, game.printDungeonAs2D());

    /*Asserting on attributes (row column numbers, treasures/arrows/monster allocated) of each node
    by traversing the player through all the locations. Monsters in 2, 4, 5, 6, 7, 9, 14
     */

    List<ReadOnlyGame> li = new ArrayList<>();
    String checkLoc = "";
    String actLoc = "";

    li.add(game.gameState());
    checkLoc = "Location: 1, Type: CAVE, Possible Moves: [DOWN, RIGHT, LEFT] Treasure: [DIAMOND], "
        + "Arrow: [CROOKEDARROW], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: null",
        li.get(0).getLocationId(),
        li.get(0).getLocationType(),
        li.get(0).getPossibleMoves(),
        li.get(0).getTreasure(),
        li.get(0).getArrows(),
        li.get(0).getMonster());
    assertEquals(checkLoc, actLoc);

    game.pickArrow();
    game.shootArrow(1, Direction.RIGHT);
    game.shootArrow(1, Direction.RIGHT);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 2, Type: CAVE, Possible Moves: [DOWN, RIGHT, LEFT] Treasure: [DIAMOND], "
        + "Arrow: [CROOKEDARROW], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: null",
        li.get(1).getLocationId(),
        li.get(1).getLocationType(),
        li.get(1).getPossibleMoves(),
        li.get(1).getTreasure(),
        li.get(1).getArrows(),
        li.get(1).getMonster());
    assertEquals(checkLoc, actLoc);

    game.pickArrow();
    game.shootArrow(1, Direction.RIGHT);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 3, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: [], Arrow: [CROOKEDARROW], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: null",
        li.get(2).getLocationId(),
        li.get(2).getLocationType(),
        li.get(2).getPossibleMoves(),
        li.get(2).getTreasure(),
        li.get(2).getArrows(),
        li.get(2).getMonster());
    assertEquals(checkLoc, actLoc);

    game.pickArrow();

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 4, Type: CAVE, Possible Moves: [LEFT] Treasure: [DIAMOND], "
        + "Arrow: [CROOKEDARROW], Monster: [OTYUGH]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: [OTYUGH]",
        li.get(3).getLocationId(),
        li.get(3).getLocationType(),
        li.get(3).getPossibleMoves(),
        li.get(3).getTreasure(),
        li.get(3).getArrows(),
        li.get(3).getMonster());
    assertEquals(checkLoc, actLoc);

    game.pickArrow();

    game.shootArrow(3, Direction.LEFT);

    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);

    game.movePlayer(Direction.LEFT);
    li.add(game.gameState());
    checkLoc = "Location: 0, Type: TUNNEL, Possible Moves: [DOWN, RIGHT] "
        + "Treasure: [], Arrow: [CROOKEDARROW], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: null",
        li.get(4).getLocationId(),
        li.get(4).getLocationType(),
        li.get(4).getPossibleMoves(),
        li.get(4).getTreasure(),
        li.get(4).getArrows(),
        li.get(4).getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.DOWN);
    li.add(game.gameState());
    checkLoc = "Location: 5, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT] Treasure: [DIAMOND], "
        + "Arrow: [CROOKEDARROW], Monster: [OTYUGH]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: [OTYUGH]",
        li.get(5).getLocationId(),
        li.get(5).getLocationType(),
        li.get(5).getPossibleMoves(),
        li.get(5).getTreasure(),
        li.get(5).getArrows(),
        li.get(5).getMonster());
    assertEquals(checkLoc, actLoc);
  }


  /**
   * Testing both direction and distance works as expected when arrow is shot.
   */
  @Test
  public void testShootArrowTravelsFreelyInTunnels() {
    game = new FantasyRolePlayingGame(5, 5, false, 7, 50, 7, 2, 1, new FixedRandom());

    String s = "";

    s = "  <T 0> —— <C 1> —— <C 2> —— <T 3> —— <C 4>  \n"
        + "    |        |        |                      \n"
        + "  <C 5> —— <C 6> —— <C 7> —— <T 8> —— <C 9>  \n"
        + "    |        |        |                      \n"
        + "  <C10> —— <C11> —— <C12> —— <T13> —— <C14>  \n"
        + "    |        |        |                      \n"
        + "  <C15> —— <C16> —— <C17> —— <T18> —— <C19>  \n"
        + "    |        |                               \n"
        + "  <T20> —— <C21> —— <T22> —— <T23> —— <C24>  \n"
        + "                                             ";

    s += "\n";

    //Asserting on string

    assertEquals(s, game.printDungeonAs2D());

    /*Asserting on attributes (row column numbers, treasures/arrows/monster allocated) of each node
    by traversing the player through all the locations. Monsters in 2, 4, 5, 6, 7, 9, 14
     */

    List<ReadOnlyGame> li = new ArrayList<>();
    String checkLoc = "";
    String actLoc = "";

    li.add(game.gameState());
    checkLoc = "Location: 1, Type: CAVE, Possible Moves: [DOWN, RIGHT, LEFT] Treasure: [DIAMOND], "
        + "Arrow: [CROOKEDARROW], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: null",
        li.get(0).getLocationId(),
        li.get(0).getLocationType(),
        li.get(0).getPossibleMoves(),
        li.get(0).getTreasure(),
        li.get(0).getArrows(),
        li.get(0).getMonster());
    assertEquals(checkLoc, actLoc);

    game.pickArrow();

    game.shootArrow(1, Direction.LEFT);
    game.shootArrow(1, Direction.LEFT);


    game.movePlayer(Direction.LEFT);
    game.pickArrow();

    game.movePlayer(Direction.DOWN);
    li.add(game.gameState());
    checkLoc = "Location: 5, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT] Treasure: [DIAMOND], "
        + "Arrow: [CROOKEDARROW], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: null",
        li.get(1).getLocationId(),
        li.get(1).getLocationType(),
        li.get(1).getPossibleMoves(),
        li.get(1).getTreasure(),
        li.get(1).getArrows(),
        li.get(1).getMonster());
    assertEquals(checkLoc, actLoc);
  }

  /**
   * Testing that the direction of the arrow doesn't change when it's going along the caves.
   */
  @Test
  public void testShootArrowTravelsInSameDirectionInCaves() {
    game = new FantasyRolePlayingGame(5, 5, false, 7, 50, 7, 2, 1, new FixedRandom());

    String s = "";

    s = "  <T 0> —— <C 1> —— <C 2> —— <T 3> —— <C 4>  \n"
        + "    |        |        |                      \n"
        + "  <C 5> —— <C 6> —— <C 7> —— <T 8> —— <C 9>  \n"
        + "    |        |        |                      \n"
        + "  <C10> —— <C11> —— <C12> —— <T13> —— <C14>  \n"
        + "    |        |        |                      \n"
        + "  <C15> —— <C16> —— <C17> —— <T18> —— <C19>  \n"
        + "    |        |                               \n"
        + "  <T20> —— <C21> —— <T22> —— <T23> —— <C24>  \n"
        + "                                             ";

    s += "\n";

    //Asserting on string

    assertEquals(s, game.printDungeonAs2D());

    /*Asserting on attributes (row column numbers, treasures/arrows/monster allocated) of each node
    by traversing the player through all the locations. Monsters in 2, 4, 5, 6, 7, 9, 14
     */

    List<ReadOnlyGame> li = new ArrayList<>();
    String checkLoc = "";
    String actLoc = "";

    li.add(game.gameState());
    checkLoc = "Location: 1, Type: CAVE, Possible Moves: [DOWN, RIGHT, LEFT] Treasure: [DIAMOND], "
        + "Arrow: [CROOKEDARROW], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: null",
        li.get(0).getLocationId(),
        li.get(0).getLocationType(),
        li.get(0).getPossibleMoves(),
        li.get(0).getTreasure(),
        li.get(0).getArrows(),
        li.get(0).getMonster());
    assertEquals(checkLoc, actLoc);

    game.pickArrow();

    game.shootArrow(2, Direction.RIGHT);
    game.shootArrow(2, Direction.RIGHT);


    game.movePlayer(Direction.RIGHT);
    game.movePlayer(Direction.RIGHT);

    game.pickArrow();

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 4, Type: CAVE, Possible Moves: [LEFT] Treasure: [DIAMOND], "
        + "Arrow: [CROOKEDARROW], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: null",
        li.get(1).getLocationId(),
        li.get(1).getLocationType(),
        li.get(1).getPossibleMoves(),
        li.get(1).getTreasure(),
        li.get(1).getArrows(),
        li.get(1).getMonster());
    assertEquals(checkLoc, actLoc);
  }

  /**
   * Testing both direction and distance works as expected when arrow is shot.
   */
  @Test
  public void testShootArrowExactDistanceAndCorrectDirection() {
    game = new FantasyRolePlayingGame(5, 5, false, 7, 50, 7, 2, 1, new FixedRandom());

    String s = "";

    s = "  <T 0> —— <C 1> —— <C 2> —— <T 3> —— <C 4>  \n"
        + "    |        |        |                      \n"
        + "  <C 5> —— <C 6> —— <C 7> —— <T 8> —— <C 9>  \n"
        + "    |        |        |                      \n"
        + "  <C10> —— <C11> —— <C12> —— <T13> —— <C14>  \n"
        + "    |        |        |                      \n"
        + "  <C15> —— <C16> —— <C17> —— <T18> —— <C19>  \n"
        + "    |        |                               \n"
        + "  <T20> —— <C21> —— <T22> —— <T23> —— <C24>  \n"
        + "                                             ";

    s += "\n";

    //Asserting on string

    assertEquals(s, game.printDungeonAs2D());

    /*Asserting on attributes (row column numbers, treasures/arrows/monster allocated) of each node
    by traversing the player through all the locations. Monsters in 2, 4, 5, 6, 7, 9, 14
     */

    List<ReadOnlyGame> li = new ArrayList<>();
    String checkLoc = "";
    String actLoc = "";

    li.add(game.gameState());
    checkLoc = "Location: 1, Type: CAVE, Possible Moves: [DOWN, RIGHT, LEFT] Treasure: [DIAMOND], "
        + "Arrow: [CROOKEDARROW], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: null",
        li.get(0).getLocationId(),
        li.get(0).getLocationType(),
        li.get(0).getPossibleMoves(),
        li.get(0).getTreasure(),
        li.get(0).getArrows(),
        li.get(0).getMonster());
    assertEquals(checkLoc, actLoc);

    game.pickArrow();
    game.shootArrow(1, Direction.RIGHT);
    game.shootArrow(1, Direction.RIGHT);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 2, Type: CAVE, Possible Moves: [DOWN, RIGHT, LEFT] Treasure: [DIAMOND], "
        + "Arrow: [CROOKEDARROW], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: null",
        li.get(1).getLocationId(),
        li.get(1).getLocationType(),
        li.get(1).getPossibleMoves(),
        li.get(1).getTreasure(),
        li.get(1).getArrows(),
        li.get(1).getMonster());
    assertEquals(checkLoc, actLoc);

    game.pickArrow();
    game.shootArrow(1, Direction.RIGHT);
    game.shootArrow(1, Direction.RIGHT);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 3, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: [], Arrow: [CROOKEDARROW], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: null",
        li.get(2).getLocationId(),
        li.get(2).getLocationType(),
        li.get(2).getPossibleMoves(),
        li.get(2).getTreasure(),
        li.get(2).getArrows(),
        li.get(2).getMonster());
    assertEquals(checkLoc, actLoc);

    game.pickArrow();


    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 4, Type: CAVE, Possible Moves: [LEFT] Treasure: [DIAMOND], "
        + "Arrow: [CROOKEDARROW], Monster: [OTYUGH]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: [OTYUGH]",
        li.get(3).getLocationId(),
        li.get(3).getLocationType(),
        li.get(3).getPossibleMoves(),
        li.get(3).getTreasure(),
        li.get(3).getArrows(),
        li.get(3).getMonster());
    assertEquals(checkLoc, actLoc);

    game.pickArrow();

    game.shootArrow(3, Direction.LEFT);
    game.shootArrow(3, Direction.LEFT);


    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);

    game.movePlayer(Direction.LEFT);
    li.add(game.gameState());
    checkLoc = "Location: 0, Type: TUNNEL, Possible Moves: [DOWN, RIGHT] "
        + "Treasure: [], Arrow: [CROOKEDARROW], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: null",
        li.get(4).getLocationId(),
        li.get(4).getLocationType(),
        li.get(4).getPossibleMoves(),
        li.get(4).getTreasure(),
        li.get(4).getArrows(),
        li.get(4).getMonster());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.DOWN);
    li.add(game.gameState());
    checkLoc = "Location: 5, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT] Treasure: [DIAMOND], "
        + "Arrow: [CROOKEDARROW], Monster: null";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Arrow: %s, Monster: null",
        li.get(5).getLocationId(),
        li.get(5).getLocationType(),
        li.get(5).getPossibleMoves(),
        li.get(5).getTreasure(),
        li.get(5).getArrows(),
        li.get(5).getMonster());
    assertEquals(checkLoc, actLoc);
  }

  /**
   * Tests if the game gets over when the player reaches the end cave.
   */
  @Test
  public void testPlayerReachingEndCave() {
    game = new FantasyRolePlayingGame(5, 5, true, 0, 50, 1, 2, 1, new FixedRandom());

    /*
    Since random is fixed, we know the ending location that is assigned to the dungeon. Asserting
    that the game gets over when the player reaches the end location.
     */

    game.movePlayer(Direction.DOWN);
    assertEquals(false, game.isReachedEnd());

    game.movePlayer(Direction.DOWN);
    assertEquals(false, game.isReachedEnd());

    game.movePlayer(Direction.RIGHT);
    assertEquals(false, game.isReachedEnd());

    game.movePlayer(Direction.RIGHT);
    assertEquals(false, game.isReachedEnd());

    game.movePlayer(Direction.RIGHT);
    assertEquals(true, game.isReachedEnd());
  }

  /**
   * Tests if the game gets over when the player enters the cave in which a monster exists.
   */
  @Test
  public void testPlayerKilledByMonsterNotInjured() {
    game = new FantasyRolePlayingGame(5, 5, true, 0, 50, 5, 2, 1, new FixedRandom());

    /*
    Since random is fixed, we know the ending location that is assigned to the dungeon. Asserting
    that the game gets over when the player is killed by the monster.
     */

    assertEquals(false, game.isKilled());

    game.movePlayer(Direction.RIGHT);

    assertEquals(true, game.isKilled());
  }


  /**
   * Tests if the game gets over when the player enters the cave in which an injured monster exists.
   */
  @Test
  public void testPlayerKilledByMonsterInjured() {
    game = new FantasyRolePlayingGame(5, 5, true, 0, 50, 5, 2, 1, new FixedRandom());

    /*
    Since random is fixed, we know the ending location that is assigned to the dungeon. Asserting
    that the game does not get over when the player has 50% chance of surviving when the monster
    is injured.
     */

    assertEquals(false, game.isKilled());
    game.shootArrow(1, Direction.RIGHT);

    game.movePlayer(Direction.RIGHT);

    assertEquals(false, game.isKilled());
  }

  /**
   * Test that there is at least a distance of 5 between execute and the end cave in a non wrapping
   * dungeon.
   */
  @Test
  public void testStartEndDistanceInWrapping() {

    game = new FantasyRolePlayingGame(5, 5, true, 0, 50, 1, 2, 1, new FixedRandom());

    //Traversing the player through nodes and checking if path is greater than or equal to 5.
    int pathCount = 0;
    boolean minFive = false;
    game.movePlayer(Direction.DOWN);

    if (!game.isReachedEnd()) {
      pathCount += 1;
    }

    game.movePlayer(Direction.DOWN);

    if (!game.isReachedEnd()) {
      pathCount += 1;
    }

    game.movePlayer(Direction.RIGHT);

    if (!game.isReachedEnd()) {
      pathCount += 1;
    }

    game.movePlayer(Direction.RIGHT);

    if (!game.isReachedEnd()) {
      pathCount += 1;
    }

    game.movePlayer(Direction.RIGHT);

    if (game.isReachedEnd()) {
      pathCount += 1;
    }

    if (pathCount >= 5) {
      minFive = true;
    }

    assertEquals(true, game.isReachedEnd());

    assertEquals(5, pathCount);

    assertTrue(minFive);

    pathCount = 0;
    minFive = false;

    game = new FantasyRolePlayingGame(6, 6, true, 0, 50, 1, 2, 1, new FixedRandom());

    //Traversing the player through nodes and checking if path is greater than or equal to 5.

    game.movePlayer(Direction.DOWN);

    if (!game.isReachedEnd()) {
      pathCount += 1;
    }

    game.movePlayer(Direction.RIGHT);

    if (!game.isReachedEnd()) {
      pathCount += 1;
    }

    game.movePlayer(Direction.RIGHT);

    if (!game.isReachedEnd()) {
      pathCount += 1;
    }

    game.movePlayer(Direction.RIGHT);

    if (!game.isReachedEnd()) {
      pathCount += 1;
    }

    game.movePlayer(Direction.RIGHT);

    if (game.isReachedEnd()) {
      pathCount += 1;
    }

    if (pathCount >= 5) {
      minFive = true;
    }

    assertEquals(true, game.isReachedEnd());

    assertEquals(5, pathCount);

    assertTrue(minFive);

    pathCount = 0;
    minFive = false;

    game = new FantasyRolePlayingGame(8, 8, true, 0, 50, 1, 2, 1, new FixedRandom());

    //Traversing the player through nodes and checking if path is greater than or equal to 5.

    game.movePlayer(Direction.RIGHT);

    if (!game.isReachedEnd()) {
      pathCount += 1;
    }

    game.movePlayer(Direction.RIGHT);

    if (!game.isReachedEnd()) {
      pathCount += 1;
    }

    game.movePlayer(Direction.RIGHT);

    if (!game.isReachedEnd()) {
      pathCount += 1;
    }

    game.movePlayer(Direction.RIGHT);

    if (!game.isReachedEnd()) {
      pathCount += 1;
    }

    game.movePlayer(Direction.RIGHT);

    if (game.isReachedEnd()) {
      pathCount += 1;
    }

    if (pathCount >= 5) {
      minFive = true;
    }

    assertEquals(true, game.isReachedEnd());

    assertEquals(5, pathCount);

    assertTrue(minFive);
  }

  /**
   * Test that there is at least a distance of 5 between execute and the end cave in a wrapping
   * dungeon.
   */
  @Test
  public void testStartEndDistanceInNonWrapping() {

    game = new FantasyRolePlayingGame(5, 5, false, 0, 50, 1, 2, 1, new FixedRandom());

    //Traversing the player through nodes and checking if path is greater than or equal to 5.
    int pathCount = 0;
    boolean minFive = false;

    game.movePlayer(Direction.LEFT);

    if (!game.isReachedEnd()) {
      pathCount += 1;
    }

    game.movePlayer(Direction.LEFT);

    if (!game.isReachedEnd()) {
      pathCount += 1;
    }

    game.movePlayer(Direction.LEFT);

    if (!game.isReachedEnd()) {
      pathCount += 1;
    }

    game.movePlayer(Direction.LEFT);

    if (!game.isReachedEnd()) {
      pathCount += 1;
    }

    game.movePlayer(Direction.DOWN);

    if (game.isReachedEnd()) {
      pathCount += 1;
    }

    if (pathCount >= 5) {
      minFive = true;
    }

    assertEquals(true, game.isReachedEnd());

    assertEquals(5, pathCount);

    assertTrue(minFive);

    pathCount = 0;
    minFive = false;

    game = new FantasyRolePlayingGame(6, 6, false, 0, 50, 1, 2, 1, new FixedRandom());

    //Traversing the player through nodes and checking if path is greater than or equal to 5.

    game.movePlayer(Direction.LEFT);

    if (!game.isReachedEnd()) {
      pathCount += 1;
    }

    game.movePlayer(Direction.LEFT);

    if (!game.isReachedEnd()) {
      pathCount += 1;
    }

    game.movePlayer(Direction.LEFT);

    if (!game.isReachedEnd()) {
      pathCount += 1;
    }

    game.movePlayer(Direction.LEFT);

    if (!game.isReachedEnd()) {
      pathCount += 1;
    }

    game.movePlayer(Direction.LEFT);

    if (!game.isReachedEnd()) {
      pathCount += 1;
    }

    game.movePlayer(Direction.DOWN);

    if (game.isReachedEnd()) {
      pathCount += 1;
    }

    if (pathCount >= 5) {
      minFive = true;
    }

    assertEquals(true, game.isReachedEnd());

    assertEquals(6, pathCount);

    assertTrue(minFive);

    pathCount = 0;
    minFive = false;

    game = new FantasyRolePlayingGame(8, 8, false, 0, 50, 1, 2, 1, new FixedRandom());

    //Traversing the player through nodes and checking if path is greater than or equal to 5.

    game.movePlayer(Direction.LEFT);

    if (!game.isReachedEnd()) {
      pathCount += 1;
    }

    game.movePlayer(Direction.LEFT);

    if (!game.isReachedEnd()) {
      pathCount += 1;
    }

    game.movePlayer(Direction.LEFT);

    if (!game.isReachedEnd()) {
      pathCount += 1;
    }

    game.movePlayer(Direction.LEFT);

    if (!game.isReachedEnd()) {
      pathCount += 1;
    }

    game.movePlayer(Direction.LEFT);

    if (!game.isReachedEnd()) {
      pathCount += 1;
    }

    game.movePlayer(Direction.LEFT);

    if (!game.isReachedEnd()) {
      pathCount += 1;
    }

    game.movePlayer(Direction.LEFT);

    if (!game.isReachedEnd()) {
      pathCount += 1;
    }

    game.movePlayer(Direction.DOWN);

    if (game.isReachedEnd()) {
      pathCount += 1;
    }

    if (pathCount >= 5) {
      minFive = true;
    }

    assertEquals(true, game.isReachedEnd());

    assertEquals(8, pathCount);

    assertTrue(minFive);
  }

  /**
   * Testing if the player can smell "less" pungent smell when the monster is 2 loations away.
   */
  @Test
  public void testLessPungentSmell() {
    game = new FantasyRolePlayingGame(5, 5, false, 0, 50, 1, 2, 1, new FixedRandom());

    game.movePlayer(Direction.LEFT);
    assertEquals(null, game.getSmell());

    game.movePlayer(Direction.LEFT);
    assertEquals(null, game.getSmell());

    game.movePlayer(Direction.LEFT);
    assertEquals(Smell.PUNGENT, game.getSmell());
  }

  /**
   * Testing if the player can smell "more" pungent smell when the monster is 2 loations away.
   */
  @Test
  public void testMorePungentSmell() {
    game = new FantasyRolePlayingGame(5, 5, false, 0, 50, 1, 2, 1, new FixedRandom());

    game.movePlayer(Direction.LEFT);
    assertEquals(null, game.getSmell());

    game.movePlayer(Direction.LEFT);
    assertEquals(null, game.getSmell());

    game.movePlayer(Direction.LEFT);
    assertEquals(Smell.PUNGENT, game.getSmell());

    game.movePlayer(Direction.LEFT);
    assertEquals(Smell.MOREPUNGENT, game.getSmell());
  }

  /**
   * Tests allocation of pits are accurate and as given by the user.
   */
  @Test
  public void testPitAllocationAndPitsOnlyInCaves() {
    game = new FantasyRolePlayingGame(5, 5, false, 0, 50, 1, 5, 5, new FixedRandom());

    String s = "";

    s = "  <T 0> —— <T 1> —— <T 2> —— <T 3> —— <C 4>  \n"
        + "    |                                        \n"
        + "  <C 5> —— <T 6> —— <T 7> —— <T 8> —— <C 9>  \n"
        + "    |                                        \n"
        + "  <C10> —— <T11> —— <T12> —— <T13> —— <C14>  \n"
        + "    |                                        \n"
        + "  <C15> —— <T16> —— <T17> —— <T18> —— <C19>  \n"
        + "    |                                        \n"
        + "  <T20> —— <T21> —— <T22> —— <T23> —— <C24>  \n"
        + "                                             ";

    s += "\n";

    //Asserting on string

    assertEquals(s, game.printDungeonAs2D());

    /*Traversing the player through all the locations of the dungeon.
     */
    List<ReadOnlyGame> li = new ArrayList<>();
    String checkLoc = "";
    String actLoc = "";

    li.add(game.gameState());
    checkLoc = "Location: 4, Type: CAVE, Possible Moves: [LEFT] Treasure: [DIAMOND]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(0).getLocationId(),
        li.get(0).getLocationType(),
        li.get(0).getPossibleMoves(),
        li.get(0).getTreasure(),
        li.get(0).getThief());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    li.add(game.gameState());
    checkLoc = "Location: 3, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: [], Enemy: THIEF";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Enemy: %s",
        li.get(1).getLocationId(),
        li.get(1).getLocationType(),
        li.get(1).getPossibleMoves(),
        li.get(1).getTreasure(),
        li.get(1).getThief());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    li.add(game.gameState());
    checkLoc = "Location: 2, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: [], Enemy: THIEF";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Enemy: %s",
        li.get(2).getLocationId(),
        li.get(2).getLocationType(),
        li.get(2).getPossibleMoves(),
        li.get(2).getTreasure(),
        li.get(1).getThief());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    li.add(game.gameState());
    checkLoc = "Location: 1, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: [], Enemy: THIEF";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Enemy: %s",
        li.get(3).getLocationId(),
        li.get(3).getLocationType(),
        li.get(3).getPossibleMoves(),
        li.get(3).getTreasure(),
        li.get(1).getThief());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    li.add(game.gameState());
    checkLoc = "Location: 0, Type: TUNNEL, Possible Moves: [DOWN, RIGHT] "
        + "Treasure: [], Enemy: THIEF";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Enemy: %s",
        li.get(4).getLocationId(),
        li.get(4).getLocationType(),
        li.get(4).getPossibleMoves(),
        li.get(4).getTreasure(),
        li.get(1).getThief());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.DOWN);
    li.add(game.gameState());
    checkLoc = "Location: 5, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT] Treasure: [DIAMOND]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(5).getLocationId(),
        li.get(5).getLocationType(),
        li.get(5).getPossibleMoves(),
        li.get(5).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 6, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: [], Enemy: THIEF";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Enemy: %s",
        li.get(6).getLocationId(),
        li.get(6).getLocationType(),
        li.get(6).getPossibleMoves(),
        li.get(6).getTreasure(),
        li.get(1).getThief());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 7, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(7).getLocationId(),
        li.get(7).getLocationType(),
        li.get(7).getPossibleMoves(),
        li.get(7).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 8, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(8).getLocationId(),
        li.get(8).getLocationType(),
        li.get(8).getPossibleMoves(),
        li.get(8).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 9, Type: CAVE, Possible Moves: [LEFT] Treasure: [DIAMOND], "
        + "Obstacles: PIT";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Obstacles: %s",
        li.get(9).getLocationId(),
        li.get(9).getLocationType(),
        li.get(9).getPossibleMoves(),
        li.get(9).getTreasure(),
        li.get(9).getPit());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);

    game.movePlayer(Direction.DOWN);
    li.add(game.gameState());
    checkLoc = "Location: 10, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT] Treasure: [DIAMOND], "
        + "Obstacles: PIT";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Obstacles: %s",
        li.get(10).getLocationId(),
        li.get(10).getLocationType(),
        li.get(10).getPossibleMoves(),
        li.get(10).getTreasure(),
        li.get(10).getPit());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 11, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(11).getLocationId(),
        li.get(11).getLocationType(),
        li.get(11).getPossibleMoves(),
        li.get(11).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 12, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(12).getLocationId(),
        li.get(12).getLocationType(),
        li.get(12).getPossibleMoves(),
        li.get(12).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 13, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(13).getLocationId(),
        li.get(13).getLocationType(),
        li.get(13).getPossibleMoves(),
        li.get(13).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 14, Type: CAVE, Possible Moves: [LEFT] Treasure: [], Obstacles: PIT";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Obstacles: %s",
        li.get(14).getLocationId(),
        li.get(14).getLocationType(),
        li.get(14).getPossibleMoves(),
        li.get(14).getTreasure(),
        li.get(14).getPit());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);

    game.movePlayer(Direction.DOWN);
    li.add(game.gameState());
    checkLoc = "Location: 15, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT] Treasure: [],"
        + " Obstacles: PIT";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Obstacles: %s",
        li.get(15).getLocationId(),
        li.get(15).getLocationType(),
        li.get(15).getPossibleMoves(),
        li.get(15).getTreasure(),
        li.get(15).getPit());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 16, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(16).getLocationId(),
        li.get(16).getLocationType(),
        li.get(16).getPossibleMoves(),
        li.get(16).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 17, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(17).getLocationId(),
        li.get(17).getLocationType(),
        li.get(17).getPossibleMoves(),
        li.get(17).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 18, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(18).getLocationId(),
        li.get(18).getLocationType(),
        li.get(18).getPossibleMoves(),
        li.get(18).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 19, Type: CAVE, Possible Moves: [LEFT] Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(19).getLocationId(),
        li.get(19).getLocationType(),
        li.get(19).getPossibleMoves(),
        li.get(19).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);

    game.movePlayer(Direction.DOWN);
    li.add(game.gameState());
    checkLoc = "Location: 20, Type: TUNNEL, Possible Moves: [UP, RIGHT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(20).getLocationId(),
        li.get(20).getLocationType(),
        li.get(20).getPossibleMoves(),
        li.get(20).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 21, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(21).getLocationId(),
        li.get(21).getLocationType(),
        li.get(21).getPossibleMoves(),
        li.get(21).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 22, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(22).getLocationId(),
        li.get(22).getLocationType(),
        li.get(22).getPossibleMoves(),
        li.get(22).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 23, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(23).getLocationId(),
        li.get(23).getLocationType(),
        li.get(23).getPossibleMoves(),
        li.get(23).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 24, Type: CAVE, Possible Moves: [LEFT] Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(24).getLocationId(),
        li.get(24).getLocationType(),
        li.get(24).getPossibleMoves(),
        li.get(24).getTreasure());
    assertEquals(checkLoc, actLoc);

    int pitCount = 0;

    for (ReadOnlyGame lr : li) {
      if (lr.getPit() != null && (lr.getPit().equals("PIT"))) {
        pitCount += 1;
      }
    }

    //Asserting on number of monsters in the cave
    assertEquals(5, pitCount);
  }

  /**
   * Tests the indicator(sound) indicating the presence of the pit. Presence can be felt only
   * one location away.
   */
  @Test
  public void testPitSoundAtOneLocationAway() {

    game = new FantasyRolePlayingGame(5, 5, false, 0, 50, 1, 5, 5, new FixedRandom());

    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);

    assertEquals("CRACKLING", game.getSound().toString());

    game.movePlayer(Direction.DOWN);
    game.movePlayer(Direction.RIGHT);
    game.movePlayer(Direction.RIGHT);


    assertEquals(null, game.getSound());

    game.movePlayer(Direction.RIGHT);

    assertEquals("CRACKLING", game.getSound().toString());
  }

  /**
   * Tests player surviving the pit.
   */
  @Test
  public void testPlayerSurvivesPitWithEnoughTreasure() {

    game = new FantasyRolePlayingGame(5, 5, false, 0, 50, 1, 5, 5, new FixedRandom());

    game.pickTreasure(Treasure.DIAMOND);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);

    assertEquals(false, game.isFallen());
  }

  /**
   *Tests player falls into pit.
   */
  @Test
  public void testPlayerDoesNotSurvivesPitWithNotEnoughTreasure() {

    game = new FantasyRolePlayingGame(5, 5, false, 0, 50, 1, 5, 5, new FixedRandom());

    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);

    assertEquals(false, game.isFallen());

    game.movePlayer(Direction.DOWN);

    assertEquals(true, game.isFallen());
  }

  /**
   * Tests allocation of thieves are accurate and as given by the user.
   */
  @Test
  public void testThievesAllocationAndThievesOnlyInTunnels() {

    game = new FantasyRolePlayingGame(5, 5, false, 0, 50, 1, 5, 5, new FixedRandom());

    String s = "";

    s = "  <T 0> —— <T 1> —— <T 2> —— <T 3> —— <C 4>  \n"
        + "    |                                        \n"
        + "  <C 5> —— <T 6> —— <T 7> —— <T 8> —— <C 9>  \n"
        + "    |                                        \n"
        + "  <C10> —— <T11> —— <T12> —— <T13> —— <C14>  \n"
        + "    |                                        \n"
        + "  <C15> —— <T16> —— <T17> —— <T18> —— <C19>  \n"
        + "    |                                        \n"
        + "  <T20> —— <T21> —— <T22> —— <T23> —— <C24>  \n"
        + "                                             ";

    s += "\n";

    //Asserting on string

    assertEquals(s, game.printDungeonAs2D());

    /*Traversing the player through all the locations of the dungeon.
     */
    List<ReadOnlyGame> li = new ArrayList<>();
    String checkLoc = "";
    String actLoc = "";

    li.add(game.gameState());
    checkLoc = "Location: 4, Type: CAVE, Possible Moves: [LEFT] Treasure: [DIAMOND]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(0).getLocationId(),
        li.get(0).getLocationType(),
        li.get(0).getPossibleMoves(),
        li.get(0).getTreasure(),
        li.get(0).getThief());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    li.add(game.gameState());
    checkLoc = "Location: 3, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: [], Enemy: THIEF";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Enemy: %s",
        li.get(1).getLocationId(),
        li.get(1).getLocationType(),
        li.get(1).getPossibleMoves(),
        li.get(1).getTreasure(),
        li.get(1).getThief());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    li.add(game.gameState());
    checkLoc = "Location: 2, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: [], Enemy: THIEF";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Enemy: %s",
        li.get(2).getLocationId(),
        li.get(2).getLocationType(),
        li.get(2).getPossibleMoves(),
        li.get(2).getTreasure(),
        li.get(1).getThief());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    li.add(game.gameState());
    checkLoc = "Location: 1, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: [], Enemy: THIEF";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Enemy: %s",
        li.get(3).getLocationId(),
        li.get(3).getLocationType(),
        li.get(3).getPossibleMoves(),
        li.get(3).getTreasure(),
        li.get(1).getThief());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    li.add(game.gameState());
    checkLoc = "Location: 0, Type: TUNNEL, Possible Moves: [DOWN, RIGHT] "
        + "Treasure: [], Enemy: THIEF";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Enemy: %s",
        li.get(4).getLocationId(),
        li.get(4).getLocationType(),
        li.get(4).getPossibleMoves(),
        li.get(4).getTreasure(),
        li.get(1).getThief());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.DOWN);
    li.add(game.gameState());
    checkLoc = "Location: 5, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT] Treasure: [DIAMOND]";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(5).getLocationId(),
        li.get(5).getLocationType(),
        li.get(5).getPossibleMoves(),
        li.get(5).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 6, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: [], Enemy: THIEF";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Enemy: %s",
        li.get(6).getLocationId(),
        li.get(6).getLocationType(),
        li.get(6).getPossibleMoves(),
        li.get(6).getTreasure(),
        li.get(1).getThief());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 7, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(7).getLocationId(),
        li.get(7).getLocationType(),
        li.get(7).getPossibleMoves(),
        li.get(7).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 8, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(8).getLocationId(),
        li.get(8).getLocationType(),
        li.get(8).getPossibleMoves(),
        li.get(8).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 9, Type: CAVE, Possible Moves: [LEFT] Treasure: [DIAMOND], "
        + "Obstacles: PIT";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Obstacles: %s",
        li.get(9).getLocationId(),
        li.get(9).getLocationType(),
        li.get(9).getPossibleMoves(),
        li.get(9).getTreasure(),
        li.get(9).getPit());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);

    game.movePlayer(Direction.DOWN);
    li.add(game.gameState());
    checkLoc = "Location: 10, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT] Treasure: [DIAMOND], "
        + "Obstacles: PIT";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Obstacles: %s",
        li.get(10).getLocationId(),
        li.get(10).getLocationType(),
        li.get(10).getPossibleMoves(),
        li.get(10).getTreasure(),
        li.get(10).getPit());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 11, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(11).getLocationId(),
        li.get(11).getLocationType(),
        li.get(11).getPossibleMoves(),
        li.get(11).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 12, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(12).getLocationId(),
        li.get(12).getLocationType(),
        li.get(12).getPossibleMoves(),
        li.get(12).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 13, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(13).getLocationId(),
        li.get(13).getLocationType(),
        li.get(13).getPossibleMoves(),
        li.get(13).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 14, Type: CAVE, Possible Moves: [LEFT] Treasure: [], Obstacles: PIT";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Obstacles: %s",
        li.get(14).getLocationId(),
        li.get(14).getLocationType(),
        li.get(14).getPossibleMoves(),
        li.get(14).getTreasure(),
        li.get(14).getPit());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);

    game.movePlayer(Direction.DOWN);
    li.add(game.gameState());
    checkLoc = "Location: 15, Type: CAVE, Possible Moves: [UP, DOWN, RIGHT] Treasure: [],"
        + " Obstacles: PIT";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s, Obstacles: %s",
        li.get(15).getLocationId(),
        li.get(15).getLocationType(),
        li.get(15).getPossibleMoves(),
        li.get(15).getTreasure(),
        li.get(15).getPit());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 16, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(16).getLocationId(),
        li.get(16).getLocationType(),
        li.get(16).getPossibleMoves(),
        li.get(16).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 17, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(17).getLocationId(),
        li.get(17).getLocationType(),
        li.get(17).getPossibleMoves(),
        li.get(17).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 18, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(18).getLocationId(),
        li.get(18).getLocationType(),
        li.get(18).getPossibleMoves(),
        li.get(18).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 19, Type: CAVE, Possible Moves: [LEFT] Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(19).getLocationId(),
        li.get(19).getLocationType(),
        li.get(19).getPossibleMoves(),
        li.get(19).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);
    game.movePlayer(Direction.LEFT);

    game.movePlayer(Direction.DOWN);
    li.add(game.gameState());
    checkLoc = "Location: 20, Type: TUNNEL, Possible Moves: [UP, RIGHT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(20).getLocationId(),
        li.get(20).getLocationType(),
        li.get(20).getPossibleMoves(),
        li.get(20).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 21, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(21).getLocationId(),
        li.get(21).getLocationType(),
        li.get(21).getPossibleMoves(),
        li.get(21).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 22, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(22).getLocationId(),
        li.get(22).getLocationType(),
        li.get(22).getPossibleMoves(),
        li.get(22).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 23, Type: TUNNEL, Possible Moves: [RIGHT, LEFT] "
        + "Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(23).getLocationId(),
        li.get(23).getLocationType(),
        li.get(23).getPossibleMoves(),
        li.get(23).getTreasure());
    assertEquals(checkLoc, actLoc);

    game.movePlayer(Direction.RIGHT);
    li.add(game.gameState());
    checkLoc = "Location: 24, Type: CAVE, Possible Moves: [LEFT] Treasure: []";
    actLoc = String.format("Location: %d, Type: %s, Possible Moves: %s "
            + "Treasure: %s",
        li.get(24).getLocationId(),
        li.get(24).getLocationType(),
        li.get(24).getPossibleMoves(),
        li.get(24).getTreasure());
    assertEquals(checkLoc, actLoc);

    int thievesCount = 0;

    for (ReadOnlyGame lr : li) {
      if (lr.getThief() != null && (lr.getThief().equals("THIEF"))) {
        thievesCount += 1;
      }
    }

    //Asserting on number of monsters in the cave
    assertEquals(5, thievesCount);
  }

  /**
   * Tests that the thief steals players arrows and treasure if the player doesn't move away
   * from the location within 5 seconds.
   */
  @Test
  public void testThiefStealsFromThePlayer() {
    game = new FantasyRolePlayingGame(5, 5, false, 0, 50, 1, 5, 5, new FixedRandom());

    try {
      game.pickTreasure(Treasure.DIAMOND);
      game.movePlayer(Direction.LEFT);
      game.pickArrow();
      game.movePlayer(Direction.LEFT);
      game.pickArrow();
      game.movePlayer(Direction.LEFT);
      game.pickArrow();
      game.movePlayer(Direction.LEFT);
      game.pickArrow();
      game.movePlayer(Direction.DOWN);

      assertEquals(1, game.getTreasureCollectedByPlayer().size());
      assertEquals(7, game.getArrowsInPlayerBag().size());

      game.movePlayer(Direction.UP);
      game.movePlayer(Direction.RIGHT);
      Thread.sleep(6000);
      game.movePlayer(Direction.RIGHT);
      game.movePlayer(Direction.RIGHT);
      game.checkTreasureStolen(System.currentTimeMillis());
      game.movePlayer(Direction.RIGHT);


      assertEquals(0, game.getTreasureCollectedByPlayer().size());
      assertEquals(0, game.getArrowsInPlayerBag().size());

    } catch (InterruptedException e) {
      throw new IllegalStateException("Timer Interrupted");
    }
  }

  /**
   * Tests that the player can escape the thief if the player moves away from the location
   * within 5 seconds.
   */
  @Test
  public void testPlayerEscapesFromTheThief() {

    game = new FantasyRolePlayingGame(5, 5, false, 0, 50, 1, 5, 5, new FixedRandom());

    game.pickTreasure(Treasure.DIAMOND);
    game.movePlayer(Direction.LEFT);
    game.pickArrow();
    game.movePlayer(Direction.LEFT);
    game.pickArrow();
    game.movePlayer(Direction.LEFT);
    game.pickArrow();
    game.movePlayer(Direction.LEFT);
    game.pickArrow();
    game.movePlayer(Direction.DOWN);

    assertEquals(1, game.getTreasureCollectedByPlayer().size());
    assertEquals(7, game.getArrowsInPlayerBag().size());

    game.movePlayer(Direction.UP);
    game.movePlayer(Direction.RIGHT);
    game.movePlayer(Direction.RIGHT);
    game.movePlayer(Direction.RIGHT);
    game.checkTreasureStolen(System.currentTimeMillis());
    game.movePlayer(Direction.RIGHT);

    assertEquals(1, game.getTreasureCollectedByPlayer().size());
    assertEquals(7, game.getArrowsInPlayerBag().size());
  }
}
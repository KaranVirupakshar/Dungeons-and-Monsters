package controller;

import static org.junit.Assert.assertEquals;

import model.dungeonsanddragons.Game;
import model.dungeonsanddragons.MockModel;
import org.junit.Test;
import view.MockView;

import java.util.ArrayList;
import java.util.List;

/**
 * Testing suite for dungeons and dragons game with mock view.
 */
public class GameSwingControllerImplTest {

  private MockView view;
  private GameSwingController controller;
  private Game model;

  /**
   * Testing new game generation from JMenu of view.
   */
  @Test
  public void testNewGame() {
    List<String> log = new ArrayList<>();
    view = new MockView(log);
    model = new MockModel(log);
    controller = new GameSwingControllerImpl(model, view);

    controller.playGame();
    controller.createModel(5,5,false,5,50,1,1,1);
    assertEquals("[called setFeatures, called resetFocus, called generateDungeonPanel, called "
        + "updateDungeonPanel, called generateDungeonPanel, called "
        + "updateDungeonPanel]", log.toString());
  }

  /**
   * Testing restart game generation from JMenu of view.
   */
  @Test
  public void testRestartGame() {
    List<String> log = new ArrayList<>();
    view = new MockView(log);
    model = new MockModel(log);
    controller = new GameSwingControllerImpl(model, view);

    controller.playGame();
    controller.resetModel(5,5,false,5,50,1,1,1);
    assertEquals("[called setFeatures, called resetFocus, called generateDungeonPanel, called "
        + "updateDungeonPanel, called generateDungeonPanel, called updateDungeonPanel]",
        log.toString());
  }

  /**
   * Testing view methods called, model methods called and panel updation sequence when player
   * movement is done.
   */
  @Test
  public void testPlayerMovement() {
    List<String> log = new ArrayList<>();
    view = new MockView(log);
    model = new MockModel(log);
    controller = new GameSwingControllerImpl(model, view);


    controller.playGame();
    controller.move("Left");
    controller.move("Right");
    controller.move("Up");
    controller.move("Down");


    assertEquals("[called setFeatures, called resetFocus, called generateDungeonPanel,"
            + " called updateDungeonPanel, Player Direction: LEFT, checkTreasureStolen, called "
            + "updateDungeonPanel, called displayPlayerAction, checkIsKilled, checkIsReachedEnd, "
            + "checkIsFallen, Player Direction: RIGHT, checkTreasureStolen, called "
            + "updateDungeonPanel, called displayPlayerAction, checkIsKilled, checkIsReachedEnd,"
            + " checkIsFallen, Player Direction: UP, checkTreasureStolen, called "
            + "updateDungeonPanel, called displayPlayerAction, checkIsKilled, checkIsReachedEnd,"
            + " checkIsFallen, Player Direction: DOWN, checkTreasureStolen, called "
            + "updateDungeonPanel, called displayPlayerAction, checkIsKilled, checkIsReachedEnd,"
            + " checkIsFallen]",
        log.toString());
  }

  /**
   * Testing view methods called, model methods called and panel updation sequence when player
   * picks treasure.
   */
  @Test
  public void testPlayerPicksTreasure() {
    List<String> log = new ArrayList<>();
    view = new MockView(log);
    model = new MockModel(log);
    controller = new GameSwingControllerImpl(model, view);

    controller.playGame();
    controller.pickTreasure("Ruby");
    controller.pickTreasure("Diamond");
    controller.pickTreasure("Sapphire");

    assertEquals("[called setFeatures, called resetFocus, called generateDungeonPanel, called"
            + " updateDungeonPanel, Pick Treasure Type: RUBY, called updateDungeonPanel, called "
            + "displayPlayerAction, Pick Treasure Type: DIAMOND, called updateDungeonPanel, called"
            + " displayPlayerAction, Pick Treasure Type: SAPPHIRE, called updateDungeonPanel, "
            + "called displayPlayerAction]",
        log.toString());
  }

  /**
   * Testing view methods called, model methods called and panel updation sequence when
   * player picks arrows.
   */
  @Test
  public void testPlayerPicksArrows() {
    List<String> log = new ArrayList<>();
    view = new MockView(log);
    model = new MockModel(log);
    controller = new GameSwingControllerImpl(model, view);

    controller.playGame();
    controller.pickArrow();
    controller.pickArrow();
    controller.pickArrow();

    assertEquals("[called setFeatures, called resetFocus, called generateDungeonPanel, called "
            + "updateDungeonPanel, picked arrow, called updateDungeonPanel, called "
            + "displayPlayerAction, picked arrow, called updateDungeonPanel, called"
            + " displayPlayerAction, picked arrow, called updateDungeonPanel, called "
            + "displayPlayerAction]",
        log.toString());
  }

  /**
   * Testing view methods called, model methods called and panel updation sequence when player
   * shoots arrows.
   */
  @Test
  public void testPlayerShootsArrows() {
    List<String> log = new ArrayList<>();
    view = new MockView(log);
    model = new MockModel(log);
    controller = new GameSwingControllerImpl(model, view);

    controller.playGame();
    controller.shootArrow("Up", 2);
    controller.shootArrow("Down", 1);
    controller.shootArrow("Right", 4);

    assertEquals("[called setFeatures, called resetFocus, called generateDungeonPanel,"
            + " called updateDungeonPanel, Shoot Arrow Distance: 2, Shoot Arrow Direction: UP,"
            + " called updateDungeonPanel, called displayPlayerAction with delay, Shoot Arrow "
            + "Distance: 1, Shoot Arrow Direction: DOWN, called updateDungeonPanel, called "
            + "displayPlayerAction with delay, Shoot Arrow Distance: 4, Shoot Arrow Direction:"
            + " RIGHT, called updateDungeonPanel, called displayPlayerAction with delay]",
        log.toString());
  }

  /**
   * Testing view methods called, model methods called and panel updation sequence when player
   * moves and is checked if the player wins or loses by getting killed by the monster or falls
   * into the pit.
   */
  @Test
  public void testPlayerLosesOrWinsAsGameStatus() {
    List<String> log = new ArrayList<>();
    view = new MockView(log);
    model = new MockModel(log);
    controller = new GameSwingControllerImpl(model, view);

    controller.playGame();
    controller.move("Left");
    controller.move("Right");
    controller.move("Up");
    controller.move("Down");

    assertEquals("[called setFeatures, called resetFocus, called generateDungeonPanel,"
            + " called updateDungeonPanel, Player Direction: LEFT, checkTreasureStolen, called "
            + "updateDungeonPanel, called displayPlayerAction, checkIsKilled, checkIsReachedEnd, "
            + "checkIsFallen, Player Direction: RIGHT, checkTreasureStolen, called "
            + "updateDungeonPanel, called displayPlayerAction, checkIsKilled, checkIsReachedEnd,"
            + " checkIsFallen, Player Direction: UP, checkTreasureStolen, called "
            + "updateDungeonPanel, called displayPlayerAction, checkIsKilled, checkIsReachedEnd,"
            + " checkIsFallen, Player Direction: DOWN, checkTreasureStolen, called "
            + "updateDungeonPanel, called displayPlayerAction, checkIsKilled, checkIsReachedEnd,"
            + " checkIsFallen]",
        log.toString());
  }


  /**
   * Testing view methods called, model methods called and panel updation sequence when player
   * performs a series of actions.
   */
  @Test
  public void testPlayerActionDisplay() {
    List<String> log = new ArrayList<>();
    view = new MockView(log);
    model = new MockModel(log);
    controller = new GameSwingControllerImpl(model, view);

    controller.playGame();
    controller.move("Right");
    controller.move("Down");
    controller.shootArrow("Up", 2);
    controller.pickTreasure("Ruby");
    controller.pickArrow();

    assertEquals("[called setFeatures, called resetFocus, called generateDungeonPanel, "
            + "called updateDungeonPanel, Player Direction: RIGHT, checkTreasureStolen, called "
            + "updateDungeonPanel, called displayPlayerAction, checkIsKilled, checkIsReachedEnd,"
            + " checkIsFallen, Player Direction: DOWN, checkTreasureStolen, called "
            + "updateDungeonPanel, called displayPlayerAction, checkIsKilled, checkIsReachedEnd,"
            + " checkIsFallen, Shoot Arrow Distance: 2, Shoot Arrow Direction: UP, called "
            + "updateDungeonPanel, called displayPlayerAction with delay, Pick Treasure Type: "
            + "RUBY, called updateDungeonPanel, called displayPlayerAction, picked arrow, called"
            + " updateDungeonPanel, called displayPlayerAction]",
        log.toString());
  }


  /**
   * Testing view methods called, model methods called and panel updation sequence when player
   * performs a series of actions.
   */
  @Test
  public void testPlayerDescription() {
    List<String> log = new ArrayList<>();
    view = new MockView(log);
    model = new MockModel(log);
    controller = new GameSwingControllerImpl(model, view);

    controller.playGame();
    controller.pickTreasure("Diamons");
    controller.shootArrow("Up", 2);
    controller.pickTreasure("Ruby");
    controller.pickArrow();
    controller.pickArrow();

    assertEquals("[called setFeatures, called resetFocus, called generateDungeonPanel, called"
            + " updateDungeonPanel, Shoot Arrow Distance: 2, Shoot Arrow Direction: UP, called "
            + "updateDungeonPanel, called displayPlayerAction with delay, Pick Treasure Type: "
            + "RUBY, called updateDungeonPanel, called displayPlayerAction, picked arrow, called"
            + " updateDungeonPanel, called displayPlayerAction, picked arrow, called "
            + "updateDungeonPanel, called displayPlayerAction]",
        log.toString());
  }
}
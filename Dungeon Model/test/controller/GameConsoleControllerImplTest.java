package controller;

import static org.junit.Assert.assertEquals;

import model.dungeonsanddragons.FantasyRolePlayingGame;
import model.dungeonsanddragons.Game;
import model.dungeonsanddragons.LoggingGameImpl;
import org.junit.Before;
import org.junit.Test;
import random.FixedRandom;

import java.io.StringReader;

/**
 * Testing framework to validate all the functionalities work together as expected when the model
 * and controller have been coupled.
 */
public class GameConsoleControllerImplTest {

  private Game g;

  /**
   * Initializing the game object that is going to be used in the test suite.
   */
  @Before
  public void setUp() {
    g = new FantasyRolePlayingGame(5, 5, false, 5, 50, 10, 2, 2, new FixedRandom());
  }

  /**
   * Testing the failing appendable.
   */
  @Test(expected = IllegalStateException.class)
  public void testFailingAppendable() {
    // Testing when something goes wrong with the Appendable
    // Here we are passing it a mock of an Appendable that always fails
    StringReader input = new StringReader("P A P D M L P A");
    Appendable gameLog = new FailingAppendable();
    GameConsoleController c = new GameConsoleControllerImpl(input, gameLog);
    c.playGame(g);
  }

  /**
   * Tests that controller is not manipulating the user input while passing the inputs to the
   * model. This is done by using the mock model Logging game Impl.
   */
  @Test
  public void testControllerWithMockModel() {
    StringReader input = new StringReader("M U M D M L M R P A P D P R P S Q");
    Appendable gameLog = new StringBuilder();
    StringBuilder inputLog = new StringBuilder();
    Game m = new LoggingGameImpl(inputLog);
    GameConsoleController c = new GameConsoleControllerImpl(input, gameLog);
    c.playGame(m);
    String check = "Player Direction: UP\n"
        + "Player Direction: DOWN\n"
        + "Player Direction: LEFT\n"
        + "Player Direction: RIGHT\n"
        + "Pick Treasure Type: DIAMOND\n"
        + "Pick Treasure Type: RUBY\n"
        + "Pick Treasure Type: SAPPHIRE\n";
    assertEquals(check, inputLog.toString());
  }


  /**
   * Tests that the IllegalArgumentException is thrown when the game model passed to the
   * controller is invalid.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidGameModel() {
    StringReader input = new StringReader("P A P D M L P A Q");
    Appendable gameLog = new StringBuilder();
    GameConsoleController c = new GameConsoleControllerImpl(input, gameLog);
    c.playGame(null);
  }

  /**
   * Tests that IllegalStateException is thrown when the readable or appendable is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidAppendableOrReadable() {
    StringReader input = new StringReader("M U M D M L M R P A P D P R P S Q");
    Appendable gameLog = new StringBuilder();
    StringBuilder inputLog = new StringBuilder();
    Game m = new LoggingGameImpl(inputLog);
    GameConsoleController c = new GameConsoleControllerImpl(null, null);
    c.playGame(m);
  }


  /**
   * Tests player playing the game.
   */
  @Test
  public void testPlayGame() {
    StringReader input = new StringReader("P A P D M L P A Q");
    Appendable gameLog = new StringBuilder();
    GameConsoleController c = new GameConsoleControllerImpl(input, gameLog);
    c.playGame(g);
    String check = "\n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "What (Weapon - A, Ruby - R, Diamond - D, Sapphire - S)? \n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You currently have 4 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " What (Weapon - A, Ruby - R, Diamond - D, Sapphire - S)? \n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "\n"
        + "You currently have 4 arrows in your bag that you can use.\n"
        + "So far, You have picked 1 Diamond\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a TUNNEL and can move DOWN, RIGHT\n"
        + "This TUNNEL has 1 arrow\n"
        + "\n"
        + "You currently have 4 arrows in your bag that you can use.\n"
        + "So far, You have picked 1 Diamond\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "What (Weapon - A, Ruby - R, Diamond - D, Sapphire - S)? \n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a TUNNEL and can move DOWN, RIGHT\n"
        + "\n"
        + "You currently have 5 arrows in your bag that you can use.\n"
        + "So far, You have picked 1 Diamond\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? \n"
        + "Booo!!!!! You just gave up!\n"
        + "You might wanna play again!";
    assertEquals(check, gameLog.toString());
  }

  /**
   * Tests player possible movements (Up, Down, Left, Right) and moves that are not available in
   * a non-wrapping dungeon.
   */
  @Test
  public void testPlayerPossibleAndNotPossibleMovesInNonWrapping() {
    Game game = new FantasyRolePlayingGame(5, 5, false, 5, 50, 2, 2, 2, new FixedRandom());
    StringReader input = new StringReader("M U M L M L M D M D M D M D M D M R Q");
    Appendable gameLog = new StringBuilder();
    GameConsoleController c = new GameConsoleControllerImpl(input, gameLog);
    c.playGame(game);
    String check = "\n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? Can't go Up!\n"
        + "\n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a TUNNEL and can move DOWN, RIGHT\n"
        + "This TUNNEL has 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? Can't go Left!\n"
        + "\n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a TUNNEL and can move DOWN, RIGHT\n"
        + "This TUNNEL has 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a TUNNEL and can move UP, RIGHT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? Can't go Down!\n"
        + "\n"
        + "You are in a TUNNEL and can move UP, RIGHT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? \n"
        + "Booo!!!!! You just gave up!\n"
        + "You might wanna play again!";
    assertEquals(check, gameLog.toString());

    //Testing the number of lines in the output
    int lines = check.split("[\n|\r]").length;
    assertEquals(66, lines);
  }

  /**
   * Tests player possible movements (Up, Down, Left, Right) and moves that are not available in
   * a non-wrapping dungeon.
   */
  @Test
  public void testPlayerPossibleAndNotPossibleMovesInWrapping() {
    Game game = new FantasyRolePlayingGame(5, 5, true, 5, 50, 2, 2, 2, new FixedRandom());
    StringReader input = new StringReader("M U M U M R M L M D M L M L M L M U Q");
    Appendable gameLog = new StringBuilder();
    GameConsoleController c = new GameConsoleControllerImpl(input, gameLog);
    c.playGame(game);
    String check = "\n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move DOWN\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? Can't go Up!\n"
        + "\n"
        + "You are in a CAVE and can move DOWN\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? Can't go Right!\n"
        + "\n"
        + "You are in a CAVE and can move DOWN\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? Can't go Left!\n"
        + "\n"
        + "You are in a CAVE and can move DOWN\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "Oops!!!!! An hungry monster just obliterated you!\n"
        + "You might wanna play again!";
    assertEquals(check, gameLog.toString());

    //Testing the number of lines in the output
    int lines = check.split("[\n|\r]").length;
    assertEquals(47, lines);
  }


  /**
   * Tests player picking arrow by asserting that the size of the player arrow bag updates
   * accurately after every pick action.
   */
  @Test
  public void testPlayerPickArrowWhenExisting() {
    Game game = new FantasyRolePlayingGame(5, 5, false, 5, 50, 2, 2, 2, new FixedRandom());
    StringReader input = new StringReader("P A M L P A M L P A Q");
    Appendable gameLog = new StringBuilder();
    GameConsoleController c = new GameConsoleControllerImpl(input, gameLog);
    c.playGame(game);
    String check = "\n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "What (Weapon - A, Ruby - R, Diamond - D, Sapphire - S)? \n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You currently have 4 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a TUNNEL and can move DOWN, RIGHT\n"
        + "This TUNNEL has 1 arrow\n"
        + "\n"
        + "You currently have 4 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "What (Weapon - A, Ruby - R, Diamond - D, Sapphire - S)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a TUNNEL and can move DOWN, RIGHT\n"
        + "\n"
        + "You currently have 5 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? Can't go Left!\n"
        + "\n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a TUNNEL and can move DOWN, RIGHT\n"
        + "\n"
        + "You currently have 5 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "What (Weapon - A, Ruby - R, Diamond - D, Sapphire - S)? Can't find that in here!\n"
        + "\n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a TUNNEL and can move DOWN, RIGHT\n"
        + "\n"
        + "You currently have 5 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? \n"
        + "Booo!!!!! You just gave up!\n"
        + "You might wanna play again!";
    assertEquals(check, gameLog.toString());

    //Testing the number of lines in the output
    int lines = check.split("[\n|\r]").length;
    assertEquals(44, lines);
  }

  /**
   * Tests players arrow bag updates accurately after picking up arrows.
   */
  @Test
  public void testPlayerArrowBagContents() {
    Game game = new FantasyRolePlayingGame(5, 5, false, 5, 50, 2, 2, 2, new FixedRandom());
    StringReader input = new StringReader("P A M L P A M D P A M R P A Q");
    Appendable gameLog = new StringBuilder();
    GameConsoleController c = new GameConsoleControllerImpl(input, gameLog);
    c.playGame(game);
    String check = "\n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "What (Weapon - A, Ruby - R, Diamond - D, Sapphire - S)? \n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You currently have 4 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a TUNNEL and can move DOWN, RIGHT\n"
        + "This TUNNEL has 1 arrow\n"
        + "\n"
        + "You currently have 4 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "What (Weapon - A, Ruby - R, Diamond - D, Sapphire - S)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a TUNNEL and can move DOWN, RIGHT\n"
        + "\n"
        + "You currently have 5 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 5 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "What (Weapon - A, Ruby - R, Diamond - D, Sapphire - S)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You currently have 6 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 6 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "What (Weapon - A, Ruby - R, Diamond - D, Sapphire - S)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You currently have 7 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? \n"
        + "Booo!!!!! You just gave up!\n"
        + "You might wanna play again!";
    assertEquals(check, gameLog.toString());

    //Testing the number of lines in the output
    int lines = check.split("[\n|\r]").length;
    assertEquals(56, lines);
  }

  /**
   * Tests player picking arrow by asserting that the size of the player arrow bag does not
   * update when player tries to pick arrow that doesn't exist.
   */
  @Test
  public void testPlayerPickArrowWhenNonExisting() {
    Game game = new FantasyRolePlayingGame(5, 5, false, 5, 50, 2, 2, 2, new FixedRandom());
    StringReader input = new StringReader("M D M D P A M L P A Q");
    Appendable gameLog = new StringBuilder();
    GameConsoleController c = new GameConsoleControllerImpl(input, gameLog);
    c.playGame(game);
    String check = "\n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "What (Weapon - A, Ruby - R, Diamond - D, Sapphire - S)? Can't find that in here!\n"
        + "\n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "What (Weapon - A, Ruby - R, Diamond - D, Sapphire - S)? Can't find that in here!\n"
        + "\n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? \n"
        + "Booo!!!!! You just gave up!\n"
        + "You might wanna play again!";
    assertEquals(check, gameLog.toString());

    //Testing the number of lines in the output
    int lines = check.split("[\n|\r]").length;
    assertEquals(41, lines);
  }

  /**
   * Tests players treasure bag updates accurately when the player picks up the treasure.
   */
  @Test
  public void testPlayerTreasureBagContents() {
    Game game = new FantasyRolePlayingGame(5, 5, false, 5, 50, 1, 2, 2, new FixedRandom());
    StringReader input = new StringReader("P D M R P D M L M D P D M L P D Q");
    Appendable gameLog = new StringBuilder();
    GameConsoleController c = new GameConsoleControllerImpl(input, gameLog);
    c.playGame(game);
    String check = "\n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "What (Weapon - A, Ruby - R, Diamond - D, Sapphire - S)? \n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "So far, You have picked 1 Diamond\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "So far, You have picked 1 Diamond\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "What (Weapon - A, Ruby - R, Diamond - D, Sapphire - S)? \n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "So far, You have picked 2 Diamonds\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "So far, You have picked 2 Diamonds\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "So far, You have picked 2 Diamonds\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "What (Weapon - A, Ruby - R, Diamond - D, Sapphire - S)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "So far, You have picked 3 Diamonds\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "So far, You have picked 3 Diamonds\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "What (Weapon - A, Ruby - R, Diamond - D, Sapphire - S)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "This CAVE has 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "So far, You have picked 4 Diamonds\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? \n"
        + "Booo!!!!! You just gave up!\n"
        + "You might wanna play again!";
    assertEquals(check, gameLog.toString());

    //Testing the number of lines in the output
    int lines = check.split("[\n|\r]").length;
    assertEquals(57, lines);
  }

  /**
   * Tests player picking treasure by asserting that the treasure bag of the player updates
   * accurately after pick treasure action.
   */
  @Test
  public void testPlayerPickTreasureWhenExisting() {
    Game game = new FantasyRolePlayingGame(5, 5, false, 5, 50, 2, 2, 2, new FixedRandom());
    StringReader input = new StringReader("P D M D P D M R P D Q");
    Appendable gameLog = new StringBuilder();
    GameConsoleController c = new GameConsoleControllerImpl(input, gameLog);
    c.playGame(game);
    String check = "\n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "What (Weapon - A, Ruby - R, Diamond - D, Sapphire - S)? \n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "So far, You have picked 1 Diamond\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "So far, You have picked 1 Diamond\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "What (Weapon - A, Ruby - R, Diamond - D, Sapphire - S)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "So far, You have picked 2 Diamonds\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "So far, You have picked 2 Diamonds\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "What (Weapon - A, Ruby - R, Diamond - D, Sapphire - S)? \n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "So far, You have picked 3 Diamonds\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? \n"
        + "Booo!!!!! You just gave up!\n"
        + "You might wanna play again!";
    assertEquals(check, gameLog.toString());

    //Testing the number of lines in the output
    int lines = check.split("[\n|\r]").length;
    assertEquals(45, lines);
  }

  /**
   * Tests player picking non-existent treasure by asserting that the treasure bag of the player
   * doesn't update after pick treasure action.
   */
  @Test
  public void testPlayerPickTreasureWhenNonExisting() {
    Game game = new FantasyRolePlayingGame(5, 5, false, 5, 50, 2, 2, 2, new FixedRandom());
    StringReader input = new StringReader("M L M D M D M D M U M R M R M R M R "
        + "M L M L M L M D M R M R M R Q");
    Appendable gameLog = new StringBuilder();
    GameConsoleController c = new GameConsoleControllerImpl(input, gameLog);
    c.playGame(game);
    String check = "\n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a TUNNEL and can move DOWN, RIGHT\n"
        + "This TUNNEL has 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "Oops!!!!! An hungry monster just obliterated you!\n"
        + "You might wanna play again!";
    assertEquals(check, gameLog.toString());

    //Testing the number of lines in the output
    int lines = check.split("[\n|\r]").length;
    assertEquals(57, lines);
  }

  /**
   * Tests player picking invalid treasure/arrow by asserting that the message defined in the
   * IllegalArgumentException is appended.
   */
  @Test
  public void testPlayerPicksInvalidItem() {
    Game game = new FantasyRolePlayingGame(5, 5, false, 5, 50, 2, 2, 2, new FixedRandom());
    StringReader input = new StringReader("M L P T P Y P M Q");
    Appendable gameLog = new StringBuilder();
    GameConsoleController c = new GameConsoleControllerImpl(input, gameLog);
    c.playGame(game);
    String check = "\n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a TUNNEL and can move DOWN, RIGHT\n"
        + "This TUNNEL has 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "What (Weapon - A, Ruby - R, Diamond - D, Sapphire - S)? Didn't offer that Option!\n"
        + "\n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a TUNNEL and can move DOWN, RIGHT\n"
        + "This TUNNEL has 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "What (Weapon - A, Ruby - R, Diamond - D, Sapphire - S)? Didn't offer that Option!\n"
        + "\n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a TUNNEL and can move DOWN, RIGHT\n"
        + "This TUNNEL has 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "What (Weapon - A, Ruby - R, Diamond - D, Sapphire - S)? Didn't offer that Option!\n"
        + "\n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a TUNNEL and can move DOWN, RIGHT\n"
        + "This TUNNEL has 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? \n"
        + "Booo!!!!! You just gave up!\n"
        + "You might wanna play again!";
    assertEquals(check, gameLog.toString());

    //Testing the number of lines in the output
    int lines = check.split("[\n|\r]").length;
    assertEquals(41, lines);
  }

  /**
   * Tests player picking invalid move by asserting that the message defined in the
   * IllegalArgumentException is appended.
   */
  @Test
  public void testPlayerMakesInvalidMove() {
    Game game = new FantasyRolePlayingGame(5, 5, false, 5, 50, 2, 2, 2, new FixedRandom());
    StringReader input = new StringReader("M Y M U M I M V Q");
    Appendable gameLog = new StringBuilder();
    GameConsoleController c = new GameConsoleControllerImpl(input, gameLog);
    c.playGame(game);
    String check = "\n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? Didn't offer that Option!\n"
        + "\n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? Can't go Up!\n"
        + "\n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? Didn't offer that Option!\n"
        + "\n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? Didn't offer that Option!\n"
        + "\n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? \n"
        + "Booo!!!!! You just gave up!\n"
        + "You might wanna play again!";
    assertEquals(check, gameLog.toString());

    //Testing the number of lines in the output
    int lines = check.split("[\n|\r]").length;
    assertEquals(42, lines);
  }

  /**
   * Tests player picking shoots using invalid values by asserting that the message defined in the
   * IllegalArgumentException is appended.
   */
  @Test
  public void testPlayerShootsUsingInvalidValues() {
    Game game = new FantasyRolePlayingGame(5, 5, false, 5, 50, 2, 2, 2, new FixedRandom());
    StringReader input = new StringReader("S 1 D S 2 V Q");
    Appendable gameLog = new StringBuilder();
    GameConsoleController c = new GameConsoleControllerImpl(input, gameLog);
    c.playGame(game);
    String check = "\n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "How far? Which direction (Up - U, Down - D, Left - L, Right - R)? "
        + "You shot the arrow into the darkness!\n"
        + "\n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 2 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "How far? Which direction (Up - U, Down - D, Left - L, Right - R)? "
        + "Didn't offer that Option!\n"
        + "\n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 2 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? \n"
        + "Booo!!!!! You just gave up!\n"
        + "You might wanna play again!";
    assertEquals(check, gameLog.toString());

    //Testing the number of lines in the output
    int lines = check.split("[\n|\r]").length;
    assertEquals(26, lines);
  }

  /**
   * Tests treasure allocated at each location is reflected accurately as the game state
   * to the player as well as treasure allocation reflects correct percentage.
   */
  @Test
  public void testTreasureAllocationAndAvailableTreasureAtEveryMove() {
    Game game = new FantasyRolePlayingGame(5, 5, false, 5, 50, 2, 2, 2, new FixedRandom());
    StringReader input = new StringReader("M L M D M D M D M U M R M R M R M R "
        + "M L M L M L M D M R M R M R Q");
    Appendable gameLog = new StringBuilder();
    GameConsoleController c = new GameConsoleControllerImpl(input, gameLog);
    c.playGame(game);
    String check = "\n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a TUNNEL and can move DOWN, RIGHT\n"
        + "This TUNNEL has 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "Oops!!!!! An hungry monster just obliterated you!\n"
        + "You might wanna play again!";
    assertEquals(check, gameLog.toString());

    //Testing the number of lines in the output
    int lines = check.split("[\n|\r]").length;
    assertEquals(57, lines);
  }


  /**
   * Tests arrows allocated at each location is reflected accurately as the game state
   * to the player as well as arrow allocation to the entire dungeon.
   */
  @Test
  public void testArrowAllocationAndAvailableArrowsAtEveryMove() {
    Game game = new FantasyRolePlayingGame(5, 5, false, 5, 50, 2, 2, 2, new FixedRandom());
    StringReader input = new StringReader("M L M D M D M D M U M R M R M R M R "
        + "M L M L M L M D M R M R M R Q");
    Appendable gameLog = new StringBuilder();
    GameConsoleController c = new GameConsoleControllerImpl(input, gameLog);
    c.playGame(game);
    String check = "\n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a TUNNEL and can move DOWN, RIGHT\n"
        + "This TUNNEL has 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "Oops!!!!! An hungry monster just obliterated you!\n"
        + "You might wanna play again!";
    assertEquals(check, gameLog.toString());

    //Testing the number of lines in the output
    int lines = check.split("[\n|\r]").length;
    assertEquals(57, lines);
  }

  /**
   * Tests non wrapping dungeon creation as well as available moves at each location is reflected
   * accurately as the game state to the player.
   */
  @Test
  public void testNonWrappingDungeonCreationAndAvailableMovesAtEveryMove() {
    Game game = new FantasyRolePlayingGame(5, 5, false, 5, 50, 2, 2, 2, new FixedRandom());
    StringReader input = new StringReader("M L M D M D M D M U M R M R M R "
        + "M L M L M L M D M R M R M R M L M L M L M D M L M U M U M L M D Q");
    Appendable gameLog = new StringBuilder();
    GameConsoleController c = new GameConsoleControllerImpl(input, gameLog);
    c.playGame(game);
    String check = "\n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a TUNNEL and can move DOWN, RIGHT\n"
        + "This TUNNEL has 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a TUNNEL and can move UP, RIGHT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? Can't go Left!\n"
        + "\n"
        + "You are in a TUNNEL and can move UP, RIGHT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? Can't go Left!\n"
        + "\n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? \n"
        + "Booo!!!!! You just gave up!\n"
        + "You might wanna play again!";
    assertEquals(check, gameLog.toString());

    //Testing the number of lines in the output
    int lines = check.split("[\n|\r]").length;
    assertEquals(143, lines);
  }

  /**
   * Tests wrapping dungeon creation as well as available moves at each location is reflected
   * accurately as the game state to the player.
   */
  @Test
  public void testWrappingDungeonCreationAndAvailableMovesAtEveryMove() {
    Game game = new FantasyRolePlayingGame(5, 5, true, 5, 50, 2, 2, 2, new FixedRandom());
    StringReader input = new StringReader("M L M D M D M D M U M R M R M R "
        + "M L M L M L M D M R M R M R M L M L M L M D M L M U M U");
    Appendable gameLog = new StringBuilder();
    GameConsoleController c = new GameConsoleControllerImpl(input, gameLog);
    c.playGame(game);
    String check = "\n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? Can't go Down!\n"
        + "\n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? Can't go Down!\n"
        + "\n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? Can't go Down!\n"
        + "\n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move DOWN\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? Can't go Right!\n"
        + "\n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move DOWN\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? Can't go Right!\n"
        + "\n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move DOWN\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? Can't go Right!\n"
        + "\n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move DOWN\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? Can't go Left!\n"
        + "\n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move DOWN\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? Can't go Left!\n"
        + "\n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move DOWN\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? Can't go Left!\n"
        + "\n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move DOWN\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? Can't go Down!\n"
        + "\n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "Oops!!!!! An hungry monster just obliterated you!\n"
        + "You might wanna play again!";
    assertEquals(check, gameLog.toString());

    //Testing the number of lines in the output
    int lines = check.split("[\n|\r]").length;
    assertEquals(146, lines);
  }

  /**
   * Tests smell status at each location is reflected accurately as the game state
   * to the player.
   */
  @Test
  public void testSmellTypeAtEveryMove() {
    Game game = new FantasyRolePlayingGame(5, 5, false, 5, 50, 2, 2, 2, new FixedRandom());
    StringReader input = new StringReader("M L M D M D M D M U M R M R M R "
        + "M L M L M L M D M R M R M R M L M L M L M D M L M U M U Q");
    Appendable gameLog = new StringBuilder();
    GameConsoleController c = new GameConsoleControllerImpl(input, gameLog);
    c.playGame(game);
    String check = "\n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a TUNNEL and can move DOWN, RIGHT\n"
        + "This TUNNEL has 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a TUNNEL and can move UP, RIGHT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? Can't go Left!\n"
        + "\n"
        + "You are in a TUNNEL and can move UP, RIGHT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? \n"
        + "Booo!!!!! You just gave up!\n"
        + "You might wanna play again!";
    assertEquals(check, gameLog.toString());

    //Testing the number of lines in the output
    int lines = check.split("[\n|\r]").length;
    assertEquals(131, lines);
  }

  /**
   * Tests player quitting the game by entering q/Q.
   */
  @Test
  public void testPlayerQuits() {
    Game game = new FantasyRolePlayingGame(5, 5, false, 5, 50, 2, 2, 2, new FixedRandom());
    StringReader input = new StringReader("M L Q");
    Appendable gameLog = new StringBuilder();
    GameConsoleController c = new GameConsoleControllerImpl(input, gameLog);
    c.playGame(game);
    String check = "\n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a TUNNEL and can move DOWN, RIGHT\n"
        + "This TUNNEL has 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? \n"
        + "Booo!!!!! You just gave up!\n"
        + "You might wanna play again!";
    assertEquals(check, gameLog.toString());

    //Testing the number of lines in the output
    int lines = check.split("[\n|\r]").length;
    assertEquals(17, lines);
  }

  /**
   * Tests player losing the game by getting eaten by the monster.
   */
  @Test
  public void testPlayerVisitingAllLocations() {
    Game game = new FantasyRolePlayingGame(5, 5, false, 5, 50, 1, 2, 2, new FixedRandom());
    StringReader input = new StringReader("M R M R M R M L M L M L M L M D M R M R M R M R "
        + "M L M L M L M L M D M D M R M R M R M R M L M L M L M U M R M R S 1 R S 1 R M R");
    Appendable gameLog = new StringBuilder();
    GameConsoleController c = new GameConsoleControllerImpl(input, gameLog);
    c.playGame(game);
    String check = "\n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "This TUNNEL has 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "This TUNNEL has 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a TUNNEL and can move DOWN, RIGHT\n"
        + "This TUNNEL has 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move LEFT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "How far? Which direction (Up - U, Down - D, Left - L, Right - R)? "
        + "You shot the arrow into the darkness!\n"
        + "\n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 2 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "How far? Which direction (Up - U, Down - D, Left - L, Right - R)? "
        + "You shot the arrow into the darkness!\n"
        + "\n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 1 arrow in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "Yayy!!!!! You've conquered the dungeon by slaying the boss Otyugh!\n"
        + "You might wanna play again!";
    assertEquals(check, gameLog.toString());

    //Testing the number of lines in the output
    int lines = check.split("[\n|\r]").length;
    assertEquals(179, lines);
  }

  /**
   * Tests player winning the game by slaying the monster and reaching the end dungeon.
   */
  @Test
  public void testPlayerWins() {
    Game game = new FantasyRolePlayingGame(5, 5, false, 5, 50, 2, 2, 2, new FixedRandom());
    StringReader input = new StringReader("M D M D M R M R S 1 R S 1 R M R");
    Appendable gameLog = new StringBuilder();
    GameConsoleController c = new GameConsoleControllerImpl(input, gameLog);
    c.playGame(game);
    String check = "\n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " How far? Which direction (Up - U, Down - D, Left - L, Right - R)? "
        + "You shot the arrow into the darkness!\n"
        + "\n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 2 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " How far? Which direction (Up - U, Down - D, Left - L, Right - R)? "
        + "You shot the arrow into the darkness!\n"
        + "\n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 1 arrow in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "Yayy!!!!! You've conquered the dungeon by slaying the boss Otyugh!\n"
        + "You might wanna play again!";
    assertEquals(check, gameLog.toString());

    //Testing the number of lines in the output
    int lines = check.split("[\n|\r]").length;
    assertEquals(47, lines);
  }

  /**
   * Tests player losing the game which means getting eaten by a monster.
   */
  @Test
  public void testPlayerLoses() {
    Game game = new FantasyRolePlayingGame(5, 5, false, 5, 50, 2, 2, 2, new FixedRandom());
    StringReader input = new StringReader("M D M U M R");
    Appendable gameLog = new StringBuilder();
    GameConsoleController c = new GameConsoleControllerImpl(input, gameLog);
    c.playGame(game);
    String check = "\n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "Oops!!!!! An hungry monster just obliterated you!\n"
        + "You might wanna play again!";
    assertEquals(check, gameLog.toString());

    //Testing the number of lines in the output
    int lines = check.split("[\n|\r]").length;
    assertEquals(24, lines);
  }

  /**
   * Tests player 50% chance of survival when entering the cave with slained dungeon.
   */
  @Test
  public void testPlayerWinsOrLosesHalfKilledMonster() {
    Game game = new FantasyRolePlayingGame(5, 5, false, 5, 50, 2, 2, 2, new FixedRandom());
    StringReader input = new StringReader("M D M D M R M R S 1 R M R");
    Appendable gameLog = new StringBuilder();
    GameConsoleController c = new GameConsoleControllerImpl(input, gameLog);
    c.playGame(game);
    String check = "\n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "How far? Which direction (Up - U, Down - D, Left - L, Right - R)? "
        + "You shot the arrow into the darkness!\n"
        + "\n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 2 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "Yayy!!!!! You've conquered the dungeon by slaying the boss Otyugh!\n"
        + "You might wanna play again!";
    assertEquals(check, gameLog.toString());

    //Testing the number of lines in the output
    int lines = check.split("[\n|\r]").length;
    assertEquals(41, lines);
  }


  /**
   * Tests player shooting the arrow in a non wrapping dungeon with exact distance and correct
   * direction and asserting that the monster gets killed.
   */
  @Test
  public void testPlayerShootingArrowInNonWrapping() {
    Game game = new FantasyRolePlayingGame(5, 5, false, 5, 50, 2, 2, 2, new FixedRandom());
    StringReader input = new StringReader("P A S 1 R S 1 R M R M L M D M D M R M R "
        + "S 1 R S 1 R M R");
    Appendable gameLog = new StringBuilder();
    GameConsoleController c = new GameConsoleControllerImpl(input, gameLog);
    c.playGame(game);
    String check = "\n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "What (Weapon - A, Ruby - R, Diamond - D, Sapphire - S)? \n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You currently have 4 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "How far? Which direction (Up - U, Down - D, Left - L, Right - R)? "
        + "You shot the arrow into the darkness!\n"
        + "\n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "How far? Which direction (Up - U, Down - D, Left - L, Right - R)? "
        + "You shot the arrow into the darkness!\n"
        + "\n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You currently have 2 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "You spot a trophy. The monster you have slayed!\n"
        + "\n"
        + "You currently have 2 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You currently have 2 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 2 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "\n"
        + "You currently have 2 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 2 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 2 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "How far? Which direction (Up - U, Down - D, Left - L, Right - R)? "
        + "You shot the arrow into the darkness!\n"
        + "\n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 1 arrow in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " How far? Which direction (Up - U, Down - D, Left - L, Right - R)?"
        + " You shot the arrow into the darkness!\n"
        + "\n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You are out of arrows. Explore to find more!.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "Yayy!!!!! You've conquered the dungeon by slaying the boss Otyugh!\n"
        + "You might wanna play again!";
    assertEquals(check, gameLog.toString());

    //Testing the number of lines in the output
    int lines = check.split("[\n|\r]").length;
    assertEquals(81, lines);
  }

  /**
   * Tests player shooting the arrow in wrapping dungeon with exact distance and correct direction
   * and asserting that the monster gets killed.
   */
  @Test
  public void testPlayerShootingArrowInWrapping() {
    Game game = new FantasyRolePlayingGame(5, 5, true, 5, 50, 2, 2, 2, new FixedRandom());
    StringReader input = new StringReader("S 3 R S 3 R M L M L M D P A S 2 D S 2 D M D M D");
    Appendable gameLog = new StringBuilder();
    GameConsoleController c = new GameConsoleControllerImpl(input, gameLog);
    c.playGame(game);
    String check = "\n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "How far? Which direction (Up - U, Down - D, Left - L, Right - R)? "
        + "You shot the arrow into the darkness!\n"
        + "\n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 2 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " How far? Which direction (Up - U, Down - D, Left - L, Right - R)? "
        + "You shot the arrow into the darkness!\n"
        + "\n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 1 arrow in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 1 arrow in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "You spot a trophy. The monster you have slayed!\n"
        + "\n"
        + "You currently have 1 arrow in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 1 arrow in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "What (Weapon - A, Ruby - R, Diamond - D, Sapphire - S)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You currently have 2 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "How far? Which direction (Up - U, Down - D, Left - L, Right - R)? "
        + "You shot the arrow into the darkness!\n"
        + "\n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You currently have 1 arrow in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "How far? Which direction (Up - U, Down - D, Left - L, Right - R)? "
        + "You shot the arrow into the darkness!\n"
        + "\n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You are out of arrows. Explore to find more!.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "\n"
        + "You are out of arrows. Explore to find more!.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "Yayy!!!!! You've conquered the dungeon by slaying the boss Otyugh!\n"
        + "You might wanna play again!";
    assertEquals(check, gameLog.toString());

    //Testing the number of lines in the output
    int lines = check.split("[\n|\r]").length;
    assertEquals(72, lines);
  }

  /**
   * Tests player missing the monster if the distance of the shot is not exact.
   */
  @Test
  public void testPlayerMissesTargetIfDistanceIsNotExact() {
    Game game = new FantasyRolePlayingGame(5, 5, true, 5, 50, 2, 2, 2, new FixedRandom());
    StringReader input = new StringReader("S 3 R S 3 R M L M L M D P A S 3 D S 3 D M D M D");
    Appendable gameLog = new StringBuilder();
    GameConsoleController c = new GameConsoleControllerImpl(input, gameLog);
    c.playGame(game);
    String check = "\n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "How far? Which direction (Up - U, Down - D, Left - L, Right - R)? "
        + "You shot the arrow into the darkness!\n"
        + "\n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 2 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "How far? Which direction (Up - U, Down - D, Left - L, Right - R)? "
        + "You shot the arrow into the darkness!\n"
        + "\n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 1 arrow in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 1 arrow in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "You spot a trophy. The monster you have slayed!\n"
        + "\n"
        + "You currently have 1 arrow in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 1 arrow in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "What (Weapon - A, Ruby - R, Diamond - D, Sapphire - S)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You currently have 2 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "How far? Which direction (Up - U, Down - D, Left - L, Right - R)?"
        + " You shot the arrow into the darkness!\n"
        + "\n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You currently have 1 arrow in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "How far? Which direction (Up - U, Down - D, Left - L, Right - R)? "
        + "You shot the arrow into the darkness!\n"
        + "\n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You are out of arrows. Explore to find more!.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "\n"
        + "You are out of arrows. Explore to find more!.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "Oops!!!!! An hungry monster just obliterated you!\n"
        + "You might wanna play again!";
    assertEquals(check, gameLog.toString());

    //Testing the number of lines in the output
    int lines = check.split("[\n|\r]").length;
    assertEquals(74, lines);
  }

  /**
   * Tests that the arrow travels straight in caves and curves in tunnels.
   */
  @Test
  public void testArrowCurvesInTunnelAndTravelsStraightInTheCave() {
    Game game = new FantasyRolePlayingGame(5, 5, true, 5, 50, 2, 2, 2, new FixedRandom());
    StringReader input = new StringReader("S 3 R S 3 R M L M L M D P A S 2 D S 2 D M D M D");
    Appendable gameLog = new StringBuilder();
    GameConsoleController c = new GameConsoleControllerImpl(input, gameLog);
    c.playGame(game);
    String check = "\n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "How far? Which direction (Up - U, Down - D, Left - L, Right - R)? "
        + "You shot the arrow into the darkness!\n"
        + "\n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 2 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " How far? Which direction (Up - U, Down - D, Left - L, Right - R)? "
        + "You shot the arrow into the darkness!\n"
        + "\n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 1 arrow in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 1 arrow in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "You spot a trophy. The monster you have slayed!\n"
        + "\n"
        + "You currently have 1 arrow in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 1 arrow in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "What (Weapon - A, Ruby - R, Diamond - D, Sapphire - S)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You currently have 2 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "How far? Which direction (Up - U, Down - D, Left - L, Right - R)? "
        + "You shot the arrow into the darkness!\n"
        + "\n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You currently have 1 arrow in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "How far? Which direction (Up - U, Down - D, Left - L, Right - R)? "
        + "You shot the arrow into the darkness!\n"
        + "\n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You are out of arrows. Explore to find more!.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "\n"
        + "You are out of arrows. Explore to find more!.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "Yayy!!!!! You've conquered the dungeon by slaying the boss Otyugh!\n"
        + "You might wanna play again!";
    assertEquals(check, gameLog.toString());

    //Testing the number of lines in the output
    int lines = check.split("[\n|\r]").length;
    assertEquals(72, lines);
  }

  /**
   * Tests that the player is initially starting with 3 arrows.
   */
  @Test
  public void testPlayerInitialArrows() {
    Game game = new FantasyRolePlayingGame(5, 5, true, 5, 50, 2, 2, 2, new FixedRandom());
    StringReader input = new StringReader("Q");
    Appendable gameLog = new StringBuilder();
    GameConsoleController c = new GameConsoleControllerImpl(input, gameLog);
    c.playGame(game);
    String check = "\n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? \n"
        + "Booo!!!!! You just gave up!\n"
        + "You might wanna play again!";
    assertEquals(check, gameLog.toString());

    //Testing the number of lines in the output
    int lines = check.split("[\n|\r]").length;
    assertEquals(10, lines);
  }

  /**
   * Tests player running out of arrows.
   */
  @Test
  public void testPlayerRunOutOfArrows() {
    Game game = new FantasyRolePlayingGame(5, 5, true, 5, 50, 2, 2, 2, new FixedRandom());
    StringReader input = new StringReader("S 1 R S 1 R S 1 R S 1 R S 1 R Q");
    Appendable gameLog = new StringBuilder();
    GameConsoleController c = new GameConsoleControllerImpl(input, gameLog);
    c.playGame(game);
    String check = "\n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " How far? Which direction (Up - U, Down - D, Left - L, Right - R)? "
        + "You shot the arrow into the darkness!\n"
        + "\n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 2 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " How far? Which direction (Up - U, Down - D, Left - L, Right - R)? "
        + "You shot the arrow into the darkness!\n"
        + "\n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 1 arrow in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " How far? Which direction (Up - U, Down - D, Left - L, Right - R)? "
        + "You shot the arrow into the darkness!\n"
        + "\n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You are out of arrows. Explore to find more!.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " How far? Which direction (Up - U, Down - D, Left - L, Right - R)? "
        + "Oops. No more arrows!\n"
        + "\n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You are out of arrows. Explore to find more!.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " How far? Which direction (Up - U, Down - D, Left - L, Right - R)? "
        + "Oops. No more arrows!\n"
        + "\n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You are out of arrows. Explore to find more!.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? \n"
        + "Booo!!!!! You just gave up!\n"
        + "You might wanna play again!";
    assertEquals(check, gameLog.toString());

    //Testing the number of lines in the output
    int lines = check.split("[\n|\r]").length;
    assertEquals(50, lines);
  }

  /**
   * Tests that there is no monster is at the beginning and there is a monster always present in
   * the end cave.
   */
  @Test
  public void testMonsterNotInCaveAndAlwaysAtEnd() {
    Game game = new FantasyRolePlayingGame(5, 5, true, 5, 50, 2, 2, 2, new FixedRandom());
    StringReader input = new StringReader("S 3 R S 3 R M L M L M D P A S 2 D S 2 D M D M D");
    Appendable gameLog = new StringBuilder();
    GameConsoleController c = new GameConsoleControllerImpl(input, gameLog);
    c.playGame(game);
    String check = "\n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "How far? Which direction (Up - U, Down - D, Left - L, Right - R)? "
        + "You shot the arrow into the darkness!\n"
        + "\n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 2 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " How far? Which direction (Up - U, Down - D, Left - L, Right - R)? "
        + "You shot the arrow into the darkness!\n"
        + "\n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 1 arrow in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 1 arrow in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "You spot a trophy. The monster you have slayed!\n"
        + "\n"
        + "You currently have 1 arrow in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 1 arrow in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "What (Weapon - A, Ruby - R, Diamond - D, Sapphire - S)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You currently have 2 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "How far? Which direction (Up - U, Down - D, Left - L, Right - R)? "
        + "You shot the arrow into the darkness!\n"
        + "\n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You currently have 1 arrow in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "How far? Which direction (Up - U, Down - D, Left - L, Right - R)? "
        + "You shot the arrow into the darkness!\n"
        + "\n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond\n"
        + "\n"
        + "You are out of arrows. Explore to find more!.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "\n"
        + "You are out of arrows. Explore to find more!.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "Yayy!!!!! You've conquered the dungeon by slaying the boss Otyugh!\n"
        + "You might wanna play again!";
    assertEquals(check, gameLog.toString());

    //Testing the number of lines in the output
    int lines = check.split("[\n|\r]").length;
    assertEquals(72, lines);
  }

  /**
   * Tests that the controller can handle case-sensitive input.
   */
  @Test
  public void testCaseSensitive() {
    Game game = new FantasyRolePlayingGame(5, 5, false, 5, 50, 2, 2, 2, new FixedRandom());
    StringReader input = new StringReader("m d m d m r s 1 r 1 q");
    Appendable gameLog = new StringBuilder();
    GameConsoleController c = new GameConsoleControllerImpl(input, gameLog);
    c.playGame(game);
    String check = "\n"
        + "You are smelling something terrible! Threat Looming!\n"
        + "You are in a CAVE and can move DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)?"
        + " Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "This CAVE has 1 Diamond and 1 arrow\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You are in a CAVE and can move UP, DOWN, RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Where to (Up - U, Down - D, Left - L, Right - R)? \n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 3 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "How far? Which direction (Up - U, Down - D, Left - L, Right - R)? "
        + "You shot the arrow into the darkness!\n"
        + "\n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 2 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? "
        + "Didn't offer that Option!\n"
        + "\n"
        + "You take a whiff and smell something foul nearby!\n"
        + "You are in a TUNNEL and can move RIGHT, LEFT\n"
        + "\n"
        + "You currently have 2 arrows in your bag that you can use.\n"
        + "You have not picked any treasure yet.\n"
        + "What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? \n"
        + "Booo!!!!! You just gave up!\n"
        + "You might wanna play again!";
    assertEquals(check, gameLog.toString());

    //Testing the number of lines in the output
    int lines = check.split("[\n|\r]").length;
    assertEquals(42, lines);
  }
}
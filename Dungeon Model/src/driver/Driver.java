package driver;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;

import controller.GameConsoleControllerImpl;
import controller.GameSwingControllerImpl;
import model.dungeonsanddragons.FantasyRolePlayingGame;
import model.dungeonsanddragons.Game;
import view.GameView;
import view.GameViewImpl;

import java.io.InputStreamReader;

/**
 * Runs a Dungeons and Dragons game interactively on the console.
 */
public class Driver {

  /**
   * Represents a starting point for the execution of the game.
   */
  public static void main(String [] args) {

    if (args.length == 0) {
      /*
      GUI game begins here. Game starts of with a 'challenging' dungeon. Player can later use
      in game menu settings to modify or continue with the same game.
       */
      Game model = new FantasyRolePlayingGame(5, 5, false, 5, 60, 2, 2, 1);
      GameView view = new GameViewImpl();
      new GameSwingControllerImpl(model, view).playGame();
    }
    else {
      /*
      Console game begins here. Parameters/Settings of the game are taken in as arguments from
      the player.
       */
      if (args.length < 6) {
        throw new IllegalArgumentException("\n\n>> Illegal Arguments. Please enter row, column, "
            + "wrapping status, interconnectivity degree, treasure percentage, and number of "
            + "monsters. <<\n\n");
      }
      else {
        try {
          Readable input = new InputStreamReader(System.in);
          Appendable output = System.out;
          new GameConsoleControllerImpl(input, output)
              .playGame(new FantasyRolePlayingGame(parseInt(args[0]), parseInt(args[1]),
                  parseBoolean(args[2]), parseInt(args[3]), parseInt(args[4]),
                  parseInt(args[5]), 1, 1));
        } catch (IllegalArgumentException e) {
          /*Throwing the exception so that the game doesn't start when there are invalid arguments
          for a model.
           */
          throw e;
        }
      }
    }
  }
}

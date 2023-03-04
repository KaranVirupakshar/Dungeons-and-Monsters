package controller;

import model.dungeonsanddragons.Game;
import model.dungeonsanddragons.Smell;
import model.dungeonsanddragons.Treasure;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

/**
 * GameConsoleControllerImpl class represents the methods that receives all its inputs from a
 * Readable object and transmits all outputs to an Appendable object. It uses the game model and
 * executes the available commands based on the input. Since the driver uses this class to handle
 * user inputs and interact with the model, this class is kept as public.
 */
public class GameConsoleControllerImpl implements GameConsoleController {

  private final Appendable out;
  private final Scanner scan;

  /**
   * Constructor to initialize the controller with the readable for input and the appendable
   * for output.
   *
   * @param in  the source to read from.
   * @param out the target to print to.
   * @throws IllegalArgumentException if the readable or appendable is null.
   */
  public GameConsoleControllerImpl(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.out = out;
    scan = new Scanner(in);
  }

  /**
   * Plays out a full game of dungeons and dragons until the player wins or gets killed or quits
   * the game. Command pattern is used to execute the user inputs.
   *
   * @param g is a non-null Game ModelM
   * @throws IllegalArgumentException if the game model is invalid.
   * @throws IllegalStateException if the append fails.
   */
  @Override
  public void playGame(Game g) {

    if (Objects.isNull(g)) {
      throw new IllegalArgumentException("Model is invalid!");
    }

    GameCommand cmd;
    boolean isQuit = false;

    try {
      while (!g.isReachedEnd() && !g.isKilled() && !isQuit) {

        //Displaying Game State and Player Description at every move.
        gameState(g);
        playerDescription(g);

        out.append("What do you want to do (Direction - M, Pick - P, Shoot - S, Quit - Q)? ");
        String input = scan.next().toUpperCase(Locale.ROOT);

        switch (input) {
          case "M":
            out.append("Where to (Up - U, Down - D, Left - L, Right - R)? ");
            String opt = scan.next().toUpperCase(Locale.ROOT);
            try {
              cmd = new MovePlayer(opt);
              cmd.execute(g);
            } catch (IllegalStateException e) {
              out.append(String.format("%s\n",e.getMessage()));
            } catch (IllegalArgumentException a) {
              out.append(String.format("%s\n",a.getMessage()));
            }
            break;

          case "P":
            out.append("What (Weapon - A, Ruby - R, Diamond - D, Sapphire - S)? ");
            opt = scan.next().toUpperCase(Locale.ROOT);

            try {
              if (opt.equals("A")) {
                cmd = new PickArrow();
                cmd.execute(g);
              }
              else {
                cmd = new PickTreasure(opt);
                cmd.execute(g);
                break;
              }
            } catch (IllegalStateException s) {
              out.append("Can't find that in here!\n");
            } catch (IllegalArgumentException a) {
              out.append(String.format("%s\n",a.getMessage()));
            }
            break;

          case "S":
            try {
              out.append("How far? ");
              int dis = scan.nextInt();
              out.append("Which direction (Up - U, Down - D, Left - L, Right - R)? ");
              String dir = scan.next().toUpperCase(Locale.ROOT);
              cmd = new ShootArrow(dis, dir);
              cmd.execute(g);
              out.append("You shot the arrow into the darkness!\n");
            } catch (IllegalStateException e) {
              out.append(String.format("%s\n",e.getMessage()));
            } catch (IllegalArgumentException a) {
              out.append(String.format("%s\n",a.getMessage()));
            } catch (InputMismatchException e) {
              out.append("Distance takes a numerical value!\n");
            }
            break;

          case "Q":
            isQuit = true;
            break;

          default:
            out.append("Didn't offer that Option!\n");
            break;
        }
      }

      if (g.isKilled()) {
        out.append("\nOops!!!!! An hungry monster just obliterated you!\n");
      }

      if (g.isReachedEnd()) {
        out.append("\nYayy!!!!! You've conquered the dungeon by slaying the boss Otyugh!\n");
      }

      if (isQuit) {
        out.append("\nBooo!!!!! You just gave up!\n");
      }
      out.append("You might wanna play again!");

    } catch (IOException e) {
      throw new IllegalStateException("Append failed!", e);
    }
  }

  /**
   * Private helper method to parse the game state returned by the model. Since an independent
   * view doesn't exist, controller is responsible for generating the view to the user.
   *
   * @param g represents the game model
   */
  private void gameState(Game g) {
    boolean treasureFound = false;
    boolean weaponFound = false;

    try {

      out.append("\n");

      if (g.getSmell() != null && g.getSmell().equals(Smell.PUNGENT)) {
        out.append("You take a whiff and smell something foul nearby!\n");
      } else if (g.getSmell() != null && g.getSmell().equals(Smell.MOREPUNGENT)) {
        out.append("You are smelling something terrible! Threat Looming!\n");
      }

      out.append(String.format("You are in a %s and can move %s\n",
          g.gameState().getLocationType(), parseMoves(g)));

      if (g.gameState().getTreasure().size() > 0) {
        treasureFound = true;
      }
      if (g.gameState().getArrows().size() > 0) {
        weaponFound = true;
      }

      if (treasureFound && weaponFound) {
        if (g.gameState().getArrows().size() == 1) {
          out.append(String.format("This %s has %s and %s arrow\n",
              g.gameState().getLocationType(), parseCaveTreasure(g),
              g.gameState().getArrows().size()));
        } else {
          out.append(String.format("This %s has %s and %s arrows\n",
              g.gameState().getLocationType(), parseCaveTreasure(g),
              g.gameState().getArrows().size()));
        }
      } else if (treasureFound && !weaponFound) {
        out.append(String.format("This %s has %s\n",
            g.gameState().getLocationType(),
            parseCaveTreasure(g)));
      } else if (!treasureFound && weaponFound) {
        if (g.gameState().getArrows().size() == 1) {
          out.append(String.format("This %s has %s arrow\n",
              g.gameState().getLocationType(),
              g.gameState().getArrows().size()));
        } else {
          out.append(String.format("This %s has %s arrows\n",
              g.gameState().getLocationType(),
              g.gameState().getArrows().size()));
        }
      }

      if (g.gameState().getMonsterHealth() == 0) {
        out.append("You spot a trophy. The monster you have slayed!\n");
      }
    }
    catch (IOException e) {
      throw new IllegalStateException("Append failed!", e);
    }
  }

  /**
   * Private helper method to parse the description of the player returned by the model. Since an
   * independent view doesn't exist, controller is responsible for generating the view to the
   * user.
   *
   * @param g represents the game model
   */
  private void playerDescription(Game g) {
    try {
      if (g.getArrowsInPlayerBag().size() > 0) {
        if (g.getArrowsInPlayerBag().size() == 1) {
          out.append(String.format("\nYou currently have %s arrow in your bag "
              + "that you can use.\n", g.getArrowsInPlayerBag().size()));
        } else {
          out.append(String.format("\nYou currently have %s arrows in your bag "
              + "that you can use.\n", g.getArrowsInPlayerBag().size()));
        }
      } else if (g.getArrowsInPlayerBag().size() == 0) {
        out.append("\nYou are out of arrows. Explore to find more!.\n");
      }

      if (g.getTreasureCollectedByPlayer().size() == 0) {
        out.append("You have not picked any treasure yet.\n");
      } else {
        out.append(String.format("So far, You have picked %s\n", parsePlayerTreasure(g)));
      }
    }
    catch (IOException e) {
      throw new IllegalStateException("Append failed!", e);
    }
  }

  /**
   * Private helper method to parse the treasure objects of the cave.
   *
   * @param g represents the game model.
   * @return the treasure in the caves as a String.
   */
  private String parseCaveTreasure(Game g) {
    String diamonds = "";
    String rubies = "";
    String sapphires = "";


    if (g.gameState().getLocationType().equals("CAVE")) {

      if (g.gameState().getTreasure().size() > 0) {
        int rCount = 0;
        int dCount = 0;
        int sCount = 0;

        for (Treasure treasure : g.gameState().getTreasure()) {
          if (treasure.toString().equals("RUBY")) {
            rCount += 1;
          } else if (treasure.toString().equals("DIAMOND")) {
            dCount += 1;
          } else if (treasure.toString().equals("SAPPHIRE")) {
            sCount += 1;
          }
        }

        diamonds = treasureCountHelper(dCount, "Diamond");
        rubies = treasureCountHelper(rCount, "Ruby");
        sapphires = treasureCountHelper(sCount, "Sapphire");
      }
    }
    return treasureDisplayHelper(diamonds, rubies, sapphires);
  }

  /**
   * Private helper method to parse the treasure objects of the player.
   *
   * @param g represents the game model.
   * @return the treasure in the players bag as a String.
   */
  private String parsePlayerTreasure(Game g) {

    int rCount = 0;
    int dCount = 0;
    int sCount = 0;

    for (Treasure treasure : g.getTreasureCollectedByPlayer()) {
      if (treasure.toString().equals("RUBY")) {
        rCount += 1;
      } else if (treasure.toString().equals("DIAMOND")) {
        dCount += 1;
      } else if (treasure.toString().equals("SAPPHIRE")) {
        sCount += 1;
      }
    }

    String diamonds = "";
    String rubies = "";
    String sapphires = "";

    diamonds = treasureCountHelper(dCount, "Diamond");
    rubies = treasureCountHelper(rCount, "Ruby");
    sapphires = treasureCountHelper(sCount, "Sapphire");

    return treasureDisplayHelper(diamonds, rubies, sapphires);
  }

  /**
   * Private helper method to parse the available moves in the location returned by the game model.
   *
   * @param g represents the game model.
   * @return the possible available moves as a String.
   */
  private String parseMoves(Game g) {
    String moves = "";

    for (String move : g.gameState().getPossibleMoves()) {
      moves += String.format("%s, ", move);
    }

    moves = moves.substring(0, moves.length() - 2);
    return moves;
  }

  /**
   * Private helper method to calculate the count of treasure.
   *
   * @param count represents the number of treasure items.
   * @param type represents the treasure type
   * @return the number of arrows with appropriate plural form.
   */
  private String treasureCountHelper(int count, String type) {
    String treasure = "";
    if (count == 1) {
      treasure = String.format("%d %s",count, type);
    } else if (count > 1) {
      treasure = String.format("%d %ss",count, type);
    }
    return treasure;
  }

  /**
   * Private helper method to parse the treasure to a readable format.
   *
   * @param diamonds represents the treasure type diamond.
   * @param rubies represents the treasure type ruby.
   * @param sapphires represents the treasure type sapphire.
   * @return the total treasure contained
   */
  private String treasureDisplayHelper(String diamonds, String rubies, String sapphires) {
    String total = "";
    if (diamonds.length() > 1) {
      total += String.format("%s, ", diamonds);
    }
    if (rubies.length() > 1) {
      total += String.format("%s, ", rubies);
    }
    if (sapphires.length() > 1) {
      total += String.format("%s, ", sapphires);
    }

    if (total.length() > 1) {
      total = total.substring(0, total.length() - 2);
      return total;
    } else {
      return "no treasure!";
    }
  }

}

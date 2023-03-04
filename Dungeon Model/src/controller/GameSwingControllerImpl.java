package controller;

import model.dungeonsanddragons.FantasyRolePlayingGame;
import model.dungeonsanddragons.Game;
import view.GameView;

import java.util.Random;

/**
 * GameConsoleControllerImpl class represents the methods that receives all its inputs from a
 * Graphical User Interface (GUI) view and processes all these inputs to call the relevant methods
 * of the model. It uses the game model and executes the available commands based on the input.
 * Since the driver uses this class to handle user inputs and interact with the model, this class
 * is kept as public.
 */
public class GameSwingControllerImpl implements GameSwingController {

  private Game model;
  private final GameView view;
  private int seed;

  /**
   * A constructor to initialize the controller with a Graphical User Interface(GUI) view.
   *
   * @param view represents the GUI view.
   */
  public GameSwingControllerImpl(Game model, GameView view) {
    this.model = model;
    this.view = view;
  }

  /**
   * Plays a game of dungeons and dragons.
   */
  @Override
  public void playGame() {
    this.view.setFeatures(this);
    this.view.resetFocus();
    view.generateDungeonPanel(model.getRows(), model.getColumns(),
        model.gameState());
    view.updateDungeonPanel(model.gameState());
  }

  /**
   * Takes the mouse click or keyboard input and calls the move method of the model.
   *
   * @param direction represents the direction of the move.
   */
  @Override
  public void move(String direction) {
    try {
      GameCommand cmd;
      cmd = new MovePlayer(direction);
      cmd.execute(model);
      model.checkTreasureStolen(System.currentTimeMillis());
      view.updateDungeonPanel(model.gameState());
      view.displayPlayerAction("Player moved " + direction);
      if (model.isKilled()) {
        view.gameOver(this, model.gameState(), " YOU GOT KILLED!!!!");
      }
      if (model.isReachedEnd()) {
        view.gameOver(this, model.gameState(), "YOU WON THE GAME!!!!");
      }
      if (model.isFallen()) {
        view.gameOver(this, model.gameState(), "YOU FELL INTO THE PIT!!!!");
      }
    }
    catch (IllegalStateException e) {
      /*
      Catch block is left empty so that there are no exceptions thrown on the console when the
      GUI is running. Made a decision to not throw any console messages since the user of the
      program will only have access to the GUI. Leaving the catch block empty also means that
      'nothing' happens on the view when an exception is thrown by the model, (cascading) which is
      intended.
       */
    }
    catch (IllegalArgumentException a) {
      /*
      Catch block is left empty so that there are no exceptions thrown on the console when the
      GUI is running. Made a decision to not throw any console messages since the user of the
      program will only have access to the GUI. Leaving the catch block empty also means that
      'nothing' happens on the view when an exception is thrown by the model, (cascading) which is
      intended.
       */
    }
  }

  /**
   * Takes the mouse click or keyboard input and calls the pick treasure of the model.
   *
   * @param treasure represents the treasure item to be picked.
   */
  @Override
  public void pickTreasure(String treasure) {
    try {
      GameCommand cmd;
      cmd = new PickTreasure(treasure);
      cmd.execute(model);
      view.updateDungeonPanel(model.gameState());
      view.displayPlayerAction("Player picked a " + treasure);
    }
    catch (IllegalStateException e) {
      /*
      Catch block is left empty so that there are no exceptions thrown on the console when the
      GUI is running. Made a decision to not throw any console messages since the user of the
      program will only have access to the GUI. Leaving the catch block empty also means that
      'nothing' happens on the view when an exception is thrown by the model, (cascading) which is
      intended.
       */
    }
    catch (IllegalArgumentException a) {
      /*
      Catch block is left empty so that there are no exceptions thrown on the console when the
      GUI is running. Made a decision to not throw any console messages since the user of the
      program will only have access to the GUI. Leaving the catch block empty also means that
      'nothing' happens on the view when an exception is thrown by the model, (cascading) which is
      intended.
       */
    }
  }

  /**
   * Takes the mouse click or keyboard input and calls the pick arrow method of the model.
   */
  @Override
  public void pickArrow() {
    try {
      GameCommand cmd;
      cmd = new PickArrow();
      cmd.execute(model);
      view.updateDungeonPanel(model.gameState());
      view.displayPlayerAction("Player picked an arrow");
    }
    catch (IllegalArgumentException a) {
       /*
      Catch block is left empty so that there are no exceptions thrown on the console when the
      GUI is running. Made a decision to not throw any console messages since the user of the
      program will only have access to the GUI. Leaving the catch block empty also means that
      'nothing' happens on the view when an exception is thrown by the model, (cascading) which is
      intended.
       */
    }
    catch (IllegalStateException s) {
       /*
      Catch block is left empty so that there are no exceptions thrown on the console when the
      GUI is running. Made a decision to not throw any console messages since the user of the
      program will only have access to the GUI. Leaving the catch block empty also means that
      'nothing' happens on the view when an exception is thrown by the model, (cascading) which is
      intended.
       */
    }
  }

  /**
   * Takes the mouse click or keyboard input and calls the shoot arrow method of the model.
   *
   * @param direction represents the direction entered by the user.
   * @param distance represents the distance entered by the user.
   */
  @Override
  public void shootArrow(String direction, int distance) {
    try {
      GameCommand cmd;
      cmd = new ShootArrow(distance, direction);
      cmd.execute(model);
      view.updateDungeonPanel(model.gameState());
      view.displayPlayerAction("Arrow shot towards " + direction + " by distance "
          + distance, 1500);
    }
    catch (IllegalArgumentException a) {
       /*
      Catch block is left empty so that there are no exceptions thrown on the console when the
      GUI is running. Made a decision to not throw any console messages since the user of the
      program will only have access to the GUI. Leaving the catch block empty also means that
      'nothing' happens on the view when an exception is thrown by the model, (cascading) which is
      intended.
       */
    }
    catch (IllegalStateException s) {
       /*
      Catch block is left empty so that there are no exceptions thrown on the console when the
      GUI is running. Made a decision to not throw any console messages since the user of the
      program will only have access to the GUI. Leaving the catch block empty also means that
      'nothing' happens on the view when an exception is thrown by the model, (cascading) which is
      intended.
       */
    }
  }

  /**
   * Creates a model based on the inputs from the view.
   *
   * @param row represents the number of rows in the grid.
   * @param column represents the number of columns in the grid.
   * @param treasure represents the treasure percentage in the game.
   * @param interconnectivity represents the interconnectivity degree.
   * @param monster represents the number of monster.
   * @param thieves represents the number of thieves.
   * @param pits represents the number of pits in the game.
   * @param wrapping represents the wrapped status.
   */
  @Override
  public void createModel(int row, int column, boolean wrapping, int interconnectivity,
      int treasure, int monster, int thieves, int pits) {
    try {
      seed = new Random().nextInt(10000);
      this.model = new FantasyRolePlayingGame(row, column, wrapping, interconnectivity,
          treasure, monster, thieves, pits, new Random(seed));
      view.generateDungeonPanel(row, column, model.gameState());
      view.updateDungeonPanel(model.gameState());
    }
    catch (IllegalArgumentException a) {
      view.displayPlayerAction("Illegal Settings for dungeon creation!", 4000);
    }
  }

  /**
   * Resets the model based on the inputs from the view. Model is reset using the same seed to
   * retain the node locations, and contents.
   *
   * @param row represents the number of rows in the grid.
   * @param column represents the number of columns in the grid.
   * @param treasure represents the treasure percentage in the game.
   * @param interconnectivity represents the interconnectivity degree.
   * @param monster represents the number of monster.
   * @param thieves represents the number of thieves.
   * @param pits represents the number of pits in the game.
   * @param wrapping represents the wrapped status.
   */
  @Override
  public void resetModel(int row, int column, boolean wrapping,
      int interconnectivity, int treasure, int monster, int thieves, int pits) {
    try {
      this.model = new FantasyRolePlayingGame(row, column, wrapping, interconnectivity,
          treasure, monster, thieves, pits, new Random(seed));
      view.generateDungeonPanel(row, column, model.gameState());
      view.updateDungeonPanel(model.gameState());
    }
    catch (IllegalArgumentException a) {
      view.displayPlayerAction("Illegal Settings for dungeon creation!", 4000);
    }
  }

}

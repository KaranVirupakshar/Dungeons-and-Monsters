package controller;

import model.dungeonsanddragons.Game;

/**
 * GameConsoleController interface represents a GameConsoleControllerImpl for Dungeons and Dragons
 * game. It can handle player moving through the dungeon, picking up treasure and arrows, as well
 * as killing the monsters. It also conveys the state of the location at each move and allows the
 * player to quit the game. Since this acts as a controller to play out the fantasy-based dungeon
 * game, a decision was made to keep the interface public.
 */
public interface GameConsoleController {

  /**
   * Plays out a full game of dungeons and dragons until the player wins or gets killed or quits
   * the game.
   *
   * @param g is a non-null Game Model
   */
  void playGame(Game g);
}

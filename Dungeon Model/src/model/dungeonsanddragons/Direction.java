package model.dungeonsanddragons;

/**
 * Direction enumeration represents all the possible moves that the player can make while playing
 * the game of dungeons and dragons. This also represents the direction in which a player can shoot
 * the arrow. Since the user needs to be able to control the player and move, this will be used
 * directly by the user of the program. Hence, this enumeration is kept as public.
 */
public enum Direction {
  UP,
  DOWN,
  LEFT,
  RIGHT
}
